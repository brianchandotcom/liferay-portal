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

package com.liferay.document.library.document.conversion.internal.upgrade.v1_0_0;

import com.liferay.document.library.document.conversion.configuration.OpenOfficeConfiguration;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeOpenOfficeConfiguration extends UpgradeProcess {

	public UpgradeOpenOfficeConfiguration(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			OpenOfficeConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		String[][] propertyMethodNamePairs = {
			{"openoffice.cache.enabled", "cacheEnabled"},
			{"openoffice.server.enabled", "serverEnabled"},
			{"openoffice.server.host", "serverHost"},
			{"openoffice.server.port", "serverPort"}
		};

		for (String[] propertyMethodNamePair : propertyMethodNamePairs) {
			String oldPropertyKey = propertyMethodNamePair[0];

			String oldPropertyValue = _prefsProps.getString(oldPropertyKey);

			if (Validator.isNotNull(oldPropertyValue)) {
				properties.put(propertyMethodNamePair[1], oldPropertyValue);

				portletPreferences.reset(oldPropertyKey);
			}
		}

		configuration.update(properties);
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}