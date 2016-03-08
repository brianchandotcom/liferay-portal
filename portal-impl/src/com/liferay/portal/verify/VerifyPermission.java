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

				for (String actionId : _deprecatedOrganizationActionIds) {
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

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);
			long userGroupClassNameId = PortalUtil.getClassNameId(
				UserGroup.class);

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			if (dbType == DBType.MYSQL) {
				fixUserDefaultRolePermissionsMySQL(
					userClassNameId, userGroupClassNameId, companyIds);

				return;
			}

			if (dbType == DBType.ORACLE) {
				fixUserDefaultRolePermissionsOracle(
					userClassNameId, userGroupClassNameId, companyIds);

				return;
			}

			for (long companyId : companyIds) {
				Role powerUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);
				Role userRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				StringBundler sb = new StringBundler(19);

				sb.append("update ResourcePermission set roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where resourcePermissionId in (select ");
				sb.append("resourcePermissionId from ResourcePermission ");
				sb.append("inner join Layout on ResourcePermission.primKey ");
				sb.append("like replace('[$PLID$]_LAYOUT_%', '[$PLID$]', ");
				sb.append("cast_text(Layout.plid)) inner join Group_ on ");
				sb.append("Layout.groupId = Group_.groupId where ");
				sb.append("ResourcePermission.scope = ");
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
		}
		finally {
			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	protected void fixUserDefaultRolePermissionsMySQL(
			long userClassNameId, long userGroupClassNameId, long[] companyIds)
		throws Exception {

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			StringBundler sb = new StringBundler(18);

			sb.append("update ResourcePermission inner join Layout on ");
			sb.append("ResourcePermission.primKey like ");
			sb.append("replace('[$PLID$]_LAYOUT_%', '[$PLID$]', ");
			sb.append("cast_text(Layout.plid)) inner join Group_ on ");
			sb.append("Layout.groupId = Group_.groupId set ");
			sb.append("ResourcePermission.roleId = ");
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
	}

	protected void fixUserDefaultRolePermissionsOracle(
			long userClassNameId, long userGroupClassNameId, long[] companyIds)
		throws Exception {

		try {
			runSQL(
				"create table ResourcePermissionPlid (resourcePermissionId " +
					"LONG null, plid LONG null)");
		}
		catch (SQLException sqle) {
			runSQL("delete from ResourcePermissionPlid");
		}

		StringBundler sb = new StringBundler(6);

		sb.append("insert into ResourcePermissionPlid(select ");
		sb.append("ResourcePermission.resourcePermissionId, ");
		sb.append("SUBSTR(ResourcePermission.primKey, 0, ");
		sb.append("INSTR(ResourcePermission.primKey, '_LAYOUT_') -1) as plid ");
		sb.append("from ResourcePermission where ResourcePermission.primKey ");
		sb.append("like '%_LAYOUT_%')");

		runSQL(sb.toString());

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			sb = new StringBundler(20);

			sb.append("update ResourcePermission set roleId = ");
			sb.append(userRole.getRoleId());
			sb.append(" where resourcePermissionId in (select ");
			sb.append("ResourcePermission.resourcePermissionId from ");
			sb.append("ResourcePermission inner join ResourcePermissionPlid ");
			sb.append("on ResourcePermission.resourcePermissionId = ");
			sb.append("ResourcePermissionPlid.resourcePermissionId inner ");
			sb.append("join Layout on ResourcePermissionPlid.plid = ");
			sb.append("Layout.plid inner join Group_ on Layout.groupId = ");
			sb.append("Group_.groupId where ResourcePermission.scope = ");
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

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyPermission.class);

	private static final List<String> _deprecatedOrganizationActionIds =
		new ArrayList<>();

	static {
		_deprecatedOrganizationActionIds.add(ActionKeys.MANAGE_ARCHIVED_SETUPS);
		_deprecatedOrganizationActionIds.add(ActionKeys.MANAGE_LAYOUTS);
		_deprecatedOrganizationActionIds.add(ActionKeys.MANAGE_STAGING);
		_deprecatedOrganizationActionIds.add(ActionKeys.MANAGE_TEAMS);
		_deprecatedOrganizationActionIds.add(ActionKeys.PUBLISH_STAGING);
		_deprecatedOrganizationActionIds.add("APPROVE_PROPOSAL");
		_deprecatedOrganizationActionIds.add("ASSIGN_REVIEWER");
	}

}