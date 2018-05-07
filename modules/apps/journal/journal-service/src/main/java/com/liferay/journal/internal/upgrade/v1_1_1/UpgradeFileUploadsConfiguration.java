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

package com.liferay.journal.internal.upgrade.v1_1_1;

import com.liferay.journal.configuration.JournalFileUploadsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;

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
		JournalFileUploadsConfiguration defaultConfig =
			ConfigurableUtil.createConfigurable(
				JournalFileUploadsConfiguration.class,
				new HashMapDictionary<>());

		Dictionary properties = new HashMapDictionary();

		properties.put(
			"imageExtensions",
			_prefsProps.getStringArray(
				_OLD_KEY_JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA,
				defaultConfig.imageExtensions()));
		properties.put(
			"largeImageMaxSize",
			_prefsProps.getLong(
				_OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE,
				defaultConfig.smallImageMaxSize()));

		Configuration configuration = _configurationAdmin.getConfiguration(
			JournalFileUploadsConfiguration.class.getName(),
			StringPool.QUESTION);

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		portletPreferences.reset(_OLD_KEY_JOURNAL_IMAGE_EXTENSIONS);
		portletPreferences.reset(_OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE);
	}

	private static final String _OLD_KEY_JOURNAL_IMAGE_EXTENSIONS =
		"journal.image.extensions";

	private static final String _OLD_KEY_JOURNAL_IMAGE_SMALL_MAX_SIZE =
		"journal.image.small.max.size";

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}