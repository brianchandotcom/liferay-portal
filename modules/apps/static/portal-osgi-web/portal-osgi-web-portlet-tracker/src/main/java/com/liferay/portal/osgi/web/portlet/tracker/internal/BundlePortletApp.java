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

package com.liferay.portal.osgi.web.portlet.tracker.internal;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.model.EventDefinition;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletFilter;
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.SpriteImage;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.osgi.web.servlet.context.helper.ServletContextHelperRegistration;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
public class BundlePortletApp implements PortletApp {

	public BundlePortletApp(
		Bundle bundle, Portlet portalPortletModel,
		ServiceTracker<ServletContextHelperRegistration, ServletContext>
			serviceTracker) {

		_portalPortletModel = portalPortletModel;
		_serviceTracker = serviceTracker;

		_pluginPackage = new BundlePluginPackage(bundle, this);
		_portletApp = portalPortletModel.getPortletApp();
	}

	@Override
	public void addEventDefinition(EventDefinition eventDefinition) {
		_portletApp.addEventDefinition(eventDefinition);
	}

	@Override
	public void addPortlet(Portlet portlet) {
		_portletApp.addPortlet(portlet);
	}

	@Override
	public void addPortletFilter(PortletFilter portletFilter) {
		_portletApp.addPortletFilter(portletFilter);
	}

	@Override
	public void addPortletURLListener(PortletURLListener portletURLListener) {
		_portletApp.addPortletURLListener(portletURLListener);
	}

	@Override
	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_portletApp.addPublicRenderParameter(publicRenderParameter);
	}

	@Override
	public void addPublicRenderParameter(String identifier, QName qName) {
		_portletApp.addPublicRenderParameter(identifier, qName);
	}

	@Override
	public void addServletURLPatterns(Set<String> servletURLPatterns) {
		_portletApp.addServletURLPatterns(servletURLPatterns);
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return _portletApp.getContainerRuntimeOptions();
	}

	@Override
	public String getContextPath() {
		ServletContext servletContext = getServletContext();

		return servletContext.getContextPath();
	}

	@Override
	public Map<String, String> getCustomUserAttributes() {
		return _portletApp.getCustomUserAttributes();
	}

	@Override
	public String getDefaultNamespace() {
		return _portletApp.getDefaultNamespace();
	}

	@Override
	public Set<EventDefinition> getEventDefinitions() {
		return _portletApp.getEventDefinitions();
	}

	public BundlePluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	@Override
	public PortletFilter getPortletFilter(String filterName) {
		return _portletApp.getPortletFilter(filterName);
	}

	@Override
	public Set<PortletFilter> getPortletFilters() {
		return _portletApp.getPortletFilters();
	}

	@Override
	public List<Portlet> getPortlets() {
		return _portletApp.getPortlets();
	}

	@Override
	public PortletURLListener getPortletURLListener(String listenerClass) {
		return _portletApp.getPortletURLListener(listenerClass);
	}

	@Override
	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletApp.getPortletURLListeners();
	}

	@Override
	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _portletApp.getPublicRenderParameter(identifier);
	}

	public Map<String, String> getRoleMappers() {
		return _portalPortletModel.getRoleMappers();
	}

	@Override
	public ServletContext getServletContext() {
		try {
			return _serviceTracker.waitForService(0);
		}
		catch (InterruptedException ie) {
			return ReflectionUtil.throwException(ie);
		}
	}

	@Override
	public String getServletContextName() {
		ServletContext servletContext = getServletContext();

		return servletContext.getServletContextName();
	}

	@Override
	public Set<String> getServletURLPatterns() {
		return _portletApp.getServletURLPatterns();
	}

	@Override
	public SpriteImage getSpriteImage(String fileName) {
		return _portletApp.getSpriteImage(fileName);
	}

	@Override
	public Set<String> getUserAttributes() {
		return _portletApp.getUserAttributes();
	}

	@Override
	public boolean isWARFile() {
		return true;
	}

	@Override
	public void removePortlet(Portlet portletModel) {
		_portletApp.removePortlet(portletModel);
	}

	@Override
	public void setDefaultNamespace(String defaultNamespace) {
		_portletApp.setDefaultNamespace(defaultNamespace);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSpriteImages(String spriteFileName, Properties properties) {
		_portletApp.setSpriteImages(spriteFileName, properties);
	}

	@Override
	public void setWARFile(boolean warFile) {
		_portletApp.setWARFile(warFile);
	}

	private final BundlePluginPackage _pluginPackage;
	private final Portlet _portalPortletModel;
	private final PortletApp _portletApp;
	private final ServiceTracker
		<ServletContextHelperRegistration, ServletContext> _serviceTracker;

}