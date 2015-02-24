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

package com.liferay.portal.soap.web.service.extender.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.soap.web.service.extender.LiferaySoapServiceTracker;

import java.io.IOException;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
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
	configurationPid = "com.liferay.portal.soap.web.service.extender",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class LiferaySoapServiceTrackerConfigAdmin {

	@Activate
	public void activate(
			final BundleContext bundleContext,
			final ComponentContext componentContext)
		throws InvalidSyntaxException, IOException {

		DependencyManager dependencyManager = new DependencyManager(
			bundleContext);

		_liferaySoapServiceTrackerConfiguration =
			Configurable.createConfigurable(
				LiferaySoapServiceTrackerConfiguration.class,
				componentContext.getProperties());

		_component = dependencyManager.createComponent();

		ExtensionManager extensionManager = new ExtensionManager();

		LiferaySoapServiceTracker liferaySoapServiceTracker =
			new LiferaySoapServiceTracker(
				bundleContext,
				_liferaySoapServiceTrackerConfiguration.contextPath(),
				extensionManager);

		_component.setImplementation(liferaySoapServiceTracker);

		String[] enabledExtensionsArray =
			_liferaySoapServiceTrackerConfiguration.enabledExtensions();

		if (enabledExtensionsArray != null) {
			for (String enabledExtensionName : enabledExtensionsArray) {
				ServiceDependency extensionServiceDependency =
					dependencyManager.createServiceDependency();

				extensionServiceDependency.setCallbacks(
					extensionManager, "addExtension", "removeExtension");
				extensionServiceDependency.setService(
					Object.class, createExtensionFilter(enabledExtensionName));
				extensionServiceDependency.setRequired(true);

				_component.add(extensionServiceDependency);
			}
		}

		dependencyManager.add(_component);
	}

	@Deactivate
	public void deactivate() {
		_component.stop();
	}

	@Modified
	public void modified() {
	}

	private String createExtensionFilter(String extensionName) {
		return "(&(cxf.extension=true)(extension.name="+ extensionName +
			")(extension.class=*))";
	}

	private org.apache.felix.dm.Component _component;
	private LiferaySoapServiceTrackerConfiguration
		_liferaySoapServiceTrackerConfiguration;

}