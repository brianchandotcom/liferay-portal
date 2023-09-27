/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Iván Zaera
 */
public class ParameterMapSettingsLocator implements SettingsLocator {

	public ParameterMapSettingsLocator(
		Map<String, String[]> parameterMap, SettingsLocator settingsLocator) {

		this(parameterMap, null, settingsLocator);
	}

	public ParameterMapSettingsLocator(
		Map<String, String[]> parameterMap, String parameterNamePrefix,
		SettingsLocator settingsLocator) {

		_parameterMap = parameterMap;
		_parameterNamePrefix = parameterNamePrefix;
		_settingsLocator = settingsLocator;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		ParameterMapSettings parameterMapSettings = new ParameterMapSettings(
			_parameterMap, _settingsLocator.getSettings());

		if (Validator.isNotNull(_parameterNamePrefix)) {
			parameterMapSettings.setParameterNamePrefix(_parameterNamePrefix);
		}

		return parameterMapSettings;
	}

	@Override
	public String getSettingsId() {
		return _settingsLocator.getSettingsId();
	}

	private final Map<String, String[]> _parameterMap;
	private final String _parameterNamePrefix;
	private final SettingsLocator _settingsLocator;

}