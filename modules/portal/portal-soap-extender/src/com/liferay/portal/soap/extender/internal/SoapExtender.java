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
import com.liferay.portal.soap.extender.internal.configuration.SoapExtenderConfiguration;

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

	public org.apache.felix.dm.Component getComponent() {
		return _component;
	}

	public DependencyManager getDependencyManager() {
		return _dependencyManager;
	}

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

		CXFJaxWSServiceRegistrator1 jaxwsServiceRegistrator =
			new CXFJaxWSServiceRegistrator1();

		jaxwsServiceRegistrator.setSoapDescriptorBuilder(
			_soapDescriptorBuilder);

		_component.setImplementation(jaxwsServiceRegistrator);

		addBusDependencies();
		addHandlerDependencies();
		addServiceDependencies();
		addSoapDescriptorBuilderDependency();

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
			String contextPathFilterString =
				"(" + HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH +
					"=" + contextPath + ")";

			addTCCLDependency(
				true, Bus.class, contextPathFilterString, "addBus",
				"removeBus");
		}
	}

	protected void addHandlerDependencies() {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] handlers = soapExtenderConfiguration.jaxwsHandlersFilters();

		if (handlers == null) {
			return;
		}

		for (String handlerFilter : handlers) {
			addTCCLDependency(
				false, Handler.class, handlerFilter, "addHandler",
				"removeHandler");
		}
	}

	protected void addServiceDependencies() {
		SoapExtenderConfiguration soapExtenderConfiguration =
			getSoapExtenderConfiguration();

		String[] serviceFilters =
			soapExtenderConfiguration.jaxwsServiceFilters();

		if (serviceFilters == null) {
			return;
		}

		for (String serviceFilter : serviceFilters) {
			addTCCLDependency(
				false, null, serviceFilter, "addService", "removeService");
		}
	}

	protected void addSoapDescriptorBuilderDependency() {
		ServiceDependency serviceDependency =
			_dependencyManager.createServiceDependency();

		String descriptorBuilderFilter =
			_soapExtenderConfiguration.soapDescriptorBuilderFilter();

		serviceDependency.setDefaultImplementation(_soapDescriptorBuilder);

		serviceDependency.setCallbacks("setSoapDescriptorBuilder", "-");
		serviceDependency.setRequired(false);

		serviceDependency.setService(
			SoapDescriptorBuilder.class, descriptorBuilderFilter);

		_component.add(serviceDependency);
	}

	protected ServiceDependency addTCCLDependency(
		boolean required, Class<?> clazz, String filter, String addName,
		String removeName) {

		ServiceDependency serviceDependency =
			_dependencyManager.createTCCLServiceDependency();

		serviceDependency.setRequired(required);

		if (clazz == null) {
			serviceDependency.setService(filter);
		}
		else {
			serviceDependency.setService(clazz, filter);
		}

		serviceDependency.setCallbacks(addName, removeName);

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

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected void setSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {

		_soapDescriptorBuilder = soapDescriptorBuilder;
	}

	private org.apache.felix.dm.Component _component;
	private TCCLDependencyManager _dependencyManager;
	private SoapDescriptorBuilder _soapDescriptorBuilder;
	private SoapExtenderConfiguration _soapExtenderConfiguration;

}