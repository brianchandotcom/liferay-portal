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

package com.liferay.commerce.wish.list.model;

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
 * This class is a wrapper for {@link CommerceWishListItem}.
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListItem
 * @generated
 */
@ProviderType
public class CommerceWishListItemWrapper implements CommerceWishListItem,
	ModelWrapper<CommerceWishListItem> {
	public CommerceWishListItemWrapper(
		CommerceWishListItem commerceWishListItem) {
		_commerceWishListItem = commerceWishListItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceWishListItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceWishListItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceWishListItemId", getCommerceWishListItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceWishListId", getCommerceWishListId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceWishListItemId = (Long)attributes.get(
				"commerceWishListItemId");

		if (commerceWishListItemId != null) {
			setCommerceWishListItemId(commerceWishListItemId);
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

		Long commerceWishListId = (Long)attributes.get("commerceWishListId");

		if (commerceWishListId != null) {
			setCommerceWishListId(commerceWishListId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceWishListItemWrapper((CommerceWishListItem)_commerceWishListItem.clone());
	}

	@Override
	public int compareTo(CommerceWishListItem commerceWishListItem) {
		return _commerceWishListItem.compareTo(commerceWishListItem);
	}

	/**
	* Returns the commerce wish list ID of this commerce wish list item.
	*
	* @return the commerce wish list ID of this commerce wish list item
	*/
	@Override
	public long getCommerceWishListId() {
		return _commerceWishListItem.getCommerceWishListId();
	}

	/**
	* Returns the commerce wish list item ID of this commerce wish list item.
	*
	* @return the commerce wish list item ID of this commerce wish list item
	*/
	@Override
	public long getCommerceWishListItemId() {
		return _commerceWishListItem.getCommerceWishListItemId();
	}

	/**
	* Returns the company ID of this commerce wish list item.
	*
	* @return the company ID of this commerce wish list item
	*/
	@Override
	public long getCompanyId() {
		return _commerceWishListItem.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this commerce wish list item.
	*
	* @return the cp definition ID of this commerce wish list item
	*/
	@Override
	public long getCPDefinitionId() {
		return _commerceWishListItem.getCPDefinitionId();
	}

	/**
	* Returns the cp instance ID of this commerce wish list item.
	*
	* @return the cp instance ID of this commerce wish list item
	*/
	@Override
	public long getCPInstanceId() {
		return _commerceWishListItem.getCPInstanceId();
	}

	/**
	* Returns the create date of this commerce wish list item.
	*
	* @return the create date of this commerce wish list item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceWishListItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceWishListItem.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce wish list item.
	*
	* @return the group ID of this commerce wish list item
	*/
	@Override
	public long getGroupId() {
		return _commerceWishListItem.getGroupId();
	}

	/**
	* Returns the json of this commerce wish list item.
	*
	* @return the json of this commerce wish list item
	*/
	@Override
	public java.lang.String getJson() {
		return _commerceWishListItem.getJson();
	}

	/**
	* Returns the modified date of this commerce wish list item.
	*
	* @return the modified date of this commerce wish list item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceWishListItem.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce wish list item.
	*
	* @return the primary key of this commerce wish list item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceWishListItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceWishListItem.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce wish list item.
	*
	* @return the user ID of this commerce wish list item
	*/
	@Override
	public long getUserId() {
		return _commerceWishListItem.getUserId();
	}

	/**
	* Returns the user name of this commerce wish list item.
	*
	* @return the user name of this commerce wish list item
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceWishListItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce wish list item.
	*
	* @return the user uuid of this commerce wish list item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceWishListItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceWishListItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceWishListItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceWishListItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceWishListItem.isNew();
	}

	@Override
	public void persist() {
		_commerceWishListItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceWishListItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce wish list ID of this commerce wish list item.
	*
	* @param commerceWishListId the commerce wish list ID of this commerce wish list item
	*/
	@Override
	public void setCommerceWishListId(long commerceWishListId) {
		_commerceWishListItem.setCommerceWishListId(commerceWishListId);
	}

	/**
	* Sets the commerce wish list item ID of this commerce wish list item.
	*
	* @param commerceWishListItemId the commerce wish list item ID of this commerce wish list item
	*/
	@Override
	public void setCommerceWishListItemId(long commerceWishListItemId) {
		_commerceWishListItem.setCommerceWishListItemId(commerceWishListItemId);
	}

	/**
	* Sets the company ID of this commerce wish list item.
	*
	* @param companyId the company ID of this commerce wish list item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceWishListItem.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this commerce wish list item.
	*
	* @param CPDefinitionId the cp definition ID of this commerce wish list item
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_commerceWishListItem.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp instance ID of this commerce wish list item.
	*
	* @param CPInstanceId the cp instance ID of this commerce wish list item
	*/
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_commerceWishListItem.setCPInstanceId(CPInstanceId);
	}

	/**
	* Sets the create date of this commerce wish list item.
	*
	* @param createDate the create date of this commerce wish list item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceWishListItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceWishListItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceWishListItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceWishListItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce wish list item.
	*
	* @param groupId the group ID of this commerce wish list item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceWishListItem.setGroupId(groupId);
	}

	/**
	* Sets the json of this commerce wish list item.
	*
	* @param json the json of this commerce wish list item
	*/
	@Override
	public void setJson(java.lang.String json) {
		_commerceWishListItem.setJson(json);
	}

	/**
	* Sets the modified date of this commerce wish list item.
	*
	* @param modifiedDate the modified date of this commerce wish list item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceWishListItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceWishListItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce wish list item.
	*
	* @param primaryKey the primary key of this commerce wish list item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceWishListItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceWishListItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce wish list item.
	*
	* @param userId the user ID of this commerce wish list item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceWishListItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce wish list item.
	*
	* @param userName the user name of this commerce wish list item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceWishListItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce wish list item.
	*
	* @param userUuid the user uuid of this commerce wish list item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceWishListItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceWishListItem> toCacheModel() {
		return _commerceWishListItem.toCacheModel();
	}

	@Override
	public CommerceWishListItem toEscapedModel() {
		return new CommerceWishListItemWrapper(_commerceWishListItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceWishListItem.toString();
	}

	@Override
	public CommerceWishListItem toUnescapedModel() {
		return new CommerceWishListItemWrapper(_commerceWishListItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceWishListItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWishListItemWrapper)) {
			return false;
		}

		CommerceWishListItemWrapper commerceWishListItemWrapper = (CommerceWishListItemWrapper)obj;

		if (Objects.equals(_commerceWishListItem,
					commerceWishListItemWrapper._commerceWishListItem)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceWishListItem getWrappedModel() {
		return _commerceWishListItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceWishListItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceWishListItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceWishListItem.resetOriginalValues();
	}

	private final CommerceWishListItem _commerceWishListItem;
}