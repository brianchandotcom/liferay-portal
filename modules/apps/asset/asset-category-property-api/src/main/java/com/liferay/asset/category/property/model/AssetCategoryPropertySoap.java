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

package com.liferay.asset.category.property.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.asset.category.property.service.http.AssetCategoryPropertyServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AssetCategoryPropertySoap implements Serializable {

	public static AssetCategoryPropertySoap toSoapModel(
		AssetCategoryProperty model) {

		AssetCategoryPropertySoap soapModel = new AssetCategoryPropertySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setCategoryPropertyId(model.getCategoryPropertyId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCategoryId(model.getCategoryId());
		soapModel.setKey(model.getKey());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static AssetCategoryPropertySoap[] toSoapModels(
		AssetCategoryProperty[] models) {

		AssetCategoryPropertySoap[] soapModels =
			new AssetCategoryPropertySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetCategoryPropertySoap[][] toSoapModels(
		AssetCategoryProperty[][] models) {

		AssetCategoryPropertySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new AssetCategoryPropertySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetCategoryPropertySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetCategoryPropertySoap[] toSoapModels(
		List<AssetCategoryProperty> models) {

		List<AssetCategoryPropertySoap> soapModels =
			new ArrayList<AssetCategoryPropertySoap>(models.size());

		for (AssetCategoryProperty model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new AssetCategoryPropertySoap[soapModels.size()]);
	}

	public AssetCategoryPropertySoap() {
	}

	public long getPrimaryKey() {
		return _categoryPropertyId;
	}

	public void setPrimaryKey(long pk) {
		setCategoryPropertyId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getCategoryPropertyId() {
		return _categoryPropertyId;
	}

	public void setCategoryPropertyId(long categoryPropertyId) {
		_categoryPropertyId = categoryPropertyId;
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

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _categoryPropertyId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _categoryId;
	private String _key;
	private String _value;

}