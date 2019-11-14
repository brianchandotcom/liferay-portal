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
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.seo.service.http.SiteSEOEntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteSEOEntrySoap implements Serializable {

	public static SiteSEOEntrySoap toSoapModel(SiteSEOEntry model) {
		SiteSEOEntrySoap soapModel = new SiteSEOEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSiteSEOEntryId(model.getSiteSEOEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOpenGraphImageFileEntryId(
			model.getOpenGraphImageFileEntryId());
		soapModel.setOpenGraphSiteEnabled(model.isOpenGraphSiteEnabled());

		return soapModel;
	}

	public static SiteSEOEntrySoap[] toSoapModels(SiteSEOEntry[] models) {
		SiteSEOEntrySoap[] soapModels = new SiteSEOEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SiteSEOEntrySoap[][] toSoapModels(SiteSEOEntry[][] models) {
		SiteSEOEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SiteSEOEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SiteSEOEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SiteSEOEntrySoap[] toSoapModels(List<SiteSEOEntry> models) {
		List<SiteSEOEntrySoap> soapModels = new ArrayList<SiteSEOEntrySoap>(
			models.size());

		for (SiteSEOEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SiteSEOEntrySoap[soapModels.size()]);
	}

	public SiteSEOEntrySoap() {
	}

	public long getPrimaryKey() {
		return _siteSEOEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSiteSEOEntryId(pk);
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

	public long getSiteSEOEntryId() {
		return _siteSEOEntryId;
	}

	public void setSiteSEOEntryId(long siteSEOEntryId) {
		_siteSEOEntryId = siteSEOEntryId;
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

	public long getOpenGraphImageFileEntryId() {
		return _openGraphImageFileEntryId;
	}

	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId) {
		_openGraphImageFileEntryId = openGraphImageFileEntryId;
	}

	public boolean getOpenGraphSiteEnabled() {
		return _openGraphSiteEnabled;
	}

	public boolean isOpenGraphSiteEnabled() {
		return _openGraphSiteEnabled;
	}

	public void setOpenGraphSiteEnabled(boolean openGraphSiteEnabled) {
		_openGraphSiteEnabled = openGraphSiteEnabled;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _siteSEOEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _openGraphImageFileEntryId;
	private boolean _openGraphSiteEnabled;

}