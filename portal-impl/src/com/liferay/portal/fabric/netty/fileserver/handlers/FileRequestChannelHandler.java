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

import com.liferay.portal.fabric.netty.fileserver.CompressionLevel;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.FileRequest;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

/**
 * @author Shuyang Zhou
 */
public class FileRequestChannelHandler
	extends SimpleChannelInboundHandler<FileRequest> {

	public static final String NAME = FileRequestChannelHandler.class.getName();

	public FileRequestChannelHandler(CompressionLevel compressionLevel) {
		_compressionLevel = compressionLevel;
	}

	@Override
	protected void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			FileRequest fileRequest)
		throws IOException {

		Path path = fileRequest.getPath();

		BasicFileAttributes basicFileAttributes = null;

		try {
			basicFileAttributes = Files.readAttributes(
				path, BasicFileAttributes.class);
		}
		catch (NoSuchFileException nsfe) {
			channelHandlerContext.writeAndFlush(
				new FileResponse(path, FileResponse.FILE_NOT_FOUND, -1, false));

			return;
		}

		FileTime fileTime = basicFileAttributes.lastModifiedTime();

		if (fileTime.toMillis() <= fileRequest.getLastModifiedTime()) {
			channelHandlerContext.writeAndFlush(
				new FileResponse(
					path, FileResponse.FILE_NOT_MODIFIED, -1, false));

			return;
		}

		FileChannel fileChannel = null;

		if (basicFileAttributes.isDirectory()) {
			fileChannel = FileChannel.open(
				FileHelperUtil.zip(
					path, FileHelperUtil.TEMP_DIR_PATH, _compressionLevel),
				StandardOpenOption.DELETE_ON_CLOSE);

			if (fileRequest.isDeleteAfterFetch()) {
				FileHelperUtil.delete(path);
			}
		}
		else if (fileRequest.isDeleteAfterFetch()) {
			fileChannel = FileChannel.open(
				path, StandardOpenOption.DELETE_ON_CLOSE);
		}
		else {
			fileChannel = FileChannel.open(path);
		}

		channelHandlerContext.write(
			new FileResponse(
				path, fileChannel.size(), fileTime.toMillis(),
				basicFileAttributes.isDirectory()));
		channelHandlerContext.writeAndFlush(
			new DefaultFileRegion(fileChannel, 0, fileChannel.size()));
	}

	private final CompressionLevel _compressionLevel;

}