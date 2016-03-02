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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias Kaefer
 * @author Douglas Wong
 * @author Matthew Kong
 * @author Raymond Augé
 */
public class VerifyPermission extends VerifyProcess {

	protected void checkPermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<String> modelNames = ResourceActionsUtil.getModelNames();

			for (String modelName : modelNames) {
				List<String> actionIds =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, actionIds, true);
			}

			List<String> portletNames = ResourceActionsUtil.getPortletNames();

			for (String portletName : portletNames) {
				List<String> actionIds =
					ResourceActionsUtil.getPortletResourceActions(portletName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					portletName, actionIds, true);
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				try {
					deleteDefaultPrivateLayoutPermissions_6(companyId);
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(e, e);
					}
				}
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_6(long companyId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				role.getRoleId());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (isPrivateLayout(
					resourcePermission.getName(),
					resourcePermission.getPrimKey())) {

				ResourcePermissionLocalServiceUtil.deleteResourcePermission(
					resourcePermission.getResourcePermissionId());
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteDefaultPrivateLayoutPermissions();

		checkPermissions();
		fixOrganizationRolePermissions();
		fixUserDefaultRolePermissions();
	}

	protected void fixOrganizationRolePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				ResourcePermission.class);

			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"name", Organization.class.getName()));

			List<ResourcePermission> resourcePermissions =
				ResourcePermissionLocalServiceUtil.dynamicQuery(dynamicQuery);

			for (ResourcePermission resourcePermission : resourcePermissions) {
				ResourcePermission groupResourcePermission = null;

				try {
					groupResourcePermission =
						ResourcePermissionLocalServiceUtil.
							getResourcePermission(
								resourcePermission.getCompanyId(),
								Group.class.getName(),
								resourcePermission.getScope(),
								resourcePermission.getPrimKey(),
								resourcePermission.getRoleId());
				}
				catch (Exception e) {
					ResourcePermissionLocalServiceUtil.setResourcePermissions(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId(),
						ResourcePermissionLocalServiceImpl.EMPTY_ACTION_IDS);

					groupResourcePermission =
						ResourcePermissionLocalServiceUtil.
							getResourcePermission(
								resourcePermission.getCompanyId(),
								Group.class.getName(),
								resourcePermission.getScope(),
								resourcePermission.getPrimKey(),
								resourcePermission.getRoleId());
				}

				for (String actionId : _DEPRECATED_ORGANIZATION_ACTION_IDS) {
					if (resourcePermission.hasActionId(actionId)) {
						resourcePermission.removeResourceAction(actionId);

						groupResourcePermission.addResourceAction(actionId);
					}
				}

				try {
					resourcePermission.resetOriginalValues();

					ResourcePermissionLocalServiceUtil.updateResourcePermission(
						resourcePermission);

					groupResourcePermission.resetOriginalValues();

					ResourcePermissionLocalServiceUtil.updateResourcePermission(
						groupResourcePermission);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			PermissionCacheUtil.clearResourceCache();
		}
	}

	protected void fixUserDefaultRolePermissions() throws Exception {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		if (dbType == DBType.MYSQL) {
			fixUserDefaultRolePermissionsMySQL();

			return;
		}

		if (dbType == DBType.ORACLE) {
			fixUserDefaultRolePermissionsOracle();

			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);
			long userGroupClassNameId = PortalUtil.getClassNameId(
				UserGroup.class);

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				Role powerUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);
				Role userRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				StringBundler sb = new StringBundler(20);

				sb.append("update ResourcePermission set roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where resourcePermissionId in (select ");
				sb.append("resourcePermissionId from ");
				sb.append("ResourcePermission inner join Layout on ");
				sb.append("ResourcePermission.primKey like ");
				sb.append("replace('[$PLID$]_LAYOUT_%', '[$PLID$]', ");
				sb.append("cast_text(Layout.plid)) inner join Group_ on ");
				sb.append("Layout.groupId = Group_.groupId");
				sb.append(" where ResourcePermission.scope = ");
				sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
				sb.append(" and ResourcePermission.roleId = ");
				sb.append(powerUserRole.getRoleId());
				sb.append(" and (Group_.classNameId = ");
				sb.append(userClassNameId);
				sb.append(" or Group_.classNameId = ");
				sb.append(userGroupClassNameId);
				sb.append(") and Layout.type_ = '");
				sb.append(LayoutConstants.TYPE_PORTLET);
				sb.append("')");

				runSQL(sb.toString());
			}

			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	protected void fixUserDefaultRolePermissionsMySQL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);
			long userGroupClassNameId = PortalUtil.getClassNameId(
				UserGroup.class);

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				Role powerUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);
				Role userRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				StringBundler sb = new StringBundler(19);

				sb.append("update ResourcePermission ");
				sb.append("inner join Layout on ");
				sb.append("ResourcePermission.primKey like ");
				sb.append("replace('[$PLID$]_LAYOUT_%', '[$PLID$]', ");
				sb.append("cast_text(Layout.plid)) inner join Group_ on ");
				sb.append("Layout.groupId = Group_.groupId");
				sb.append(" set ResourcePermission.roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where ResourcePermission.scope = ");
				sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
				sb.append(" and ResourcePermission.roleId = ");
				sb.append(powerUserRole.getRoleId());
				sb.append(" and (Group_.classNameId = ");
				sb.append(userClassNameId);
				sb.append(" or Group_.classNameId = ");
				sb.append(userGroupClassNameId);
				sb.append(") and Layout.type_ = '");
				sb.append(LayoutConstants.TYPE_PORTLET);
				sb.append(StringPool.APOSTROPHE);

				runSQL(sb.toString());
			}

			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	protected void fixUserDefaultRolePermissionsOracle() throws Exception {
		String insertSQL =
			"insert into ResourcePermissionPlid(" +
				"select ResourcePermission.resourcePermissionId, " +
					"SUBSTR(ResourcePermission.primKey, 0, " +
						"INSTR(ResourcePermission.primKey, '_LAYOUT_') -1) " +
							"as plid from ResourcePermission where " +
								"ResourcePermission.primKey like '%_LAYOUT_%')";

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try {
				runSQL(
					"create table ResourcePermissionPlid" +
						"(resourcePermissionId LONG null, plid LONG null)");
			}
			catch (SQLException sqle) {
				runSQL("delete from ResourcePermissionPlid");
			}

			runSQL(insertSQL);

			long userClassNameId = PortalUtil.getClassNameId(User.class);
			long userGroupClassNameId = PortalUtil.getClassNameId(
				UserGroup.class);

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				Role powerUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);
				Role userRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				StringBundler sb = new StringBundler(23);

				sb.append("update ResourcePermission set roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where resourcePermissionId in (select ");
				sb.append("ResourcePermission.resourcePermissionId from ");
				sb.append("ResourcePermission inner join ");
				sb.append("ResourcePermissionPlid on ");
				sb.append("ResourcePermission.resourcePermissionId = ");
				sb.append("ResourcePermissionPlid.resourcePermissionId ");
				sb.append("inner join Layout on ");
				sb.append("ResourcePermissionPlid.plid = Layout.plid ");
				sb.append("inner join Group_ on ");
				sb.append("Layout.groupId = Group_.groupId");
				sb.append(" where ResourcePermission.scope = ");
				sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
				sb.append(" and ResourcePermission.roleId = ");
				sb.append(powerUserRole.getRoleId());
				sb.append(" and (Group_.classNameId = ");
				sb.append(userClassNameId);
				sb.append(" or Group_.classNameId = ");
				sb.append(userGroupClassNameId);
				sb.append(") and Layout.type_ = '");
				sb.append(LayoutConstants.TYPE_PORTLET);
				sb.append("')");

				runSQL(sb.toString());
			}

			runSQL("drop table ResourcePermissionPlid");

			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	protected boolean isPrivateLayout(String name, String primKey)
		throws Exception {

		if (!name.equals(Layout.class.getName()) &&
			!primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

			return false;
		}

		if (primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {
			primKey = StringUtil.extractFirst(
				primKey, PortletConstants.LAYOUT_SEPARATOR);
		}

		long plid = GetterUtil.getLong(primKey);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPublicLayout() || layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	private static final List<String> _DEPRECATED_ORGANIZATION_ACTION_IDS =
		new ArrayList<>();

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyPermission.class);

	static {
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add(
			ActionKeys.MANAGE_ARCHIVED_SETUPS);
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add(ActionKeys.MANAGE_LAYOUTS);
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add(ActionKeys.MANAGE_STAGING);
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add(ActionKeys.MANAGE_TEAMS);
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add(ActionKeys.PUBLISH_STAGING);
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add("APPROVE_PROPOSAL");
		_DEPRECATED_ORGANIZATION_ACTION_IDS.add("ASSIGN_REVIEWER");
	}

}