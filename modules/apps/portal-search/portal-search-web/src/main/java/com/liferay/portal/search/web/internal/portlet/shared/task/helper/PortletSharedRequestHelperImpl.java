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

package com.liferay.portal.search.web.internal.portlet.shared.task.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = PortletSharedRequestHelper.class)
public class PortletSharedRequestHelperImpl
	implements PortletSharedRequestHelper {

	@Override
	public <T> T getAttribute(String name, RenderRequest renderRequest) {
		return getAttribute(name, _getSharedHttpServletRequest(renderRequest));
	}

	@Override
	public String getCompleteURL(RenderRequest renderRequest) {
		return _getCompleteOriginalURL(
			portal.getHttpServletRequest(renderRequest));
	}

	@Override
	public String getParameter(String name, RenderRequest renderRequest) {
		HttpServletRequest httpServletRequest = _getSharedHttpServletRequest(
			renderRequest);

		String parameterValue = StringUtil.trim(
			httpServletRequest.getParameter(name));

		if (Validator.isBlank(parameterValue)) {
			return null;
		}

		return parameterValue;
	}

	@Override
	public String[] getParameterValues(
		String name, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = _getSharedHttpServletRequest(
			renderRequest);

		String[] parameterValues = httpServletRequest.getParameterValues(name);

		if (ArrayUtil.isEmpty(parameterValues)) {
			return null;
		}

		return parameterValues;
	}

	@Override
	public void setAttribute(
		String name, Object attributeValue, RenderRequest renderRequest) {

		HttpServletRequest httpServletRequest = _getSharedHttpServletRequest(
			renderRequest);

		httpServletRequest.setAttribute(name, attributeValue);
	}

	@SuppressWarnings("unchecked")
	protected <T> T getAttribute(
		String name, HttpServletRequest httpServletRequest) {

		return (T)httpServletRequest.getAttribute(name);
	}

	@Reference
	protected Portal portal;

	private String _getCompleteOriginalURL(
		HttpServletRequest httpServletRequest) {

		String requestURL = null;
		String queryString = null;

		if (HttpComponentsUtil.isForwarded(httpServletRequest)) {
			requestURL = portal.getAbsoluteURL(
				httpServletRequest,
				(String)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI));

			queryString = (String)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}
		else {
			requestURL = portal.getAbsoluteURL(
				httpServletRequest,
				HttpComponentsUtil.getPath(
					String.valueOf(httpServletRequest.getRequestURL())));

			queryString = httpServletRequest.getQueryString();
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

		if (httpServletRequest.isRequestedSessionIdFromURL()) {
			HttpSession httpSession = httpServletRequest.getSession();

			String sessionId = httpSession.getId();

			completeURL = portal.getURLWithSessionId(completeURL, sessionId);
		}

		if (_log.isWarnEnabled() && completeURL.contains("?&")) {
			_log.warn("Invalid URL " + completeURL);
		}

		return completeURL;
	}

	private HttpServletRequest _getSharedHttpServletRequest(
		RenderRequest renderRequest) {

		return portal.getOriginalServletRequest(
			portal.getHttpServletRequest(renderRequest));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletSharedRequestHelperImpl.class);

}