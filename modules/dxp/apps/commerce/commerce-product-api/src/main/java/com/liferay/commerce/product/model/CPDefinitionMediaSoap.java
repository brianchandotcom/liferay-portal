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
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.service.http.CPDefinitionMediaServiceSoap}.
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.http.CPDefinitionMediaServiceSoap
 * @generated
 */
@ProviderType
public class CPDefinitionMediaSoap implements Serializable {
	public static CPDefinitionMediaSoap toSoapModel(CPDefinitionMedia model) {
		CPDefinitionMediaSoap soapModel = new CPDefinitionMediaSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionMediaId(model.getCPDefinitionMediaId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setDDMContent(model.getDDMContent());
		soapModel.setPriority(model.getPriority());
		soapModel.setCPMediaTypeId(model.getCPMediaTypeId());

		return soapModel;
	}

	public static CPDefinitionMediaSoap[] toSoapModels(
		CPDefinitionMedia[] models) {
		CPDefinitionMediaSoap[] soapModels = new CPDefinitionMediaSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionMediaSoap[][] toSoapModels(
		CPDefinitionMedia[][] models) {
		CPDefinitionMediaSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionMediaSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionMediaSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionMediaSoap[] toSoapModels(
		List<CPDefinitionMedia> models) {
		List<CPDefinitionMediaSoap> soapModels = new ArrayList<CPDefinitionMediaSoap>(models.size());

		for (CPDefinitionMedia model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionMediaSoap[soapModels.size()]);
	}

	public CPDefinitionMediaSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionMediaId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionMediaId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionMediaId() {
		return _CPDefinitionMediaId;
	}

	public void setCPDefinitionMediaId(long CPDefinitionMediaId) {
		_CPDefinitionMediaId = CPDefinitionMediaId;
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

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public String getDDMContent() {
		return _DDMContent;
	}

	public void setDDMContent(String DDMContent) {
		_DDMContent = DDMContent;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public long getCPMediaTypeId() {
		return _CPMediaTypeId;
	}

	public void setCPMediaTypeId(long CPMediaTypeId) {
		_CPMediaTypeId = CPMediaTypeId;
	}

	private String _uuid;
	private long _CPDefinitionMediaId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _fileEntryId;
	private String _DDMContent;
	private int _priority;
	private long _CPMediaTypeId;
}