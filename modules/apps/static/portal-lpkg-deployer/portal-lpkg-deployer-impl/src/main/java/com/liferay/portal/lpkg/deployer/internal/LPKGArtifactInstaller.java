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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.io.File;
import java.io.InputStream;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.felix.fileinstall.ArtifactInstaller;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.Version;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = ArtifactInstaller.class)
public class LPKGArtifactInstaller implements ArtifactInstaller {

	@Override
	public boolean canHandle(File file) {
		String name = StringUtil.toLowerCase(file.getName());

		return name.endsWith(".lpkg");
	}

	@Override
	public void install(File file) throws Exception {
		Properties properties = new Properties();

		List<File> lpkgFiles = ContainerLPKGUtil.deploy(
			file, _bundleContext, properties);

		if (lpkgFiles != null) {
			return;
		}

		String canonicalPath = LPKGLocationUtil.getLPKGLocation(file);

		Bundle existingBundle = _bundleContext.getBundle(canonicalPath);

		if (existingBundle != null) {
			_update(file, properties);
		}

		if (GetterUtil.getBoolean(
				properties.getProperty("restart-required"), true)) {

			if (existingBundle == null) {
				_logRestartRequired(canonicalPath);
			}

			return;
		}

		for (Bundle bundle : _lpkgDeployer.deploy(_bundleContext, file)) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			String fragmentHost = headers.get(Constants.FRAGMENT_HOST);

			if (fragmentHost != null) {
				continue;
			}

			try {
				bundle.start();
			}
			catch (BundleException be) {
				_log.error(
					StringBundler.concat(
						"Unable to start ", bundle, " for ", file),
					be);
			}
		}
	}

	@Override
	public void uninstall(File file) throws Exception {
		Bundle bundle = _bundleContext.getBundle(
			LPKGLocationUtil.getLPKGLocation(file));

		if (bundle != null) {
			bundle.uninstall();
		}
	}

	@Override
	public void update(File file) throws Exception {
		_update(file, _readMarketplaceProperties(file));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _logRestartRequired(String canonicalPath) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"The portal instance needs to be restarted to complete the " +
					"installation of " + canonicalPath);
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

	private FrameworkEvent _refreshBundles(Set<Bundle> bundles)
		throws Exception {

		Bundle systemBundle = _bundleContext.getBundle(0);

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			bundles,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		return defaultNoticeableFuture.get();
	}

	private void _update(File file, Properties properties) throws Exception {
		String canonicalPath = LPKGLocationUtil.getLPKGLocation(file);

		Bundle bundle = _bundleContext.getBundle(canonicalPath);

		if (bundle != null) {
			Version currentVersion = bundle.getVersion();

			Version newVersion = new Version(properties.getProperty("version"));

			if (newVersion.compareTo(currentVersion) > 0) {
				if (GetterUtil.getBoolean(
						properties.getProperty("restart-required"), true)) {

					_logRestartRequired(canonicalPath);

					return;
				}

				Map<Bundle, List<Bundle>> deployedLPKGBundles =
					_lpkgDeployer.getDeployedLPKGBundles();

				List<Bundle> installedBundles = deployedLPKGBundles.get(bundle);

				Set<Bundle> wrapperBundles = new HashSet<>();

				for (Bundle installedBundle : installedBundles) {
					Dictionary<String, String> headers = bundle.getHeaders(
						StringPool.BLANK);

					if (Boolean.getBoolean(headers.get("Wrapper-Bundle"))) {
						wrapperBundles.add(installedBundle);
					}
				}

				if (!wrapperBundles.isEmpty()) {
					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Refreshing ", wrapperBundles, " to update ",
								bundle));
					}

					FrameworkEvent frameworkEvent = _refreshBundles(
						wrapperBundles);

					if (frameworkEvent.getType() !=
							FrameworkEvent.PACKAGES_REFRESHED) {

						_log.error(
							StringBundler.concat(
								"Unable to refresh ", wrapperBundles,
								" because of framework event ", frameworkEvent),
							frameworkEvent.getThrowable());
					}
				}

				bundle.update(_lpkgDeployer.toBundle(file));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LPKGArtifactInstaller.class);

	private BundleContext _bundleContext;

	@Reference
	private LPKGDeployer _lpkgDeployer;

	@Reference(target = "(" + URLConstants.URL_HANDLER_PROTOCOL + "=webbundle)")
	private URLStreamHandlerService _urlStreamHandlerService;

}