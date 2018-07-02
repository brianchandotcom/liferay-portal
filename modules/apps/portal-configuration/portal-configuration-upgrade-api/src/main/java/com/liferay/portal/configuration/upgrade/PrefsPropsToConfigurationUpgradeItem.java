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

package com.liferay.portal.configuration.upgrade;

/**
 * @author     Drew Brokke
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class PrefsPropsToConfigurationUpgradeItem {

	public PrefsPropsToConfigurationUpgradeItem(
		String prefsPropsName, PrefsPropsValueType prefsPropsValueType,
		String configurationMethodName) {

		_prefsPropsName = prefsPropsName;
		_prefsPropsValueType = prefsPropsValueType;
		_configurationMethodName = configurationMethodName;
	}

	public PrefsPropsToConfigurationUpgradeItem(
		String prefsPropsName, String configurationMethodName) {

		this(
			prefsPropsName, PrefsPropsValueType.STRING,
			configurationMethodName);
	}

	public String getConfigurationMethodName() {
		return _configurationMethodName;
	}

	public String getPrefsPropsName() {
		return _prefsPropsName;
	}

	public PrefsPropsValueType getPrefsPropsValueType() {
		return _prefsPropsValueType;
	}

	private final String _configurationMethodName;
	private final String _prefsPropsName;
	private final PrefsPropsValueType _prefsPropsValueType;

}