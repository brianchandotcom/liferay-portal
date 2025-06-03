/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseRequestHelper<T extends BaseRequestHelperInfo> {

	public abstract boolean isAcceptableRequest(
		HttpServletRequest httpServletRequest);

	public void process(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		T baseRequestHelperInfo = getBaseRequestHelperInfo(httpServletRequest);

		if (baseRequestHelperInfo == null) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);

			return;
		}

		String eTag = baseRequestHelperInfo.getETag();

		if (eTag != null) {
			if (StringUtil.equals(
					httpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH),
					eTag)) {

				httpServletResponse.setStatus(
					HttpServletResponse.SC_NOT_MODIFIED);

				return;
			}

			httpServletResponse.setHeader(HttpHeaders.ETAG, eTag);
		}

		if (baseRequestHelperInfo.isImmutable()) {
			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL,
				"immutable, max-age=31536000, public");
		}
		else {
			StringBuilder sb = new StringBuilder();

			if (baseRequestHelperInfo.isSendNoCache()) {
				sb.append("no-cache, ");
			}
			else {
				sb.append("must-revalidate, ");
			}

			sb.append("max-age=");
			sb.append(baseRequestHelperInfo.getMaxAge());
			sb.append(", public");

			httpServletResponse.setHeader(
				HttpHeaders.CACHE_CONTROL, sb.toString());
		}

		sendContent(
			httpServletRequest, httpServletResponse, baseRequestHelperInfo);
	}

	protected abstract T getBaseRequestHelperInfo(
		HttpServletRequest httpServletRequest);

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

	protected abstract void sendContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T baseRequestHelperInfo)
		throws IOException, ServletException;

}