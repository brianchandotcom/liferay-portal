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

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.page.template.service.http.LayoutPageTemplateFolderServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.http.LayoutPageTemplateFolderServiceSoap
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderSoap implements Serializable {
	public static LayoutPageTemplateFolderSoap toSoapModel(
		LayoutPageTemplateFolder model) {
		LayoutPageTemplateFolderSoap soapModel = new LayoutPageTemplateFolderSoap();

		soapModel.setLayoutPageTemplateFolderId(model.getLayoutPageTemplateFolderId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static LayoutPageTemplateFolderSoap[] toSoapModels(
		LayoutPageTemplateFolder[] models) {
		LayoutPageTemplateFolderSoap[] soapModels = new LayoutPageTemplateFolderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateFolderSoap[][] toSoapModels(
		LayoutPageTemplateFolder[][] models) {
		LayoutPageTemplateFolderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutPageTemplateFolderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateFolderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateFolderSoap[] toSoapModels(
		List<LayoutPageTemplateFolder> models) {
		List<LayoutPageTemplateFolderSoap> soapModels = new ArrayList<LayoutPageTemplateFolderSoap>(models.size());

		for (LayoutPageTemplateFolder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutPageTemplateFolderSoap[soapModels.size()]);
	}

	public LayoutPageTemplateFolderSoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateFolderId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateFolderId(pk);
	}

	public long getLayoutPageTemplateFolderId() {
		return _layoutPageTemplateFolderId;
	}

	public void setLayoutPageTemplateFolderId(long layoutPageTemplateFolderId) {
		_layoutPageTemplateFolderId = layoutPageTemplateFolderId;
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

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private long _layoutPageTemplateFolderId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
}