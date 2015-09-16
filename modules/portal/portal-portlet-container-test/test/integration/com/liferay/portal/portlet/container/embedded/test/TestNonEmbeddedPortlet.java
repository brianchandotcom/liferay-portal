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

package com.liferay.portal.portlet.container.embedded.test;

import com.liferay.portal.model.impl.PortletImpl;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Manuel de la Peña
 */
public class TestNonEmbeddedPortlet extends PortletImpl implements Portlet {

	@Override
	public void destroy() {

		// NOOP

	}

	@Override
	public String getPortletId() {
		return getClass().getCanonicalName();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {

		// NOOP

	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public boolean isUndeployedPortlet() {
		return true;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		// NOOP

	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		// NOOP

	}

}