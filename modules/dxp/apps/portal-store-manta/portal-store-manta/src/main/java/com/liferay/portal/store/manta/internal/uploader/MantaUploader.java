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

package com.liferay.portal.store.manta.internal.uploader;

import com.joyent.manta.client.MantaClient;
import com.joyent.manta.client.multipart.MantaMultipartManager;
import com.joyent.manta.client.multipart.MantaMultipartUpload;
import com.joyent.manta.client.multipart.MantaMultipartUploadPart;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Park
 */
public class MantaUploader {

	public MantaUploader(MantaClient mantaClient) {
		_mantaClient = mantaClient;

		_multipartUploadThreshold = 0;
		_mantaMultipartManager = null;
	}

	public MantaUploader(
		MantaClient mantaClient, long multipartUploadThreshold) {

		_mantaClient = mantaClient;
		_multipartUploadThreshold = multipartUploadThreshold;

		_mantaMultipartManager = new MantaMultipartManager(_mantaClient);
	}

	public void put(String fileName, byte[] bytes) throws IOException {
		_mantaClient.putDirectory(getDir(fileName), true);

		if ((_mantaMultipartManager != null) &&
			(bytes.length > _multipartUploadThreshold)) {

			putMultipart(fileName, bytes);
		}
		else {
			_mantaClient.put(fileName, bytes);
		}
	}

	public void put(String fileName, File file) throws IOException {
		_mantaClient.putDirectory(getDir(fileName), true);

		if ((_mantaMultipartManager != null) &&
			(file.length() > _multipartUploadThreshold)) {

			putMultipart(fileName, file);
		}
		else {
			_mantaClient.put(fileName, file);
		}
	}

	public void put(String fileName, InputStream is) throws IOException {
		File file = null;

		try {
			file = FileUtil.createTempFile(is);

			put(fileName, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	protected void createPartFiles(File file, int count, List<File> partFiles)
		throws IOException {

		long partSize = (file.length() / count) + 1;

		try (InputStream inputStream = new FileInputStream(file)) {
			for (int i = 0; i < count; i++) {
				File partFile = FileUtil.createTempFile();

				partFiles.add(partFile);

				try (OutputStream outputStream = new FileOutputStream(
						partFile)) {

					StreamUtil.transfer(
						inputStream, outputStream, StreamUtil.BUFFER_SIZE,
						false, partSize);
				}
			}
		}
	}

	protected String getDir(String fileName) {
		int i = fileName.lastIndexOf(StringPool.SLASH);

		if (i < 0) {
			return fileName;
		}

		return fileName.substring(0, i);
	}

	protected void putMultipart(String fileName, byte[] bytes)
		throws IOException {

		File file = null;

		try {
			file = FileUtil.createTempFile(bytes);

			putMultipart(fileName, file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	protected void putMultipart(String fileName, File file) throws IOException {
		int partCount = (int)(file.length() / _multipartUploadThreshold) + 1;

		List<File> partFiles = new ArrayList<>(partCount);

		try {
			createPartFiles(file, partCount, partFiles);

			MantaMultipartUpload mantaMultipartUpload =
				_mantaMultipartManager.initiateUpload(fileName);

			List<MantaMultipartUploadPart> mantaMultipartUploadParts =
				new ArrayList<>(partFiles.size());

			for (int i = 0; i < partFiles.size(); i++) {
				MantaMultipartUploadPart mantaMultipartUploadPart =
					_mantaMultipartManager.uploadPart(
						mantaMultipartUpload, i, partFiles.get(i));

				mantaMultipartUploadParts.add(mantaMultipartUploadPart);
			}

			_mantaMultipartManager.complete(
				mantaMultipartUpload, mantaMultipartUploadParts.stream());

			_mantaMultipartManager.waitForCompletion(
				mantaMultipartUpload,
				uuid -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Timed out waiting for Manta to commit upload of " +
								fileName);
					}

					return uuid;
				});
		}
		finally {
			for (File partFile : partFiles) {
				FileUtil.delete(partFile);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MantaUploader.class);

	private final MantaClient _mantaClient;
	private final MantaMultipartManager _mantaMultipartManager;
	private final long _multipartUploadThreshold;

}