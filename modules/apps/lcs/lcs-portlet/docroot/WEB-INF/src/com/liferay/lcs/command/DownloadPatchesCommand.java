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
import com.liferay.lcs.util.ResponseMessageUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.patcher.PatcherUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

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
		if (!PatcherUtil.isConfigured() ||
			(PatcherUtil.getPatchDirectory() == null)) {

			return;
		}

		Map<String, String> payload =
			(Map<String, String>)commandMessage.getPayload();

		for (String fileName : payload.keySet()) {
			if (_log.isInfoEnabled()) {
				_log.info("Downloading patch " + fileName);
			}

			Map<String, Integer> responsePayload = new HashMap<>();

			responsePayload.put(fileName, LCSConstants.PATCHES_DOWNLOADING);

			ResponseMessage responseMessage =
				ResponseMessageUtil.createResponseMessage(
					commandMessage, responsePayload);

			_lcsConnectionManager.sendMessage(responseMessage);

			File file = new File(PatcherUtil.getPatchDirectory(), fileName);

			String urlString = payload.get(fileName);

			if (_log.isDebugEnabled()) {
				_log.debug("Download URL " + urlString);
			}

			try {
				URL url = new URL(urlString);

				InputStream inputStream = new BufferedInputStream(
					url.openStream());

				FileUtil.write(file, inputStream);

				inputStream.close();
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);

				responsePayload.clear();

				responsePayload.put(fileName, LCSConstants.PATCHES_ERROR);

				responseMessage = ResponseMessageUtil.createResponseMessage(
					commandMessage, responsePayload, ioe.getMessage());

				_lcsConnectionManager.sendMessage(responseMessage);

				return;
			}

			if (!isValidZipFile(file)) {
				responsePayload.clear();

				responsePayload.put(fileName, LCSConstants.PATCHES_ERROR);

				responseMessage = ResponseMessageUtil.createResponseMessage(
					commandMessage, responsePayload,
					"File " + file + " is corrupted");

				_lcsConnectionManager.sendMessage(responseMessage);

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

	private boolean isValidZipFile(File file) {
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

	private static Log _log = LogFactoryUtil.getLog(
		DownloadPatchesCommand.class);

	private LCSConnectionManager _lcsConnectionManager;

}