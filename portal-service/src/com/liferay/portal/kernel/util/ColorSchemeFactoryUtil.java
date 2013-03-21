/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.ColorScheme;

/**
 * @author Vilmos Papp
 */
public class ColorSchemeFactoryUtil {

	public static ColorScheme getColorScheme() {
		return getColorSchemeFactory().getColorScheme();
	}

	public static ColorScheme getColorScheme(String colorSchemeId) {
		return getColorSchemeFactory().getColorScheme(colorSchemeId);
	}

	public static ColorScheme getColorScheme(
		String colorSchemeId, String name, String cssClass) {

		return getColorSchemeFactory().getColorScheme(
			colorSchemeId, name, cssClass);
	}

	public static ColorSchemeFactory getColorSchemeFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			ColorSchemeFactoryUtil.class);

		return _colorSchemeFactory;
	}

	public static ColorScheme getDefaultRegularColorScheme() {
		return getColorSchemeFactory().getDefaultRegularColorScheme();
	}

	public static ColorScheme getDefaultWapColorScheme() {
		return getColorSchemeFactory().getDefaultWapColorScheme();
	}

	public void setColorSchemeFactory(ColorSchemeFactory colorSchemeFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_colorSchemeFactory = colorSchemeFactory;
	}

	private static ColorSchemeFactory _colorSchemeFactory;

}