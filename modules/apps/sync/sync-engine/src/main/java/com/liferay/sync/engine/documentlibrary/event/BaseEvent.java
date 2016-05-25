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

package com.liferay.sync.engine.documentlibrary.event;

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.documentlibrary.util.BatchEvent;
import com.liferay.sync.engine.documentlibrary.util.BatchEventManager;
import com.liferay.sync.engine.documentlibrary.util.FileEventManager;
import com.liferay.sync.engine.documentlibrary.util.ServerUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;

import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public abstract class BaseEvent implements Event {

	public BaseEvent(
		long syncAccountId, String urlPath, Map<String, Object> parameters) {

		_syncAccountId = syncAccountId;
		_urlPath = urlPath;
		_parameters = parameters;

		FileEventManager.addEvent(this);
	}

	@Override
	public void cancel() {
		if (_httpGet != null) {
			_httpGet.abort();
		}

		if (_httpPost != null) {
			_httpPost.abort();
		}

		FileEventManager.removeEvent(this);

		_cancelled = true;
	}

	public void executeAsynchronousGet(HttpGet httpGet) throws Exception {
		Session session = getSession();

		_httpGet = httpGet;

		session.asynchronousExecute(_httpGet, _handler);
	}

	public void executeAsynchronousPost(
			String urlPath, Map<String, Object> parameters)
		throws Exception {

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		executeAsynchronousPost(
			syncAccount.getUrl() + "/api/jsonws" + urlPath, parameters,
			_handler);
	}

	public void executeAsynchronousPost(
			String urlPath, Map<String, Object> parameters,
			Handler<Void> handler)
		throws Exception {

		Session session = getSession();

		_httpPost = new HttpPost(urlPath);

		session.asynchronousExecute(_httpPost, parameters, handler);
	}

	public void executeGet(String urlPath) throws Exception {
		Session session = getSession();

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		_httpGet = new HttpGet(syncAccount.getUrl() + urlPath);

		session.execute(_httpGet, _handler);
	}

	public void executePost(String urlPath, Map<String, Object> parameters)
		throws Exception {

		Session session = getSession();

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		_httpPost = new HttpPost(
			syncAccount.getUrl() + "/api/jsonws" + urlPath);

		session.execute(_httpPost, parameters, _handler);
	}

	@Override
	public Map<String, Object> getParameters() {
		return _parameters;
	}

	@Override
	public Object getParameterValue(String key) {
		return _parameters.get(key);
	}

	@Override
	public long getSyncAccountId() {
		return _syncAccountId;
	}

	@Override
	public String getURLPath() {
		return ServerUtil.getURLPath(_syncAccountId, _urlPath);
	}

	@Override
	public boolean isCancelled() {
		return _cancelled;
	}

	@Override
	public void run() {
		if (!SyncEngine.isRunning()) {
			return;
		}

		_handler = getHandler();

		try {
			if (_logger.isTraceEnabled()) {
				logEvent();
			}

			processRequest();
		}
		catch (Exception e) {
			_handler.handleException(e);

			_handler.processFinally();
		}
	}

	protected Session getSession() {
		return SessionManager.getSession(_syncAccountId);
	}

	protected void logEvent() {
		Class<?> clazz = getClass();

		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		if (syncFile != null) {
			_logger.trace(
				"Processing event {} file path {}", clazz.getSimpleName(),
				syncFile.getFilePathName());
		}
		else {
			_logger.trace("Processing event {}", clazz.getSimpleName());
		}
	}

	protected void processAsynchronousRequest() throws Exception {
		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		BatchEvent batchEvent = BatchEventManager.getBatchEvent(syncFile);

		if (!batchEvent.addEvent(this)) {
			executeAsynchronousPost(getURLPath(), _parameters);
		}
	}

	protected void processRequest() throws Exception {
		executePost(getURLPath(), _parameters);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseEvent.class);

	private boolean _cancelled;
	private Handler<Void> _handler;
	private HttpGet _httpGet;
	private HttpPost _httpPost;
	private final Map<String, Object> _parameters;
	private final long _syncAccountId;
	private final String _urlPath;

}