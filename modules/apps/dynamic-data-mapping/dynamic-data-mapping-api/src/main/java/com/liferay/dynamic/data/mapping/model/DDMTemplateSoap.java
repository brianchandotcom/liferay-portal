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
 * This class is used by SOAP remote services, specifically {@link com.liferay.dynamic.data.mapping.service.http.DDMTemplateServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateSoap implements Serializable {

	public static DDMTemplateSoap toSoapModel(DDMTemplate model) {
		DDMTemplateSoap soapModel = new DDMTemplateSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setVersionUserId(model.getVersionUserId());
		soapModel.setVersionUserName(model.getVersionUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setResourceClassNameId(model.getResourceClassNameId());
		soapModel.setTemplateKey(model.getTemplateKey());
		soapModel.setVersion(model.getVersion());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());
		soapModel.setMode(model.getMode());
		soapModel.setLanguage(model.getLanguage());
		soapModel.setScript(model.getScript());
		soapModel.setCacheable(model.isCacheable());
		soapModel.setSmallImage(model.isSmallImage());
		soapModel.setSmallImageId(model.getSmallImageId());
		soapModel.setSmallImageURL(model.getSmallImageURL());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static DDMTemplateSoap[] toSoapModels(DDMTemplate[] models) {
		DDMTemplateSoap[] soapModels = new DDMTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateSoap[][] toSoapModels(DDMTemplate[][] models) {
		DDMTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new DDMTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new DDMTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static DDMTemplateSoap[] toSoapModels(List<DDMTemplate> models) {
		List<DDMTemplateSoap> soapModels = new ArrayList<DDMTemplateSoap>(
			models.size());

		for (DDMTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new DDMTemplateSoap[soapModels.size()]);
	}

	public DDMTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _templateId;
	}

	public void setPrimaryKey(long pk) {
		setTemplateId(pk);
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

	public long getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(long templateId) {
		_templateId = templateId;
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

	public long getVersionUserId() {
		return _versionUserId;
	}

	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	public String getVersionUserName() {
		return _versionUserName;
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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

	public long getResourceClassNameId() {
		return _resourceClassNameId;
	}

	public void setResourceClassNameId(long resourceClassNameId) {
		_resourceClassNameId = resourceClassNameId;
	}

	public String getTemplateKey() {
		return _templateKey;
	}

	public void setTemplateKey(String templateKey) {
		_templateKey = templateKey;
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

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getMode() {
		return _mode;
	}

	public void setMode(String mode) {
		_mode = mode;
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

	public boolean getCacheable() {
		return _cacheable;
	}

	public boolean isCacheable() {
		return _cacheable;
	}

	public void setCacheable(boolean cacheable) {
		_cacheable = cacheable;
	}

	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		_smallImage = smallImage;
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		_smallImageId = smallImageId;
	}

	public String getSmallImageURL() {
		return _smallImageURL;
	}

	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _templateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _resourceClassNameId;
	private String _templateKey;
	private String _version;
	private String _name;
	private String _description;
	private String _type;
	private String _mode;
	private String _language;
	private String _script;
	private boolean _cacheable;
	private boolean _smallImage;
	private long _smallImageId;
	private String _smallImageURL;
	private Date _lastPublishDate;

}