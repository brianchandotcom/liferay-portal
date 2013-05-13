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

package com.liferay.portal.servlet.filters.threaddump;

import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class ThreadDumpFilter
	extends BasePortalFilter implements TryFinallyFilter {

	@Override
	public void doFilterFinally(
		HttpServletRequest request, HttpServletResponse response,
		Object object) {

		ScheduledFuture<?> scheduledFuture = (ScheduledFuture<?>)object;

		scheduledFuture.cancel(true);
	}

	@Override
	public Object doFilterTry(
		HttpServletRequest request, HttpServletResponse response) {

		ScheduledFuture<?> scheduledFuture = _scheduledExecutorService.schedule(
			_threadDumper, PropsValues.THREAD_DUMP_SPEED_THRESHOLD,
			TimeUnit.SECONDS);

		return scheduledFuture;
	}

	private static final int _MAX_THREAD_DUMPERS = 5;

	private static ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(_MAX_THREAD_DUMPERS);
	private static ThreadDumper _threadDumper = new ThreadDumper();

}