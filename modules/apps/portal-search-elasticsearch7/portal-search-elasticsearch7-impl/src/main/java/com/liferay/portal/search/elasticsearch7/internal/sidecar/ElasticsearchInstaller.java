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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class ElasticsearchInstaller {

	public static Builder builder() {
		return new Builder();
	}

	public ElasticsearchInstaller() {
	}

	public ElasticsearchInstaller(
		ElasticsearchInstaller elasticsearchInstaller) {

		_distributablesDirectoryPath =
			elasticsearchInstaller._distributablesDirectoryPath;
		_distribution = elasticsearchInstaller._distribution;
		_installationDirectoryPath =
			elasticsearchInstaller._installationDirectoryPath;
	}

	public void install() {
		if (_isAlreadyInstalled()) {
			return;
		}

		_createDestinationDirectory();

		try {
			_createTemporaryDownloadDirectory();

			try {
				_downloadAndInstallElasticsearch();

				_downloadAndInstallPlugins();
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
			finally {
				_deleteTemporaryDownloadDirectory();
			}
		}
		catch (RuntimeException runtimeException) {
			_deleteDestinationDirectory();

			throw runtimeException;
		}
	}

	public static class Builder {

		public ElasticsearchInstaller build() {
			return new ElasticsearchInstaller(_elasticsearchInstaller);
		}

		public Builder distributablesDirectoryPath(
			Path distributablesDirectoryPath) {

			_elasticsearchInstaller._distributablesDirectoryPath =
				distributablesDirectoryPath;

			return this;
		}

		public Builder distribution(Distribution distribution) {
			_elasticsearchInstaller._distribution = distribution;

			return this;
		}

		public Builder installationDirectoryPath(
			Path installationDirectoryPath) {

			_elasticsearchInstaller._installationDirectoryPath =
				installationDirectoryPath;

			return this;
		}

		private final ElasticsearchInstaller _elasticsearchInstaller =
			new ElasticsearchInstaller();

	}

	protected static String getChecksum(Path path) throws IOException {
		try (InputStream inputStream = Files.newInputStream(path)) {
			return DigestUtils.sha512Hex(inputStream);
		}
	}

	protected void createDirectories(Path directoryPath) {
		try {
			Files.createDirectories(directoryPath);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static Path _getTemporaryDirectoryPath() {
		Path path = Paths.get(SystemProperties.get(SystemProperties.TMP_DIR));

		return path.resolve(ElasticsearchInstaller.class.getSimpleName());
	}

	private void _createDestinationDirectory() {
		createDirectories(_installationDirectoryPath);
	}

	private void _createTemporaryDownloadDirectory() {
		createDirectories(_temporaryDirectoryPath);
	}

	private void _deleteDestinationDirectory() {
		PathUtil.deleteDir(_installationDirectoryPath);
	}

	private void _deleteTemporaryDownloadDirectory() {
		PathUtil.deleteDir(_temporaryDirectoryPath);
	}

	private void _downloadAndInstallElasticsearch() throws IOException {
		Path filePath = _getFilePath(
			_distribution.getElasticsearchDistributable());

		String rootArchiveName = UncompressUtil.unarchive(
			filePath, _temporaryDirectoryPath);

		PathUtil.copyDirectory(
			_temporaryDirectoryPath.resolve(rootArchiveName),
			_installationDirectoryPath);
	}

	private void _downloadAndInstallPlugin(Distributable distributable)
		throws IOException {

		Path filePath = _getFilePath(distributable);

		String pluginName = StringUtils.substringBeforeLast(
			String.valueOf(filePath.getFileName()), StringPool.DASH);

		Path extractedDirectoryPath = _temporaryDirectoryPath.resolve(
			pluginName);

		UncompressUtil.unzip(filePath, extractedDirectoryPath);

		Path pluginsDirectoryPath = _installationDirectoryPath.resolve(
			"plugins");

		createDirectories(pluginsDirectoryPath);

		Path pluginDestinationDirectoryPath = pluginsDirectoryPath.resolve(
			pluginName);

		PathUtil.copyDirectory(
			extractedDirectoryPath, pluginDestinationDirectoryPath);
	}

	private void _downloadAndInstallPlugins() throws IOException {
		for (Distributable distributable :
				_distribution.getPluginDistributables()) {

			_downloadAndInstallPlugin(distributable);
		}
	}

	private Path _getFilePath(Distributable distributable) throws IOException {
		Path filePath = _locateOrDownload(distributable);

		_guardChecksum(filePath, distributable.getChecksum());

		return filePath;
	}

	private void _guardChecksum(Path filePath, String checksum)
		throws IOException {

		if (!checksum.equals(getChecksum(filePath))) {
			throw new RuntimeException("Checksum mismatch");
		}
	}

	private boolean _isAlreadyInstalled() {
		return Files.exists(_installationDirectoryPath);
	}

	private Path _locateOrDownload(Distributable distributable)
		throws IOException {

		String downloadURLString = distributable.getDownloadURLString();

		String fileName = StringUtils.substringAfterLast(
			downloadURLString, StringPool.FORWARD_SLASH);

		Path distributableFilePath = _distributablesDirectoryPath.resolve(
			fileName);

		if (Files.exists(distributableFilePath)) {
			return distributableFilePath;
		}

		Path downloadedFilePath = _temporaryDirectoryPath.resolve(fileName);

		PathUtil.download(new URL(downloadURLString), downloadedFilePath);

		return downloadedFilePath;
	}

	private static final Path _temporaryDirectoryPath =
		_getTemporaryDirectoryPath();

	private Path _distributablesDirectoryPath;
	private Distribution _distribution;
	private Path _installationDirectoryPath;

}