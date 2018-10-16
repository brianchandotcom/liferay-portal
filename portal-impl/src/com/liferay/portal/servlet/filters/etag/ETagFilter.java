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

package com.liferay.portal.servlet.filters.etag;

import com.liferay.portal.kernel.servlet.RestrictedByteBufferCacheServletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.nio.ByteBuffer;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class ETagFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		ETagFilter.class.getName() + "#SKIP_FILTER";

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		if (ParamUtil.getBoolean(request, _ETAG, true) &&
			!isAlreadyFiltered(request)) {

			return true;
		}

		return false;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}

		return false;
	}

	protected boolean isEligibleForETag(int status) {
		if ((status >= HttpServletResponse.SC_OK) &&
			(status < HttpServletResponse.SC_MULTIPLE_CHOICES)) {

			return true;
		}

		return false;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		request.setAttribute(SKIP_FILTER, Boolean.TRUE);

		RestrictedByteBufferCacheServletResponse
			restrictedByteBufferCacheServletResponse =
				new RestrictedByteBufferCacheServletResponse(
					response, PropsValues.ETAG_RESPONSE_SIZE_MAX);

		processFilter(
			ETagFilter.class.getName(), request,
			restrictedByteBufferCacheServletResponse, filterChain);

		if (request.isAsyncSupported() && request.isAsyncStarted()) {
			AsyncContext asyncContext = request.getAsyncContext();

			asyncContext.addListener(
				new ETagFilterAsyncListener(
					asyncContext, request, response,
					restrictedByteBufferCacheServletResponse));
		}
		else {
			_postProcessETag(
				request, response, restrictedByteBufferCacheServletResponse);
		}
	}

	private void _postProcessETag(
			HttpServletRequest request, HttpServletResponse response,
			RestrictedByteBufferCacheServletResponse
				restrictedByteBufferCacheServletResponse)
		throws IOException {

		if (!restrictedByteBufferCacheServletResponse.isOverflowed()) {
			ByteBuffer byteBuffer =
				restrictedByteBufferCacheServletResponse.getByteBuffer();

			if (!isEligibleForETag(
					restrictedByteBufferCacheServletResponse.getStatus()) ||
				!ETagUtil.processETag(request, response, byteBuffer)) {

				restrictedByteBufferCacheServletResponse.flushCache();
			}
		}
	}

	private static final String _ETAG = "etag";

	private class ETagFilterAsyncListener implements AsyncListener {

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			_postProcessETag(
				_httpServletRequest, _httpServletResponse,
				_restrictedByteBufferCacheServletResponse);
		}

		@Override
		public void onError(AsyncEvent event) {
		}

		@Override
		public void onStartAsync(AsyncEvent event) {
			_asyncContext.addListener(this);
		}

		@Override
		public void onTimeout(AsyncEvent event) {
		}

		private ETagFilterAsyncListener(
			AsyncContext asyncContext, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			RestrictedByteBufferCacheServletResponse
				restrictedByteBufferCacheServletResponse) {

			_asyncContext = asyncContext;
			_httpServletRequest = httpServletRequest;
			_httpServletResponse = httpServletResponse;
			_restrictedByteBufferCacheServletResponse =
				restrictedByteBufferCacheServletResponse;
		}

		private final AsyncContext _asyncContext;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;
		private final RestrictedByteBufferCacheServletResponse
			_restrictedByteBufferCacheServletResponse;

	}

}