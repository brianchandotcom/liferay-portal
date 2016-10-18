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

package com.liferay.sync.engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BasePersistenceImpl;

import java.util.Map;
import java.util.Set;

/**
 * @author Dennis Ju
 */
@DatabaseTable(
	daoClass = BasePersistenceImpl.class, tableName = "SyncLanClient"
)
@JsonIgnoreProperties(
	ignoreUnknown = true, value = {"hostname", "modifiedTime", "uiEvent"}
)
public class SyncLanClient extends BaseModel {

	public Map<String, Set<Long>> getEndpoints() {
		return endpoints;
	}

	public String getHostname() {
		return hostname;
	}

	public long getModifiedTime() {
		return modifiedTime;
	}

	public int getPort() {
		return port;
	}

	public String getSyncLanClientUuid() {
		return syncLanClientUuid;
	}

	public void setEndpoints(Map<String, Set<Long>> endpoints) {
		this.endpoints = endpoints;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSyncLanClientUuid(String syncLanClientUuid) {
		this.syncLanClientUuid = syncLanClientUuid;
	}

	protected Map<String, Set<Long>> endpoints;

	@DatabaseField(useGetSet = true)
	protected String hostname;

	@DatabaseField(useGetSet = true)
	protected long modifiedTime;

	@DatabaseField(useGetSet = true)
	protected int port;

	@DatabaseField(id = true, useGetSet = true)
	protected String syncLanClientUuid;

}