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

package com.liferay.content.repository.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.content.repository.service.http.ContentRepositoryEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContentRepositoryEntrySoap implements Serializable {

	public static ContentRepositoryEntrySoap toSoapModel(
		ContentRepositoryEntry model) {

		ContentRepositoryEntrySoap soapModel = new ContentRepositoryEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setContentRepositoryEntryId(
			model.getContentRepositoryEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static ContentRepositoryEntrySoap[] toSoapModels(
		ContentRepositoryEntry[] models) {

		ContentRepositoryEntrySoap[] soapModels =
			new ContentRepositoryEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ContentRepositoryEntrySoap[][] toSoapModels(
		ContentRepositoryEntry[][] models) {

		ContentRepositoryEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ContentRepositoryEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ContentRepositoryEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ContentRepositoryEntrySoap[] toSoapModels(
		List<ContentRepositoryEntry> models) {

		List<ContentRepositoryEntrySoap> soapModels =
			new ArrayList<ContentRepositoryEntrySoap>(models.size());

		for (ContentRepositoryEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ContentRepositoryEntrySoap[soapModels.size()]);
	}

	public ContentRepositoryEntrySoap() {
	}

	public long getPrimaryKey() {
		return _contentRepositoryEntryId;
	}

	public void setPrimaryKey(long pk) {
		setContentRepositoryEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getContentRepositoryEntryId() {
		return _contentRepositoryEntryId;
	}

	public void setContentRepositoryEntryId(long contentRepositoryEntryId) {
		_contentRepositoryEntryId = contentRepositoryEntryId;
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

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _contentRepositoryEntryId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _groupId;

}