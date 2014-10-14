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

package com.liferay.portal.fabric.netty.fileserver.handlers;

import com.liferay.portal.fabric.netty.codec.serialization.ObjectDecodeChannelInboundHandler;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.IOException;

import java.nio.file.Path;

/**
 * @author Shuyang Zhou
 */
public class FileResponseChannelHandler
	extends ObjectDecodeChannelInboundHandler<FileResponse> {

	public FileResponseChannelHandler(
		AsyncBroker<Path, FileResponse> asyncBroker,
		EventExecutorGroup eventExecutorGroup) {

		_asyncBroker = asyncBroker;
		_eventExecutorGroup = eventExecutorGroup;
	}

	@Override
	public FileResponse channelRead0(
			ChannelHandlerContext channelHandlerContext,
			FileResponse fileResponse, ByteBuf byteBuf)
		throws IOException {

		if (fileResponse.isFileNotFound() || fileResponse.isFileNotModified()) {
			if (!_asyncBroker.takeWithResult(
					fileResponse.getPath(), fileResponse)) {

				_log.error(
					"Unable to place result " + fileResponse +
						" because no future exists with ID " +
							fileResponse.getPath());
			}

			return null;
		}

		ChannelPipeline channelPipeline = channelHandlerContext.pipeline();

		channelPipeline.addFirst(
			new FileUploadChannelHandler(
				_asyncBroker, fileResponse, _eventExecutorGroup.next()));

		channelPipeline.fireChannelRead(byteBuf.retain());

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileResponseChannelHandler.class);

	private final AsyncBroker<Path, FileResponse> _asyncBroker;
	private final EventExecutorGroup _eventExecutorGroup;

}