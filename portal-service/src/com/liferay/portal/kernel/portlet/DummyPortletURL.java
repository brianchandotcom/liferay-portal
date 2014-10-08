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

import com.liferay.portal.kernel.util.StringPool;

import java.io.Writer;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyPortletURL implements PortletURL {

	public static DummyPortletURL getInstance() {
		return _instance;
	}

	@Override
	public void addProperty(String key, String value) {
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return Collections.emptyMap();
	}

	@Override
	public PortletMode getPortletMode() {
		return PortletMode.VIEW;
	}

	@Override
	public WindowState getWindowState() {
		return WindowState.NORMAL;
	}

	@Override
	public void removePublicRenderParameter(String name) {
	}

	@Override
	public void setParameter(String name, String value) {
	}

	@Override
	public void setParameter(String name, String[] values) {
	}

	@Override
	public void setParameters(Map<String, String[]> parameters) {
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
	public String toString() {
		return StringPool.BLANK;
	}

	@Override
	public void write(Writer writer) {
	}

	@Override
	public void write(Writer writer, boolean escapeXML) {
	}

	private DummyPortletURL() {
	}

	private static final DummyPortletURL _instance = new DummyPortletURL();

}