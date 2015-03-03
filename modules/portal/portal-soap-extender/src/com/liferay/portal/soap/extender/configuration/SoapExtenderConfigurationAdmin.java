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

import com.liferay.portal.soap.extender.SoapExtender;

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
	configurationPid = "com.liferay.portal.soap.extender.SoapExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class SoapExtenderConfigurationAdmin {

	@Activate
	protected void activate(final ComponentContext componentContext) {
		DependencyManager dependencyManager = new DependencyManager(
			componentContext.getBundleContext());

		_soapExtenderConfiguration = Configurable.createConfigurable(
			SoapExtenderConfiguration.class, componentContext.getProperties());

		_component = dependencyManager.createComponent();

		ExtensionManager extensionManager = new ExtensionManager();

		SoapExtender soapExtender = new SoapExtender(
			componentContext.getBundleContext(),
			_soapExtenderConfiguration.contextPath(), extensionManager);

		_component.setImplementation(soapExtender);

		String[] extensions = _soapExtenderConfiguration.extensions();

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
		return "(&(soap.extension=true)(soap.extension.name=" +
			extensionName + ")(soap.extension.class=*))";
	}

	private org.apache.felix.dm.Component _component;
	private SoapExtenderConfiguration _soapExtenderConfiguration;

}