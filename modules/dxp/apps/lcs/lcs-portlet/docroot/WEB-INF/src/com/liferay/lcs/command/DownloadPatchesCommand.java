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

package com.liferay.lcs.command;

import com.liferay.lcs.exception.CompressionException;
import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPatcherUtil;
import com.liferay.lcs.util.PatchUtil;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class DownloadPatchesCommand implements Command {

	public void downloadPatches(CommandMessage commandMessage)
		throws CompressionException, JSONWebServiceException {

		Map<String, String> payload =
			(Map<String, String>)commandMessage.getPayload();

		for (Map.Entry<String, String> entry : payload.entrySet()) {
			String fileName = entry.getKey();

			if (fileName.endsWith(LCSConstants.PATCHES_MD5SUM_SUFFIX)) {
				continue;
			}

			_lcsConnectionManager.sendMessage(
				_getResponseMessage(
					commandMessage, fileName,
					LCSConstants.PATCHES_DOWNLOADING));

			File localFile = new File(
				LCSPatcherUtil.getPatchDirectory(), fileName);

			String remoteFileURL = entry.getValue();

			if (_log.isInfoEnabled()) {
				_log.info("Downloading remote file URL " + remoteFileURL);
			}

			try {
				while (!_transferBytes(remoteFileURL, localFile));

				String md5Sum = payload.get(
					fileName + LCSConstants.PATCHES_MD5SUM_SUFFIX);

				_checkMD5Sum(fileName, md5Sum);

				_checkZipFile(localFile);
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				_lcsConnectionManager.sendMessage(
					_getResponseMessage(
						commandMessage, fileName, LCSConstants.PATCHES_ERROR));

				return;
			}

			_lcsConnectionManager.sendMessage(
				_getResponseMessage(
					commandMessage, fileName, LCSConstants.PATCHES_DOWNLOADED));

			if (_log.isInfoEnabled()) {
				_log.info("Downloaded patch " + fileName);
			}
		}
	}

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (!LCSPatcherUtil.isConfigured() ||
			(LCSPatcherUtil.getPatchDirectory() == null)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to execute command. Patcher util is not " +
						"configured");
			}

			return;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Executing download patches command");
		}

		try {
			downloadPatches(commandMessage);
		}
		catch (Exception e) {
			StringBuilder sb = new StringBuilder(4);

			sb.append("Failed to download patches");

			if (e instanceof CompressionException ||
				e instanceof JSONWebServiceException) {

				sb.append(". Unable to send download status feedback to LCS ");
				sb.append("gateway. Please check download status at LCS ");
				sb.append("dashboard and repeat procedure if necessary");
			}

			_log.error(sb.toString(), e);
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private void _checkMD5Sum(String fileName, String md5Sum)
		throws IOException {

		if (Validator.isNull(md5Sum)) {
			throw new IOException("Unable to check MD5Sum which is null");
		}

		File downloadedFile = new File(
			LCSPatcherUtil.getPatchDirectory(), fileName);

		byte[] bytes = FileUtil.getBytes(downloadedFile);

		String actualMD5Sum = PatchUtil.getPatchMD5Sum(bytes);

		if (actualMD5Sum == null) {
			throw new IOException("Unable to check MD5Sum which is null");
		}

		if (!md5Sum.equals(actualMD5Sum)) {
			_log.error("MD5Sum check failed for file " + fileName);

			throw new IOException("MD5Sum check failed");
		}
	}

	private void _checkZipFile(File file) throws IOException {
		ZipFile zipfile = null;
		ZipInputStream zipInputStream = null;

		try {
			zipfile = new ZipFile(file);

			FileInputStream fileInputStream = new FileInputStream(file);

			zipInputStream = new ZipInputStream(fileInputStream);

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			if (zipEntry == null) {
				throw new ZipException(
					"Expected ZIP entry instance but encounted null");
			}

			while (zipEntry != null) {
				zipfile.getInputStream(zipEntry);

				zipEntry.getCompressedSize();
				zipEntry.getCrc();
				zipEntry.getName();

				zipEntry = zipInputStream.getNextEntry();
			}
		}
		finally {
			try {
				if (zipfile != null) {
					zipfile.close();
				}

				if (zipInputStream != null) {
					zipInputStream.close();
				}
			}
			catch (IOException ioe) {
				_log.error("Unable to close validated ZIP file");
			}
		}
	}

	private ResponseMessage _getResponseMessage(
		CommandMessage commandMessage, String fileName, int downloadStatus) {

		Map<String, Integer> responsePayload = new HashMap<>();

		responsePayload.put(fileName, downloadStatus);

		return ResponseMessageUtil.createResponseMessage(
			commandMessage, responsePayload);
	}

	private boolean _transferBytes(String remoteFileURL, File localFile)
		throws IOException {

		long totalTransferred = localFile.length();

		URL url = new URL(remoteFileURL);

		URLConnection urlConnection = url.openConnection();

		urlConnection.setRequestProperty(
			"Range", "bytes=" + totalTransferred + "-");

		ReadableByteChannel readableByteChannel = Channels.newChannel(
			urlConnection.getInputStream());

		long remaining = urlConnection.getContentLength();

		long transferred = 0;

		long bufferSize = 65536;

		if (_log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder(4);

			sb.append("Remaining size: ");
			sb.append(remaining);
			sb.append(", transferred size: ");
			sb.append(totalTransferred);

			_log.debug(sb.toString());
		}

		FileOutputStream fileOutputStream = new FileOutputStream(
			localFile, true);

		while (remaining > 0) {
			try {
				FileChannel fileChannel = fileOutputStream.getChannel();

				long currentTransferred = fileChannel.transferFrom(
					readableByteChannel, totalTransferred, bufferSize);

				totalTransferred += currentTransferred;
				transferred += currentTransferred;

				if (currentTransferred == 0) {
					break;
				}
			}
			catch (IOException ioe) {
				if (_log.isDebugEnabled()) {
					_log.debug("File transfer is broken", ioe);
				}

				break;
			}
		}

		if (transferred == remaining) {
			if (_log.isInfoEnabled()) {
				_log.info("File transfer is complete");
			}

			fileOutputStream.close();
			readableByteChannel.close();

			return true;
		}

		fileOutputStream.close();

		if (_log.isInfoEnabled()) {
			_log.info("File transfer is incomplete, retrying");
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadPatchesCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}