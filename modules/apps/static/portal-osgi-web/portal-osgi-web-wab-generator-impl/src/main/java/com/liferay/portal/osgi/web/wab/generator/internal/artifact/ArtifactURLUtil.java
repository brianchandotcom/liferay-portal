/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Constants;

/**
 * @author Matthew Tambara
 * @author Raymond Augé
 * @author Gregory Amerson
 */
public class ArtifactURLUtil {

	public static String getClientExtensionSymbolicName(String path) {
		int x = path.lastIndexOf('/');
		int y = path.lastIndexOf(CharPool.PERIOD);

		return path.substring(x + 1, y);
	}

	public static String getSymbolicName(String path) {
		int x = path.lastIndexOf('/');
		int y = path.lastIndexOf(CharPool.PERIOD);

		String symbolicName = path.substring(x + 1, y);

		Matcher matcher = _pattern.matcher(symbolicName);

		if (matcher.matches()) {
			symbolicName = matcher.group(1);
		}

		return symbolicName;
	}

	public static URL transform(URL artifact) throws Exception {
		String contextName = null;

		String path = artifact.getPath();

		String fileExtension = path.substring(
			path.lastIndexOf(CharPool.PERIOD) + 1);

		if (fileExtension.equals("war")) {
			try (ZipFile zipFile = new ZipFile(new File(artifact.toURI()))) {
				contextName = _readServletContextName(zipFile);
			}
		}

		String symbolicName = getSymbolicName(path);

		if (fileExtension.equals("zip") && _isClientExtensionZip(path)) {
			symbolicName = getClientExtensionSymbolicName(path);
		}

		if (contextName == null) {
			contextName = symbolicName;
		}

		return new URL(
			"webbundle", null,
			StringBundler.concat(
				artifact.getPath(), "?", Constants.BUNDLE_SYMBOLICNAME, "=",
				symbolicName, "&Web-ContextPath=/", contextName,
				"&fileExtension=", fileExtension, "&protocol=file"));
	}

	private static boolean _isClientExtensionZip(String path) {
		try (ZipFile zipFile = new ZipFile(path)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith(".client-extension-config.json") &&
					(name.indexOf("/") == -1)) {

					return true;
				}
			}
		}
		catch (IOException ioException) {
			_log.error("Path " + path + " is not a valid ZIP", ioException);
		}

		return false;
	}

	private static String _readServletContextName(ZipFile zipFile)
		throws Exception {

		ZipEntry zipEntry = zipFile.getEntry(
			"WEB-INF/liferay-plugin-package.properties");

		if (zipEntry == null) {
			return null;
		}

		Properties properties = new Properties();

		try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
			properties.load(inputStream);
		}

		return properties.getProperty("servlet-context-name");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ArtifactURLUtil.class);

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-[0-9\\.]+)");

}