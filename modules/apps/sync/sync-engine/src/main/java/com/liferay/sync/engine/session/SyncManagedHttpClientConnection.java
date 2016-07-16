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

package com.liferay.sync.engine.session;

import com.liferay.sync.engine.document.library.event.constants.EventURLPaths;
import com.liferay.sync.engine.document.library.util.ServerUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.session.rate.limiter.RateLimitedOutputStream;
import com.liferay.sync.engine.util.StreamUtil;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.RequestLine;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.util.Args;

/**
 * @author Jonathan McCann
 */
public class SyncManagedHttpClientConnection
	extends DefaultManagedHttpClientConnection {

	public SyncManagedHttpClientConnection(String id, int bufferSize) {
		super(id, bufferSize);
	}

	@Override
	public void sendRequestEntity(HttpEntityEnclosingRequest request)
		throws HttpException, IOException {

		Args.notNull(request, "HTTP request");

		ensureOpen();

		HttpEntity entity = request.getEntity();

		if (entity == null) {
			return;
		}

		OutputStream outputStream = null;

		try {
			outputStream = getOutputStream(request);

			entity.writeTo(outputStream);
		}
		finally {
			StreamUtil.cleanUp(outputStream);
		}
	}

	protected OutputStream getOutputStream(HttpEntityEnclosingRequest request)
		throws HttpException {

		OutputStream outputStream = prepareOutput(request);

		Header syncUUIDheader = request.getFirstHeader("Sync-UUID");

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			syncUUIDheader.getValue());

		if (syncAccount == null) {
			return outputStream;
		}

		RequestLine requestLine = request.getRequestLine();

		String uri = requestLine.getUri();

		if (uri.endsWith(
				ServerUtil.getURLPath(
					syncAccount.getSyncAccountId(),
					EventURLPaths.ADD_FILE_ENTRY)) ||
			uri.endsWith(
				ServerUtil.getURLPath(
					syncAccount.getSyncAccountId(),
					EventURLPaths.UPDATE_FILE_ENTRIES)) ||
			uri.endsWith(
				ServerUtil.getURLPath(
					syncAccount.getSyncAccountId(),
					EventURLPaths.UPDATE_FILE_ENTRY))) {

			return new RateLimitedOutputStream(
				outputStream, syncAccount.getSyncAccountId());
		}

		return outputStream;
	}

}