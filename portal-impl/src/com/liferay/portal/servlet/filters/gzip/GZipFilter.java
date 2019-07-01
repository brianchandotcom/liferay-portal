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

package com.liferay.portal.servlet.filters.gzip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class GZipFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		GZipFilter.class.getName() + "#SKIP_FILTER";

	public GZipFilter() {

		// The compression filter will work on JBoss, Tomcat, WebLogic,
		// and WebSphere, but may break on other servers

		boolean filterEnabled = false;

		if (super.isFilterEnabled() &&
			(ServerDetector.isJBoss() || ServerDetector.isTomcat() ||
			 ServerDetector.isWebLogic() || ServerDetector.isWebSphere())) {

			filterEnabled = true;
		}

		_filterEnabled = filterEnabled;
	}

	@Override
	public boolean isFilterEnabled() {
		return _filterEnabled;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (isCompress(httpServletRequest) && !isInclude(httpServletRequest) &&
			BrowserSnifferUtil.acceptsGzip(httpServletRequest) &&
			!isAlreadyFiltered(httpServletRequest)) {

			return true;
		}

		return false;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest httpServletRequest) {
		if (httpServletRequest.getAttribute(SKIP_FILTER) != null) {
			return true;
		}

		return false;
	}

	protected boolean isCompress(HttpServletRequest httpServletRequest) {
		if (ParamUtil.getBoolean(httpServletRequest, _COMPRESS, true)) {
			return true;
		}

		return false;
	}

	protected boolean isInclude(HttpServletRequest httpServletRequest) {
		String uri = (String)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);

		if (uri == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(httpServletRequest);

			_log.debug("Compressing " + completeURL);
		}

		httpServletRequest.setAttribute(SKIP_FILTER, Boolean.TRUE);

		GZipResponse gZipResponse = new GZipResponse(httpServletResponse);

		processFilter(
			GZipFilter.class.getName(), httpServletRequest, gZipResponse,
			filterChain);

		gZipResponse.finishResponse();
	}

	private static final String _COMPRESS = "compress";

	private static final Log _log = LogFactoryUtil.getLog(GZipFilter.class);

	private final boolean _filterEnabled;

}