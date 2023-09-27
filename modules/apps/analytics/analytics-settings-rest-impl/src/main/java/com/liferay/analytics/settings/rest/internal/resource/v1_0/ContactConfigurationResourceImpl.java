/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.internal.resource.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.dto.v1_0.ContactConfiguration;
import com.liferay.analytics.settings.rest.internal.client.AnalyticsCloudClient;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.analytics.settings.rest.resource.v1_0.ContactConfigurationResource;
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
	private AnalyticsCloudClient _analyticsCloudClient;

	@Reference
	private AnalyticsSettingsManager _analyticsSettingsManager;

}