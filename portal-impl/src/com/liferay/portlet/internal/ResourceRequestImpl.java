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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayResourceRequest;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.AsyncPortletServletRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletAsyncContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceParameters;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class ResourceRequestImpl
	extends ClientDataRequestImpl implements LiferayResourceRequest {

	@Override
	public void defineObjects(
		PortletConfig portletConfig, PortletResponse portletResponse) {

		super.defineObjects(portletConfig, portletResponse);

		_resourceResponse = (ResourceResponse)portletResponse;
	}

	@Override
	public String getCacheability() {
		return _cacheablity;
	}

	@Override
	public DispatcherType getDispatcherType() {
		ThemeDisplay themeDisplay = (ThemeDisplay)getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isAsync()) {
			return DispatcherType.ASYNC;
		}

		return DispatcherType.REQUEST;
	}

	@Override
	public String getETag() {
		return null;
	}

	@Override
	public String getLifecycle() {
		return PortletRequest.RESOURCE_PHASE;
	}

	@Override
	public PortletAsyncContext getPortletAsyncContext() {
		if (!isAsyncSupported() ||
			(!isAsyncStarted() && (_portletAsyncContextImpl == null))) {

			throw new IllegalStateException();
		}

		return _portletAsyncContextImpl;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             javax.portlet.RenderState#getRenderParameters()}
	 */
	@Deprecated
	@Override
	public Map<String, String[]> getPrivateRenderParameterMap() {
		Map<String, String[]> privateRenderParameters = new HashMap<>();

		RenderParametersImpl liferayRenderParametersImpl =
			(RenderParametersImpl)getRenderParameters();

		Map<String, String[]> liferayRenderParametersMap =
			liferayRenderParametersImpl.getParameterMap();

		for (Map.Entry<String, String[]> entry :
				liferayRenderParametersMap.entrySet()) {

			String renderParameterName = entry.getKey();

			if (!liferayRenderParametersImpl.isPublic(renderParameterName)) {
				privateRenderParameters.put(
					renderParameterName, entry.getValue());
			}
		}

		if (privateRenderParameters.isEmpty()) {
			return Collections.emptyMap();
		}

		return Collections.unmodifiableMap(privateRenderParameters);
	}

	@Override
	public String getResourceID() {
		return _resourceID;
	}

	@Override
	public ResourceParameters getResourceParameters() {
		if (getPortletSpecMajorVersion() < 3) {
			throw new UnsupportedOperationException("Requires 3.0 opt-in");
		}

		return _resourceParameters;
	}

	@Override
	public void init(
		HttpServletRequest httpServletRequest, Portlet portlet,
		InvokerPortlet invokerPortlet, PortletContext portletContext,
		WindowState windowState, PortletMode portletMode,
		PortletPreferences preferences, long plid) {

		if (Validator.isNull(windowState.toString())) {
			windowState = WindowState.NORMAL;
		}

		if (Validator.isNull(portletMode.toString())) {
			portletMode = PortletMode.VIEW;
		}

		super.init(
			httpServletRequest, portlet, invokerPortlet, portletContext,
			windowState, portletMode, preferences, plid);

		_cacheablity = ParamUtil.getString(
			httpServletRequest, "p_p_cacheability", ResourceURL.PAGE);

		_portletConfig = invokerPortlet.getPortletConfig();

		_resourceID = httpServletRequest.getParameter("p_p_resource_id");

		if (!PortalUtil.isValidResourceId(_resourceID)) {
			_resourceID = StringPool.BLANK;
		}

		if (getPortletSpecMajorVersion() >= 3) {
			String portletNamespace = PortalUtil.getPortletNamespace(
				getPortletName());

			_resourceParameters = new ResourceParametersImpl(
				getPortletParameterMap(httpServletRequest, portletNamespace),
				portletNamespace);
		}
	}

	@Override
	public boolean isAsyncStarted() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest(this);

		return httpServletRequest.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		HttpServletRequest httpServletRequest = _getHttpServletRequest(this);

		if (!httpServletRequest.isAsyncSupported()) {
			return false;
		}

		Portlet portlet = getPortlet();

		return portlet.isAsyncSupported();
	}

	@Override
	public PortletAsyncContext startPortletAsync()
		throws IllegalStateException {

		return startPortletAsync(this, _resourceResponse);
	}

	@Override
	public PortletAsyncContext startPortletAsync(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IllegalStateException {

		if (!isAsyncSupported() || isAsyncStarted()) {
			throw new IllegalStateException();
		}

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			resourceRequest);

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)getAttribute(
				PortletServlet.PORTLET_SERVLET_RESPONSE);

		if (httpServletResponse == null) {
			LiferayPortletResponse liferayPortletResponse =
				PortalUtil.getLiferayPortletResponse(resourceResponse);

			httpServletResponse =
				liferayPortletResponse.getHttpServletResponse();
		}

		if (_portletAsyncContextImpl == null) {
			_portletAsyncContextImpl = new PortletAsyncContextImpl();

			httpServletRequest = new AsyncPortletServletRequest(
				httpServletRequest);
		}

		AsyncContext asyncContext = httpServletRequest.startAsync(
			httpServletRequest, httpServletResponse);

		boolean hasOriginalRequestAndResponse = false;

		if ((resourceRequest == this) &&
			(resourceResponse == _resourceResponse)) {

			hasOriginalRequestAndResponse = true;
		}

		_portletAsyncContextImpl.initialize(
			resourceRequest, resourceResponse, _portletConfig, asyncContext,
			hasOriginalRequestAndResponse);

		// The portletConfig is already set by PortletRequestImpl.defineObjects

		if (!hasOriginalRequestAndResponse) {
			setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, resourceRequest);
			setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE, resourceResponse);
		}

		return _portletAsyncContextImpl;
	}

	private HttpServletRequest _getHttpServletRequest(
		ResourceRequest resourceRequest) {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)getAttribute(
				PortletServlet.PORTLET_SERVLET_REQUEST);

		if (httpServletRequest != null) {
			return httpServletRequest;
		}

		if (resourceRequest == this) {
			return getHttpServletRequest();
		}

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(resourceRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	private String _cacheablity;
	private PortletAsyncContextImpl _portletAsyncContextImpl;
	private PortletConfig _portletConfig;
	private String _resourceID;
	private ResourceParameters _resourceParameters;
	private ResourceResponse _resourceResponse;

}