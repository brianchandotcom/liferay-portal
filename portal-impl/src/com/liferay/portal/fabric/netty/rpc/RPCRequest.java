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

import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class RPCRequest<T extends Serializable> extends RPCSerializable {

	public RPCRequest(long id, RPCCallable<T> rpcCallable) {
		super(id);

		_rpcCallable = rpcCallable;
	}

	@Override
	public void execute(final Channel channel) {
		ChannelThreadLocal.setChannel(channel);

		try {
			NoticeableFuture<T> noticeableFuture = _rpcCallable.call();

			noticeableFuture.addFutureListener(
				new FutureListener<T>() {

					@Override
					public void complete(Future<T> future) {
						if (future.isCancelled()) {
							sendRPCResponse(
								channel,
								new RPCResponse<T>(id, true, null, null));

							return;
						}

						try {
							sendRPCResponse(
								channel,
								new RPCResponse<T>(
									id, false, future.get(), null));
						}
						catch (Throwable throwable) {
							if (throwable instanceof ExecutionException) {
								throwable = throwable.getCause();
							}

							sendRPCResponse(
								channel,
								new RPCResponse<T>(id, false, null, throwable));
						}
					}

				});
		}
		catch (Throwable t) {
			sendRPCResponse(channel, new RPCResponse<T>(id, false, null, t));
		}
		finally {
			ChannelThreadLocal.removeChannel();
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{id=");
		sb.append(id);
		sb.append(", rpcCallable=");
		sb.append(_rpcCallable);
		sb.append("}");

		return sb.toString();
	}

	protected void sendRPCResponse(
		Channel channel, RPCResponse<T> rpcResponse) {

		ChannelFuture channelFuture = channel.writeAndFlush(rpcResponse);

		channelFuture.addListener(new LogErrorFutureListener(rpcResponse));
	}

	protected static class LogErrorFutureListener
		implements ChannelFutureListener {

		@Override
		public void operationComplete(ChannelFuture channelFuture) {
			if (channelFuture.isSuccess()) {
				return;
			}

			if (channelFuture.isCancelled()) {
				_log.error(
					"Cancelled on sending RPC response: " + _rpcResponse);

				return;
			}

			_log.error(
				"Unable to send RPC response: " + _rpcResponse,
				channelFuture.cause());
		}

		protected LogErrorFutureListener(RPCResponse<?> rpcResponse) {
			_rpcResponse = rpcResponse;
		}

		private final RPCResponse<?> _rpcResponse;

	}

	private static final Log _log = LogFactoryUtil.getLog(RPCRequest.class);

	private static final long serialVersionUID = 1L;

	private final RPCCallable<T> _rpcCallable;

}