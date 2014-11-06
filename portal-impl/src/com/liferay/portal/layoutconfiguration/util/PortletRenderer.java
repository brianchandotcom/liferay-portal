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

package com.liferay.portal.layoutconfiguration.util;

import com.liferay.portal.kernel.executor.CopyThreadLocalCallable;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;

import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class PortletRenderer {

	public PortletRenderer(
		Portlet portlet, String columnId, Integer columnCount,
		Integer columnPos) {

		_portlet = portlet;
		_columnId = columnId;
		_columnCount = columnCount;
		_columnPos = columnPos;
	}

	public void finishParallelRender() {
		if (_restrictPortletServletRequest != null) {
			_restrictPortletServletRequest.mergeSharedAttributes();
		}
	}

	public Callable<StringBundler> getCallable(
		HttpServletRequest request, HttpServletResponse response) {

		return new PortletRendererCallable(request, response);
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public StringBundler render(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, null, _columnId, _columnPos, _columnCount);

		return _render(request, response);
	}

	public StringBundler renderAjax(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, _RENDER_PATH, _columnId, _columnPos, _columnCount);

		_restrictPortletServletRequest = (RestrictPortletServletRequest)request;

		return _render(request, response);
	}

	public StringBundler renderError(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		request.setAttribute(
			WebKeys.PARALLEL_RENDERING_TIMEOUT_ERROR, Boolean.TRUE);

		request = PortletContainerUtil.setupOptionalRenderParameters(
			request, null, _columnId, _columnPos, _columnCount);

		_restrictPortletServletRequest = (RestrictPortletServletRequest)request;

		try {
			return _render(request, response);
		}
		finally {
			request.removeAttribute(WebKeys.PARALLEL_RENDERING_TIMEOUT_ERROR);
		}
	}

	private StringBundler _render(
			HttpServletRequest request, HttpServletResponse response)
		throws PortletContainerException {

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(response);

		Object lock = request.getAttribute(
			WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

		request.setAttribute(WebKeys.PARALLEL_RENDERING_MERGE_LOCK, null);

		Object portletParallelRender = request.getAttribute(
			WebKeys.PORTLET_PARALLEL_RENDER);

		request.setAttribute(WebKeys.PORTLET_PARALLEL_RENDER, Boolean.FALSE);

		try {
			PortletContainerUtil.render(
				request, bufferCacheServletResponse, _portlet);

			return bufferCacheServletResponse.getStringBundler();
		}
		catch (IOException ioe) {
			throw new PortletContainerException(ioe);
		}
		finally {
			request.setAttribute(WebKeys.PARALLEL_RENDERING_MERGE_LOCK, lock);
			request.setAttribute(
				WebKeys.PORTLET_PARALLEL_RENDER, portletParallelRender);
		}
	}

	private static final String _RENDER_PATH =
		"/html/portal/load_render_portlet.jsp";

	private final Integer _columnCount;
	private final String _columnId;
	private final Integer _columnPos;
	private final Portlet _portlet;
	private RestrictPortletServletRequest _restrictPortletServletRequest;

	private class PortletRendererCallable
		extends CopyThreadLocalCallable<StringBundler> {

		public PortletRendererCallable(
			HttpServletRequest request, HttpServletResponse response) {

			super(
				ParallelRenderThreadLocalBinderUtil.getThreadLocalBinder(),
				false, true);

			_request = request;
			_response = response;
		}

		@Override
		public StringBundler doCall() throws Exception {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			HttpServletRequest request =
				PortletContainerUtil.setupOptionalRenderParameters(
					_request, null, _columnId, _columnPos, _columnCount);

			_restrictPortletServletRequest =
				(RestrictPortletServletRequest)request;

			try {
				themeDisplay = (ThemeDisplay)themeDisplay.clone();

				request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

				return _render(request, _response);
			}
			catch (Exception e) {

				// Under parallel rendering context. An interrupted state means
				// the call was cancelled and so we should not rethrow the
				// exception.

				Thread currentThread = Thread.currentThread();

				if (!currentThread.isInterrupted()) {
					throw e;
				}

				return null;
			}
		}

		private final HttpServletRequest _request;
		private final HttpServletResponse _response;
	}

}