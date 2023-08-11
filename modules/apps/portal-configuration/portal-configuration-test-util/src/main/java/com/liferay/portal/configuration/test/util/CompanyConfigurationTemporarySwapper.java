/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util;

import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.Enumeration;

/**
 * @author Cristina González
 */
public class CompanyConfigurationTemporarySwapper implements AutoCloseable {

	public CompanyConfigurationTemporarySwapper(
			long companyId, String pid, Dictionary<String, Object> properties)
		throws Exception {

		_companyId = companyId;
		_pid = pid;

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new CompanyServiceSettingsLocator(_companyId, _pid));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		_initialProperties = new HashMapDictionary();

		Enumeration<String> keysEnumeration = properties.keys();

		while (keysEnumeration.hasMoreElements()) {
			String key = keysEnumeration.nextElement();

			_initialProperties.put(key, modifiableSettings.getValue(key, null));

			Object value = properties.get(key);

			if (value instanceof String[]) {
				modifiableSettings.setValues(key, (String[])value);
			}
			else {
				modifiableSettings.setValue(key, String.valueOf(value));
			}
		}

		modifiableSettings.store();
	}

	@Override
	public void close() throws Exception {
		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new CompanyServiceSettingsLocator(_companyId, _pid));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		Enumeration<String> keysEnumeration = _initialProperties.keys();

		while (keysEnumeration.hasMoreElements()) {
			String key = keysEnumeration.nextElement();

			modifiableSettings.setValue(
				key, String.valueOf(_initialProperties.get(key)));
		}

		modifiableSettings.store();
	}

	private final long _companyId;
	private final Dictionary<String, Object> _initialProperties;
	private final String _pid;

}