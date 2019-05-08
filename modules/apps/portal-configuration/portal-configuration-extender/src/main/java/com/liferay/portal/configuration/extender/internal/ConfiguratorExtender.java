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

package com.liferay.portal.configuration.extender.internal;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

import org.apache.felix.cm.file.ConfigurationHandler;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 * @author Miguel Pastor
 */
@Component(immediate = true, service = {})
public class ConfiguratorExtender implements BundleTrackerCustomizer<Bundle> {

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String configurationPath = headers.get("Liferay-Configuration-Path");

		if (configurationPath == null) {
			return null;
		}

		List<NamedConfigurationContent> namedConfigurationContents =
			new ArrayList<>();

		_addNamedConfigurations(
			bundle, configurationPath, namedConfigurationContents,
			inputStream -> ConfigurationHandler.read(inputStream), "*.config");

		_addNamedConfigurations(
			bundle, configurationPath, namedConfigurationContents,
			inputStream -> PropertiesUtil.load(inputStream, "UTF-8"),
			"*.properties");

		if (namedConfigurationContents.isEmpty()) {
			return null;
		}

		_process(
			_configurationAdmin, bundle.getSymbolicName(),
			namedConfigurationContents);

		return bundle;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle trackedBundle) {
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE | Bundle.STARTING, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private static void _process(
		ConfigurationAdmin configurationAdmin, String symbolicName,
		Collection<NamedConfigurationContent> namedConfigurationContents) {

		for (NamedConfigurationContent namedConfigurationContent :
				namedConfigurationContents) {

			try {
				_process(
					configurationAdmin, symbolicName,
					namedConfigurationContent);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static void _process(
			ConfigurationAdmin configurationAdmin, String symbolicName,
			NamedConfigurationContent namedConfigurationContent)
		throws InvalidSyntaxException, IOException {

		Configuration configuration = null;
		String configuratorURL = null;

		if (namedConfigurationContent.getFactoryPid() == null) {
			String pid = namedConfigurationContent.getPid();

			if (ArrayUtil.isNotEmpty(
					configurationAdmin.listConfigurations(
						"(service.pid=" + pid + ")"))) {

				return;
			}

			configuration = configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION);
		}
		else {
			configuratorURL =
				symbolicName + "#" + namedConfigurationContent.getPid();

			if (ArrayUtil.isNotEmpty(
					configurationAdmin.listConfigurations(
						"(configurator.url=" + configuratorURL + ")"))) {

				return;
			}

			configuration = configurationAdmin.createFactoryConfiguration(
				namedConfigurationContent.getFactoryPid(), StringPool.QUESTION);
		}

		Dictionary<String, Object> properties = null;

		try {
			properties = namedConfigurationContent.getProperties();
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Supplier from description ", namedConfigurationContent,
						" threw an exception: "),
					t);
			}

			return;
		}

		if (configuratorURL != null) {
			properties.put("configurator.url", configuratorURL);
		}

		configuration.update(properties);
	}

	private void _addNamedConfigurations(
		Bundle bundle, String configurationPath,
		List<NamedConfigurationContent> namedConfigurationContents,
		UnsafeFunction<InputStream, Dictionary<?, ?>, IOException>
			propertyFunction,
		String filePattern) {

		Enumeration<URL> entries = bundle.findEntries(
			configurationPath, filePattern, true);

		if (entries == null) {
			return;
		}

		while (entries.hasMoreElements()) {
			URL url = entries.nextElement();

			String name = url.getFile();

			int lastIndexOfSlash = name.lastIndexOf('/');

			if (lastIndexOfSlash < 0) {
				lastIndexOfSlash = 0;
			}

			String factoryPid = null;
			String pid = null;

			int index = name.lastIndexOf('-');

			if (index > lastIndexOfSlash) {
				factoryPid = name.substring(lastIndexOfSlash, index);
				pid = name.substring(
					index + 1, name.length() + 1 - filePattern.length());
			}
			else {
				pid = name.substring(
					lastIndexOfSlash, name.length() + 1 - filePattern.length());
			}

			namedConfigurationContents.add(
				new NamedConfigurationContent(
					factoryPid, pid,
					() -> {
						try (InputStream inputStream = url.openStream()) {
							return propertyFunction.apply(inputStream);
						}
					}));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfiguratorExtender.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}