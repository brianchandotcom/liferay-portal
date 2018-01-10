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

import com.liferay.lcs.messaging.CommandMessage;
import com.liferay.lcs.messaging.ResponseMessage;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPatcherUtil;
import com.liferay.lcs.util.PatchUtil;
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

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

	@Override
	public void execute(CommandMessage commandMessage) throws PortalException {
		if (!LCSPatcherUtil.isConfigured() ||
			(LCSPatcherUtil.getPatchDirectory() == null)) {

			return;
		}

		if (_log.isTraceEnabled()) {
			_log.trace("Executing download patches command");
		}

		Map<String, String> payload =
			(Map<String, String>)commandMessage.getPayload();

		for (Map.Entry<String, String> entry : payload.entrySet()) {
			String fileName = entry.getKey();

			if (fileName.endsWith(LCSConstants.PATCHES_MD5SUM_SUFFIX)) {
				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Downloading patch " + fileName);
			}

			Map<String, Integer> responsePayload = new HashMap<>();

			responsePayload.put(fileName, LCSConstants.PATCHES_DOWNLOADING);

			ResponseMessage responseMessage =
				ResponseMessageUtil.createResponseMessage(
					commandMessage, responsePayload);

			_lcsConnectionManager.sendMessage(responseMessage);

			File localFile = new File(
				LCSPatcherUtil.getPatchDirectory(), fileName);

			String remoteFileURL = entry.getValue();

			if (_log.isDebugEnabled()) {
				_log.debug("Remote file URL " + remoteFileURL);
			}

			try {
				while (!_transferBytes(remoteFileURL, localFile));

				String md5Sum = payload.get(
					fileName + LCSConstants.PATCHES_MD5SUM_SUFFIX);

				if ((md5Sum != null) && !md5Sum.isEmpty()) {
					File downloadedFile = new File(
						LCSPatcherUtil.getPatchDirectory(), fileName);

					if (!_isValidMD5Sum(downloadedFile, md5Sum)) {
						_log.error(
							"Downloaded file " + fileName + " is corrupted");

						downloadedFile.delete();

						_sendErrorMessage(
							commandMessage, fileName, responsePayload,
							"Downloaded file " + fileName + " is corrupted");

						return;
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				_sendErrorMessage(
					commandMessage, fileName, responsePayload, e.getMessage());

				return;
			}

			if (!_isValidZipFile(localFile)) {
				_sendErrorMessage(
					commandMessage, fileName, responsePayload,
					"File " + localFile + " is corrupted");

				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Downloaded patch " + fileName);
			}

			responsePayload.clear();

			responsePayload.put(fileName, LCSConstants.PATCHES_DOWNLOADED);

			responseMessage = ResponseMessageUtil.createResponseMessage(
				commandMessage, responsePayload);

			_lcsConnectionManager.sendMessage(responseMessage);
		}
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	private boolean _isValidMD5Sum(File file, String expectedMD5Sum) {
		try {
			byte[] bytes = FileUtil.getBytes(file);

			String actualMD5Sum = PatchUtil.getPatchMD5Sum(bytes);

			if (actualMD5Sum != null) {
				return expectedMD5Sum.equals(actualMD5Sum);
			}
			else {
				return true;
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable check MD5 sum for file " + file);
			}

			return true;
		}
	}

	private boolean _isValidZipFile(File file) {
		ZipFile zipfile = null;
		ZipInputStream zipInputStream = null;

		try {
			zipfile = new ZipFile(file);

			FileInputStream fileInputStream = new FileInputStream(file);

			zipInputStream = new ZipInputStream(fileInputStream);

			ZipEntry zipEntry = zipInputStream.getNextEntry();

			if (zipEntry == null) {
				return false;
			}

			while (zipEntry != null) {
				zipfile.getInputStream(zipEntry);

				zipEntry.getCompressedSize();
				zipEntry.getCrc();
				zipEntry.getName();

				zipEntry = zipInputStream.getNextEntry();
			}

			return true;
		}
		catch (ZipException ze) {
			return false;
		}
		catch (IOException ioe) {
			return false;
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
			}
		}
	}

	private void _sendErrorMessage(
			CommandMessage commandMessage, String fileName,
			Map<String, Integer> responsePayload, String errorMessage)
		throws PortalException {

		responsePayload.clear();

		responsePayload.put(fileName, LCSConstants.PATCHES_ERROR);

		ResponseMessage responseMessage =
			ResponseMessageUtil.createResponseMessage(
				commandMessage, responsePayload, errorMessage);

		_lcsConnectionManager.sendMessage(responseMessage);
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