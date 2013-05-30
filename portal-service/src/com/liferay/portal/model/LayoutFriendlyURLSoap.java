/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class LayoutFriendlyURLSoap implements Serializable {
	public static LayoutFriendlyURLSoap toSoapModel(LayoutFriendlyURL model) {
		LayoutFriendlyURLSoap soapModel = new LayoutFriendlyURLSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutFriendlyURLId(model.getLayoutFriendlyURLId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPlid(model.getPlid());
		soapModel.setPrivateLayout(model.getPrivateLayout());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setLanguageId(model.getLanguageId());

		return soapModel;
	}

	public static LayoutFriendlyURLSoap[] toSoapModels(
		LayoutFriendlyURL[] models) {
		LayoutFriendlyURLSoap[] soapModels = new LayoutFriendlyURLSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutFriendlyURLSoap[][] toSoapModels(
		LayoutFriendlyURL[][] models) {
		LayoutFriendlyURLSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutFriendlyURLSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutFriendlyURLSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutFriendlyURLSoap[] toSoapModels(
		List<LayoutFriendlyURL> models) {
		List<LayoutFriendlyURLSoap> soapModels = new ArrayList<LayoutFriendlyURLSoap>(models.size());

		for (LayoutFriendlyURL model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutFriendlyURLSoap[soapModels.size()]);
	}

	public LayoutFriendlyURLSoap() {
	}

	public long getPrimaryKey() {
		return _layoutFriendlyURLId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutFriendlyURLId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLayoutFriendlyURLId() {
		return _layoutFriendlyURLId;
	}

	public void setLayoutFriendlyURLId(long layoutFriendlyURLId) {
		_layoutFriendlyURLId = layoutFriendlyURLId;
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

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
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

	private String _uuid;
	private long _layoutFriendlyURLId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _plid;
	private boolean _privateLayout;
	private String _friendlyURL;
	private String _languageId;
}