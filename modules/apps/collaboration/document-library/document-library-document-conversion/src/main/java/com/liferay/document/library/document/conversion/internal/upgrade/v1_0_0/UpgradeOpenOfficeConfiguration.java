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
import com.liferay.document.library.document.conversion.constants.LegacyOpenOfficePropsKeys;
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

	@Override
	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			OpenOfficeConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyOpenOfficePropsKeys.OPENOFFICE_CACHE_ENABLED))) {

			properties.put(
				"cacheEnabled",
				_prefsProps.getBoolean(
					LegacyOpenOfficePropsKeys.OPENOFFICE_CACHE_ENABLED));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_ENABLED))) {

			properties.put(
				"serverEnabled",
				_prefsProps.getBoolean(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_ENABLED));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_HOST))) {

			properties.put(
				"serverHost",
				_prefsProps.getString(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_HOST));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_PORT))) {

			properties.put(
				"serverPort",
				_prefsProps.getInteger(
					LegacyOpenOfficePropsKeys.OPENOFFICE_SERVER_PORT));
		}

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		for (String key : LegacyOpenOfficePropsKeys.OPENOFFICE_KEYS) {
			portletPreferences.reset(key);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}