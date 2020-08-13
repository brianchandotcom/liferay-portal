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
public class AppBuilderAppVersionSoap implements Serializable {

	public static AppBuilderAppVersionSoap toSoapModel(
		AppBuilderAppVersion model) {

		AppBuilderAppVersionSoap soapModel = new AppBuilderAppVersionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setAppBuilderAppVersionId(model.getAppBuilderAppVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAppBuilderAppId(model.getAppBuilderAppId());
		soapModel.setDdlRecordSetId(model.getDdlRecordSetId());
		soapModel.setDdmStructureId(model.getDdmStructureId());
		soapModel.setDdmStructureLayoutId(model.getDdmStructureLayoutId());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static AppBuilderAppVersionSoap[] toSoapModels(
		AppBuilderAppVersion[] models) {

		AppBuilderAppVersionSoap[] soapModels =
			new AppBuilderAppVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppVersionSoap[][] toSoapModels(
		AppBuilderAppVersion[][] models) {

		AppBuilderAppVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AppBuilderAppVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AppBuilderAppVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AppBuilderAppVersionSoap[] toSoapModels(
		List<AppBuilderAppVersion> models) {

		List<AppBuilderAppVersionSoap> soapModels =
			new ArrayList<AppBuilderAppVersionSoap>(models.size());

		for (AppBuilderAppVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AppBuilderAppVersionSoap[soapModels.size()]);
	}

	public AppBuilderAppVersionSoap() {
	}

	public long getPrimaryKey() {
		return _appBuilderAppVersionId;
	}

	public void setPrimaryKey(long pk) {
		setAppBuilderAppVersionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAppBuilderAppVersionId() {
		return _appBuilderAppVersionId;
	}

	public void setAppBuilderAppVersionId(long appBuilderAppVersionId) {
		_appBuilderAppVersionId = appBuilderAppVersionId;
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

	public long getAppBuilderAppId() {
		return _appBuilderAppId;
	}

	public void setAppBuilderAppId(long appBuilderAppId) {
		_appBuilderAppId = appBuilderAppId;
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

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	private String _uuid;
	private long _appBuilderAppVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _appBuilderAppId;
	private long _ddlRecordSetId;
	private long _ddmStructureId;
	private long _ddmStructureLayoutId;
	private String _version;

}