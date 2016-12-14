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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseReplacePortletId extends BaseUpgradePortletId {

	protected boolean hasPortlet(String portletId) throws SQLException {
		return hasRow(
			"select count(*) from Portlet where portletId = ?", portletId);
	}

	protected boolean hasResourceAction(String name) throws SQLException {
		return hasRow(
			"select count(*) from ResourceAction where name = ?", name);
	}

	protected boolean hasResourcePermission(String name) throws SQLException {
		return hasRow(
			"select count(*) from ResourcePermission where name = ?", name);
	}

	protected boolean hasRow(String sql, String value) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, value);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	protected void updatePortletId(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		if (!hasPortlet(newRootPortletId)) {
			super.updatePortletId(oldRootPortletId, newRootPortletId);
		}
	}

	@Override
	protected void updateResourceAction(String oldName, String newName)
		throws Exception {

		if (hasResourceAction(newName)) {
			StringBundler sb = new StringBundler(3);

			sb.append("select RA1.resourceActionId from ResourceAction RA1 ");
			sb.append("inner join ResourceAction RA2 on RA1.actionId = ");
			sb.append("RA2.actionId where RA1.name = ? and RA2.name = ?");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from ResourceAction where resourceActionId = " +
							"?")) {

				ps1.setString(1, oldName);
				ps1.setString(2, newName);

				ResultSet rs = ps1.executeQuery();

				int deleteCount = 0;

				while (rs.next()) {
					ps2.setLong(1, rs.getLong(1));

					ps2.addBatch();

					deleteCount++;
				}

				if (deleteCount > 0) {
					ps2.executeBatch();
				}
			}
		}

		super.updateResourceAction(oldName, newName);
	}

	@Override
	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		if (hasResourcePermission(newRootPortletId)) {
			try (PreparedStatement ps = connection.prepareStatement(
					"delete from ResourcePermission where name = ?")) {

				ps.setString(1, oldRootPortletId);

				ps.execute();
			}
		}
		else {
			super.updateResourcePermission(
				oldRootPortletId, newRootPortletId, updateName);
		}
	}

}