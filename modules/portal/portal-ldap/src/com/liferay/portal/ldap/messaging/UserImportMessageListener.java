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

package com.liferay.portal.ldap.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.ldap.configuration.LDAPConfiguration;
import com.liferay.portal.ldap.settings.LDAPConfigurationSettingsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.ldap.LDAPUserImporter;
import com.liferay.portal.service.CompanyLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	property = {
		"destination.name=" + DestinationNames.SCHEDULED_USER_LDAP_IMPORT
	},
	service = MessageListener.class
)
public class UserImportMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long time =
			System.currentTimeMillis() - _ldapUserImporter.getLastImportTime();

		time = Math.round(time / 60000.0);

		List<Company> companies = _companyLocalService.getCompanies(false);

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			LDAPConfiguration ldapCompanyServiceSettings =
				_ldapConfigurationSettingsUtil.getLDAPConfiguration(companyId);

			if (time >= ldapCompanyServiceSettings.importInterval()) {
				_ldapUserImporter.importUsers(companyId);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.SCHEDULED_USER_LDAP_IMPORT + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	@Reference(unbind = "-")
	protected void setLdapConfigurationSettingsUtil(
		LDAPConfigurationSettingsUtil ldapConfigurationSettingsUtil) {

		_ldapConfigurationSettingsUtil = ldapConfigurationSettingsUtil;
	}

	@Reference(unbind = "-")
	protected void setLdapUserImporter(LDAPUserImporter ldapUserImporter) {
		_ldapUserImporter = ldapUserImporter;
	}

	private CompanyLocalService _companyLocalService;
	private LDAPConfigurationSettingsUtil _ldapConfigurationSettingsUtil;
	private LDAPUserImporter _ldapUserImporter;

}