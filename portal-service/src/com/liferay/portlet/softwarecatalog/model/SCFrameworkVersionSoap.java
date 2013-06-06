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

package com.liferay.portlet.softwarecatalog.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.softwarecatalog.service.http.SCFrameworkVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.http.SCFrameworkVersionServiceSoap
 * @generated
 */
public class SCFrameworkVersionSoap implements Serializable {
	public static SCFrameworkVersionSoap toSoapModel(SCFrameworkVersion model) {
		SCFrameworkVersionSoap soapModel = new SCFrameworkVersionSoap();

		soapModel.setFrameworkVersionId(model.getFrameworkVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setUrl(model.getUrl());
		soapModel.setActive(model.getActive());
		soapModel.setPriority(model.getPriority());

		return soapModel;
	}

	public static SCFrameworkVersionSoap[] toSoapModels(
		SCFrameworkVersion[] models) {
		SCFrameworkVersionSoap[] soapModels = new SCFrameworkVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SCFrameworkVersionSoap[][] toSoapModels(
		SCFrameworkVersion[][] models) {
		SCFrameworkVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SCFrameworkVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SCFrameworkVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SCFrameworkVersionSoap[] toSoapModels(
		List<SCFrameworkVersion> models) {
		List<SCFrameworkVersionSoap> soapModels = new ArrayList<SCFrameworkVersionSoap>(models.size());

		for (SCFrameworkVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SCFrameworkVersionSoap[soapModels.size()]);
	}

	public SCFrameworkVersionSoap() {
	}

	public long getPrimaryKey() {
		return _frameworkVersionId;
	}

	public void setPrimaryKey(long pk) {
		setFrameworkVersionId(pk);
	}

	public long getFrameworkVersionId() {
		return _frameworkVersionId;
	}

	public void setFrameworkVersionId(long frameworkVersionId) {
		_frameworkVersionId = frameworkVersionId;
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

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	private long _frameworkVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _url;
	private boolean _active;
	private int _priority;
}