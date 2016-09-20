/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.service.permission.AssetPermission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class UpgradePermission extends UpgradeProcess {

	protected int checkActions(List<Integer> resourceActions, int actionsIds) {
		int newActionsIds = 0;

		for (Integer resourceAction : resourceActions) {
			if ((actionsIds & resourceAction.intValue()) != 0) {
				newActionsIds += resourceAction.intValue();
			}
		}

		return newActionsIds;
	}

	protected int checkCategoriesActions(int actionsIds) throws Exception {
		List<Integer> resourceActions = getResourceActions(
			AssetPermission.RESOURCE_NAME_CATEGORIES);

		return checkActions(resourceActions, actionsIds);
	}

	protected int checkTagsActions(int actionsIds) throws Exception {
		List<Integer> resourceActions = getResourceActions(
			AssetPermission.RESOURCE_NAME_TAGS);

		return checkActions(resourceActions, actionsIds);
	}

	protected void deleteResourceActions() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(3);

			sb.append("delete from ResourceAction where name = '");
			sb.append(AssetPermission.RESOURCE_NAME);
			sb.append("';");

			runSQL(sb.toString());
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteResourceActions();
		loadResourceActions();
		renameResourcePermission();
	}

	protected List<Integer> getResourceActions(String resourceName)
		throws Exception {

		List<Integer> resourceActions = new ArrayList<>();

		StringBundler sb = new StringBundler(1);

		sb.append("select bitwiseValue from ResourceAction where name = ?;");

		PreparedStatement ps = connection.prepareStatement(sb.toString());

		ps.setString(1, resourceName);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				resourceActions.add(rs.getInt(1));
			}
		}

		return resourceActions;
	}

	protected void loadResourceActions() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[] sources = PropsValues.RESOURCE_ACTIONS_CONFIGS;

			Class<?> clazz = getClass();
			ClassLoader classLoader = clazz.getClassLoader();

			for (String source : sources) {
				ResourceActionsUtil.read(null, classLoader, source);
			}

			List<String> categoriesResourceActions =
				ResourceActionsUtil.getResourceActions(
					AssetPermission.RESOURCE_NAME_CATEGORIES);

			StringBundler sb = new StringBundler(3);

			sb.append("insert into ResourceAction (");
			sb.append("resourceActionId, name, actionId, bitwiseValue) ");
			sb.append("values (?, ?, ?, ?);");

			long availableBits = -2;

			for (String action : categoriesResourceActions) {
				PreparedStatement ps = connection.prepareStatement(
					sb.toString());

				long bitwiseValue = Long.lowestOneBit(availableBits);

				availableBits ^= bitwiseValue;

				ps.setLong(1, increment());
				ps.setString(2, AssetPermission.RESOURCE_NAME_CATEGORIES);
				ps.setString(3, action);
				ps.setLong(4, bitwiseValue);

				ps.execute();
			}

			List<String> tagsResourceActions =
				ResourceActionsUtil.getResourceActions(
					AssetPermission.RESOURCE_NAME_TAGS);

			availableBits = -2;

			for (String action : tagsResourceActions) {
				PreparedStatement ps = connection.prepareStatement(
					sb.toString());

				long bitwiseValue = Long.lowestOneBit(availableBits);

				availableBits ^= bitwiseValue;

				ps.setLong(1, increment());
				ps.setString(2, AssetPermission.RESOURCE_NAME_TAGS);
				ps.setString(3, action);
				ps.setLong(4, bitwiseValue);

				ps.execute();
			}

		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void renameResourcePermission() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(4);

			sb.append("select actionIds, companyId, ");
			sb.append("ownerId, primKey, primKeyId, resourcePermissionId, ");
			sb.append("roleId, scope, viewActionId from ResourcePermission ");
			sb.append("where name = ?");

			PreparedStatement ps = connection.prepareStatement(sb.toString());

			ps.setString(1, AssetPermission.RESOURCE_NAME);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					int scope = rs.getInt("scope");
					long ownerId = rs.getLong("ownerId");
					long primKeyId = rs.getLong("primKeyId");
					String primKey = rs.getString("primKey");
					long resourcePermissionId = rs.getLong(
						"resourcePermissionId");
					long roleId = rs.getLong("roleId");
					int actionIds = rs.getInt("actionIds");
					int viewActionId = rs.getInt("viewActionId");

					int tagsActions = checkTagsActions(actionIds);
					int categoriesActions = checkCategoriesActions(actionIds);

					StringBundler insertSb = new StringBundler(6);

					insertSb.append("insert into ResourcePermission (");
					insertSb.append("resourcePermissionId, companyId, ");
					insertSb.append("name, scope, primKey, primKeyId, ");
					insertSb.append("roleId, ownerId, actionIds, ");
					insertSb.append("viewActionId) values (");
					insertSb.append("?,?,?,?,?,?,?,?,?,?);");

					StringBundler deleteSb = new StringBundler();

					deleteSb.append("delete from ResourcePermission where ");
					deleteSb.append("resourcePermissionId = ?;");

					PreparedStatement deletePs = connection.prepareStatement(
						deleteSb.toString());

					deletePs.setLong(1, resourcePermissionId);

					deletePs.execute();

					if (tagsActions != 0) {
						PreparedStatement insertPs =
							connection.prepareStatement(insertSb.toString());

						insertPs.setLong(1, increment());
						insertPs.setLong(2, companyId);
						insertPs.setString(
							3, AssetPermission.RESOURCE_NAME_TAGS);
						insertPs.setInt(4, scope);
						insertPs.setString(5, primKey);
						insertPs.setLong(6, primKeyId);
						insertPs.setLong(7, roleId);
						insertPs.setLong(8, ownerId);
						insertPs.setInt(9, tagsActions);
						insertPs.setInt(10, viewActionId);

						insertPs.execute();
					}

					if (categoriesActions != 0) {
						PreparedStatement insertPs =
							connection.prepareStatement(insertSb.toString());

						insertPs.setLong(1, increment());
						insertPs.setLong(2, companyId);
						insertPs.setString(
							3, AssetPermission.RESOURCE_NAME_CATEGORIES);
						insertPs.setInt(4, scope);
						insertPs.setString(5, primKey);
						insertPs.setLong(6, primKeyId);
						insertPs.setLong(7, roleId);
						insertPs.setLong(8, ownerId);
						insertPs.setInt(9, categoriesActions);
						insertPs.setInt(10, viewActionId);

						insertPs.execute();
					}
				}
			}
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

}