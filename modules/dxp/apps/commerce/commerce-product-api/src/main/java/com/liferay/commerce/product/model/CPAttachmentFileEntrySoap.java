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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPAttachmentFileEntryServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPAttachmentFileEntryServiceSoap
 * @generated
 */
@ProviderType
public class CPAttachmentFileEntrySoap implements Serializable {
	public static CPAttachmentFileEntrySoap toSoapModel(
		CPAttachmentFileEntry model) {
		CPAttachmentFileEntrySoap soapModel = new CPAttachmentFileEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPAttachmentFileEntryId(model.getCPAttachmentFileEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setJson(model.getJson());
		soapModel.setPriority(model.getPriority());
		soapModel.setType(model.getType());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPAttachmentFileEntrySoap[] toSoapModels(
		CPAttachmentFileEntry[] models) {
		CPAttachmentFileEntrySoap[] soapModels = new CPAttachmentFileEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPAttachmentFileEntrySoap[][] toSoapModels(
		CPAttachmentFileEntry[][] models) {
		CPAttachmentFileEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPAttachmentFileEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPAttachmentFileEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPAttachmentFileEntrySoap[] toSoapModels(
		List<CPAttachmentFileEntry> models) {
		List<CPAttachmentFileEntrySoap> soapModels = new ArrayList<CPAttachmentFileEntrySoap>(models.size());

		for (CPAttachmentFileEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPAttachmentFileEntrySoap[soapModels.size()]);
	}

	public CPAttachmentFileEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CPAttachmentFileEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCPAttachmentFileEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPAttachmentFileEntryId() {
		return _CPAttachmentFileEntryId;
	}

	public void setCPAttachmentFileEntryId(long CPAttachmentFileEntryId) {
		_CPAttachmentFileEntryId = CPAttachmentFileEntryId;
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

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getJson() {
		return _json;
	}

	public void setJson(String json) {
		_json = json;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPAttachmentFileEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _fileEntryId;
	private Date _displayDate;
	private Date _expirationDate;
	private String _title;
	private String _json;
	private double _priority;
	private int _type;
	private Date _lastPublishDate;
}