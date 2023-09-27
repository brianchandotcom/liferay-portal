/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;

import java.io.IOException;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Vilmos Papp
 */
public class ColorSchemeFactoryUtil {

	public static ColorScheme getColorScheme() {
		return new ColorSchemeImpl();
	}

	public static ColorScheme getColorScheme(String colorSchemeId) {
		return new ColorSchemeImpl(colorSchemeId);
	}

	public static ColorScheme getColorScheme(
		String colorSchemeId, String name, String cssClass) {

		return new ColorSchemeImpl(colorSchemeId, name, cssClass);
	}

	public static ColorScheme getDefaultRegularColorScheme() {
		return new ColorSchemeImpl(
			getDefaultRegularColorSchemeId(), StringPool.BLANK,
			StringPool.BLANK);
	}

	public static String getDefaultRegularColorSchemeId() {
		return PropsUtil.get(PropsKeys.DEFAULT_REGULAR_COLOR_SCHEME_ID);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ColorSchemeFactoryUtil.class);

	private static class ColorSchemeImpl implements ColorScheme {

		public ColorSchemeImpl() {
			this(null, null, null);
		}

		public ColorSchemeImpl(String colorSchemeId) {
			this(colorSchemeId, null, null);
		}

		public ColorSchemeImpl(
			String colorSchemeId, String name, String cssClass) {

			_colorSchemeId = colorSchemeId;
			_name = name;
			_cssClass = cssClass;
		}

		@Override
		public int compareTo(ColorScheme colorScheme) {
			return getName().compareTo(colorScheme.getName());
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof ColorScheme)) {
				return false;
			}

			ColorScheme colorScheme = (ColorScheme)object;

			if (Objects.equals(
					getColorSchemeId(), colorScheme.getColorSchemeId())) {

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

				if ((pos > 0) &&
					_colorSchemeImagesPath.endsWith(
						_cssClass.substring(0, pos))) {

					String subclassPath = StringUtil.replace(
						_cssClass, CharPool.SPACE, CharPool.SLASH);

					return _colorSchemeImagesPath + subclassPath.substring(pos);
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
			catch (IOException ioException) {
				_log.error(
					"Unable to load colors cheme properties", ioException);
			}
		}

		@Override
		public void setSettingsProperties(Properties settingsProperties) {
			_settingsProperties = settingsProperties;
		}

		private final String _colorSchemeId;
		private String _colorSchemeImagesPath =
			"${images-path}/color_schemes/${css-class}";
		private String _cssClass;
		private boolean _defaultCs;
		private String _name;
		private Properties _settingsProperties = new SafeProperties();

	}

}