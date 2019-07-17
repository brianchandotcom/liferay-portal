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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			doServeResource(resourceRequest, resourceResponse);

			return !SessionErrors.isEmpty(resourceRequest);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected abstract void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception;

	protected PortletConfig getPortletConfig(ResourceRequest resourceRequest) {
		return PortletConfigFactoryUtil.get(
			PortalUtil.getPortletId(resourceRequest));
	}

	protected PortletRequestDispatcher getPortletRequestDispatcher(
		ResourceRequest resourceRequest, String path) {

		PortletConfig portletConfig = getPortletConfig(resourceRequest);

		PortletContext portletContext = portletConfig.getPortletContext();

		return portletContext.getRequestDispatcher(path);
	}

	protected void include(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			String jspPath)
		throws IOException, PortletException {

		PortletConfig portletConfig = getPortletConfig(resourceRequest);

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(jspPath);

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

}