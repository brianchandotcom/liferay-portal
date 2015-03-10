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
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

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

	protected void deleteDefaultPrivateLayoutPermissions() throws Exception {
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
		fixDockbarPermissions();
		fixOrganizationRolePermissions();
		fixUserDefaultRolePermissions();
	}

	protected void fixDockbarPermissions() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			try {
				Role role = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				ResourcePermissionLocalServiceUtil.addResourcePermission(
					companyId, PortletKeys.DOCKBAR,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(role.getCompanyId()), role.getRoleId(),
					ActionKeys.VIEW);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	protected void fixOrganizationRolePermissions() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ResourcePermission.class);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", Organization.class.getName()));

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermission groupResourcePermission = null;

			try {
				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId());
			}
			catch (Exception e) {
				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					resourcePermission.getCompanyId(), Group.class.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					ResourcePermissionLocalServiceImpl.EMPTY_ACTION_IDS);

				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
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

		PermissionCacheUtil.clearCache();
	}

	protected void fixUserDefaultRolePermissions() throws Exception {
		long userClassNameId = PortalUtil.getClassNameId(User.class);
		long userGroupClassNameId = PortalUtil.getClassNameId(UserGroup.class);

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			StringBundler join = new StringBundler(17);

			join.append("ResourcePermission rp1 ");
			join.append("left outer join ResourcePermission rp2 on ");
			join.append("rp1.companyId = rp2.companyId and ");
			join.append("rp1.name = rp2.name and ");
			join.append("rp1.primKey = rp2.primKey and ");
			join.append("rp1.scope = rp2.scope and rp2.roleId = ");
			join.append(userRole.getRoleId());
			join.append(" inner join Layout l on ");
			join.append("rp1.companyId = l.companyId and ");
			join.append("rp1.primKey like replace('[$PLID$]");
			join.append(PortletConstants.LAYOUT_SEPARATOR);
			join.append("%', '[$PLID$]', cast_text(l.plid)) ");
			join.append("inner join Group_ g on ");
			join.append("l.groupId = g.groupId and ");
			join.append("l.type_ = '");
			join.append(LayoutConstants.TYPE_PORTLET);
			join.append(StringPool.APOSTROPHE);

			StringBundler where = new StringBundler(12);

			where.append("where rp1.scope = ");
			where.append(ResourceConstants.SCOPE_INDIVIDUAL);
			where.append(" and rp1.primKey like '%");
			where.append(PortletConstants.LAYOUT_SEPARATOR);
			where.append("%' and rp1.roleId = ");
			where.append(powerUserRole.getRoleId());
			where.append(" and rp2.roleId is null and ");
			where.append("(g.classNameId = ");
			where.append(userClassNameId);
			where.append(" or g.classNameId = ");
			where.append(userGroupClassNameId);
			where.append(StringPool.CLOSE_PARENTHESIS);

			StringBundler sb = new StringBundler(8);

			if (dbType.equals(DB.TYPE_MYSQL)) {
				sb.append("update ");
				sb.append(join.toString());
				sb.append(" set rp1.roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(StringPool.SPACE);
				sb.append(where.toString());
			}
			else {
				sb.append("update ResourcePermission set roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where resourcePermissionId in (");
				sb.append("select rp1.resourcePermissionId from ");
				sb.append(join.toString());
				sb.append(StringPool.SPACE);
				sb.append(where.toString());
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			runSQL(sb.toString());
		}

		EntityCacheUtil.clearCache();
		FinderCacheUtil.clearCache();
	}

	protected boolean isPrivateLayout(String name, String primKey)
		throws Exception {

		if (!name.equals(Layout.class.getName())) {
			return false;
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