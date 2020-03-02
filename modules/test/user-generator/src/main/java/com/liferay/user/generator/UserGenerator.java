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

package com.liferay.user.generator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.user.generator.configuration.UserGeneratorConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
	configurationPid = "com.liferay.user.generator.configuration.UserGeneratorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class UserGenerator {

	@Activate
	protected void activate(Map<String, Object> properties) {
		UserGeneratorConfiguration userGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				UserGeneratorConfiguration.class, properties);

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				userGeneratorConfiguration.virtualHostName());

			_companyId = company.getPrimaryKey();
			_defaultUserId = _userLocalService.getDefaultUserId(_companyId);
		}
		catch (PortalException e) {
			_log.error(e, e);
		}

		try {
			File csvFile = new File(userGeneratorConfiguration.pathToUserCsv());

			CSVParser csvParser = CSVParser.parse(csvFile, Charset.defaultCharset(), CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreSurroundingSpaces().withNullString(""));

			for (CSVRecord csvRecord : csvParser) {
				String emailAddress = csvRecord.get("emailAddress");

				if (_addedUserMap.containsKey(emailAddress)) {
					continue;
				}

				String password = csvRecord.get("password");
				String screenName = csvRecord.get("screenName");
				String firstName = csvRecord.get("firstName");
				String middleName = csvRecord.get("middleName");
				String lastName = csvRecord.get("lastName");

				int javaMonthOffset = 1; //java.util.Date months start at 0 (meaning 0 for January)
				int birthdayMonth =
					Integer.parseInt(csvRecord.get("birthdayMonth")) - javaMonthOffset;
				int birthdayDay =
					Integer.parseInt(csvRecord.get("birthdayDay"));
				int birthdayYear =
					Integer.parseInt(csvRecord.get("birthdayYear"));
				String jobTitle = csvRecord.get("jobTitle");

				String gender = csvRecord.get("gender");

				boolean male = true;

				if(gender.equalsIgnoreCase("female")) {
					male = false;
				}

				long[] organizationIds = _getIdArrayFromCell(csvRecord, "organizations");

				long[] userGroupIds = _getIdArrayFromCell(csvRecord, "userGroups");

				long[] roleIds = _getIdArrayFromCell(csvRecord, "roles");

				try {
					User user = _userLocalService.addUser(
						_defaultUserId, _companyId, false, password, password,
						false, screenName, emailAddress, 0, StringPool.BLANK,
						LocaleUtil.getDefault(), firstName, middleName, lastName, 0, 0,
						male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
						null, organizationIds, roleIds, userGroupIds, false,
						new ServiceContext());

					_addedUserMap.put(emailAddress, user.getPrimaryKey());
				}
				catch (PortalException e) {
					_log.error(e, e);
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					userGeneratorConfiguration.customActivationMessage());
			}
		}
		catch (IOException e) {
			_log.error(e, e);
		}
	}

	@Deactivate
	protected void deactivate() {

			_addedUserMap.entrySet().parallelStream().forEach(e -> {
				try {
					_userLocalService.deleteUser(e.getValue());
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}
			});

			_addedOrganizationMap.entrySet().parallelStream().forEach(e -> {
				try {
					_organizationLocalService.deleteOrganization(e.getValue());
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}
			});

			_addedRoleMap.entrySet().parallelStream().forEach(e -> {
				try {
					_roleLocalService.deleteRole(e.getValue());
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}
			});

		try {
			_userGroupLocalService.deleteUserGroups(_companyId);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private long[] _getIdArrayFromCell(CSVRecord csvRecord, String headerName) {
		System.out.println(csvRecord.get("emailAddress") + ": " + headerName + " being added"); //TODO REMOVE

		String recordCell = csvRecord.get(headerName);

		if(recordCell == null) {
			return null;
		}

		String[] stringArray = recordCell.split(",");

		long[] idArray = new long[stringArray.length];

		for (int i = 0; i < stringArray.length; i++) {
			String name = stringArray[i].trim();

			try {
				if(headerName.equalsIgnoreCase("organizations")) {
					if (_addedOrganizationMap.containsKey(name)) {
						idArray[i] = _addedOrganizationMap.get(name);
					}
					else {
						Organization newOrganization = _organizationLocalService.addOrganization(
							_defaultUserId, 0, name, false);

						idArray[i] = newOrganization.getPrimaryKey();

						_addedOrganizationMap.put(name, idArray[i]);
					}
				}
				else if(headerName.equalsIgnoreCase("userGroups")) {
					if (_addedUserGroupMap.containsKey(name)) {
						idArray[i] = _addedUserGroupMap.get(name);
					}
					else {
						UserGroup newUserGroup = _userGroupLocalService.addUserGroup(
							_defaultUserId, _companyId, name, null, null);

						idArray[i] = newUserGroup.getPrimaryKey();

						_addedUserGroupMap.put(name, idArray[i]);
					}
				}
				else if(headerName.equalsIgnoreCase("roles")) {
					if (_addedRoleMap.containsKey(name)) {
						idArray[i] = _addedRoleMap.get(name);
					}
					else {
						Role newRole = _roleLocalService.addRole(_defaultUserId, null, 0, name, null, null, 0, null, null);

						idArray[i] = newRole.getPrimaryKey();

						_addedRoleMap.put(name, idArray[i]);
					}
				}

			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return idArray;
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private long _companyId;

	private long _defaultUserId;

	private static final Log _log = LogFactoryUtil.getLog(UserGenerator.class);

	private volatile HashMap<String, Long> _addedUserMap = new HashMap<>();

	private volatile HashMap<String, Long> _addedOrganizationMap = new HashMap<>();

	private volatile HashMap<String, Long> _addedUserGroupMap = new HashMap<>();

	private volatile HashMap<String, Long> _addedRoleMap = new HashMap<>();
	
	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;
	
	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}