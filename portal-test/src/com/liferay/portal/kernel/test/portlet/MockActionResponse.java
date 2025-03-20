/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.portlet;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.RenderURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Dante Wang
 */
public class MockActionResponse
	extends MockStateAwareResponse implements ActionResponse {

	@Override
	public RenderURL createRedirectURL(MimeResponse.Copy copy)
		throws IllegalStateException {

		return new MockRenderURL(getPortalContext(), copy);
	}

	public String getRedirectedUrl() {
		return _redirectedUrl;
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (!_redirectAllowed) {
			throw new IllegalStateException(
				"Cannot call sendRedirect after windowState, portletMode, or " +
					"renderParameters have been set");
		}

		_redirectedUrl = location;
	}

	@Override
	public void sendRedirect(String location, String renderUrlParamName)
		throws IOException {

		sendRedirect(location);

		if (renderUrlParamName != null) {
			setRenderParameter(renderUrlParamName, location);
		}
	}

	@Override
	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_redirectedUrl != null) {
			throw new IllegalStateException(
				"Cannot set PortletMode after sendRedirect has been called");
		}

		super.setPortletMode(portletMode);

		_redirectAllowed = false;
	}

	@Override
	public void setRenderParameter(String key, String value) {
		if (_redirectedUrl != null) {
			throw new IllegalStateException(
				"Cannot set render parameters after sendRedirect has been " +
					"called");
		}

		super.setRenderParameter(key, value);

		_redirectAllowed = false;
	}

	@Override
	public void setRenderParameter(String key, String[] values) {
		if (_redirectedUrl != null) {
			throw new IllegalStateException(
				"Cannot set render parameters after sendRedirect has been " +
					"called");
		}

		super.setRenderParameter(key, values);

		_redirectAllowed = false;
	}

	@Override
	public void setRenderParameters(Map<String, String[]> parameters) {
		if (_redirectedUrl != null) {
			throw new IllegalStateException(
				"Cannot set render parameters after sendRedirect has been " +
					"called");
		}

		super.setRenderParameters(parameters);

		_redirectAllowed = false;
	}

	@Override
	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_redirectedUrl != null) {
			throw new IllegalStateException(
				"Cannot set WindowState after sendRedirect has been called");
		}

		super.setWindowState(windowState);

		_redirectAllowed = false;
	}

	private boolean _redirectAllowed = true;
	private String _redirectedUrl;

}