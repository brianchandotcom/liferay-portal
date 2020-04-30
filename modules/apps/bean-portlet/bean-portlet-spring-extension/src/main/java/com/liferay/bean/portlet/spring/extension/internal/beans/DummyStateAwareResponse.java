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

package com.liferay.bean.portlet.spring.extension.internal.beans;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletMode;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public class DummyStateAwareResponse
	extends DummyPortletResponse implements StateAwareResponse {

	public static final StateAwareResponse INSTANCE =
		new DummyStateAwareResponse();

	@Override
	public PortletMode getPortletMode() {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public Map<String, String[]> getRenderParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public MutableRenderParameters getRenderParameters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public WindowState getWindowState() {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void removePublicRenderParameter(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEvent(QName qName, Serializable serializable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEvent(String name, Serializable serializable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPortletMode(PortletMode portletMode) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setRenderParameter(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setRenderParameter(String name, String... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setRenderParameters(Map<String, String[]> map) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWindowState(WindowState windowState) {
		throw new UnsupportedOperationException();
	}

}