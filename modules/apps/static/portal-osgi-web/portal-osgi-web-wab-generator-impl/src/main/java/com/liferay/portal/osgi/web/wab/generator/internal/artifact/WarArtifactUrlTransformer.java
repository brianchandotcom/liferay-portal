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

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @author Gregory Amerson
 */
public class WarArtifactUrlTransformer implements FileInstaller {

	public WarArtifactUrlTransformer(AtomicBoolean portalIsReady) {
		_portalIsReady = portalIsReady;
	}

	@Override
	public boolean canTransformURL(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".war")) {
			if (!_hasResources(artifact)) {
				return true;
			}

			return _portalIsReady.get();
		}

		if (name.endsWith(".zip") && _isClientExtensionZip(artifact)) {
			return true;
		}

		return false;
	}

	@Override
	public URL transformURL(File artifact) throws Exception {
		URI uri = artifact.toURI();

		return ArtifactURLUtil.transform(uri.toURL());
	}

	@Override
	public void uninstall(File file) {
	}

	private boolean _hasResources(File artifact) {
		try (ZipFile zipFile = new ZipFile(artifact)) {
			if ((zipFile.getEntry("WEB-INF/classes/resources-importer/") !=
					null) ||
				(zipFile.getEntry("WEB-INF/classes/templates-importer/") !=
					null)) {

				return true;
			}

			ZipEntry zipEntry = zipFile.getEntry(
				"WEB-INF/liferay-plugin-package.properties");

			if (zipEntry == null) {
				return false;
			}

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				Properties properties = new Properties();

				properties.load(inputStream);

				return Validator.isNotNull(
					properties.getProperty("resources-importer-external-dir"));
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to check resources in " + artifact, ioException);
		}

		return false;
	}

	private boolean _isClientExtensionZip(File artifact) {
		try (ZipFile zipFile = new ZipFile(artifact)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			boolean foundConfigJson = false;
			boolean foundStatic = false;

			while (enumeration.hasMoreElements() &&
				   (!foundConfigJson || !foundStatic)) {

				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("config.json") && (name.indexOf("/") == -1)) {
					foundConfigJson = true;
				}
				else if (name.startsWith("static/")) {
					foundStatic = true;
				}
			}

			if (foundConfigJson && foundStatic) {
				return true;
			}

			return false;
		}
		catch (IOException ioException) {
			_log.error("Unable to check resources in " + artifact, ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WarArtifactUrlTransformer.class);

	private final AtomicBoolean _portalIsReady;

}