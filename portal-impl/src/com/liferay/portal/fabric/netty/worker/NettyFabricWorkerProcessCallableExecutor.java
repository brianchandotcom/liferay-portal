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

package com.liferay.portal.fabric.netty.worker;

import com.liferay.portal.fabric.netty.rpc.RPCUtil;
import com.liferay.portal.fabric.status.JMXProxyUtil.ProcessCallableExecutor;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;

import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerProcessCallableExecutor
	implements ProcessCallableExecutor {

	public NettyFabricWorkerProcessCallableExecutor(
		Channel channel, long fabricWorkerId, long rpcRelayTime) {

		_channel = channel;
		_fabricWorkerId = fabricWorkerId;
		_rpcRelayTimeout = rpcRelayTime;
	}

	@Override
	public <V extends Serializable> NoticeableFuture<V> execute(
		ProcessCallable<V> processCallable) {

		return RPCUtil.execute(
			_channel,
			new NettyFabricWorkerBridgeRPCCallable<V>(
				_fabricWorkerId, processCallable, _rpcRelayTimeout));
	}

	private final Channel _channel;
	private final long _fabricWorkerId;
	private final long _rpcRelayTimeout;

}