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

package com.liferay.portal.kernel.test.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayResourceRequest;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;

import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.RenderParameters;
import javax.portlet.ResourceParameters;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.mock.web.portlet.MockResourceRequest;

/**
 * @author Cristina González
 */
public class MockLiferayResourceRequest
	extends MockResourceRequest implements LiferayResourceRequest {

	@Override
	public void cleanUp() {
	}

	@Override
	public Map<String, String[]> clearRenderParameters() {
		return null;
	}

	@Override
	public void defineObjects(
		PortletConfig portletConfig, PortletResponse portletResponse) {
	}

	@Override
	public long getContentLengthLong() {
		return 0;
	}

	@Override
	public DispatcherType getDispatcherType() {
		return null;
	}

	@Override
	public HttpServletRequest getHttpServletRequest() {
		return null;
	}

	@Override
	public String getLifecycle() {
		return null;
	}

	@Override
	public HttpServletRequest getOriginalHttpServletRequest() {
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, PortletException {
		return null;
	}

	@Override
	public Collection<Part> getParts() throws IOException, PortletException {
		return null;
	}

	@Override
	public long getPlid() {
		return 0;
	}

	@Override
	public Portlet getPortlet() {
		return null;
	}

	@Override
	public PortletAsyncContext getPortletAsyncContext() {
		return null;
	}

	@Override
	public PortletContext getPortletContext() {
		return null;
	}

	@Override
	public String getPortletName() {
		return null;
	}

	@Override
	public HttpServletRequest getPortletRequestDispatcherRequest() {
		return null;
	}

	@Override
	public RenderParameters getRenderParameters() {
		return null;
	}

	@Override
	public ResourceParameters getResourceParameters() {
		return null;
	}

	@Override
	public String getUserAgent() {
		return null;
	}

	@Override
	public void invalidateSession() {
	}

	@Override
	public boolean isAsyncStarted() {
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		return false;
	}

	@Override
	public void setPortletRequestDispatcherRequest(
		HttpServletRequest httpServletRequest) {
	}

	@Override
	public PortletAsyncContext startPortletAsync()
		throws IllegalStateException {

		return null;
	}

	@Override
	public PortletAsyncContext startPortletAsync(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IllegalStateException {

		return null;
	}

}