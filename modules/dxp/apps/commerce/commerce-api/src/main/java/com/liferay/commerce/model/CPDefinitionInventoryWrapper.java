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
 * This class is a wrapper for {@link CPDefinitionInventory}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventory
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryWrapper implements CPDefinitionInventory,
	ModelWrapper<CPDefinitionInventory> {
	public CPDefinitionInventoryWrapper(
		CPDefinitionInventory cpDefinitionInventory) {
		_cpDefinitionInventory = cpDefinitionInventory;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionInventory.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionInventory.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionInventoryId", getCPDefinitionInventoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPDefinitionInventoryEngine",
			getCPDefinitionInventoryEngine());
		attributes.put("lowStockActivity", getLowStockActivity());
		attributes.put("displayAvailability", isDisplayAvailability());
		attributes.put("displayStockQuantity", isDisplayStockQuantity());
		attributes.put("minStockQuantity", getMinStockQuantity());
		attributes.put("backOrders", isBackOrders());
		attributes.put("minOrderQuantity", getMinOrderQuantity());
		attributes.put("maxOrderQuantity", getMaxOrderQuantity());
		attributes.put("allowedOrderQuantities", getAllowedOrderQuantities());
		attributes.put("multipleOrderQuantity", getMultipleOrderQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionInventoryId = (Long)attributes.get(
				"CPDefinitionInventoryId");

		if (CPDefinitionInventoryId != null) {
			setCPDefinitionInventoryId(CPDefinitionInventoryId);
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

		String CPDefinitionInventoryEngine = (String)attributes.get(
				"CPDefinitionInventoryEngine");

		if (CPDefinitionInventoryEngine != null) {
			setCPDefinitionInventoryEngine(CPDefinitionInventoryEngine);
		}

		String lowStockActivity = (String)attributes.get("lowStockActivity");

		if (lowStockActivity != null) {
			setLowStockActivity(lowStockActivity);
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

		Integer minOrderQuantity = (Integer)attributes.get("minOrderQuantity");

		if (minOrderQuantity != null) {
			setMinOrderQuantity(minOrderQuantity);
		}

		Integer maxOrderQuantity = (Integer)attributes.get("maxOrderQuantity");

		if (maxOrderQuantity != null) {
			setMaxOrderQuantity(maxOrderQuantity);
		}

		String allowedOrderQuantities = (String)attributes.get(
				"allowedOrderQuantities");

		if (allowedOrderQuantities != null) {
			setAllowedOrderQuantities(allowedOrderQuantities);
		}

		Integer multipleOrderQuantity = (Integer)attributes.get(
				"multipleOrderQuantity");

		if (multipleOrderQuantity != null) {
			setMultipleOrderQuantity(multipleOrderQuantity);
		}
	}

	@Override
	public Object clone() {
		return new CPDefinitionInventoryWrapper((CPDefinitionInventory)_cpDefinitionInventory.clone());
	}

	@Override
	public int compareTo(CPDefinitionInventory cpDefinitionInventory) {
		return _cpDefinitionInventory.compareTo(cpDefinitionInventory);
	}

	/**
	* Returns the allowed order quantities of this cp definition inventory.
	*
	* @return the allowed order quantities of this cp definition inventory
	*/
	@Override
	public String getAllowedOrderQuantities() {
		return _cpDefinitionInventory.getAllowedOrderQuantities();
	}

	/**
	* Returns the back orders of this cp definition inventory.
	*
	* @return the back orders of this cp definition inventory
	*/
	@Override
	public boolean getBackOrders() {
		return _cpDefinitionInventory.getBackOrders();
	}

	/**
	* Returns the company ID of this cp definition inventory.
	*
	* @return the company ID of this cp definition inventory
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionInventory.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this cp definition inventory.
	*
	* @return the cp definition ID of this cp definition inventory
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionInventory.getCPDefinitionId();
	}

	/**
	* Returns the cp definition inventory engine of this cp definition inventory.
	*
	* @return the cp definition inventory engine of this cp definition inventory
	*/
	@Override
	public String getCPDefinitionInventoryEngine() {
		return _cpDefinitionInventory.getCPDefinitionInventoryEngine();
	}

	/**
	* Returns the cp definition inventory ID of this cp definition inventory.
	*
	* @return the cp definition inventory ID of this cp definition inventory
	*/
	@Override
	public long getCPDefinitionInventoryId() {
		return _cpDefinitionInventory.getCPDefinitionInventoryId();
	}

	/**
	* Returns the create date of this cp definition inventory.
	*
	* @return the create date of this cp definition inventory
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionInventory.getCreateDate();
	}

	/**
	* Returns the display availability of this cp definition inventory.
	*
	* @return the display availability of this cp definition inventory
	*/
	@Override
	public boolean getDisplayAvailability() {
		return _cpDefinitionInventory.getDisplayAvailability();
	}

	/**
	* Returns the display stock quantity of this cp definition inventory.
	*
	* @return the display stock quantity of this cp definition inventory
	*/
	@Override
	public boolean getDisplayStockQuantity() {
		return _cpDefinitionInventory.getDisplayStockQuantity();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionInventory.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp definition inventory.
	*
	* @return the group ID of this cp definition inventory
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionInventory.getGroupId();
	}

	/**
	* Returns the low stock activity of this cp definition inventory.
	*
	* @return the low stock activity of this cp definition inventory
	*/
	@Override
	public String getLowStockActivity() {
		return _cpDefinitionInventory.getLowStockActivity();
	}

	/**
	* Returns the max order quantity of this cp definition inventory.
	*
	* @return the max order quantity of this cp definition inventory
	*/
	@Override
	public int getMaxOrderQuantity() {
		return _cpDefinitionInventory.getMaxOrderQuantity();
	}

	/**
	* Returns the min order quantity of this cp definition inventory.
	*
	* @return the min order quantity of this cp definition inventory
	*/
	@Override
	public int getMinOrderQuantity() {
		return _cpDefinitionInventory.getMinOrderQuantity();
	}

	/**
	* Returns the min stock quantity of this cp definition inventory.
	*
	* @return the min stock quantity of this cp definition inventory
	*/
	@Override
	public int getMinStockQuantity() {
		return _cpDefinitionInventory.getMinStockQuantity();
	}

	/**
	* Returns the modified date of this cp definition inventory.
	*
	* @return the modified date of this cp definition inventory
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionInventory.getModifiedDate();
	}

	/**
	* Returns the multiple order quantity of this cp definition inventory.
	*
	* @return the multiple order quantity of this cp definition inventory
	*/
	@Override
	public int getMultipleOrderQuantity() {
		return _cpDefinitionInventory.getMultipleOrderQuantity();
	}

	/**
	* Returns the primary key of this cp definition inventory.
	*
	* @return the primary key of this cp definition inventory
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionInventory.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionInventory.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp definition inventory.
	*
	* @return the user ID of this cp definition inventory
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionInventory.getUserId();
	}

	/**
	* Returns the user name of this cp definition inventory.
	*
	* @return the user name of this cp definition inventory
	*/
	@Override
	public String getUserName() {
		return _cpDefinitionInventory.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition inventory.
	*
	* @return the user uuid of this cp definition inventory
	*/
	@Override
	public String getUserUuid() {
		return _cpDefinitionInventory.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition inventory.
	*
	* @return the uuid of this cp definition inventory
	*/
	@Override
	public String getUuid() {
		return _cpDefinitionInventory.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionInventory.hashCode();
	}

	/**
	* Returns <code>true</code> if this cp definition inventory is back orders.
	*
	* @return <code>true</code> if this cp definition inventory is back orders; <code>false</code> otherwise
	*/
	@Override
	public boolean isBackOrders() {
		return _cpDefinitionInventory.isBackOrders();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionInventory.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition inventory is display availability.
	*
	* @return <code>true</code> if this cp definition inventory is display availability; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayAvailability() {
		return _cpDefinitionInventory.isDisplayAvailability();
	}

	/**
	* Returns <code>true</code> if this cp definition inventory is display stock quantity.
	*
	* @return <code>true</code> if this cp definition inventory is display stock quantity; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayStockQuantity() {
		return _cpDefinitionInventory.isDisplayStockQuantity();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionInventory.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionInventory.isNew();
	}

	@Override
	public void persist() {
		_cpDefinitionInventory.persist();
	}

	/**
	* Sets the allowed order quantities of this cp definition inventory.
	*
	* @param allowedOrderQuantities the allowed order quantities of this cp definition inventory
	*/
	@Override
	public void setAllowedOrderQuantities(String allowedOrderQuantities) {
		_cpDefinitionInventory.setAllowedOrderQuantities(allowedOrderQuantities);
	}

	/**
	* Sets whether this cp definition inventory is back orders.
	*
	* @param backOrders the back orders of this cp definition inventory
	*/
	@Override
	public void setBackOrders(boolean backOrders) {
		_cpDefinitionInventory.setBackOrders(backOrders);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionInventory.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition inventory.
	*
	* @param companyId the company ID of this cp definition inventory
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionInventory.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this cp definition inventory.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition inventory
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionInventory.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp definition inventory engine of this cp definition inventory.
	*
	* @param CPDefinitionInventoryEngine the cp definition inventory engine of this cp definition inventory
	*/
	@Override
	public void setCPDefinitionInventoryEngine(
		String CPDefinitionInventoryEngine) {
		_cpDefinitionInventory.setCPDefinitionInventoryEngine(CPDefinitionInventoryEngine);
	}

	/**
	* Sets the cp definition inventory ID of this cp definition inventory.
	*
	* @param CPDefinitionInventoryId the cp definition inventory ID of this cp definition inventory
	*/
	@Override
	public void setCPDefinitionInventoryId(long CPDefinitionInventoryId) {
		_cpDefinitionInventory.setCPDefinitionInventoryId(CPDefinitionInventoryId);
	}

	/**
	* Sets the create date of this cp definition inventory.
	*
	* @param createDate the create date of this cp definition inventory
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionInventory.setCreateDate(createDate);
	}

	/**
	* Sets whether this cp definition inventory is display availability.
	*
	* @param displayAvailability the display availability of this cp definition inventory
	*/
	@Override
	public void setDisplayAvailability(boolean displayAvailability) {
		_cpDefinitionInventory.setDisplayAvailability(displayAvailability);
	}

	/**
	* Sets whether this cp definition inventory is display stock quantity.
	*
	* @param displayStockQuantity the display stock quantity of this cp definition inventory
	*/
	@Override
	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		_cpDefinitionInventory.setDisplayStockQuantity(displayStockQuantity);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionInventory.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionInventory.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionInventory.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp definition inventory.
	*
	* @param groupId the group ID of this cp definition inventory
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionInventory.setGroupId(groupId);
	}

	/**
	* Sets the low stock activity of this cp definition inventory.
	*
	* @param lowStockActivity the low stock activity of this cp definition inventory
	*/
	@Override
	public void setLowStockActivity(String lowStockActivity) {
		_cpDefinitionInventory.setLowStockActivity(lowStockActivity);
	}

	/**
	* Sets the max order quantity of this cp definition inventory.
	*
	* @param maxOrderQuantity the max order quantity of this cp definition inventory
	*/
	@Override
	public void setMaxOrderQuantity(int maxOrderQuantity) {
		_cpDefinitionInventory.setMaxOrderQuantity(maxOrderQuantity);
	}

	/**
	* Sets the min order quantity of this cp definition inventory.
	*
	* @param minOrderQuantity the min order quantity of this cp definition inventory
	*/
	@Override
	public void setMinOrderQuantity(int minOrderQuantity) {
		_cpDefinitionInventory.setMinOrderQuantity(minOrderQuantity);
	}

	/**
	* Sets the min stock quantity of this cp definition inventory.
	*
	* @param minStockQuantity the min stock quantity of this cp definition inventory
	*/
	@Override
	public void setMinStockQuantity(int minStockQuantity) {
		_cpDefinitionInventory.setMinStockQuantity(minStockQuantity);
	}

	/**
	* Sets the modified date of this cp definition inventory.
	*
	* @param modifiedDate the modified date of this cp definition inventory
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionInventory.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the multiple order quantity of this cp definition inventory.
	*
	* @param multipleOrderQuantity the multiple order quantity of this cp definition inventory
	*/
	@Override
	public void setMultipleOrderQuantity(int multipleOrderQuantity) {
		_cpDefinitionInventory.setMultipleOrderQuantity(multipleOrderQuantity);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionInventory.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition inventory.
	*
	* @param primaryKey the primary key of this cp definition inventory
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionInventory.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionInventory.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp definition inventory.
	*
	* @param userId the user ID of this cp definition inventory
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionInventory.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition inventory.
	*
	* @param userName the user name of this cp definition inventory
	*/
	@Override
	public void setUserName(String userName) {
		_cpDefinitionInventory.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition inventory.
	*
	* @param userUuid the user uuid of this cp definition inventory
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpDefinitionInventory.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition inventory.
	*
	* @param uuid the uuid of this cp definition inventory
	*/
	@Override
	public void setUuid(String uuid) {
		_cpDefinitionInventory.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionInventory> toCacheModel() {
		return _cpDefinitionInventory.toCacheModel();
	}

	@Override
	public CPDefinitionInventory toEscapedModel() {
		return new CPDefinitionInventoryWrapper(_cpDefinitionInventory.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpDefinitionInventory.toString();
	}

	@Override
	public CPDefinitionInventory toUnescapedModel() {
		return new CPDefinitionInventoryWrapper(_cpDefinitionInventory.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpDefinitionInventory.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionInventoryWrapper)) {
			return false;
		}

		CPDefinitionInventoryWrapper cpDefinitionInventoryWrapper = (CPDefinitionInventoryWrapper)obj;

		if (Objects.equals(_cpDefinitionInventory,
					cpDefinitionInventoryWrapper._cpDefinitionInventory)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionInventory.getStagedModelType();
	}

	@Override
	public CPDefinitionInventory getWrappedModel() {
		return _cpDefinitionInventory;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionInventory.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionInventory.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionInventory.resetOriginalValues();
	}

	private final CPDefinitionInventory _cpDefinitionInventory;
}