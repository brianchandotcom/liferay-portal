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

package com.liferay.analytics.demo.data.creator;

import com.liferay.analytics.demo.data.creator.configuration.AnalyticsDemoDataCreatorConfiguration;
import com.liferay.analytics.demo.data.creator.util.GeneratedDataUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.DuplicateRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;

import java.util.Arrays;
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
	service = {}
)
public class AnalyticsCSVDemoDataCreatorImpl {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_loadConfig(properties);

		_addCsvUsers(
			_analyticsDemoDataCreatorConfiguration.pathToUserCsv());

		if (_log.isInfoEnabled()) {
			_log.info(
				_analyticsDemoDataCreatorConfiguration.customActivationMessage());
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			_generatedDataUtil.deleteAll();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _loadConfig(Map<String, Object> properties) {
		_analyticsDemoDataCreatorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsDemoDataCreatorConfiguration.class, properties);

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				_analyticsDemoDataCreatorConfiguration.virtualHostName());

			long companyId = company.getPrimaryKey();

			_generatedDataUtil.setCompanyId(companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private void _addCsvUsers(String csvFilePath) {
		File userCsvFile = new File(csvFilePath);

		CSVParser csvParser = _getCSVParser(userCsvFile);

		for (CSVRecord csvRecord : csvParser) {
			String emailAddress = csvRecord.get("emailAddress");

			if (_generatedDataUtil.containsUserKey(emailAddress)) {
				continue;
			}

			try {
				User user = _addCsvUser(csvRecord);

				_generatedDataUtil.putUser(emailAddress, user);

				if (_log.isInfoEnabled()) {
					_log.info("Created user: " + emailAddress);
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}
	}

	private User _addCsvUser(CSVRecord csvRecord) throws PortalException {
		String emailAddress = csvRecord.get("emailAddress");
		String password = csvRecord.get("password");
		String screenName = csvRecord.get("screenName");
		String firstName = csvRecord.get("firstName");
		String middleName = csvRecord.get("middleName");
		String lastName = csvRecord.get("lastName");
		String jobTitle = csvRecord.get("jobTitle");

		String gender = csvRecord.get("gender");

		boolean male = gender.equalsIgnoreCase("male");

		int javaMonthOffset = 1; //java.util.Date months start at 0 (meaning 0 for January)

		int birthdayMonth =
			GetterUtil.getInteger(csvRecord.get("birthdayMonth")) -
				javaMonthOffset;

		int birthdayDay = GetterUtil.getInteger(csvRecord.get("birthdayDay"));

		int birthdayYear = GetterUtil.getInteger(csvRecord.get("birthdayYear"));

		long[] groupIds = null;
		long[] organizationIds = _getIdArrayFromRowCell(
			csvRecord, "organizations");
		long[] roleIds = _getIdArrayFromRowCell(csvRecord, "roles");
		long[] userGroupIds = _getIdArrayFromRowCell(csvRecord, "userGroups");
		long[] teamIds = _getIdArrayFromRowCell(csvRecord, "teams");

		User user = _userLocalService.addUser(
			_generatedDataUtil.getDefaultUserId(),
			_generatedDataUtil.getCompanyId(), false, password, password, false,
			screenName, emailAddress, 0, StringPool.BLANK,
			LocaleUtil.getDefault(), firstName, middleName, lastName, 0, 0,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, false,
			new ServiceContext());

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
					if (_generatedDataUtil.containsOrganizationKey(name)) {
						Organization org = _generatedDataUtil.getOrganization(
							name);

						idArray[i] = org.getPrimaryKey();
					}
					else {
						Organization newOrg =
							_organizationLocalService.addOrganization(
								_generatedDataUtil.getDefaultUserId(), 0, name,
								false);

						_generatedDataUtil.putOrganization(name, newOrg);

						idArray[i] = newOrg.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("userGroups")) {
					if (_generatedDataUtil.containsUserGroupKey(name)) {
						UserGroup userGroup = _generatedDataUtil.getUserGroup(
							name);

						idArray[i] = userGroup.getPrimaryKey();
					}
					else {
						UserGroup newUserGroup =
							_userGroupLocalService.addUserGroup(
								_generatedDataUtil.getDefaultUserId(),
								_generatedDataUtil.getCompanyId(), name, null,
								null);

						_generatedDataUtil.putUserGroup(name, newUserGroup);

						idArray[i] = newUserGroup.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("roles")) {
					if (_generatedDataUtil.containsRoleKey(name)) {
						Role role = _generatedDataUtil.getRole(name);

						idArray[i] = role.getPrimaryKey();
					}
					else {
						Role role;

						try {
							role = _roleLocalService.addRole(
								_generatedDataUtil.getDefaultUserId(), null, 0,
								name, null, null, RoleConstants.TYPE_REGULAR,
								null, null);
						}
						catch (DuplicateRoleException duplicateRoleException) {
							role = _roleLocalService.getRole(
								_generatedDataUtil.getCompanyId(), name);
						}

						_generatedDataUtil.putRole(name, role);

						idArray[i] = role.getPrimaryKey();
					}
				}
				else if (header.equalsIgnoreCase("teams")) {
					if (_generatedDataUtil.containsTeamKey(name)) {
						Team team = _generatedDataUtil.getTeam(name);

						idArray[i] = team.getPrimaryKey();
					}
					else {
						Team newTeam = _teamLocalService.addTeam(
							_generatedDataUtil.getDefaultUserId(),
							_generatedDataUtil.getDefaultGroupId(), name, null,
							new ServiceContext());

						_generatedDataUtil.putTeam(name, newTeam);

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

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GeneratedDataUtil _generatedDataUtil;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}