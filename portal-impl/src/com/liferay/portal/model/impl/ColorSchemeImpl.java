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

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ColorSchemeImpl implements ColorScheme {

	public ColorSchemeImpl() {
		this(null, null, null);
	}

	public ColorSchemeImpl(String colorSchemeId) {
		this(colorSchemeId, null, null);
	}

	public ColorSchemeImpl(String colorSchemeId, String name, String cssClass) {
		_colorSchemeId = colorSchemeId;
		_name = name;
		_cssClass = cssClass;
	}

	@Override
	public int compareTo(ColorScheme colorScheme) {
		return getName().compareTo(colorScheme.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ColorScheme)) {
			return false;
		}

		ColorScheme colorScheme = (ColorScheme)obj;

		String colorSchemeId = colorScheme.getColorSchemeId();

		if (getColorSchemeId().equals(colorSchemeId)) {
			return true;
		}

		return false;
	}

	@Override
	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	@Override
	public String getColorSchemeImagesPath() {
		return _colorSchemeImagesPath;
	}

	@Override
	public String getColorSchemeThumbnailPath() {

		// LEP-5270

		if (Validator.isNotNull(_cssClass) &&
			Validator.isNotNull(_colorSchemeImagesPath)) {

			int pos = _cssClass.indexOf(CharPool.SPACE);

			if (pos > 0) {
				if (_colorSchemeImagesPath.endsWith(
						_cssClass.substring(0, pos))) {

					String subclassPath = StringUtil.replace(
						_cssClass, CharPool.SPACE, CharPool.SLASH);

					return _colorSchemeImagesPath + subclassPath.substring(pos);
				}
			}
		}

		return _colorSchemeImagesPath;
	}

	@Override
	public String getCssClass() {
		return _cssClass;
	}

	@Override
	public boolean getDefaultCs() {
		return _defaultCs;
	}

	@Override
	public String getName() {
		if (Validator.isNull(_name)) {
			return _colorSchemeId;
		}

		return _name;
	}

	@Override
	public String getSetting(String key) {
		//return _settingsProperties.getProperty(key);

		// FIX ME

		if (key.endsWith("-bg")) {
			return "#FFFFFF";
		}

		return "#000000";
	}

	@Override
	public String getSettings() {
		return PropertiesUtil.toString(_settingsProperties);
	}

	@Override
	public Properties getSettingsProperties() {
		return _settingsProperties;
	}

	@Override
	public int hashCode() {
		return _colorSchemeId.hashCode();
	}

	@Override
	public boolean isDefaultCs() {
		return _defaultCs;
	}

	@Override
	public void setColorSchemeImagesPath(String colorSchemeImagesPath) {
		_colorSchemeImagesPath = colorSchemeImagesPath;
	}

	@Override
	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	@Override
	public void setDefaultCs(boolean defaultCs) {
		_defaultCs = defaultCs;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties.clear();

		try {
			PropertiesUtil.load(_settingsProperties, settings);

			PropertiesUtil.trimKeys(_settingsProperties);
		}
		catch (IOException ioe) {
			_log.error("Unable to load colors cheme properties", ioe);
		}
	}

	@Override
	public void setSettingsProperties(Properties settingsProperties) {
		_settingsProperties = settingsProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ColorSchemeImpl.class);

	private final String _colorSchemeId;
	private String _colorSchemeImagesPath =
		"${images-path}/color_schemes/${css-class}";
	private String _cssClass;
	private boolean _defaultCs;
	private String _name;
	private Properties _settingsProperties = new SafeProperties();

}