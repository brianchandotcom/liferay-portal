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

package com.liferay.document.library.sync.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLSyncEventSoap implements Serializable {

	public static DLSyncEventSoap toSoapModel(DLSyncEvent model) {
		DLSyncEventSoap soapModel = new DLSyncEventSoap();

		soapModel.setSyncEventId(model.getSyncEventId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setEvent(model.getEvent());
		soapModel.setType(model.getType());
		soapModel.setTypePK(model.getTypePK());

		return soapModel;
	}

	public static DLSyncEventSoap[] toSoapModels(DLSyncEvent[] models) {
		DLSyncEventSoap[] soapModels = new DLSyncEventSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLSyncEventSoap[][] toSoapModels(DLSyncEvent[][] models) {
		DLSyncEventSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLSyncEventSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLSyncEventSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLSyncEventSoap[] toSoapModels(List<DLSyncEvent> models) {
		List<DLSyncEventSoap> soapModels = new ArrayList<DLSyncEventSoap>(
			models.size());

		for (DLSyncEvent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLSyncEventSoap[soapModels.size()]);
	}

	public DLSyncEventSoap() {
	}

	public long getPrimaryKey() {
		return _syncEventId;
	}

	public void setPrimaryKey(long pk) {
		setSyncEventId(pk);
	}

	public long getSyncEventId() {
		return _syncEventId;
	}

	public void setSyncEventId(long syncEventId) {
		_syncEventId = syncEventId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getModifiedTime() {
		return _modifiedTime;
	}

	public void setModifiedTime(long modifiedTime) {
		_modifiedTime = modifiedTime;
	}

	public String getEvent() {
		return _event;
	}

	public void setEvent(String event) {
		_event = event;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public long getTypePK() {
		return _typePK;
	}

	public void setTypePK(long typePK) {
		_typePK = typePK;
	}

	private long _syncEventId;
	private long _companyId;
	private long _modifiedTime;
	private String _event;
	private String _type;
	private long _typePK;

}