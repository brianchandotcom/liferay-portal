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

package com.liferay.ac.csv.data.generator;

import com.liferay.ac.csv.data.generator.configuration.AcCsvDataGeneratorConfiguration;
import com.liferay.ac.csv.data.generator.csv.CsvUser;
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
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

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
	configurationPid = "com.liferay.csv.user.generator.configuration.AcCsvDataGeneratorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class AcCsvDataGenerator {

	@Activate
	protected void activate(Map<String, Object> properties) {
		AcCsvDataGeneratorConfiguration AcCsvDataGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AcCsvDataGeneratorConfiguration.class, properties);

		_verbose = AcCsvDataGeneratorConfiguration.verbose();

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				AcCsvDataGeneratorConfiguration.virtualHostName());

			_companyId = company.getPrimaryKey();

			_defaultUserId = _userLocalService.getDefaultUserId(_companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		try {
			File csvFile = new File(AcCsvDataGeneratorConfiguration.pathToUserCsv());

			CSVParser csvParser = CSVParser.parse(
				csvFile, Charset.defaultCharset(),
				CSVFormat.DEFAULT.withFirstRecordAsHeader(
				).withIgnoreSurroundingSpaces(
				).withNullString(
					""
				));


			_csvUser.setCompanyId(_companyId);

			for (CSVRecord csvRecord : csvParser) {
				if (_addedUserMap.containsKey(csvRecord.get("emailAddress"))) {
					continue;
				}

				_csvUser.assignOrganizations(
					_getIdArrayFromCell(csvRecord, "organizations"));

				_csvUser.assignUserGroups(
					_getIdArrayFromCell(csvRecord, "userGroups"));

				_csvUser.assignRoles(_getIdArrayFromCell(csvRecord, "roles"));


				try {
					User user = _csvUser.addUser(csvRecord);

					_addedUserMap.put(user.getEmailAddress(), user.getPrimaryKey());

					if (!_verbose.equalsIgnoreCase("false")) {
						System.out.println(
							"Created user: " + user.getEmailAddress());
					}
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(AcCsvDataGeneratorConfiguration.customActivationMessage());
			}
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}
	}

	@Deactivate
	protected void deactivate() {
		_addedUserMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_userLocalService.deleteUser(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		_addedOrganizationMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_organizationLocalService.deleteOrganization(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		_addedRoleMap.entrySet(
		).parallelStream(
		).forEach(
			e -> {
				try {
					_roleLocalService.deleteRole(e.getValue());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}
			}
		);

		try {
			_userGroupLocalService.deleteUserGroups(_companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	@Reference(target = ModuleServiceLifecycle.SYSTEM_CHECK, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private long[] _getIdArrayFromCell(CSVRecord csvRecord, String headerName) {
		String recordCell = csvRecord.get(headerName);

		if (recordCell == null) {
			return null;
		}

		String[] stringArray = recordCell.split(",");

		long[] idArray = new long[stringArray.length];

		for (int i = 0; i < stringArray.length; i++) {
			String name = stringArray[i].trim();

			try {
				if (headerName.equalsIgnoreCase("organizations")) {
					if (_addedOrganizationMap.containsKey(name)) {
						idArray[i] = _addedOrganizationMap.get(name);
					}
					else {
						Organization newOrganization =
							_organizationLocalService.addOrganization(
								_defaultUserId, 0, name, false);

						idArray[i] = newOrganization.getPrimaryKey();

						_addedOrganizationMap.put(name, idArray[i]);
					}
				}
				else if (headerName.equalsIgnoreCase("userGroups")) {
					if (_addedUserGroupMap.containsKey(name)) {
						idArray[i] = _addedUserGroupMap.get(name);
					}
					else {
						UserGroup newUserGroup =
							_userGroupLocalService.addUserGroup(
								_defaultUserId, _companyId, name, null, null);

						idArray[i] = newUserGroup.getPrimaryKey();

						_addedUserGroupMap.put(name, idArray[i]);
					}
				}
				else if (headerName.equalsIgnoreCase("roles")) {
					if (_addedRoleMap.containsKey(name)) {
						idArray[i] = _addedRoleMap.get(name);
					}
					else {
						Role newRole = _roleLocalService.addRole(
							_defaultUserId, null, 0, name, null, null, 0, null,
							null);

						idArray[i] = newRole.getPrimaryKey();

						_addedRoleMap.put(name, idArray[i]);
					}
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		if (!_verbose.equalsIgnoreCase("false")) {
			System.out.printf(
				"Added %s to %s with ID's: %s%n", csvRecord.get("emailAddress"),
				headerName, Arrays.toString(idArray));
		}

		return idArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(AcCsvDataGenerator.class);

	private volatile HashMap<String, Long> _addedOrganizationMap =
		new HashMap<>();
	private volatile HashMap<String, Long> _addedRoleMap = new HashMap<>();
	private volatile HashMap<String, Long> _addedUserGroupMap = new HashMap<>();
	private volatile HashMap<String, Long> _addedUserMap = new HashMap<>();
	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	private long _defaultUserId;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private CsvUser _csvUser;

	private String _verbose;

}