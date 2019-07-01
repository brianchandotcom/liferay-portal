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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = {})
public class SearchHttpUtil {

	public static String getCompleteOriginalURL(
		HttpServletRequest httpServletRequest) {

		boolean forwarded = false;

		Object requestURLObject = httpServletRequest.getAttribute(
			JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI);

		if (requestURLObject != null) {
			forwarded = true;
		}

		String requestURL = null;
		String queryString = null;

		if (forwarded) {
			requestURL = _portal.getAbsoluteURL(
				httpServletRequest,
				(String)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI));

			queryString = (String)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
		}
		else {
			requestURL = String.valueOf(httpServletRequest.getRequestURL());

			queryString = httpServletRequest.getQueryString();
		}

		StringBuffer sb = new StringBuffer();

		sb.append(requestURL);

		if (queryString != null) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		String proxyPath = _portal.getPathProxy();

		if (Validator.isNotNull(proxyPath)) {
			int x =
				sb.indexOf(Http.PROTOCOL_DELIMITER) +
					Http.PROTOCOL_DELIMITER.length();

			int y = sb.indexOf(StringPool.SLASH, x);

			sb.insert(y, proxyPath);
		}

		String completeURL = sb.toString();

		if (httpServletRequest.isRequestedSessionIdFromURL()) {
			HttpSession session = httpServletRequest.getSession();

			String sessionId = session.getId();

			completeURL = _portal.getURLWithSessionId(completeURL, sessionId);
		}

		if (_log.isWarnEnabled() && completeURL.contains("?&")) {
			_log.warn("Invalid URL " + completeURL);
		}

		return completeURL;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private static final Log _log = LogFactoryUtil.getLog(SearchHttpUtil.class);

	private static Portal _portal;

}