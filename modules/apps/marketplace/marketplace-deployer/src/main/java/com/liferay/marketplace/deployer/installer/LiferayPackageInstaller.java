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

package com.liferay.marketplace.deployer.installer;

import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.AppLocalService;
import com.liferay.marketplace.service.ModuleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
public class LiferayPackageInstaller
	implements BundleTrackerCustomizer<Bundle> {

	public LiferayPackageInstaller(
		BundleContext bundleContext, AppLocalService appLocalService,
		ModuleLocalService moduleLocalService) {

		_bundleContext = bundleContext;
		_appLocalService = appLocalService;
		_moduleLocalService = moduleLocalService;
	}

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent event) {
		URL url = bundle.getEntry("liferay-marketplace.properties");

		if (url == null) {
			return null;
		}

		List<Bundle> bundles = new ArrayList<>();

		Enumeration<URL> jarEntries = bundle.findEntries("/", "*.jar", false);
		Enumeration<URL> warEntries = bundle.findEntries("/", "*.war", false);

		try {
			bundles.addAll(
				installArtifacts(
					jarEntries, new JarArtifactInstaller(_bundleContext)));
			bundles.addAll(
				installArtifacts(
					warEntries, new WARArtifactInstaller(_bundleContext)));

			for (Bundle installedBundle : bundles) {
				installedBundle.start();
			}

			_registerAppInMarketplace(url);
		}
		catch (Exception e) {
			bundles.add(bundle);

			_uninstallBundles(bundles);

			return null;
		}

		_installedLpkgFiles.put(bundle.getBundleId(), bundles);

		return bundle;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent event, Bundle object) {

		removedBundle(bundle, event, object);

		addingBundle(bundle, event);
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Bundle object) {
		List<Bundle> bundles = _installedLpkgFiles.get(object.getBundleId());

		_uninstallBundles(bundles);

		_installedLpkgFiles.remove(object.getBundleId());
	}

	protected List<Bundle> installArtifacts(
			Enumeration<URL> enumeration, ArtifactInstaller artifactIntaller)
		throws Exception {

		if (enumeration == null) {
			return Collections.emptyList();
		}

		List<Bundle> bundles = new ArrayList<>();

		while (enumeration.hasMoreElements()) {
			bundles.add(artifactIntaller.install(enumeration.nextElement()));
		}

		return bundles;
	}

	private void _registerAppInMarketplace(URL url)
		throws IOException, PortalException {

		Properties properties = PropertiesUtil.load(
			url.openStream(), StringPool.ISO_8859_1);

		long remoteAppId = GetterUtil.getLong(
			properties.getProperty("remote-app-id"));
		String version = properties.getProperty("version");

		if ((remoteAppId <= 0) || Validator.isNull(version)) {
			return;
		}

		String title = properties.getProperty("title");
		String description = properties.getProperty("description");
		String category = properties.getProperty("category");
		String iconURL = properties.getProperty("icon-url");

		App app = _appLocalService.updateApp(
			0, remoteAppId, title, description, category, iconURL, version,
			null);

		String[] bundles = StringUtil.split(properties.getProperty("bundles"));

		for (String bundle : bundles) {
			String[] bundleParts = StringUtil.split(bundle, StringPool.POUND);

			String bundleSymbolicName = bundleParts[0];
			String bundleVersion = bundleParts[1];
			String contextName = bundleParts[2];

			_moduleLocalService.addModule(
				0, app.getAppId(), bundleSymbolicName, bundleVersion,
				contextName);
		}

		String[] contextNames = StringUtil.split(
			properties.getProperty("context-names"));

		for (String contextName : contextNames) {
			_moduleLocalService.addModule(
				0, app.getAppId(), StringPool.BLANK, StringPool.BLANK,
				contextName);
		}

		_appLocalService.processMarketplaceProperties(properties);
	}

	private void _uninstallBundles(List<Bundle> bundles) {
		for (Bundle bundle : bundles) {
			try {
				bundle.uninstall();
			}
			catch (BundleException be) {
			}
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+\\.\\d+)?");

	private final AppLocalService _appLocalService;
	private BundleContext _bundleContext;
	private final ConcurrentMap<Long, List<Bundle>> _installedLpkgFiles =
		new ConcurrentHashMap<>();
	private final ModuleLocalService _moduleLocalService;

	private static final class JarArtifactInstaller
		implements ArtifactInstaller {

		public JarArtifactInstaller(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public Bundle install(URL url) throws Exception {
			return _bundleContext.installBundle(
				url.getPath(), url.openStream());
		}

		private BundleContext _bundleContext;

	}

	private static final class WARArtifactInstaller
		implements ArtifactInstaller {

		public WARArtifactInstaller(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public Bundle install(URL url) throws Exception {
			String path = url.getPath();

			int x = path.lastIndexOf("/");
			int y = path.lastIndexOf(".war");

			String contextName = path.substring(x + 1, y);

			Matcher matcher = _pattern.matcher(contextName);

			if (matcher.matches()) {
				contextName = matcher.group(1);
			}

			File tempFile = File.createTempFile(
				"lpkg-module-" + contextName, ".war");

			try {
				StreamUtil.transfer(
					url.openStream(), new FileOutputStream(tempFile));

				String location =
					"webbundle:file://" + tempFile.getAbsolutePath() +
						"?Web-ContextPath=/" + contextName;

				return _bundleContext.installBundle(location);
			}
			finally {
				tempFile.delete();
			}
		}

		private final BundleContext _bundleContext;

	}

	private interface ArtifactInstaller {

		public Bundle install(URL url) throws Exception;

	}

}