/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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