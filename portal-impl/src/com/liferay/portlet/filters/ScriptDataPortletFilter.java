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

package com.liferay.portlet.filters;

import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.MimeResponseImpl;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class ScriptDataPortletFilter implements RenderFilter, ResourceFilter {

	public void destroy() {
	}

	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		filterChain.doFilter(renderRequest, renderResponse);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		ScriptData scriptData = (ScriptData)request.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isIsolated() || themeDisplay.isStateExclusive()) {
			_flushScriptData(
				request, scriptData, (MimeResponseImpl)renderResponse);
		}
	}

	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		filterChain.doFilter(resourceRequest, resourceResponse);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		ScriptData scriptData = (ScriptData)request.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		_flushScriptData(
			request, scriptData, (MimeResponseImpl)resourceResponse);
	}

	public void init(FilterConfig filterConfig) throws PortletException {
	}

	private void _flushScriptData(
			HttpServletRequest request, ScriptData scriptData,
			MimeResponseImpl mimeResponseImpl)
		throws IOException {

		if (mimeResponseImpl.isCalledGetPortletOutputStream()) {
			OutputStream outputStream =
				mimeResponseImpl.getPortletOutputStream();

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream);

			scriptData.writeTo(request, outputStreamWriter);

			outputStreamWriter.flush();
		}
		else {
			scriptData.writeTo(request, mimeResponseImpl.getWriter());
		}
	}

}