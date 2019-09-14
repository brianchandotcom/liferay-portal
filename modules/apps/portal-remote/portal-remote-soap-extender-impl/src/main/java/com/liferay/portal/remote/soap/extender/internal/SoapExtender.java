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

package com.liferay.portal.remote.soap.extender.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.remote.soap.extender.SoapDescriptorBuilder;
import com.liferay.portal.remote.soap.extender.internal.configuration.SoapExtenderConfiguration;

import java.util.Map;

import javax.xml.ws.handler.Handler;

import org.apache.cxf.Bus;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.remote.soap.extender.internal.configuration.SoapExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class SoapExtender {

	public SoapExtenderConfiguration getSoapExtenderConfiguration() {
		return _soapExtenderConfiguration;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_soapExtenderConfiguration = ConfigurableUtil.createConfigurable(
			SoapExtenderConfiguration.class, properties);

		_dependencyManager = new DependencyManager(bundleContext);

		_enableComponent();
	}

	protected void addBusDependencies(org.apache.felix.dm.Component component) {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] contextPaths = soapExtenderConfiguration.contextPaths();

		if (contextPaths == null) {
			return;
		}

		for (String contextPath : contextPaths) {
			addTCCLServiceDependency(
				component, true, Bus.class,
				StringBundler.concat(
					"(", HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH,
					"=", contextPath, ")"),
				"addBus", "removeBus");
		}
	}

	protected void addJaxWsHandlerServiceDependencies(
		org.apache.felix.dm.Component component) {

		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] jaxWsHandlerFilterStrings =
			soapExtenderConfiguration.jaxWsHandlerFilterStrings();

		if (jaxWsHandlerFilterStrings == null) {
			return;
		}

		for (String jaxWsHandlerFilterString : jaxWsHandlerFilterStrings) {
			addTCCLServiceDependency(
				component, false, Handler.class, jaxWsHandlerFilterString,
				"addHandler", "removeHandler");
		}
	}

	protected void addJaxWsServiceDependencies(
		org.apache.felix.dm.Component component) {

		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] jaxWsServiceFilterStrings =
			soapExtenderConfiguration.jaxWsServiceFilterStrings();

		if (jaxWsServiceFilterStrings == null) {
			return;
		}

		for (String jaxWsServiceFilterString : jaxWsServiceFilterStrings) {
			addTCCLServiceDependency(
				component, false, null, jaxWsServiceFilterString, "addService",
				"removeService");
		}
	}

	protected void addSoapDescriptorBuilderServiceDependency(
		org.apache.felix.dm.Component component) {

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks("setSoapDescriptorBuilder", null);
		serviceDependency.setRequired(false);
		serviceDependency.setService(
			SoapDescriptorBuilder.class,
			_soapExtenderConfiguration.soapDescriptorBuilderFilter());

		component.add(serviceDependency);
	}

	protected ServiceDependency addTCCLServiceDependency(
		org.apache.felix.dm.Component component, boolean required,
		Class<?> clazz, String filterString, String addName,
		String removeName) {

		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setCallbacks(addName, removeName);
		serviceDependency.setRequired(required);

		if (clazz == null) {
			serviceDependency.setService(filterString);
		}
		else {
			serviceDependency.setService(clazz, filterString);
		}

		component.add(serviceDependency);

		return serviceDependency;
	}

	@Deactivate
	protected void deactivate() {
		_dependencyManager.clear();
	}

	@Modified
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		deactivate();

		activate(bundleContext, properties);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {

		_soapDescriptorBuilder = soapDescriptorBuilder;

		if (_dependencyManager != null) {
			_dependencyManager.clear();

			_enableComponent();
		}
	}

	protected void unsetSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {
	}

	private void _enableComponent() {
		org.apache.felix.dm.Component component =
			_dependencyManager.createComponent();

		CXFJaxWsServiceRegistrator cxfJaxWsServiceRegistrator =
			new CXFJaxWsServiceRegistrator();

		cxfJaxWsServiceRegistrator.setSoapDescriptorBuilder(
			_soapDescriptorBuilder);

		component.setImplementation(cxfJaxWsServiceRegistrator);

		addBusDependencies(component);
		addJaxWsHandlerServiceDependencies(component);
		addJaxWsServiceDependencies(component);
		addSoapDescriptorBuilderServiceDependency(component);

		_dependencyManager.add(component);
	}

	private DependencyManager _dependencyManager;
	private SoapDescriptorBuilder _soapDescriptorBuilder;
	private SoapExtenderConfiguration _soapExtenderConfiguration;

}