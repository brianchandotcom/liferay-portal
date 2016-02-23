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

package com.liferay.portal.osgi.web.portlet.container.embedded.test;

import com.liferay.portal.model.impl.PortletImpl;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Manuel de la Peña
 */
public class TestNonembeddedPortlet extends PortletImpl implements Portlet {

	@Override
	public void destroy() {
	}

	@Override
	public String getPortletId() {
		Class<?> clazz = getClass();

		return clazz.getCanonicalName();
	}

	@Override
	public void init(PortletConfig portletConfig) {
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
		ActionRequest actionRequest, ActionResponse actionResponse) {
	}

	@Override
	public void render(
		RenderRequest renderRequest, RenderResponse renderResponse) {
	}

}