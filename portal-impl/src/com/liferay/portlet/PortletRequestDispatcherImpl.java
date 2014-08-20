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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequestDispatcher;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.servlet.NamespaceServletRequest;
import com.liferay.portal.struts.StrutsURLEncoder;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Raymond Augé
 */
public class PortletRequestDispatcherImpl
	implements LiferayPortletRequestDispatcher {

	public PortletRequestDispatcherImpl(
		RequestDispatcher requestDispatcher, boolean named,
		PortletContext portletContext) {

		this(requestDispatcher, named, portletContext, null);
	}

	public PortletRequestDispatcherImpl(
		RequestDispatcher requestDispatcher, boolean named,
		PortletContext portletContext, String path) {

		_requestDispatcher = requestDispatcher;
		_named = named;
		_liferayPortletContext = (LiferayPortletContext)portletContext;
		_path = path;

		_portlet = _liferayPortletContext.getPortlet();
	}

	@Override
	public void forward(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IllegalStateException, IOException, PortletException {

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		if (response.isCommitted()) {
			throw new IllegalStateException("Response is already committed");
		}

		try {
			dispatch(portletRequest, portletResponse, false, false);
		}
		catch (ServletException se) {
			_log.error(se, se);

			throw new PortletException(se);
		}
	}

	@Override
	public void include(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws IOException, PortletException {

		try {
			dispatch(portletRequest, portletResponse, false, true);
		}
		catch (ServletException se) {
			_log.error(se, se);

			throw new PortletException(se);
		}
	}

	@Override
	public void include(
			PortletRequest portletRequest, PortletResponse portletResponse,
			boolean strutsURLEncoder)
		throws IOException, PortletException {

		try {
			dispatch(portletRequest, portletResponse, strutsURLEncoder, true);
		}
		catch (ServletException se) {
			_log.error(se, se);

			throw new PortletException(se);
		}
	}

	@Override
	public void include(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			dispatch(renderRequest, renderResponse, false, true);
		}
		catch (ServletException se) {
			_log.error(se, se);

			throw new PortletException(se);
		}
	}

	protected void dispatch(
			PortletRequest portletRequest, PortletResponse portletResponse,
			boolean strutsURLEncoder, boolean include)
		throws IOException, ServletException {

		if (!include) {
			if (portletResponse instanceof MimeResponseImpl) {
				MimeResponseImpl mimeResponseImpl =
					(MimeResponseImpl)portletResponse;

				if (mimeResponseImpl.isCalledFlushBuffer()) {
					throw new IllegalStateException();
				}
			}
		}

		PortletRequestImpl portletRequestImpl =
			PortletRequestImpl.getPortletRequestImpl(portletRequest);
		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);
		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, portletResponse);

		String pathInfo = null;
		String queryString = null;
		String requestURI = null;
		String servletPath = null;

		if (_path != null) {
			String pathNoQueryString = _path;

			int pos = _path.indexOf(CharPool.QUESTION);

			if (pos != -1) {
				pathNoQueryString = _path.substring(0, pos);
				queryString = _path.substring(pos + 1);

				Map<String, String[]> queryParams =
					new HashMap<String, String[]>();

				String[] queryParamsArray = StringUtil.split(
					queryString, CharPool.AMPERSAND);

				for (String element : queryParamsArray) {
					String[] nameValuePair = StringUtil.split(
						element, CharPool.EQUAL);

					String name = nameValuePair[0];
					String value = StringPool.BLANK;

					if (nameValuePair.length == 2) {
						value = nameValuePair[1];
					}

					String[] values = queryParams.get(name);

					if (values == null) {
						queryParams.put(name, new String[] {value});
					}
					else {
						String[] newValues = new String[values.length + 1];

						System.arraycopy(
							values, 0, newValues, 0, values.length);

						newValues[newValues.length - 1] = value;

						queryParams.put(name, newValues);
					}
				}

				DynamicServletRequest dynamicRequest = null;

				if (portletRequestImpl.isPrivateRequestAttributes()) {
					String portletNamespace = PortalUtil.getPortletNamespace(
						portletRequestImpl.getPortletName());

					dynamicRequest = new NamespaceServletRequest(
						request, portletNamespace, portletNamespace);
				}
				else {
					dynamicRequest = new DynamicServletRequest(request);
				}

				for (Map.Entry<String, String[]> entry :
						queryParams.entrySet()) {

					String name = entry.getKey();
					String[] values = entry.getValue();

					String[] oldValues = dynamicRequest.getParameterValues(
						name);

					if (oldValues == null) {
						dynamicRequest.setParameterValues(name, values);
					}
					else {
						String[] newValues = ArrayUtil.append(
							values, oldValues);

						dynamicRequest.setParameterValues(name, newValues);
					}
				}

				request = dynamicRequest;
			}

			Portlet portlet = portletRequestImpl.getPortlet();

			PortletApp portletApp = portlet.getPortletApp();

			Set<String> servletURLPatterns = portletApp.getServletURLPatterns();

			for (String urlPattern : servletURLPatterns) {
				if (urlPattern.endsWith("/*")) {
					pos = urlPattern.indexOf("/*");

					urlPattern = urlPattern.substring(0, pos);

					if (pathNoQueryString.startsWith(urlPattern)) {
						pathInfo = pathNoQueryString.substring(
							urlPattern.length());
						servletPath = urlPattern;

						break;
					}
				}
			}

			if ((pathInfo == null) && (servletPath == null)) {
				pathInfo = pathNoQueryString;
			}

			String contextPath = portletRequest.getContextPath();

			if (contextPath.equals(StringPool.SLASH)) {
				requestURI = pathNoQueryString;
			}
			else {
				requestURI = contextPath + pathNoQueryString;
			}
		}

		PortletServletRequest portletServletRequest = new PortletServletRequest(
			request, portletRequest, pathInfo, queryString, requestURI,
			servletPath, _named, include);

		PortletServletResponse portletServletResponse =
			new PortletServletResponse(response, portletResponse, include);

		URLEncoder urlEncoder = _portlet.getURLEncoderInstance();

		if (urlEncoder != null) {
			portletResponseImpl.setURLEncoder(urlEncoder);
		}
		else if (strutsURLEncoder) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			URLEncoder strutsURLEncoderObj = new StrutsURLEncoder(
				portletServletRequest.getContextPath(),
				themeDisplay.getPathMain(),
				(String)_liferayPortletContext.getAttribute(
					Globals.SERVLET_KEY),
				(LiferayPortletURL)portletResponseImpl.createRenderURL());

			portletResponseImpl.setURLEncoder(strutsURLEncoderObj);
		}

		if (include) {
			_requestDispatcher.include(
				portletServletRequest, portletServletResponse);
		}
		else {
			_requestDispatcher.forward(
				portletServletRequest, portletServletResponse);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletRequestDispatcherImpl.class);

	private final LiferayPortletContext _liferayPortletContext;
	private final boolean _named;
	private final String _path;
	private final Portlet _portlet;
	private final RequestDispatcher _requestDispatcher;

}