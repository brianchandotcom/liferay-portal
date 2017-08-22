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

package com.liferay.portal.bundle.blacklist;

import com.liferay.portal.bundle.blacklist.internal.BundleBlacklistConfiguration;
import com.liferay.portal.bundle.blacklist.internal.ParamUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.lpkg.deployer.util.BundleStartLevelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.startlevel.BundleStartLevel;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(
	configurationPid = "com.liferay.portal.bundle.blacklist.internal.BundleBlacklistConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = BundleBlacklist.class
)
public class BundleBlacklist {

	@Activate
	@Modified
	protected void activate(
			BundleContext bundleContext, Map<String, String> properties)
		throws Throwable {

		_bundleContext = bundleContext;

		ClassLoader classLoader = BundleBlacklist.class.getClassLoader();

		classLoader.loadClass(BundleStartLevelUtil.class.getName());

		Bundle bundle = bundleContext.getBundle();

		Bundle systemBundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = systemBundle.getBundleContext();

		FrameworkWiring frameworkWiring = systemBundle.adapt(
			FrameworkWiring.class);

		if (_selfMonitorBundleListener == null) {
			_selfMonitorBundleListener = new SelfMonitorBundleListener(
				bundle, systemBundleContext, frameworkWiring, _lpkgDeployer,
				_uninstalledBundles);
		}

		systemBundleContext.addBundleListener(_selfMonitorBundleListener);

		_initializeBlacklistMap(bundle);

		BundleBlacklistConfiguration bundleBlacklistConfiguration =
			ConfigurableUtil.createConfigurable(
				BundleBlacklistConfiguration.class, properties);

		_blacklistBundleSymbolicNames = new HashSet<>(
			Arrays.asList(
				bundleBlacklistConfiguration.blacklistBundleSymbolicNames()));

		bundleContext.addBundleListener(_bundleListener);

		_scanBundles(frameworkWiring);

		Set<Entry<String, UninstalledBundleData>> entrySet =
			_uninstalledBundles.entrySet();

		Iterator<Entry<String, UninstalledBundleData>> iterator =
			entrySet.iterator();

		while (iterator.hasNext()) {
			Entry<String, UninstalledBundleData> entry = iterator.next();

			String symbolicName = entry.getKey();

			if (!_blacklistBundleSymbolicNames.contains(symbolicName)) {
				if (_log.isInfoEnabled()) {
					_log.info("Reinstalling bundle " + symbolicName);
				}

				_reinstallBundle(
					frameworkWiring, entry.getValue(), bundleContext,
					_lpkgDeployer);

				iterator.remove();

				_removeBlacklistProperty(symbolicName);
			}
		}
	}

	private static void _refreshBundles(
		FrameworkWiring frameworkWiring, List<Bundle> refreshBundles) {

		final DefaultNoticeableFuture<FrameworkEvent> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		frameworkWiring.refreshBundles(
			refreshBundles,
			new FrameworkListener() {

				@Override
				public void frameworkEvent(FrameworkEvent frameworkEvent) {
					defaultNoticeableFuture.set(frameworkEvent);
				}

			});

		try {
			FrameworkEvent frameworkEvent = defaultNoticeableFuture.get();

			if (frameworkEvent.getType() != FrameworkEvent.PACKAGES_REFRESHED) {
				throw frameworkEvent.getThrowable();
			}
		}
		catch (Throwable t) {
			ReflectionUtil.throwException(t);
		}
	}

	private static void _reinstallBundle(
			FrameworkWiring frameworkWiring,
			UninstalledBundleData uninstalledBundleData,
			BundleContext bundleContext, LPKGDeployer lpkgDeployer)
		throws Throwable {

		Bundle bundle = null;

		String location = uninstalledBundleData.getLocation();

		Map<String, String[]> parameters = ParamUtil.getParameterMap(location);

		String[] lpkgPath = parameters.get("lpkgPath");

		String[] protocol = parameters.get("protocol");

		String[] webContextPath = parameters.get("Web-ContextPath");

		if (parameters.isEmpty() && location.endsWith(".lpkg")) {
			bundle = bundleContext.installBundle(
				location, lpkgDeployer.toBundle(new File(location)));
		}
		else if (ArrayUtil.isNotEmpty(lpkgPath)) {
			bundle = bundleContext.getBundle(lpkgPath[0]);

			_refreshBundles(
				frameworkWiring, Collections.<Bundle>singletonList(bundle));

			return;
		}
		else if (ArrayUtil.isNotEmpty(protocol) && protocol[0].equals("lpkg") &&
				 ArrayUtil.isNotEmpty(webContextPath)) {

			String contextName = webContextPath[0].substring(1);

			for (Bundle installedBundle : bundleContext.getBundles()) {
				Dictionary<String, String> headers =
					installedBundle.getHeaders();

				if (contextName.equals(
						headers.get("Liferay-WAB-Context-Name"))) {

					_refreshBundles(
						frameworkWiring,
						Collections.<Bundle>singletonList(installedBundle));
				}
			}

			return;
		}
		else {
			bundle = bundleContext.installBundle(location);
		}

		int startLevel = uninstalledBundleData.getStartLevel();

		BundleStartLevelUtil.setStartLevelAndStart(
			bundle, startLevel, bundleContext);
	}

	private void _initializeBlacklistMap(Bundle bundle) throws IOException {
		File blacklistFile = bundle.getDataFile(_BLACKLIST_FILE_NAME);

		if (!blacklistFile.exists()) {
			return;
		}

		Properties blacklistProperties = new Properties();

		try (InputStream inputStream = new FileInputStream(blacklistFile)) {
			blacklistProperties.load(inputStream);
		}

		Set<Entry<Object, Object>> entries = blacklistProperties.entrySet();

		for (Entry<Object, Object> entry : entries) {
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

	private boolean _processBundle(Bundle bundle) throws Exception {
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

				_saveBlacklistProperty(symbolicName, uninstalledBundleData);
			}
			catch (Exception e) {
				_log.error("Unable to uninstall " + bundle, e);

				_uninstalledBundles.remove(symbolicName);
			}

			return true;
		}

		return false;
	}

	private void _removeBlacklistProperty(String symbolicName)
		throws Exception {

		Bundle bundle = _bundleContext.getBundle();

		File blacklistFile = bundle.getDataFile(_BLACKLIST_FILE_NAME);

		Properties blacklistProperties = new Properties();

		if (blacklistFile.exists()) {
			try (InputStream inputStream = new FileInputStream(blacklistFile)) {
				blacklistProperties.load(inputStream);
			}
		}

		blacklistProperties.remove(symbolicName);

		try (OutputStream outputStream = new FileOutputStream(blacklistFile)) {
			blacklistProperties.store(outputStream, null);
		}
	}

	private void _saveBlacklistProperty(
			String symbolicName, UninstalledBundleData uninstalledBundleData)
		throws Exception {

		Bundle bundle = _bundleContext.getBundle();

		File blacklistFile = bundle.getDataFile(_BLACKLIST_FILE_NAME);

		Properties blacklistProperties = new Properties();

		if (blacklistFile.exists()) {
			try (InputStream inputStream = new FileInputStream(blacklistFile)) {
				blacklistProperties.load(inputStream);
			}
		}

		blacklistProperties.setProperty(
			symbolicName, uninstalledBundleData.toString());

		try (OutputStream outputStream = new FileOutputStream(blacklistFile)) {
			blacklistProperties.store(outputStream, null);
		}
	}

	private void _scanBundles(FrameworkWiring frameworkWiring)
		throws Exception {

		List<Bundle> uninstalledBundles = new ArrayList<>();

		for (Bundle bundle : _bundleContext.getBundles()) {
			if ((bundle.getState() != Bundle.UNINSTALLED) &&
				_processBundle(bundle)) {

				uninstalledBundles.add(bundle);
			}
		}

		if (!uninstalledBundles.isEmpty()) {
			_refreshBundles(frameworkWiring, uninstalledBundles);
		}
	}

	private static final String _BLACKLIST_FILE_NAME = "blacklist.properties";

	private static final Log _log = LogFactoryUtil.getLog(
		BundleBlacklist.class);

	private Set<String> _blacklistBundleSymbolicNames;
	private BundleContext _bundleContext;

	private final BundleListener _bundleListener =
		new SynchronousBundleListener() {

			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				if (bundleEvent.getType() != BundleEvent.INSTALLED) {
					return;
				}

				try {
					_processBundle(bundleEvent.getBundle());
				}
				catch (Exception e) {
					ReflectionUtil.throwException(e);
				}
			}

		};

	@Reference
	private LPKGDeployer _lpkgDeployer;

	private final Pattern _pattern = Pattern.compile(
		"\\{location=(.+), startLevel=(\\d+)\\}");
	private BundleListener _selfMonitorBundleListener;
	private final Map<String, UninstalledBundleData> _uninstalledBundles =
		new HashMap<>();

	private static class SelfMonitorBundleListener implements BundleListener {

		@Override
		public void bundleChanged(BundleEvent bundleEvent) {
			Bundle bundle = bundleEvent.getBundle();

			if (!bundle.equals(_bundle)) {
				return;
			}

			if (bundleEvent.getType() == BundleEvent.STARTED) {

				// In case of STARTED, that means the blacklist bundle has been
				// updated or refreshed, we must unregister the self monitor
				// listener to release previous BundleRevision.

				_systemBundleContext.removeBundleListener(this);

				return;
			}

			if (bundleEvent.getType() != BundleEvent.UNINSTALLED) {
				return;
			}

			for (UninstalledBundleData uninstalledBundleData :
					_uninstalledBundles.values()) {

				try {
					_reinstallBundle(
						_frameworkWiring, uninstalledBundleData,
						_systemBundleContext, _lpkgDeployer);
				}
				catch (Throwable t) {
					ReflectionUtil.throwException(t);
				}
			}

			_systemBundleContext.removeBundleListener(this);
		}

		private SelfMonitorBundleListener(
			Bundle bundle, BundleContext systemBundleContext,
			FrameworkWiring frameworkWiring, LPKGDeployer lpkgDeployer,
			Map<String, UninstalledBundleData> uninstalledBundles) {

			_bundle = bundle;
			_systemBundleContext = systemBundleContext;
			_frameworkWiring = frameworkWiring;
			_lpkgDeployer = lpkgDeployer;
			_uninstalledBundles = uninstalledBundles;
		}

		private final Bundle _bundle;
		private final FrameworkWiring _frameworkWiring;
		private final LPKGDeployer _lpkgDeployer;
		private final BundleContext _systemBundleContext;
		private final Map<String, UninstalledBundleData> _uninstalledBundles;

	}

	private static class UninstalledBundleData {

		public String getLocation() {
			return _location;
		}

		public int getStartLevel() {
			return _startLevel;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			sb.append("{location=");
			sb.append(_location);
			sb.append(", startLevel=");
			sb.append(_startLevel);
			sb.append("}");

			return sb.toString();
		}

		private UninstalledBundleData(String location, int startLevel) {
			_location = location;

			_startLevel = startLevel;
		}

		private final String _location;
		private final int _startLevel;

	}

}