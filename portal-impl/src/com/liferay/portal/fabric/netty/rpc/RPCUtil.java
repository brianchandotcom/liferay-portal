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

package com.liferay.portal.fabric.netty.rpc;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.portal.fabric.netty.handlers.NettyChannelAttributes;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class RPCUtil {

	public static <T extends Serializable> NoticeableFuture<T> execute(
		Channel channel, RPCCallable<T> rpcCallable) {

		final AsyncBroker<Long, T> asyncBroker =
			NettyChannelAttributes.getAsyncBroker(channel);

		final long id = NettyChannelAttributes.nextId(channel);

		final NoticeableFuture<T> noticeableFuture = asyncBroker.post(id);

		ChannelFuture channelFuture = channel.writeAndFlush(
			new RPCRequest<T>(id, rpcCallable));

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (channelFuture.isSuccess()) {
						return;
					}

					if (channelFuture.isCancelled()) {
						noticeableFuture.cancel(true);

						return;
					}

					if (!asyncBroker.takeWithException(
							id, channelFuture.cause())) {

						_log.error(
							"Unable to place exception because no future " +
								"exists with ID " + id,
							channelFuture.cause());
					}
				}

			});

		return noticeableFuture;
	}

	private static final Log _log = LogFactoryUtil.getLog(RPCUtil.class);

}