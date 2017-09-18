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

package com.liferay.modern.site.building.fragment.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.modern.site.building.fragment.service.http.MSBFragmentEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.modern.site.building.fragment.service.http.MSBFragmentEntryServiceSoap
 * @generated
 */
@ProviderType
public class MSBFragmentEntrySoap implements Serializable {
	public static MSBFragmentEntrySoap toSoapModel(MSBFragmentEntry model) {
		MSBFragmentEntrySoap soapModel = new MSBFragmentEntrySoap();

		soapModel.setMsbFragmentEntryId(model.getMsbFragmentEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setMsbFragmentCollectionId(model.getMsbFragmentCollectionId());
		soapModel.setName(model.getName());
		soapModel.setCss(model.getCss());
		soapModel.setHtml(model.getHtml());
		soapModel.setJs(model.getJs());

		return soapModel;
	}

	public static MSBFragmentEntrySoap[] toSoapModels(MSBFragmentEntry[] models) {
		MSBFragmentEntrySoap[] soapModels = new MSBFragmentEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MSBFragmentEntrySoap[][] toSoapModels(
		MSBFragmentEntry[][] models) {
		MSBFragmentEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MSBFragmentEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new MSBFragmentEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MSBFragmentEntrySoap[] toSoapModels(
		List<MSBFragmentEntry> models) {
		List<MSBFragmentEntrySoap> soapModels = new ArrayList<MSBFragmentEntrySoap>(models.size());

		for (MSBFragmentEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MSBFragmentEntrySoap[soapModels.size()]);
	}

	public MSBFragmentEntrySoap() {
	}

	public long getPrimaryKey() {
		return _msbFragmentEntryId;
	}

	public void setPrimaryKey(long pk) {
		setMsbFragmentEntryId(pk);
	}

	public long getMsbFragmentEntryId() {
		return _msbFragmentEntryId;
	}

	public void setMsbFragmentEntryId(long msbFragmentEntryId) {
		_msbFragmentEntryId = msbFragmentEntryId;
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

	public long getMsbFragmentCollectionId() {
		return _msbFragmentCollectionId;
	}

	public void setMsbFragmentCollectionId(long msbFragmentCollectionId) {
		_msbFragmentCollectionId = msbFragmentCollectionId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
	}

	public String getHtml() {
		return _html;
	}

	public void setHtml(String html) {
		_html = html;
	}

	public String getJs() {
		return _js;
	}

	public void setJs(String js) {
		_js = js;
	}

	private long _msbFragmentEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _msbFragmentCollectionId;
	private String _name;
	private String _css;
	private String _html;
	private String _js;
}