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

import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentSoap implements Serializable {
	public static LayoutPageTemplateFragmentSoap toSoapModel(
		LayoutPageTemplateFragment model) {
		LayoutPageTemplateFragmentSoap soapModel = new LayoutPageTemplateFragmentSoap();

		soapModel.setGroupId(model.getGroupId());
		soapModel.setLayoutPageTemplateId(model.getLayoutPageTemplateId());
		soapModel.setFragmentEntryId(model.getFragmentEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPosition(model.getPosition());

		return soapModel;
	}

	public static LayoutPageTemplateFragmentSoap[] toSoapModels(
		LayoutPageTemplateFragment[] models) {
		LayoutPageTemplateFragmentSoap[] soapModels = new LayoutPageTemplateFragmentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateFragmentSoap[][] toSoapModels(
		LayoutPageTemplateFragment[][] models) {
		LayoutPageTemplateFragmentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutPageTemplateFragmentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateFragmentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateFragmentSoap[] toSoapModels(
		List<LayoutPageTemplateFragment> models) {
		List<LayoutPageTemplateFragmentSoap> soapModels = new ArrayList<LayoutPageTemplateFragmentSoap>(models.size());

		for (LayoutPageTemplateFragment model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutPageTemplateFragmentSoap[soapModels.size()]);
	}

	public LayoutPageTemplateFragmentSoap() {
	}

	public LayoutPageTemplateFragmentPK getPrimaryKey() {
		return new LayoutPageTemplateFragmentPK(_groupId,
			_layoutPageTemplateId, _fragmentEntryId);
	}

	public void setPrimaryKey(LayoutPageTemplateFragmentPK pk) {
		setGroupId(pk.groupId);
		setLayoutPageTemplateId(pk.layoutPageTemplateId);
		setFragmentEntryId(pk.fragmentEntryId);
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getLayoutPageTemplateId() {
		return _layoutPageTemplateId;
	}

	public void setLayoutPageTemplateId(long layoutPageTemplateId) {
		_layoutPageTemplateId = layoutPageTemplateId;
	}

	public long getFragmentEntryId() {
		return _fragmentEntryId;
	}

	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryId = fragmentEntryId;
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

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	private long _groupId;
	private long _layoutPageTemplateId;
	private long _fragmentEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private int _position;
}