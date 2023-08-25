/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import java.nio.file.Path;

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
		if (path == null) {
			return null;
		}

		return path.toAbsolutePath();
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