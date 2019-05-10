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

import com.liferay.petra.concurrent.NoticeableThreadPoolExecutor;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Preston Crary
 */
public class PortalExecutorManagerInvocationHandler
	implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		if (Objects.equals(method.getName(), "getPortalExecutor")) {
			return new NoticeableThreadPoolExecutor(
				1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
				Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy(),
				new ThreadPoolHandlerAdapter()) {

				@Override
				public void execute(Runnable runnable) {
					runnable.run();
				}

			};
		}

		throw new UnsupportedOperationException();
	}

}