/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.portal.kernel.settings.BaseSettings;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.settings.SettingsLocator;

import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class SuffixParameterMapSettingsLocator
	extends ParameterMapSettingsLocator {

	public SuffixParameterMapSettingsLocator(
		Map<String, String[]> parameterMap, String parameterNamePrefix,
		String parameterNameSuffix, SettingsLocator settingsLocator) {

		super(parameterMap, parameterNamePrefix, settingsLocator);

		_parameterMap = parameterMap;
		_parameterNamePrefix = parameterNamePrefix;
		_parameterNameSuffix = parameterNameSuffix;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		return new BaseSettings(super.getSettings()) {

			@Override
			protected String doGetValue(String key) {
				String[] values = doGetValues(key);

				if (values == null) {
					return null;
				}

				return values[0];
			}

			@Override
			protected String[] doGetValues(String key) {
				return _parameterMap.get(
					_parameterNamePrefix + key + _parameterNameSuffix);
			}

		};
	}

	private final Map<String, String[]> _parameterMap;
	private final String _parameterNamePrefix;
	private final String _parameterNameSuffix;

}