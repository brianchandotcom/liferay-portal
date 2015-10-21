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

package com.liferay.portal.portlet.container.test;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Raymond Augé
 */
public class TestPortlet extends GenericPortlet {

	public boolean isActionCalled() {
		return _actionCalled;
	}

	public boolean isRenderCalled() {
		return _renderCalled;
	}

	public boolean isResourceCalled() {
		return _resourceCalled;
	}

	@Override
	public void processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		_actionCalled = true;
	}

	@Override
	@SuppressWarnings("unused")
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_renderCalled = true;
	}

	public void reset() {
		_actionCalled = false;
		_renderCalled = false;
		_resourceCalled = false;
	}

	@Override
	@SuppressWarnings("unused")
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		_resourceCalled = true;
	}

	private boolean _actionCalled;
	private boolean _renderCalled;
	private boolean _resourceCalled;

}