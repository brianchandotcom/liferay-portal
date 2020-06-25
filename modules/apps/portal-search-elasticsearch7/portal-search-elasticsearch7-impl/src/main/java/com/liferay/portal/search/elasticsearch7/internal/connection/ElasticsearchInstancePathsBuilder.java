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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import java.nio.file.Path;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public class ElasticsearchInstancePathsBuilder {

	public ElasticsearchInstancePaths build() {
		return new ElasticsearchInstancePathsImpl(
			toAbsolutePath(_dataPath), toAbsolutePath(_homePath),
			toAbsolutePath(_workPath));
	}

	public ElasticsearchInstancePathsBuilder dataPath(Path dataHomePath) {
		_dataPath = dataHomePath;

		return this;
	}

	public ElasticsearchInstancePathsBuilder homePath(Path homePath) {
		_homePath = homePath;

		return this;
	}

	public ElasticsearchInstancePathsBuilder workPath(Path workPath) {
		_workPath = workPath;

		return this;
	}

	protected Path toAbsolutePath(Path path) {
		return Optional.ofNullable(
			path
		).map(
			Path::toAbsolutePath
		).orElse(
			null
		);
	}

	private Path _dataPath;
	private Path _homePath;
	private Path _workPath;

	private static class ElasticsearchInstancePathsImpl
		implements ElasticsearchInstancePaths {

		public ElasticsearchInstancePathsImpl(
			Path dataPath, Path homePath, Path workPath) {

			_dataPath = dataPath;
			_homePath = homePath;
			_workPath = workPath;
		}

		@Override
		public Path getDataPath() {
			return _dataPath;
		}

		@Override
		public Path getHomePath() {
			return _homePath;
		}

		@Override
		public Path getWorkPath() {
			return _workPath;
		}

		private final Path _dataPath;
		private final Path _homePath;
		private final Path _workPath;

	}

}