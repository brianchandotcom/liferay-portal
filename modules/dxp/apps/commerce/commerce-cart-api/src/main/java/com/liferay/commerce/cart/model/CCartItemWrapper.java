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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CCartItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CCartItem
 * @generated
 */
@ProviderType
public class CCartItemWrapper implements CCartItem, ModelWrapper<CCartItem> {
	public CCartItemWrapper(CCartItem cCartItem) {
		_cCartItem = cCartItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CCartItem.class;
	}

	@Override
	public String getModelClassName() {
		return CCartItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CCartItemId", getCCartItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CCartId", getCCartId());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("quantity", getQuantity());
		attributes.put("json", getJson());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CCartItemId = (Long)attributes.get("CCartItemId");

		if (CCartItemId != null) {
			setCCartItemId(CCartItemId);
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

		Long CCartId = (Long)attributes.get("CCartId");

		if (CCartId != null) {
			setCCartId(CCartId);
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
	public CCartItem toEscapedModel() {
		return new CCartItemWrapper(_cCartItem.toEscapedModel());
	}

	@Override
	public CCartItem toUnescapedModel() {
		return new CCartItemWrapper(_cCartItem.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cCartItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cCartItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cCartItem.isNew();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition() {
		return _cCartItem.getCPDefinition();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cCartItem.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CCartItem> toCacheModel() {
		return _cCartItem.toCacheModel();
	}

	@Override
	public int compareTo(CCartItem cCartItem) {
		return _cCartItem.compareTo(cCartItem);
	}

	/**
	* Returns the quantity of this c cart item.
	*
	* @return the quantity of this c cart item
	*/
	@Override
	public int getQuantity() {
		return _cCartItem.getQuantity();
	}

	@Override
	public int hashCode() {
		return _cCartItem.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cCartItem.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CCartItemWrapper((CCartItem)_cCartItem.clone());
	}

	/**
	* Returns the json of this c cart item.
	*
	* @return the json of this c cart item
	*/
	@Override
	public java.lang.String getJson() {
		return _cCartItem.getJson();
	}

	/**
	* Returns the user name of this c cart item.
	*
	* @return the user name of this c cart item
	*/
	@Override
	public java.lang.String getUserName() {
		return _cCartItem.getUserName();
	}

	/**
	* Returns the user uuid of this c cart item.
	*
	* @return the user uuid of this c cart item
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cCartItem.getUserUuid();
	}

	/**
	* Returns the uuid of this c cart item.
	*
	* @return the uuid of this c cart item
	*/
	@Override
	public java.lang.String getUuid() {
		return _cCartItem.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cCartItem.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cCartItem.toXmlString();
	}

	/**
	* Returns the create date of this c cart item.
	*
	* @return the create date of this c cart item
	*/
	@Override
	public Date getCreateDate() {
		return _cCartItem.getCreateDate();
	}

	/**
	* Returns the modified date of this c cart item.
	*
	* @return the modified date of this c cart item
	*/
	@Override
	public Date getModifiedDate() {
		return _cCartItem.getModifiedDate();
	}

	/**
	* Returns the c cart ID of this c cart item.
	*
	* @return the c cart ID of this c cart item
	*/
	@Override
	public long getCCartId() {
		return _cCartItem.getCCartId();
	}

	/**
	* Returns the c cart item ID of this c cart item.
	*
	* @return the c cart item ID of this c cart item
	*/
	@Override
	public long getCCartItemId() {
		return _cCartItem.getCCartItemId();
	}

	/**
	* Returns the cp definition ID of this c cart item.
	*
	* @return the cp definition ID of this c cart item
	*/
	@Override
	public long getCPDefinitionId() {
		return _cCartItem.getCPDefinitionId();
	}

	/**
	* Returns the cp instance ID of this c cart item.
	*
	* @return the cp instance ID of this c cart item
	*/
	@Override
	public long getCPInstanceId() {
		return _cCartItem.getCPInstanceId();
	}

	/**
	* Returns the company ID of this c cart item.
	*
	* @return the company ID of this c cart item
	*/
	@Override
	public long getCompanyId() {
		return _cCartItem.getCompanyId();
	}

	/**
	* Returns the group ID of this c cart item.
	*
	* @return the group ID of this c cart item
	*/
	@Override
	public long getGroupId() {
		return _cCartItem.getGroupId();
	}

	/**
	* Returns the primary key of this c cart item.
	*
	* @return the primary key of this c cart item
	*/
	@Override
	public long getPrimaryKey() {
		return _cCartItem.getPrimaryKey();
	}

	/**
	* Returns the user ID of this c cart item.
	*
	* @return the user ID of this c cart item
	*/
	@Override
	public long getUserId() {
		return _cCartItem.getUserId();
	}

	@Override
	public void persist() {
		_cCartItem.persist();
	}

	/**
	* Sets the c cart ID of this c cart item.
	*
	* @param CCartId the c cart ID of this c cart item
	*/
	@Override
	public void setCCartId(long CCartId) {
		_cCartItem.setCCartId(CCartId);
	}

	/**
	* Sets the c cart item ID of this c cart item.
	*
	* @param CCartItemId the c cart item ID of this c cart item
	*/
	@Override
	public void setCCartItemId(long CCartItemId) {
		_cCartItem.setCCartItemId(CCartItemId);
	}

	/**
	* Sets the cp definition ID of this c cart item.
	*
	* @param CPDefinitionId the cp definition ID of this c cart item
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cCartItem.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp instance ID of this c cart item.
	*
	* @param CPInstanceId the cp instance ID of this c cart item
	*/
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		_cCartItem.setCPInstanceId(CPInstanceId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cCartItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this c cart item.
	*
	* @param companyId the company ID of this c cart item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cCartItem.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this c cart item.
	*
	* @param createDate the create date of this c cart item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cCartItem.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cCartItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cCartItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cCartItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this c cart item.
	*
	* @param groupId the group ID of this c cart item
	*/
	@Override
	public void setGroupId(long groupId) {
		_cCartItem.setGroupId(groupId);
	}

	/**
	* Sets the json of this c cart item.
	*
	* @param json the json of this c cart item
	*/
	@Override
	public void setJson(java.lang.String json) {
		_cCartItem.setJson(json);
	}

	/**
	* Sets the modified date of this c cart item.
	*
	* @param modifiedDate the modified date of this c cart item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cCartItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cCartItem.setNew(n);
	}

	/**
	* Sets the primary key of this c cart item.
	*
	* @param primaryKey the primary key of this c cart item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cCartItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cCartItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the quantity of this c cart item.
	*
	* @param quantity the quantity of this c cart item
	*/
	@Override
	public void setQuantity(int quantity) {
		_cCartItem.setQuantity(quantity);
	}

	/**
	* Sets the user ID of this c cart item.
	*
	* @param userId the user ID of this c cart item
	*/
	@Override
	public void setUserId(long userId) {
		_cCartItem.setUserId(userId);
	}

	/**
	* Sets the user name of this c cart item.
	*
	* @param userName the user name of this c cart item
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cCartItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this c cart item.
	*
	* @param userUuid the user uuid of this c cart item
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cCartItem.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this c cart item.
	*
	* @param uuid the uuid of this c cart item
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cCartItem.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CCartItemWrapper)) {
			return false;
		}

		CCartItemWrapper cCartItemWrapper = (CCartItemWrapper)obj;

		if (Objects.equals(_cCartItem, cCartItemWrapper._cCartItem)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cCartItem.getStagedModelType();
	}

	@Override
	public CCartItem getWrappedModel() {
		return _cCartItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cCartItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cCartItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cCartItem.resetOriginalValues();
	}

	private final CCartItem _cCartItem;
}