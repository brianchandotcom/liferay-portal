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

package com.liferay.portal.kernel.route.model;

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
public class GroupFriendlyURLSoap implements Serializable {
	public static GroupFriendlyURLSoap toSoapModel(GroupFriendlyURL model) {
		GroupFriendlyURLSoap soapModel = new GroupFriendlyURLSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setGroupFriendlyURLId(model.getGroupFriendlyURLId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static GroupFriendlyURLSoap[] toSoapModels(GroupFriendlyURL[] models) {
		GroupFriendlyURLSoap[] soapModels = new GroupFriendlyURLSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static GroupFriendlyURLSoap[][] toSoapModels(
		GroupFriendlyURL[][] models) {
		GroupFriendlyURLSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new GroupFriendlyURLSoap[models.length][models[0].length];
		}
		else {
			soapModels = new GroupFriendlyURLSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static GroupFriendlyURLSoap[] toSoapModels(
		List<GroupFriendlyURL> models) {
		List<GroupFriendlyURLSoap> soapModels = new ArrayList<GroupFriendlyURLSoap>(models.size());

		for (GroupFriendlyURL model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new GroupFriendlyURLSoap[soapModels.size()]);
	}

	public GroupFriendlyURLSoap() {
	}

	public long getPrimaryKey() {
		return _groupFriendlyURLId;
	}

	public void setPrimaryKey(long pk) {
		setGroupFriendlyURLId(pk);
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

	public long getGroupFriendlyURLId() {
		return _groupFriendlyURLId;
	}

	public void setGroupFriendlyURLId(long groupFriendlyURLId) {
		_groupFriendlyURLId = groupFriendlyURLId;
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

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _groupFriendlyURLId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _friendlyURL;
	private String _languageId;
	private Date _lastPublishDate;
}