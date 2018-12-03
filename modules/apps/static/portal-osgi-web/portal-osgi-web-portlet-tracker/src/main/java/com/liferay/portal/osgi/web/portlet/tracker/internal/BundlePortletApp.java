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

import com.liferay.portal.kernel.model.EventDefinition;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletFilter;
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.SpriteImage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Raymond Augé
 */
public class BundlePortletApp implements PortletApp {

	public BundlePortletApp(
		Bundle bundle, Portlet portalPortletModel,
		ServletContext servletContext) {

		_portalPortletModel = portalPortletModel;
		_servletContext = servletContext;

		_pluginPackage = new BundlePluginPackage(bundle, this);
		_portletApp = portalPortletModel.getPortletApp();
	}

	@Override
	public void addEventDefinition(EventDefinition eventDefinition) {
		_eventDefinitions.add(eventDefinition);
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
		_portletURLListeners.add(portletURLListener);
		_portletURLListenersMap.put(
			portletURLListener.getListenerClass(), portletURLListener);
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
		if (_defaultNamespace == null) {
			return _portletApp.getDefaultNamespace();
		}

		return _defaultNamespace;
	}

	@Override
	public Set<EventDefinition> getEventDefinitions() {
		return _eventDefinitions;
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
		return _portletURLListenersMap.get(listenerClass);
	}

	@Override
	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletURLListeners;
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
		return _servletContext;
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
	public int getSpecMajorVersion() {
		return _specMajorVersion;
	}

	@Override
	public int getSpecMinorVersion() {
		return _specMinorVersion;
	}

	@Override
	public SpriteImage getSpriteImage(String fileName) {
		return _spriteImagesMap.get(fileName);
	}

	@Override
	public Set<String> getUserAttributes() {
		return _portletApp.getUserAttributes();
	}

	@Override
	public boolean isWARFile() {
		return _warFile;
	}

	@Override
	public void removePortlet(Portlet portletModel) {
		_portletApp.removePortlet(portletModel);
	}

	@Override
	public void setDefaultNamespace(String defaultNamespace) {
		if (Validator.isNull(defaultNamespace)) {
			_defaultNamespace = null;
		}
		else {
			_defaultNamespace = defaultNamespace;
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSpecMajorVersion(int specMajorVersion) {
		_specMajorVersion = specMajorVersion;
	}

	@Override
	public void setSpecMinorVersion(int specMinorVersion) {
		_specMinorVersion = specMinorVersion;
	}

	@Override
	public void setSpriteImages(String spriteFileName, Properties properties) {
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			String value = (String)entry.getValue();

			int[] values = StringUtil.split(value, 0);

			int offset = values[0];
			int height = values[1];
			int width = values[2];

			SpriteImage spriteImage = new SpriteImage(
				spriteFileName, key, offset, height, width);

			_spriteImagesMap.put(key, spriteImage);
		}
	}

	@Override
	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	private String _defaultNamespace;
	private final Set<EventDefinition> _eventDefinitions = new HashSet<>();
	private final BundlePluginPackage _pluginPackage;
	private final Portlet _portalPortletModel;
	private final PortletApp _portletApp;
	private final Set<PortletURLListener> _portletURLListeners =
		new LinkedHashSet<>();
	private final Map<String, PortletURLListener> _portletURLListenersMap =
		new HashMap<>();
	private final ServletContext _servletContext;
	private int _specMajorVersion = 2;
	private int _specMinorVersion;
	private final Map<String, SpriteImage> _spriteImagesMap = new HashMap<>();
	private boolean _warFile = true;

}