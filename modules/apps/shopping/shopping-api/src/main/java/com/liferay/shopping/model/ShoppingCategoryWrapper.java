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

package com.liferay.shopping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingCategory}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategory
 * @generated
 */
@ProviderType
public class ShoppingCategoryWrapper implements ShoppingCategory,
	ModelWrapper<ShoppingCategory> {
	public ShoppingCategoryWrapper(ShoppingCategory shoppingCategory) {
		_shoppingCategory = shoppingCategory;
	}

	@Override
	public Class<?> getModelClass() {
		return ShoppingCategory.class;
	}

	@Override
	public String getModelClassName() {
		return ShoppingCategory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("categoryId", getCategoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCategoryId", getParentCategoryId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long parentCategoryId = (Long)attributes.get("parentCategoryId");

		if (parentCategoryId != null) {
			setParentCategoryId(parentCategoryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public ShoppingCategory toEscapedModel() {
		return new ShoppingCategoryWrapper(_shoppingCategory.toEscapedModel());
	}

	@Override
	public ShoppingCategory toUnescapedModel() {
		return new ShoppingCategoryWrapper(_shoppingCategory.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _shoppingCategory.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _shoppingCategory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _shoppingCategory.isNew();
	}

	@Override
	public boolean isRoot() {
		return _shoppingCategory.isRoot();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _shoppingCategory.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ShoppingCategory> toCacheModel() {
		return _shoppingCategory.toCacheModel();
	}

	@Override
	public int compareTo(ShoppingCategory shoppingCategory) {
		return _shoppingCategory.compareTo(shoppingCategory);
	}

	@Override
	public int hashCode() {
		return _shoppingCategory.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _shoppingCategory.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingCategoryWrapper((ShoppingCategory)_shoppingCategory.clone());
	}

	/**
	* Returns the description of this shopping category.
	*
	* @return the description of this shopping category
	*/
	@Override
	public java.lang.String getDescription() {
		return _shoppingCategory.getDescription();
	}

	/**
	* Returns the name of this shopping category.
	*
	* @return the name of this shopping category
	*/
	@Override
	public java.lang.String getName() {
		return _shoppingCategory.getName();
	}

	/**
	* Returns the user name of this shopping category.
	*
	* @return the user name of this shopping category
	*/
	@Override
	public java.lang.String getUserName() {
		return _shoppingCategory.getUserName();
	}

	/**
	* Returns the user uuid of this shopping category.
	*
	* @return the user uuid of this shopping category
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _shoppingCategory.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _shoppingCategory.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _shoppingCategory.toXmlString();
	}

	/**
	* Returns the create date of this shopping category.
	*
	* @return the create date of this shopping category
	*/
	@Override
	public Date getCreateDate() {
		return _shoppingCategory.getCreateDate();
	}

	/**
	* Returns the modified date of this shopping category.
	*
	* @return the modified date of this shopping category
	*/
	@Override
	public Date getModifiedDate() {
		return _shoppingCategory.getModifiedDate();
	}

	/**
	* Returns the category ID of this shopping category.
	*
	* @return the category ID of this shopping category
	*/
	@Override
	public long getCategoryId() {
		return _shoppingCategory.getCategoryId();
	}

	/**
	* Returns the company ID of this shopping category.
	*
	* @return the company ID of this shopping category
	*/
	@Override
	public long getCompanyId() {
		return _shoppingCategory.getCompanyId();
	}

	/**
	* Returns the group ID of this shopping category.
	*
	* @return the group ID of this shopping category
	*/
	@Override
	public long getGroupId() {
		return _shoppingCategory.getGroupId();
	}

	/**
	* Returns the parent category ID of this shopping category.
	*
	* @return the parent category ID of this shopping category
	*/
	@Override
	public long getParentCategoryId() {
		return _shoppingCategory.getParentCategoryId();
	}

	/**
	* Returns the primary key of this shopping category.
	*
	* @return the primary key of this shopping category
	*/
	@Override
	public long getPrimaryKey() {
		return _shoppingCategory.getPrimaryKey();
	}

	/**
	* Returns the user ID of this shopping category.
	*
	* @return the user ID of this shopping category
	*/
	@Override
	public long getUserId() {
		return _shoppingCategory.getUserId();
	}

	@Override
	public void persist() {
		_shoppingCategory.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_shoppingCategory.setCachedModel(cachedModel);
	}

	/**
	* Sets the category ID of this shopping category.
	*
	* @param categoryId the category ID of this shopping category
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_shoppingCategory.setCategoryId(categoryId);
	}

	/**
	* Sets the company ID of this shopping category.
	*
	* @param companyId the company ID of this shopping category
	*/
	@Override
	public void setCompanyId(long companyId) {
		_shoppingCategory.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this shopping category.
	*
	* @param createDate the create date of this shopping category
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_shoppingCategory.setCreateDate(createDate);
	}

	/**
	* Sets the description of this shopping category.
	*
	* @param description the description of this shopping category
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_shoppingCategory.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_shoppingCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_shoppingCategory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_shoppingCategory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this shopping category.
	*
	* @param groupId the group ID of this shopping category
	*/
	@Override
	public void setGroupId(long groupId) {
		_shoppingCategory.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this shopping category.
	*
	* @param modifiedDate the modified date of this shopping category
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_shoppingCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this shopping category.
	*
	* @param name the name of this shopping category
	*/
	@Override
	public void setName(java.lang.String name) {
		_shoppingCategory.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_shoppingCategory.setNew(n);
	}

	/**
	* Sets the parent category ID of this shopping category.
	*
	* @param parentCategoryId the parent category ID of this shopping category
	*/
	@Override
	public void setParentCategoryId(long parentCategoryId) {
		_shoppingCategory.setParentCategoryId(parentCategoryId);
	}

	/**
	* Sets the primary key of this shopping category.
	*
	* @param primaryKey the primary key of this shopping category
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_shoppingCategory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_shoppingCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this shopping category.
	*
	* @param userId the user ID of this shopping category
	*/
	@Override
	public void setUserId(long userId) {
		_shoppingCategory.setUserId(userId);
	}

	/**
	* Sets the user name of this shopping category.
	*
	* @param userName the user name of this shopping category
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_shoppingCategory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this shopping category.
	*
	* @param userUuid the user uuid of this shopping category
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCategory.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ShoppingCategoryWrapper)) {
			return false;
		}

		ShoppingCategoryWrapper shoppingCategoryWrapper = (ShoppingCategoryWrapper)obj;

		if (Objects.equals(_shoppingCategory,
					shoppingCategoryWrapper._shoppingCategory)) {
			return true;
		}

		return false;
	}

	@Override
	public ShoppingCategory getWrappedModel() {
		return _shoppingCategory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _shoppingCategory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _shoppingCategory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_shoppingCategory.resetOriginalValues();
	}

	private final ShoppingCategory _shoppingCategory;
}