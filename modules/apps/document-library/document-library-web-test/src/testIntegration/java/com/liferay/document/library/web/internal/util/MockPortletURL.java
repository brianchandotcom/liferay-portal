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

package com.liferay.document.library.web.internal.util;

import java.io.Writer;

import java.util.Map;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.WindowState;
import javax.portlet.annotations.PortletSerializable;

/**
 * @author Cristina González
 */
public class MockPortletURL implements PortletURL, RenderURL {

	@Override
	public void addProperty(String key, String value) {
	}

	@Override
	public Appendable append(Appendable appendable) {
		return null;
	}

	@Override
	public Appendable append(Appendable appendable, boolean escapeXML) {
		return null;
	}

	@Override
	public String getFragmentIdentifier() {
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return null;
	}

	@Override
	public PortletMode getPortletMode() {
		return null;
	}

	@Override
	public MutableRenderParameters getRenderParameters() {
		return null;
	}

	@Override
	public WindowState getWindowState() {
		return null;
	}

	@Override
	public void removePublicRenderParameter(String name) {
	}

	@Override
	public void setBeanParameter(PortletSerializable portletSerializable) {
	}

	@Override
	public void setFragmentIdentifier(String fragment) {
	}

	@Override
	public void setParameter(String name, String value) {
	}

	@Override
	public void setParameter(String name, String... values) {
	}

	@Override
	public void setParameters(Map<String, String[]> map) {
	}

	@Override
	public void setPortletMode(PortletMode portletMode) {
	}

	@Override
	public void setProperty(String key, String value) {
	}

	@Override
	public void setSecure(boolean secure) {
	}

	@Override
	public void setWindowState(WindowState windowState) {
	}

	@Override
	public void write(Writer writer) {
	}

	@Override
	public void write(Writer writer, boolean escapeXML) {
	}

}