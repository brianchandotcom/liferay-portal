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

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;

import java.nio.file.Path;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class MockRepository implements Repository {

	@Override
	public void dispose(boolean delete) {
	}

	@Override
	public NoticeableFuture<Path> getFile(
		Path remoteFilePath, Path localFilePath, boolean deleteAfterFetch) {

		return null;
	}

	@Override
	public NoticeableFuture<Map<Path, Path>> getFiles(
		Map<Path, Path> pathMap, boolean deleteAfterFetch) {

		DefaultNoticeableFuture<Map<Path, Path>> defaultNoticeableFuture =
			new DefaultNoticeableFuture<Map<Path, Path>>();

		defaultNoticeableFuture.set(pathMap);

		return defaultNoticeableFuture;
	}

	@Override
	public Path getRepositoryPath() {
		return null;
	}

}