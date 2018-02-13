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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class WarArtifactUrlTransformer implements ArtifactUrlTransformer {

	public WarArtifactUrlTransformer(AtomicBoolean portalIsReady) {
		_portalIsReady = portalIsReady;
	}

	@Override
	public boolean canHandle(File artifact) {
		String name = artifact.getName();

		if (!name.endsWith(".war")) {
			return false;
		}

		if (!_hasResources(artifact)) {
			return true;
		}

		return _portalIsReady.get();
	}

	@Override
	public URL transform(URL artifact) throws Exception {
		return ArtifactURLUtil.transform(artifact);
	}

	private boolean _hasResources(File artifact) {
		try (ZipFile zipFile = new ZipFile(artifact)) {
			if (zipFile.getEntry("WEB-INF/classes/resources-importer/") !=
					null) {

				return true;
			}

			if (zipFile.getEntry("WEB-INF/classes/templates-importer/") !=
					null) {

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
		catch (IOException ioe) {
			_log.error("Unable to check resources in " + artifact, ioe);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WarArtifactUrlTransformer.class);

	private final AtomicBoolean _portalIsReady;

}