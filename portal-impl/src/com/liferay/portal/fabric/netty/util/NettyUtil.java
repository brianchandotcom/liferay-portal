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

import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuyang Zhou
 */
public class NettyUtil {

	public static void bindShutdown(
		EventExecutorGroup master, final EventExecutorGroup slave,
		final long quietPeriod, final long timeout) {

		io.netty.util.concurrent.Future<?> future = master.terminationFuture();

		future.addListener(
			new io.netty.util.concurrent.FutureListener<Object>() {

				@Override
				public void operationComplete(
						io.netty.util.concurrent.Future<Object> future)
					throws InterruptedException {

					slave.shutdownGracefully(
						quietPeriod, timeout, TimeUnit.MILLISECONDS);

					if (!slave.awaitTermination(
							timeout, TimeUnit.MILLISECONDS) &&
						_log.isWarnEnabled()) {

						_log.warn("Bind shutdown timeout " + slave);
					}
				}

			});
	}

	public static ChannelPipeline createEmptyChannelPipeline() {
		Channel channel = new EmbeddedChannel(
			new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel channel) {
				}

			});

		return channel.pipeline();
	}

	public static <T> void scheduleCancellation(
		Channel channel, final NoticeableFuture<T> noticeableFuture,
		long timeout) {

		EventLoop eventLoop = channel.eventLoop();

		final Future<?> cancellationFuture = eventLoop.schedule(
			new Runnable() {

				@Override
				public void run() {
					if (noticeableFuture.cancel(true) && _log.isWarnEnabled()) {
						_log.warn("Cancelled timeout " + noticeableFuture);
					}
				}

			},
			timeout, TimeUnit.MILLISECONDS);

		noticeableFuture.addFutureListener(
			new FutureListener<T>() {

				@Override
				public void complete(Future<T> future) {
					if (cancellationFuture.cancel(true) &&
						_log.isDebugEnabled()) {

						_log.debug(
							"Cancelled scheduled cancellation for " +
								noticeableFuture);
					}
				}

			});
	}

	private static final Log _log = LogFactoryUtil.getLog(NettyUtil.class);

}