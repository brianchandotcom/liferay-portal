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

package com.liferay.portal.bootstrap;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

import org.eclipse.osgi.container.ModuleReadHook;

/**
 * @author Matthew Tambara
 */
public class ModuleReadHookImpl implements ModuleReadHook {

	@Override
	public void process(long bundleId, String location) {
		if ((bundleId == 0) || location.startsWith("webbundle")) {
			return;
		}

		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_STATE_DIR, "org.eclipse.osgi",
			String.valueOf(bundleId), "0/bundleFile");

		File bundleFile = path.toFile();

		if (bundleFile.exists()) {
			return;
		}

		try {
			if (location.startsWith("file")) {
				URL url = new URL(location);

				File file = new File(url.getPath());

				Files.copy(file.toPath(), path);
			}
			else {
				Matcher matcher = _pattern.matcher(location);

				if (matcher.find()) {
					try (ZipFile zipFile = new ZipFile(matcher.group(2));
						InputStream inputStream = zipFile.getInputStream(
							zipFile.getEntry(matcher.group(1)))) {

						Files.copy(inputStream, path);
					}
				}
				else if (_log.isWarnEnabled()) {
					_log.warn("No bundle found for location " + location);
				}
			}
		}
		catch (IOException ioe) {
			_log.error(
				StringBundler.concat(
					"Unable to copy from ", location, " to ", path),
				ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleReadHookImpl.class);

	private static final Pattern _pattern = Pattern.compile(
		"(.*\\.jar).*lpkgPath=([^&]*)");

}