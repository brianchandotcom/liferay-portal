/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagProperty}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagProperty
 * @generated
 */
public class AssetTagPropertyWrapper implements AssetTagProperty,
	ModelWrapper<AssetTagProperty> {
	public AssetTagPropertyWrapper(AssetTagProperty assetTagProperty) {
		_assetTagProperty = assetTagProperty;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetTagProperty.class;
	}

	@Override
	public String getModelClassName() {
		return AssetTagProperty.class.getName();
	}

	@Override
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

	@Override
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
	@Override
	public long getPrimaryKey() {
		return _assetTagProperty.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset tag property.
	*
	* @param primaryKey the primary key of this asset tag property
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetTagProperty.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the tag property ID of this asset tag property.
	*
	* @return the tag property ID of this asset tag property
	*/
	@Override
	public long getTagPropertyId() {
		return _assetTagProperty.getTagPropertyId();
	}

	/**
	* Sets the tag property ID of this asset tag property.
	*
	* @param tagPropertyId the tag property ID of this asset tag property
	*/
	@Override
	public void setTagPropertyId(long tagPropertyId) {
		_assetTagProperty.setTagPropertyId(tagPropertyId);
	}

	/**
	* Returns the company ID of this asset tag property.
	*
	* @return the company ID of this asset tag property
	*/
	@Override
	public long getCompanyId() {
		return _assetTagProperty.getCompanyId();
	}

	/**
	* Sets the company ID of this asset tag property.
	*
	* @param companyId the company ID of this asset tag property
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetTagProperty.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this asset tag property.
	*
	* @return the user ID of this asset tag property
	*/
	@Override
	public long getUserId() {
		return _assetTagProperty.getUserId();
	}

	/**
	* Sets the user ID of this asset tag property.
	*
	* @param userId the user ID of this asset tag property
	*/
	@Override
	public void setUserId(long userId) {
		_assetTagProperty.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset tag property.
	*
	* @return the user uuid of this asset tag property
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetTagProperty.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset tag property.
	*
	* @param userUuid the user uuid of this asset tag property
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetTagProperty.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this asset tag property.
	*
	* @return the user name of this asset tag property
	*/
	@Override
	public java.lang.String getUserName() {
		return _assetTagProperty.getUserName();
	}

	/**
	* Sets the user name of this asset tag property.
	*
	* @param userName the user name of this asset tag property
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_assetTagProperty.setUserName(userName);
	}

	/**
	* Returns the create date of this asset tag property.
	*
	* @return the create date of this asset tag property
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _assetTagProperty.getCreateDate();
	}

	/**
	* Sets the create date of this asset tag property.
	*
	* @param createDate the create date of this asset tag property
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_assetTagProperty.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this asset tag property.
	*
	* @return the modified date of this asset tag property
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _assetTagProperty.getModifiedDate();
	}

	/**
	* Sets the modified date of this asset tag property.
	*
	* @param modifiedDate the modified date of this asset tag property
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetTagProperty.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the tag ID of this asset tag property.
	*
	* @return the tag ID of this asset tag property
	*/
	@Override
	public long getTagId() {
		return _assetTagProperty.getTagId();
	}

	/**
	* Sets the tag ID of this asset tag property.
	*
	* @param tagId the tag ID of this asset tag property
	*/
	@Override
	public void setTagId(long tagId) {
		_assetTagProperty.setTagId(tagId);
	}

	/**
	* Returns the key of this asset tag property.
	*
	* @return the key of this asset tag property
	*/
	@Override
	public java.lang.String getKey() {
		return _assetTagProperty.getKey();
	}

	/**
	* Sets the key of this asset tag property.
	*
	* @param key the key of this asset tag property
	*/
	@Override
	public void setKey(java.lang.String key) {
		_assetTagProperty.setKey(key);
	}

	/**
	* Returns the value of this asset tag property.
	*
	* @return the value of this asset tag property
	*/
	@Override
	public java.lang.String getValue() {
		return _assetTagProperty.getValue();
	}

	/**
	* Sets the value of this asset tag property.
	*
	* @param value the value of this asset tag property
	*/
	@Override
	public void setValue(java.lang.String value) {
		_assetTagProperty.setValue(value);
	}

	@Override
	public boolean isNew() {
		return _assetTagProperty.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_assetTagProperty.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _assetTagProperty.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetTagProperty.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _assetTagProperty.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagProperty.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetTagProperty.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagProperty.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetTagProperty.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetTagProperty.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagProperty.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetTagPropertyWrapper((AssetTagProperty)_assetTagProperty.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		return _assetTagProperty.compareTo(assetTagProperty);
	}

	@Override
	public int hashCode() {
		return _assetTagProperty.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetTagProperty> toCacheModel() {
		return _assetTagProperty.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTagProperty toEscapedModel() {
		return new AssetTagPropertyWrapper(_assetTagProperty.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.asset.model.AssetTagProperty toUnescapedModel() {
		return new AssetTagPropertyWrapper(_assetTagProperty.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetTagProperty.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetTagProperty.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetTagProperty.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetTagPropertyWrapper)) {
			return false;
		}

		AssetTagPropertyWrapper assetTagPropertyWrapper = (AssetTagPropertyWrapper)obj;

		if (Validator.equals(_assetTagProperty,
					assetTagPropertyWrapper._assetTagProperty)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AssetTagProperty getWrappedAssetTagProperty() {
		return _assetTagProperty;
	}

	@Override
	public AssetTagProperty getWrappedModel() {
		return _assetTagProperty;
	}

	@Override
	public void resetOriginalValues() {
		_assetTagProperty.resetOriginalValues();
	}

	private AssetTagProperty _assetTagProperty;
}