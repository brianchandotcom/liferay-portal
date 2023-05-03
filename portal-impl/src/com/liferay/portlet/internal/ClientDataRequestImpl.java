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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.portlet.ClientDataRequestHelperUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletException;
import javax.portlet.RenderParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public abstract class ClientDataRequestImpl
	extends PortletRequestImpl implements ClientDataRequest {

	@Override
	public String getCharacterEncoding() {
		return getHttpServletRequest().getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return getHttpServletRequest().getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return getHttpServletRequest().getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return getHttpServletRequest().getContentType();
	}

	@Override
	public String getMethod() {
		return getHttpServletRequest().getMethod();
	}

	@Override
	public Part getPart(String name) throws IOException, PortletException {
		return ClientDataRequestHelperUtil.getPart(
			name, getHttpServletRequest(), getPortlet());
	}

	@Override
	public Collection<Part> getParts() throws IOException, PortletException {
		return ClientDataRequestHelperUtil.getParts(
			getHttpServletRequest(), getPortlet());
	}

	@Override
	public InputStream getPortletInputStream() throws IOException {
		_checkContentType();

		return getHttpServletRequest().getInputStream();
	}

	@Override
	public BufferedReader getReader()
		throws IOException, UnsupportedEncodingException {

		_calledGetReader = true;

		_checkContentType();

		return getHttpServletRequest().getReader();
	}

	@Override
	public void setCharacterEncoding(String enc)
		throws UnsupportedEncodingException {

		if (_calledGetReader) {
			throw new IllegalStateException();
		}

		getHttpServletRequest().setCharacterEncoding(enc);
	}

	protected Map<String, String[]> getPortletParameterMap(
		HttpServletRequest httpServletRequest, String portletNamespace) {

		Map<String, String[]> portletParameterMap = new LinkedHashMap<>();
		Map<String, String[]> parameterMap = getParameterMap();
		Map<String, String[]> servletRequestParameterMap =
			httpServletRequest.getParameterMap();

		RenderParameters renderParameters = getRenderParameters();

		Set<String> renderParameterNames = renderParameters.getNames();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			// If the parameter name is not a public/private render parameter,
			// then regard it as an action parameter (during an ActionRequest)
			// or as a resource parameter (during a ResourceRequest). Also,
			// if the parameter name is prefixed with the portlet namespace in
			// the original request, then regard it as an action or resource
			// parameter (even if it has the same name as a public render
			// parameter). See: TCK V3PortletParametersTests_SPEC11_3_getNames
			// and V3PortletParametersTests_SPEC11_4_getNames.

			if (renderParameterNames.contains(name)) {
				String[] values = servletRequestParameterMap.get(
					portletNamespace.concat(name));

				if (values != null) {
					portletParameterMap.put(name, values);
				}
			}
			else {
				portletParameterMap.put(name, entry.getValue());
			}
		}

		return portletParameterMap;
	}

	private void _checkContentType() {
		if (StringUtil.equalsIgnoreCase(getMethod(), HttpMethods.POST) &&
			StringUtil.equalsIgnoreCase(
				getContentType(),
				ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED)) {

			throw new IllegalStateException();
		}
	}

	private boolean _calledGetReader;

}