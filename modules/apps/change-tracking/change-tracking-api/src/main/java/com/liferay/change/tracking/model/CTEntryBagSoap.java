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

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class CTEntryBagSoap implements Serializable {
	public static CTEntryBagSoap toSoapModel(CTEntryBag model) {
		CTEntryBagSoap soapModel = new CTEntryBagSoap();

		soapModel.setCtEntryBagId(model.getCtEntryBagId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOwnerCTEntryId(model.getOwnerCTEntryId());
		soapModel.setCtCollectionId(model.getCtCollectionId());

		return soapModel;
	}

	public static CTEntryBagSoap[] toSoapModels(CTEntryBag[] models) {
		CTEntryBagSoap[] soapModels = new CTEntryBagSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CTEntryBagSoap[][] toSoapModels(CTEntryBag[][] models) {
		CTEntryBagSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CTEntryBagSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CTEntryBagSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CTEntryBagSoap[] toSoapModels(List<CTEntryBag> models) {
		List<CTEntryBagSoap> soapModels = new ArrayList<CTEntryBagSoap>(models.size());

		for (CTEntryBag model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CTEntryBagSoap[soapModels.size()]);
	}

	public CTEntryBagSoap() {
	}

	public long getPrimaryKey() {
		return _ctEntryBagId;
	}

	public void setPrimaryKey(long pk) {
		setCtEntryBagId(pk);
	}

	public long getCtEntryBagId() {
		return _ctEntryBagId;
	}

	public void setCtEntryBagId(long ctEntryBagId) {
		_ctEntryBagId = ctEntryBagId;
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

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
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

	public long getOwnerCTEntryId() {
		return _ownerCTEntryId;
	}

	public void setOwnerCTEntryId(long ownerCTEntryId) {
		_ownerCTEntryId = ownerCTEntryId;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	private long _ctEntryBagId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _ownerCTEntryId;
	private long _ctCollectionId;
}