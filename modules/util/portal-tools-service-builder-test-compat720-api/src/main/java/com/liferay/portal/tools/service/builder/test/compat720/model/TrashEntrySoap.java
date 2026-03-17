/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

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
public class TrashEntrySoap implements Serializable {

	public static TrashEntrySoap toSoapModel(TrashEntry model) {
		TrashEntrySoap soapModel = new TrashEntrySoap();

		soapModel.setTrashEntryId(model.getTrashEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static TrashEntrySoap[] toSoapModels(TrashEntry[] models) {
		TrashEntrySoap[] soapModels = new TrashEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[][] toSoapModels(TrashEntry[][] models) {
		TrashEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TrashEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new TrashEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TrashEntrySoap[] toSoapModels(List<TrashEntry> models) {
		List<TrashEntrySoap> soapModels = new ArrayList<TrashEntrySoap>(
			models.size());

		for (TrashEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TrashEntrySoap[soapModels.size()]);
	}

	public TrashEntrySoap() {
	}

	public long getPrimaryKey() {
		return _trashEntryId;
	}

	public void setPrimaryKey(long pk) {
		setTrashEntryId(pk);
	}

	public long getTrashEntryId() {
		return _trashEntryId;
	}

	public void setTrashEntryId(long trashEntryId) {
		_trashEntryId = trashEntryId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _trashEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;

}
// SB-Hash:-49612213