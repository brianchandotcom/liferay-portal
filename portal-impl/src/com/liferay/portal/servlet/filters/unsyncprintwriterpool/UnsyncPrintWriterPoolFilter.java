/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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