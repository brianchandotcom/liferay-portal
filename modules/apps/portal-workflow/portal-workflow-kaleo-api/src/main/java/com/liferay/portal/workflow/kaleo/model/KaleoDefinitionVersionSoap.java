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

package com.liferay.portal.workflow.kaleo.model;

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
public class KaleoDefinitionVersionSoap implements Serializable {

	public static KaleoDefinitionVersionSoap toSoapModel(
		KaleoDefinitionVersion model) {

		KaleoDefinitionVersionSoap soapModel = new KaleoDefinitionVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setKaleoDefinitionVersionId(
			model.getKaleoDefinitionVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setKaleoDefinitionId(model.getKaleoDefinitionId());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setContent(model.getContent());
		soapModel.setVersion(model.getVersion());
		soapModel.setStartKaleoNodeId(model.getStartKaleoNodeId());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static KaleoDefinitionVersionSoap[] toSoapModels(
		KaleoDefinitionVersion[] models) {

		KaleoDefinitionVersionSoap[] soapModels =
			new KaleoDefinitionVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static KaleoDefinitionVersionSoap[][] toSoapModels(
		KaleoDefinitionVersion[][] models) {

		KaleoDefinitionVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new KaleoDefinitionVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new KaleoDefinitionVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static KaleoDefinitionVersionSoap[] toSoapModels(
		List<KaleoDefinitionVersion> models) {

		List<KaleoDefinitionVersionSoap> soapModels =
			new ArrayList<KaleoDefinitionVersionSoap>(models.size());

		for (KaleoDefinitionVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new KaleoDefinitionVersionSoap[soapModels.size()]);
	}

	public KaleoDefinitionVersionSoap() {
	}

	public long getPrimaryKey() {
		return _kaleoDefinitionVersionId;
	}

	public void setPrimaryKey(long pk) {
		setKaleoDefinitionVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
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

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
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

	public long getKaleoDefinitionId() {
		return _kaleoDefinitionId;
	}

	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		_kaleoDefinitionId = kaleoDefinitionId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public long getStartKaleoNodeId() {
		return _startKaleoNodeId;
	}

	public void setStartKaleoNodeId(long startKaleoNodeId) {
		_startKaleoNodeId = startKaleoNodeId;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private long _kaleoDefinitionVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private Date _createDate;
	private Date _modifiedDate;
	private long _kaleoDefinitionId;
	private String _name;
	private String _title;
	private String _description;
	private String _content;
	private String _version;
	private long _startKaleoNodeId;
	private int _status;

}