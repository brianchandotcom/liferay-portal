/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Alexander Chow
 * @author Connor McKay
 * @author Igor Beslic
 */
public class UpgradePermission extends UpgradeProcess {

	protected ResourceBlock convertResourcePermissions(
			long companyId, String name, long primKey)
		throws PortalException, SystemException {

		PermissionedModel permissionedModel =
			ResourceBlockLocalServiceUtil.getPermissionedModel(name, primKey);

		long groupId = 0;

		if (permissionedModel instanceof GroupedModel) {
			GroupedModel groupedModel = (GroupedModel)permissionedModel;

			groupId = groupedModel.getGroupId();
		}

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			getResourceBlockPermissionsContainer(
					companyId, groupId, name, primKey);

		String permissionsHash =
			ResourceBlockLocalServiceUtil.getPermissionsHash(
				resourceBlockPermissionsContainer);

		ResourceBlock resourceBlock =
			ResourceBlockLocalServiceUtil.updateResourceBlockId(
				companyId, groupId, name, permissionedModel, permissionsHash,
				resourceBlockPermissionsContainer);

		return resourceBlock;
	}

	protected void convertResourcePermissions(
			String name, String tableName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + ", companyId from " + tableName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long companyId = rs.getLong("companyId");

				ResourceBlock resourceBlock = convertResourcePermissions(
					companyId, name, primKey);

				if (_log.isInfoEnabled() &&
					((resourceBlock.getResourceBlockId() % 100) == 0)) {

					_log.info("Processed 100 resource blocks for " + name);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getScopeResourcePermissions(
				_SCOPES);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			int scope = resourcePermission.getScope();

			if (!name.equals(resourcePermission.getName())) {
				continue;
			}

			if ((scope == ResourceConstants.SCOPE_COMPANY) ||
				(scope == ResourceConstants.SCOPE_GROUP_TEMPLATE)) {

				ResourceBlockLocalServiceUtil.setCompanyScopePermissions(
					resourcePermission.getCompanyId(), name,
					resourcePermission.getRoleId(),
					resourcePermission.getActionIds());
			}
			else if (scope == ResourceConstants.SCOPE_GROUP) {
				ResourceBlockLocalServiceUtil.setGroupScopePermissions(
					resourcePermission.getCompanyId(),
					GetterUtil.getLong(resourcePermission.getPrimaryKey()),
					name, resourcePermission.getRoleId(),
					resourcePermission.getActionIds());
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {

		// LPS-14202 and LPS-17841

		RoleLocalServiceUtil.checkSystemRoles();

		updatePermissions("com.liferay.portlet.bookmarks", true, true);
		updatePermissions("com.liferay.portlet.documentlibrary", false, true);
		updatePermissions("com.liferay.portlet.imagegallery", true, true);
		updatePermissions("com.liferay.portlet.messageboards", true, true);
		updatePermissions("com.liferay.portlet.shopping", true, true);

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			convertResourcePermissions(
				BookmarksEntry.class.getName(), "BookmarksEntry", "entryId");
			convertResourcePermissions(
				BookmarksFolder.class.getName(), "BookmarksFolder", "folderId");
		}
	}

	protected ResourceBlockPermissionsContainer
			getResourceBlockPermissionsContainer(
				long companyId, long groupId, String name, long primKey)
		throws SystemException {

		ResourceBlockPermissionsContainer resourceBlockPermissionContainer =
			new ResourceBlockPermissionsContainer();

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getResourceResourcePermissions(
				companyId, groupId, name, String.valueOf(primKey));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourceBlockPermissionContainer.addPermission(
				resourcePermission.getRoleId(),
				resourcePermission.getActionIds());
		}

		return resourceBlockPermissionContainer;
	}

	protected void updatePermissions(
			String name, boolean community, boolean guest)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			updatePermissions_6(name, community, guest);
		}
		else {
			updatePermissions_1to5(name, community, guest);
		}
	}

	protected void updatePermissions_1to5(
			String name, boolean community, boolean guest)
		throws Exception {

		if (community) {
			setContainerResourcePermissions(
				name, RoleConstants.ORGANIZATION_USER, ActionKeys.VIEW);
			setContainerResourcePermissions(
				name, RoleConstants.SITE_MEMBER, ActionKeys.VIEW);
		}

		if (guest) {
			setContainerResourcePermissions(
				name, RoleConstants.GUEST, ActionKeys.VIEW);
		}

		setContainerResourcePermissions(
			name, RoleConstants.OWNER, ActionKeys.VIEW);
	}

	protected void updatePermissions_6(
			String name, boolean community, boolean guest)
		throws Exception {

		List<String> modelActions = ResourceActionsUtil.getModelResourceActions(
			name);

		ResourceActionLocalServiceUtil.checkResourceActions(name, modelActions);

		int scope = ResourceConstants.SCOPE_INDIVIDUAL;
		long actionIdsLong = 1;

		if (community) {
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.ORGANIZATION_USER, scope, actionIdsLong);
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.SITE_MEMBER, scope, actionIdsLong);
		}

		if (guest) {
			ResourcePermissionLocalServiceUtil.addResourcePermissions(
				name, RoleConstants.GUEST, scope, actionIdsLong);
		}

		ResourcePermissionLocalServiceUtil.addResourcePermissions(
			name, RoleConstants.OWNER, scope, actionIdsLong);
	}

	protected void setContainerResourcePermissions(String name,
			String roleName, String actionId)
		throws PortalException, SQLException, SystemException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsCompany = null;
		ResultSet rsGroups = null;
		ResultSet rsResource = null;
		ResultSet rsPermission = null;
		ResultSet rsRole = null;

		try {
			con = DataAccess.getConnection();

			ps = con
					.prepareStatement("select companyId from Company");
			rsCompany = ps.executeQuery();

			while (rsCompany.next()) {
				long companyId = rsCompany.getLong("companyId");

				ps = con
						.prepareStatement("select distinct codeId from ResourceCode "
											.concat("where companyId = ? ")
											.concat("and name = ? and scope = ?"));

				ps.setLong(1, companyId);
				ps.setString(2, name);
				ps.setInt(3, ResourceConstants.SCOPE_INDIVIDUAL);

				rs = ps.executeQuery();
				while (rs.next()) {
					long codeId = rs.getLong("codeId");

					ps = con
							.prepareStatement("select groupId from Group_ "
											.concat("where companyId = ?"));
					ps.setLong(1, companyId);

					rsGroups = ps.executeQuery();

					while (rsGroups.next()) {
						String primKey = Long.toString(rsGroups.getLong("groupId"));

						ps = con
								.prepareStatement("select resourceId from Resource "
											.concat("where codeId = ? and primKey = ?"));

						ps.setLong(1, codeId);
						ps.setString(2, primKey);

						rsResource = ps.executeQuery();
						while (rsResource.next()) {
							long resourceId = rsResource.getLong("resourceId");
							ps = con
									.prepareStatement("select permissionId from Permission "
											.concat("where actionId = ? and resourceId = ?"));
							ps.setString(1, actionId);
							ps.setLong(2, resourceId);

							rsPermission = ps.executeQuery();

							long permissionId = 0;

							if (rsPermission.next()) {
								permissionId = rsPermission.getLong("permissionId");
							} else {
								permissionId = CounterLocalServiceUtil
										.increment("com.liferay.portal.model.Permisson");

								ps = con
										.prepareStatement("insert into Permission_"
												.concat(" (permissionId, companyId, actionId,")
														.concat(" resourceId) values(?, ?, ?, ?)"));

								ps.setLong(1, permissionId);
								ps.setLong(2, companyId);
								ps.setString(3, actionId);
								ps.setLong(4, resourceId);

								if (ps.executeUpdate() < 1) {
									// TODO ask ray -

								}
							}

							if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5)
									|| roleName.equals(RoleConstants.ORGANIZATION_USER)
									|| roleName.equals(RoleConstants.OWNER)
									|| roleName.equals(RoleConstants.SITE_MEMBER)) {

								ps = con
										.prepareStatement("select roleId from Role_ "
												.concat("where companyId = ? and roleName = ?"));
								ps.setLong(1, companyId);
								ps.setString(2, roleName);

								rsRole = ps.executeQuery();

								if (rsRole.next()) {
									ps = con
											.prepareStatement("insert into Role_Permissions "
													.concat("(roleId, permissionId) values (?, ?)"));
									ps.setLong(1, rsRole.getLong("roleId"));
									ps.setLong(2, permissionId);

									if (ps.executeUpdate() < 1) {
										//TODO ask ray -
									}
								}
								//								permissionPersistence.addRole(
								//									permission.getPermissionId(), role);
							}
							else {
								long defaultUserId = UserServiceUtil
										.getDefaultUserId(companyId);
								ps = con
										.prepareStatement("insert into User_Permissions "
												.concat("(userId, permissionId) values (?, ?)"));

								ps.setLong(1, defaultUserId);
								ps.setLong(2, permissionId);

								if (ps.executeUpdate() < 1) {
									//TODO ask ray -
								}
							}
						}

					}

					if (_log.isInfoEnabled()) {
						_log.info("Processed 100 resource blocks for " + name);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final int[] _SCOPES = {
		ResourceConstants.SCOPE_COMPANY, ResourceConstants.SCOPE_GROUP,
		ResourceConstants.SCOPE_GROUP_TEMPLATE
	};

	private static Log _log = LogFactoryUtil.getLog(UpgradePermission.class);

}