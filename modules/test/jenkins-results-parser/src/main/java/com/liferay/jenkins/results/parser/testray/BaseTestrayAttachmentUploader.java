/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Build;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestrayAttachmentUploader
	implements TestrayAttachmentUploader {

	@Override
	public URL getTestrayServerURL() {
		return _testrayServerURL;
	}

	@Override
	public void prepareFiles() {
		if (_prepared) {
			return;
		}

		File preparedFilesBaseDir = getPreparedFilesBaseDir();

		JenkinsResultsParserUtil.delete(preparedFilesBaseDir);

		TestrayAttachmentRecorder testrayAttachmentRecorder =
			getTestrayAttachmentRecorder();

		testrayAttachmentRecorder.record();

		try {
			JenkinsResultsParserUtil.copy(
				testrayAttachmentRecorder.getRecordedFilesBaseDir(),
				preparedFilesBaseDir);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		for (File preparedFile : getPreparedFiles()) {
			String preparedFileName = preparedFile.getName();

			if (preparedFileName.endsWith(".html")) {
				try {
					String preparedFileContent = JenkinsResultsParserUtil.read(
						preparedFile);

					File preparedParentFile = preparedFile.getParentFile();

					preparedFileContent = preparedFileContent.replaceAll(
						"(screenshots/(?:after|before|screenshot)\\d+)\\.jpg",
						JenkinsResultsParserUtil.combine(
							String.valueOf(getTestrayServerLogsURL()), "/",
							testrayAttachmentRecorder.getRelativeBuildDirPath(),
							"/", preparedParentFile.getName(), "/$1.jpg.gz"));

					preparedFileContent = preparedFileContent.replace(
						"https://cdn.alloyui.com/3.1.0/",
						"https://cdn.jsdelivr.net/npm/alloy-ui@3.1.0/build/");

					JenkinsResultsParserUtil.write(
						preparedFile, preparedFileContent);
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}

			_convertToGzipFile(preparedFile);
		}

		_prepared = true;
	}

	protected BaseTestrayAttachmentUploader(Build build, URL testrayServerURL) {
		if (build == null) {
			throw new RuntimeException("Please set a build");
		}

		_build = build;

		_testrayAttachmentRecorder =
			TestrayFactory.newTestrayAttachmentRecorder(build);

		_testrayServerURL = testrayServerURL;
	}

	protected Build getBuild() {
		return _build;
	}

	protected List<File> getPreparedFiles() {
		return JenkinsResultsParserUtil.findFiles(
			getPreparedFilesBaseDir(), ".*");
	}

	protected TestrayAttachmentRecorder getTestrayAttachmentRecorder() {
		return _testrayAttachmentRecorder;
	}

	private File _convertToGzipFile(File file) {
		File gzipFile = new File(file.getParent(), file.getName() + ".gz");

		JenkinsResultsParserUtil.gzip(file, gzipFile);

		JenkinsResultsParserUtil.delete(file);

		return gzipFile;
	}

	private final Build _build;
	private boolean _prepared;
	private final TestrayAttachmentRecorder _testrayAttachmentRecorder;
	private final URL _testrayServerURL;

}