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

package com.liferay.portal.soap.extender.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.soap.extender.LiferaySoapServiceTracker;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.soap.extender",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class LiferaySoapServiceTrackerConfigAdmin {

	@Activate
	protected void activate(final ComponentContext componentContext) {
		DependencyManager dependencyManager = new DependencyManager(
			componentContext.getBundleContext());

		_liferaySoapServiceTrackerConfiguration =
			Configurable.createConfigurable(
				LiferaySoapServiceTrackerConfiguration.class,
				componentContext.getProperties());

		_component = dependencyManager.createComponent();

		ExtensionManager extensionManager = new ExtensionManager();

		LiferaySoapServiceTracker liferaySoapServiceTracker =
			new LiferaySoapServiceTracker(
				componentContext.getBundleContext(),
				_liferaySoapServiceTrackerConfiguration.contextPath(),
				extensionManager);

		_component.setImplementation(liferaySoapServiceTracker);

		String[] extensions =
			_liferaySoapServiceTrackerConfiguration.extensions();

		if (extensions != null) {
			for (String extension : extensions) {
				ServiceDependency serviceDependency =
					dependencyManager.createServiceDependency();

				serviceDependency.setCallbacks(
					extensionManager, "addExtension", "removeExtension");
				serviceDependency.setService(
					Object.class, _createExtensionFilter(extension));
				serviceDependency.setRequired(true);

				_component.add(serviceDependency);
			}
		}

		dependencyManager.add(_component);
	}

	@Deactivate
	protected void deactivate() {
		_component.stop();
	}

	@Modified
	protected void modified() {
	}

	private String _createExtensionFilter(String extensionName) {
		return "(&(soap.cxf.extension=true)(soap.extension.name=" +
			extensionName + ")(soap.extension.class=*))";
	}

	private org.apache.felix.dm.Component _component;
	private LiferaySoapServiceTrackerConfiguration
		_liferaySoapServiceTrackerConfiguration;

}