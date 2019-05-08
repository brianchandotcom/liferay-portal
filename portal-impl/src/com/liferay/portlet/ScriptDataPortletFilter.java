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

package com.liferay.portlet;

import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.internal.MimeResponseImpl;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletResponseWrapper;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class ScriptDataPortletFilter implements RenderFilter, ResourceFilter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		filterChain.doFilter(renderRequest, renderResponse);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isIsolated() && !themeDisplay.isStateExclusive()) {
			return;
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		_flushScriptData(
			scriptData, _getMimeResponseImpl(renderResponse),
			PortalUtil.getPortletId(renderRequest));
	}

	@Override
	public void doFilter(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			FilterChain filterChain)
		throws IOException, PortletException {

		filterChain.doFilter(resourceRequest, resourceResponse);

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(resourceRequest);

		ScriptData scriptData = (ScriptData)httpServletRequest.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData == null) {
			return;
		}

		_flushScriptData(
			scriptData, _getMimeResponseImpl(resourceResponse),
			PortalUtil.getPortletId(resourceRequest));
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	private void _flushScriptData(
			ScriptData scriptData, MimeResponseImpl mimeResponseImpl,
			String portletId)
		throws IOException {

		if (mimeResponseImpl.isCalledGetPortletOutputStream()) {
			OutputStream outputStream =
				mimeResponseImpl.getPortletOutputStream();

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				outputStream);

			scriptData.writeTo(outputStreamWriter, portletId);

			outputStreamWriter.flush();
		}
		else {
			scriptData.writeTo(mimeResponseImpl.getWriter(), portletId);
		}
	}

	private MimeResponseImpl _getMimeResponseImpl(MimeResponse mimeResponse) {
		while (!(mimeResponse instanceof MimeResponseImpl) &&
			   (mimeResponse instanceof PortletResponseWrapper)) {

			PortletResponseWrapper portletResponseWrapper =
				(PortletResponseWrapper)mimeResponse;

			mimeResponse = (MimeResponse)portletResponseWrapper.getResponse();
		}

		return (MimeResponseImpl)mimeResponse;
	}

}