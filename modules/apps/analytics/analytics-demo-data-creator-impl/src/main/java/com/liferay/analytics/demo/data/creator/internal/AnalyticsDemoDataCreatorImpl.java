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

package com.liferay.analytics.demo.data.creator.internal;

import com.liferay.analytics.demo.data.creator.AnalyticsDemoDataCreator;
import com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.DuplicateOrganizationException;
import com.liferay.portal.kernel.exception.DuplicateRoleException;
import com.liferay.portal.kernel.exception.DuplicateTeamException;
import com.liferay.portal.kernel.exception.DuplicateUserGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	configurationPid = "com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = AnalyticsDemoDataCreator.class
)
public class AnalyticsDemoDataCreatorImpl implements AnalyticsDemoDataCreator {

	@Override
	public void create() throws Exception {
		File file = new File(
			_analyticsDemoDataCreatorConfiguration.pathToUsersCSV());

		for (CSVRecord csvRecord : _getCSVParser(file)) {
			String emailAddress = csvRecord.get("emailAddress");

			if (_users.containsKey(emailAddress)) {
				continue;
			}

			try {
				_users.put(emailAddress, _addUser(csvRecord));

				if (_log.isInfoEnabled()) {
					_log.info("Created user " + emailAddress);
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished adding analytics demo data");
		}
	}

	@Override
	public void delete() throws PortalException {
		for (Map.Entry<String, User> entry : _users.entrySet()) {
			_userLocalService.deleteUser(entry.getValue());
		}

		for (Map.Entry<String, Organization> entry :
				_organizations.entrySet()) {

			_organizationLocalService.deleteOrganization(entry.getValue());
		}

		for (Map.Entry<String, Role> entry : _roles.entrySet()) {
			_roleLocalService.deleteRole(entry.getValue());
		}

		for (Map.Entry<String, Team> entry : _teams.entrySet()) {
			_teamLocalService.deleteTeam(entry.getValue());
		}

		for (Map.Entry<String, UserGroup> entry : _userGroups.entrySet()) {
			_userGroupLocalService.deleteUserGroup(entry.getValue());
		}

		_organizations.clear();
		_roles.clear();
		_teams.clear();
		_userGroups.clear();
		_users.clear();
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_analyticsDemoDataCreatorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsDemoDataCreatorConfiguration.class, properties);

		Company company = _companyLocalService.getCompanyByVirtualHost(
			_analyticsDemoDataCreatorConfiguration.virtualHostname());

		_companyId = company.getCompanyId();

		_defaultUserId = _userLocalService.getDefaultUserId(_companyId);

		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		_defaultGroupId = group.getGroupId();

		create();
	}

	@Deactivate
	protected void deactivate() {
		try {
			delete();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private long[] _addEntries(CSVRecord csvRecord, String header)
		throws PortalException {

		String cell = csvRecord.get(header);

		if (cell == null) {
			return null;
		}

		long[] ids = new long[0];

		String[] values = cell.split(",");

		if (StringUtil.equalsIgnoreCase(header, "organizations")) {
			ids = _addOrganizations(values);
		}
		else if (StringUtil.equalsIgnoreCase(header, "roles")) {
			ids = _addRoles(values);
		}
		else if (StringUtil.equalsIgnoreCase(header, "teams")) {
			ids = _addTeams(values);
		}
		else if (StringUtil.equalsIgnoreCase(header, "userGroups")) {
			ids = _addUserGroups(values);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Added ", header, " with ids: ", Arrays.toString(ids)));
		}

		return ids;
	}

	private long[] _addOrganizations(String[] values) throws PortalException {
		long[] ids = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			String name = values[i].trim();

			Organization organization = null;

			try {
				organization = _organizationLocalService.addOrganization(
					_defaultUserId, 0, name, false);
			}
			catch (DuplicateOrganizationException
						duplicateOrganizationException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						duplicateOrganizationException,
						duplicateOrganizationException);
				}

				organization = _organizationLocalService.getOrganization(
					_companyId, name);
			}

			_organizations.put(name, organization);

			ids[i] = organization.getPrimaryKey();
		}

		return ids;
	}

	private long[] _addRoles(String[] values) throws PortalException {
		long[] ids = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			String name = values[i].trim();

			Role role = null;

			try {
				role = _roleLocalService.addRole(
					_defaultUserId, null, 0, name, null, null,
					RoleConstants.TYPE_REGULAR, null, null);
			}
			catch (DuplicateRoleException duplicateRoleException) {
				if (_log.isDebugEnabled()) {
					_log.debug(duplicateRoleException, duplicateRoleException);
				}

				role = _roleLocalService.getRole(_companyId, name);
			}

			_roles.put(name, role);

			ids[i] = role.getPrimaryKey();
		}

		return ids;
	}

	private long[] _addTeams(String[] values) throws PortalException {
		long[] ids = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			String name = values[i].trim();

			Team team = null;

			try {
				team = _teamLocalService.addTeam(
					_defaultUserId, _defaultGroupId, name, null,
					new ServiceContext());
			}
			catch (DuplicateTeamException duplicateTeamException) {
				if (_log.isDebugEnabled()) {
					_log.debug(duplicateTeamException, duplicateTeamException);
				}

				team = _teamLocalService.getTeam(_defaultGroupId, name);
			}

			_teams.put(name, team);

			ids[i] = team.getPrimaryKey();
		}

		return ids;
	}

	private User _addUser(CSVRecord csvRecord) throws PortalException {
		String gender = csvRecord.get("gender");

		boolean male = StringUtil.equalsIgnoreCase(gender, "male");

		User user = _userLocalService.addUser(
			_defaultUserId, _companyId, false, csvRecord.get("password"),
			csvRecord.get("password"), false, csvRecord.get("screenName"),
			csvRecord.get("emailAddress"), LocaleUtil.getDefault(),
			csvRecord.get("firstName"), csvRecord.get("middleName"),
			csvRecord.get("lastName"), 0, 0, male,
			GetterUtil.getInteger(csvRecord.get("birthdayMonth")) - 1,
			GetterUtil.getInteger(csvRecord.get("birthdayDay")),
			GetterUtil.getInteger(csvRecord.get("birthdayYear")),
			csvRecord.get("jobTitle"), null,
			_addEntries(csvRecord, "organizations"),
			_addEntries(csvRecord, "roles"),
			_addEntries(csvRecord, "userGroups"), false, new ServiceContext());

		long[] teamIds = _addEntries(csvRecord, "teams");

		if (teamIds != null) {
			_teamLocalService.addUserTeams(user.getPrimaryKey(), teamIds);
		}

		return user;
	}

	private long[] _addUserGroups(String[] values) throws PortalException {
		long[] ids = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			String name = values[i].trim();

			UserGroup userGroup = null;

			try {
				userGroup = _userGroupLocalService.addUserGroup(
					_defaultUserId, _companyId, name, null, null);
			}
			catch (DuplicateUserGroupException duplicateUserGroupException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						duplicateUserGroupException,
						duplicateUserGroupException);
				}

				userGroup = _userGroupLocalService.getUserGroup(
					_companyId, name);
			}

			_userGroups.put(name, userGroup);

			ids[i] = userGroup.getPrimaryKey();
		}

		return ids;
	}

	private CSVParser _getCSVParser(File csvFile) throws Exception {
		CSVFormat csvFormat = CSVFormat.DEFAULT;

		csvFormat = csvFormat.withFirstRecordAsHeader();
		csvFormat = csvFormat.withIgnoreSurroundingSpaces();
		csvFormat = csvFormat.withNullString(StringPool.BLANK);

		try {
			return CSVParser.parse(
				csvFile, Charset.defaultCharset(), csvFormat);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);

			throw ioException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDemoDataCreatorImpl.class);

	private volatile AnalyticsDemoDataCreatorConfiguration
		_analyticsDemoDataCreatorConfiguration;
	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private long _defaultGroupId;
	private long _defaultUserId;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	private final HashMap<String, Organization> _organizations =
		new HashMap<>();

	@Reference
	private RoleLocalService _roleLocalService;

	private final HashMap<String, Role> _roles = new HashMap<>();

	@Reference
	private TeamLocalService _teamLocalService;

	private final HashMap<String, Team> _teams = new HashMap<>();

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	private final HashMap<String, UserGroup> _userGroups = new HashMap<>();

	@Reference
	private UserLocalService _userLocalService;

	private final HashMap<String, User> _users = new HashMap<>();

}