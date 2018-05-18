/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		_parameterNameSuffix = parameterNamePrefix;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		Settings settings = super.getSettings();

		return new BaseSettings(settings) {

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