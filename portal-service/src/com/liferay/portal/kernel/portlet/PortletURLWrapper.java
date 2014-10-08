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

package com.liferay.portal.kernel.portlet;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletURLWrapper implements PortletURL {

	public PortletURLWrapper(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	public void addProperty(String key, String value) {
		_portletURL.addProperty(key, value);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _portletURL.getParameterMap();
	}

	@Override
	public PortletMode getPortletMode() {
		return _portletURL.getPortletMode();
	}

	@Override
	public WindowState getWindowState() {
		return _portletURL.getWindowState();
	}

	@Override
	public void removePublicRenderParameter(String name) {
		_portletURL.removePublicRenderParameter(name);
	}

	@Override
	public void setParameter(String name, String value) {
		_portletURL.setParameter(name, value);
	}

	@Override
	public void setParameter(String name, String[] values) {
		_portletURL.setParameter(name, values);
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		_portletURL.setParameters(parameters);
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		_portletURL.setPortletMode(portletMode);
	}

	@Override
	public void setProperty(String key, String value) {
		_portletURL.setProperty(key, value);
	}

	@Override
	public void setSecure(boolean secure) throws PortletSecurityException {
		_portletURL.setSecure(secure);
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		_portletURL.setWindowState(windowState);
	}

	@Override
	public String toString() {
		return _portletURL.toString();
	}

	@Override
	public void write(Writer writer) throws IOException {
		_portletURL.write(writer);
	}

	@Override
	public void write(Writer writer, boolean escapeXML) throws IOException {
		_portletURL.write(writer, escapeXML);
	}

	private final PortletURL _portletURL;

}