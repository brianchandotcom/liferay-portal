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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class AssetCategoryPropertyWrapper
	extends BaseModelWrapper<AssetCategoryProperty>
	implements AssetCategoryProperty, ModelWrapper<AssetCategoryProperty> {

	public AssetCategoryPropertyWrapper(
		AssetCategoryProperty assetCategoryProperty) {

		super(assetCategoryProperty);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

	/**
	 * Returns the category ID of this asset category property.
	 *
	 * @return the category ID of this asset category property
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the category property ID of this asset category property.
	 *
	 * @return the category property ID of this asset category property
	 */
	@Override
	public long getCategoryPropertyId() {
		return model.getCategoryPropertyId();
	}

	/**
	 * Returns the company ID of this asset category property.
	 *
	 * @return the company ID of this asset category property
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset category property.
	 *
	 * @return the create date of this asset category property
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the key of this asset category property.
	 *
	 * @return the key of this asset category property
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the modified date of this asset category property.
	 *
	 * @return the modified date of this asset category property
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this asset category property.
	 *
	 * @return the mvcc version of this asset category property
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset category property.
	 *
	 * @return the primary key of this asset category property
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this asset category property.
	 *
	 * @return the user ID of this asset category property
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset category property.
	 *
	 * @return the user name of this asset category property
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset category property.
	 *
	 * @return the user uuid of this asset category property
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this asset category property.
	 *
	 * @return the value of this asset category property
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset category property model instance should use the <code>AssetCategoryProperty</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the category ID of this asset category property.
	 *
	 * @param categoryId the category ID of this asset category property
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the category property ID of this asset category property.
	 *
	 * @param categoryPropertyId the category property ID of this asset category property
	 */
	@Override
	public void setCategoryPropertyId(long categoryPropertyId) {
		model.setCategoryPropertyId(categoryPropertyId);
	}

	/**
	 * Sets the company ID of this asset category property.
	 *
	 * @param companyId the company ID of this asset category property
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset category property.
	 *
	 * @param createDate the create date of this asset category property
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the key of this asset category property.
	 *
	 * @param key the key of this asset category property
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the modified date of this asset category property.
	 *
	 * @param modifiedDate the modified date of this asset category property
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this asset category property.
	 *
	 * @param mvccVersion the mvcc version of this asset category property
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset category property.
	 *
	 * @param primaryKey the primary key of this asset category property
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this asset category property.
	 *
	 * @param userId the user ID of this asset category property
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset category property.
	 *
	 * @param userName the user name of this asset category property
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset category property.
	 *
	 * @param userUuid the user uuid of this asset category property
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this asset category property.
	 *
	 * @param value the value of this asset category property
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected AssetCategoryPropertyWrapper wrap(
		AssetCategoryProperty assetCategoryProperty) {

		return new AssetCategoryPropertyWrapper(assetCategoryProperty);
	}

}