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
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
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
			_analyticsConfigurationTracker.getAnalyticsConfiguration(pid);

		properties.put(
			"previousSyncAllContacts",
			analyticsConfiguration.syncAllContacts());

		String[] syncedContactFieldNames =
			analyticsConfiguration.syncedContactFieldNames();

		if (!ArrayUtil.isEmpty(syncedContactFieldNames)) {
			properties.put(
				"previousSyncedContactFieldNames", syncedContactFieldNames);
		}

		String[] syncedUserFieldNames =
			analyticsConfiguration.syncedUserFieldNames();

		if (!ArrayUtil.isEmpty(syncedUserFieldNames)) {
			properties.put(
				"previousSyncedUserFieldNames", syncedUserFieldNames);
		}

		String token = analyticsConfiguration.token();

		if (token != null) {
			properties.put("previousToken", token);
		}
	}

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

}