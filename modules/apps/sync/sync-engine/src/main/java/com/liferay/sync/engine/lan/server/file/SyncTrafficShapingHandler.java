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

package com.liferay.sync.engine.lan.server.file;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dennis Ju
 */
public class SyncTrafficShapingHandler
	extends GlobalChannelTrafficShapingHandler {

	public SyncTrafficShapingHandler(
		ScheduledExecutorService scheduledExecutorService) {

		super(scheduledExecutorService);
	}

	public int decrementConnectionsCount() {
		return _connectionsCount.decrementAndGet();
	}

	public int getConnectionsCount() {
		return _connectionsCount.get();
	}

	public int incrementConnectionsCount() {
		return _connectionsCount.incrementAndGet();
	}

	public void setWriteDelay(long writeDelay) {
		_writeDelay = writeDelay;
	}

	@Override
	protected void submitWrite(
		final ChannelHandlerContext channelHandlerContext, Object message,
		long size, long writedelay, long now, ChannelPromise channelPromise) {

		super.submitWrite(
			channelHandlerContext, message, size, _writeDelay, now,
			channelPromise);
	}

	private final AtomicInteger _connectionsCount = new AtomicInteger();
	private long _writeDelay;

}