/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.SpriteImage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.XMLConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletAppImpl implements PortletApp {

	public PortletAppImpl(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_contextPath = StringPool.SLASH.concat(_servletContextName);
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	@Override
	public void addEventDefinition(EventDefinition eventDefinition) {
		_eventDefinitions.add(eventDefinition);
	}

	@Override
	public void addPortlet(Portlet portlet) {
		_portlets.add(portlet);
	}

	@Override
	public void addPortletFilter(PortletFilter portletFilter) {
		_portletFilters.add(portletFilter);
		_portletFiltersByFilterName.put(
			portletFilter.getFilterName(), portletFilter);
	}

	@Override
	public void addPortletURLListener(PortletURLListener portletURLListener) {
		_portletURLListeners.add(portletURLListener);
		_portletURLListenersByListenerClass.put(
			portletURLListener.getListenerClass(), portletURLListener);
	}

	@Override
	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_publicRenderParametersByIdentifier.put(
			publicRenderParameter.getIdentifier(), publicRenderParameter);
	}

	@Override
	public void addPublicRenderParameter(String identifier, QName qName) {
		PublicRenderParameter publicRenderParameter =
			new PublicRenderParameterImpl(identifier, qName, this);

		addPublicRenderParameter(publicRenderParameter);
	}

	@Override
	public void addServletURLPatterns(Set<String> servletURLPatterns) {
		_servletURLPatterns.addAll(servletURLPatterns);
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return _containerRuntimeOptions;
	}

	@Override
	public String getContextPath() {
		return _contextPath;
	}

	@Override
	public Map<String, String> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	@Override
	public String getDefaultNamespace() {
		return _defaultNamespace;
	}

	@Override
	public Set<EventDefinition> getEventDefinitions() {
		return _eventDefinitions;
	}

	@Override
	public PortletFilter getPortletFilter(String filterName) {
		return _portletFiltersByFilterName.get(filterName);
	}

	@Override
	public Set<PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	@Override
	public List<Portlet> getPortlets() {
		return _portlets;
	}

	@Override
	public PortletURLListener getPortletURLListener(String listenerClass) {
		return _portletURLListenersByListenerClass.get(listenerClass);
	}

	@Override
	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletURLListeners;
	}

	@Override
	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _publicRenderParametersByIdentifier.get(identifier);
	}

	@Override
	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public Set<String> getServletURLPatterns() {
		return _servletURLPatterns;
	}

	@Override
	public SpriteImage getSpriteImage(String fileName) {
		return _spriteImagesMap.get(fileName);
	}

	@Override
	public Set<String> getUserAttributes() {
		return _userAttributes;
	}

	@Override
	public boolean isWARFile() {
		return _warFile;
	}

	@Override
	public void setDefaultNamespace(String defaultNamespace) {
		_defaultNamespace = defaultNamespace;
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

	private Map<String, String[]> _containerRuntimeOptions =
		new HashMap<String, String[]>();
	private String _contextPath = StringPool.BLANK;
	private Map<String, String> _customUserAttributes =
		new LinkedHashMap<String, String>();
	private String _defaultNamespace = XMLConstants.NULL_NS_URI;
	private Set<EventDefinition> _eventDefinitions =
		new LinkedHashSet<EventDefinition>();
	private Set<PortletFilter> _portletFilters =
		new LinkedHashSet<PortletFilter>();
	private Map<String, PortletFilter> _portletFiltersByFilterName =
		new HashMap<String, PortletFilter>();
	private List<Portlet> _portlets = new UniqueList<Portlet>();
	private Set<PortletURLListener> _portletURLListeners =
		new LinkedHashSet<PortletURLListener>();
	private Map<String, PortletURLListener>
		_portletURLListenersByListenerClass =
			new HashMap<String, PortletURLListener>();
	private Map<String, PublicRenderParameter>
		_publicRenderParametersByIdentifier =
			new HashMap<String, PublicRenderParameter>();
	private String _servletContextName = StringPool.BLANK;
	private Set<String> _servletURLPatterns = new LinkedHashSet<String>();
	private Map<String, SpriteImage> _spriteImagesMap =
		new HashMap<String, SpriteImage>();
	private Set<String> _userAttributes = new LinkedHashSet<String>();
	private boolean _warFile;

}