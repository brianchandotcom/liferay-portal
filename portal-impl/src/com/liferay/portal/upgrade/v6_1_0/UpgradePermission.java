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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PermissionedModel;
import com.liferay.portal.kernel.model.ResourceBlock;
import com.liferay.portal.kernel.model.ResourceBlockPermissionsContainer;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.RoleWrapper;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourceLocalServiceWrapper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceWrapper;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceWrapper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.aop.ServiceWrapperProxyUtil;

import java.io.Closeable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Alexander Chow
 * @author Connor McKay
 * @author Igor Beslic
 */
public class UpgradePermission extends UpgradeProcess {

	protected ResourceBlock convertResourcePermissions(
		String tableName, String pkColumnName, long companyId, long groupId,
		String name, long primKey) {

		PermissionedModel permissionedModel = new UpgradePermissionedModel(
			tableName, pkColumnName, primKey);

		ResourceBlockPermissionsContainer resourceBlockPermissionsContainer =
			getResourceBlockPermissionsContainer(
				companyId, groupId, name, primKey);

		String permissionsHash =
			resourceBlockPermissionsContainer.getPermissionsHash();

		ResourceBlock resourceBlock =
			ResourceBlockLocalServiceUtil.updateResourceBlockId(
				companyId, groupId, name, permissionedModel, permissionsHash,
				resourceBlockPermissionsContainer);

		return resourceBlock;
	}

	protected void convertResourcePermissions(
			String name, String tableName, String pkColumnName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(name)) {
			try (PreparedStatement ps = connection.prepareStatement(
					"select " + pkColumnName + ", groupId, companyId from " +
						tableName);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long primKey = rs.getLong(pkColumnName);
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");

					ResourceBlock resourceBlock = convertResourcePermissions(
						tableName, pkColumnName, companyId, groupId, name,
						primKey);

					if (_log.isInfoEnabled() &&
						((resourceBlock.getResourceBlockId() % 100) == 0)) {

						_log.info("Processed 100 resource blocks for " + name);
					}
				}
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
						GetterUtil.getLong(resourcePermission.getPrimKey()),
						name, resourcePermission.getRoleId(),
						resourcePermission.getActionIds());
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Closeable closeable1 = ServiceWrapperProxyUtil.createProxy(
				PortalBeanLocatorUtil.locate(
					ResourceLocalService.class.getName()),
				Pre7ResourceLocalServiceImpl.class);
			Closeable closeable2 = ServiceWrapperProxyUtil.createProxy(
				PortalBeanLocatorUtil.locate(
					ResourcePermissionLocalService.class.getName()),
				Pre7ResourcePermissionLocalServiceImpl.class);
			Closeable closeable3 = ServiceWrapperProxyUtil.createProxy(
				PortalBeanLocatorUtil.locate(RoleLocalService.class.getName()),
				Pre7RoleLocalServiceImpl.class)) {

			Class<? extends UpgradePermission> upgradePermissionClass =
				getClass();

			ResourceActionsUtil.read(
				null, upgradePermissionClass.getClassLoader(),
				"com/liferay/portal/upgrade/v6_1_0/dependencies" +
					"/resource-actions.xml");

			// LPS-46141

			List<String> modelActions =
				ResourceActionsUtil.getModelResourceActions(
					"com.liferay.portal.model.Role");

			ResourceActionLocalServiceUtil.checkResourceActions(
				"com.liferay.portal.model.Role", modelActions);

			// LPS-14202 and LPS-17841

			RoleLocalServiceUtil.checkSystemRoles();

			updatePermissions("com.liferay.portlet.bookmarks", true, true);
			updatePermissions(
				"com.liferay.portlet.documentlibrary", false, true);
			updatePermissions("com.liferay.portlet.imagegallery", true, true);
			updatePermissions("com.liferay.portlet.messageboards", true, true);
			updatePermissions("com.liferay.portlet.shopping", true, true);

			convertResourcePermissions(
				"com.liferay.portlet.bookmarks.model.BookmarksEntry",
				"BookmarksEntry", "entryId");
			convertResourcePermissions(
				"com.liferay.portlet.bookmarks.model.BookmarksFolder",
				"BookmarksFolder", "folderId");
		}
	}

	protected ResourceBlockPermissionsContainer
		getResourceBlockPermissionsContainer(
			long companyId, long groupId, String name, long primKey) {

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

		try (LoggingTimer loggingTimer = new LoggingTimer(name)) {
			List<String> modelActions =
				ResourceActionsUtil.getModelResourceActions(name);

			ResourceActionLocalServiceUtil.checkResourceActions(
				name, modelActions);

			int scope = ResourceConstants.SCOPE_INDIVIDUAL;
			long actionIdsLong = 1;

			if (community) {
				ResourcePermissionLocalServiceUtil.addResourcePermissions(
					name, RoleConstants.ORGANIZATION_USER, scope,
					actionIdsLong);
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
	}

	private static final int[] _SCOPES = {
		ResourceConstants.SCOPE_COMPANY, ResourceConstants.SCOPE_GROUP,
		ResourceConstants.SCOPE_GROUP_TEMPLATE
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePermission.class);

	private static class Pre7ResourceLocalServiceImpl
		extends ResourceLocalServiceWrapper {

		@Override
		public void addResources(
				long companyId, long groupId, long userId, String name,
				long primKey, boolean portletActions,
				boolean addGroupPermissions, boolean addGuestPermissions)
			throws PortalException {

			if (name.equals(Role.class.getName())) {
				name = "com.liferay.portal.model.Role";
			}

			super.addResources(
				companyId, groupId, userId, name, primKey, portletActions,
				addGroupPermissions, addGuestPermissions);
		}

		private Pre7ResourceLocalServiceImpl(
			ResourceLocalService resourceLocalService) {

			super(resourceLocalService);
		}

	}

	private static class Pre7ResourcePermissionLocalServiceImpl
		extends ResourcePermissionLocalServiceWrapper {

		@Override
		public void setResourcePermissions(
				long companyId, String name, int scope, String primKey,
				long roleId, String[] actionIds)
			throws PortalException {

			if (name.equals(Role.class.getName())) {
				name = "com.liferay.portal.model.Role";
			}

			super.setResourcePermissions(
				companyId, name, scope, primKey, roleId, actionIds);
		}

		private Pre7ResourcePermissionLocalServiceImpl(
			ResourcePermissionLocalService resourcePermissionLocalService) {

			super(resourcePermissionLocalService);
		}

	}

	private static class Pre7RoleLocalServiceImpl
		extends RoleLocalServiceWrapper {

		@Override
		public Role deleteRole(Role role) throws PortalException {
			String className = role.getClassName();

			if (className.equals(Role.class.getName())) {
				return new RoleWrapper(role) {

					@Override
					public String getClassName() {
						String className = super.getClassName();

						if (className.equals(Role.class.getName())) {
							className = "com.liferay.portal.model.Role";
						}

						return className;
					}

				};
			}

			return super.deleteRole(role);
		}

		private Pre7RoleLocalServiceImpl(RoleLocalService roleLocalService) {
			super(roleLocalService);
		}

	}

	private class UpgradePermissionedModel implements PermissionedModel {

		public UpgradePermissionedModel(
			String tableName, String pkColumnName, long primKey) {

			_tableName = tableName;
			_pkColumnName = pkColumnName;
			_primKey = primKey;
		}

		@Override
		public long getResourceBlockId() {
			return _resourceBlockId;
		}

		@Override
		public void persist() {
			try {
				StringBundler sb = new StringBundler(8);

				sb.append("update ");
				sb.append(_tableName);
				sb.append(" set resourceBlockId = ");
				sb.append(_resourceBlockId);
				sb.append(" where ");
				sb.append(_pkColumnName);
				sb.append(" = ");
				sb.append(_primKey);

				runSQL(sb.toString());
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		@Override
		public void setResourceBlockId(long resourceBlockId) {
			_resourceBlockId = resourceBlockId;
		}

		private final String _pkColumnName;
		private final long _primKey;
		private long _resourceBlockId;
		private final String _tableName;

	}

}