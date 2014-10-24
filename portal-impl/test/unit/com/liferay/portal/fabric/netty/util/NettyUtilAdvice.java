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

package com.liferay.portal.fabric.netty.util;

import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;

import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Shuyang Zhou
 */
@Aspect
public class NettyUtilAdvice {

	public static void shutdown() {
		_scheduledExecutorService.shutdown();
	}

	@Around(
		"execution(public static void com.liferay.portal.fabric.netty." +
			"util.NettyUtil.scheduleCancellation(io.netty.channel." +
				"Channel, com.liferay.portal.kernel.concurrent." +
					"NoticeableFuture, long)) && args(channel, " +
						"noticeableFuture, timeout)")
	public <T> void scheduleCancellation(
		Channel channel, final NoticeableFuture<T> noticeableFuture,
		long timeout) {

		if (timeout == 0) {
			noticeableFuture.cancel(true);

			return;
		}

		final Future<?> cancellationFuture =
			_scheduledExecutorService.schedule(
				new Runnable() {

					@Override
					public void run() {
						noticeableFuture.cancel(true);
					}

				},
				timeout, TimeUnit.MILLISECONDS);

		noticeableFuture.addFutureListener(
			new FutureListener<T>() {

				@Override
				public void complete(Future<T> future) {
					cancellationFuture.cancel(true);
				}

			});
	}

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();

}