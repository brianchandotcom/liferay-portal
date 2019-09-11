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
 * This class is used by SOAP remote services, specifically {@link com.liferay.site.navigation.service.http.SiteNavigationMenuItemServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuItemSoap implements Serializable {

	public static SiteNavigationMenuItemSoap toSoapModel(
		SiteNavigationMenuItem model) {

		SiteNavigationMenuItemSoap soapModel = new SiteNavigationMenuItemSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setSiteNavigationMenuItemId(
			model.getSiteNavigationMenuItemId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSiteNavigationMenuId(model.getSiteNavigationMenuId());
		soapModel.setParentSiteNavigationMenuItemId(
			model.getParentSiteNavigationMenuItemId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setOrder(model.getOrder());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static SiteNavigationMenuItemSoap[] toSoapModels(
		SiteNavigationMenuItem[] models) {

		SiteNavigationMenuItemSoap[] soapModels =
			new SiteNavigationMenuItemSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SiteNavigationMenuItemSoap[][] toSoapModels(
		SiteNavigationMenuItem[][] models) {

		SiteNavigationMenuItemSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SiteNavigationMenuItemSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SiteNavigationMenuItemSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SiteNavigationMenuItemSoap[] toSoapModels(
		List<SiteNavigationMenuItem> models) {

		List<SiteNavigationMenuItemSoap> soapModels =
			new ArrayList<SiteNavigationMenuItemSoap>(models.size());

		for (SiteNavigationMenuItem model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SiteNavigationMenuItemSoap[soapModels.size()]);
	}

	public SiteNavigationMenuItemSoap() {
	}

	public long getPrimaryKey() {
		return _siteNavigationMenuItemId;
	}

	public void setPrimaryKey(long pk) {
		setSiteNavigationMenuItemId(pk);
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

	public long getSiteNavigationMenuItemId() {
		return _siteNavigationMenuItemId;
	}

	public void setSiteNavigationMenuItemId(long siteNavigationMenuItemId) {
		_siteNavigationMenuItemId = siteNavigationMenuItemId;
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

	public long getSiteNavigationMenuId() {
		return _siteNavigationMenuId;
	}

	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenuId = siteNavigationMenuId;
	}

	public long getParentSiteNavigationMenuItemId() {
		return _parentSiteNavigationMenuItemId;
	}

	public void setParentSiteNavigationMenuItemId(
		long parentSiteNavigationMenuItemId) {

		_parentSiteNavigationMenuItemId = parentSiteNavigationMenuItemId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getOrder() {
		return _order;
	}

	public void setOrder(int order) {
		_order = order;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _siteNavigationMenuItemId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _siteNavigationMenuId;
	private long _parentSiteNavigationMenuItemId;
	private String _name;
	private String _type;
	private String _typeSettings;
	private int _order;
	private Date _lastPublishDate;

}