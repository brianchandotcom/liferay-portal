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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ShoppingCategory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategory
 * @generated
 */
public class ShoppingCategoryWrapper implements ShoppingCategory,
	ModelWrapper<ShoppingCategory> {
	public ShoppingCategoryWrapper(ShoppingCategory shoppingCategory) {
		_shoppingCategory = shoppingCategory;
	}

	public Class<?> getModelClass() {
		return ShoppingCategory.class;
	}

	public String getModelClassName() {
		return ShoppingCategory.class.getName();
	}

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

	/**
	* Returns the primary key of this shopping category.
	*
	* @return the primary key of this shopping category
	*/
	public long getPrimaryKey() {
		return _shoppingCategory.getPrimaryKey();
	}

	/**
	* Sets the primary key of this shopping category.
	*
	* @param primaryKey the primary key of this shopping category
	*/
	public void setPrimaryKey(long primaryKey) {
		_shoppingCategory.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the category ID of this shopping category.
	*
	* @return the category ID of this shopping category
	*/
	public long getCategoryId() {
		return _shoppingCategory.getCategoryId();
	}

	/**
	* Sets the category ID of this shopping category.
	*
	* @param categoryId the category ID of this shopping category
	*/
	public void setCategoryId(long categoryId) {
		_shoppingCategory.setCategoryId(categoryId);
	}

	/**
	* Returns the group ID of this shopping category.
	*
	* @return the group ID of this shopping category
	*/
	public long getGroupId() {
		return _shoppingCategory.getGroupId();
	}

	/**
	* Sets the group ID of this shopping category.
	*
	* @param groupId the group ID of this shopping category
	*/
	public void setGroupId(long groupId) {
		_shoppingCategory.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this shopping category.
	*
	* @return the company ID of this shopping category
	*/
	public long getCompanyId() {
		return _shoppingCategory.getCompanyId();
	}

	/**
	* Sets the company ID of this shopping category.
	*
	* @param companyId the company ID of this shopping category
	*/
	public void setCompanyId(long companyId) {
		_shoppingCategory.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this shopping category.
	*
	* @return the user ID of this shopping category
	*/
	public long getUserId() {
		return _shoppingCategory.getUserId();
	}

	/**
	* Sets the user ID of this shopping category.
	*
	* @param userId the user ID of this shopping category
	*/
	public void setUserId(long userId) {
		_shoppingCategory.setUserId(userId);
	}

	/**
	* Returns the user uuid of this shopping category.
	*
	* @return the user uuid of this shopping category
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shoppingCategory.getUserUuid();
	}

	/**
	* Sets the user uuid of this shopping category.
	*
	* @param userUuid the user uuid of this shopping category
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCategory.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this shopping category.
	*
	* @return the user name of this shopping category
	*/
	public java.lang.String getUserName() {
		return _shoppingCategory.getUserName();
	}

	/**
	* Sets the user name of this shopping category.
	*
	* @param userName the user name of this shopping category
	*/
	public void setUserName(java.lang.String userName) {
		_shoppingCategory.setUserName(userName);
	}

	/**
	* Returns the create date of this shopping category.
	*
	* @return the create date of this shopping category
	*/
	public java.util.Date getCreateDate() {
		return _shoppingCategory.getCreateDate();
	}

	/**
	* Sets the create date of this shopping category.
	*
	* @param createDate the create date of this shopping category
	*/
	public void setCreateDate(java.util.Date createDate) {
		_shoppingCategory.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this shopping category.
	*
	* @return the modified date of this shopping category
	*/
	public java.util.Date getModifiedDate() {
		return _shoppingCategory.getModifiedDate();
	}

	/**
	* Sets the modified date of this shopping category.
	*
	* @param modifiedDate the modified date of this shopping category
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingCategory.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the parent category ID of this shopping category.
	*
	* @return the parent category ID of this shopping category
	*/
	public long getParentCategoryId() {
		return _shoppingCategory.getParentCategoryId();
	}

	/**
	* Sets the parent category ID of this shopping category.
	*
	* @param parentCategoryId the parent category ID of this shopping category
	*/
	public void setParentCategoryId(long parentCategoryId) {
		_shoppingCategory.setParentCategoryId(parentCategoryId);
	}

	/**
	* Returns the name of this shopping category.
	*
	* @return the name of this shopping category
	*/
	public java.lang.String getName() {
		return _shoppingCategory.getName();
	}

	/**
	* Sets the name of this shopping category.
	*
	* @param name the name of this shopping category
	*/
	public void setName(java.lang.String name) {
		_shoppingCategory.setName(name);
	}

	/**
	* Returns the description of this shopping category.
	*
	* @return the description of this shopping category
	*/
	public java.lang.String getDescription() {
		return _shoppingCategory.getDescription();
	}

	/**
	* Sets the description of this shopping category.
	*
	* @param description the description of this shopping category
	*/
	public void setDescription(java.lang.String description) {
		_shoppingCategory.setDescription(description);
	}

	public boolean isNew() {
		return _shoppingCategory.isNew();
	}

	public void setNew(boolean n) {
		_shoppingCategory.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingCategory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingCategory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingCategory.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCategory.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_shoppingCategory.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCategory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_shoppingCategory.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_shoppingCategory.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingCategory.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ShoppingCategoryWrapper((ShoppingCategory)_shoppingCategory.clone());
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory) {
		return _shoppingCategory.compareTo(shoppingCategory);
	}

	@Override
	public int hashCode() {
		return _shoppingCategory.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.shopping.model.ShoppingCategory> toCacheModel() {
		return _shoppingCategory.toCacheModel();
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory toEscapedModel() {
		return new ShoppingCategoryWrapper(_shoppingCategory.toEscapedModel());
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory toUnescapedModel() {
		return new ShoppingCategoryWrapper(_shoppingCategory.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _shoppingCategory.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingCategory.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_shoppingCategory.persist();
	}

	public boolean isRoot() {
		return _shoppingCategory.isRoot();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ShoppingCategory getWrappedShoppingCategory() {
		return _shoppingCategory;
	}

	public ShoppingCategory getWrappedModel() {
		return _shoppingCategory;
	}

	public void resetOriginalValues() {
		_shoppingCategory.resetOriginalValues();
	}

	private ShoppingCategory _shoppingCategory;
}