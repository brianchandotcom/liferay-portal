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

package com.liferay.portal.kernel.settings;

import java.util.Map;

/**
 * @author Ivan Zaera
 */
public class ParameterMapSettingsLocator implements SettingsLocator {

	public ParameterMapSettingsLocator(
		Map<String, String[]> parameterMap, SettingsLocator settingsLocator) {

		_parameterMap = parameterMap;
		_settingsLocator = settingsLocator;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		Settings settings = _settingsLocator.getSettings();

		return new ParameterMapSettings(_parameterMap, settings);
	}

	@Override
	public String getSettingsId() {
		return _settingsLocator.getSettingsId();
	}

	private final Map<String, String[]> _parameterMap;
	private final SettingsLocator _settingsLocator;

}