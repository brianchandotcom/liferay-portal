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

package com.liferay.portal.search.web.internal.portlet.shared.task;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.util.SearchArrayUtil;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;

import java.util.Optional;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = PortletSharedRequestHelper.class)
public class PortletSharedRequestHelperImpl
	implements PortletSharedRequestHelper {

	@Override
	public <T> Optional<T> getAttribute(
		String name, RenderRequest renderRequest) {

		return Optional.ofNullable(
			getAttribute(name, getSharedRequest(renderRequest)));
	}

	@Override
	public String getCompleteURL(RenderRequest renderRequest) {
		return getCompleteOriginalURL(
			portal.getHttpServletRequest(renderRequest));
	}

	@Override
	public Optional<String> getParameter(
		String name, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		String parameter = httpServletRequest.getParameter(name);

		return SearchStringUtil.maybe(parameter);
	}

	@Override
	public Optional<String[]> getParameterValues(
		String name, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		String[] parameterValues = httpServletRequest.getParameterValues(name);

		return SearchArrayUtil.maybe(parameterValues);
	}

	@Override
	public void setAttribute(
		String name, Object attributeValue, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = getSharedRequest(renderRequest);

		httpServletRequest.setAttribute(name, attributeValue);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(
		String name, HttpServletRequest httpServletRequest) {

		return (T)httpServletRequest.getAttribute(name);
	}

	protected String getCompleteOriginalURL(HttpServletRequest request) {
		boolean forwarded = false;

		if (request.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI) != null) {

			forwarded = true;
		}

		String requestURL = null;
		String queryString = null;

		if (forwarded) {
			requestURL = portal.getAbsoluteURL(
				request,
				(String)request.getAttribute(
					JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI));

			queryString = (String)request.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}
		else {
			requestURL = String.valueOf(request.getRequestURL());

			queryString = request.getQueryString();
		}

		StringBuffer sb = new StringBuffer();

		sb.append(requestURL);

		if (queryString != null) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		String proxyPath = portal.getPathProxy();

		if (Validator.isNotNull(proxyPath)) {
			int x =
				sb.indexOf(Http.PROTOCOL_DELIMITER) +
					Http.PROTOCOL_DELIMITER.length();

			int y = sb.indexOf(StringPool.SLASH, x);

			sb.insert(y, proxyPath);
		}

		String completeURL = sb.toString();

		if (request.isRequestedSessionIdFromURL()) {
			HttpSession session = request.getSession();

			String sessionId = session.getId();

			completeURL = portal.getURLWithSessionId(completeURL, sessionId);
		}

		if (_log.isWarnEnabled()) {
			if (completeURL.contains("?&")) {
				_log.warn("Invalid url " + completeURL);
			}
		}

		return completeURL;
	}

	protected HttpServletRequest getSharedRequest(RenderRequest renderRequest) {
		return portal.getOriginalServletRequest(
			portal.getHttpServletRequest(renderRequest));
	}

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSharedRequestHelperImpl.class);

}