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
public class FriendlyURLSoap implements Serializable {
	public static FriendlyURLSoap toSoapModel(FriendlyURL model) {
		FriendlyURLSoap soapModel = new FriendlyURLSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setFriendlyUrlId(model.getFriendlyUrlId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setUrlTitle(model.getUrlTitle());
		soapModel.setMain(model.getMain());

		return soapModel;
	}

	public static FriendlyURLSoap[] toSoapModels(FriendlyURL[] models) {
		FriendlyURLSoap[] soapModels = new FriendlyURLSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLSoap[][] toSoapModels(FriendlyURL[][] models) {
		FriendlyURLSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FriendlyURLSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLSoap[] toSoapModels(List<FriendlyURL> models) {
		List<FriendlyURLSoap> soapModels = new ArrayList<FriendlyURLSoap>(models.size());

		for (FriendlyURL model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FriendlyURLSoap[soapModels.size()]);
	}

	public FriendlyURLSoap() {
	}

	public long getPrimaryKey() {
		return _friendlyUrlId;
	}

	public void setPrimaryKey(long pk) {
		setFriendlyUrlId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFriendlyUrlId() {
		return _friendlyUrlId;
	}

	public void setFriendlyUrlId(long friendlyUrlId) {
		_friendlyUrlId = friendlyUrlId;
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

	public String getUrlTitle() {
		return _urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		_urlTitle = urlTitle;
	}

	public boolean getMain() {
		return _main;
	}

	public boolean isMain() {
		return _main;
	}

	public void setMain(boolean main) {
		_main = main;
	}

	private String _uuid;
	private long _friendlyUrlId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _urlTitle;
	private boolean _main;
}