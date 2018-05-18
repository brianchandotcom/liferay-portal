/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.content.search.web.internal.display.context;

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
 * @author Marco Leo
 */
public class NullPortletURL implements PortletURL {

	@Override
	public void addProperty(String key, String value) {
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
	public WindowState getWindowState() {
		return null;
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
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {
	}

	@Override
	public void setProperty(String key, String value) {
	}

	@Override
	public void setSecure(boolean secure) throws PortletSecurityException {
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {
	}

	@Override
	public void write(Writer out) throws IOException {
	}

	@Override
	public void write(Writer out, boolean escapeXML) throws IOException {
	}

}