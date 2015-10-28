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

package com.liferay.portal.configurator.extender;

import com.liferay.portal.configurator.extender.internal.ConfiguratorExtension;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.felix.utils.extender.AbstractExtender;
import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class ConfiguratorExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		setSynchronous(true);

		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeConfigurationDescriptionFactory"
	)
	protected void addConfigurationDescriptionFactory(
		ConfigurationDescriptionFactory configurationDescriptionFactory) {

		_configurationDescriptionFactories.add(configurationDescriptionFactory);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeConfigurationURLLocator"
	)
	protected void addConfigurationURLLocator(
		NamedConfigurationContentFactory namedConfigurationContentFactory) {

		_namedConfigurationContentFactories.add(
			namedConfigurationContentFactory);
	}

	@Deactivate
	protected void deactivate(BundleContext bundleContext) throws Exception {
		stop(bundleContext);
	}

	@Override
	protected void debug(Bundle bundle, String s) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + s);
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		Collection<NamedConfigurationContent> configurationURLs =
			new ArrayList<>();

		for (NamedConfigurationContentFactory namedConfigurationContentFactory :
				_namedConfigurationContentFactories) {

			try {
				configurationURLs.addAll(
					namedConfigurationContentFactory.create(
						new BundleStorageImpl(bundle)));
			}
			catch (Throwable t) {
				_logger.log(
					Logger.LOG_INFO, "ConfigurationURLLocator threw Exception",
					t);
			}
		}

		if (configurationURLs.isEmpty()) {
			return null;
		}

		return new ConfiguratorExtension(
			_configurationAdmin, new Logger(bundle.getBundleContext()),
			bundle.getSymbolicName(), configurationURLs,
			_configurationDescriptionFactories);
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_logger.log(Logger.LOG_ERROR, s, throwable);
	}

	protected void removeConfigurationDescriptionFactory(
		ConfigurationDescriptionFactory configurationDescriptionFactory) {

		_configurationDescriptionFactories.remove(
			configurationDescriptionFactory);
	}

	protected void removeConfigurationURLLocator(
		NamedConfigurationContentFactory namedConfigurationContentFactory) {

		_namedConfigurationContentFactories.remove(
			namedConfigurationContentFactory);
	}

	@Reference
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + s);
	}

	private ConfigurationAdmin _configurationAdmin;
	private final Collection<ConfigurationDescriptionFactory>
		_configurationDescriptionFactories = new CopyOnWriteArrayList<>();
	private Logger _logger;
	private final Collection<NamedConfigurationContentFactory>
		_namedConfigurationContentFactories = new CopyOnWriteArrayList<>();

	private static class BundleStorageImpl implements BundleStorage {

		public BundleStorageImpl(Bundle bundle) {
			_bundle = bundle;
		}

		@Override
		public Enumeration<URL> findEntries(String s, String s1, boolean b) {
			return _bundle.findEntries(s, s1, b);
		}

		@Override
		public long getBundleId() {
			return _bundle.getBundleId();
		}

		@Override
		public URL getEntry(String s) {
			return _bundle.getEntry(s);
		}

		@Override
		public Enumeration<String> getEntryPaths(String s) {
			return _bundle.getEntryPaths(s);
		}

		@Override
		public Dictionary<String, String> getHeaders() {
			return _bundle.getHeaders();
		}

		public Dictionary<String, String> getHeaders(String s) {
			return _bundle.getHeaders(s);
		}

		@Override
		public String getLocation() {
			return _bundle.getLocation();
		}

		@Override
		public URL getResource(String s) {
			return _bundle.getResource(s);
		}

		@Override
		public Enumeration<URL> getResources(String s) throws IOException {
			return _bundle.getResources(s);
		}

		@Override
		public String getSymbolicName() {
			return _bundle.getSymbolicName();
		}

		private final Bundle _bundle;

	}

}