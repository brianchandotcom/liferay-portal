/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class LiferaySyncCountsDisplay {

	public LiferaySyncCountsDisplay() {
	}

	public LiferaySyncCountsDisplay(
		FaroProject faroProject, String dataSourceId,
		LiferayProvider.ContactsConfiguration contactsConfiguration,
		ContactsEngineClient contactsEngineClient) {

		LiferayProvider.ContactsConfiguration allContactsContactsConfiguration =
			new LiferayProvider.ContactsConfiguration();

		allContactsContactsConfiguration.setEnableAllContacts(true);

		_allUsersCount = contactsEngineClient.getDataSourceDXPTotal(
			faroProject, dataSourceId, allContactsContactsConfiguration);

		if (ListUtil.isNotNull(contactsConfiguration.getOrganizations())) {
			LiferayProvider.ContactsConfiguration
				organizationsContactsConfiguration =
					new LiferayProvider.ContactsConfiguration();

			organizationsContactsConfiguration.setOrganizations(
				contactsConfiguration.getOrganizations());
			organizationsContactsConfiguration.setUserGroups(
				Collections.emptyList());

			_organizationsUsersCount =
				contactsEngineClient.getDataSourceDXPTotal(
					faroProject, dataSourceId,
					organizationsContactsConfiguration);
		}

		Results<Individual> results = contactsEngineClient.getIndividuals(
			faroProject, dataSourceId, false, 1, 0, null);

		_currentUsersCount = results.getTotal();

		_totalUsersCount = contactsEngineClient.getDataSourceDXPTotal(
			faroProject, dataSourceId, contactsConfiguration);

		if (ListUtil.isNotNull(contactsConfiguration.getUserGroups())) {
			LiferayProvider.ContactsConfiguration
				userGroupsContactsConfiguration =
					new LiferayProvider.ContactsConfiguration();

			userGroupsContactsConfiguration.setOrganizations(
				Collections.emptyList());
			userGroupsContactsConfiguration.setUserGroups(
				contactsConfiguration.getUserGroups());

			_userGroupsUsersCount = contactsEngineClient.getDataSourceDXPTotal(
				faroProject, dataSourceId, userGroupsContactsConfiguration);
		}
	}

	private long _allUsersCount;
	private long _currentUsersCount;
	private long _organizationsUsersCount;
	private long _totalUsersCount;
	private long _userGroupsUsersCount;

}