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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.ColorSchemeFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.impl.ColorSchemeImpl;

/**
 * @author Vilmos Papp
 */
public class ColorSchemeFactoryImpl implements ColorSchemeFactory {

	@Override
	public ColorScheme getColorScheme() {
		return new ColorSchemeImpl();
	}

	@Override
	public ColorScheme getColorScheme(String colorSchemeId) {
		return new ColorSchemeImpl(colorSchemeId);
	}

	@Override
	public ColorScheme getColorScheme(
		String colorSchemeId, String name, String cssClass) {

		return new ColorSchemeImpl(colorSchemeId, name, cssClass);
	}

	@Override
	public ColorScheme getDefaultRegularColorScheme() {
		return new ColorSchemeImpl(
			getDefaultRegularColorSchemeId(), StringPool.BLANK,
			StringPool.BLANK);
	}

	@Override
	public String getDefaultRegularColorSchemeId() {
		return PropsValues.DEFAULT_REGULAR_COLOR_SCHEME_ID;
	}

	@Override
	public ColorScheme getDefaultWapColorScheme() {
		return new ColorSchemeImpl(
			getDefaultWapColorSchemeId(), StringPool.BLANK, StringPool.BLANK);
	}

	@Override
	public String getDefaultWapColorSchemeId() {
		return PropsValues.DEFAULT_WAP_COLOR_SCHEME_ID;
	}

}