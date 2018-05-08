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

package com.liferay.users.admin.web.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class UpgradeFileUploadsConfiguration extends UpgradeProcess {

	public UpgradeFileUploadsConfiguration(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeFileUploadsConfiguration();
	}

	protected void upgradeFileUploadsConfiguration() throws Exception {
		UserFileUploadsConfiguration defaultConfig =
			ConfigurableUtil.createConfigurable(
				UserFileUploadsConfiguration.class, new HashMapDictionary<>());

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"imageCheckToken",
			_prefsProps.getBoolean(
				_OLD_KEY_USERS_IMAGE_CHECK_TOKEN,
				defaultConfig.imageCheckToken()));
		properties.put(
			"imageMaxHeight",
			_prefsProps.getInteger(
				_OLD_KEY_USERS_IMAGE_MAX_HEIGHT,
				defaultConfig.imageMaxHeight()));
		properties.put(
			"imageMaxSize",
			_prefsProps.getLong(
				_OLD_KEY_USERS_IMAGE_MAX_SIZE, defaultConfig.imageMaxSize()));
		properties.put(
			"imageMaxWidth",
			_prefsProps.getInteger(
				_OLD_KEY_USERS_IMAGE_MAX_WIDTH, defaultConfig.imageMaxWidth()));

		Configuration configuration = _configurationAdmin.getConfiguration(
			UserFileUploadsConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		portletPreferences.reset(_OLD_KEY_USERS_IMAGE_CHECK_TOKEN);
		portletPreferences.reset(_OLD_KEY_USERS_IMAGE_MAX_HEIGHT);
		portletPreferences.reset(_OLD_KEY_USERS_IMAGE_MAX_SIZE);
		portletPreferences.reset(_OLD_KEY_USERS_IMAGE_MAX_WIDTH);
	}

	private static final String _OLD_KEY_USERS_IMAGE_CHECK_TOKEN =
		"users.image.check.token";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_HEIGHT =
		"users.image.max.height";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_SIZE =
		"users.image.max.size";

	private static final String _OLD_KEY_USERS_IMAGE_MAX_WIDTH =
		"users.image.max.width";

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}