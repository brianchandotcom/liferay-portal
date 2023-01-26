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

package com.liferay.analytics.settings.internal.configuration.persistence.listener;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	property = "model.class.name=com.liferay.analytics.settings.configuration.AnalyticsConfiguration",
	service = ConfigurationModelListener.class
)
public class AnalyticsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationRegistry.getAnalyticsConfiguration(pid);

		String[] commerceSyncEnabledAnalyticsChannelIds =
			analyticsConfiguration.commerceSyncEnabledAnalyticsChannelIds();

		if (commerceSyncEnabledAnalyticsChannelIds == null) {
			commerceSyncEnabledAnalyticsChannelIds = new String[0];
		}

		properties.put(
			"previousCommerceSyncEnabledAnalyticsChannelIds",
			commerceSyncEnabledAnalyticsChannelIds);

		properties.put(
			"previousSyncAllAccounts",
			analyticsConfiguration.syncAllAccounts());

		properties.put(
			"previousSyncAllContacts",
			analyticsConfiguration.syncAllContacts());

		String[] syncedAccountFieldNames =
			analyticsConfiguration.syncedAccountFieldNames();

		if (!ArrayUtil.isEmpty(syncedAccountFieldNames)) {
			properties.put(
				"previousSyncedAccountFieldNames", syncedAccountFieldNames);
		}

		String[] syncedAccountGroupIds =
			analyticsConfiguration.syncedAccountGroupIds();

		if (!ArrayUtil.isEmpty(syncedAccountGroupIds)) {
			properties.put(
				"previousSyncedAccountGroupIds", syncedAccountGroupIds);
		}

		String[] syncedCommerceChannelIds =
			analyticsConfiguration.syncedCommerceChannelIds();

		if (syncedCommerceChannelIds == null) {
			syncedCommerceChannelIds = new String[0];
		}

		properties.put(
			"previousSyncedCommerceChannelIds", syncedCommerceChannelIds);

		String[] syncedContactFieldNames =
			analyticsConfiguration.syncedContactFieldNames();

		if (!ArrayUtil.isEmpty(syncedContactFieldNames)) {
			properties.put(
				"previousSyncedContactFieldNames", syncedContactFieldNames);
		}

		String[] syncedOrderFieldNames =
			analyticsConfiguration.syncedOrderFieldNames();

		if (!ArrayUtil.isEmpty(syncedOrderFieldNames)) {
			properties.put(
				"previousSyncedOrderFieldNames", syncedOrderFieldNames);
		}

		String[] syncedOrganizationIds =
			analyticsConfiguration.syncedOrganizationIds();

		if (!ArrayUtil.isEmpty(syncedOrderFieldNames)) {
			properties.put(
				"previousSyncedOrganizationIds", syncedOrganizationIds);
		}

		String[] syncedProductFieldNames =
			analyticsConfiguration.syncedProductFieldNames();

		if (!ArrayUtil.isEmpty(syncedProductFieldNames)) {
			properties.put(
				"previousSyncedProductFieldNames", syncedProductFieldNames);
		}

		String[] syncedUserFieldNames =
			analyticsConfiguration.syncedUserFieldNames();

		if (!ArrayUtil.isEmpty(syncedUserFieldNames)) {
			properties.put(
				"previousSyncedUserFieldNames", syncedUserFieldNames);
		}

		String[] syncedUserGroupIds =
			analyticsConfiguration.syncedUserGroupIds();

		if (!ArrayUtil.isEmpty(syncedProductFieldNames)) {
			properties.put("previousSyncedUserGroupIds", syncedUserGroupIds);
		}

		String token = analyticsConfiguration.token();

		if (token != null) {
			properties.put("previousToken", token);
		}
	}

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

}