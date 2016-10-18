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

package com.liferay.sync.engine.lan.session;

import com.liferay.sync.engine.model.SyncLanClient;

/**
 * @author Dennis Ju
 */
public class SyncLanClientQueryResult {

	public int getConnectionsCount() {
		return _connectionsCount;
	}

	public int getDownloadRate() {
		return _downloadRate;
	}

	public String getEncryptedToken() {
		return _encryptedToken;
	}

	public int getMaxConnections() {
		return _maxConnections;
	}

	public SyncLanClient getSyncLanClient() {
		return _syncLanClient;
	}

	public void setConnectionsCount(int connectionsCount) {
		_connectionsCount = connectionsCount;
	}

	public void setDownloadRate(int downloadRate) {
		_downloadRate = downloadRate;
	}

	public void setEncryptedToken(String encryptedToken) {
		_encryptedToken = encryptedToken;
	}

	public void setMaxConnections(int maxConnections) {
		_maxConnections = maxConnections;
	}

	public void setSyncLanClient(SyncLanClient syncLanClient) {
		_syncLanClient = syncLanClient;
	}

	private int _connectionsCount;
	private int _downloadRate;
	private String _encryptedToken;
	private int _maxConnections;
	private SyncLanClient _syncLanClient;

}