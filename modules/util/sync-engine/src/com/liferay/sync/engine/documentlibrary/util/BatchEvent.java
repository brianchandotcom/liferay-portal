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

package com.liferay.sync.engine.documentlibrary.util;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.event.UpdateFileEntriesEvent;
import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.JSONUtil;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BatchEvent {

	public BatchEvent(long syncAccountId) throws Exception {
		_syncAccountId = syncAccountId;
	}

	public synchronized boolean addEvent(Event event) {
		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		if (syncAccount.getBatchFileMaxSize() <= 0) {
			return false;
		}

		try {
			Map<String, Object> parameters = event.getParameters();

			SyncFile syncFile = (SyncFile)parameters.get("syncFile");

			String zipFileId =
				syncFile.getSyncFileId() + "_" + System.currentTimeMillis();

			Path deltaFilePath = (Path)parameters.get("deltaFilePath");
			Path filePath = (Path)parameters.get("filePath");

			if (deltaFilePath != null) {
				if (!addFile(
						deltaFilePath, zipFileId,
						syncAccount.getBatchFileMaxSize())) {

					return false;
				}
			}
			else if (filePath != null) {
				if (!addFile(
						filePath, zipFileId,
						syncAccount.getBatchFileMaxSize())) {

					return false;
				}
			}

			parameters.put("urlPath", event.getURLPath());
			parameters.put("zipFileId", zipFileId);

			parameters = new HashMap<>(parameters);

			parameters.remove("deltaFilePath");
			parameters.remove("filePath");
			parameters.remove("syncFile");

			_batchParameters.add(parameters);

			_eventCount++;

			_handlers.put(zipFileId, event.getHandler());

			if ((_eventCount >= 250) ||
				(_totalFileSize >= syncAccount.getBatchFileMaxSize())) {

				fireBatchEvent();
			}

			return true;
		}
		catch (IOException ioe) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(ioe.getMessage(), ioe);
			}

			return false;
		}
	}

	public synchronized void fireBatchEvent() {
		try {
			if (_closed || (_eventCount == 0)) {
				return;
			}

			Path filePath = Files.createTempFile("manifest", ".json");

			JSONUtil.writeValue(filePath.toFile(), _batchParameters);

			writeFilePathToZip(filePath, "manifest.json");

			_zipOutputStream.close();

			Map<String, Object> parameters = new HashMap<>();

			parameters.put("handlers", _handlers);
			parameters.put("zipFilePath", _zipFilePath);

			UpdateFileEntriesEvent updateFileEntriesEvent =
				new UpdateFileEntriesEvent(_syncAccountId, parameters);

			updateFileEntriesEvent.run();

			_closed = true;
		}
		catch (Exception e) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(e.getMessage(), e);
			}
		}
	}

	public synchronized boolean isClosed() {
		return _closed;
	}

	protected boolean addFile(
			Path filePath, String zipFileId, int batchFileMaxSize)
		throws IOException {

		long fileSize = Files.size(filePath);

		if (fileSize >= (batchFileMaxSize / 10)) {
			return false;
		}

		writeFilePathToZip(filePath, zipFileId);

		_totalFileSize += fileSize;

		return true;
	}

	protected void writeFilePathToZip(Path filePath, String name)
		throws IOException {

		InputStream inputStream = null;

		try {
			if (_zipOutputStream == null) {
				SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
					_syncAccountId);

				_zipFilePath = FileUtil.getFilePath(
					syncAccount.getFilePathName(), ".data",
					String.valueOf(System.currentTimeMillis()) + ".zip");

				OutputStream outputStream = Files.newOutputStream(_zipFilePath);

				_zipOutputStream = new ZipOutputStream(outputStream);

				_zipOutputStream.setLevel(0);
			}

			ZipEntry zipEntry = new ZipEntry(name);

			_zipOutputStream.putNextEntry(zipEntry);

			inputStream = Files.newInputStream(filePath);

			byte[] buffer = new byte[1024];

			int length = 0;

			while ((length = inputStream.read(buffer)) > 0) {
				_zipOutputStream.write(buffer, 0, length);
			}
		}
		finally {
			if (_zipOutputStream != null) {
				_zipOutputStream.closeEntry();
			}

			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BatchEvent.class);

	private final List<Map<String, Object>> _batchParameters =
		new ArrayList<>();
	private boolean _closed;
	private int _eventCount;
	private final Map<String, Handler<Void>> _handlers = new HashMap<>();
	private final long _syncAccountId;
	private long _totalFileSize;
	private Path _zipFilePath;
	private ZipOutputStream _zipOutputStream;

}