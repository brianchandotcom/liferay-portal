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

package com.liferay.portal.remote.rest.extender.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.remote.rest.extender.configuration.RestExtenderConfiguration;

import java.util.Map;

import javax.ws.rs.core.Application;

import org.apache.cxf.Bus;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.remote.rest.extender.configuration.RestExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class RESTExtender {

	public RestExtenderConfiguration getRestExtenderConfiguration() {
		return _restExtenderConfiguration;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_restExtenderConfiguration = ConfigurableUtil.createConfigurable(
			RestExtenderConfiguration.class, properties);

		_dependencyManager = new DependencyManager(bundleContext);

		_component = _dependencyManager.createComponent();

		CXFJaxRsServiceRegistrator cxfJaxRsServiceRegistrator =
			new CXFJaxRsServiceRegistrator(properties);

		_component.setImplementation(cxfJaxRsServiceRegistrator);

		_addBusDependencies();
		_addJaxRsApplicationDependencies();
		_addJaxRsProviderServiceDependencies();
		_addJaxRsServiceDependencies();

		_dependencyManager.add(_component);
	}

	@Deactivate
	protected void deactivate() {
		_dependencyManager.clear();
	}

	private void _addBusDependencies() {
		RestExtenderConfiguration restExtenderConfiguration =
			getRestExtenderConfiguration();

		String[] contextPaths = restExtenderConfiguration.contextPaths();

		if (contextPaths == null) {
			return;
		}

		for (String contextPath : contextPaths) {
			if (Validator.isNull(contextPath)) {
				continue;
			}

			_addTCCLServiceDependency(
				true, Bus.class,
				StringBundler.concat(
					"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
					"=", contextPath, ")"),
				"addBus", "removeBus");
		}
	}

	private void _addJaxRsApplicationDependencies() {
		RestExtenderConfiguration restExtenderConfiguration =
			getRestExtenderConfiguration();

		String[] jaxRsApplicationFilterStrings =
			restExtenderConfiguration.jaxRsApplicationFilterStrings();

		if (jaxRsApplicationFilterStrings == null) {
			_addTCCLServiceDependency(
				false, Application.class, null, "addApplication",
				"removeApplication");

			return;
		}

		for (String jaxRsApplicationFilterString :
				jaxRsApplicationFilterStrings) {

			_addTCCLServiceDependency(
				false, Application.class, jaxRsApplicationFilterString,
				"addApplication", "removeApplication");
		}
	}

	private void _addJaxRsProviderServiceDependencies() {
		RestExtenderConfiguration soapExtenderConfiguration =
			getRestExtenderConfiguration();

		String[] jaxRsProviderFilterStrings =
			soapExtenderConfiguration.jaxRsProviderFilterStrings();

		if (jaxRsProviderFilterStrings == null) {
			return;
		}

		for (String jaxRsProviderFilterString : jaxRsProviderFilterStrings) {
			if (Validator.isNull(jaxRsProviderFilterString)) {
				continue;
			}

			_addTCCLServiceDependency(
				false, null, jaxRsProviderFilterString, "addProvider",
				"removeProvider");
		}
	}

	private void _addJaxRsServiceDependencies() {
		RestExtenderConfiguration soapExtenderConfiguration =
			getRestExtenderConfiguration();

		String[] jaxRsServiceFilterStrings =
			soapExtenderConfiguration.jaxRsServiceFilterStrings();

		if (jaxRsServiceFilterStrings == null) {
			return;
		}

		for (String jaxRsServiceFilterString : jaxRsServiceFilterStrings) {
			if (Validator.isNull(jaxRsServiceFilterString)) {
				continue;
			}

			_addTCCLServiceDependency(
				false, null, jaxRsServiceFilterString, "addService",
				"removeService");
		}
	}

	private ServiceDependency _addTCCLServiceDependency(
		boolean required, Class<?> clazz, String filter, String addName,
		String removeName) {

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks(addName, removeName);
		serviceDependency.setRequired(required);

		if (clazz == null) {
			serviceDependency.setService(filter);
		}
		else {
			if (filter == null) {
				serviceDependency.setService(clazz);
			}
			else {
				serviceDependency.setService(clazz, filter);
			}
		}

		_component.add(serviceDependency);

		return serviceDependency;
	}

	private org.apache.felix.dm.Component _component;
	private DependencyManager _dependencyManager;

	@Reference
	private JaxrsServiceRuntime _jaxrsServiceRuntime;

	private RestExtenderConfiguration _restExtenderConfiguration;

}