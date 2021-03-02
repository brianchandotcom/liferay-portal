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

package com.liferay.petra.portlet.url.impl;

import com.liferay.petra.portlet.url.PortletURL;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.annotations.PortletSerializable;

/**
 * @author Hugo Huijser
 */
public class PortletURLImpl implements PortletURL {

	public PortletURLImpl(LiferayPortletURL liferayPortletURL) {
		_liferayPortletURL = liferayPortletURL;
	}

	@Override
	public void addProperty(String key, String value) {
		_liferayPortletURL.addProperty(key, value);
	}

	@Override
	public Appendable append(Appendable out) throws IOException {
		return _liferayPortletURL.append(out);
	}

	@Override
	public Appendable append(Appendable out, boolean escapeXML)
		throws IOException {

		return _liferayPortletURL.append(out, escapeXML);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _liferayPortletURL.getParameterMap();
	}

	@Override
	public PortletMode getPortletMode() {
		return _liferayPortletURL.getPortletMode();
	}

	@Override
	public MutableRenderParameters getRenderParameters() {
		return _liferayPortletURL.getRenderParameters();
	}

	@Override
	public WindowState getWindowState() {
		return _liferayPortletURL.getWindowState();
	}

	@Override
	public void removePublicRenderParameter(String name) {
		_liferayPortletURL.removePublicRenderParameter(name);
	}

	@Override
	public void setBeanParameter(PortletSerializable portletSerializable) {
		_liferayPortletURL.setBeanParameter(portletSerializable);
	}

	@Override
	public void setParameter(String name, Object value) {
		_liferayPortletURL.setParameter(name, String.valueOf(value));
	}

	@Override
	public void setParameter(String name, String value) {
		_liferayPortletURL.setParameter(name, value);
	}

	@Override
	public void setParameter(String name, String... values) {
		_liferayPortletURL.setParameter(name, values);
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
		_liferayPortletURL.setParameters(parameters);
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		_liferayPortletURL.setPortletMode(portletMode);
	}

	@Override
	public void setProperty(String key, String value) {
		_liferayPortletURL.setProperty(key, value);
	}

	@Override
	public void setSecure(boolean secure) throws PortletSecurityException {
		_liferayPortletURL.setSecure(secure);
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		_liferayPortletURL.setWindowState(windowState);
	}

	@Override
	public void write(Writer out) throws IOException {
		_liferayPortletURL.write(out);
	}

	@Override
	public void write(Writer out, boolean escapeXML) throws IOException {
		_liferayPortletURL.write(out, escapeXML);
	}

	private final LiferayPortletURL _liferayPortletURL;

}