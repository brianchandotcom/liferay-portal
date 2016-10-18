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

package com.liferay.sync.engine.lan.server.discovery;

import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.model.SyncLanClient;
import com.liferay.sync.engine.model.SyncLanEndpoint;
import com.liferay.sync.engine.service.SyncLanClientService;
import com.liferay.sync.engine.service.SyncLanEndpointService;
import com.liferay.sync.engine.util.JSONUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanDiscoveryListenerHandler
	extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	public void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			DatagramPacket datagramPacket)
		throws Exception {

		ByteBuf byteBuf = datagramPacket.content();

		SyncLanClient syncLanClient = JSONUtil.readValue(
			byteBuf.toString(CharsetUtil.UTF_8), SyncLanClient.class);

		String syncLanClientUuid = syncLanClient.getSyncLanClientUuid();

		if (syncLanClientUuid.equals(LanClientUtil.getSyncLanClientUuid())) {
			return;
		}

		List<SyncLanEndpoint> syncLanEndpoints =
			SyncLanEndpointService.findSyncLanEndPoints(syncLanClientUuid);

		Map<String, Set<Long>> endpoints = syncLanClient.getEndpoints();

		for (Map.Entry<String, Set<Long>> entry : endpoints.entrySet()) {
			for (Long groupId : entry.getValue()) {
				SyncLanEndpoint syncLanEndpoint = new SyncLanEndpoint();

				syncLanEndpoint.setLanServerUuid(entry.getKey());
				syncLanEndpoint.setRepositoryId(groupId);
				syncLanEndpoint.setSyncLanClientUuid(syncLanClientUuid);

				if (!syncLanEndpoints.remove(syncLanEndpoint)) {
					SyncLanEndpointService.update(syncLanEndpoint);
				}
			}
		}

		for (SyncLanEndpoint syncLanEndpoint : syncLanEndpoints) {
			SyncLanEndpointService.deleteSyncLanEndpoint(syncLanEndpoint);
		}

		InetSocketAddress inetSocketAddress = datagramPacket.sender();

		syncLanClient.setHostname(inetSocketAddress.getHostName());

		syncLanClient.setModifiedTime(System.currentTimeMillis());

		SyncLanClientService.update(syncLanClient);
	}

	@Override
	public void channelReadComplete(
		ChannelHandlerContext channelHandlerContext) {

		channelHandlerContext.flush();
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable cause) {

		_logger.error(cause.getMessage(), cause);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanDiscoveryListenerHandler.class);

}