/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagProperty}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagProperty
 * @generated
 */
public class AssetTagPropertyWrapper implements AssetTagProperty,
	ModelWrapper<AssetTagProperty> {
	public AssetTagPropertyWrapper(AssetTagProperty assetTagProperty) {
		_assetTagProperty = assetTagProperty;
	}

	public Class<?> getModelClass() {
		return AssetTagProperty.class;
	}

	public String getModelClassName() {
		return AssetTagProperty.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("tagPropertyId", getTagPropertyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("tagId", getTagId());
		attributes.put("key", getKey());
		attributes.put("value", getValue());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long tagPropertyId = (Long)attributes.get("tagPropertyId");

		if (tagPropertyId != null) {
			setTagPropertyId(tagPropertyId);
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

		Long tagId = (Long)attributes.get("tagId");

		if (tagId != null) {
			setTagId(tagId);
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

	/**
	* Returns the primary key of this asset tag property.
	*
	* @return the primary key of this asset tag property
	*/
	public long getPrimaryKey() {
		return _assetTagProperty.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset tag property.
	*
	* @param primaryKey the primary key of this asset tag property
	*/
	public void setPrimaryKey(long primaryKey) {
		_assetTagProperty.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the tag property ID of this asset tag property.
	*
	* @return the tag property ID of this asset tag property
	*/
	public long getTagPropertyId() {
		return _assetTagProperty.getTagPropertyId();
	}

	/**
	* Sets the tag property ID of this asset tag property.
	*
	* @param tagPropertyId the tag property ID of this asset tag property
	*/
	public void setTagPropertyId(long tagPropertyId) {
		_assetTagProperty.setTagPropertyId(tagPropertyId);
	}

	/**
	* Returns the company ID of this asset tag property.
	*
	* @return the company ID of this asset tag property
	*/
	public long getCompanyId() {
		return _assetTagProperty.getCompanyId();
	}

	/**
	* Sets the company ID of this asset tag property.
	*
	* @param companyId the company ID of this asset tag property
	*/
	public void setCompanyId(long companyId) {
		_assetTagProperty.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset tag property.
	*
	* @return the user ID of this asset tag property
	*/
	public long getUserId() {
		return _assetTagProperty.getUserId();
	}

	/**
	* Sets the user ID of this asset tag property.
	*
	* @param userId the user ID of this asset tag property
	*/
	public void setUserId(long userId) {
		_assetTagProperty.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset tag property.
	*
	* @return the user uuid of this asset tag property
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagProperty.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset tag property.
	*
	* @param userUuid the user uuid of this asset tag property
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_assetTagProperty.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset tag property.
	*
	* @return the user name of this asset tag property
	*/
	public java.lang.String getUserName() {
		return _assetTagProperty.getUserName();
	}

	/**
	* Sets the user name of this asset tag property.
	*
	* @param userName the user name of this asset tag property
	*/
	public void setUserName(java.lang.String userName) {
		_assetTagProperty.setUserName(userName);
	}

	/**
	* Returns the create date of this asset tag property.
	*
	* @return the create date of this asset tag property
	*/
	public java.util.Date getCreateDate() {
		return _assetTagProperty.getCreateDate();
	}

	/**
	* Sets the create date of this asset tag property.
	*
	* @param createDate the create date of this asset tag property
	*/
	public void setCreateDate(java.util.Date createDate) {
		_assetTagProperty.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this asset tag property.
	*
	* @return the modified date of this asset tag property
	*/
	public java.util.Date getModifiedDate() {
		return _assetTagProperty.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset tag property.
	*
	* @param modifiedDate the modified date of this asset tag property
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetTagProperty.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the tag ID of this asset tag property.
	*
	* @return the tag ID of this asset tag property
	*/
	public long getTagId() {
		return _assetTagProperty.getTagId();
	}

	/**
	* Sets the tag ID of this asset tag property.
	*
	* @param tagId the tag ID of this asset tag property
	*/
	public void setTagId(long tagId) {
		_assetTagProperty.setTagId(tagId);
	}

	/**
	* Returns the key of this asset tag property.
	*
	* @return the key of this asset tag property
	*/
	public java.lang.String getKey() {
		return _assetTagProperty.getKey();
	}

	/**
	* Sets the key of this asset tag property.
	*
	* @param key the key of this asset tag property
	*/
	public void setKey(java.lang.String key) {
		_assetTagProperty.setKey(key);
	}

	/**
	* Returns the value of this asset tag property.
	*
	* @return the value of this asset tag property
	*/
	public java.lang.String getValue() {
		return _assetTagProperty.getValue();
	}

	/**
	* Sets the value of this asset tag property.
	*
	* @param value the value of this asset tag property
	*/
	public void setValue(java.lang.String value) {
		_assetTagProperty.setValue(value);
	}

	public boolean isNew() {
		return _assetTagProperty.isNew();
	}

	public void setNew(boolean n) {
		_assetTagProperty.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagProperty.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagProperty.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagProperty.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagProperty.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetTagProperty.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagProperty.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagProperty.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetTagPropertyWrapper((AssetTagProperty)_assetTagProperty.clone());
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return _assetTagProperty.compareTo(assetTagProperty);
	}

	@Override
	public int hashCode() {
		return _assetTagProperty.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetTagProperty> toCacheModel() {
		return _assetTagProperty.toCacheModel();
	}

	public com.liferay.portlet.asset.model.AssetTagProperty toEscapedModel() {
		return new AssetTagPropertyWrapper(_assetTagProperty.toEscapedModel());
	}

	public com.liferay.portlet.asset.model.AssetTagProperty toUnescapedModel() {
		return new AssetTagPropertyWrapper(_assetTagProperty.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetTagProperty.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagProperty.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagProperty.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public AssetTagProperty getWrappedAssetTagProperty() {
		return _assetTagProperty;
	}

	public AssetTagProperty getWrappedModel() {
		return _assetTagProperty;
	}

	public void resetOriginalValues() {
		_assetTagProperty.resetOriginalValues();
	}

	private AssetTagProperty _assetTagProperty;
}