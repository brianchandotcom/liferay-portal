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

package com.liferay.portal.sso.ntlm.servlet.filters;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;

import jcifs.util.Base64;

/**
 * @author Brian Wing Shun Chan
 */
public class NtlmPostFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		long companyId = PortalInstances.getCompanyId(request);

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.NTLM_AUTH_ENABLED,
				PropsValues.NTLM_AUTH_ENABLED) &&
			BrowserSnifferUtil.isIe(request) &&
			request.getMethod().equals(HttpMethods.POST)) {

			String authorization = GetterUtil.getString(
				request.getHeader(HttpHeaders.AUTHORIZATION));

			if (authorization.startsWith("NTLM ")) {
				byte[] src = Base64.decode(authorization.substring(5));

				if (src[8] == 1) {
					Type1Message type1 = new Type1Message(src);
					Type2Message type2 = new Type2Message(
						type1, new byte[8], null);

					authorization = Base64.encode(type2.toByteArray());

					response.setHeader(
						HttpHeaders.WWW_AUTHENTICATE, "NTLM " + authorization);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentLength(0);

					response.flushBuffer();

					return;
				}
			}
		}

		processFilter(NtlmPostFilter.class, request, response, filterChain);
	}

	private static final Log _log = LogFactoryUtil.getLog(NtlmPostFilter.class);

}