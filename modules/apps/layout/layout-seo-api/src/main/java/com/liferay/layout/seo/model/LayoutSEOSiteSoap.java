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

package com.liferay.layout.seo.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.seo.service.http.LayoutSEOSiteServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOSiteSoap implements Serializable {

	public static LayoutSEOSiteSoap toSoapModel(LayoutSEOSite model) {
		LayoutSEOSiteSoap soapModel = new LayoutSEOSiteSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutSEOSiteId(model.getLayoutSEOSiteId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOpenGraphEnabled(model.isOpenGraphEnabled());
		soapModel.setOpenGraphImageFileEntryId(
			model.getOpenGraphImageFileEntryId());

		return soapModel;
	}

	public static LayoutSEOSiteSoap[] toSoapModels(LayoutSEOSite[] models) {
		LayoutSEOSiteSoap[] soapModels = new LayoutSEOSiteSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutSEOSiteSoap[][] toSoapModels(LayoutSEOSite[][] models) {
		LayoutSEOSiteSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutSEOSiteSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutSEOSiteSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutSEOSiteSoap[] toSoapModels(List<LayoutSEOSite> models) {
		List<LayoutSEOSiteSoap> soapModels = new ArrayList<LayoutSEOSiteSoap>(
			models.size());

		for (LayoutSEOSite model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutSEOSiteSoap[soapModels.size()]);
	}

	public LayoutSEOSiteSoap() {
	}

	public long getPrimaryKey() {
		return _layoutSEOSiteId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutSEOSiteId(pk);
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

	public long getLayoutSEOSiteId() {
		return _layoutSEOSiteId;
	}

	public void setLayoutSEOSiteId(long layoutSEOSiteId) {
		_layoutSEOSiteId = layoutSEOSiteId;
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

	public boolean getOpenGraphEnabled() {
		return _openGraphEnabled;
	}

	public boolean isOpenGraphEnabled() {
		return _openGraphEnabled;
	}

	public void setOpenGraphEnabled(boolean openGraphEnabled) {
		_openGraphEnabled = openGraphEnabled;
	}

	public long getOpenGraphImageFileEntryId() {
		return _openGraphImageFileEntryId;
	}

	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId) {
		_openGraphImageFileEntryId = openGraphImageFileEntryId;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _layoutSEOSiteId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _openGraphEnabled;
	private long _openGraphImageFileEntryId;

}