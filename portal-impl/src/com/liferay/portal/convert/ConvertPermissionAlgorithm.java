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

package com.liferay.portal.convert;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.convert.util.PermissionView;
import com.liferay.portal.convert.util.ResourcePermissionView;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MultiValueMap;
import com.liferay.portal.kernel.util.MultiValueMapFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseConstants;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.PermissionModelImpl;
import com.liferay.portal.model.impl.ResourceCodeModelImpl;
import com.liferay.portal.model.impl.ResourceModelImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class converts all existing permissions from the legacy permissions
 * algorithm to the latest algorithm.
 * </p>
 *
 * @author Alexander Chow
 */
public class ConvertPermissionAlgorithm extends ConvertProcess {

	@Override
	public String getDescription() {
		return "convert-legacy-permission-algorithm";
	}

	@Override
	public String[] getParameterNames() {
		return new String[] {"generate-custom-roles=checkbox"};
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = false;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 6) {
			enabled = true;
		}

		return enabled;
	}

	protected String convertGuestUsers(String legacyFile) throws Exception {
		UnsyncBufferedReader legacyFileReader = new UnsyncBufferedReader(
			new FileReader(legacyFile));

		Writer legacyFileUpdatedWriter = new UnsyncBufferedWriter(
			new FileWriter(legacyFile + _UPDATED));
		Writer legacyFileExtRolesPermissionsWriter = new UnsyncBufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLES_PERMIMISSIONS));

		try {
			String line = null;

			while (Validator.isNotNull(line = legacyFileReader.readLine())) {
				String[] values = StringUtil.split(line);

				long companyId = PermissionView.getCompanyId(values);
				long permissionId = PermissionView.getPermissionId(values);
				int scope = PermissionView.getScopeId(values);
				long userId = PermissionView.getPrimaryKey(values);

				if ((scope == ResourceConstants.SCOPE_INDIVIDUAL) &&
					_guestUsersSet.contains(userId)) {

					long roleId = _guestRolesMap.get(companyId).getRoleId();

					String key = roleId + "_" + permissionId;

					if (_rolesPermissions.contains(key)) {
						continue;
					}
					else {
						_rolesPermissions.add(key);
					}

					legacyFileExtRolesPermissionsWriter.write(
						roleId + "," + permissionId + "\n");
				}
				else {
					legacyFileUpdatedWriter.write(line + "\n");
				}
			}
		}
		finally {
			legacyFileReader.close();

			legacyFileUpdatedWriter.close();
			legacyFileExtRolesPermissionsWriter.close();
		}

		Table table = new Table(
			"Roles_Permissions",
			new Object[][] {
				{"roleId", Types.BIGINT}, {"permissionId", Types.BIGINT}
			});

		table.populateTable(legacyFile + _EXT_ROLES_PERMIMISSIONS);

		FileUtil.delete(legacyFile);
		FileUtil.delete(legacyFile + _EXT_ROLES_PERMIMISSIONS);

		return legacyFile + _UPDATED;
	}

	protected void convertPermissions(
			int type, String legacyName, String[] primKeys, String newName,
			Object[][] newColumns)
		throws Exception {

		MaintenanceUtil.appendStatus("Processing " + legacyName);

		Table legacyTable = new PermissionView(legacyName, primKeys);

		String legacyFile = legacyTable.generateTempFile();

		if (legacyFile == null) {
			return;
		}

		if (type == RoleConstants.TYPE_REGULAR) {
			legacyFile = convertGuestUsers(legacyFile);

			MaintenanceUtil.appendStatus(
				"Converted guest users to guest roles");
		}

		convertRoles(legacyFile, type, newName, newColumns);

		MaintenanceUtil.appendStatus("Converted roles for " + legacyName);

		DB db = DBFactoryUtil.getDB();

		db.runSQL(legacyTable.getDeleteSQL());

		FileUtil.delete(legacyFile);
	}

	protected void convertResourcePermission(Writer writer, String name)
		throws Exception {

		ResourcePermissionView resourcePermissionView =
			new ResourcePermissionView(name);

		UnsyncBufferedReader resourcePermissionReader = null;

		String resourcePermissionFile =
			resourcePermissionView.generateTempFile();

		if (resourcePermissionFile == null) {
			return;
		}

		MultiValueMap<Tuple, String> mvp =
			(MultiValueMap<Tuple, String>)
				MultiValueMapFactoryUtil.getMultiValueMap(
					_CONVERT_RESOURCE_PERMISSION);

		try {
			resourcePermissionReader = new UnsyncBufferedReader(
				new FileReader(resourcePermissionFile));

			String line = null;

			while (Validator.isNotNull(
					line = resourcePermissionReader.readLine())) {

				String[] values = StringUtil.split(line);

				String actionId = ResourcePermissionView.getActionId(values);
				long companyId = ResourcePermissionView.getCompanyId(values);
				int scope = ResourcePermissionView.getScope(values);
				String primKey = ResourcePermissionView.getPrimaryKey(values);
				long roleId = ResourcePermissionView.getRoleId(values);

				mvp.put(new Tuple(companyId, scope, primKey, roleId), actionId);
			}
		}
		finally {
			if (resourcePermissionReader != null) {
				resourcePermissionReader.close();
			}

			FileUtil.delete(resourcePermissionFile);
		}

		for (Tuple key : mvp.keySet()) {
			long resourcePermissionId = CounterLocalServiceUtil.increment(
				ResourcePermission.class.getName());

			long companyId = (Long)key.getObject(0);
			int scope = (Integer)key.getObject(1);
			String primKey = (String)key.getObject(2);
			long roleId = (Long)key.getObject(3);

			long actionIds = 0;

			for (String actionId : mvp.getAll(key)) {
				try {
					ResourceAction resourceAction =
						ResourceActionLocalServiceUtil.getResourceAction(
							name, actionId);

					actionIds |= resourceAction.getBitwiseValue();
				}
				catch (NoSuchResourceActionException nsrae) {
					if (_log.isWarnEnabled()) {
						String msg = nsrae.getMessage();

						_log.warn("Could not find resource action " + msg);
					}
				}
			}

			writer.append(resourcePermissionId + StringPool.COMMA);
			writer.append(companyId + StringPool.COMMA);
			writer.append(name + StringPool.COMMA);
			writer.append(scope + StringPool.COMMA);
			writer.append(primKey + StringPool.COMMA);
			writer.append(roleId + StringPool.COMMA);
			writer.append(0 + StringPool.COMMA);
			writer.append(actionIds + StringPool.COMMA + StringPool.NEW_LINE);
		}
	}

	protected void convertRoles(
			String legacyFile, int type, String newName, Object[][] newColumns)
		throws Exception {

		UnsyncBufferedReader legacyFileReader = new UnsyncBufferedReader(
			new FileReader(legacyFile));

		Writer legacyFileExtRoleWriter = new UnsyncBufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLE));
		Writer legacyFileExtRolesPermissionsWriter = new UnsyncBufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLES_PERMIMISSIONS));
		Writer legacyFileExtOtherRolesWriter = new UnsyncBufferedWriter(
			new FileWriter(legacyFile + _EXT_OTHER_ROLES));

		try {

			// Group by resource id

			MultiValueMap<Long, String[]> mvp =
				(MultiValueMap<Long, String[]>)
					MultiValueMapFactoryUtil.getMultiValueMap(_CONVERT_ROLES);

			String line = null;

			while (Validator.isNotNull(line = legacyFileReader.readLine())) {
				String[] values = StringUtil.split(line);

				long resourceId = PermissionView.getResourceId(values);

				mvp.put(resourceId, values);
			}

			// Assign role for each grouping

			for (Long key : mvp.keySet()) {
				List<String[]> valuesList = new ArrayList<String[]>(
					mvp.getAll(key));

				String[] values = valuesList.get(0);

				long companyId = PermissionView.getCompanyId(values);
				long groupId = PermissionView.getPrimaryKey(values);
				String name = PermissionView.getNameId(values);
				int scope = PermissionView.getScopeId(values);

				// Group action ids and permission ids

				List<String> actionsIds = new ArrayList<String>();
				List<Long> permissionIds = new ArrayList<Long>();

				for (String[] curValues : valuesList) {
					String actionId = PermissionView.getActionId(curValues);
					long permissionId = PermissionView.getPermissionId(
						curValues);

					actionsIds.add(actionId);
					permissionIds.add(permissionId);
				}

				// Look for owner and system roles

				if ((type != RoleConstants.TYPE_ORGANIZATION) &&
					(scope == ResourceConstants.SCOPE_INDIVIDUAL)) {

					// Find default actions

					List<String> defaultActions = null;

					if (type == RoleConstants.TYPE_REGULAR) {
						defaultActions = ResourceActionsUtil.getResourceActions(
							name);
					}
					else {
						defaultActions =
							ResourceActionsUtil.getResourceGroupDefaultActions(
								name);
					}

					// Resolve owner and system roles

					Role defaultRole = null;

					if (type == RoleConstants.TYPE_REGULAR) {
						if (defaultActions instanceof UnmodifiableList) {
							defaultActions = new ArrayList<String>(
								defaultActions);
						}

						Collections.sort(actionsIds);
						Collections.sort(defaultActions);

						if (defaultActions.equals(actionsIds)) {
							defaultRole = _ownerRolesMap.get(companyId);
						}
					}
					else {
						if (defaultActions.containsAll(actionsIds)) {
							Role[] defaultRoles = _defaultRolesMap.get(
								companyId);

							Group group = _groupsMap.get(groupId);

							if (group == null) {
								continue;
							}

							if (group.isOrganization()) {
								defaultRole = defaultRoles[0];
							}
							else if (group.isRegularSite()) {
								defaultRole = defaultRoles[2];
							}
							else if (group.isUser() || group.isUserGroup()) {
								defaultRole = defaultRoles[1];
							}
						}
					}

					if (defaultRole != null) {
						long roleId = defaultRole.getRoleId();

						for (Long permissionId : permissionIds) {
							String curKey = roleId + "_" + permissionId;

							if (_rolesPermissions.contains(curKey)) {
								continue;
							}
							else {
								_rolesPermissions.add(curKey);
							}

							legacyFileExtRolesPermissionsWriter.write(
								roleId + "," + permissionId + ",\n");
						}

						continue;
					}
				}

				if (isGenerateCustomRoles()) {

					// Role_

					long roleId = CounterLocalServiceUtil.increment();

					String roleName = StringUtil.upperCaseFirstLetter(
						RoleConstants.getTypeLabel(type));

					roleName += " " + StringUtil.toHexString(roleId);

					String[] roleColumns = new String[] {
						String.valueOf(roleId), String.valueOf(companyId),
						String.valueOf(
							ClassNameLocalServiceUtil.getClassNameId(
								Role.class)),
						String.valueOf(roleId), roleName, StringPool.BLANK,
						"Autogenerated role from portal upgrade",
						String.valueOf(type), "lfr-permission-algorithm-5"
					};

					for (int i = 0; i < roleColumns.length; i++) {
						legacyFileExtRoleWriter.write(
							roleColumns[i] + StringPool.COMMA);

						if (i == (roleColumns.length - 1)) {
							legacyFileExtRoleWriter.write(StringPool.NEW_LINE);
						}
					}

					// Roles_Permissions

					for (Long permissionId : permissionIds) {
						String curKey = roleId + "_" + permissionId;

						if (_rolesPermissions.contains(curKey)) {
							continue;
						}
						else {
							_rolesPermissions.add(curKey);
						}

						legacyFileExtRolesPermissionsWriter.write(
							roleId + "," + permissionId + ",\n");
					}

					// Others_Roles

					for (int i = 0; i < newColumns.length - 1; i++) {
						legacyFileExtOtherRolesWriter.write(
							values[i] + StringPool.COMMA);
					}

					legacyFileExtOtherRolesWriter.write(roleId + ",\n");
				}
			}
		}
		finally {
			legacyFileReader.close();

			legacyFileExtRoleWriter.close();
			legacyFileExtRolesPermissionsWriter.close();
			legacyFileExtOtherRolesWriter.close();
		}

		// Role_

		Table roleTable = new Table(
			RoleModelImpl.TABLE_NAME, RoleModelImpl.TABLE_COLUMNS);

		roleTable.populateTable(legacyFile + _EXT_ROLE);

		// Roles_Permissions

		Table rolesPermissionsTable = new Table(
			"Roles_Permissions",
			new Object[][] {
				{"roleId", Types.BIGINT}, {"permissionId", Types.BIGINT}
			});

		rolesPermissionsTable.populateTable(
			legacyFile + _EXT_ROLES_PERMIMISSIONS);

		// Others_Roles

		Table othersRolesTable = new Table(newName, newColumns);

		othersRolesTable.populateTable(legacyFile + _EXT_OTHER_ROLES);

		// Clean up

		FileUtil.delete(legacyFile + _EXT_ROLE);
		FileUtil.delete(legacyFile + _EXT_ROLES_PERMIMISSIONS);
		FileUtil.delete(legacyFile + _EXT_OTHER_ROLES);
	}

	protected void convertToBitwise() throws Exception {

		// ResourceAction and ResourcePermission

		MaintenanceUtil.appendStatus(
			"Generating ResourceAction and ResourcePermission data");

		Table table = new Table(
			ResourceCodeModelImpl.TABLE_NAME,
			new Object[][] {
				{"name", new Integer(Types.VARCHAR)}
			});

		table.setSelectSQL(
			"SELECT name FROM " + ResourceCodeModelImpl.TABLE_NAME +
				" GROUP BY name");

		String tempFile = table.generateTempFile();

		UnsyncBufferedReader resourceNameReader = new UnsyncBufferedReader(
			new FileReader(tempFile));

		Writer resourcePermissionWriter = new UnsyncBufferedWriter(
			new FileWriter(tempFile + _EXT_RESOURCE_PERMISSION));

		PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM = 6;

		try {
			String line = null;

			while (Validator.isNotNull(line = resourceNameReader.readLine())) {
				String[] values = StringUtil.split(line);

				if (values.length == 0) {
					continue;
				}

				String name = values[0];

				List<String> defaultActionIds =
					ResourceActionsUtil.getResourceActions(name);

				ResourceActionLocalServiceUtil.checkResourceActions(
					name, defaultActionIds);

				convertResourcePermission(resourcePermissionWriter, name);
			}

			resourcePermissionWriter.close();

			MaintenanceUtil.appendStatus("Updating ResourcePermission table");

			Table resourcePermissionTable = new Table(
				ResourcePermissionModelImpl.TABLE_NAME,
				ResourcePermissionModelImpl.TABLE_COLUMNS);

			resourcePermissionTable.populateTable(
				tempFile + _EXT_RESOURCE_PERMISSION);
		}
		catch (Exception e) {
			PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM = 5;

			throw e;
		}
		finally {
			resourceNameReader.close();

			resourcePermissionWriter.close();

			FileUtil.delete(tempFile);
			FileUtil.delete(tempFile + _EXT_RESOURCE_PERMISSION);
		}

		// Clean up

		MaintenanceUtil.appendStatus("Cleaning up legacy tables");

		DB db = DBFactoryUtil.getDB();

		db.runSQL("DELETE FROM " + ResourceCodeModelImpl.TABLE_NAME);
		db.runSQL("DELETE FROM " + PermissionModelImpl.TABLE_NAME);
		db.runSQL("DELETE FROM " + ResourceModelImpl.TABLE_NAME);
		db.runSQL("DELETE FROM Roles_Permissions");

		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.getRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}
		catch (PortalException pe) {
			release = ReleaseLocalServiceUtil.addRelease(
				ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME,
				ReleaseInfo.getBuildNumber());
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), ReleaseInfo.getBuildNumber(),
			ReleaseInfo.getBuildDate(), false);

		MaintenanceUtil.appendStatus("Converted to bitwise permission");
	}

	protected void convertToRBAC() throws Exception {
		initializeRBAC();

		// Groups_Permissions

		convertPermissions(
			RoleConstants.TYPE_SITE, "Groups_Permissions",
			new String[] {"groupId"}, "Groups_Roles",
			new Object[][] {
				{"groupId", Types.BIGINT}, {"roleId", Types.BIGINT}
			});

		// OrgGroupPermission

		convertPermissions(
			RoleConstants.TYPE_ORGANIZATION, "OrgGroupPermission",
			new String[] {"organizationId", "groupId"}, "OrgGroupRole",
			new Object[][] {
				{"organizationId", Types.BIGINT}, {"groupId", Types.BIGINT},
				{"roleId", Types.BIGINT}
			});

		// Users_Permissions

		convertPermissions(
			RoleConstants.TYPE_REGULAR, "Users_Permissions",
			new String[] {"userId"}, "Users_Roles",
			new Object[][] {
				{"userId", Types.BIGINT}, {"roleId", Types.BIGINT}
			});

		// Clean up

		PermissionCacheUtil.clearCache();

		PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM = 5;

		MaintenanceUtil.appendStatus("Converted to RBAC permission");
	}

	@Override
	protected void doConvert() throws Exception {
		try {
			BatchSessionUtil.setEnabled(true);

			initialize();

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
				convertToRBAC();
			}

			convertToBitwise();

			MaintenanceUtil.appendStatus(
				"Please set " + PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM +
					" in your portal-ext.properties to 6 and restart server");
		}
		finally {
			ShutdownUtil.shutdown(0);
		}
	}

	protected void initialize() throws Exception {

		// Resource actions for unknown portlets

		List<ResourceCode> resourceCodes =
			ResourceCodeLocalServiceUtil.getResourceCodes(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ResourceCode resourceCode : resourceCodes) {
			String name = resourceCode.getName();

			if (!name.contains(StringPool.PERIOD)) {
				ResourceActionsUtil.getPortletResourceActions(name);
			}
		}
	}

	protected void initializeRBAC() throws Exception {

		// System roles and default users

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			_defaultRolesMap.put(
				companyId,
				new Role[] {
					RoleLocalServiceUtil.getRole(
						companyId, RoleConstants.ORGANIZATION_USER),
					RoleLocalServiceUtil.getRole(
						companyId, RoleConstants.POWER_USER),
					RoleLocalServiceUtil.getRole(
						companyId, RoleConstants.SITE_MEMBER)
					}
				);

			Role guestRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.GUEST);

			_guestRolesMap.put(companyId, guestRole);

			Role ownerRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			_ownerRolesMap.put(companyId, ownerRole);

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			_guestUsersSet.add(defaultUserId);
		}

		// Roles_Permissions

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("SELECT * FROM Roles_Permissions");

			rs = ps.executeQuery();

			while (rs.next()) {
				long roleId = rs.getLong("roleId");
				long permissionId = rs.getLong("permissionId");

				_rolesPermissions.add(roleId + "_" + permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// Groups

		List<Group> groups = GroupLocalServiceUtil.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			_groupsMap.put(group.getGroupId(), group);
		}
	}

	protected boolean isGenerateCustomRoles() {
		String[] parameterValues = getParameterValues();

		return GetterUtil.getBoolean(parameterValues[0]);
	}

	private static final String _CONVERT_RESOURCE_PERMISSION =
		PropsKeys.MULTI_VALUE_MAP + ConvertPermissionAlgorithm.class.getName() +
			".convertResourcePermission";

	private static final String _CONVERT_ROLES =
		PropsKeys.MULTI_VALUE_MAP + ConvertPermissionAlgorithm.class.getName() +
			".convertRoles";

	private static final String _EXT_OTHER_ROLES = ".others_roles";

	private static final String _EXT_RESOURCE_PERMISSION =
		".resource_permission";

	private static final String _EXT_ROLE = ".role";

	private static final String _EXT_ROLES_PERMIMISSIONS = ".roles_permissions";

	private static final String _UPDATED = ".updated";

	private static Log _log = LogFactoryUtil.getLog(
		ConvertPermissionAlgorithm.class);

	private Map<Long, Role[]> _defaultRolesMap = new HashMap<Long, Role[]>();
	private Map<Long, Group> _groupsMap = new HashMap<Long, Group>();
	private Map<Long, Role> _guestRolesMap = new HashMap<Long, Role>();
	private Set<Long> _guestUsersSet = new HashSet<Long>();
	private Map<Long, Role> _ownerRolesMap = new HashMap<Long, Role>();
	private Set<String> _rolesPermissions = new HashSet<String>();

}