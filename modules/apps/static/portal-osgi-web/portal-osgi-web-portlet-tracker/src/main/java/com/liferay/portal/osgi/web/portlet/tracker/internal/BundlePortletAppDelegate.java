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
import com.liferay.portal.kernel.model.PortletURLListener;
import com.liferay.portal.kernel.model.SpriteImage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class BundlePortletAppDelegate {

	public BundlePortletAppDelegate(
		Portlet portalPortletModel, ServletContext servletContext) {

		_servletContext = servletContext;
		_portletApp = portalPortletModel.getPortletApp();
	}

	public void addEventDefinition(EventDefinition eventDefinition) {
		_eventDefinitions.add(eventDefinition);
	}

	public void addPortletURLListener(PortletURLListener portletURLListener) {
		_portletURLListeners.add(portletURLListener);
		_portletURLListenersMap.put(
			portletURLListener.getListenerClass(), portletURLListener);
	}

	public String getContextPath() {
		ServletContext servletContext = getServletContext();

		return servletContext.getContextPath();
	}

	public String getDefaultNamespace() {
		if (_defaultNamespace == null) {
			return _portletApp.getDefaultNamespace();
		}

		return _defaultNamespace;
	}

	public Set<EventDefinition> getEventDefinitions() {
		return _eventDefinitions;
	}

	public PortletURLListener getPortletURLListener(String listenerClass) {
		return _portletURLListenersMap.get(listenerClass);
	}

	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletURLListeners;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public String getServletContextName() {
		ServletContext servletContext = getServletContext();

		return servletContext.getServletContextName();
	}

	public int getSpecMajorVersion() {
		return _specMajorVersion;
	}

	public int getSpecMinorVersion() {
		return _specMinorVersion;
	}

	public SpriteImage getSpriteImage(String fileName) {
		return _spriteImagesMap.get(fileName);
	}

	public boolean isWARFile() {
		return _warFile;
	}

	public void setDefaultNamespace(String defaultNamespace) {
		if (Validator.isNull(defaultNamespace)) {
			_defaultNamespace = null;
		}
		else {
			_defaultNamespace = defaultNamespace;
		}
	}

	public void setServletContext(ServletContext servletContext) {
		throw new UnsupportedOperationException();
	}

	public void setSpecMajorVersion(int specMajorVersion) {
		_specMajorVersion = specMajorVersion;
	}

	public void setSpecMinorVersion(int specMinorVersion) {
		_specMinorVersion = specMinorVersion;
	}

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

	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	private String _defaultNamespace;
	private final Set<EventDefinition> _eventDefinitions = new HashSet<>();
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