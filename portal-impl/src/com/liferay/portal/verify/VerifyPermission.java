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
import com.liferay.petra.string.CharPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
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
import com.liferay.portal.util.PortalInstances;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	protected void deleteConflictingUserDefaultRolePermissions(
			long companyId, long powerUserRoleId, long userRoleId,
			long userClassNameId, long userGroupClassNameId)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(14);

			sb.append("select resourcePermission1.resourcePermissionId from ");
			sb.append("ResourcePermission resourcePermission1 inner join ");
			sb.append("ResourcePermission resourcePermission2 on ");
			sb.append("resourcePermission1.companyId = ");
			sb.append("resourcePermission2.companyId and ");
			sb.append("resourcePermission1.name = resourcePermission2.name ");
			sb.append("and resourcePermission1.scope = ");
			sb.append("resourcePermission2.scope and ");
			sb.append("resourcePermission1.primKey = ");
			sb.append("resourcePermission2.primKey inner join Layout on ");
			sb.append("resourcePermission1.companyId = Layout.companyId and ");
			sb.append("resourcePermission1.primKey like ");
			sb.append("replace('[$PLID$]_LAYOUT_%', '[$PLID$]', ");
			sb.append("cast_text(Layout.plid)) and Layout.type_ = '");
			sb.append(LayoutConstants.TYPE_PORTLET);
			sb.append(CharPool.APOSTROPHE);
			sb.append(" inner join Group_ on Layout.groupId = Group_.groupId ");
			sb.append("where resourcePermission1.companyId = ");
			sb.append(companyId);
			sb.append(" and resourcePermission1.roleId = ");
			sb.append(powerUserRoleId);
			sb.append(" and resourcePermission2.roleId = ");
			sb.append(userRoleId);
			sb.append(" and resourcePermission1.scope = ");
			sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
			sb.append(" and (Group_.classNameId = ");
			sb.append(userClassNameId);
			sb.append(" or Group_.classNameId = ");
			sb.append(userGroupClassNameId);
			sb.append(")");

			try (Statement ps1 = connection.createStatement();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from ResourcePermission where " +
							"resourcePermissionId = ?")) {

				String sql = SQLTransformer.transform(sb.toString());

				try (ResultSet rs = ps1.executeQuery(sql)) {
					while (rs.next()) {
						ps2.setLong(1, rs.getLong(1));

						ps2.addBatch();
					}
				}

				ps2.executeBatch();
			}
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void deleteDefaultPrivateLayoutPermissions_6(long companyId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		ActionableDynamicQuery actionableDynamicQuery =
			ResourcePermissionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName("roleId");

					dynamicQuery.add(property.eq(role.getRoleId()));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<ResourcePermission>() {

				@Override
				public void performAction(ResourcePermission resourcePermission)
					throws PortalException {

					if (isPrivateLayout(
							resourcePermission.getName(),
							resourcePermission.getPrimKey())) {

						ResourcePermissionLocalServiceUtil.
							deleteResourcePermission(
								resourcePermission.getResourcePermissionId());
					}
				}

			});

		actionableDynamicQuery.performActions();
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
						oldActionIds & ~_deprecatedOrganizationBitwiseValues;

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

		long powerUserRoleId = powerUserRole.getRoleId();

		Role userRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.USER);

		long userRoleId = userRole.getRoleId();

		String userPagePermissionsTableName = "TMP_VERIFY_1";
		String userPagePermissionsConflictsTableName = "TMP_VERIFY_2";

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
					" and roleId in (", String.valueOf(powerUserRoleId), ", ",
					String.valueOf(userRoleId), ")"));

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
					"insert into ",
					userPagePermissionsConflictsTableName,
					" select primKey from ", userPagePermissionsTableName,
					" group by primKey having COUNT(*) > 1"));

			runSQL(
				StringBundler.concat(
					"delete from ", userPagePermissionsTableName,
					" where roleId = ", String.valueOf(userRoleId)));

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
					String.valueOf(userRoleId), " where resourcePermissionId ",
					"in (select resourcePermissionId from ",
					userPagePermissionsTableName, " where conflict = FALSE)"));
		}
	}

	protected void fixUserDefaultRolePermissionsMySQL(
			long userClassNameId, long userGroupClassNameId, long[] companyIds)
		throws Exception {

		try {
			runSQL(
				"create index TEMP_INDEX on ResourcePermission (roleId, " +
					"scope, primKey[$COLUMN_LENGTH:255$])");
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle, sqle);
			}
		}

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			StringBundler sb = new StringBundler(18);

			sb.append("update ignore ResourcePermission inner join Layout on ");
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

		runSQL("drop index TEMP_INDEX on ResourcePermission");
	}

	protected void fixUserDefaultRolePermissionsOracle(
			long userClassNameId, long userGroupClassNameId, long[] companyIds)
		throws Exception {

		try {
			runSQL("alter table ResourcePermission drop column plid");
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle, sqle);
			}
		}

		runSQL("alter table ResourcePermission add plid NUMBER null");

		runSQL("create index tmp_res_plid on ResourcePermission(plid)");

		StringBundler sb = new StringBundler(6);

		sb.append("update ResourcePermission r1 set plid = (select ");
		sb.append("SUBSTR(ResourcePermission.primKey, 0, ");
		sb.append("INSTR(ResourcePermission.primKey, '_LAYOUT_') -1) from ");
		sb.append("ResourcePermission where r1.resourcePermissionId = ");
		sb.append("ResourcePermission.resourcePermissionId and ");
		sb.append("ResourcePermission.primKey like '%_LAYOUT_%')");

		runSQL(sb.toString());

		for (long companyId : companyIds) {
			Role powerUserRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.POWER_USER);
			Role userRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.USER);

			sb = new StringBundler(24);

			sb.append("update ResourcePermission r1 set roleId = ");
			sb.append(userRole.getRoleId());
			sb.append(" where exists (select ");
			sb.append("ResourcePermission.resourcePermissionId from ");
			sb.append("ResourcePermission inner join Layout on ");
			sb.append("ResourcePermission.plid = Layout.plid inner join ");
			sb.append("Group_ on Layout.groupId = Group_.groupId where ");
			sb.append("r1.resourcePermissionId = ResourcePermission.");
			sb.append("resourcePermissionId and ResourcePermission.scope = ");
			sb.append(ResourceConstants.SCOPE_INDIVIDUAL);
			sb.append(" and ResourcePermission.roleId = ");
			sb.append(powerUserRole.getRoleId());
			sb.append(" and (Group_.classNameId = ");
			sb.append(userClassNameId);
			sb.append(" or Group_.classNameId = ");
			sb.append(userGroupClassNameId);
			sb.append(") and Layout.type_ = '");
			sb.append(LayoutConstants.TYPE_PORTLET);
			sb.append("') and not exists (select resourcePermissionId from ");
			sb.append("ResourcePermission r2 where r1.name = r2.name and ");
			sb.append("r1.scope = r2.scope and r1.primKey = r2.primKey and ");
			sb.append("r2.roleId = ");
			sb.append(userRole.getRoleId());
			sb.append(")");

			runSQL(sb.toString());
		}

		runSQL("alter table ResourcePermission drop column plid");
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected boolean isPrivateLayout(String name, String primKey)
		throws PortalException {

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

	private static final long _deprecatedOrganizationBitwiseValues;
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

		_deprecatedOrganizationBitwiseValues =
			deprecatedOrganizationBitwiseValues;
	}

}