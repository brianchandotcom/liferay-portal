/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

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