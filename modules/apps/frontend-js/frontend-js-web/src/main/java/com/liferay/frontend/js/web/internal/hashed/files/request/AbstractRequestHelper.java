/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public abstract class AbstractRequestHelper<T extends RequestInfo> {

	public abstract boolean isAcceptableRequest(
		HttpServletRequest httpServletRequest);

	public void process(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		T requestInfo = getRequestInfo(httpServletRequest);

		if (requestInfo == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String hash = requestInfo.getHash();

		if (hash != null) {
			if (StringUtil.equals(
					httpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH),
					hash)) {

				httpServletResponse.setStatus(
					HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}

			httpServletResponse.setHeader(HttpHeaders.ETAG, hash);
		}

		if (requestInfo.isVirtual()) {
			StringBuilder cacheControlSB = new StringBuilder();

			if (requestInfo.getSendNoCache()) {
				cacheControlSB.append("no-cache, ");
			}
			else {
				cacheControlSB.append("must-revalidate, ");
			}

			cacheControlSB.append("max-age=");
			cacheControlSB.append(requestInfo.getMaxAge());
			cacheControlSB.append(", public");

			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL, cacheControlSB.toString());
		}
		else {
			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				"immutable, max-age=31536000, public");
		}

		sendContent(httpServletRequest, httpServletResponse, requestInfo);
	}

	protected String getHash(String uri) {
		int i = uri.lastIndexOf(".(");

		if (i == -1) {
			return null;
		}

		int j = uri.lastIndexOf(").");

		if (j == -1) {
			return null;
		}

		return uri.substring(i + 2, j);
	}

	protected abstract T getRequestInfo(HttpServletRequest httpServletRequest);

	protected abstract void sendContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T requestInfo)
		throws IOException, ServletException;

}