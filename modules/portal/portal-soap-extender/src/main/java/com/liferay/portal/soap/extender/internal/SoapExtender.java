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

package com.liferay.portal.soap.extender.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.dependency.manager.tccl.TCCLDependencyManager;
import com.liferay.portal.soap.extender.SoapDescriptorBuilder;
import com.liferay.portal.soap.extender.configuration.SoapExtenderConfiguration;

import java.util.Map;

import javax.xml.ws.handler.Handler;

import org.apache.cxf.Bus;
import org.apache.felix.dm.ServiceDependency;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.portal.soap.extender.configuration.SoapExtenderConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE
)
public class SoapExtender {

	public SoapExtenderConfiguration getSoapExtenderConfiguration() {
		return _soapExtenderConfiguration;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_soapExtenderConfiguration = Configurable.createConfigurable(
			SoapExtenderConfiguration.class, properties);

		_dependencyManager = new TCCLDependencyManager(bundleContext);

		_component = _dependencyManager.createComponent();

		CXFJaxWsServiceRegistrator cxfJaxWsServiceRegistrator =
			new CXFJaxWsServiceRegistrator();

		cxfJaxWsServiceRegistrator.setSoapDescriptorBuilder(
			_soapDescriptorBuilder);

		_component.setImplementation(cxfJaxWsServiceRegistrator);

		addBusDependencies();
		addJaxWsHandlerServiceDependencies();
		addJaxWsServiceDependencies();
		addSoapDescriptorBuilderServiceDependency();

		_dependencyManager.add(_component);

		_component.start();
	}

	protected void addBusDependencies() {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] contextPaths = soapExtenderConfiguration.contextPaths();

		if (contextPaths == null) {
			return;
		}

		for (String contextPath : contextPaths) {
			addTCCLServiceDependency(
				true, Bus.class,
				"(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH +
					"=" + contextPath + ")",
				"addBus", "removeBus");
		}
	}

	protected void addJaxWsHandlerServiceDependencies() {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] jaxWsHandlerFilterStrings =
			soapExtenderConfiguration.jaxWsHandlerFilterStrings();

		if (jaxWsHandlerFilterStrings == null) {
			return;
		}

		for (String jaxWsHandlerFilterString : jaxWsHandlerFilterStrings) {
			addTCCLServiceDependency(
				false, Handler.class, jaxWsHandlerFilterString, "addHandler",
				"removeHandler");
		}
	}

	protected void addJaxWsServiceDependencies() {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] jaxWsServiceFilterStrings =
			soapExtenderConfiguration.jaxWsServiceFilterStrings();

		if (jaxWsServiceFilterStrings == null) {
			return;
		}

		for (String jaxWsServiceFilterString : jaxWsServiceFilterStrings) {
			addTCCLServiceDependency(
				false, null, jaxWsServiceFilterString, "addService",
				"removeService");
		}
	}

	protected void addSoapDescriptorBuilderServiceDependency() {
		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		serviceDependency.setDefaultImplementation(_soapDescriptorBuilder);
		serviceDependency.setCallbacks("setSoapDescriptorBuilder", "-");
		serviceDependency.setRequired(false);
		serviceDependency.setService(
			SoapDescriptorBuilder.class,
			_soapExtenderConfiguration.soapDescriptorBuilderFilter());

		_component.add(serviceDependency);
	}

	protected ServiceDependency addTCCLServiceDependency(
		boolean required, Class<?> clazz, String filterString, String addName,
		String removeName) {

		ServiceDependency serviceDependency =
			_dependencyManager.createTCCLServiceDependency();

		serviceDependency.setCallbacks(addName, removeName);
		serviceDependency.setRequired(required);

		if (clazz == null) {
			serviceDependency.setService(filterString);
		}
		else {
			serviceDependency.setService(clazz, filterString);
		}

		_component.add(serviceDependency);

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

	@Reference(policyOption = ReferencePolicyOption.GREEDY, unbind = "-")
	protected void setSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {

		_soapDescriptorBuilder = soapDescriptorBuilder;
	}

	private org.apache.felix.dm.Component _component;
	private TCCLDependencyManager _dependencyManager;
	private SoapDescriptorBuilder _soapDescriptorBuilder;
	private SoapExtenderConfiguration _soapExtenderConfiguration;

}