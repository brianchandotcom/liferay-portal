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

package com.liferay.sync.engine.documentlibrary.handler;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.util.JSONUtil;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.InputStream;

import java.nio.file.Paths;

import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.io.input.CountingInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class DownloadFilesHandler extends BaseHandler {

	public DownloadFilesHandler(Event event) {
		super(event);
	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		final Session session = SessionManager.getSession(getSyncAccountId());

		Header tokenHeader = httpResponse.getFirstHeader("Sync-JWT");

		if (tokenHeader != null) {
			session.setToken(tokenHeader.getValue());
		}

		Map<String, DownloadFileHandler> handlers =
			(Map<String, DownloadFileHandler>)getParameterValue("handlers");

		InputStream inputStream = null;

		try {
			HttpEntity httpEntity = httpResponse.getEntity();

			inputStream = new CountingInputStream(httpEntity.getContent()) {

				@Override
				protected synchronized void afterRead(int n) {
					session.incrementDownloadedBytes(n);

					super.afterRead(n);
				}

			};

			ZipInputStream zipInputStream = new ZipInputStream(inputStream);

			ZipEntry zipEntry = null;

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String zipEntryName = zipEntry.getName();

				if (zipEntryName.equals("errors.json")) {
					JsonNode rootJsonNode = JSONUtil.readTree(zipInputStream);

					Iterator<Map.Entry<String, JsonNode>> fields =
						rootJsonNode.fields();

					while (fields.hasNext()) {
						Map.Entry<String, JsonNode> field = fields.next();

						Handler<Void> handler = handlers.get(field.getKey());

						JsonNode valueJsonNode = field.getValue();

						JsonNode exceptionJsonNode = valueJsonNode.get(
							"exception");

						handler.handlePortalException(
							exceptionJsonNode.textValue());
					}

					break;
				}

				DownloadFileHandler downloadFileHandler = handlers.get(
					zipEntryName);

				SyncFile syncFile =
					(SyncFile)downloadFileHandler.getParameterValue("syncFile");

				if (downloadFileHandler.isUnsynced(syncFile)) {
					continue;
				}

				if (_logger.isTraceEnabled()) {
					_logger.trace(
						"Handling response {} file path {}",
							DownloadFileHandler.class.getSimpleName(),
							syncFile.getFilePathName());
				}

				try {
					downloadFileHandler.copyFile(
						syncFile, Paths.get(syncFile.getFilePathName()),
						new CloseShieldInputStream(zipInputStream), false);
				}
				catch (Exception e) {
					if (!isEventCancelled()) {
						_logger.error(e.getMessage(), e);
					}
				}
				finally {
					downloadFileHandler.removeEvent();
				}
			}
		}
		catch (Exception e) {
			if (!isEventCancelled() && _logger.isDebugEnabled()) {
				_logger.debug(e.getMessage(), e);
			}
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		DownloadFilesHandler.class);

}