/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model;

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
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTypeId(model.getTypeId());
		soapModel.setType(model.getType());
		soapModel.setEvent(model.getEvent());

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
		List<DLSyncEventSoap> soapModels = new ArrayList<DLSyncEventSoap>(models.size());

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

	public long getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(long modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getTypeId() {
		return _typeId;
	}

	public void setTypeId(long typeId) {
		_typeId = typeId;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getEvent() {
		return _event;
	}

	public void setEvent(String event) {
		_event = event;
	}

	private long _syncEventId;
	private long _modifiedDate;
	private long _typeId;
	private String _type;
	private String _event;
}