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

package com.liferay.layout.page.template.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.page.template.service.http.LayoutPageTemplateEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutPageTemplateEntrySoap implements Serializable {

	public static LayoutPageTemplateEntrySoap toSoapModel(
		LayoutPageTemplateEntry model) {

		LayoutPageTemplateEntrySoap soapModel =
			new LayoutPageTemplateEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutPageTemplateEntryId(
			model.getLayoutPageTemplateEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutPageTemplateCollectionId(
			model.getLayoutPageTemplateCollectionId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassTypeId(model.getClassTypeId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setPreviewFileEntryId(model.getPreviewFileEntryId());
		soapModel.setDefaultTemplate(model.isDefaultTemplate());
		soapModel.setLayoutPrototypeId(model.getLayoutPrototypeId());
		soapModel.setPlid(model.getPlid());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static LayoutPageTemplateEntrySoap[] toSoapModels(
		LayoutPageTemplateEntry[] models) {

		LayoutPageTemplateEntrySoap[] soapModels =
			new LayoutPageTemplateEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateEntrySoap[][] toSoapModels(
		LayoutPageTemplateEntry[][] models) {

		LayoutPageTemplateEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LayoutPageTemplateEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateEntrySoap[] toSoapModels(
		List<LayoutPageTemplateEntry> models) {

		List<LayoutPageTemplateEntrySoap> soapModels =
			new ArrayList<LayoutPageTemplateEntrySoap>(models.size());

		for (LayoutPageTemplateEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new LayoutPageTemplateEntrySoap[soapModels.size()]);
	}

	public LayoutPageTemplateEntrySoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateEntryId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateEntryId(pk);
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

	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntryId;
	}

	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
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

	public long getLayoutPageTemplateCollectionId() {
		return _layoutPageTemplateCollectionId;
	}

	public void setLayoutPageTemplateCollectionId(
		long layoutPageTemplateCollectionId) {

		_layoutPageTemplateCollectionId = layoutPageTemplateCollectionId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassTypeId() {
		return _classTypeId;
	}

	public void setClassTypeId(long classTypeId) {
		_classTypeId = classTypeId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public long getPreviewFileEntryId() {
		return _previewFileEntryId;
	}

	public void setPreviewFileEntryId(long previewFileEntryId) {
		_previewFileEntryId = previewFileEntryId;
	}

	public boolean getDefaultTemplate() {
		return _defaultTemplate;
	}

	public boolean isDefaultTemplate() {
		return _defaultTemplate;
	}

	public void setDefaultTemplate(boolean defaultTemplate) {
		_defaultTemplate = defaultTemplate;
	}

	public long getLayoutPrototypeId() {
		return _layoutPrototypeId;
	}

	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPrototypeId = layoutPrototypeId;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
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
	private String _uuid;
	private long _layoutPageTemplateEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutPageTemplateCollectionId;
	private long _classNameId;
	private long _classTypeId;
	private String _name;
	private int _type;
	private long _previewFileEntryId;
	private boolean _defaultTemplate;
	private long _layoutPrototypeId;
	private long _plid;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}