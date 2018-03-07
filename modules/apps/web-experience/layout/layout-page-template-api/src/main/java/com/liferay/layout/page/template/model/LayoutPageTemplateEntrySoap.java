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
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.page.template.service.http.LayoutPageTemplateEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.layout.page.template.service.http.LayoutPageTemplateEntryServiceSoap
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntrySoap implements Serializable {
	public static LayoutPageTemplateEntrySoap toSoapModel(
		LayoutPageTemplateEntry model) {
		LayoutPageTemplateEntrySoap soapModel = new LayoutPageTemplateEntrySoap();

		soapModel.setLayoutPageTemplateEntryId(model.getLayoutPageTemplateEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutPageTemplateCollectionId(model.getLayoutPageTemplateCollectionId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setName(model.getName());
		soapModel.setHtmlPreviewEntryId(model.getHtmlPreviewEntryId());
		soapModel.setDefaultTemplate(model.getDefaultTemplate());

		return soapModel;
	}

	public static LayoutPageTemplateEntrySoap[] toSoapModels(
		LayoutPageTemplateEntry[] models) {
		LayoutPageTemplateEntrySoap[] soapModels = new LayoutPageTemplateEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateEntrySoap[][] toSoapModels(
		LayoutPageTemplateEntry[][] models) {
		LayoutPageTemplateEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutPageTemplateEntrySoap[models.length][models[0].length];
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
		List<LayoutPageTemplateEntrySoap> soapModels = new ArrayList<LayoutPageTemplateEntrySoap>(models.size());

		for (LayoutPageTemplateEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutPageTemplateEntrySoap[soapModels.size()]);
	}

	public LayoutPageTemplateEntrySoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateEntryId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateEntryId(pk);
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public long getHtmlPreviewEntryId() {
		return _htmlPreviewEntryId;
	}

	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_htmlPreviewEntryId = htmlPreviewEntryId;
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

	private long _layoutPageTemplateEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutPageTemplateCollectionId;
	private long _classNameId;
	private String _name;
	private long _htmlPreviewEntryId;
	private boolean _defaultTemplate;
}