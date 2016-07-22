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

package com.liferay.sync.engine.document.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.sync.engine.model.SyncFile;

import java.util.List;
import java.util.Map;

/**
 * @author Michael Young
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncDLObjectUpdate {

	public static final String PREFERENCE_KEY_SYNC_CONTEXT_MODIFIED_TIME =
		"sync.context.modified.time";

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public int getResultsTotal() {
		return resultsTotal;
	}

	public Map<String, Long> getSettingsModifiedTimes() {
		return settingsModifiedTimes;
	}

	public List<SyncFile> getSyncFiles() {
		return syncFiles;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public void setResultsTotal(int resultsTotal) {
		this.resultsTotal = resultsTotal;
	}

	public void setSyncFiles(List<SyncFile> syncFiles) {
		this.syncFiles = syncFiles;
	}

	protected long lastAccessTime;
	protected int resultsTotal;
	protected Map<String, Long> settingsModifiedTimes;

	@JsonProperty("syncDLObjects")
	protected List<SyncFile> syncFiles;

}