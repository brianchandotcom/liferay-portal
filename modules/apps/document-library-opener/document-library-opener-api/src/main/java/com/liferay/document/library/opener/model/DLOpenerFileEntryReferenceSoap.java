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

package com.liferay.document.library.opener.model;

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
public class DLOpenerFileEntryReferenceSoap implements Serializable {

	public static DLOpenerFileEntryReferenceSoap toSoapModel(
		DLOpenerFileEntryReference model) {

		DLOpenerFileEntryReferenceSoap soapModel =
			new DLOpenerFileEntryReferenceSoap();

		soapModel.setDlOpenerFileEntryReferenceId(
			model.getDlOpenerFileEntryReferenceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setReferenceKey(model.getReferenceKey());
		soapModel.setReferenceType(model.getReferenceType());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static DLOpenerFileEntryReferenceSoap[] toSoapModels(
		DLOpenerFileEntryReference[] models) {

		DLOpenerFileEntryReferenceSoap[] soapModels =
			new DLOpenerFileEntryReferenceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DLOpenerFileEntryReferenceSoap[][] toSoapModels(
		DLOpenerFileEntryReference[][] models) {

		DLOpenerFileEntryReferenceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DLOpenerFileEntryReferenceSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new DLOpenerFileEntryReferenceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DLOpenerFileEntryReferenceSoap[] toSoapModels(
		List<DLOpenerFileEntryReference> models) {

		List<DLOpenerFileEntryReferenceSoap> soapModels =
			new ArrayList<DLOpenerFileEntryReferenceSoap>(models.size());

		for (DLOpenerFileEntryReference model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DLOpenerFileEntryReferenceSoap[soapModels.size()]);
	}

	public DLOpenerFileEntryReferenceSoap() {
	}

	public long getPrimaryKey() {
		return _dlOpenerFileEntryReferenceId;
	}

	public void setPrimaryKey(long pk) {
		setDlOpenerFileEntryReferenceId(pk);
	}

	public long getDlOpenerFileEntryReferenceId() {
		return _dlOpenerFileEntryReferenceId;
	}

	public void setDlOpenerFileEntryReferenceId(
		long dlOpenerFileEntryReferenceId) {

		_dlOpenerFileEntryReferenceId = dlOpenerFileEntryReferenceId;
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

	public String getReferenceKey() {
		return _referenceKey;
	}

	public void setReferenceKey(String referenceKey) {
		_referenceKey = referenceKey;
	}

	public String getReferenceType() {
		return _referenceType;
	}

	public void setReferenceType(String referenceType) {
		_referenceType = referenceType;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _dlOpenerFileEntryReferenceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _referenceKey;
	private String _referenceType;
	private long _fileEntryId;
	private int _type;

}