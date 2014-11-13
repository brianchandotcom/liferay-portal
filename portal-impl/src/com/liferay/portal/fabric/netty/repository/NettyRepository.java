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

package com.liferay.portal.fabric.netty.repository;

import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.FileRequest;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.fabric.netty.fileserver.handlers.FileResponseChannelHandler;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.repository.RepositoryHelperUtil;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.concurrent.BaseFutureListener;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFutureConverter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class NettyRepository implements Repository {

	public NettyRepository(
		Path repositoryPath, Channel channel,
		EventExecutorGroup eventExecutorGroup, long getFileTimeout) {

		if (repositoryPath == null) {
			throw new NullPointerException("Repository path is null");
		}

		if (channel == null) {
			throw new NullPointerException("Channel is null");
		}

		if (eventExecutorGroup == null) {
			throw new NullPointerException("Event executor group is null");
		}

		if (!Files.isDirectory(repositoryPath)) {
			throw new IllegalArgumentException(
				repositoryPath + " is not a directory");
		}

		this.repositoryPath = repositoryPath;
		this.channel = channel;
		this.getFileTimeout = getFileTimeout;

		ChannelPipeline channelPipeline = channel.pipeline();

		channelPipeline.addLast(
			new FileResponseChannelHandler(asyncBroker, eventExecutorGroup));
	}

	@Override
	public void dispose(boolean delete) {
		for (Path path : pathMap.values()) {
			FileHelperUtil.delete(true, path);
		}

		pathMap.clear();

		if (delete) {
			FileHelperUtil.delete(true, repositoryPath);
		}
	}

	@Override
	public NoticeableFuture<Path> getFile(
		Path remoteFilePath, Path localFilePath, boolean deleteAfterFetch) {

		if (localFilePath == null) {
			return getFile(
				remoteFilePath,
				RepositoryHelperUtil.getRepositoryFilePath(
					repositoryPath, remoteFilePath),
				deleteAfterFetch, true);
		}

		return getFile(remoteFilePath, localFilePath, deleteAfterFetch, false);
	}

	@Override
	public NoticeableFuture<Map<Path, Path>> getFiles(
		Map<Path, Path> pathMap, boolean deleteAfterFetch) {

		final DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<Map<Path, Path>>();

		if (pathMap.isEmpty()) {
			defaultNoticeableFuture.set(pathMap);

			return defaultNoticeableFuture;
		}

		final Map<Path, Path> resultPathMap =
			new ConcurrentHashMap<Path, Path>();

		final AtomicInteger counter = new AtomicInteger(pathMap.size());

		for (Map.Entry<Path, Path> entry : pathMap.entrySet()) {
			final Path remoteFilePath = entry.getKey();

			NoticeableFuture<Path> noticeableFuture = getFile(
				remoteFilePath, entry.getValue(), deleteAfterFetch);

			noticeableFuture.addFutureListener(
				new BaseFutureListener<Path>() {

					@Override
					public void completeWithCancel(Future<Path> future) {
						defaultNoticeableFuture.cancel(true);
					}

					@Override
					public void completeWithException(
						Future<Path> future, Throwable throwable) {

						defaultNoticeableFuture.setException(throwable);
					}

					@Override
					public void completeWithResult(
						Future<Path> future, Path localFilePath) {

						if (localFilePath != null) {
							resultPathMap.put(remoteFilePath, localFilePath);
						}

						if (counter.decrementAndGet() <= 0) {
							defaultNoticeableFuture.set(resultPathMap);
						}
					}

				});
		}

		return defaultNoticeableFuture;
	}

	@Override
	public Path getRepositoryPath() {
		return repositoryPath;
	}

	protected static long getLastModifiedTime(Path path) {
		if (path == null) {
			return Long.MIN_VALUE;
		}

		try {
			FileTime fileTime = Files.getLastModifiedTime(path);

			return fileTime.toMillis();
		}
		catch (IOException ioe) {
			return Long.MIN_VALUE;
		}
	}

	protected NoticeableFuture<Path> getFile(
		final Path remoteFilePath, final Path localFilePath,
		boolean deleteAfterFetch, final boolean populateCache) {

		if (_log.isDebugEnabled()) {
			_log.debug("Fetching remote file " + remoteFilePath);
		}

		final Path cachedLocalFilePath = pathMap.get(remoteFilePath);

		final DefaultNoticeableFuture<FileResponse> defaultNoticeableFuture =
			new DefaultNoticeableFuture<FileResponse>();

		NoticeableFuture<FileResponse> noticeableFuture = asyncBroker.post(
			remoteFilePath, defaultNoticeableFuture);

		if (noticeableFuture == null) {
			noticeableFuture = defaultNoticeableFuture;

			NettyUtil.scheduleCancellation(
				channel, defaultNoticeableFuture, getFileTimeout);

			ChannelFuture channelFuture = channel.writeAndFlush(
				new FileRequest(
					remoteFilePath, getLastModifiedTime(cachedLocalFilePath),
					deleteAfterFetch));

			channelFuture.addListener(
				new ChannelFutureListener() {

					@Override
					public void operationComplete(ChannelFuture channelFuture) {
						if (channelFuture.isSuccess()) {
							return;
						}

						if (channelFuture.isCancelled()) {
							defaultNoticeableFuture.cancel(true);

							return;
						}

						Throwable throwable = new IOException(
							"Unable to fetch remote file " + remoteFilePath,
							channelFuture.cause());

						if (!asyncBroker.takeWithException(
								remoteFilePath, throwable)) {

							_log.error(
								"Unable to place exception because no future " +
									"exists with ID " + remoteFilePath,
								throwable);
						}
					}

				});
		}

		return new NoticeableFutureConverter<Path, FileResponse>(
			noticeableFuture) {

				@Override
				protected Path convert(FileResponse fileResponse)
					throws IOException {

					if (fileResponse.isFileNotFound()) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Remote file " + remoteFilePath +
									" is not found");
						}

						return null;
					}

					if (fileResponse.isFileNotModified()) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Remote file " + remoteFilePath +
									" is not modified, use cached local file " +
										cachedLocalFilePath);
						}

						return cachedLocalFilePath;
					}

					Path targetLocalFilePath = localFilePath;

					synchronized (fileResponse) {
						Path recheckCacheLocalFilePath = pathMap.get(
							remoteFilePath);

						if (recheckCacheLocalFilePath != null) {
							targetLocalFilePath = recheckCacheLocalFilePath;
						}

						Path tempLocalFilePath = fileResponse.getLocalFile();

						if (tempLocalFilePath.startsWith(repositoryPath)) {
							Files.copy(
								fileResponse.getLocalFile(),
								targetLocalFilePath);
						}
						else {
							Files.move(
								fileResponse.getLocalFile(),
								targetLocalFilePath);
						}

						if (populateCache) {
							pathMap.put(remoteFilePath, targetLocalFilePath);
						}

						fileResponse.setLocalFile(targetLocalFilePath);
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Fetched remote file " + remoteFilePath + " to " +
								targetLocalFilePath);
					}

					return targetLocalFilePath;
				}

			};
	}

	protected final AsyncBroker<Path, FileResponse> asyncBroker =
		new AsyncBroker<Path, FileResponse>();
	protected final Channel channel;
	protected final long getFileTimeout;
	protected final Map<Path, Path> pathMap =
		new ConcurrentHashMap<Path, Path>();
	protected final Path repositoryPath;

	private static final Log _log = LogFactoryUtil.getLog(
		NettyRepository.class);

}