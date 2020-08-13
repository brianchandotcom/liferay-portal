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

package com.liferay.app.builder.model;

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
public class AppBuilderAppSoap implements Serializable {

	public static AppBuilderAppSoap toSoapModel(AppBuilderApp model) {
		AppBuilderAppSoap soapModel = new AppBuilderAppSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setAppBuilderAppId(model.getAppBuilderAppId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setActive(model.isActive());
		soapModel.setDdlRecordSetId(model.getDdlRecordSetId());
		soapModel.setDdmStructureId(model.getDdmStructureId());
		soapModel.setDdmStructureLayoutId(model.getDdmStructureLayoutId());
		soapModel.setDeDataListViewId(model.getDeDataListViewId());
		soapModel.setName(model.getName());
		soapModel.setScope(model.getScope());

		return soapModel;
	}

	public static AppBuilderAppSoap[] toSoapModels(AppBuilderApp[] models) {
		AppBuilderAppSoap[] soapModels = new AppBuilderAppSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppSoap[][] toSoapModels(AppBuilderApp[][] models) {
		AppBuilderAppSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AppBuilderAppSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AppBuilderAppSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppSoap[] toSoapModels(List<AppBuilderApp> models) {
		List<AppBuilderAppSoap> soapModels = new ArrayList<AppBuilderAppSoap>(
			models.size());

		for (AppBuilderApp model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AppBuilderAppSoap[soapModels.size()]);
	}

	public AppBuilderAppSoap() {
	}

	public long getPrimaryKey() {
		return _appBuilderAppId;
	}

	public void setPrimaryKey(long pk) {
		setAppBuilderAppId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	public void setAppBuilderAppId(long appBuilderAppId) {
		_appBuilderAppId = appBuilderAppId;
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

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public long getDdlRecordSetId() {
		return _ddlRecordSetId;
	}

	public void setDdlRecordSetId(long ddlRecordSetId) {
		_ddlRecordSetId = ddlRecordSetId;
	}

	public long getDdmStructureId() {
		return _ddmStructureId;
	}

	public void setDdmStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public long getDdmStructureLayoutId() {
		return _ddmStructureLayoutId;
	}

	public void setDdmStructureLayoutId(long ddmStructureLayoutId) {
		_ddmStructureLayoutId = ddmStructureLayoutId;
	}

	public long getDeDataListViewId() {
		return _deDataListViewId;
	}

	public void setDeDataListViewId(long deDataListViewId) {
		_deDataListViewId = deDataListViewId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getScope() {
		return _scope;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	private String _uuid;
	private long _appBuilderAppId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _active;
	private long _ddlRecordSetId;
	private long _ddmStructureId;
	private long _ddmStructureLayoutId;
	private long _deDataListViewId;
	private String _name;
	private String _scope;

}