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

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.documentlibrary.event.Event;
import com.liferay.sync.engine.documentlibrary.event.GetSyncContextEvent;
import com.liferay.sync.engine.documentlibrary.util.FileEventManager;
import com.liferay.sync.engine.documentlibrary.util.ServerEventUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.service.SyncFileService;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.ConnectionRetryUtil;

import java.io.FileNotFoundException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BaseHandler implements Handler<Void> {

	public BaseHandler(Event event) {
		_event = event;
	}

	@Override
	public String getException(String response) {
		return null;
	}

	@Override
	public void handleException(Exception e) {
		if (_event.isCancelled()) {
			return;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			getSyncAccountId());

		if (!ConnectionRetryUtil.retryInProgress(getSyncAccountId()) &&
			_logger.isDebugEnabled()) {

			_logger.debug("Handling exception {}", e.toString());
		}

		if (e instanceof HttpResponseException) {
			HttpResponseException hre = (HttpResponseException)e;

			int statusCode = hre.getStatusCode();

			if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
				if (syncAccount.getUiEvent() ==
						SyncAccount.UI_EVENT_AUTHENTICATION_EXCEPTION) {

					if (_logger.isDebugEnabled()) {
						_logger.debug(
							"Aborting reauthentication attempts. Retry limit " +
								"reached.");
					}

					syncAccount.setState(SyncAccount.STATE_DISCONNECTED);

					SyncAccountService.update(syncAccount);
				}
				else {
					syncAccount.setState(SyncAccount.STATE_DISCONNECTED);
					syncAccount.setUiEvent(
						SyncAccount.UI_EVENT_AUTHENTICATION_EXCEPTION);

					SyncAccountService.update(syncAccount);

					retryServerConnection(
						SyncAccount.UI_EVENT_AUTHENTICATION_EXCEPTION);
				}

				return;
			}
		}

		if ((e instanceof ClientProtocolException) ||
			(e instanceof ConnectTimeoutException) ||
			(e instanceof HttpHostConnectException) ||
			(e instanceof NoHttpResponseException) ||
			(e instanceof SocketException) ||
			(e instanceof SocketTimeoutException) ||
			(e instanceof UnknownHostException)) {

			retryServerConnection(SyncAccount.UI_EVENT_CONNECTION_EXCEPTION);
		}
		else if (e instanceof FileNotFoundException) {
			SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

			String message = e.getMessage();

			if (message.contains("The process cannot access the file")) {
				if (_logger.isTraceEnabled()) {
					_logger.trace(
						"Retrying event {} for sync file {}", _event, syncFile);
				}

				ExecutorService executorService =
					SyncEngine.getEventProcessorExecutorService();

				executorService.execute(_event);
			}
			else if (syncFile.getVersion() == null) {
				SyncFileService.deleteSyncFile(syncFile, false);
			}
		}
		else {
			_logger.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean handlePortalException(String exception) throws Exception {
		return false;
	}

	@Override
	public Void handleResponse(HttpResponse httpResponse) {
		if (_event.isCancelled()) {
			return null;
		}

		try {
			StatusLine statusLine = httpResponse.getStatusLine();

			if ((statusLine.getStatusCode() != HttpStatus.SC_OK) &&
				(statusLine.getStatusCode() != HttpStatus.SC_PARTIAL_CONTENT)) {

				_logger.error("Status code {}", statusLine.getStatusCode());

				throw new HttpResponseException(
					statusLine.getStatusCode(), statusLine.getReasonPhrase());
			}

			if (_logger.isTraceEnabled()) {
				Class<?> clazz = getClass();

				SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

				if (syncFile != null) {
					_logger.trace(
						"Handling response {} file path {}",
							clazz.getSimpleName(), syncFile.getFilePathName());
				}
				else {
					_logger.trace(
						"Handling response {}", clazz.getSimpleName());
				}
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

	public void processFinally() {
	}

	@Override
	public void processResponse(String response) throws Exception {
	}

	public void removeEvent() {
		FileEventManager.removeEvent(_event);
	}

	protected void doHandleResponse(HttpResponse httpResponse)
		throws Exception {
	}

	protected SyncFile getLocalSyncFile() {
		SyncFile localSyncFile = (SyncFile)getParameterValue("syncFile");

		return SyncFileService.fetchSyncFile(localSyncFile.getSyncFileId());
	}

	protected Map<String, Object> getParameters() {
		return _event.getParameters();
	}

	protected Object getParameterValue(String key) {
		return _event.getParameterValue(key);
	}

	protected long getSyncAccountId() {
		return _event.getSyncAccountId();
	}

	protected void handleSiteDeactivatedException() {
		SyncSite syncSite = (SyncSite)getParameterValue("syncSite");

		if (syncSite == null) {
			SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

			syncSite = SyncSiteService.fetchSyncSite(
				syncFile.getRepositoryId(), getSyncAccountId());
		}

		if (syncSite != null) {
			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Sync site {} was deactivated or removed.",
					syncSite.getName());
			}

			syncSite.setUiEvent(SyncSite.UI_EVENT_SYNC_SITE_DEACTIVATED);

			SyncSiteService.update(syncSite);

			SyncSiteService.deleteSyncSite(syncSite.getSyncSiteId());
		}
	}

	protected boolean isEventCancelled() {
		return _event.isCancelled();
	}

	protected void retryServerConnection(int uiEvent) {
		if (!(_event instanceof GetSyncContextEvent) &&
			ConnectionRetryUtil.retryInProgress(getSyncAccountId())) {

			return;
		}

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			getSyncAccountId());

		int retryCount = ConnectionRetryUtil.getRetryCount(getSyncAccountId());

		if (retryCount > 0) {
			syncAccount.setState(SyncAccount.STATE_DISCONNECTED);
			syncAccount.setUiEvent(uiEvent);

			SyncAccountService.update(syncAccount);

			if (_logger.isDebugEnabled()) {
				_logger.debug(
					"Attempting to reconnect to {}. Retry #{}.",
					syncAccount.getUrl(), retryCount);
			}
		}

		ServerEventUtil.retryServerConnection(
			getSyncAccountId(),
			ConnectionRetryUtil.incrementRetryDelay(getSyncAccountId()));
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseHandler.class);

	private final Event _event;

}