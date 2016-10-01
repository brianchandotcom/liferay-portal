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

package com.liferay.sync.engine.document.library.handler;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.sync.engine.document.library.event.Event;
import com.liferay.sync.engine.file.system.Watcher;
import com.liferay.sync.engine.file.system.util.WatcherManager;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.util.ConnectionRetryUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.JSONUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BaseJSONHandler extends BaseHandler {

	public BaseJSONHandler(Event event) {
		super(event);
	}

	@Override
	public String getException(String response) {
		String exception = null;

		JsonNode responseJsonNode = null;

		try {
			response = StringEscapeUtils.unescapeJava(response);

			response = response.replaceAll("\n", "\\n");

			responseJsonNode = JSONUtil.readTree(response);
		}
		catch (Exception e) {
			return "";
		}

		JsonNode errorJsonNode = responseJsonNode.get("error");

		if (errorJsonNode == null) {
			JsonNode exceptionJsonNode = responseJsonNode.get("exception");

			if (exceptionJsonNode == null) {
				return "";
			}

			exception = exceptionJsonNode.asText();

			if (exception.startsWith("No JSON web service action")) {
				return "NoSuchJSONWebServiceException";
			}
		}

		if (exception == null) {
			JsonNode typeJsonNode = null;

			JsonNode rootCauseJsonNode = responseJsonNode.get("rootCause");

			if (rootCauseJsonNode != null) {
				typeJsonNode = rootCauseJsonNode.get("type");
			}
			else {
				typeJsonNode = errorJsonNode.get("type");
			}

			exception = typeJsonNode.asText();
		}

		if (exception.equals("java.lang.RuntimeException")) {
			JsonNode messageJsonNode = null;

			if (errorJsonNode != null) {
				messageJsonNode = errorJsonNode.get("message");
			}
			else {
				messageJsonNode = responseJsonNode.get("message");
			}

			String message = messageJsonNode.asText();

			if (message.startsWith("No JSON web service action")) {
				return "NoSuchJSONWebServiceException";
			}
		}

		return exception;
	}

	@Override
	public boolean handlePortalException(String exception) throws Exception {
		if (exception.isEmpty()) {
			return false;
		}

		String innerException = "";

		if (exception.contains("$")) {
			String[] exceptionParts = exception.split("\\$");

			exception = exceptionParts[0];
			innerException = exceptionParts[1];
		}

		boolean retryInProgress = ConnectionRetryUtil.retryInProgress(
			getSyncAccountId());

		if (!retryInProgress && _logger.isDebugEnabled()) {
			_logger.debug("Handling exception {}", exception);
		}

		if (exception.equals("Authenticated access required") ||
			exception.equals("java.lang.SecurityException")) {

			throw new HttpResponseException(
				HttpStatus.SC_UNAUTHORIZED, "Authenticated access required");
		}
		else if (exception.endsWith("DuplicateLockException")) {
			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return true;
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_DUPLICATE_LOCK);

			SyncFileService.update(syncFile);
		}
		else if (exception.endsWith("FileExtensionException")) {
			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return true;
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_INVALID_FILE_EXTENSION);

			SyncFileService.update(syncFile);
		}
		else if (exception.endsWith("FileNameException") ||
				 exception.endsWith("FolderNameException")) {

			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return true;
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_INVALID_FILE_NAME);

			SyncFileService.update(syncFile);
		}
		else if (exception.equals("java.lang.OutOfMemoryError")) {
			retryServerConnection(SyncAccount.UI_EVENT_CONNECTION_EXCEPTION);
		}
		else if (exception.endsWith("NoSuchFileEntryException") ||
				 exception.endsWith("NoSuchFolderException")) {

			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return true;
			}

			Path filePath = Paths.get(syncFile.getFilePathName());

			if (FileUtil.exists(filePath)) {
				Watcher watcher = WatcherManager.getWatcher(getSyncAccountId());

				watcher.addDeletedFilePathName(syncFile.getFilePathName());

				FileUtil.deleteFile(filePath);
			}

			SyncFileService.deleteSyncFile(syncFile, false);
		}
		else if (exception.endsWith("NoSuchJSONWebServiceException")) {
			retryServerConnection(SyncAccount.UI_EVENT_SYNC_WEB_MISSING);
		}
		else if (exception.endsWith("PrincipalException")) {
			SyncFile syncFile = getLocalSyncFile();

			if (syncFile == null) {
				return true;
			}

			SyncFileService.setStatuses(
				syncFile, SyncFile.STATE_ERROR,
				SyncFile.UI_EVENT_INVALID_PERMISSIONS);
		}
		else if (exception.endsWith("SyncClientMinBuildException")) {
			retryServerConnection(
				SyncAccount.UI_EVENT_MIN_BUILD_REQUIREMENT_FAILED);
		}
		else if (exception.endsWith("SyncDeviceActiveException")) {
			retryServerConnection(SyncAccount.UI_EVENT_SYNC_ACCOUNT_NOT_ACTIVE);
		}
		else if (exception.endsWith("SyncDeviceWipeException")) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Wiping Sync account {}", getSyncAccountId());
			}

			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				getSyncAccountId());

			syncAccount.setUiEvent(SyncAccount.UI_EVENT_SYNC_ACCOUNT_WIPED);

			SyncAccountService.update(syncAccount);

			SyncAccountService.deleteSyncAccount(getSyncAccountId(), false);
		}
		else if (exception.endsWith("SyncServicesUnavailableException")) {
			retryServerConnection(
				SyncAccount.UI_EVENT_SYNC_SERVICES_NOT_ACTIVE);
		}
		else if (exception.endsWith("SyncSiteUnavailableException")) {
			handleSiteDeactivatedException();
		}
		else if (exception.endsWith("UploadException") ||
				 innerException.equals("SizeLimitExceededException")) {

			SyncFile syncFile = getLocalSyncFile();

			if ((syncFile == null) || syncFile.isSystem()) {
				return true;
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_EXCEEDED_SIZE_LIMIT);

			SyncFileService.update(syncFile);
		}
		else {
			if (retryInProgress && _logger.isDebugEnabled()) {
				_logger.debug("Handling exception {}", exception);
			}

			SyncFile syncFile = getLocalSyncFile();

			if ((syncFile == null) || syncFile.isSystem()) {
				return true;
			}

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

			SyncFileService.update(syncFile);
		}

		return true;
	}

	@Override
	public Void handleResponse(HttpResponse httpResponse) {
		try {
			if (isEventCancelled()) {
				return null;
			}

			StatusLine statusLine = httpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				String response = getResponseString(httpResponse);

				if (handlePortalException(getException(response))) {
					return null;
				}

				_logger.error("Status code {}", statusLine.getStatusCode());

				throw new HttpResponseException(
					statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}

			doHandleResponse(httpResponse);
		}
		catch (Exception e) {
			handleException(e);
		}
		finally {
			processFinally();

			removeEvent();
		}

		return null;
	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		Header header = httpResponse.getFirstHeader("Sync-JWT");

		if (header != null) {
			Session session = SessionManager.getSession(getSyncAccountId());

			session.addHeader("Sync-JWT", header.getValue());
		}

		String response = getResponseString(httpResponse);

		if (handlePortalException(getException(response))) {
			return;
		}

		if (_logger.isTraceEnabled()) {
			logResponse(response);
		}

		processResponse(response);
	}

	protected String getResponseString(HttpResponse httpResponse)
		throws Exception {

		HttpEntity httpEntity = httpResponse.getEntity();

		return EntityUtils.toString(httpEntity);
	}

	protected void logResponse(String response) {
		Class<?> clazz = getClass();

		_logger.trace(
			"Handling response {} {}", clazz.getSimpleName(), response);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseJSONHandler.class);

}