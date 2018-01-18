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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	protected void doVerify() throws Exception {
		checkPermissions();
		fixOrganizationRolePermissions();
		fixUserDefaultRolePermissions();
	}

	protected void fixOrganizationRolePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				ResourcePermissionLocalServiceUtil.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"name", Organization.class.getName()));
				});
			actionableDynamicQuery.setPerformActionMethod(
				(ResourcePermission resourcePermission) -> {
					long oldActionIds = resourcePermission.getActionIds();

					long newActionIds =
						oldActionIds & ~_DEPRECATED_ORGANIZATION_BITWISE_VALUES;

					if (newActionIds == oldActionIds) {
						return;
					}

					resourcePermission.setActionIds(newActionIds);

					ResourcePermissionLocalServiceUtil.updateResourcePermission(
						resourcePermission);

					long newGroupActionIds = 0;

					for (Map.Entry<Long, Long> entry :
							_organizationToGroupBitwiseValues.entrySet()) {

						long organizationBitwiseValue = entry.getKey();
						long groupBitwiseValue = entry.getValue();

						if ((oldActionIds & organizationBitwiseValue) != 0) {
							newGroupActionIds |= groupBitwiseValue;
						}
					}

					ResourcePermission groupResourcePermission =
						ResourcePermissionLocalServiceUtil.
							fetchResourcePermission(
								resourcePermission.getCompanyId(),
								Group.class.getName(),
								resourcePermission.getScope(),
								resourcePermission.getPrimKey(),
								resourcePermission.getRoleId());

					if (groupResourcePermission == null) {
						long resourcePermissionId =
							CounterLocalServiceUtil.increment(
								ResourcePermission.class.getName());

						groupResourcePermission =
							ResourcePermissionLocalServiceUtil.
								createResourcePermission(resourcePermissionId);

						groupResourcePermission.setCompanyId(
							resourcePermission.getCompanyId());
						groupResourcePermission.setName(Group.class.getName());
						groupResourcePermission.setScope(
							resourcePermission.getScope());
						groupResourcePermission.setPrimKey(
							resourcePermission.getPrimKey());
						groupResourcePermission.setPrimKeyId(
							GetterUtil.getLong(
								resourcePermission.getPrimKey()));
						groupResourcePermission.setRoleId(
							resourcePermission.getRoleId());
						groupResourcePermission.setOwnerId(0);
						groupResourcePermission.setViewActionId(
							(newGroupActionIds % 2) == 1);
					}

					groupResourcePermission.setActionIds(
						groupResourcePermission.getActionIds() |
						newGroupActionIds);

					ResourcePermissionLocalServiceUtil.updateResourcePermission(
						groupResourcePermission);
				});

			actionableDynamicQuery.performActions();
		}
	}

	protected void fixUserDefaultRolePermissions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long userClassNameId = PortalUtil.getClassNameId(User.class);
			long userGroupClassNameId = PortalUtil.getClassNameId(
				UserGroup.class);

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				fixUserDefaultRolePermissions(
					userClassNameId, userGroupClassNameId, companyId);
			}
		}
		finally {
			EntityCacheUtil.clearCache();
			FinderCacheUtil.clearCache();
		}
	}

	protected void fixUserDefaultRolePermissions(
			long userClassNameId, long userGroupClassNameId, long companyId)
		throws Exception {

		Role powerUserRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.POWER_USER);
		Role userRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.USER);

		String userPagePermissionsTableName = "TEMP_VERIFY_1";
		String userPagePermissionsConflictsTableName = "TEMP_VERIFY_2";

		try (AutoCloseable dropUserPagePermissionsTable = () -> runSQL(
				"drop table " + userPagePermissionsTableName);
			AutoCloseable dropUserPagePermissionsConflictsTable = () -> runSQL(
				"drop table " + userPagePermissionsConflictsTableName)) {

			runSQL(
				StringBundler.concat(
					"create table ", userPagePermissionsTableName,
					" (resourcePermissionId LONG not null primary key, ",
					"primKey VARCHAR(255), plidLength INTEGER, plidString ",
					"VARCHAR(255), plid LONG, roleId LONG, conflict BOOLEAN)"));

			runSQL(
				StringBundler.concat(
					"create index IX_VERIFY_1 on ",
					userPagePermissionsTableName, " (plid)"));

			runSQL(
				StringBundler.concat(
					"create index IX_VERIFY_2 on ",
					userPagePermissionsTableName,
					" (primKey[$COLUMN_LENGTH:255$])"));

			runSQL(
				StringBundler.concat(
					"create table ", userPagePermissionsConflictsTableName,
					" (primKey VARCHAR(255) not null)"));

			runSQL(
				StringBundler.concat(
					"create index IX_VERIFY_3 on ",
					userPagePermissionsConflictsTableName,
					" (primKey[$COLUMN_LENGTH:255$])"));

			if (_log.isInfoEnabled()) {
				_log.info("Populating temporary table of portlet permissions");
			}

			runSQL(
				StringBundler.concat(
					"insert into ", userPagePermissionsTableName,
					" select resourcePermissionId, primKey, 0 as plidLength, ",
					"NULL as plidString, 0 as plid, roleId, FALSE as conflict ",
					"from ResourcePermission where companyId = ",
					String.valueOf(companyId),
					" and primKey LIKE '%_LAYOUT_%' and scope = ",
					String.valueOf(ResourceConstants.SCOPE_INDIVIDUAL),
					" and roleId in (",
					String.valueOf(powerUserRole.getRoleId()), ", ",
					String.valueOf(userRole.getRoleId()), ")"));

			if (_log.isInfoEnabled()) {
				_log.info("Deriving plid for portlet permissions");
			}

			runSQL(
				StringBundler.concat(
					"update ", userPagePermissionsTableName,
					" set plidLength = INSTR(primKey, '",
					PortletConstants.LAYOUT_SEPARATOR, "') - 1"));

			runSQL(
				StringBundler.concat(
					"update ", userPagePermissionsTableName,
					" set plidString = SUBSTR(primKey, 1, plidLength)"));

			runSQL(
				StringBundler.concat(
					"update ", userPagePermissionsTableName,
					" set plid = CAST_LONG(plidString)"));

			if (_log.isInfoEnabled()) {
				_log.info("Identifying portlets on user personal pages");
			}

			runSQL(
				StringBundler.concat(
					"delete from ", userPagePermissionsTableName,
					" where not exists (select 1 from Layout inner join ",
					"Group_ on Layout.groupId = Group_.groupId where ",
					userPagePermissionsTableName,
					".plid = Layout.plid and Group_.classNameId in (",
					String.valueOf(userClassNameId), ", ",
					String.valueOf(userGroupClassNameId),
					") and Layout.type_ = '", LayoutConstants.TYPE_PORTLET,
					"')"));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Identifying portlets that have both user and power ",
						"user permissions"));
			}

			runSQL(
				StringBundler.concat(
					"insert into ", userPagePermissionsConflictsTableName,
					" select primKey from ", userPagePermissionsTableName,
					" group by primKey having COUNT(*) > 1"));

			runSQL(
				StringBundler.concat(
					"delete from ", userPagePermissionsTableName,
					" where roleId = ", String.valueOf(userRole.getRoleId())));

			runSQL(
				StringBundler.concat(
					"update ", userPagePermissionsTableName,
					" set conflict = TRUE where exists (select 1 from ",
					userPagePermissionsConflictsTableName, " where ",
					userPagePermissionsTableName, ".primKey = ",
					userPagePermissionsConflictsTableName, ".primKey)"));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Deleting power user permissions where the portlet ",
						"has both user and power user permissions"));
			}

			runSQL(
				StringBundler.concat(
					"delete from ResourcePermission where ",
					"resourcePermissionId in (select resourcePermissionId ",
					"from ", userPagePermissionsTableName,
					" where conflict = TRUE)"));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Switching remaining portlet power user permissions ",
						"to user permissions"));
			}

			runSQL(
				StringBundler.concat(
					"update ResourcePermission set roleId = ",
					String.valueOf(userRole.getRoleId()),
					" where resourcePermissionId in (select ",
					"resourcePermissionId from ", userPagePermissionsTableName,
					" where conflict = FALSE)"));
		}
	}

	private static final long _DEPRECATED_ORGANIZATION_BITWISE_VALUES;

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyPermission.class);

	private static final Map<Long, Long> _organizationToGroupBitwiseValues =
		new HashMap<>();

	static {
		String[] deprecatedOrganizationActionIds = {
			ActionKeys.MANAGE_ARCHIVED_SETUPS, ActionKeys.MANAGE_LAYOUTS,
			ActionKeys.MANAGE_STAGING, ActionKeys.MANAGE_TEAMS,
			ActionKeys.PUBLISH_STAGING, "APPROVE_PROPOSAL", "ASSIGN_REVIEWER"
		};

		long deprecatedOrganizationBitwiseValues = 0;

		for (String actionId : deprecatedOrganizationActionIds) {
			ResourceAction organizationResourceAction =
				ResourceActionLocalServiceUtil.fetchResourceAction(
					Organization.class.getName(), actionId);

			if (organizationResourceAction != null) {
				deprecatedOrganizationBitwiseValues |=
					organizationResourceAction.getBitwiseValue();

				ResourceAction groupResourceAction =
					ResourceActionLocalServiceUtil.fetchResourceAction(
						Group.class.getName(), actionId);

				if (groupResourceAction != null) {
					_organizationToGroupBitwiseValues.put(
						organizationResourceAction.getBitwiseValue(),
						groupResourceAction.getBitwiseValue());
				}
			}
		}

		_DEPRECATED_ORGANIZATION_BITWISE_VALUES =
			deprecatedOrganizationBitwiseValues;
	}

}