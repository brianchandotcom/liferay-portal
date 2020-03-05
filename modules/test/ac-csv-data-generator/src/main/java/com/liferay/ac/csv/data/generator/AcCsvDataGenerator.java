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
import com.liferay.ac.csv.data.generator.util.GeneratedDataUtil;
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
	configurationPid = "com.liferay.ac.csv.data.generator.configuration.AcCsvDataGeneratorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = {}
)
public class AcCsvDataGenerator {

	@Activate
	protected void activate(Map<String, Object> properties) {
		AcCsvDataGeneratorConfiguration acCsvDataGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AcCsvDataGeneratorConfiguration.class, properties);

		_verbose = acCsvDataGeneratorConfiguration.verbose();

		try {
			Company company = _companyLocalService.getCompanyByVirtualHost(
				acCsvDataGeneratorConfiguration.virtualHostName());

			_companyId = company.getPrimaryKey();

			_generatedDataUtil.setCompanyId(_companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		try {
			File csvFile = new File(
				acCsvDataGeneratorConfiguration.pathToUserCsv());

			CSVParser csvParser = CSVParser.parse(
				csvFile, Charset.defaultCharset(),
				CSVFormat.DEFAULT.withFirstRecordAsHeader(
				).withIgnoreSurroundingSpaces(
				).withNullString(
					""
				));

			for (CSVRecord csvRecord : csvParser) {
				if (_generatedDataUtil.containsUserKey(
						csvRecord.get("emailAddress"))) {

					continue;
				}

				_csvUser.assignOrganizations(
					_getIdArrayFromCell(csvRecord, "organizations"));

				_csvUser.assignUserGroups(
					_getIdArrayFromCell(csvRecord, "userGroups"));

				_csvUser.assignRoles(_getIdArrayFromCell(csvRecord, "roles"));

				try {
					User user = _csvUser.addUser(csvRecord);

					_generatedDataUtil.putUser(
						user.getEmailAddress(), user.getPrimaryKey());

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
				_log.info(
					acCsvDataGeneratorConfiguration.customActivationMessage());
			}
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}
	}

	@Deactivate
	protected void deactivate() {
		_generatedDataUtil.deleteAll();
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
					if (_generatedDataUtil.containsOrganizationKey(name)) {
						idArray[i] = _generatedDataUtil.getOrganization(name);
					}
					else {
						Organization newOrganization =
							_organizationLocalService.addOrganization(
								_generatedDataUtil.getDefaultUserId(), 0, name,
								false);

						idArray[i] = newOrganization.getPrimaryKey();

						_generatedDataUtil.putOrganization(name, idArray[i]);
					}
				}
				else if (headerName.equalsIgnoreCase("userGroups")) {
					if (_generatedDataUtil.containsUserGroupKey(name)) {
						idArray[i] = _generatedDataUtil.getUserGroup(name);
					}
					else {
						UserGroup newUserGroup =
							_userGroupLocalService.addUserGroup(
								_generatedDataUtil.getDefaultUserId(),
								_companyId, name, null, null);

						idArray[i] = newUserGroup.getPrimaryKey();

						_generatedDataUtil.putUserGroup(name, idArray[i]);
					}
				}
				else if (headerName.equalsIgnoreCase("roles")) {
					if (_generatedDataUtil.containsRoleKey(name)) {
						idArray[i] = _generatedDataUtil.getRole(name);
					}
					else {
						Role newRole = _roleLocalService.addRole(
							_generatedDataUtil.getDefaultUserId(), null, 0,
							name, null, null, 0, null, null);

						idArray[i] = newRole.getPrimaryKey();

						_generatedDataUtil.putRole(name, idArray[i]);
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

	private static final Log _log = LogFactoryUtil.getLog(
		AcCsvDataGenerator.class);

	private long _companyId;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CsvUser _csvUser;

	@Reference
	private GeneratedDataUtil _generatedDataUtil;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	private String _verbose;

}