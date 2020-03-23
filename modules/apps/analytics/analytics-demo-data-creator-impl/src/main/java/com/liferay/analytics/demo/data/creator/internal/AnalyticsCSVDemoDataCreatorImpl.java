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

import com.liferay.analytics.demo.data.creator.AnalyticsCSVDemoDataCreator;
import com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.DuplicateRoleException;
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
	service = AnalyticsCSVDemoDataCreator.class
)
public class AnalyticsCSVDemoDataCreatorImpl
	implements AnalyticsCSVDemoDataCreator {

	public void create() throws Exception {
		File file = new File(
			_analyticsDemoDataCreatorConfiguration.pathToUsersCSV());

		for (CSVRecord csvRecord : _getCSVParser(file)) {
			String emailAddress = csvRecord.get("emailAddress");

			if (_users.containsKey(emailAddress)) {
				continue;
			}

			try {
				_users.put(emailAddress, _addCsvUser(csvRecord));

				if (_log.isInfoEnabled()) {
					_log.info("Created user: " + emailAddress);
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

	private User _addCsvUser(CSVRecord csvRecord) throws PortalException {
		String gender = csvRecord.get("gender");

		boolean male = StringUtil.equalsIgnoreCase(gender, "male");

		long[] organizationIds = _getIdArrayFromRowCell(
			csvRecord, "organizations");
		long[] roleIds = _getIdArrayFromRowCell(csvRecord, "roles");
		long[] teamIds = _getIdArrayFromRowCell(csvRecord, "teams");
		long[] userGroupIds = _getIdArrayFromRowCell(csvRecord, "userGroups");

		User user = _userLocalService.addUser(
			_userLocalService.getDefaultUserId(_companyId), _companyId, false,
			csvRecord.get("password"), csvRecord.get("password"), false,
			csvRecord.get("screenName"), csvRecord.get("emailAddress"), 0,
			StringPool.BLANK, LocaleUtil.getDefault(),
			csvRecord.get("firstName"), csvRecord.get("middleName"),
			csvRecord.get("lastName"), 0, 0, male,
			GetterUtil.getInteger(csvRecord.get("birthdayMonth")) - 1,
			GetterUtil.getInteger(csvRecord.get("birthdayDay")),
			GetterUtil.getInteger(csvRecord.get("birthdayYear")),
			csvRecord.get("jobTitle"), null, organizationIds, roleIds,
			userGroupIds, false, new ServiceContext());

		if (teamIds != null) {
			_teamLocalService.addUserTeams(user.getPrimaryKey(), teamIds);
		}

		return user;
	}

	private CSVParser _getCSVParser(File csvFile) {
		CSVParser csvParser = null;

		try {
			CSVFormat csvFormat = CSVFormat.DEFAULT;

			csvFormat = csvFormat.withFirstRecordAsHeader();
			csvFormat = csvFormat.withIgnoreSurroundingSpaces();
			csvFormat = csvFormat.withNullString("");

			csvParser = CSVParser.parse(
				csvFile, Charset.defaultCharset(), csvFormat);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}

		return csvParser;
	}

	private long[] _getIdArrayFromRowCell(CSVRecord csvRecord, String header) {
		String recordCell = csvRecord.get(header);

		if (recordCell == null) {
			return null;
		}

		String[] stringArray = recordCell.split(",");

		long[] idArray = new long[stringArray.length];

		for (int i = 0; i < stringArray.length; i++) {
			String name = stringArray[i].trim();

			try {
				if (header.equalsIgnoreCase("organizations")) {
					if (_organizations.containsKey(name)) {
						Organization org = _organizations.get(name);

						idArray[i] = org.getPrimaryKey();
					}
					else {
						Organization newOrg =
							_organizationLocalService.addOrganization(
								_defaultUserId, 0, name, false);

						_organizations.put(name, newOrg);

						idArray[i] = newOrg.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("userGroups")) {
					if (_userGroups.containsKey(name)) {
						UserGroup userGroup = _userGroups.get(name);

						idArray[i] = userGroup.getPrimaryKey();
					}
					else {
						UserGroup newUserGroup =
							_userGroupLocalService.addUserGroup(
								_defaultUserId, _companyId, name, null,
								null);

						_userGroups.put(name, newUserGroup);

						idArray[i] = newUserGroup.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("roles")) {
					if (_roles.containsRoleKey(name)) {
						Role role = _roles.get(name);

						idArray[i] = role.getPrimaryKey();
					}
					else {
						Role role;

						try {
							role = _roleLocalService.addRole(
								_defaultUserId, null, 0, name, null, null,
								RoleConstants.TYPE_REGULAR, null, null);
						}
						catch (DuplicateRoleException duplicateRoleException) {
							role = _roleLocalService.getRole(
								_companyId, name);
						}

						_roles.put(name, role);

						idArray[i] = role.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("teams")) {
					if (_teams.containsTeamKey(name)) {
						Team team = _teams.get(name);

						idArray[i] = team.getPrimaryKey();
					}
					else {
						Team newTeam = _teamLocalService.addTeam(
							_defaultUserId, _defaultGroupId, name, null,
							new ServiceContext());

						_teams.put(name, newTeam);

						idArray[i] = newTeam.getPrimaryKey();
					}
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Added %s to %s with ID's: %s%n",
					csvRecord.get("emailAddress"), header,
					Arrays.toString(idArray)));
		}

		return idArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsCSVDemoDataCreatorImpl.class);

	private AnalyticsDemoDataCreatorConfiguration
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