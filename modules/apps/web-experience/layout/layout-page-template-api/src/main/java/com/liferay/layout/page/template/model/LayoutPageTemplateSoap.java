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
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.page.template.service.http.LayoutPageTemplateServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.http.LayoutPageTemplateServiceSoap
 * @generated
 */
@ProviderType
public class LayoutPageTemplateSoap implements Serializable {
	public static LayoutPageTemplateSoap toSoapModel(LayoutPageTemplate model) {
		LayoutPageTemplateSoap soapModel = new LayoutPageTemplateSoap();

		soapModel.setLayoutPageTemplateId(model.getLayoutPageTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutPageTemplateFolderId(model.getLayoutPageTemplateFolderId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static LayoutPageTemplateSoap[] toSoapModels(
		LayoutPageTemplate[] models) {
		LayoutPageTemplateSoap[] soapModels = new LayoutPageTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateSoap[][] toSoapModels(
		LayoutPageTemplate[][] models) {
		LayoutPageTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutPageTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateSoap[] toSoapModels(
		List<LayoutPageTemplate> models) {
		List<LayoutPageTemplateSoap> soapModels = new ArrayList<LayoutPageTemplateSoap>(models.size());

		for (LayoutPageTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutPageTemplateSoap[soapModels.size()]);
	}

	public LayoutPageTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateId(pk);
	}

	public long getLayoutPageTemplateId() {
		return _layoutPageTemplateId;
	}

	public void setLayoutPageTemplateId(long layoutPageTemplateId) {
		_layoutPageTemplateId = layoutPageTemplateId;
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

	public long getLayoutPageTemplateFolderId() {
		return _layoutPageTemplateFolderId;
	}

	public void setLayoutPageTemplateFolderId(long layoutPageTemplateFolderId) {
		_layoutPageTemplateFolderId = layoutPageTemplateFolderId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _layoutPageTemplateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutPageTemplateFolderId;
	private String _name;
}