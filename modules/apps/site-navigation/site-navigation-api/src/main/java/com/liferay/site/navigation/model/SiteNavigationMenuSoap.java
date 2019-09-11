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

package com.liferay.site.navigation.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.site.navigation.service.http.SiteNavigationMenuServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuSoap implements Serializable {

	public static SiteNavigationMenuSoap toSoapModel(SiteNavigationMenu model) {
		SiteNavigationMenuSoap soapModel = new SiteNavigationMenuSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSiteNavigationMenuId(model.getSiteNavigationMenuId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setAuto(model.isAuto());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static SiteNavigationMenuSoap[] toSoapModels(
		SiteNavigationMenu[] models) {

		SiteNavigationMenuSoap[] soapModels =
			new SiteNavigationMenuSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SiteNavigationMenuSoap[][] toSoapModels(
		SiteNavigationMenu[][] models) {

		SiteNavigationMenuSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SiteNavigationMenuSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SiteNavigationMenuSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SiteNavigationMenuSoap[] toSoapModels(
		List<SiteNavigationMenu> models) {

		List<SiteNavigationMenuSoap> soapModels =
			new ArrayList<SiteNavigationMenuSoap>(models.size());

		for (SiteNavigationMenu model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SiteNavigationMenuSoap[soapModels.size()]);
	}

	public SiteNavigationMenuSoap() {
	}

	public long getPrimaryKey() {
		return _siteNavigationMenuId;
	}

	public void setPrimaryKey(long pk) {
		setSiteNavigationMenuId(pk);
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

	public long getSiteNavigationMenuId() {
		return _siteNavigationMenuId;
	}

	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenuId = siteNavigationMenuId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public boolean getAuto() {
		return _auto;
	}

	public boolean isAuto() {
		return _auto;
	}

	public void setAuto(boolean auto) {
		_auto = auto;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _siteNavigationMenuId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private int _type;
	private boolean _auto;
	private Date _lastPublishDate;

}