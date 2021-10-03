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

package com.liferay.template.model;

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
public class TemplateEntrySoap implements Serializable {

	public static TemplateEntrySoap toSoapModel(TemplateEntry model) {
		TemplateEntrySoap soapModel = new TemplateEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUuid(model.getUuid());
		soapModel.setTemplateEntryId(model.getTemplateEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDDMTemplateId(model.getDDMTemplateId());
		soapModel.setInfoItemClassName(model.getInfoItemClassName());
		soapModel.setInfoItemFormVariationKey(
			model.getInfoItemFormVariationKey());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static TemplateEntrySoap[] toSoapModels(TemplateEntry[] models) {
		TemplateEntrySoap[] soapModels = new TemplateEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TemplateEntrySoap[][] toSoapModels(TemplateEntry[][] models) {
		TemplateEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TemplateEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TemplateEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TemplateEntrySoap[] toSoapModels(List<TemplateEntry> models) {
		List<TemplateEntrySoap> soapModels = new ArrayList<TemplateEntrySoap>(
			models.size());

		for (TemplateEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TemplateEntrySoap[soapModels.size()]);
	}

	public TemplateEntrySoap() {
	}

	public long getPrimaryKey() {
		return _templateEntryId;
	}

	public void setPrimaryKey(long pk) {
		setTemplateEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getTemplateEntryId() {
		return _templateEntryId;
	}

	public void setTemplateEntryId(long templateEntryId) {
		_templateEntryId = templateEntryId;
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

	public long getDDMTemplateId() {
		return _ddmTemplateId;
	}

	public void setDDMTemplateId(long ddmTemplateId) {
		_ddmTemplateId = ddmTemplateId;
	}

	public String getInfoItemClassName() {
		return _infoItemClassName;
	}

	public void setInfoItemClassName(String infoItemClassName) {
		_infoItemClassName = infoItemClassName;
	}

	public String getInfoItemFormVariationKey() {
		return _infoItemFormVariationKey;
	}

	public void setInfoItemFormVariationKey(String infoItemFormVariationKey) {
		_infoItemFormVariationKey = infoItemFormVariationKey;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _templateEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _ddmTemplateId;
	private String _infoItemClassName;
	private String _infoItemFormVariationKey;
	private Date _lastPublishDate;

}