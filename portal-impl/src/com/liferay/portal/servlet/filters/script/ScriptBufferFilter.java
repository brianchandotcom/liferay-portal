/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.script;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.taglib.aui.AUIUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class ScriptBufferFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		ScriptBufferFilter.class.getName() + "SKIP_FILTER";

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (super.isFilterEnabled() && !isAlreadyFiltered(request) ) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (_log.isDebugEnabled()) {
			String completeURL = HttpUtil.getCompleteURL(request);

			_log.debug("Flushing scripts buffer for " + completeURL);
		}

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response);

		processFilter(
			ScriptBufferFilter.class, request, bufferCacheServletResponse,
			filterChain);

		String content = bufferCacheServletResponse.getString();

		String contentType = response.getContentType();

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.TEXT_HTML)) {

			String scriptData = AUIUtil.getScriptData(request);

			if (content.indexOf(_CLOSE_BODY) > -1) {
				content = StringUtil.replace(
					content, _CLOSE_BODY, scriptData.concat(_CLOSE_BODY));
			}
			else {
				content = content.concat(scriptData);
			}

			request.setAttribute(WebKeys.SCRIPT_DATA_OUTPUTED, true);
		}

		ServletResponseUtil.write(response, content);
	}

	private static final String _CLOSE_BODY = "</body>";

	private static Log _log = LogFactoryUtil.getLog(ScriptBufferFilter.class);

}