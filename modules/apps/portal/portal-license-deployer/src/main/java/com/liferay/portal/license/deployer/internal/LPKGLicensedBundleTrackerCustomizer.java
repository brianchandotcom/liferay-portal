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

package com.liferay.portal.license.deployer.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.felix.fileinstall.ArtifactInstaller;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class LPKGLicensedBundleTrackerCustomizer
	implements BundleTrackerCustomizer<Bundle> {

	public LPKGLicensedBundleTrackerCustomizer(
		ArtifactInstaller licenseInstaller) {

		_licenseInstaller = licenseInstaller;
	}

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		URL url = bundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return null;
		}

		File file = new File(bundle.getLocation());

		if (!file.exists()) {
			return null;
		}

		boolean hasLicense = false;

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();

			while (zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement();

				String zipEntryName = zipEntry.getName();

				if (!zipEntryName.endsWith(".xml")) {
					continue;
				}

				Path tempFilePath = Files.createTempFile(null, ".xml");

				try (InputStream inputStream = zipFile.getInputStream(
						zipEntry)) {

					Files.copy(
						inputStream, tempFilePath,
						StandardCopyOption.REPLACE_EXISTING);

					File tempFile = tempFilePath.toFile();

					if (_licenseInstaller.canHandle(tempFile)) {
						_licenseInstaller.install(tempFile);

						hasLicense = true;
					}
				}
				finally {
					Files.delete(tempFilePath);
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to register license", e);

			return null;
		}

		if (hasLicense) {
			return bundle;
		}

		return null;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGLicensedBundleTrackerCustomizer.class);

	private final ArtifactInstaller _licenseInstaller;

}