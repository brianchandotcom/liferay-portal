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

package com.liferay.dynamic.data.mapping.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMTemplateVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateVersionSoap implements Serializable {

	public static DDMTemplateVersionSoap toSoapModel(DDMTemplateVersion model) {
		DDMTemplateVersionSoap soapModel = new DDMTemplateVersionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setTemplateVersionId(model.getTemplateVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setVersion(model.getVersion());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setLanguage(model.getLanguage());
		soapModel.setScript(model.getScript());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static DDMTemplateVersionSoap[] toSoapModels(
		DDMTemplateVersion[] models) {

		DDMTemplateVersionSoap[] soapModels =
			new DDMTemplateVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateVersionSoap[][] toSoapModels(
		DDMTemplateVersion[][] models) {

		DDMTemplateVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new DDMTemplateVersionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMTemplateVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateVersionSoap[] toSoapModels(
		List<DDMTemplateVersion> models) {

		List<DDMTemplateVersionSoap> soapModels =
			new ArrayList<DDMTemplateVersionSoap>(models.size());

		for (DDMTemplateVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new DDMTemplateVersionSoap[soapModels.size()]);
	}

	public DDMTemplateVersionSoap() {
	}

	public long getPrimaryKey() {
		return _templateVersionId;
	}

	public void setPrimaryKey(long pk) {
		setTemplateVersionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getTemplateVersionId() {
		return _templateVersionId;
	}

	public void setTemplateVersionId(long templateVersionId) {
		_templateVersionId = templateVersionId;
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

	public long getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(long templateId) {
		_templateId = templateId;
	}

	public String getVersion() {
		return _version;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getLanguage() {
		return _language;
	}

	public void setLanguage(String language) {
		_language = language;
	}

	public String getScript() {
		return _script;
	}

	public void setScript(String script) {
		_script = script;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
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

	private long _mvccVersion;
	private long _templateVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _classNameId;
	private long _classPK;
	private long _templateId;
	private String _version;
	private String _name;
	private String _description;
	private String _language;
	private String _script;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}