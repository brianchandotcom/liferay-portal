/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.net.URI;
import java.net.URL;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class JasperVersionDetector {

	public static String getJasperVersion() {
		if (_jasperVersion != null) {
			return _jasperVersion;
		}

		URL url = JasperVersionDetector.class.getResource(
			"/org/apache/jasper/JasperException.class");

		if (url == null) {
			_jasperVersion = StringPool.BLANK;

			return _jasperVersion;
		}

		String path = url.getPath();

		int pos = path.indexOf(CharPool.EXCLAMATION);

		if (pos == -1) {
			_jasperVersion = StringPool.BLANK;

			return _jasperVersion;
		}

		try {
			URI jarFileURI = new URI(path.substring(0, pos));

			JarFile jarFile = new JarFile(new File(jarFileURI));

			Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			_jasperVersion = GetterUtil.getString(
				attributes.getValue("Specification-Version"));
		}
		catch (Exception e) {
			_jasperVersion = StringPool.BLANK;
		}

		return _jasperVersion;
	}

	private static String _jasperVersion;

}