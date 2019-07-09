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
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (ParamUtil.getBoolean(httpServletRequest, _ETAG, true) &&
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

	protected boolean isEligibleForETag(int status) {
		if ((status >= HttpServletResponse.SC_OK) &&
			(status < HttpServletResponse.SC_MULTIPLE_CHOICES)) {

			return true;
		}

		return false;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		httpServletRequest.setAttribute(SKIP_FILTER, Boolean.TRUE);

		RestrictedByteBufferCacheServletResponse
			restrictedByteBufferCacheServletResponse =
				new RestrictedByteBufferCacheServletResponse(
					httpServletResponse, PropsValues.ETAG_RESPONSE_SIZE_MAX);

		processFilter(
			ETagFilter.class.getName(), httpServletRequest,
			restrictedByteBufferCacheServletResponse, filterChain);

		if (httpServletRequest.isAsyncSupported() &&
			httpServletRequest.isAsyncStarted()) {

			AsyncContext asyncContext = httpServletRequest.getAsyncContext();

			asyncContext.addListener(
				new ETagFilterAsyncListener(
					asyncContext, httpServletRequest, httpServletResponse,
					restrictedByteBufferCacheServletResponse));
		}
		else {
			_postProcessETag(
				httpServletRequest, httpServletResponse,
				restrictedByteBufferCacheServletResponse);
		}
	}

	private void _postProcessETag(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			RestrictedByteBufferCacheServletResponse
				restrictedByteBufferCacheServletResponse)
		throws IOException {

		if (!restrictedByteBufferCacheServletResponse.isOverflowed() &&
			(!isEligibleForETag(
				restrictedByteBufferCacheServletResponse.getStatus()) ||
			 !ETagUtil.processETag(
				 httpServletRequest, httpServletResponse,
				 restrictedByteBufferCacheServletResponse.getByteBuffer()))) {

			restrictedByteBufferCacheServletResponse.flushCache();
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