/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class RepositoryEntrySoap implements Serializable {
	public static RepositoryEntrySoap toSoapModel(RepositoryEntry model) {
		RepositoryEntrySoap soapModel = new RepositoryEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRepositoryEntryId(model.getRepositoryEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setRepositoryId(model.getRepositoryId());
		soapModel.setMappedId(model.getMappedId());
		soapModel.setManualCheckInRequired(model.getManualCheckInRequired());

		return soapModel;
	}

	public static RepositoryEntrySoap[] toSoapModels(RepositoryEntry[] models) {
		RepositoryEntrySoap[] soapModels = new RepositoryEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RepositoryEntrySoap[][] toSoapModels(
		RepositoryEntry[][] models) {
		RepositoryEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RepositoryEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new RepositoryEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RepositoryEntrySoap[] toSoapModels(
		List<RepositoryEntry> models) {
		List<RepositoryEntrySoap> soapModels = new ArrayList<RepositoryEntrySoap>(models.size());

		for (RepositoryEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RepositoryEntrySoap[soapModels.size()]);
	}

	public RepositoryEntrySoap() {
	}

	public long getPrimaryKey() {
		return _repositoryEntryId;
	}

	public void setPrimaryKey(long pk) {
		setRepositoryEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRepositoryEntryId() {
		return _repositoryEntryId;
	}

	public void setRepositoryEntryId(long repositoryEntryId) {
		_repositoryEntryId = repositoryEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	public void setRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
	}

	public String getMappedId() {
		return _mappedId;
	}

	public void setMappedId(String mappedId) {
		_mappedId = mappedId;
	}

	public boolean getManualCheckInRequired() {
		return _manualCheckInRequired;
	}

	public boolean isManualCheckInRequired() {
		return _manualCheckInRequired;
	}

	public void setManualCheckInRequired(boolean manualCheckInRequired) {
		_manualCheckInRequired = manualCheckInRequired;
	}

	private String _uuid;
	private long _repositoryEntryId;
	private long _groupId;
	private long _repositoryId;
	private String _mappedId;
	private boolean _manualCheckInRequired;
}