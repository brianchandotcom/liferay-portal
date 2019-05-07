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

package com.liferay.portal.servlet.filters.unsyncprintwriterpool;

import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class UnsyncPrintWriterPoolFilter
	extends BasePortalFilter implements TryFinallyFilter {

	@Override
	public void doFilterFinally(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Object object) {

		if (httpServletRequest.isAsyncSupported() &&
			httpServletRequest.isAsyncStarted()) {

			AsyncContext asyncContext = httpServletRequest.getAsyncContext();

			asyncContext.addListener(
				new UnsyncPrintWriterPoolFilterAsyncListener(asyncContext));
		}
		else {
			UnsyncPrintWriterPool.cleanUp();
		}
	}

	@Override
	public Object doFilterTry(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		UnsyncPrintWriterPool.setEnabled(true);

		return null;
	}

	private static class UnsyncPrintWriterPoolFilterAsyncListener
		implements AsyncListener {

		@Override
		public void onComplete(AsyncEvent asyncEvent) {
			UnsyncPrintWriterPool.cleanUp();
		}

		@Override
		public void onError(AsyncEvent asyncEvent) {
		}

		@Override
		public void onStartAsync(AsyncEvent asyncEvent) {
			_asyncContext.addListener(this);
		}

		@Override
		public void onTimeout(AsyncEvent asyncEvent) {
		}

		private UnsyncPrintWriterPoolFilterAsyncListener(
			AsyncContext asyncContext) {

			_asyncContext = asyncContext;
		}

		private final AsyncContext _asyncContext;

	}

}