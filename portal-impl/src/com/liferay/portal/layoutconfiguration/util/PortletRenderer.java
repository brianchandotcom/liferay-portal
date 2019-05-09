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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.RestrictPortletServletRequest;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.ThreadLocalBinder;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Neil Griffin
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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		Map<String, Object> headerRequestAttributes) {

		return new PortletRendererCallable(
			httpServletRequest, httpServletResponse, headerRequestAttributes);
	}

	public Portlet getPortlet() {
		return _portlet;
	}

	public StringBundler render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<String, Object> headerRequestAttributes)
		throws PortletContainerException {

		httpServletRequest = PortletContainerUtil.setupOptionalRenderParameters(
			httpServletRequest, null, _columnId, _columnPos, _columnCount);

		_copyHeaderRequestAttributes(
			headerRequestAttributes, httpServletRequest);

		return _render(httpServletRequest, httpServletResponse);
	}

	public StringBundler renderAjax(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortletContainerException {

		httpServletRequest = PortletContainerUtil.setupOptionalRenderParameters(
			httpServletRequest, _RENDER_PATH, _columnId, _columnPos,
			_columnCount);

		_restrictPortletServletRequest =
			(RestrictPortletServletRequest)httpServletRequest;

		return _render(httpServletRequest, httpServletResponse);
	}

	public StringBundler renderError(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortletContainerException {

		httpServletRequest = PortletContainerUtil.setupOptionalRenderParameters(
			httpServletRequest, null, _columnId, _columnPos, _columnCount);

		httpServletRequest.setAttribute(
			WebKeys.PARALLEL_RENDERING_TIMEOUT_ERROR, Boolean.TRUE);

		_restrictPortletServletRequest =
			(RestrictPortletServletRequest)httpServletRequest;

		try {
			return _render(httpServletRequest, httpServletResponse);
		}
		finally {
			httpServletRequest.removeAttribute(
				WebKeys.PARALLEL_RENDERING_TIMEOUT_ERROR);
		}
	}

	public Map<String, Object> renderHeaders(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			List<String> attributePrefixes)
		throws PortletContainerException {

		httpServletRequest = PortletContainerUtil.setupOptionalRenderParameters(
			httpServletRequest, null, _columnId, _columnPos, _columnCount);

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(httpServletResponse);

		PortletContainerUtil.renderHeaders(
			httpServletRequest, bufferCacheServletResponse, _portlet);

		Map<String, Object> headerRequestAttributes = new HashMap<>();

		Enumeration<String> attributeNames =
			httpServletRequest.getAttributeNames();

		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();

			if (attributeName.contains(
					"javax.portlet.faces.renderResponseOutput")) {

				headerRequestAttributes.put(
					attributeName,
					httpServletRequest.getAttribute(attributeName));
			}
			else if (attributePrefixes != null) {
				for (String attributePrefix : attributePrefixes) {
					if (attributeName.contains(attributePrefix)) {
						headerRequestAttributes.put(
							attributeName,
							httpServletRequest.getAttribute(attributeName));

						break;
					}
				}
			}
		}

		return headerRequestAttributes;
	}

	private void _copyHeaderRequestAttributes(
		Map<String, Object> headerRequestAttributes,
		HttpServletRequest httpServletRequest) {

		if (headerRequestAttributes != null) {
			for (Map.Entry<String, Object> entry :
					headerRequestAttributes.entrySet()) {

				httpServletRequest.setAttribute(
					entry.getKey(), entry.getValue());
			}
		}
	}

	private StringBundler _render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortletContainerException {

		BufferCacheServletResponse bufferCacheServletResponse =
			new BufferCacheServletResponse(httpServletResponse);

		Object lock = httpServletRequest.getAttribute(
			WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

		httpServletRequest.setAttribute(
			WebKeys.PARALLEL_RENDERING_MERGE_LOCK, null);

		Object portletParallelRender = httpServletRequest.getAttribute(
			WebKeys.PORTLET_PARALLEL_RENDER);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_PARALLEL_RENDER, Boolean.FALSE);

		try {
			PortletContainerUtil.render(
				httpServletRequest, bufferCacheServletResponse, _portlet);

			return bufferCacheServletResponse.getStringBundler();
		}
		catch (IOException ioe) {
			throw new PortletContainerException(ioe);
		}
		finally {
			httpServletRequest.setAttribute(
				WebKeys.PARALLEL_RENDERING_MERGE_LOCK, lock);
			httpServletRequest.setAttribute(
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

	private abstract class CopyThreadLocalCallable<T> implements Callable<T> {

		public CopyThreadLocalCallable(boolean readOnly, boolean clearOnExit) {
			this(null, readOnly, clearOnExit);
		}

		public CopyThreadLocalCallable(
			ThreadLocalBinder threadLocalBinder, boolean readOnly,
			boolean clearOnExit) {

			_threadLocalBinder = threadLocalBinder;

			if (_threadLocalBinder != null) {
				_threadLocalBinder.record();
			}

			if (readOnly) {
				_longLivedThreadLocals = Collections.unmodifiableMap(
					CentralizedThreadLocal.getLongLivedThreadLocals());
				_shortLivedlThreadLocals = Collections.unmodifiableMap(
					CentralizedThreadLocal.getShortLivedThreadLocals());
			}
			else {
				_longLivedThreadLocals =
					CentralizedThreadLocal.getLongLivedThreadLocals();
				_shortLivedlThreadLocals =
					CentralizedThreadLocal.getShortLivedThreadLocals();
			}

			_clearOnExit = clearOnExit;
		}

		@Override
		public final T call() throws Exception {
			CentralizedThreadLocal.setThreadLocals(
				_longLivedThreadLocals, _shortLivedlThreadLocals);

			if (_threadLocalBinder != null) {
				_threadLocalBinder.bind();
			}

			try {
				return doCall();
			}
			finally {
				if (_clearOnExit) {
					if (_threadLocalBinder != null) {
						_threadLocalBinder.cleanUp();
					}

					CentralizedThreadLocal.clearLongLivedThreadLocals();
					CentralizedThreadLocal.clearShortLivedThreadLocals();
				}
			}
		}

		public abstract T doCall() throws Exception;

		private final boolean _clearOnExit;
		private final Map<CentralizedThreadLocal<?>, Object>
			_longLivedThreadLocals;
		private final Map<CentralizedThreadLocal<?>, Object>
			_shortLivedlThreadLocals;
		private final ThreadLocalBinder _threadLocalBinder;

	}

	private class PortletRendererCallable
		extends CopyThreadLocalCallable<StringBundler> {

		public PortletRendererCallable(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Map<String, Object> headerRequestAttributes) {

			super(
				ParallelRenderThreadLocalBinderUtil.getThreadLocalBinder(),
				false, true);

			_httpServletRequest = httpServletRequest;
			_httpServletResponse = httpServletResponse;
			_headerRequestAttributes = headerRequestAttributes;
		}

		@Override
		public StringBundler doCall() throws Exception {
			HttpServletRequest httpServletRequest =
				PortletContainerUtil.setupOptionalRenderParameters(
					_httpServletRequest, null, _columnId, _columnPos,
					_columnCount);

			_copyHeaderRequestAttributes(
				_headerRequestAttributes, httpServletRequest);

			_restrictPortletServletRequest =
				(RestrictPortletServletRequest)httpServletRequest;

			try {
				_split(_httpServletRequest, _restrictPortletServletRequest);

				return _render(httpServletRequest, _httpServletResponse);
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

		private void _split(
			HttpServletRequest httpServletRequest,
			RestrictPortletServletRequest restrictPortletServletRequest) {

			Enumeration<String> attributeNames =
				httpServletRequest.getAttributeNames();

			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();

				Object attribute = httpServletRequest.getAttribute(
					attributeName);

				if (!(attribute instanceof Mergeable<?>) ||
					!RestrictPortletServletRequest.isSharedRequestAttribute(
						attributeName)) {

					continue;
				}

				Mergeable<?> mergeable = (Mergeable<?>)attribute;

				restrictPortletServletRequest.setAttribute(
					attributeName, mergeable.split());
			}
		}

		private final Map<String, Object> _headerRequestAttributes;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;

	}

}