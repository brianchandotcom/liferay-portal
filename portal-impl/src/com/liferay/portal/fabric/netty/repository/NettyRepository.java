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

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.BaseFutureListener;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFutureConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.fileserver.FileRequest;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.repository.RepositoryHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class NettyRepository implements Repository<Channel> {

	public NettyRepository(Path repositoryPath, long getFileTimeout) {
		if (repositoryPath == null) {
			throw new NullPointerException("Repository path is null");
		}

		if (!Files.isDirectory(repositoryPath)) {
			throw new IllegalArgumentException(
				repositoryPath + " is not a directory");
		}

		this.repositoryPath = repositoryPath;
		this.getFileTimeout = getFileTimeout;
	}

	@Override
	public void dispose(boolean delete) {
		Set<Map.Entry<Path, Path>> entrySet = pathMap.entrySet();

		Iterator<Map.Entry<Path, Path>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Path, Path> entry = iterator.next();

			iterator.remove();

			FileHelperUtil.delete(true, entry.getValue());
		}

		if (delete) {
			FileHelperUtil.delete(true, repositoryPath);
		}
	}

	@Override
	public AsyncBroker<Path, FileResponse> getAsyncBroker() {
		return asyncBroker;
	}

	@Override
	public NoticeableFuture<Path> getFile(
		Channel channel, Path remoteFilePath, Path localFilePath,
		boolean deleteAfterFetch) {

		if (localFilePath == null) {
			return getFile(
				channel, remoteFilePath,
				RepositoryHelperUtil.getRepositoryFilePath(
					repositoryPath, remoteFilePath),
				deleteAfterFetch, true);
		}

		return getFile(
			channel, remoteFilePath, localFilePath, deleteAfterFetch, false);
	}

	@Override
	public NoticeableFuture<Map<Path, Path>> getFiles(
		Channel channel, Map<Path, Path> pathMap, boolean deleteAfterFetch) {

		final DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		if (pathMap.isEmpty()) {
			defaultNoticeableFuture.set(pathMap);

			return defaultNoticeableFuture;
		}

		final Map<Path, Path> resultPathMap = new ConcurrentHashMap<>();

		final AtomicInteger counter = new AtomicInteger(pathMap.size());

		for (Map.Entry<Path, Path> entry : pathMap.entrySet()) {
			final Path remoteFilePath = entry.getKey();

			NoticeableFuture<Path> noticeableFuture = getFile(
				channel, remoteFilePath, entry.getValue(), deleteAfterFetch);

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
		Channel channel, final Path remoteFilePath, final Path localFilePath,
		boolean deleteAfterFetch, final boolean populateCache) {

		if (_log.isDebugEnabled()) {
			_log.debug("Fetching remote file " + remoteFilePath);
		}

		final Path cachedLocalFilePath = pathMap.get(remoteFilePath);

		boolean[] newMarker = new boolean[1];

		NoticeableFuture<FileResponse> noticeableFuture = asyncBroker.post(
			remoteFilePath, newMarker);

		if (newMarker[0]) {
			NettyUtil.scheduleCancellation(
				channel, noticeableFuture, getFileTimeout);

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
							noticeableFuture.cancel(true);

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
							"Remote file " + remoteFilePath + " is not found");
					}

					return null;
				}

				if (fileResponse.isFileNotModified()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"Remote file ", remoteFilePath,
								" is not modified, use cached local file ",
								cachedLocalFilePath));
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
							fileResponse.getLocalFile(), targetLocalFilePath,
							StandardCopyOption.REPLACE_EXISTING);
					}
					else {
						Files.move(
							fileResponse.getLocalFile(), targetLocalFilePath,
							StandardCopyOption.REPLACE_EXISTING);
					}

					if (populateCache) {
						pathMap.put(remoteFilePath, targetLocalFilePath);
					}

					fileResponse.setLocalFile(targetLocalFilePath);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Fetched remote file ", remoteFilePath, " to ",
							targetLocalFilePath));
				}

				return targetLocalFilePath;
			}

		};
	}

	protected final AsyncBroker<Path, FileResponse> asyncBroker =
		new AsyncBroker<>();
	protected final long getFileTimeout;
	protected final Map<Path, Path> pathMap = new ConcurrentHashMap<>();
	protected final Path repositoryPath;

	private static final Log _log = LogFactoryUtil.getLog(
		NettyRepository.class);

}