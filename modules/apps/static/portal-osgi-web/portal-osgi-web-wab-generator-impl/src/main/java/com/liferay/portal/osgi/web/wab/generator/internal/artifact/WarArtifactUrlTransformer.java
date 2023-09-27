/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @author Gregory Amerson
 */
public class WarArtifactUrlTransformer implements FileInstaller {

	@Override
	public boolean canTransformURL(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".war") ||
			(name.endsWith(".zip") && _isClientExtensionZip(artifact))) {

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

	private boolean _isClientExtensionZip(File artifact) {
		try (ZipFile zipFile = new ZipFile(artifact)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (Objects.equals(
						name, "WEB-INF/liferay-plugin-package.properties") ||
					(name.endsWith(".client-extension-config.json") &&
					 (name.indexOf("/") == -1))) {

					return true;
				}
			}

			return false;
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to check if " + artifact + " is a client extension",
				ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WarArtifactUrlTransformer.class);

}