/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
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

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId)) {

			_companyId = companyId;
			_pid = pid;

			ModifiableSettings modifiableSettings = _getModifiableSettings();

			_initialProperties = new HashMapDictionary<>();

			Enumeration<String> keysEnumeration = properties.keys();

			while (keysEnumeration.hasMoreElements()) {
				String key = keysEnumeration.nextElement();

				String[] values = modifiableSettings.getValues(key, null);

				if (values != null) {
					_initialProperties.put(key, values);
				}
				else {
					_initialProperties.put(
						key, modifiableSettings.getValue(key, null));
				}

				_setValue(modifiableSettings, key, properties.get(key));
			}

			modifiableSettings.store();
		}
	}

	@Override
	public void close() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_companyId)) {

			ModifiableSettings modifiableSettings = _getModifiableSettings();

			Enumeration<String> keysEnumeration = _initialProperties.keys();

			while (keysEnumeration.hasMoreElements()) {
				String key = keysEnumeration.nextElement();

				_setValue(modifiableSettings, key, _initialProperties.get(key));
			}

			modifiableSettings.store();
		}
	}

	private ModifiableSettings _getModifiableSettings()
		throws SettingsException {

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new CompanyServiceSettingsLocator(_companyId, _pid));

		return settings.getModifiableSettings();
	}

	private void _setValue(
		ModifiableSettings modifiableSettings, String key, Object value) {

		if (value == null) {
			modifiableSettings.setValue(key, null);
		}
		else if (value instanceof String[]) {
			modifiableSettings.setValues(key, (String[])value);
		}
		else {
			modifiableSettings.setValue(key, String.valueOf(value));
		}
	}

	private final long _companyId;
	private final Dictionary<String, Object> _initialProperties;
	private final String _pid;

}