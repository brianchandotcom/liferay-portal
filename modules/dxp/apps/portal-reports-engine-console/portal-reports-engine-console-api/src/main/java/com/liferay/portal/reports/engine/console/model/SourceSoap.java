/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.reports.engine.console.service.http.SourceServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SourceSoap implements Serializable {

	public static SourceSoap toSoapModel(Source model) {
		SourceSoap soapModel = new SourceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setSourceId(model.getSourceId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setName(model.getName());
		soapModel.setDriverClassName(model.getDriverClassName());
		soapModel.setDriverUrl(model.getDriverUrl());
		soapModel.setDriverUserName(model.getDriverUserName());
		soapModel.setDriverPassword(model.getDriverPassword());

		return soapModel;
	}

	public static SourceSoap[] toSoapModels(Source[] models) {
		SourceSoap[] soapModels = new SourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SourceSoap[][] toSoapModels(Source[][] models) {
		SourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SourceSoap[] toSoapModels(List<Source> models) {
		List<SourceSoap> soapModels = new ArrayList<SourceSoap>(models.size());

		for (Source model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SourceSoap[soapModels.size()]);
	}

	public SourceSoap() {
	}

	public long getPrimaryKey() {
		return _sourceId;
	}

	public void setPrimaryKey(long pk) {
		setSourceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getSourceId() {
		return _sourceId;
	}

	public void setSourceId(long sourceId) {
		_sourceId = sourceId;
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

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDriverClassName() {
		return _driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		_driverClassName = driverClassName;
	}

	public String getDriverUrl() {
		return _driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		_driverUrl = driverUrl;
	}

	public String getDriverUserName() {
		return _driverUserName;
	}

	public void setDriverUserName(String driverUserName) {
		_driverUserName = driverUserName;
	}

	public String getDriverPassword() {
		return _driverPassword;
	}

	public void setDriverPassword(String driverPassword) {
		_driverPassword = driverPassword;
	}

	private String _uuid;
	private long _sourceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private Date _lastPublishDate;
	private String _name;
	private String _driverClassName;
	private String _driverUrl;
	private String _driverUserName;
	private String _driverPassword;

}