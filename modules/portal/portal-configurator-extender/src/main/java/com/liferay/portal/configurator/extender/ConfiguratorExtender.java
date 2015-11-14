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

import java.util.ArrayList;
import java.util.Collection;
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
		unbind = "removeConfigurationFactory"
	)
	protected void addConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactories.add(configurationFactory);
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
		List<Configuration> configurations = new ArrayList<>();

		for (ConfigurationFactory configurationFactory :
				_configurationFactories) {

			try {
				configurations.addAll(configurationFactory.create(bundle));
			}
			catch (Throwable t) {
				_logger.log(
					Logger.LOG_INFO, "ConfigurationURLLocator threw Exception",
					t);
			}
		}

		if (configurations.isEmpty()) {
			return null;
		}

		return new ConfiguratorExtension(
			_configurationAdmin, bundle.getSymbolicName(),
			configurations.toArray(new Configuration[configurations.size()]));
	}

	@Override
	protected void error(String s, Throwable throwable) {
		_logger.log(Logger.LOG_ERROR, s, throwable);
	}

	protected void removeConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactories.remove(configurationFactory);
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
	private final Collection<ConfigurationFactory> _configurationFactories =
		new CopyOnWriteArrayList<>();
	private Logger _logger;

}