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

package com.liferay.changeset.model;

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
public class ChangesetEntrySoap implements Serializable {

	public static ChangesetEntrySoap toSoapModel(ChangesetEntry model) {
		ChangesetEntrySoap soapModel = new ChangesetEntrySoap();

		soapModel.setChangesetEntryId(model.getChangesetEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setChangesetCollectionId(model.getChangesetCollectionId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static ChangesetEntrySoap[] toSoapModels(ChangesetEntry[] models) {
		ChangesetEntrySoap[] soapModels = new ChangesetEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ChangesetEntrySoap[][] toSoapModels(
		ChangesetEntry[][] models) {

		ChangesetEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ChangesetEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ChangesetEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ChangesetEntrySoap[] toSoapModels(
		List<ChangesetEntry> models) {

		List<ChangesetEntrySoap> soapModels = new ArrayList<ChangesetEntrySoap>(
			models.size());

		for (ChangesetEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ChangesetEntrySoap[soapModels.size()]);
	}

	public ChangesetEntrySoap() {
	}

	public long getPrimaryKey() {
		return _changesetEntryId;
	}

	public void setPrimaryKey(long pk) {
		setChangesetEntryId(pk);
	}

	public long getChangesetEntryId() {
		return _changesetEntryId;
	}

	public void setChangesetEntryId(long changesetEntryId) {
		_changesetEntryId = changesetEntryId;
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

	public long getChangesetCollectionId() {
		return _changesetCollectionId;
	}

	public void setChangesetCollectionId(long changesetCollectionId) {
		_changesetCollectionId = changesetCollectionId;
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

	private long _changesetEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _changesetCollectionId;
	private long _classNameId;
	private long _classPK;

}