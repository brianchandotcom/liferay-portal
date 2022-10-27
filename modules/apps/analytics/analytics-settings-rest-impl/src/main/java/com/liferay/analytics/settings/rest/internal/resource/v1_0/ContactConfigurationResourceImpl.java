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

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactConfiguration;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactAccountGroupDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactOrganizationDTOConverter;
import com.liferay.analytics.settings.rest.internal.dto.v1_0.converter.ContactUserGroupDTOConverter;
import com.liferay.analytics.settings.rest.internal.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactConfigurationResource;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Ferrari
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/contact-configuration.properties",
	scope = ServiceScope.PROTOTYPE, service = ContactConfigurationResource.class
)
public class ContactConfigurationResourceImpl
	extends BaseContactConfigurationResourceImpl {

	@Override
	public ContactConfiguration getContactConfiguration() throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(
				contextCompany.getCompanyId());

		return new ContactConfiguration() {
			{
				syncAllAccounts = analyticsConfiguration.syncAllAccounts();
				syncAllContacts = analyticsConfiguration.syncAllContacts();
				syncedAccountGroupIds =
					analyticsConfiguration.syncedAccountGroupIds();
				syncedOrganizationIds =
					analyticsConfiguration.syncedOrganizationIds();
				syncedUserGroupIds =
					analyticsConfiguration.syncedUserGroupIds();
			}
		};
	}

	@Override
	public void putContactConfiguration(
			ContactConfiguration contactConfiguration)
		throws Exception {

		boolean accountsSelected = false;

		if (contactConfiguration.getSyncAllAccounts() ||
			!ArrayUtil.isEmpty(
				contactConfiguration.getSyncedAccountGroupIds())) {

			accountsSelected = true;
		}

		boolean contactsSelected = false;

		if (contactConfiguration.getSyncAllContacts() ||
			!ArrayUtil.isEmpty(
				contactConfiguration.getSyncedOrganizationIds()) ||
			!ArrayUtil.isEmpty(contactConfiguration.getSyncedUserGroupIds())) {

			contactsSelected = true;
		}

		_analyticsCloudClient.updateAnalyticsDataSourceDetails(
			accountsSelected, contextCompany.getCompanyId(), null,
			contactsSelected, null);

		_analyticsSettingsManager.updateCompanyConfiguration(
			contextCompany.getCompanyId(),
			HashMapBuilder.<String, Object>put(
				"syncAllAccounts", contactConfiguration.getSyncAllAccounts()
			).put(
				"syncAllContacts", contactConfiguration.getSyncAllContacts()
			).put(
				"syncedAccountGroupIds",
				contactConfiguration.getSyncedAccountGroupIds()
			).put(
				"syncedOrganizationIds",
				contactConfiguration.getSyncedOrganizationIds()
			).put(
				"syncedUserGroupIds",
				contactConfiguration.getSyncedUserGroupIds()
			).build());
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

	@Reference
	private ContactAccountGroupDTOConverter _contactAccountGroupDTOConverter;

	@Reference
	private ContactOrganizationDTOConverter _contactOrganizationDTOConverter;

	@Reference
	private ContactUserGroupDTOConverter _contactUserGroupDTOConverter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}