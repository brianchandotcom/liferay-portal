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
import com.liferay.sync.engine.filesystem.Watcher;
import com.liferay.sync.engine.filesystem.util.WatcherRegistry;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;
import com.liferay.sync.engine.util.ConnectionRetryUtil;
import com.liferay.sync.engine.util.FileUtil;
import com.liferay.sync.engine.util.JSONUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

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
				return
					"com.liferay.portal.kernel.jsonwebservice." +
						"NoSuchJSONWebServiceException";
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
				return
					"com.liferay.portal.kernel.jsonwebservice." +
						"NoSuchJSONWebServiceException";
			}
		}

		return exception;
	}

	@Override
	public boolean handlePortalException(String exception) throws Exception {
		if (exception.isEmpty()) {
			return false;
		}

		int pos = exception.indexOf("$");

		if (pos > 0) {
			exception = exception.substring(0, pos);
		}

		boolean retryInProgress = ConnectionRetryUtil.retryInProgress(
			getSyncAccountId());

		if (!retryInProgress && _logger.isDebugEnabled()) {
			_logger.debug("Handling exception {}", exception);
		}

		if (exception.equals(
				"com.liferay.portal.kernel.lock.DuplicateLockException")) {

			SyncFile syncFile = getLocalSyncFile();

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_DUPLICATE_LOCK);

			SyncFileService.update(syncFile);
		}
		else if (exception.equals(
					"com.liferay.portal.kernel.upload.UploadException") ||
				 exception.contains("SizeLimitExceededException")) {

			SyncFile syncFile = getLocalSyncFile();

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_EXCEEDED_SIZE_LIMIT);

			SyncFileService.update(syncFile);
		}
		else if (exception.equals(
					"com.liferay.portal.security.auth.PrincipalException")) {

			SyncFileService.setStatuses(
				getLocalSyncFile(), SyncFile.STATE_ERROR,
				SyncFile.UI_EVENT_INVALID_PERMISSIONS);
		}
		else if (exception.equals(
					"com.liferay.portlet.documentlibrary." +
						"FileExtensionException")) {

			SyncFile syncFile = getLocalSyncFile();

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_INVALID_FILE_EXTENSION);

			SyncFileService.update(syncFile);
		}
		else if (exception.equals(
					"com.liferay.portlet.documentlibrary.FileNameException") ||
				 exception.equals(
					 "com.liferay.portlet.documentlibrary." +
						 "FolderNameException")) {

			SyncFile syncFile = getLocalSyncFile();

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_INVALID_FILE_NAME);

			SyncFileService.update(syncFile);
		}
		else if (exception.equals(
					"com.liferay.portlet.documentlibrary." +
						"NoSuchFileEntryException") ||
				 exception.equals(
					"com.liferay.portlet.documentlibrary." +
						"NoSuchFolderException")) {

			SyncFile syncFile = getLocalSyncFile();

			Path filePath = Paths.get(syncFile.getFilePathName());

			if (Files.exists(filePath)) {
				Watcher watcher = WatcherRegistry.getWatcher(
					getSyncAccountId());

				List<String> deletedFilePathNames =
					watcher.getDeletedFilePathNames();

				deletedFilePathNames.add(syncFile.getFilePathName());

				FileUtil.deleteFile(filePath);
			}

			SyncFileService.deleteSyncFile(syncFile, false);
		}
		else if (exception.equals(
					"com.liferay.sync.SyncClientMinBuildException")) {

			retryServerConnection(
				SyncAccount.UI_EVENT_MIN_BUILD_REQUIREMENT_FAILED);
		}
		else if (exception.equals(
					"com.liferay.sync.SyncServicesUnavailableException")) {

			retryServerConnection(
				SyncAccount.UI_EVENT_SYNC_SERVICES_NOT_ACTIVE);
		}
		else if (exception.equals(
					"com.liferay.sync.SyncSiteUnavailableException")) {

			handleSiteDeactivatedException();
		}
		else if (exception.equals(
					"com.liferay.portal.kernel.jsonwebservice." +
						"NoSuchJSONWebServiceException")) {

			retryServerConnection(SyncAccount.UI_EVENT_SYNC_WEB_MISSING);
		}
		else if (exception.equals("Authenticated access required") ||
				 exception.equals("java.lang.SecurityException")) {

			retryServerConnection(
				SyncAccount.UI_EVENT_AUTHENTICATION_EXCEPTION);
		}
		else {
			if (retryInProgress && _logger.isDebugEnabled()) {
				_logger.debug("Handling exception {}", exception);
			}

			SyncFile syncFile = getLocalSyncFile();

			syncFile.setState(SyncFile.STATE_ERROR);
			syncFile.setUiEvent(SyncFile.UI_EVENT_NONE);

			SyncFileService.update(syncFile);
		}

		return true;
	}

	@Override
	public Void handleResponse(HttpResponse httpResponse) {
		try {
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
		}

		return null;
	}

	@Override
	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {

		Header header = httpResponse.getFirstHeader("Sync-JWT");

		if (header != null) {
			Session session = SessionManager.getSession(getSyncAccountId());

			session.setToken(header.getValue());
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