/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.test;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.petra.concurrent.NoticeableThreadPoolExecutor;
import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class SyncDestinationUtil {

	public static SafeCloseable sync() {
		Destination destination = MessageBusUtil.getDestination(
			AntivirusAsyncConstants.ANTIVIRUS_DESTINATION);

		Object originalExecutor = ReflectionTestUtil.getAndSetFieldValue(
			destination, "_noticeableThreadPoolExecutor",
			_syncNoticeableThreadPoolExecutor);

		return new SafeCloseable() {

			@Override
			public void close() {
				ReflectionTestUtil.setFieldValue(
					destination, "_noticeableThreadPoolExecutor",
					originalExecutor);
			}

		};
	}

	private static final NoticeableThreadPoolExecutor
		_syncNoticeableThreadPoolExecutor = new NoticeableThreadPoolExecutor(
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