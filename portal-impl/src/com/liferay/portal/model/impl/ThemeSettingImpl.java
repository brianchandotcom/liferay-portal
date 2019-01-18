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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ThemeSetting;

import java.io.Serializable;

/**
 * @author Julio Camarero
 * @author Raymond Augé
 */
public class ThemeSettingImpl implements Serializable, ThemeSetting {

	public static String namespaceProperty(String device) {
		return _PROPERTY_NAMESPACE.concat(device);
	}

	public static String namespaceProperty(String device, String key) {
		return namespaceProperty(
			device
		).concat(
			StringPool.COLON
		).concat(
			key
		);
	}

	public ThemeSettingImpl(
		boolean configurable, String[] options, String script, String type,
		String value) {

		_configurable = configurable;
		_options = options;
		_script = script;
		_type = type;
		_value = value;
	}

	@Override
	public String[] getOptions() {
		return _options;
	}

	@Override
	public String getScript() {
		return _script;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public boolean isConfigurable() {
		return _configurable;
	}

	@Override
	public void setConfigurable(boolean configurable) {
		_configurable = configurable;
	}

	@Override
	public void setOptions(String[] options) {
		_options = options;
	}

	@Override
	public void setScript(String script) {
		_script = script;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	private static final String _PROPERTY_NAMESPACE = "lfr-theme:";

	private static final long serialVersionUID = 1L;

	private boolean _configurable;
	private String[] _options;
	private String _script;
	private String _type;
	private String _value;

}