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

package com.liferay.portal.fabric.repository;

import com.liferay.petra.concurrent.AsyncBroker;
import com.liferay.petra.concurrent.DefaultNoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.portal.fabric.netty.fileserver.FileResponse;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class MockRepository<T> implements Repository<T> {

	public MockRepository() {
		this(null);
	}

	public MockRepository(String repositoryPath) {
		if (repositoryPath == null) {
			_repositoryPath = null;
		}
		else {
			_repositoryPath = Paths.get(repositoryPath);
		}
	}

	@Override
	public void dispose(boolean delete) {
	}

	@Override
	public AsyncBroker<Path, FileResponse> getAsyncBroker() {
		return _asyncBroker;
	}

	@Override
	public NoticeableFuture<Path> getFile(
		T t, Path remoteFilePath, Path localFilePath,
		boolean deleteAfterFetch) {

		return null;
	}

	@Override
	public NoticeableFuture<Map<Path, Path>> getFiles(
		T t, Map<Path, Path> pathMap, boolean deleteAfterFetch) {

		DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		defaultNoticeableFuture.set(pathMap);

		return defaultNoticeableFuture;
	}

	@Override
	public Path getRepositoryPath() {
		return _repositoryPath;
	}

	private final AsyncBroker<Path, FileResponse> _asyncBroker =
		new AsyncBroker<>();
	private final Path _repositoryPath;

}