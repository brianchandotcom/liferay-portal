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

package com.liferay.portal.settings;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.portal.settings.util.SettingsDefinitionLifecycleHandler;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class SettingsDefinitionsTracker {

	@Deactivate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_processPendingRegistrations();
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition) {

		_pendingSettingsDefinitions.add(settingsDefinition);

		_processPendingRegistrations();
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	protected void unsetSettingsDefinition(
		SettingsDefinition<?, ?> settingsDefinition) {

		SettingsDefinitionLifecycleHandler<?, ?>
			settingsDefinitionLifecycleHandler =
				_settingsDefinitionLifecycleHandlers.remove(settingsDefinition);

		settingsDefinitionLifecycleHandler.stop();
	}

	private void _processPendingRegistrations() {
		if (_bundleContext == null) {
			return;
		}

		Iterator<SettingsDefinition<?, ?>> iterator =
			_pendingSettingsDefinitions.iterator();

		while (iterator.hasNext()) {
			SettingsDefinition<?, ?> settingsDefinition = iterator.next();

			SettingsDefinitionLifecycleHandler<?, ?>
				settingsDefinitionLifecycleHandler =
					new SettingsDefinitionLifecycleHandler<>(
						settingsDefinition, _bundleContext, _configurationAdmin,
						_settingsFactory);

			settingsDefinitionLifecycleHandler.start();

			_settingsDefinitionLifecycleHandlers.put(
				settingsDefinition, settingsDefinitionLifecycleHandler);

			iterator.remove();
		}

		_pendingSettingsDefinitions.clear();
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private final ConcurrentHashSet<SettingsDefinition<?, ?>>
		_pendingSettingsDefinitions = new ConcurrentHashSet<>();
	private final ConcurrentMap <SettingsDefinition<?, ?>,
		SettingsDefinitionLifecycleHandler<?, ?>>
			_settingsDefinitionLifecycleHandlers = new ConcurrentHashMap<>();
	private SettingsFactory _settingsFactory;

}