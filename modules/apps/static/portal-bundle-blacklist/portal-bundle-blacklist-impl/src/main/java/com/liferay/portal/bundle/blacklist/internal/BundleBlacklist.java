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

package com.liferay.portal.bundle.blacklist.internal;

import com.liferay.osgi.util.BundleUtil;
import com.liferay.portal.bundle.blacklist.internal.configuration.BundleBlacklistConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(
	configurationPid = "com.liferay.portal.bundle.blacklist.internal.configuration.BundleBlacklistConfiguration",
	immediate = true, service = BundleBlacklist.class
)
public class BundleBlacklist {

	public List<String> getBlacklistBundleSymbolicNames() {
		return new ArrayList<>(_uninstalledBundles.keySet());
	}

	@Activate
	@Modified
	protected void activate(
			BundleContext bundleContext, Map<String, String> properties)
		throws Exception {

		Bundle bundle = bundleContext.getBundle();

		_blacklistFile = bundle.getDataFile(_BLACKLIST_FILE_NAME);

		Bundle systemBundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = systemBundle.getBundleContext();

		if (_selfMonitorBundleListener == null) {
			_selfMonitorBundleListener = new SelfMonitorBundleListener(
				bundle, systemBundleContext, _lpkgDeployer,
				_uninstalledBundles);
		}

		systemBundleContext.addBundleListener(_selfMonitorBundleListener);

		_loadFromBlacklistFile();

		BundleBlacklistConfiguration bundleBlacklistConfiguration =
			ConfigurableUtil.createConfigurable(
				BundleBlacklistConfiguration.class, properties);

		_blacklistBundleSymbolicNames = new HashSet<>(
			Arrays.asList(
				bundleBlacklistConfiguration.blacklistBundleSymbolicNames()));

		_blacklistBundleSymbolicNames.remove(bundle.getSymbolicName());

		bundleContext.addBundleListener(_bundleListener);

		_scanBundles(bundleContext);

		Set<Map.Entry<String, UninstalledBundleData>> entrySet =
			_uninstalledBundles.entrySet();

		Iterator<Map.Entry<String, UninstalledBundleData>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, UninstalledBundleData> entry = iterator.next();

			String symbolicName = entry.getKey();

			if (!_blacklistBundleSymbolicNames.contains(symbolicName)) {
				if (_log.isInfoEnabled()) {
					_log.info("Reinstalling bundle " + symbolicName);
				}

				UninstalledBundleData uninstalledBundleData = entry.getValue();

				BundleUtil.installBundle(
					bundleContext, _lpkgDeployer,
					uninstalledBundleData.getLocation(),
					uninstalledBundleData.getStartLevel());

				iterator.remove();

				_removeFromBlacklistFile(symbolicName);
			}
		}
	}

	private void _addToBlacklistFile(
			String symbolicName, UninstalledBundleData uninstalledBundleData)
		throws IOException {

		Properties blacklistProperties = new Properties();

		if (_blacklistFile.exists()) {
			try (InputStream inputStream = new FileInputStream(
					_blacklistFile)) {

				blacklistProperties.load(inputStream);
			}
		}

		blacklistProperties.setProperty(
			symbolicName, uninstalledBundleData.toString());

		try (OutputStream outputStream = new FileOutputStream(_blacklistFile)) {
			blacklistProperties.store(outputStream, null);
		}
	}

	private void _loadFromBlacklistFile() throws Exception {
		if (!_blacklistFile.exists()) {
			return;
		}

		Properties blacklistProperties = new Properties();

		try (InputStream inputStream = new FileInputStream(_blacklistFile)) {
			blacklistProperties.load(inputStream);
		}

		Set<Map.Entry<Object, Object>> entries = blacklistProperties.entrySet();

		for (Map.Entry<Object, Object> entry : entries) {
			String value = (String)entry.getValue();

			Matcher matcher = _pattern.matcher(value);

			if (matcher.matches()) {
				_uninstalledBundles.put(
					(String)entry.getKey(),
					new UninstalledBundleData(
						matcher.group(1), Integer.valueOf(matcher.group(2))));
			}
		}
	}

	private boolean _processBundle(Bundle bundle) {
		String symbolicName = bundle.getSymbolicName();

		if (_blacklistBundleSymbolicNames.contains(symbolicName)) {
			if (_log.isInfoEnabled()) {
				_log.info("Stopping blacklisted bundle " + bundle);
			}

			BundleStartLevel bundleStartLevel = bundle.adapt(
				BundleStartLevel.class);

			UninstalledBundleData uninstalledBundleData =
				new UninstalledBundleData(
					bundle.getLocation(), bundleStartLevel.getStartLevel());

			_uninstalledBundles.put(symbolicName, uninstalledBundleData);

			try {
				bundle.uninstall();

				_addToBlacklistFile(symbolicName, uninstalledBundleData);
			}
			catch (Exception exception) {
				_log.error("Unable to uninstall " + bundle, exception);

				_uninstalledBundles.remove(symbolicName);
			}

			return true;
		}

		return false;
	}

	private void _removeFromBlacklistFile(String symbolicName)
		throws Exception {

		Properties blacklistProperties = new Properties();

		if (_blacklistFile.exists()) {
			try (InputStream inputStream = new FileInputStream(
					_blacklistFile)) {

				blacklistProperties.load(inputStream);
			}
		}

		blacklistProperties.remove(symbolicName);

		try (OutputStream outputStream = new FileOutputStream(_blacklistFile)) {
			blacklistProperties.store(outputStream, null);
		}
	}

	private void _scanBundles(BundleContext bundleContext) {
		List<Bundle> uninstalledBundles = new ArrayList<>();

		for (Bundle bundle : bundleContext.getBundles()) {
			if ((bundle.getState() != Bundle.UNINSTALLED) &&
				_processBundle(bundle)) {

				uninstalledBundles.add(bundle);
			}
		}

		if (!uninstalledBundles.isEmpty()) {
			BundleUtil.refreshBundles(bundleContext, uninstalledBundles);
		}
	}

	private static final String _BLACKLIST_FILE_NAME = "blacklist.properties";

	private static final Log _log = LogFactoryUtil.getLog(
		BundleBlacklist.class);

	private static final Pattern _pattern = Pattern.compile(
		"\\{location=([^,]+), startLevel=(\\d+)\\}");

	private volatile Set<String> _blacklistBundleSymbolicNames;
	private volatile File _blacklistFile;

	private final BundleListener _bundleListener =
		new SynchronousBundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				if (bundleEvent.getType() == BundleEvent.INSTALLED) {
					_processBundle(bundleEvent.getBundle());
				}
			}

		};

	@Reference
	private LPKGDeployer _lpkgDeployer;

	private volatile BundleListener _selfMonitorBundleListener;
	private final Map<String, UninstalledBundleData> _uninstalledBundles =
		new ConcurrentHashMap<>();

}