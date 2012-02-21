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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

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
		return _instance._jasperVersion;
	}

	private JasperVersionDetector() {
		try {
			Class<?> clazz = getClass();

			URL url = clazz.getResource(
				"/org/apache/jasper/JasperException.class");

			if (url == null) {
				return;
			}

			String path = url.getPath();

			int pos = path.indexOf(CharPool.EXCLAMATION);

			if (pos == -1) {
				return;
			}

			URI jarFileURI = new URI(path.substring(0, pos));

			JarFile jarFile = new JarFile(new File(jarFileURI));

			Manifest manifest = jarFile.getManifest();

			Attributes attributes = manifest.getMainAttributes();

			String jasperVersion = GetterUtil.getString(
				attributes.getValue("Specification-Version"));

			if (Validator.isNull(jasperVersion) ||
				!Character.isDigit(jasperVersion.charAt(0))) {

				jasperVersion = GetterUtil.getString(
					attributes.getValue("Implementation-Version"));

				if (Validator.isNull(jasperVersion)) {
					jasperVersion = GetterUtil.getString(
						attributes.getValue("Bundle-Version"));
				}
			}

			if (Validator.isNotNull(jasperVersion)) {
				_jasperVersion = jasperVersion;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JasperVersionDetector.class);

	private static JasperVersionDetector _instance =
		new JasperVersionDetector();

	private String _jasperVersion = StringPool.BLANK;

}