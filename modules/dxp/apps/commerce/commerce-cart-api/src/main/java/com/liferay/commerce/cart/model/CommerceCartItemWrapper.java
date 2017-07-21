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

package com.liferay.commerce.cart.model;

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
 * This class is a wrapper for {@link CommerceCartItem}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceCartItem
 * @generated
 */
@ProviderType
public class CommerceCartItemWrapper implements CommerceCartItem,
	ModelWrapper<CommerceCartItem> {
	public CommerceCartItemWrapper(CommerceCartItem commerceCartItem) {
		_commerceCartItem = commerceCartItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceCartItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceCartItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("CommerceCartItemId", getCommerceCartItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CommerceCartId", getCommerceCartId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("quantity", getQuantity());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CommerceCartItemId = (Long)attributes.get("CommerceCartItemId");

		if (CommerceCartItemId != null) {
			setCommerceCartItemId(CommerceCartItemId);
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

		Long CommerceCartId = (Long)attributes.get("CommerceCartId");

		if (CommerceCartId != null) {
			setCommerceCartId(CommerceCartId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJson(json);
		}
	}

	@Override
	public CommerceCartItem toEscapedModel() {
		return new CommerceCartItemWrapper(_commerceCartItem.toEscapedModel());
	}

	@Override
	public CommerceCartItem toUnescapedModel() {
		return new CommerceCartItemWrapper(_commerceCartItem.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _commerceCartItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceCartItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceCartItem.isNew();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition() {
		return _commerceCartItem.getCPDefinition();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceCartItem.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceCartItem> toCacheModel() {
		return _commerceCartItem.toCacheModel();
	}

	@Override
	public int compareTo(CommerceCartItem commerceCartItem) {
		return _commerceCartItem.compareTo(commerceCartItem);
	}

	/**
	* Returns the quantity of this commerce cart item.
	*
	* @return the quantity of this commerce cart item
	*/
	@Override
	public int getQuantity() {
		return _commerceCartItem.getQuantity();
	}

	@Override
	public int hashCode() {
		return _commerceCartItem.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceCartItem.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceCartItemWrapper((CommerceCartItem)_commerceCartItem.clone());
	}

	/**
	* Returns the json of this commerce cart item.
	*
	* @return the json of this commerce cart item
	*/
	@Override
	public java.lang.String getJson() {
		return _commerceCartItem.getJson();
	}

	/**
	* Returns the user name of this commerce cart item.
	*
	* @return the user name of this commerce cart item
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceCartItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce cart item.
	*
	* @return the user uuid of this commerce cart item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceCartItem.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceCartItem.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceCartItem.toXmlString();
	}

	/**
	* Returns the create date of this commerce cart item.
	*
	* @return the create date of this commerce cart item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceCartItem.getCreateDate();
	}

	/**
	* Returns the modified date of this commerce cart item.
	*
	* @return the modified date of this commerce cart item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceCartItem.getModifiedDate();
	}

	/**
	* Returns the cp definition ID of this commerce cart item.
	*
	* @return the cp definition ID of this commerce cart item
	*/
	@Override
	public long getCPDefinitionId() {
		return _commerceCartItem.getCPDefinitionId();
	}

	/**
	* Returns the cp instance ID of this commerce cart item.
	*
	* @return the cp instance ID of this commerce cart item
	*/
	@Override
	public long getCPInstanceId() {
		return _commerceCartItem.getCPInstanceId();
	}

	/**
	* Returns the commerce cart ID of this commerce cart item.
	*
	* @return the commerce cart ID of this commerce cart item
	*/
	@Override
	public long getCommerceCartId() {
		return _commerceCartItem.getCommerceCartId();
	}

	/**
	* Returns the commerce cart item ID of this commerce cart item.
	*
	* @return the commerce cart item ID of this commerce cart item
	*/
	@Override
	public long getCommerceCartItemId() {
		return _commerceCartItem.getCommerceCartItemId();
	}

	/**
	* Returns the company ID of this commerce cart item.
	*
	* @return the company ID of this commerce cart item
	*/
	@Override
	public long getCompanyId() {
		return _commerceCartItem.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce cart item.
	*
	* @return the group ID of this commerce cart item
	*/
	@Override
	public long getGroupId() {
		return _commerceCartItem.getGroupId();
	}

	/**
	* Returns the primary key of this commerce cart item.
	*
	* @return the primary key of this commerce cart item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceCartItem.getPrimaryKey();
	}

	/**
	* Returns the user ID of this commerce cart item.
	*
	* @return the user ID of this commerce cart item
	*/
	@Override
	public long getUserId() {
		return _commerceCartItem.getUserId();
	}

	@Override
	public void persist() {
		_commerceCartItem.persist();
	}

	/**
	* Sets the cp definition ID of this commerce cart item.
	*
	* @param CPDefinitionId the cp definition ID of this commerce cart item
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_commerceCartItem.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp instance ID of this commerce cart item.
	*
	* @param CPInstanceId the cp instance ID of this commerce cart item
	*/
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_commerceCartItem.setCPInstanceId(CPInstanceId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceCartItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce cart ID of this commerce cart item.
	*
	* @param CommerceCartId the commerce cart ID of this commerce cart item
	*/
	@Override
	public void setCommerceCartId(long CommerceCartId) {
		_commerceCartItem.setCommerceCartId(CommerceCartId);
	}

	/**
	* Sets the commerce cart item ID of this commerce cart item.
	*
	* @param CommerceCartItemId the commerce cart item ID of this commerce cart item
	*/
	@Override
	public void setCommerceCartItemId(long CommerceCartItemId) {
		_commerceCartItem.setCommerceCartItemId(CommerceCartItemId);
	}

	/**
	* Sets the company ID of this commerce cart item.
	*
	* @param companyId the company ID of this commerce cart item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceCartItem.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce cart item.
	*
	* @param createDate the create date of this commerce cart item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceCartItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceCartItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceCartItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceCartItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce cart item.
	*
	* @param groupId the group ID of this commerce cart item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceCartItem.setGroupId(groupId);
	}

	/**
	* Sets the json of this commerce cart item.
	*
	* @param json the json of this commerce cart item
	*/
	@Override
	public void setJson(java.lang.String json) {
		_commerceCartItem.setJson(json);
	}

	/**
	* Sets the modified date of this commerce cart item.
	*
	* @param modifiedDate the modified date of this commerce cart item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceCartItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceCartItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce cart item.
	*
	* @param primaryKey the primary key of this commerce cart item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceCartItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceCartItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the quantity of this commerce cart item.
	*
	* @param quantity the quantity of this commerce cart item
	*/
	@Override
	public void setQuantity(int quantity) {
		_commerceCartItem.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this commerce cart item.
	*
	* @param userId the user ID of this commerce cart item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceCartItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce cart item.
	*
	* @param userName the user name of this commerce cart item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceCartItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce cart item.
	*
	* @param userUuid the user uuid of this commerce cart item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceCartItem.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCartItemWrapper)) {
			return false;
		}

		CommerceCartItemWrapper commerceCartItemWrapper = (CommerceCartItemWrapper)obj;

		if (Objects.equals(_commerceCartItem,
					commerceCartItemWrapper._commerceCartItem)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceCartItem getWrappedModel() {
		return _commerceCartItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceCartItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceCartItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceCartItem.resetOriginalValues();
	}

	private final CommerceCartItem _commerceCartItem;
}