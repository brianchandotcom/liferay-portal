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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BasePersistenceImpl;

import java.util.Arrays;

/**
 * @author Dennis Ju
 */
@DatabaseTable(
	daoClass = BasePersistenceImpl.class, tableName = "SyncLanEndpoint"
)
public class SyncLanEndpoint extends BaseModel {

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof SyncLanEndpoint)) {
			return false;
		}

		SyncLanEndpoint syncLanEndpoint = (SyncLanEndpoint)object;

		if (!lanServerUuid.equals(syncLanEndpoint.lanServerUuid)) {
			return false;
		}
		else if (repositoryId != syncLanEndpoint.repositoryId) {
			return false;
		}
		else if (!syncLanClientUuid.equals(syncLanEndpoint.syncLanClientUuid)) {
			return false;
		}

		return true;
	}

	public String getLanServerUuid() {
		return lanServerUuid;
	}

	public long getRepositoryId() {
		return repositoryId;
	}

	public String getSyncLanClientUuid() {
		return syncLanClientUuid;
	}

	public long getSyncLanEndpointId() {
		return syncLanEndpointId;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(
			new Object[] {lanServerUuid, repositoryId, syncLanClientUuid});
	}

	public void setLanServerUuid(String lanServerUuid) {
		this.lanServerUuid = lanServerUuid;
	}

	public void setRepositoryId(long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public void setSyncLanClientUuid(String syncLanClientUuid) {
		this.syncLanClientUuid = syncLanClientUuid;
	}

	public void setSyncLanEndpointId(long syncLanEndpointId) {
		this.syncLanEndpointId = syncLanEndpointId;
	}

	@DatabaseField(useGetSet = true)
	protected String lanServerUuid;

	@DatabaseField(useGetSet = true)
	protected long repositoryId;

	@DatabaseField(useGetSet = true)
	protected String syncLanClientUuid;

	@DatabaseField(generatedId = true, useGetSet = true)
	protected long syncLanEndpointId;

}