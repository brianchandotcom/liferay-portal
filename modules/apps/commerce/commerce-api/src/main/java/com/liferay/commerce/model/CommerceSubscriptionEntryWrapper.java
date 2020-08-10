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
 * This class is a wrapper for {@link CommerceSubscriptionEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceSubscriptionEntry
 * @generated
 */
public class CommerceSubscriptionEntryWrapper
	implements CommerceSubscriptionEntry,
			   ModelWrapper<CommerceSubscriptionEntry> {

	public CommerceSubscriptionEntryWrapper(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		_commerceSubscriptionEntry = commerceSubscriptionEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceSubscriptionEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceSubscriptionEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceSubscriptionEntryId", getCommerceSubscriptionEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPInstanceUuid", getCPInstanceUuid());
		attributes.put("CProductId", getCProductId());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("subscriptionLength", getSubscriptionLength());
		attributes.put("subscriptionType", getSubscriptionType());
		attributes.put(
			"subscriptionTypeSettings", getSubscriptionTypeSettings());
		attributes.put("currentCycle", getCurrentCycle());
		attributes.put("maxSubscriptionCycles", getMaxSubscriptionCycles());
		attributes.put("subscriptionStatus", getSubscriptionStatus());
		attributes.put("lastIterationDate", getLastIterationDate());
		attributes.put("nextIterationDate", getNextIterationDate());
		attributes.put("startDate", getStartDate());
		attributes.put(
			"deliverySubscriptionLength", getDeliverySubscriptionLength());
		attributes.put(
			"deliverySubscriptionType", getDeliverySubscriptionType());
		attributes.put(
			"deliverySubscriptionTypeSettings",
			getDeliverySubscriptionTypeSettings());
		attributes.put("deliveryCurrentCycle", getDeliveryCurrentCycle());
		attributes.put(
			"deliveryMaxSubscriptionCycles",
			getDeliveryMaxSubscriptionCycles());
		attributes.put(
			"deliverySubscriptionStatus", getDeliverySubscriptionStatus());
		attributes.put(
			"deliveryLastIterationDate", getDeliveryLastIterationDate());
		attributes.put(
			"deliveryNextIterationDate", getDeliveryNextIterationDate());
		attributes.put("deliveryStartDate", getDeliveryStartDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceSubscriptionEntryId = (Long)attributes.get(
			"commerceSubscriptionEntryId");

		if (commerceSubscriptionEntryId != null) {
			setCommerceSubscriptionEntryId(commerceSubscriptionEntryId);
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

		String CPInstanceUuid = (String)attributes.get("CPInstanceUuid");

		if (CPInstanceUuid != null) {
			setCPInstanceUuid(CPInstanceUuid);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Integer subscriptionLength = (Integer)attributes.get(
			"subscriptionLength");

		if (subscriptionLength != null) {
			setSubscriptionLength(subscriptionLength);
		}

		String subscriptionType = (String)attributes.get("subscriptionType");

		if (subscriptionType != null) {
			setSubscriptionType(subscriptionType);
		}

		String subscriptionTypeSettings = (String)attributes.get(
			"subscriptionTypeSettings");

		if (subscriptionTypeSettings != null) {
			setSubscriptionTypeSettings(subscriptionTypeSettings);
		}

		Long currentCycle = (Long)attributes.get("currentCycle");

		if (currentCycle != null) {
			setCurrentCycle(currentCycle);
		}

		Long maxSubscriptionCycles = (Long)attributes.get(
			"maxSubscriptionCycles");

		if (maxSubscriptionCycles != null) {
			setMaxSubscriptionCycles(maxSubscriptionCycles);
		}

		Integer subscriptionStatus = (Integer)attributes.get(
			"subscriptionStatus");

		if (subscriptionStatus != null) {
			setSubscriptionStatus(subscriptionStatus);
		}

		Date lastIterationDate = (Date)attributes.get("lastIterationDate");

		if (lastIterationDate != null) {
			setLastIterationDate(lastIterationDate);
		}

		Date nextIterationDate = (Date)attributes.get("nextIterationDate");

		if (nextIterationDate != null) {
			setNextIterationDate(nextIterationDate);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Integer deliverySubscriptionLength = (Integer)attributes.get(
			"deliverySubscriptionLength");

		if (deliverySubscriptionLength != null) {
			setDeliverySubscriptionLength(deliverySubscriptionLength);
		}

		String deliverySubscriptionType = (String)attributes.get(
			"deliverySubscriptionType");

		if (deliverySubscriptionType != null) {
			setDeliverySubscriptionType(deliverySubscriptionType);
		}

		String deliverySubscriptionTypeSettings = (String)attributes.get(
			"deliverySubscriptionTypeSettings");

		if (deliverySubscriptionTypeSettings != null) {
			setDeliverySubscriptionTypeSettings(
				deliverySubscriptionTypeSettings);
		}

		Long deliveryCurrentCycle = (Long)attributes.get(
			"deliveryCurrentCycle");

		if (deliveryCurrentCycle != null) {
			setDeliveryCurrentCycle(deliveryCurrentCycle);
		}

		Long deliveryMaxSubscriptionCycles = (Long)attributes.get(
			"deliveryMaxSubscriptionCycles");

		if (deliveryMaxSubscriptionCycles != null) {
			setDeliveryMaxSubscriptionCycles(deliveryMaxSubscriptionCycles);
		}

		Integer deliverySubscriptionStatus = (Integer)attributes.get(
			"deliverySubscriptionStatus");

		if (deliverySubscriptionStatus != null) {
			setDeliverySubscriptionStatus(deliverySubscriptionStatus);
		}

		Date deliveryLastIterationDate = (Date)attributes.get(
			"deliveryLastIterationDate");

		if (deliveryLastIterationDate != null) {
			setDeliveryLastIterationDate(deliveryLastIterationDate);
		}

		Date deliveryNextIterationDate = (Date)attributes.get(
			"deliveryNextIterationDate");

		if (deliveryNextIterationDate != null) {
			setDeliveryNextIterationDate(deliveryNextIterationDate);
		}

		Date deliveryStartDate = (Date)attributes.get("deliveryStartDate");

		if (deliveryStartDate != null) {
			setDeliveryStartDate(deliveryStartDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceSubscriptionEntryWrapper(
			(CommerceSubscriptionEntry)_commerceSubscriptionEntry.clone());
	}

	@Override
	public int compareTo(CommerceSubscriptionEntry commerceSubscriptionEntry) {
		return _commerceSubscriptionEntry.compareTo(commerceSubscriptionEntry);
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItem() {
		return _commerceSubscriptionEntry.fetchCommerceOrderItem();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntry.fetchCPDefinition();
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance() {
		return _commerceSubscriptionEntry.fetchCPInstance();
	}

	/**
	 * Returns the commerce order item ID of this commerce subscription entry.
	 *
	 * @return the commerce order item ID of this commerce subscription entry
	 */
	@Override
	public long getCommerceOrderItemId() {
		return _commerceSubscriptionEntry.getCommerceOrderItemId();
	}

	/**
	 * Returns the commerce subscription entry ID of this commerce subscription entry.
	 *
	 * @return the commerce subscription entry ID of this commerce subscription entry
	 */
	@Override
	public long getCommerceSubscriptionEntryId() {
		return _commerceSubscriptionEntry.getCommerceSubscriptionEntryId();
	}

	/**
	 * Returns the company ID of this commerce subscription entry.
	 *
	 * @return the company ID of this commerce subscription entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceSubscriptionEntry.getCompanyId();
	}

	@Override
	public long getCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceSubscriptionEntry.getCPDefinitionId();
	}

	@Override
	public long getCPInstanceId() {
		return _commerceSubscriptionEntry.getCPInstanceId();
	}

	/**
	 * Returns the cp instance uuid of this commerce subscription entry.
	 *
	 * @return the cp instance uuid of this commerce subscription entry
	 */
	@Override
	public String getCPInstanceUuid() {
		return _commerceSubscriptionEntry.getCPInstanceUuid();
	}

	/**
	 * Returns the c product ID of this commerce subscription entry.
	 *
	 * @return the c product ID of this commerce subscription entry
	 */
	@Override
	public long getCProductId() {
		return _commerceSubscriptionEntry.getCProductId();
	}

	/**
	 * Returns the create date of this commerce subscription entry.
	 *
	 * @return the create date of this commerce subscription entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceSubscriptionEntry.getCreateDate();
	}

	/**
	 * Returns the current cycle of this commerce subscription entry.
	 *
	 * @return the current cycle of this commerce subscription entry
	 */
	@Override
	public long getCurrentCycle() {
		return _commerceSubscriptionEntry.getCurrentCycle();
	}

	/**
	 * Returns the delivery current cycle of this commerce subscription entry.
	 *
	 * @return the delivery current cycle of this commerce subscription entry
	 */
	@Override
	public long getDeliveryCurrentCycle() {
		return _commerceSubscriptionEntry.getDeliveryCurrentCycle();
	}

	/**
	 * Returns the delivery last iteration date of this commerce subscription entry.
	 *
	 * @return the delivery last iteration date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryLastIterationDate() {
		return _commerceSubscriptionEntry.getDeliveryLastIterationDate();
	}

	/**
	 * Returns the delivery max subscription cycles of this commerce subscription entry.
	 *
	 * @return the delivery max subscription cycles of this commerce subscription entry
	 */
	@Override
	public long getDeliveryMaxSubscriptionCycles() {
		return _commerceSubscriptionEntry.getDeliveryMaxSubscriptionCycles();
	}

	/**
	 * Returns the delivery next iteration date of this commerce subscription entry.
	 *
	 * @return the delivery next iteration date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryNextIterationDate() {
		return _commerceSubscriptionEntry.getDeliveryNextIterationDate();
	}

	/**
	 * Returns the delivery start date of this commerce subscription entry.
	 *
	 * @return the delivery start date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryStartDate() {
		return _commerceSubscriptionEntry.getDeliveryStartDate();
	}

	/**
	 * Returns the delivery subscription length of this commerce subscription entry.
	 *
	 * @return the delivery subscription length of this commerce subscription entry
	 */
	@Override
	public int getDeliverySubscriptionLength() {
		return _commerceSubscriptionEntry.getDeliverySubscriptionLength();
	}

	/**
	 * Returns the delivery subscription status of this commerce subscription entry.
	 *
	 * @return the delivery subscription status of this commerce subscription entry
	 */
	@Override
	public int getDeliverySubscriptionStatus() {
		return _commerceSubscriptionEntry.getDeliverySubscriptionStatus();
	}

	/**
	 * Returns the delivery subscription type of this commerce subscription entry.
	 *
	 * @return the delivery subscription type of this commerce subscription entry
	 */
	@Override
	public String getDeliverySubscriptionType() {
		return _commerceSubscriptionEntry.getDeliverySubscriptionType();
	}

	/**
	 * Returns the delivery subscription type settings of this commerce subscription entry.
	 *
	 * @return the delivery subscription type settings of this commerce subscription entry
	 */
	@Override
	public String getDeliverySubscriptionTypeSettings() {
		return _commerceSubscriptionEntry.getDeliverySubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getDeliverySubscriptionTypeSettingsProperties() {

		return _commerceSubscriptionEntry.
			getDeliverySubscriptionTypeSettingsProperties();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceSubscriptionEntry.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this commerce subscription entry.
	 *
	 * @return the group ID of this commerce subscription entry
	 */
	@Override
	public long getGroupId() {
		return _commerceSubscriptionEntry.getGroupId();
	}

	/**
	 * Returns the last iteration date of this commerce subscription entry.
	 *
	 * @return the last iteration date of this commerce subscription entry
	 */
	@Override
	public Date getLastIterationDate() {
		return _commerceSubscriptionEntry.getLastIterationDate();
	}

	/**
	 * Returns the max subscription cycles of this commerce subscription entry.
	 *
	 * @return the max subscription cycles of this commerce subscription entry
	 */
	@Override
	public long getMaxSubscriptionCycles() {
		return _commerceSubscriptionEntry.getMaxSubscriptionCycles();
	}

	/**
	 * Returns the modified date of this commerce subscription entry.
	 *
	 * @return the modified date of this commerce subscription entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceSubscriptionEntry.getModifiedDate();
	}

	/**
	 * Returns the next iteration date of this commerce subscription entry.
	 *
	 * @return the next iteration date of this commerce subscription entry
	 */
	@Override
	public Date getNextIterationDate() {
		return _commerceSubscriptionEntry.getNextIterationDate();
	}

	/**
	 * Returns the primary key of this commerce subscription entry.
	 *
	 * @return the primary key of this commerce subscription entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceSubscriptionEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceSubscriptionEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the start date of this commerce subscription entry.
	 *
	 * @return the start date of this commerce subscription entry
	 */
	@Override
	public Date getStartDate() {
		return _commerceSubscriptionEntry.getStartDate();
	}

	/**
	 * Returns the subscription length of this commerce subscription entry.
	 *
	 * @return the subscription length of this commerce subscription entry
	 */
	@Override
	public int getSubscriptionLength() {
		return _commerceSubscriptionEntry.getSubscriptionLength();
	}

	/**
	 * Returns the subscription status of this commerce subscription entry.
	 *
	 * @return the subscription status of this commerce subscription entry
	 */
	@Override
	public int getSubscriptionStatus() {
		return _commerceSubscriptionEntry.getSubscriptionStatus();
	}

	/**
	 * Returns the subscription type of this commerce subscription entry.
	 *
	 * @return the subscription type of this commerce subscription entry
	 */
	@Override
	public String getSubscriptionType() {
		return _commerceSubscriptionEntry.getSubscriptionType();
	}

	/**
	 * Returns the subscription type settings of this commerce subscription entry.
	 *
	 * @return the subscription type settings of this commerce subscription entry
	 */
	@Override
	public String getSubscriptionTypeSettings() {
		return _commerceSubscriptionEntry.getSubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSubscriptionTypeSettingsProperties() {

		return _commerceSubscriptionEntry.
			getSubscriptionTypeSettingsProperties();
	}

	/**
	 * Returns the user ID of this commerce subscription entry.
	 *
	 * @return the user ID of this commerce subscription entry
	 */
	@Override
	public long getUserId() {
		return _commerceSubscriptionEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce subscription entry.
	 *
	 * @return the user name of this commerce subscription entry
	 */
	@Override
	public String getUserName() {
		return _commerceSubscriptionEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce subscription entry.
	 *
	 * @return the user uuid of this commerce subscription entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceSubscriptionEntry.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce subscription entry.
	 *
	 * @return the uuid of this commerce subscription entry
	 */
	@Override
	public String getUuid() {
		return _commerceSubscriptionEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceSubscriptionEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceSubscriptionEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceSubscriptionEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceSubscriptionEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceSubscriptionEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceSubscriptionEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce order item ID of this commerce subscription entry.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce subscription entry
	 */
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceSubscriptionEntry.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Sets the commerce subscription entry ID of this commerce subscription entry.
	 *
	 * @param commerceSubscriptionEntryId the commerce subscription entry ID of this commerce subscription entry
	 */
	@Override
	public void setCommerceSubscriptionEntryId(
		long commerceSubscriptionEntryId) {

		_commerceSubscriptionEntry.setCommerceSubscriptionEntryId(
			commerceSubscriptionEntryId);
	}

	/**
	 * Sets the company ID of this commerce subscription entry.
	 *
	 * @param companyId the company ID of this commerce subscription entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceSubscriptionEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance uuid of this commerce subscription entry.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this commerce subscription entry
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		_commerceSubscriptionEntry.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this commerce subscription entry.
	 *
	 * @param CProductId the c product ID of this commerce subscription entry
	 */
	@Override
	public void setCProductId(long CProductId) {
		_commerceSubscriptionEntry.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce subscription entry.
	 *
	 * @param createDate the create date of this commerce subscription entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceSubscriptionEntry.setCreateDate(createDate);
	}

	/**
	 * Sets the current cycle of this commerce subscription entry.
	 *
	 * @param currentCycle the current cycle of this commerce subscription entry
	 */
	@Override
	public void setCurrentCycle(long currentCycle) {
		_commerceSubscriptionEntry.setCurrentCycle(currentCycle);
	}

	/**
	 * Sets the delivery current cycle of this commerce subscription entry.
	 *
	 * @param deliveryCurrentCycle the delivery current cycle of this commerce subscription entry
	 */
	@Override
	public void setDeliveryCurrentCycle(long deliveryCurrentCycle) {
		_commerceSubscriptionEntry.setDeliveryCurrentCycle(
			deliveryCurrentCycle);
	}

	/**
	 * Sets the delivery last iteration date of this commerce subscription entry.
	 *
	 * @param deliveryLastIterationDate the delivery last iteration date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryLastIterationDate(Date deliveryLastIterationDate) {
		_commerceSubscriptionEntry.setDeliveryLastIterationDate(
			deliveryLastIterationDate);
	}

	/**
	 * Sets the delivery max subscription cycles of this commerce subscription entry.
	 *
	 * @param deliveryMaxSubscriptionCycles the delivery max subscription cycles of this commerce subscription entry
	 */
	@Override
	public void setDeliveryMaxSubscriptionCycles(
		long deliveryMaxSubscriptionCycles) {

		_commerceSubscriptionEntry.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);
	}

	/**
	 * Sets the delivery next iteration date of this commerce subscription entry.
	 *
	 * @param deliveryNextIterationDate the delivery next iteration date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryNextIterationDate(Date deliveryNextIterationDate) {
		_commerceSubscriptionEntry.setDeliveryNextIterationDate(
			deliveryNextIterationDate);
	}

	/**
	 * Sets the delivery start date of this commerce subscription entry.
	 *
	 * @param deliveryStartDate the delivery start date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryStartDate(Date deliveryStartDate) {
		_commerceSubscriptionEntry.setDeliveryStartDate(deliveryStartDate);
	}

	/**
	 * Sets the delivery subscription length of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionLength the delivery subscription length of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionLength(int deliverySubscriptionLength) {
		_commerceSubscriptionEntry.setDeliverySubscriptionLength(
			deliverySubscriptionLength);
	}

	/**
	 * Sets the delivery subscription status of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionStatus the delivery subscription status of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionStatus(int deliverySubscriptionStatus) {
		_commerceSubscriptionEntry.setDeliverySubscriptionStatus(
			deliverySubscriptionStatus);
	}

	/**
	 * Sets the delivery subscription type of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionType the delivery subscription type of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionType(String deliverySubscriptionType) {
		_commerceSubscriptionEntry.setDeliverySubscriptionType(
			deliverySubscriptionType);
	}

	/**
	 * Sets the delivery subscription type settings of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionTypeSettings the delivery subscription type settings of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionTypeSettings(
		String deliverySubscriptionTypeSettings) {

		_commerceSubscriptionEntry.setDeliverySubscriptionTypeSettings(
			deliverySubscriptionTypeSettings);
	}

	@Override
	public void setDeliverySubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			deliverySubscriptionTypeSettingsProperties) {

		_commerceSubscriptionEntry.
			setDeliverySubscriptionTypeSettingsProperties(
				deliverySubscriptionTypeSettingsProperties);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceSubscriptionEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceSubscriptionEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceSubscriptionEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this commerce subscription entry.
	 *
	 * @param groupId the group ID of this commerce subscription entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceSubscriptionEntry.setGroupId(groupId);
	}

	/**
	 * Sets the last iteration date of this commerce subscription entry.
	 *
	 * @param lastIterationDate the last iteration date of this commerce subscription entry
	 */
	@Override
	public void setLastIterationDate(Date lastIterationDate) {
		_commerceSubscriptionEntry.setLastIterationDate(lastIterationDate);
	}

	/**
	 * Sets the max subscription cycles of this commerce subscription entry.
	 *
	 * @param maxSubscriptionCycles the max subscription cycles of this commerce subscription entry
	 */
	@Override
	public void setMaxSubscriptionCycles(long maxSubscriptionCycles) {
		_commerceSubscriptionEntry.setMaxSubscriptionCycles(
			maxSubscriptionCycles);
	}

	/**
	 * Sets the modified date of this commerce subscription entry.
	 *
	 * @param modifiedDate the modified date of this commerce subscription entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceSubscriptionEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceSubscriptionEntry.setNew(n);
	}

	/**
	 * Sets the next iteration date of this commerce subscription entry.
	 *
	 * @param nextIterationDate the next iteration date of this commerce subscription entry
	 */
	@Override
	public void setNextIterationDate(Date nextIterationDate) {
		_commerceSubscriptionEntry.setNextIterationDate(nextIterationDate);
	}

	/**
	 * Sets the primary key of this commerce subscription entry.
	 *
	 * @param primaryKey the primary key of this commerce subscription entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceSubscriptionEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceSubscriptionEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the start date of this commerce subscription entry.
	 *
	 * @param startDate the start date of this commerce subscription entry
	 */
	@Override
	public void setStartDate(Date startDate) {
		_commerceSubscriptionEntry.setStartDate(startDate);
	}

	/**
	 * Sets the subscription length of this commerce subscription entry.
	 *
	 * @param subscriptionLength the subscription length of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionLength(int subscriptionLength) {
		_commerceSubscriptionEntry.setSubscriptionLength(subscriptionLength);
	}

	/**
	 * Sets the subscription status of this commerce subscription entry.
	 *
	 * @param subscriptionStatus the subscription status of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionStatus(int subscriptionStatus) {
		_commerceSubscriptionEntry.setSubscriptionStatus(subscriptionStatus);
	}

	/**
	 * Sets the subscription type of this commerce subscription entry.
	 *
	 * @param subscriptionType the subscription type of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionType(String subscriptionType) {
		_commerceSubscriptionEntry.setSubscriptionType(subscriptionType);
	}

	/**
	 * Sets the subscription type settings of this commerce subscription entry.
	 *
	 * @param subscriptionTypeSettings the subscription type settings of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		_commerceSubscriptionEntry.setSubscriptionTypeSettings(
			subscriptionTypeSettings);
	}

	@Override
	public void setSubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			subscriptionTypeSettingsProperties) {

		_commerceSubscriptionEntry.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsProperties);
	}

	/**
	 * Sets the user ID of this commerce subscription entry.
	 *
	 * @param userId the user ID of this commerce subscription entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceSubscriptionEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce subscription entry.
	 *
	 * @param userName the user name of this commerce subscription entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceSubscriptionEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce subscription entry.
	 *
	 * @param userUuid the user uuid of this commerce subscription entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceSubscriptionEntry.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce subscription entry.
	 *
	 * @param uuid the uuid of this commerce subscription entry
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceSubscriptionEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceSubscriptionEntry>
		toCacheModel() {

		return _commerceSubscriptionEntry.toCacheModel();
	}

	@Override
	public CommerceSubscriptionEntry toEscapedModel() {
		return new CommerceSubscriptionEntryWrapper(
			_commerceSubscriptionEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceSubscriptionEntry.toString();
	}

	@Override
	public CommerceSubscriptionEntry toUnescapedModel() {
		return new CommerceSubscriptionEntryWrapper(
			_commerceSubscriptionEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceSubscriptionEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceSubscriptionEntryWrapper)) {
			return false;
		}

		CommerceSubscriptionEntryWrapper commerceSubscriptionEntryWrapper =
			(CommerceSubscriptionEntryWrapper)object;

		if (Objects.equals(
				_commerceSubscriptionEntry,
				commerceSubscriptionEntryWrapper._commerceSubscriptionEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceSubscriptionEntry.getStagedModelType();
	}

	@Override
	public CommerceSubscriptionEntry getWrappedModel() {
		return _commerceSubscriptionEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceSubscriptionEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceSubscriptionEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceSubscriptionEntry.resetOriginalValues();
	}

	private final CommerceSubscriptionEntry _commerceSubscriptionEntry;

}