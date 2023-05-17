/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.servlet.filter;

import com.liferay.osb.faro.engine.client.util.TokenUtil;
import com.liferay.osb.faro.web.internal.util.ServletRequestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = {
		"osgi.http.whiteboard.filter.name=com.liferay.osb.faro.web.internal.servlet.filter.EmailServletFilter",
		"osgi.http.whiteboard.filter.pattern=/email/*"
	},
	service = Filter.class
)
public class EmailServletFilter extends BaseFilter {

	@Override
	protected Log getLog() {
		return _log;
	}

	protected boolean isInvalidRequest(HttpServletRequest httpServletRequest) {
		String faroBackendSecuritySignature = httpServletRequest.getHeader(
			"OSB-Asah-Faro-Backend-Security-Signature");

		if (faroBackendSecuritySignature == null) {
			_logInvalidRequest(null, httpServletRequest);

			return true;
		}

		if (!Objects.equals(
				faroBackendSecuritySignature,
				DigestUtils.sha256Hex(
					StringUtil.merge(
						Arrays.asList(
							TokenUtil.getOSBAsahSecurityToken(),
							ServletRequestUtil.getOriginalURL(
								httpServletRequest)),
						StringPool.BLANK)))) {

			_logInvalidRequest(
				faroBackendSecuritySignature, httpServletRequest);

			return true;
		}

		return false;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (isInvalidRequest(httpServletRequest)) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);

			return;
		}

		Class<?> clazz = getClass();

		processFilter(
			clazz.getName(), httpServletRequest, httpServletResponse,
			filterChain);
	}

	private void _logInvalidRequest(
		String faroBackendSecuritySignature,
		HttpServletRequest httpServletRequest) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"%s attempted to access %s with an invalid security " +
						"signature %s",
					httpServletRequest.getRemoteAddr(),
					httpServletRequest.getRequestURI(),
					faroBackendSecuritySignature));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmailServletFilter.class);

}