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

package com.liferay.change.tracking.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CTCommentSoap implements Serializable {

	public static CTCommentSoap toSoapModel(CTComment model) {
		CTCommentSoap soapModel = new CTCommentSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCommentId(model.getCtCommentId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setCtEntryId(model.getCtEntryId());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static CTCommentSoap[] toSoapModels(CTComment[] models) {
		CTCommentSoap[] soapModels = new CTCommentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTCommentSoap[][] toSoapModels(CTComment[][] models) {
		CTCommentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTCommentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTCommentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTCommentSoap[] toSoapModels(List<CTComment> models) {
		List<CTCommentSoap> soapModels = new ArrayList<CTCommentSoap>(
			models.size());

		for (CTComment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTCommentSoap[soapModels.size()]);
	}

	public CTCommentSoap() {
	}

	public long getPrimaryKey() {
		return _ctCommentId;
	}

	public void setPrimaryKey(long pk) {
		setCtCommentId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCommentId() {
		return _ctCommentId;
	}

	public void setCtCommentId(long ctCommentId) {
		_ctCommentId = ctCommentId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getCtEntryId() {
		return _ctEntryId;
	}

	public void setCtEntryId(long ctEntryId) {
		_ctEntryId = ctEntryId;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _mvccVersion;
	private long _ctCommentId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _ctCollectionId;
	private long _ctEntryId;
	private String _value;

}