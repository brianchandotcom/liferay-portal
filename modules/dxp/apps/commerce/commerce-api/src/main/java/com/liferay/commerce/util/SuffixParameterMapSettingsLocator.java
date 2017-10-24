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