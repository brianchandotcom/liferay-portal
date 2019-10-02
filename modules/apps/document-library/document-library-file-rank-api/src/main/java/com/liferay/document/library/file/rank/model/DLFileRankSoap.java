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

package com.liferay.document.library.file.rank.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileRankSoap implements Serializable {

	public static DLFileRankSoap toSoapModel(DLFileRank model) {
		DLFileRankSoap soapModel = new DLFileRankSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setFileRankId(model.getFileRankId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static DLFileRankSoap[] toSoapModels(DLFileRank[] models) {
		DLFileRankSoap[] soapModels = new DLFileRankSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLFileRankSoap[][] toSoapModels(DLFileRank[][] models) {
		DLFileRankSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DLFileRankSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DLFileRankSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLFileRankSoap[] toSoapModels(List<DLFileRank> models) {
		List<DLFileRankSoap> soapModels = new ArrayList<DLFileRankSoap>(
			models.size());

		for (DLFileRank model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DLFileRankSoap[soapModels.size()]);
	}

	public DLFileRankSoap() {
	}

	public long getPrimaryKey() {
		return _fileRankId;
	}

	public void setPrimaryKey(long pk) {
		setFileRankId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getFileRankId() {
		return _fileRankId;
	}

	public void setFileRankId(long fileRankId) {
		_fileRankId = fileRankId;
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

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _mvccVersion;
	private long _fileRankId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private long _fileEntryId;
	private boolean _active;

}