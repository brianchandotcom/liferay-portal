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

package com.liferay.portlet.asset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetCategoryProperty}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryProperty
 * @generated
 */
@ProviderType
public class AssetCategoryPropertyWrapper implements AssetCategoryProperty,
	ModelWrapper<AssetCategoryProperty> {
	public AssetCategoryPropertyWrapper(
		AssetCategoryProperty assetCategoryProperty) {
		_assetCategoryProperty = assetCategoryProperty;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetCategoryProperty.class;
	}

	@Override
	public String getModelClassName() {
		return AssetCategoryProperty.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("categoryPropertyId", getCategoryPropertyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("categoryId", getCategoryId());
		attributes.put("key", getKey());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long categoryPropertyId = (Long)attributes.get("categoryPropertyId");

		if (categoryPropertyId != null) {
			setCategoryPropertyId(categoryPropertyId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new AssetCategoryPropertyWrapper((AssetCategoryProperty)_assetCategoryProperty.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty) {
		return _assetCategoryProperty.compareTo(assetCategoryProperty);
	}

	/**
	* Returns the category ID of this asset category property.
	*
	* @return the category ID of this asset category property
	*/
	@Override
	public long getCategoryId() {
		return _assetCategoryProperty.getCategoryId();
	}

	/**
	* Returns the category property ID of this asset category property.
	*
	* @return the category property ID of this asset category property
	*/
	@Override
	public long getCategoryPropertyId() {
		return _assetCategoryProperty.getCategoryPropertyId();
	}

	/**
	* Returns the company ID of this asset category property.
	*
	* @return the company ID of this asset category property
	*/
	@Override
	public long getCompanyId() {
		return _assetCategoryProperty.getCompanyId();
	}

	/**
	* Returns the create date of this asset category property.
	*
	* @return the create date of this asset category property
	*/
	@Override
	public Date getCreateDate() {
		return _assetCategoryProperty.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetCategoryProperty.getExpandoBridge();
	}

	/**
	* Returns the key of this asset category property.
	*
	* @return the key of this asset category property
	*/
	@Override
	public java.lang.String getKey() {
		return _assetCategoryProperty.getKey();
	}

	/**
	* Returns the modified date of this asset category property.
	*
	* @return the modified date of this asset category property
	*/
	@Override
	public Date getModifiedDate() {
		return _assetCategoryProperty.getModifiedDate();
	}

	/**
	* Returns the primary key of this asset category property.
	*
	* @return the primary key of this asset category property
	*/
	@Override
	public long getPrimaryKey() {
		return _assetCategoryProperty.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetCategoryProperty.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this asset category property.
	*
	* @return the user ID of this asset category property
	*/
	@Override
	public long getUserId() {
		return _assetCategoryProperty.getUserId();
	}

	/**
	* Returns the user name of this asset category property.
	*
	* @return the user name of this asset category property
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetCategoryProperty.getUserName();
	}

	/**
	* Returns the user uuid of this asset category property.
	*
	* @return the user uuid of this asset category property
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _assetCategoryProperty.getUserUuid();
	}

	/**
	* Returns the value of this asset category property.
	*
	* @return the value of this asset category property
	*/
	@Override
	public java.lang.String getValue() {
		return _assetCategoryProperty.getValue();
	}

	@Override
	public int hashCode() {
		return _assetCategoryProperty.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetCategoryProperty.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetCategoryProperty.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetCategoryProperty.isNew();
	}

	@Override
	public void persist() {
		_assetCategoryProperty.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetCategoryProperty.setCachedModel(cachedModel);
	}

	/**
	* Sets the category ID of this asset category property.
	*
	* @param categoryId the category ID of this asset category property
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_assetCategoryProperty.setCategoryId(categoryId);
	}

	/**
	* Sets the category property ID of this asset category property.
	*
	* @param categoryPropertyId the category property ID of this asset category property
	*/
	@Override
	public void setCategoryPropertyId(long categoryPropertyId) {
		_assetCategoryProperty.setCategoryPropertyId(categoryPropertyId);
	}

	/**
	* Sets the company ID of this asset category property.
	*
	* @param companyId the company ID of this asset category property
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetCategoryProperty.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset category property.
	*
	* @param createDate the create date of this asset category property
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetCategoryProperty.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetCategoryProperty.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetCategoryProperty.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetCategoryProperty.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the key of this asset category property.
	*
	* @param key the key of this asset category property
	*/
	@Override
	public void setKey(java.lang.String key) {
		_assetCategoryProperty.setKey(key);
	}

	/**
	* Sets the modified date of this asset category property.
	*
	* @param modifiedDate the modified date of this asset category property
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetCategoryProperty.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_assetCategoryProperty.setNew(n);
	}

	/**
	* Sets the primary key of this asset category property.
	*
	* @param primaryKey the primary key of this asset category property
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetCategoryProperty.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetCategoryProperty.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this asset category property.
	*
	* @param userId the user ID of this asset category property
	*/
	@Override
	public void setUserId(long userId) {
		_assetCategoryProperty.setUserId(userId);
	}

	/**
	* Sets the user name of this asset category property.
	*
	* @param userName the user name of this asset category property
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetCategoryProperty.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset category property.
	*
	* @param userUuid the user uuid of this asset category property
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetCategoryProperty.setUserUuid(userUuid);
	}

	/**
	* Sets the value of this asset category property.
	*
	* @param value the value of this asset category property
	*/
	@Override
	public void setValue(java.lang.String value) {
		_assetCategoryProperty.setValue(value);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetCategoryProperty> toCacheModel() {
		return _assetCategoryProperty.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryProperty toEscapedModel() {
		return new AssetCategoryPropertyWrapper(_assetCategoryProperty.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetCategoryProperty.toString();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetCategoryProperty toUnescapedModel() {
		return new AssetCategoryPropertyWrapper(_assetCategoryProperty.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetCategoryProperty.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetCategoryPropertyWrapper)) {
			return false;
		}

		AssetCategoryPropertyWrapper assetCategoryPropertyWrapper = (AssetCategoryPropertyWrapper)obj;

		if (Validator.equals(_assetCategoryProperty,
					assetCategoryPropertyWrapper._assetCategoryProperty)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetCategoryProperty getWrappedModel() {
		return _assetCategoryProperty;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetCategoryProperty.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetCategoryProperty.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetCategoryProperty.resetOriginalValues();
	}

	private final AssetCategoryProperty _assetCategoryProperty;
}