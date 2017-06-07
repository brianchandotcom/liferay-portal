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

package com.liferay.portal.upload.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upload.configuration.UploadServletRequestConfiguration;
import com.liferay.portal.upload.constants.LegacyUploadServletRequestPropsKeys;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeUploadServletRequestConfiguration extends UpgradeProcess {

	public UpgradeUploadServletRequestConfiguration(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			UploadServletRequestConfiguration.class.getName(),
			StringPool.QUESTION);

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyUploadServletRequestPropsKeys.
						UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE))) {

			properties.put(
				"maxSize",
				_prefsProps.getLong(
					LegacyUploadServletRequestPropsKeys.
						UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyUploadServletRequestPropsKeys.
						UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR))) {

			properties.put(
				"tempDir",
				_prefsProps.getString(
					LegacyUploadServletRequestPropsKeys.
						UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR));
		}

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		for (String key : LegacyUploadServletRequestPropsKeys.KEYS) {
			portletPreferences.reset(key);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}