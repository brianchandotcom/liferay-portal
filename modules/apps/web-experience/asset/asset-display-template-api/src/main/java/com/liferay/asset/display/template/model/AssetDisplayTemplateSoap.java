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

package com.liferay.asset.display.template.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.asset.display.template.service.http.AssetDisplayTemplateServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.display.template.service.http.AssetDisplayTemplateServiceSoap
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateSoap implements Serializable {
	public static AssetDisplayTemplateSoap toSoapModel(
		AssetDisplayTemplate model) {
		AssetDisplayTemplateSoap soapModel = new AssetDisplayTemplateSoap();

		soapModel.setAssetDisplayTemplateId(model.getAssetDisplayTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setDDMTemplateId(model.getDDMTemplateId());
		soapModel.setMain(model.getMain());

		return soapModel;
	}

	public static AssetDisplayTemplateSoap[] toSoapModels(
		AssetDisplayTemplate[] models) {
		AssetDisplayTemplateSoap[] soapModels = new AssetDisplayTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayTemplateSoap[][] toSoapModels(
		AssetDisplayTemplate[][] models) {
		AssetDisplayTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetDisplayTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetDisplayTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetDisplayTemplateSoap[] toSoapModels(
		List<AssetDisplayTemplate> models) {
		List<AssetDisplayTemplateSoap> soapModels = new ArrayList<AssetDisplayTemplateSoap>(models.size());

		for (AssetDisplayTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetDisplayTemplateSoap[soapModels.size()]);
	}

	public AssetDisplayTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _assetDisplayTemplateId;
	}

	public void setPrimaryKey(long pk) {
		setAssetDisplayTemplateId(pk);
	}

	public long getAssetDisplayTemplateId() {
		return _assetDisplayTemplateId;
	}

	public void setAssetDisplayTemplateId(long assetDisplayTemplateId) {
		_assetDisplayTemplateId = assetDisplayTemplateId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getDDMTemplateId() {
		return _DDMTemplateId;
	}

	public void setDDMTemplateId(long DDMTemplateId) {
		_DDMTemplateId = DDMTemplateId;
	}

	public boolean getMain() {
		return _main;
	}

	public boolean isMain() {
		return _main;
	}

	public void setMain(boolean main) {
		_main = main;
	}

	private long _assetDisplayTemplateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private long _classNameId;
	private long _DDMTemplateId;
	private boolean _main;
}