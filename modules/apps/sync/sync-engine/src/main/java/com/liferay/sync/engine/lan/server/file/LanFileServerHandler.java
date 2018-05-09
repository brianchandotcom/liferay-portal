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

import com.liferay.sync.engine.lan.util.LanTokenUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.util.GetterUtil;
import com.liferay.sync.engine.util.OSDetector;
import com.liferay.sync.engine.util.PropsValues;
import com.liferay.sync.engine.util.Validator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.traffic.TrafficCounter;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanFileServerHandler
	extends SimpleChannelInboundHandler<FullHttpRequest> {

	public LanFileServerHandler(
		SyncTrafficShapingHandler syncTrafficShapingHandler) {

		_syncTrafficShapingHandler = syncTrafficShapingHandler;

		_trafficCounter = _syncTrafficShapingHandler.trafficCounter();
	}

	@Override
	public void channelRead0(
			ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest)
		throws Exception {

		DecoderResult decoderResult = fullHttpRequest.decoderResult();

		if (!decoderResult.isSuccess()) {
			_sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);

			return;
		}

		if (fullHttpRequest.method() == HttpMethod.GET) {
			processGetRequest(channelHandlerContext, fullHttpRequest);
		}
		else if (fullHttpRequest.method() == HttpMethod.HEAD) {
			processHeadRequest(channelHandlerContext, fullHttpRequest);
		}
		else {
			_sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);
		}
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable exception) {

		String message = exception.getMessage();

		Channel channel = channelHandlerContext.channel();

		if (!message.startsWith("An established connection was aborted") &&
			!message.startsWith(
				"An existing connectionn was forcibly closed") &&
			!message.startsWith("Connection reset by peer")) {

			_logger.error(
				"Client {}: {}", channel.remoteAddress(),
				exception.getMessage(), exception);
		}

		channel.close();
	}

	protected void processGetRequest(
			ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest)
		throws Exception {

		if (_logger.isTraceEnabled()) {
			Channel channel = channelHandlerContext.channel();

			_logger.trace(
				"Client {}: processing get request {}", channel.remoteAddress(),
				fullHttpRequest.uri());
		}

		HttpHeaders requestHttpHeaders = fullHttpRequest.headers();

		String lanToken = requestHttpHeaders.get("lanToken");

		if (Validator.isBlank(lanToken)) {
			Channel channel = channelHandlerContext.channel();

			_logger.error(
				"Client {}: did not send token", channel.remoteAddress());

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		if (!LanTokenUtil.containsLanToken(lanToken)) {
			Channel channel = channelHandlerContext.channel();

			_logger.error(
				"Client {}: token not found or expired",
				channel.remoteAddress());

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		SyncFile syncFile = _getSyncFile(fullHttpRequest);

		if (syncFile == null) {
			if (_logger.isTraceEnabled()) {
				Channel channel = channelHandlerContext.channel();

				_logger.trace(
					"Client {}: SyncFile not found. uri: {}",
					channel.remoteAddress(), fullHttpRequest.uri());
			}

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		if (_syncTrafficShapingHandler.getConnectionsCount() >=
				PropsValues.SYNC_LAN_SERVER_MAX_CONNECTIONS) {

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		_syncTrafficShapingHandler.incrementConnectionsCount();

		try {
			sendFile(channelHandlerContext, fullHttpRequest, syncFile);
		}
		catch (Exception e) {
			_syncTrafficShapingHandler.decrementConnectionsCount();

			throw e;
		}

		LanTokenUtil.removeLanToken(lanToken);
	}

	protected void processHeadRequest(
		ChannelHandlerContext channelHandlerContext,
		FullHttpRequest fullHttpRequest) {

		if (_logger.isTraceEnabled()) {
			Channel channel = channelHandlerContext.channel();

			_logger.trace(
				"Client {}: processing head request {}",
				channel.remoteAddress(), fullHttpRequest.uri());
		}

		SyncFile syncFile = _getSyncFile(fullHttpRequest);

		if (syncFile == null) {
			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		String lanTokenKey = syncFile.getLanTokenKey();

		if ((lanTokenKey == null) || lanTokenKey.isEmpty()) {
			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		String encryptedToken = null;

		try {
			encryptedToken = LanTokenUtil.createEncryptedToken(lanTokenKey);
		}
		catch (Exception e) {
			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		HttpResponse httpResponse = new DefaultFullHttpResponse(
			HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

		HttpHeaders httpHeaders = httpResponse.headers();

		httpHeaders.set(
			"connectionsCount",
			_syncTrafficShapingHandler.getConnectionsCount());

		httpHeaders.set("downloadRate", _trafficCounter.lastWrittenBytes());
		httpHeaders.set("encryptedToken", encryptedToken);
		httpHeaders.set(
			"maxConnections", PropsValues.SYNC_LAN_SERVER_MAX_CONNECTIONS);

		channelHandlerContext.writeAndFlush(httpResponse);
	}

	protected void sendFile(
			final ChannelHandlerContext channelHandlerContext,
			FullHttpRequest fullHttpRequest, SyncFile syncFile)
		throws Exception {

		Path path = Paths.get(syncFile.getFilePathName());

		if (Files.notExists(path)) {
			_syncTrafficShapingHandler.decrementConnectionsCount();

			if (_logger.isTraceEnabled()) {
				Channel channel = channelHandlerContext.channel();

				_logger.trace(
					"Client {}: file not found {}", channel.remoteAddress(),
					path);
			}

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		if (_logger.isDebugEnabled()) {
			Channel channel = channelHandlerContext.channel();

			_logger.debug(
				"Client {}: sending file {}", channel.remoteAddress(), path);
		}

		long modifiedTime = syncFile.getModifiedTime();
		long previousModifiedTime = syncFile.getPreviousModifiedTime();

		if (OSDetector.isApple()) {
			modifiedTime = modifiedTime / 1000 * 1000;
			previousModifiedTime = previousModifiedTime / 1000 * 1000;
		}

		FileTime currentFileTime = Files.getLastModifiedTime(
			path, LinkOption.NOFOLLOW_LINKS);

		long currentTime = currentFileTime.toMillis();

		if ((currentTime != modifiedTime) &&
			(currentTime != previousModifiedTime)) {

			_syncTrafficShapingHandler.decrementConnectionsCount();

			Channel channel = channelHandlerContext.channel();

			_logger.error(
				"Client {}: file modified {}, currentTime {}, modifiedTime " +
					"{}, previousModifiedTime {}",
				channel.remoteAddress(), path, currentTime, modifiedTime,
				previousModifiedTime);

			_sendError(channelHandlerContext, HttpResponseStatus.NOT_FOUND);

			return;
		}

		HttpResponse httpResponse = new DefaultHttpResponse(
			HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

		long size = Files.size(path);

		HttpUtil.setContentLength(httpResponse, size);

		HttpHeaders httpHeaders = httpResponse.headers();

		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

		httpHeaders.set(
			HttpHeaderNames.CONTENT_TYPE,
			mimetypesFileTypeMap.getContentType(syncFile.getName()));

		if (HttpUtil.isKeepAlive(fullHttpRequest)) {
			httpHeaders.set(
				HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}

		channelHandlerContext.write(httpResponse);

		SyncChunkedFile syncChunkedFile = new SyncChunkedFile(
			path, size, 4 * 1024 * 1024, currentTime);

		ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
			new HttpChunkedInput(syncChunkedFile),
			channelHandlerContext.newProgressivePromise());

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture)
					throws Exception {

					_syncTrafficShapingHandler.decrementConnectionsCount();

					if (channelFuture.isSuccess()) {
						return;
					}

					Throwable exception = channelFuture.cause();

					Channel channel = channelHandlerContext.channel();

					_logger.error(
						"Client {}: {}", channel.remoteAddress(),
						exception.getMessage(), exception);

					channelHandlerContext.close();
				}

			});

		if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
			channelFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private SyncFile _getSyncFile(FullHttpRequest fullHttpRequest) {
		String[] pathArray = StringUtils.split(fullHttpRequest.uri(), "/");

		if (pathArray.length != 4) {
			return null;
		}

		String lanServerUuid = pathArray[0];
		long repositoryId = GetterUtil.getLong(pathArray[1]);
		long typePK = GetterUtil.getLong(pathArray[2]);
		long versionId = GetterUtil.getLong(pathArray[3]);

		if (lanServerUuid.isEmpty() || (repositoryId == 0) || (typePK == 0) ||
			(versionId == 0)) {

			return null;
		}

		List<SyncAccount> syncAccounts = SyncAccountService.findSyncAccounts(
			lanServerUuid);

		for (SyncAccount syncAccount : syncAccounts) {
			SyncFile syncFile = SyncFileService.fetchSyncFile(
				repositoryId, syncAccount.getSyncAccountId(), typePK,
				versionId);

			if ((syncFile != null) &&
				(syncFile.getState() == SyncFile.STATE_SYNCED)) {

				return syncFile;
			}
		}

		return null;
	}

	private void _sendError(
		ChannelHandlerContext channelHandlerContext,
		HttpResponseStatus httpResponseStatus) {

		ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(
			new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, httpResponseStatus));

		channelFuture.addListener(ChannelFutureListener.CLOSE);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanFileServerHandler.class);

	private final SyncTrafficShapingHandler _syncTrafficShapingHandler;
	private final TrafficCounter _trafficCounter;

}