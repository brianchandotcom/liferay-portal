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
import com.liferay.portal.kernel.dao.db.DBType;
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
import com.liferay.portal.kernel.util.StringUtil;
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
		fixOrganizationRolePermissions();
		fixUserDefaultRolePermissions();
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

		PermissionCacheUtil.clearResourceCache();
	}

	protected void fixUserDefaultRolePermissions() throws Exception {
		long userClassNameId = PortalUtil.getClassNameId(User.class);
		long userGroupClassNameId = PortalUtil.getClassNameId(UserGroup.class);

		DB db = DBFactoryUtil.getDB();

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			StringBundler joinSB = new StringBundler(22);

			joinSB.append("ResourcePermission resourcePermission1 left outer ");
			joinSB.append("join ResourcePermission resourcePermission2 on ");
			joinSB.append("resourcePermission1.companyId = ");
			joinSB.append("resourcePermission2.companyId and ");
			joinSB.append("resourcePermission1.name = ");
			joinSB.append("resourcePermission2.name and ");
			joinSB.append("resourcePermission1.primKey = ");
			joinSB.append("resourcePermission2.primKey and ");
			joinSB.append("resourcePermission1.scope = ");
			joinSB.append("resourcePermission2.scope and ");
			joinSB.append("resourcePermission2.roleId = ");
			joinSB.append(userRole.getRoleId());
			joinSB.append(" inner join Layout on ");
			joinSB.append("resourcePermission1.companyId = Layout.companyId ");
			joinSB.append("and resourcePermission1.primKey like ");
			joinSB.append("replace('[$PLID$]");
			joinSB.append(PortletConstants.LAYOUT_SEPARATOR);
			joinSB.append("%', '[$PLID$]', cast_text(Layout.plid)) inner ");
			joinSB.append("join Group_ on Layout.groupId = ");
			joinSB.append("Group_.groupId and Layout.type_ = '");
			joinSB.append(LayoutConstants.TYPE_PORTLET);
			joinSB.append(StringPool.APOSTROPHE);

			StringBundler whereSB = new StringBundler(12);

			whereSB.append("where resourcePermission1.scope = ");
			whereSB.append(ResourceConstants.SCOPE_INDIVIDUAL);
			whereSB.append(" and resourcePermission1.primKey like '%");
			whereSB.append(PortletConstants.LAYOUT_SEPARATOR);
			whereSB.append("%' and resourcePermission1.roleId = ");
			whereSB.append(powerUserRole.getRoleId());
			whereSB.append(" and resourcePermission2.roleId is null and ");
			whereSB.append("(Group_.classNameId = ");
			whereSB.append(userClassNameId);
			whereSB.append(" or Group_.classNameId = ");
			whereSB.append(userGroupClassNameId);
			whereSB.append(StringPool.CLOSE_PARENTHESIS);

			StringBundler sb = new StringBundler(8);

			if (db.getDBType() == DBType.MYSQL) {
				sb.append("update ");
				sb.append(joinSB.toString());
				sb.append(" set resourcePermission1.roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(StringPool.SPACE);
				sb.append(whereSB.toString());
			}
			else {
				sb.append("update ResourcePermission set roleId = ");
				sb.append(userRole.getRoleId());
				sb.append(" where resourcePermissionId in (select ");
				sb.append("resourcePermission1.resourcePermissionId from ");
				sb.append(joinSB.toString());
				sb.append(StringPool.SPACE);
				sb.append(whereSB.toString());
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}

			runSQL(sb.toString());
		}

		EntityCacheUtil.clearCache();
		FinderCacheUtil.clearCache();
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