/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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