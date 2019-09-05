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

package com.liferay.friendly.url.model;

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
public class FriendlyURLEntrySoap implements Serializable {

	public static FriendlyURLEntrySoap toSoapModel(FriendlyURLEntry model) {
		FriendlyURLEntrySoap soapModel = new FriendlyURLEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setDefaultLanguageId(model.getDefaultLanguageId());
		soapModel.setFriendlyURLEntryId(model.getFriendlyURLEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static FriendlyURLEntrySoap[] toSoapModels(
		FriendlyURLEntry[] models) {

		FriendlyURLEntrySoap[] soapModels =
			new FriendlyURLEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntrySoap[][] toSoapModels(
		FriendlyURLEntry[][] models) {

		FriendlyURLEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FriendlyURLEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntrySoap[] toSoapModels(
		List<FriendlyURLEntry> models) {

		List<FriendlyURLEntrySoap> soapModels =
			new ArrayList<FriendlyURLEntrySoap>(models.size());

		for (FriendlyURLEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FriendlyURLEntrySoap[soapModels.size()]);
	}

	public FriendlyURLEntrySoap() {
	}

	public long getPrimaryKey() {
		return _friendlyURLEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFriendlyURLEntryId(pk);
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

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryId = friendlyURLEntryId;
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

	private long _mvccVersion;
	private String _uuid;
	private String _defaultLanguageId;
	private long _friendlyURLEntryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;

}