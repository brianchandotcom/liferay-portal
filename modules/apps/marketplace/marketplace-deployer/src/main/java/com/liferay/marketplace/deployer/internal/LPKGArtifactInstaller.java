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

package com.liferay.marketplace.deployer.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.io.File;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.felix.fileinstall.ArtifactInstaller;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class LPKGArtifactInstaller implements ArtifactInstaller {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public boolean canHandle(File file) {
		String name = StringUtil.toLowerCase(file.getName());

		return name.endsWith(".lpkg");
	}

	@Override
	public void install(File file) throws Exception {
		Properties properties = _readMarketplaceProperties(file);

		if (GetterUtil.getBoolean(
				properties.getProperty("restart-required"), true)) {

			return;
		}

		for (Bundle bundle : _lpkgDeployer.deploy(_bundleContext, file)) {
			Dictionary<String, String> headers = bundle.getHeaders();

			String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

			if (fragmentHost != null) {
				continue;
			}

			try {
				bundle.start();
			}
			catch (BundleException be) {
				_log.error("Unable to start " + bundle + " for " + file, be);
			}
		}
	}

	@Override
	public void uninstall(File file) throws Exception {
		Bundle bundle = _bundleContext.getBundle(file.getCanonicalPath());

		if (bundle != null) {
			bundle.uninstall();
		}
	}

	@Override
	public void update(File file) throws Exception {
		Properties properties = _readMarketplaceProperties(file);

		if (GetterUtil.getBoolean(
				properties.getProperty("restart-required"), true)) {

			return;
		}

		Bundle bundle = _bundleContext.getBundle(file.getCanonicalPath());

		if (bundle != null) {
			Version currentVersion = bundle.getVersion();
			Version newVersion = new Version(properties.getProperty("version"));

			if (newVersion.compareTo(currentVersion) > 0) {
				bundle.update(_lpkgDeployer.toBundle(file));
			}
		}
	}

	private Properties _readMarketplaceProperties(File file) {
		try (ZipFile zipFile = new ZipFile(file)) {
			ZipEntry zipEntry = zipFile.getEntry(
				"liferay-marketplace.properties");

			if (zipEntry == null) {
				return null;
			}

			Properties properties = new Properties();

			try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
				properties.load(inputStream);
			}

			return properties;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to read liferay-marketplace.properties from " +
						file.getName(),
					e);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGArtifactInstaller.class);

	private BundleContext _bundleContext;

	@Reference
	private LPKGDeployer _lpkgDeployer;

}