/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.threadlocal;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.util.CentralizedThreadLocal;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ThreadLocalFilter
	extends BasePortalFilter implements TryFinallyFilter {

	@Override
	public void doFilterFinally(
		HttpServletRequest request, HttpServletResponse response,
		Object ojbect) {

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	@Override
	public Object doFilterTry(
		HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}