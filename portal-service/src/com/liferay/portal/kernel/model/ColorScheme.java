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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.Accessor;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public interface ColorScheme extends Comparable<ColorScheme>, Serializable {

	public static final Accessor<ColorScheme, String> NAME_ACCESSOR =
		new Accessor<ColorScheme, String>() {

			@Override
			public String get(ColorScheme colorScheme) {
				return colorScheme.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<ColorScheme> getTypeClass() {
				return ColorScheme.class;
			}

		};

	public String getColorSchemeId();

	public String getColorSchemeImagesPath();

	public String getColorSchemeThumbnailPath();

	public String getCssClass();

	public boolean getDefaultCs();

	public String getName();

	public String getSetting(String key);

	public String getSettings();

	public Properties getSettingsProperties();

	public boolean isDefaultCs();

	public void setColorSchemeImagesPath(String colorSchemeImagesPath);

	public void setCssClass(String cssClass);

	public void setDefaultCs(boolean defaultCs);

	public void setName(String name);

	public void setSettings(String settings);

	public void setSettingsProperties(Properties settingsProperties);

}