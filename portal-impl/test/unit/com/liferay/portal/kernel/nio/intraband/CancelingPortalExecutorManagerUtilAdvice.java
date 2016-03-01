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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;

import java.util.concurrent.Future;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Preston Crary
 */
@Aspect
public class CancelingPortalExecutorManagerUtilAdvice {

	@Around(
		"execution(* com.liferay.portal.kernel.executor." +
			"PortalExecutorManagerUtil.getPortalExecutor(..))"
	)
	public ThreadPoolExecutor getPortalExecutor() {
		return new ThreadPoolExecutor(0, 1) {

			@Override
			public void execute(Runnable runnable) {
				Future<?> future = (Future)runnable;

				future.cancel(true);
			}

		};
	}

	@Around(
		"execution(private com.liferay.portal.kernel.executor." +
			"PortalExecutorManagerUtil.new())"
	)
	public void PortalExecutorManagerUtil() {
	}

}