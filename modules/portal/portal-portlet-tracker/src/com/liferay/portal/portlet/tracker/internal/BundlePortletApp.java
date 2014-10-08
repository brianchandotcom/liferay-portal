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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.SpriteImage;

import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class BundlePortletApp extends ServletContextHelper
	implements PortletApp {

	public BundlePortletApp(
		Bundle bundle, Portlet portalPortletModel, String servletContextName,
		String contextPath, String httpServiceEndpoint) {

		super(bundle);

		_portalPortletModel = portalPortletModel;
		_servletContextName = servletContextName;

		if ((httpServiceEndpoint.length() > 0) &&
			httpServiceEndpoint.endsWith("/")) {

			httpServiceEndpoint = httpServiceEndpoint.substring(
				0, httpServiceEndpoint.length() - 1);
		}

		_contextPath = httpServiceEndpoint + contextPath;
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
		return _contextPath;
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

	@Override
	public URL getResource(String name) {
		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		URL url = super.getResource("META-INF/resources/" + name);

		if (url != null) {
			return url;
		}

		return super.getResource(name);
	}

	public Map<String, String> getRoleMappers() {
		return _portalPortletModel.getRoleMappers();
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
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
		return false;
	}

	@Override
	public void setDefaultNamespace(String defaultNamespace) {
		_portletApp.setDefaultNamespace(defaultNamespace);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public void setSpriteImages(String spriteFileName, Properties properties) {
		_portletApp.setSpriteImages(spriteFileName, properties);
	}

	@Override
	public void setWARFile(boolean warFile) {
		_portletApp.setWARFile(warFile);
	}

	private final String _contextPath;
	private final BundlePluginPackage _pluginPackage;
	private final Portlet _portalPortletModel;
	private final PortletApp _portletApp;
	private ServletContext _servletContext;
	private final String _servletContextName;

}