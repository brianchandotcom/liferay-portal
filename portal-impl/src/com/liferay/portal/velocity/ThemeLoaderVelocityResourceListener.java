/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeLoaderVelocityResourceListener
	extends VelocityResourceListener {

	@Override
	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		try {
			return doGetResourceStream(source);
		}
		catch (Exception e) {
			throw new ResourceNotFoundException(source);
		}
	}

	protected InputStream doGetResourceStream(String source) throws Exception {
		int pos = source.indexOf(THEME_LOADER_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String servletContextName = source.substring(0, pos);

		ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
			servletContextName);

		if (themeLoader == null) {
			_log.error(
				source + " is not valid because " + servletContextName +
					" does not map to a theme loader");

			return null;
		}

		String name = source.substring(pos + THEME_LOADER_SEPARATOR.length());

		String themesPath = themeLoader.getThemesPath();

		if (name.startsWith(themesPath)) {
			name = name.substring(themesPath.length());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				name + " is associated with the theme loader " +
					servletContextName + " " + themeLoader);
		}

		File fileStorage = themeLoader.getFileStorage();

		return new FileInputStream(fileStorage.getPath() + name);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeLoaderVelocityResourceListener.class);

}