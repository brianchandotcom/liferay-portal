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
 * This class is a wrapper for {@link CommerceInventory}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventory
 * @generated
 */
@ProviderType
public class CommerceInventoryWrapper implements CommerceInventory,
	ModelWrapper<CommerceInventory> {
	public CommerceInventoryWrapper(CommerceInventory commerceInventory) {
		_commerceInventory = commerceInventory;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceInventory.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceInventory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceInventoryId", getCommerceInventoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("commerceInventoryEngine", getCommerceInventoryEngine());
		attributes.put("displayAvailability", getDisplayAvailability());
		attributes.put("displayStockQuantity", getDisplayStockQuantity());
		attributes.put("minStockQuantity", getMinStockQuantity());
		attributes.put("backOrders", getBackOrders());
		attributes.put("minCartQuantity", getMinCartQuantity());
		attributes.put("maxCartQuantity", getMaxCartQuantity());
		attributes.put("allowedCartQuantities", getAllowedCartQuantities());
		attributes.put("multipleCartQuantity", getMultipleCartQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceInventoryId = (Long)attributes.get("commerceInventoryId");

		if (commerceInventoryId != null) {
			setCommerceInventoryId(commerceInventoryId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		String commerceInventoryEngine = (String)attributes.get(
				"commerceInventoryEngine");

		if (commerceInventoryEngine != null) {
			setCommerceInventoryEngine(commerceInventoryEngine);
		}

		Boolean displayAvailability = (Boolean)attributes.get(
				"displayAvailability");

		if (displayAvailability != null) {
			setDisplayAvailability(displayAvailability);
		}

		Boolean displayStockQuantity = (Boolean)attributes.get(
				"displayStockQuantity");

		if (displayStockQuantity != null) {
			setDisplayStockQuantity(displayStockQuantity);
		}

		Integer minStockQuantity = (Integer)attributes.get("minStockQuantity");

		if (minStockQuantity != null) {
			setMinStockQuantity(minStockQuantity);
		}

		Boolean backOrders = (Boolean)attributes.get("backOrders");

		if (backOrders != null) {
			setBackOrders(backOrders);
		}

		Integer minCartQuantity = (Integer)attributes.get("minCartQuantity");

		if (minCartQuantity != null) {
			setMinCartQuantity(minCartQuantity);
		}

		Integer maxCartQuantity = (Integer)attributes.get("maxCartQuantity");

		if (maxCartQuantity != null) {
			setMaxCartQuantity(maxCartQuantity);
		}

		String allowedCartQuantities = (String)attributes.get(
				"allowedCartQuantities");

		if (allowedCartQuantities != null) {
			setAllowedCartQuantities(allowedCartQuantities);
		}

		Integer multipleCartQuantity = (Integer)attributes.get(
				"multipleCartQuantity");

		if (multipleCartQuantity != null) {
			setMultipleCartQuantity(multipleCartQuantity);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceInventoryWrapper((CommerceInventory)_commerceInventory.clone());
	}

	@Override
	public int compareTo(CommerceInventory commerceInventory) {
		return _commerceInventory.compareTo(commerceInventory);
	}

	/**
	* Returns the allowed cart quantities of this commerce inventory.
	*
	* @return the allowed cart quantities of this commerce inventory
	*/
	@Override
	public java.lang.String getAllowedCartQuantities() {
		return _commerceInventory.getAllowedCartQuantities();
	}

	/**
	* Returns the back orders of this commerce inventory.
	*
	* @return the back orders of this commerce inventory
	*/
	@Override
	public boolean getBackOrders() {
		return _commerceInventory.getBackOrders();
	}

	/**
	* Returns the commerce inventory engine of this commerce inventory.
	*
	* @return the commerce inventory engine of this commerce inventory
	*/
	@Override
	public java.lang.String getCommerceInventoryEngine() {
		return _commerceInventory.getCommerceInventoryEngine();
	}

	/**
	* Returns the commerce inventory ID of this commerce inventory.
	*
	* @return the commerce inventory ID of this commerce inventory
	*/
	@Override
	public long getCommerceInventoryId() {
		return _commerceInventory.getCommerceInventoryId();
	}

	/**
	* Returns the company ID of this commerce inventory.
	*
	* @return the company ID of this commerce inventory
	*/
	@Override
	public long getCompanyId() {
		return _commerceInventory.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this commerce inventory.
	*
	* @return the cp definition ID of this commerce inventory
	*/
	@Override
	public long getCPDefinitionId() {
		return _commerceInventory.getCPDefinitionId();
	}

	/**
	* Returns the create date of this commerce inventory.
	*
	* @return the create date of this commerce inventory
	*/
	@Override
	public Date getCreateDate() {
		return _commerceInventory.getCreateDate();
	}

	/**
	* Returns the display availability of this commerce inventory.
	*
	* @return the display availability of this commerce inventory
	*/
	@Override
	public boolean getDisplayAvailability() {
		return _commerceInventory.getDisplayAvailability();
	}

	/**
	* Returns the display stock quantity of this commerce inventory.
	*
	* @return the display stock quantity of this commerce inventory
	*/
	@Override
	public boolean getDisplayStockQuantity() {
		return _commerceInventory.getDisplayStockQuantity();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceInventory.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce inventory.
	*
	* @return the group ID of this commerce inventory
	*/
	@Override
	public long getGroupId() {
		return _commerceInventory.getGroupId();
	}

	/**
	* Returns the max cart quantity of this commerce inventory.
	*
	* @return the max cart quantity of this commerce inventory
	*/
	@Override
	public int getMaxCartQuantity() {
		return _commerceInventory.getMaxCartQuantity();
	}

	/**
	* Returns the min cart quantity of this commerce inventory.
	*
	* @return the min cart quantity of this commerce inventory
	*/
	@Override
	public int getMinCartQuantity() {
		return _commerceInventory.getMinCartQuantity();
	}

	/**
	* Returns the min stock quantity of this commerce inventory.
	*
	* @return the min stock quantity of this commerce inventory
	*/
	@Override
	public int getMinStockQuantity() {
		return _commerceInventory.getMinStockQuantity();
	}

	/**
	* Returns the modified date of this commerce inventory.
	*
	* @return the modified date of this commerce inventory
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceInventory.getModifiedDate();
	}

	/**
	* Returns the multiple cart quantity of this commerce inventory.
	*
	* @return the multiple cart quantity of this commerce inventory
	*/
	@Override
	public int getMultipleCartQuantity() {
		return _commerceInventory.getMultipleCartQuantity();
	}

	/**
	* Returns the primary key of this commerce inventory.
	*
	* @return the primary key of this commerce inventory
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceInventory.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceInventory.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce inventory.
	*
	* @return the user ID of this commerce inventory
	*/
	@Override
	public long getUserId() {
		return _commerceInventory.getUserId();
	}

	/**
	* Returns the user name of this commerce inventory.
	*
	* @return the user name of this commerce inventory
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceInventory.getUserName();
	}

	/**
	* Returns the user uuid of this commerce inventory.
	*
	* @return the user uuid of this commerce inventory
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceInventory.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce inventory.
	*
	* @return the uuid of this commerce inventory
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceInventory.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceInventory.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce inventory is back orders.
	*
	* @return <code>true</code> if this commerce inventory is back orders; <code>false</code> otherwise
	*/
	@Override
	public boolean isBackOrders() {
		return _commerceInventory.isBackOrders();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceInventory.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this commerce inventory is display availability.
	*
	* @return <code>true</code> if this commerce inventory is display availability; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayAvailability() {
		return _commerceInventory.isDisplayAvailability();
	}

	/**
	* Returns <code>true</code> if this commerce inventory is display stock quantity.
	*
	* @return <code>true</code> if this commerce inventory is display stock quantity; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayStockQuantity() {
		return _commerceInventory.isDisplayStockQuantity();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceInventory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceInventory.isNew();
	}

	@Override
	public void persist() {
		_commerceInventory.persist();
	}

	/**
	* Sets the allowed cart quantities of this commerce inventory.
	*
	* @param allowedCartQuantities the allowed cart quantities of this commerce inventory
	*/
	@Override
	public void setAllowedCartQuantities(java.lang.String allowedCartQuantities) {
		_commerceInventory.setAllowedCartQuantities(allowedCartQuantities);
	}

	/**
	* Sets whether this commerce inventory is back orders.
	*
	* @param backOrders the back orders of this commerce inventory
	*/
	@Override
	public void setBackOrders(boolean backOrders) {
		_commerceInventory.setBackOrders(backOrders);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceInventory.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce inventory engine of this commerce inventory.
	*
	* @param commerceInventoryEngine the commerce inventory engine of this commerce inventory
	*/
	@Override
	public void setCommerceInventoryEngine(
		java.lang.String commerceInventoryEngine) {
		_commerceInventory.setCommerceInventoryEngine(commerceInventoryEngine);
	}

	/**
	* Sets the commerce inventory ID of this commerce inventory.
	*
	* @param commerceInventoryId the commerce inventory ID of this commerce inventory
	*/
	@Override
	public void setCommerceInventoryId(long commerceInventoryId) {
		_commerceInventory.setCommerceInventoryId(commerceInventoryId);
	}

	/**
	* Sets the company ID of this commerce inventory.
	*
	* @param companyId the company ID of this commerce inventory
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceInventory.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this commerce inventory.
	*
	* @param CPDefinitionId the cp definition ID of this commerce inventory
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_commerceInventory.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this commerce inventory.
	*
	* @param createDate the create date of this commerce inventory
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceInventory.setCreateDate(createDate);
	}

	/**
	* Sets whether this commerce inventory is display availability.
	*
	* @param displayAvailability the display availability of this commerce inventory
	*/
	@Override
	public void setDisplayAvailability(boolean displayAvailability) {
		_commerceInventory.setDisplayAvailability(displayAvailability);
	}

	/**
	* Sets whether this commerce inventory is display stock quantity.
	*
	* @param displayStockQuantity the display stock quantity of this commerce inventory
	*/
	@Override
	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		_commerceInventory.setDisplayStockQuantity(displayStockQuantity);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceInventory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceInventory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceInventory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce inventory.
	*
	* @param groupId the group ID of this commerce inventory
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceInventory.setGroupId(groupId);
	}

	/**
	* Sets the max cart quantity of this commerce inventory.
	*
	* @param maxCartQuantity the max cart quantity of this commerce inventory
	*/
	@Override
	public void setMaxCartQuantity(int maxCartQuantity) {
		_commerceInventory.setMaxCartQuantity(maxCartQuantity);
	}

	/**
	* Sets the min cart quantity of this commerce inventory.
	*
	* @param minCartQuantity the min cart quantity of this commerce inventory
	*/
	@Override
	public void setMinCartQuantity(int minCartQuantity) {
		_commerceInventory.setMinCartQuantity(minCartQuantity);
	}

	/**
	* Sets the min stock quantity of this commerce inventory.
	*
	* @param minStockQuantity the min stock quantity of this commerce inventory
	*/
	@Override
	public void setMinStockQuantity(int minStockQuantity) {
		_commerceInventory.setMinStockQuantity(minStockQuantity);
	}

	/**
	* Sets the modified date of this commerce inventory.
	*
	* @param modifiedDate the modified date of this commerce inventory
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceInventory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the multiple cart quantity of this commerce inventory.
	*
	* @param multipleCartQuantity the multiple cart quantity of this commerce inventory
	*/
	@Override
	public void setMultipleCartQuantity(int multipleCartQuantity) {
		_commerceInventory.setMultipleCartQuantity(multipleCartQuantity);
	}

	@Override
	public void setNew(boolean n) {
		_commerceInventory.setNew(n);
	}

	/**
	* Sets the primary key of this commerce inventory.
	*
	* @param primaryKey the primary key of this commerce inventory
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceInventory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceInventory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce inventory.
	*
	* @param userId the user ID of this commerce inventory
	*/
	@Override
	public void setUserId(long userId) {
		_commerceInventory.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce inventory.
	*
	* @param userName the user name of this commerce inventory
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceInventory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce inventory.
	*
	* @param userUuid the user uuid of this commerce inventory
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceInventory.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce inventory.
	*
	* @param uuid the uuid of this commerce inventory
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceInventory.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceInventory> toCacheModel() {
		return _commerceInventory.toCacheModel();
	}

	@Override
	public CommerceInventory toEscapedModel() {
		return new CommerceInventoryWrapper(_commerceInventory.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceInventory.toString();
	}

	@Override
	public CommerceInventory toUnescapedModel() {
		return new CommerceInventoryWrapper(_commerceInventory.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceInventory.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceInventoryWrapper)) {
			return false;
		}

		CommerceInventoryWrapper commerceInventoryWrapper = (CommerceInventoryWrapper)obj;

		if (Objects.equals(_commerceInventory,
					commerceInventoryWrapper._commerceInventory)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceInventory.getStagedModelType();
	}

	@Override
	public CommerceInventory getWrappedModel() {
		return _commerceInventory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceInventory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceInventory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceInventory.resetOriginalValues();
	}

	private final CommerceInventory _commerceInventory;
}