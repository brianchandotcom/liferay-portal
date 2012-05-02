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

package com.liferay.portlet.trash.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.trash.service.http.TrashEntryServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.trash.service.http.TrashEntryServiceSoap
 * @generated
 */
public class TrashEntrySoap implements Serializable {
	public static TrashEntrySoap toSoapModel(TrashEntry model) {
		TrashEntrySoap soapModel = new TrashEntrySoap();

		soapModel.setEntryId(model.getEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setStatus(model.getStatus());
		soapModel.setDeletedByUserId(model.getDeletedByUserId());
		soapModel.setDeletedByUserName(model.getDeletedByUserName());

		return soapModel;
	}

	public static TrashEntrySoap[] toSoapModels(TrashEntry[] models) {
		TrashEntrySoap[] soapModels = new TrashEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[][] toSoapModels(TrashEntry[][] models) {
		TrashEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TrashEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TrashEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[] toSoapModels(List<TrashEntry> models) {
		List<TrashEntrySoap> soapModels = new ArrayList<TrashEntrySoap>(models.size());

		for (TrashEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TrashEntrySoap[soapModels.size()]);
	}

	public TrashEntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getDeletedByUserId() {
		return _deletedByUserId;
	}

	public void setDeletedByUserId(long deletedByUserId) {
		_deletedByUserId = deletedByUserId;
	}

	public String getDeletedByUserName() {
		return _deletedByUserName;
	}

	public void setDeletedByUserName(String deletedByUserName) {
		_deletedByUserName = deletedByUserName;
	}

	private long _entryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private long _classNameId;
	private long _classPK;
	private String _typeSettings;
	private int _status;
	private long _deletedByUserId;
	private String _deletedByUserName;
}