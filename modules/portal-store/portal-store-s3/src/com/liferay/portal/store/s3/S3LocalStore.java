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

package com.liferay.portal.store.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Time;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;

/**
 * @author Edward C. Han
 */
public class S3LocalStore {

	public S3LocalStore(
		S3KeyTransformer s3KeyTransformer, int tempDirCleanUpExpunge,
		int tempDirCleanUpFrequency) {

		_s3KeyTransformer = s3KeyTransformer;
		_tempDirCleanUpExpunge = tempDirCleanUpExpunge;
		_tempDirCleanUpFrequency = tempDirCleanUpFrequency;
	}

	public void cleanUpTempFiles() {
		_calledGetFileCount++;

		if (_calledGetFileCount < _tempDirCleanUpFrequency) {
			return;
		}

		synchronized (this) {
			if (_calledGetFileCount == 0) {
				return;
			}

			_calledGetFileCount = 0;

			String tempDirName = SystemProperties.get(
				SystemProperties.TMP_DIR) + _TEMP_DIR_NAME;

			File tempDir = new File(tempDirName);

			long lastModified = System.currentTimeMillis();

			lastModified -= (_tempDirCleanUpExpunge * Time.DAY);

			cleanUpTempFiles(tempDir, lastModified);
		}
	}

	public void cleanUpTempFiles(File file, long lastModified) {
		if (!file.isDirectory()) {
			return;
		}

		String[] fileNames = FileUtil.listDirs(file);

		if (fileNames.length == 0) {
			if (file.lastModified() < lastModified) {
				FileUtil.deltree(file);

				return;
			}
		}
		else {
			for (String fileName : fileNames) {
				cleanUpTempFiles(new File(file, fileName), lastModified);
			}

			String[] subfileNames = file.list();

			if (subfileNames.length == 0) {
				FileUtil.deltree(file);

				return;
			}
		}
	}

	public File getTempFile(S3Object s3Object, String fileName)
		throws IOException {

		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append(_TEMP_DIR_NAME);
		sb.append(
				DateUtil.getCurrentDate(
					_TEMP_DIR_PATTERN, LocaleUtil.getDefault()));
		sb.append(_s3KeyTransformer.getNormalizedFileName(fileName));

		ObjectMetadata metadata = s3Object.getObjectMetadata();
		Date lastModifiedDate = metadata.getLastModified();

		sb.append(lastModifiedDate.getTime());

		String tempFileName = sb.toString();

		File tempFile = new File(tempFileName);

		if (tempFile.exists() &&
			(tempFile.lastModified() >= lastModifiedDate.getTime())) {

			return tempFile;
		}

		InputStream inputStream = s3Object.getObjectContent();

		if (inputStream == null) {
			throw new IOException("S3 object input stream is null");
		}

		OutputStream outputStream = null;

		try {
			File parentFile = tempFile.getParentFile();

			FileUtil.mkdirs(parentFile);

			outputStream = new FileOutputStream(tempFile);

			StreamUtil.transfer(inputStream, outputStream);
		}
		finally {
			StreamUtil.cleanUp(inputStream, outputStream);
		}

		return tempFile;
	}

	private static final String _TEMP_DIR_NAME = "/liferay/s3";

	private static final String _TEMP_DIR_PATTERN = "/yyyy/MM/dd/HH/";

	private int _calledGetFileCount;
	private final S3KeyTransformer _s3KeyTransformer;
	private final int _tempDirCleanUpExpunge;
	private final int _tempDirCleanUpFrequency;

}