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

import com.liferay.osgi.felix.util.AbstractExtender;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
@Component(immediate = true, service = {})
public class ConfiguratorExtender extends AbstractExtender {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_logger = new Logger(bundleContext);

		start(bundleContext);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addConfigurationDescriptionFactory(
		ConfigurationDescriptionFactory configurationDescriptionFactory) {

		_configurationDescriptionFactories.add(configurationDescriptionFactory);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
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
		_logger.log(
			Logger.LOG_DEBUG, StringBundler.concat("[", bundle, "] ", s));
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String configurationPath = headers.get("Liferay-Configuration-Path");

		if (configurationPath == null) {
			return null;
		}

		List<NamedConfigurationContent> namedConfigurationContents =
			new ArrayList<>();

		for (NamedConfigurationContentFactory namedConfigurationContentFactory :
				_namedConfigurationContentFactories) {

			_addNamedConfigurations(
				bundle, configurationPath, namedConfigurationContents,
				namedConfigurationContentFactory,
				namedConfigurationContentFactory.getFilePattern());
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

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void warn(Bundle bundle, String s, Throwable throwable) {
		_logger.log(
			Logger.LOG_WARNING, StringBundler.concat("[", bundle, "] ", s));
	}

	private void _addNamedConfigurations(
		Bundle bundle, String configurationPath,
		List<NamedConfigurationContent> namedConfigurationContents,
		NamedConfigurationContentFactory namedConfigurationContentFactory,
		String filePattern) {

		try {
			Enumeration<URL> entries = bundle.findEntries(
				configurationPath, filePattern, true);

			if (entries == null) {
				return;
			}

			while (entries.hasMoreElements()) {
				URL url = entries.nextElement();

				namedConfigurationContents.add(
					namedConfigurationContentFactory.create(
						_getName(url, filePattern), url.openStream()));
			}
		}
		catch (Throwable t) {
			_logger.log(Logger.LOG_INFO, t.getMessage(), t);
		}
	}

	private String _getName(URL url, String filePattern) {
		String name = url.getFile();

		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		int lastIndexOfSlash = name.lastIndexOf('/');

		if (lastIndexOfSlash > 0) {
			name = name.substring(lastIndexOfSlash + 1);
		}

		return name.substring(0, name.length() + 1 - filePattern.length());
	}

	private ConfigurationAdmin _configurationAdmin;
	private final Collection<ConfigurationDescriptionFactory>
		_configurationDescriptionFactories = new CopyOnWriteArrayList<>();
	private Logger _logger;
	private final Collection<NamedConfigurationContentFactory>
		_namedConfigurationContentFactories = new CopyOnWriteArrayList<>();

}