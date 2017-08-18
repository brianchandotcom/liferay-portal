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

package com.liferay.commerce.model;

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
 * This class is a wrapper for {@link CommerceOrderItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItem
 * @generated
 */
@ProviderType
public class CommerceOrderItemWrapper implements CommerceOrderItem,
	ModelWrapper<CommerceOrderItem> {
	public CommerceOrderItemWrapper(CommerceOrderItem commerceOrderItem) {
		_commerceOrderItem = commerceOrderItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrderItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceOrderItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderId", getCommerceOrderId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("quantity", getQuantity());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
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

		Long commerceOrderId = (Long)attributes.get("commerceOrderId");

		if (commerceOrderId != null) {
			setCommerceOrderId(commerceOrderId);
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
	public java.lang.Object clone() {
		return new CommerceOrderItemWrapper((CommerceOrderItem)_commerceOrderItem.clone());
	}

	@Override
	public int compareTo(CommerceOrderItem commerceOrderItem) {
		return _commerceOrderItem.compareTo(commerceOrderItem);
	}

	/**
	* Returns the commerce order ID of this commerce order item.
	*
	* @return the commerce order ID of this commerce order item
	*/
	@Override
	public long getCommerceOrderId() {
		return _commerceOrderItem.getCommerceOrderId();
	}

	/**
	* Returns the commerce order item ID of this commerce order item.
	*
	* @return the commerce order item ID of this commerce order item
	*/
	@Override
	public long getCommerceOrderItemId() {
		return _commerceOrderItem.getCommerceOrderItemId();
	}

	/**
	* Returns the company ID of this commerce order item.
	*
	* @return the company ID of this commerce order item
	*/
	@Override
	public long getCompanyId() {
		return _commerceOrderItem.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this commerce order item.
	*
	* @return the cp definition ID of this commerce order item
	*/
	@Override
	public long getCPDefinitionId() {
		return _commerceOrderItem.getCPDefinitionId();
	}

	/**
	* Returns the cp instance ID of this commerce order item.
	*
	* @return the cp instance ID of this commerce order item
	*/
	@Override
	public long getCPInstanceId() {
		return _commerceOrderItem.getCPInstanceId();
	}

	/**
	* Returns the create date of this commerce order item.
	*
	* @return the create date of this commerce order item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceOrderItem.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceOrderItem.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce order item.
	*
	* @return the group ID of this commerce order item
	*/
	@Override
	public long getGroupId() {
		return _commerceOrderItem.getGroupId();
	}

	/**
	* Returns the json of this commerce order item.
	*
	* @return the json of this commerce order item
	*/
	@Override
	public java.lang.String getJson() {
		return _commerceOrderItem.getJson();
	}

	/**
	* Returns the modified date of this commerce order item.
	*
	* @return the modified date of this commerce order item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceOrderItem.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce order item.
	*
	* @return the primary key of this commerce order item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceOrderItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceOrderItem.getPrimaryKeyObj();
	}

	/**
	* Returns the quantity of this commerce order item.
	*
	* @return the quantity of this commerce order item
	*/
	@Override
	public int getQuantity() {
		return _commerceOrderItem.getQuantity();
	}

	/**
	* Returns the user ID of this commerce order item.
	*
	* @return the user ID of this commerce order item
	*/
	@Override
	public long getUserId() {
		return _commerceOrderItem.getUserId();
	}

	/**
	* Returns the user name of this commerce order item.
	*
	* @return the user name of this commerce order item
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceOrderItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce order item.
	*
	* @return the user uuid of this commerce order item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceOrderItem.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceOrderItem.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceOrderItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceOrderItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceOrderItem.isNew();
	}

	@Override
	public void persist() {
		_commerceOrderItem.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceOrderItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce order ID of this commerce order item.
	*
	* @param commerceOrderId the commerce order ID of this commerce order item
	*/
	@Override
	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderItem.setCommerceOrderId(commerceOrderId);
	}

	/**
	* Sets the commerce order item ID of this commerce order item.
	*
	* @param commerceOrderItemId the commerce order item ID of this commerce order item
	*/
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceOrderItem.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	* Sets the company ID of this commerce order item.
	*
	* @param companyId the company ID of this commerce order item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceOrderItem.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this commerce order item.
	*
	* @param CPDefinitionId the cp definition ID of this commerce order item
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_commerceOrderItem.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp instance ID of this commerce order item.
	*
	* @param CPInstanceId the cp instance ID of this commerce order item
	*/
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_commerceOrderItem.setCPInstanceId(CPInstanceId);
	}

	/**
	* Sets the create date of this commerce order item.
	*
	* @param createDate the create date of this commerce order item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceOrderItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceOrderItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceOrderItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce order item.
	*
	* @param groupId the group ID of this commerce order item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceOrderItem.setGroupId(groupId);
	}

	/**
	* Sets the json of this commerce order item.
	*
	* @param json the json of this commerce order item
	*/
	@Override
	public void setJson(java.lang.String json) {
		_commerceOrderItem.setJson(json);
	}

	/**
	* Sets the modified date of this commerce order item.
	*
	* @param modifiedDate the modified date of this commerce order item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceOrderItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceOrderItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce order item.
	*
	* @param primaryKey the primary key of this commerce order item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceOrderItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceOrderItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the quantity of this commerce order item.
	*
	* @param quantity the quantity of this commerce order item
	*/
	@Override
	public void setQuantity(int quantity) {
		_commerceOrderItem.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this commerce order item.
	*
	* @param userId the user ID of this commerce order item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceOrderItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce order item.
	*
	* @param userName the user name of this commerce order item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceOrderItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce order item.
	*
	* @param userUuid the user uuid of this commerce order item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceOrderItem.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceOrderItem> toCacheModel() {
		return _commerceOrderItem.toCacheModel();
	}

	@Override
	public CommerceOrderItem toEscapedModel() {
		return new CommerceOrderItemWrapper(_commerceOrderItem.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceOrderItem.toString();
	}

	@Override
	public CommerceOrderItem toUnescapedModel() {
		return new CommerceOrderItemWrapper(_commerceOrderItem.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceOrderItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderItemWrapper)) {
			return false;
		}

		CommerceOrderItemWrapper commerceOrderItemWrapper = (CommerceOrderItemWrapper)obj;

		if (Objects.equals(_commerceOrderItem,
					commerceOrderItemWrapper._commerceOrderItem)) {
			return true;
		}

		return false;
	}

	@Override
	public CommerceOrderItem getWrappedModel() {
		return _commerceOrderItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceOrderItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceOrderItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceOrderItem.resetOriginalValues();
	}

	private final CommerceOrderItem _commerceOrderItem;
}