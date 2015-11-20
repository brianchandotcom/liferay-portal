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
import java.util.List;
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
		unbind = "removeNamedConfigurationContentFactory"
	)
	protected void addNamedConfigurationContentFactory(
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
		Collection<NamedConfigurationContent> namedConfigurationContents =
			new ArrayList<>();

		for (NamedConfigurationContentFactory namedConfigurationContentFactory :
				_namedConfigurationContentFactories) {

			try {
				List<NamedConfigurationContent> contents =
					namedConfigurationContentFactory.create(
						new BundleStorageImpl(bundle));

				if (contents != null) {
					namedConfigurationContents.addAll(contents);
				}
			}
			catch (Throwable t) {
				_logger.log(Logger.LOG_INFO, t.getMessage(), t);
			}
		}

		if (namedConfigurationContents.isEmpty()) {
			return null;
		}

		return new ConfiguratorExtension(
			_configurationAdmin, new Logger(bundle.getBundleContext()),
			bundle.getSymbolicName(), namedConfigurationContents,
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

	protected void removeNamedConfigurationContentFactory(
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

	private volatile ConfigurationAdmin _configurationAdmin;
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
		public Enumeration<URL> findEntries(
			String root, String pattern, boolean recurse) {

			return _bundle.findEntries(root, pattern, recurse);
		}

		@Override
		public long getBundleId() {
			return _bundle.getBundleId();
		}

		@Override
		public URL getEntry(String name) {
			return _bundle.getEntry(name);
		}

		@Override
		public Enumeration<String> getEntryPaths(String name) {
			return _bundle.getEntryPaths(name);
		}

		@Override
		public Dictionary<String, String> getHeaders() {
			return _bundle.getHeaders();
		}

		@Override
		public String getLocation() {
			return _bundle.getLocation();
		}

		@Override
		public URL getResource(String name) {
			return _bundle.getResource(name);
		}

		@Override
		public Enumeration<URL> getResources(String name) throws IOException {
			return _bundle.getResources(name);
		}

		@Override
		public String getSymbolicName() {
			return _bundle.getSymbolicName();
		}

		private final Bundle _bundle;

	}

}