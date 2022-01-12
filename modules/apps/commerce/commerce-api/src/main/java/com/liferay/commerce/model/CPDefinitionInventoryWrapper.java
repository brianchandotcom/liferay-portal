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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionInventory}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventory
 * @generated
 */
public class CPDefinitionInventoryWrapper
	extends BaseModelWrapper<CPDefinitionInventory>
	implements CPDefinitionInventory, ModelWrapper<CPDefinitionInventory> {

	public CPDefinitionInventoryWrapper(
		CPDefinitionInventory cpDefinitionInventory) {

		super(cpDefinitionInventory);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionInventoryId", getCPDefinitionInventoryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put(
			"CPDefinitionInventoryEngine", getCPDefinitionInventoryEngine());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public CPDefinitionInventory cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the allowed order quantities of this cp definition inventory.
	 *
	 * @return the allowed order quantities of this cp definition inventory
	 */
	@Override
	public String getAllowedOrderQuantities() {
		return model.getAllowedOrderQuantities();
	}

	@Override
	public int[] getAllowedOrderQuantitiesArray() {
		return model.getAllowedOrderQuantitiesArray();
	}

	/**
	 * Returns the back orders of this cp definition inventory.
	 *
	 * @return the back orders of this cp definition inventory
	 */
	@Override
	public boolean getBackOrders() {
		return model.getBackOrders();
	}

	/**
	 * Returns the company ID of this cp definition inventory.
	 *
	 * @return the company ID of this cp definition inventory
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp definition ID of this cp definition inventory.
	 *
	 * @return the cp definition ID of this cp definition inventory
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the cp definition inventory engine of this cp definition inventory.
	 *
	 * @return the cp definition inventory engine of this cp definition inventory
	 */
	@Override
	public String getCPDefinitionInventoryEngine() {
		return model.getCPDefinitionInventoryEngine();
	}

	/**
	 * Returns the cp definition inventory ID of this cp definition inventory.
	 *
	 * @return the cp definition inventory ID of this cp definition inventory
	 */
	@Override
	public long getCPDefinitionInventoryId() {
		return model.getCPDefinitionInventoryId();
	}

	/**
	 * Returns the create date of this cp definition inventory.
	 *
	 * @return the create date of this cp definition inventory
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp definition inventory.
	 *
	 * @return the ct collection ID of this cp definition inventory
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the display availability of this cp definition inventory.
	 *
	 * @return the display availability of this cp definition inventory
	 */
	@Override
	public boolean getDisplayAvailability() {
		return model.getDisplayAvailability();
	}

	/**
	 * Returns the display stock quantity of this cp definition inventory.
	 *
	 * @return the display stock quantity of this cp definition inventory
	 */
	@Override
	public boolean getDisplayStockQuantity() {
		return model.getDisplayStockQuantity();
	}

	/**
	 * Returns the group ID of this cp definition inventory.
	 *
	 * @return the group ID of this cp definition inventory
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the low stock activity of this cp definition inventory.
	 *
	 * @return the low stock activity of this cp definition inventory
	 */
	@Override
	public String getLowStockActivity() {
		return model.getLowStockActivity();
	}

	/**
	 * Returns the max order quantity of this cp definition inventory.
	 *
	 * @return the max order quantity of this cp definition inventory
	 */
	@Override
	public int getMaxOrderQuantity() {
		return model.getMaxOrderQuantity();
	}

	/**
	 * Returns the min order quantity of this cp definition inventory.
	 *
	 * @return the min order quantity of this cp definition inventory
	 */
	@Override
	public int getMinOrderQuantity() {
		return model.getMinOrderQuantity();
	}

	/**
	 * Returns the min stock quantity of this cp definition inventory.
	 *
	 * @return the min stock quantity of this cp definition inventory
	 */
	@Override
	public int getMinStockQuantity() {
		return model.getMinStockQuantity();
	}

	/**
	 * Returns the modified date of this cp definition inventory.
	 *
	 * @return the modified date of this cp definition inventory
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the multiple order quantity of this cp definition inventory.
	 *
	 * @return the multiple order quantity of this cp definition inventory
	 */
	@Override
	public int getMultipleOrderQuantity() {
		return model.getMultipleOrderQuantity();
	}

	/**
	 * Returns the mvcc version of this cp definition inventory.
	 *
	 * @return the mvcc version of this cp definition inventory
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp definition inventory.
	 *
	 * @return the primary key of this cp definition inventory
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp definition inventory.
	 *
	 * @return the user ID of this cp definition inventory
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition inventory.
	 *
	 * @return the user name of this cp definition inventory
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition inventory.
	 *
	 * @return the user uuid of this cp definition inventory
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp definition inventory.
	 *
	 * @return the uuid of this cp definition inventory
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this cp definition inventory is back orders.
	 *
	 * @return <code>true</code> if this cp definition inventory is back orders; <code>false</code> otherwise
	 */
	@Override
	public boolean isBackOrders() {
		return model.isBackOrders();
	}

	/**
	 * Returns <code>true</code> if this cp definition inventory is display availability.
	 *
	 * @return <code>true</code> if this cp definition inventory is display availability; <code>false</code> otherwise
	 */
	@Override
	public boolean isDisplayAvailability() {
		return model.isDisplayAvailability();
	}

	/**
	 * Returns <code>true</code> if this cp definition inventory is display stock quantity.
	 *
	 * @return <code>true</code> if this cp definition inventory is display stock quantity; <code>false</code> otherwise
	 */
	@Override
	public boolean isDisplayStockQuantity() {
		return model.isDisplayStockQuantity();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the allowed order quantities of this cp definition inventory.
	 *
	 * @param allowedOrderQuantities the allowed order quantities of this cp definition inventory
	 */
	@Override
	public void setAllowedOrderQuantities(String allowedOrderQuantities) {
		model.setAllowedOrderQuantities(allowedOrderQuantities);
	}

	/**
	 * Sets whether this cp definition inventory is back orders.
	 *
	 * @param backOrders the back orders of this cp definition inventory
	 */
	@Override
	public void setBackOrders(boolean backOrders) {
		model.setBackOrders(backOrders);
	}

	/**
	 * Sets the company ID of this cp definition inventory.
	 *
	 * @param companyId the company ID of this cp definition inventory
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this cp definition inventory.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition inventory
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the cp definition inventory engine of this cp definition inventory.
	 *
	 * @param CPDefinitionInventoryEngine the cp definition inventory engine of this cp definition inventory
	 */
	@Override
	public void setCPDefinitionInventoryEngine(
		String CPDefinitionInventoryEngine) {

		model.setCPDefinitionInventoryEngine(CPDefinitionInventoryEngine);
	}

	/**
	 * Sets the cp definition inventory ID of this cp definition inventory.
	 *
	 * @param CPDefinitionInventoryId the cp definition inventory ID of this cp definition inventory
	 */
	@Override
	public void setCPDefinitionInventoryId(long CPDefinitionInventoryId) {
		model.setCPDefinitionInventoryId(CPDefinitionInventoryId);
	}

	/**
	 * Sets the create date of this cp definition inventory.
	 *
	 * @param createDate the create date of this cp definition inventory
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp definition inventory.
	 *
	 * @param ctCollectionId the ct collection ID of this cp definition inventory
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets whether this cp definition inventory is display availability.
	 *
	 * @param displayAvailability the display availability of this cp definition inventory
	 */
	@Override
	public void setDisplayAvailability(boolean displayAvailability) {
		model.setDisplayAvailability(displayAvailability);
	}

	/**
	 * Sets whether this cp definition inventory is display stock quantity.
	 *
	 * @param displayStockQuantity the display stock quantity of this cp definition inventory
	 */
	@Override
	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		model.setDisplayStockQuantity(displayStockQuantity);
	}

	/**
	 * Sets the group ID of this cp definition inventory.
	 *
	 * @param groupId the group ID of this cp definition inventory
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the low stock activity of this cp definition inventory.
	 *
	 * @param lowStockActivity the low stock activity of this cp definition inventory
	 */
	@Override
	public void setLowStockActivity(String lowStockActivity) {
		model.setLowStockActivity(lowStockActivity);
	}

	/**
	 * Sets the max order quantity of this cp definition inventory.
	 *
	 * @param maxOrderQuantity the max order quantity of this cp definition inventory
	 */
	@Override
	public void setMaxOrderQuantity(int maxOrderQuantity) {
		model.setMaxOrderQuantity(maxOrderQuantity);
	}

	/**
	 * Sets the min order quantity of this cp definition inventory.
	 *
	 * @param minOrderQuantity the min order quantity of this cp definition inventory
	 */
	@Override
	public void setMinOrderQuantity(int minOrderQuantity) {
		model.setMinOrderQuantity(minOrderQuantity);
	}

	/**
	 * Sets the min stock quantity of this cp definition inventory.
	 *
	 * @param minStockQuantity the min stock quantity of this cp definition inventory
	 */
	@Override
	public void setMinStockQuantity(int minStockQuantity) {
		model.setMinStockQuantity(minStockQuantity);
	}

	/**
	 * Sets the modified date of this cp definition inventory.
	 *
	 * @param modifiedDate the modified date of this cp definition inventory
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the multiple order quantity of this cp definition inventory.
	 *
	 * @param multipleOrderQuantity the multiple order quantity of this cp definition inventory
	 */
	@Override
	public void setMultipleOrderQuantity(int multipleOrderQuantity) {
		model.setMultipleOrderQuantity(multipleOrderQuantity);
	}

	/**
	 * Sets the mvcc version of this cp definition inventory.
	 *
	 * @param mvccVersion the mvcc version of this cp definition inventory
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp definition inventory.
	 *
	 * @param primaryKey the primary key of this cp definition inventory
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp definition inventory.
	 *
	 * @param userId the user ID of this cp definition inventory
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition inventory.
	 *
	 * @param userName the user name of this cp definition inventory
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition inventory.
	 *
	 * @param userUuid the user uuid of this cp definition inventory
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp definition inventory.
	 *
	 * @param uuid the uuid of this cp definition inventory
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CPDefinitionInventory, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPDefinitionInventory, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDefinitionInventoryWrapper wrap(
		CPDefinitionInventory cpDefinitionInventory) {

		return new CPDefinitionInventoryWrapper(cpDefinitionInventory);
	}

}