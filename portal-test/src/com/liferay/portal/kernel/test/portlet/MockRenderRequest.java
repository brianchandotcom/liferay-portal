/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.portlet;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.RenderParameters;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;

/**
 * @author Dante Wang
 */
public class MockRenderRequest
	extends MockPortletRequest implements RenderRequest {

	public MockRenderRequest() {
	}

	public MockRenderRequest(
		PortalContext portalContext, PortletContext portletContext) {

		super(portalContext, portletContext);
	}

	public MockRenderRequest(PortletContext portletContext) {
		super(portletContext);
	}

	public MockRenderRequest(PortletMode portletMode) {
		setPortletMode(portletMode);
	}

	public MockRenderRequest(
		PortletMode portletMode, MutableRenderParameters renderParameters) {

		this(portletMode);

		_renderParameters = renderParameters;
	}

	public MockRenderRequest(PortletMode portletMode, WindowState windowState) {
		setPortletMode(portletMode);
		setWindowState(windowState);
	}

	@Override
	public String getETag() {
		return getProperty("portlet.ETag");
	}

	@Override
	public RenderParameters getRenderParameters() {
		if (_renderParameters == null) {
			_renderParameters = new MockRenderParameters();
		}

		return _renderParameters;
	}

	@Override
	protected String getLifecyclePhase() {
		return "RENDER_PHASE";
	}

	private RenderParameters _renderParameters;

}