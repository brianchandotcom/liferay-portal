/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceSubscriptionEntry>
	implements CommerceSubscriptionEntry,
			   ModelWrapper<CommerceSubscriptionEntry> {

	public CommerceSubscriptionEntryWrapper(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		super(commerceSubscriptionEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public CommerceSubscriptionEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItem() {
		return model.fetchCommerceOrderItem();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.fetchCPDefinition();
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance fetchCPInstance() {
		return model.fetchCPInstance();
	}

	/**
	 * Returns the commerce order item ID of this commerce subscription entry.
	 *
	 * @return the commerce order item ID of this commerce subscription entry
	 */
	@Override
	public long getCommerceOrderItemId() {
		return model.getCommerceOrderItemId();
	}

	/**
	 * Returns the commerce subscription entry ID of this commerce subscription entry.
	 *
	 * @return the commerce subscription entry ID of this commerce subscription entry
	 */
	@Override
	public long getCommerceSubscriptionEntryId() {
		return model.getCommerceSubscriptionEntryId();
	}

	/**
	 * Returns the company ID of this commerce subscription entry.
	 *
	 * @return the company ID of this commerce subscription entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public long getCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinitionId();
	}

	@Override
	public long getCPInstanceId() {
		return model.getCPInstanceId();
	}

	/**
	 * Returns the cp instance uuid of this commerce subscription entry.
	 *
	 * @return the cp instance uuid of this commerce subscription entry
	 */
	@Override
	public String getCPInstanceUuid() {
		return model.getCPInstanceUuid();
	}

	/**
	 * Returns the c product ID of this commerce subscription entry.
	 *
	 * @return the c product ID of this commerce subscription entry
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this commerce subscription entry.
	 *
	 * @return the create date of this commerce subscription entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the current cycle of this commerce subscription entry.
	 *
	 * @return the current cycle of this commerce subscription entry
	 */
	@Override
	public long getCurrentCycle() {
		return model.getCurrentCycle();
	}

	/**
	 * Returns the delivery current cycle of this commerce subscription entry.
	 *
	 * @return the delivery current cycle of this commerce subscription entry
	 */
	@Override
	public long getDeliveryCurrentCycle() {
		return model.getDeliveryCurrentCycle();
	}

	/**
	 * Returns the delivery last iteration date of this commerce subscription entry.
	 *
	 * @return the delivery last iteration date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryLastIterationDate() {
		return model.getDeliveryLastIterationDate();
	}

	/**
	 * Returns the delivery max subscription cycles of this commerce subscription entry.
	 *
	 * @return the delivery max subscription cycles of this commerce subscription entry
	 */
	@Override
	public long getDeliveryMaxSubscriptionCycles() {
		return model.getDeliveryMaxSubscriptionCycles();
	}

	/**
	 * Returns the delivery next iteration date of this commerce subscription entry.
	 *
	 * @return the delivery next iteration date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryNextIterationDate() {
		return model.getDeliveryNextIterationDate();
	}

	/**
	 * Returns the delivery start date of this commerce subscription entry.
	 *
	 * @return the delivery start date of this commerce subscription entry
	 */
	@Override
	public Date getDeliveryStartDate() {
		return model.getDeliveryStartDate();
	}

	/**
	 * Returns the delivery subscription length of this commerce subscription entry.
	 *
	 * @return the delivery subscription length of this commerce subscription entry
	 */
	@Override
	public int getDeliverySubscriptionLength() {
		return model.getDeliverySubscriptionLength();
	}

	/**
	 * Returns the delivery subscription status of this commerce subscription entry.
	 *
	 * @return the delivery subscription status of this commerce subscription entry
	 */
	@Override
	public int getDeliverySubscriptionStatus() {
		return model.getDeliverySubscriptionStatus();
	}

	/**
	 * Returns the delivery subscription type of this commerce subscription entry.
	 *
	 * @return the delivery subscription type of this commerce subscription entry
	 */
	@Override
	public String getDeliverySubscriptionType() {
		return model.getDeliverySubscriptionType();
	}

	/**
	 * Returns the delivery subscription type settings of this commerce subscription entry.
	 *
	 * @return the delivery subscription type settings of this commerce subscription entry
	 */
	@Override
	public String getDeliverySubscriptionTypeSettings() {
		return model.getDeliverySubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getDeliverySubscriptionTypeSettingsUnicodeProperties() {

		return model.getDeliverySubscriptionTypeSettingsUnicodeProperties();
	}

	/**
	 * Returns the group ID of this commerce subscription entry.
	 *
	 * @return the group ID of this commerce subscription entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last iteration date of this commerce subscription entry.
	 *
	 * @return the last iteration date of this commerce subscription entry
	 */
	@Override
	public Date getLastIterationDate() {
		return model.getLastIterationDate();
	}

	/**
	 * Returns the max subscription cycles of this commerce subscription entry.
	 *
	 * @return the max subscription cycles of this commerce subscription entry
	 */
	@Override
	public long getMaxSubscriptionCycles() {
		return model.getMaxSubscriptionCycles();
	}

	/**
	 * Returns the modified date of this commerce subscription entry.
	 *
	 * @return the modified date of this commerce subscription entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce subscription entry.
	 *
	 * @return the mvcc version of this commerce subscription entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the next iteration date of this commerce subscription entry.
	 *
	 * @return the next iteration date of this commerce subscription entry
	 */
	@Override
	public Date getNextIterationDate() {
		return model.getNextIterationDate();
	}

	/**
	 * Returns the primary key of this commerce subscription entry.
	 *
	 * @return the primary key of this commerce subscription entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this commerce subscription entry.
	 *
	 * @return the start date of this commerce subscription entry
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the subscription length of this commerce subscription entry.
	 *
	 * @return the subscription length of this commerce subscription entry
	 */
	@Override
	public int getSubscriptionLength() {
		return model.getSubscriptionLength();
	}

	/**
	 * Returns the subscription status of this commerce subscription entry.
	 *
	 * @return the subscription status of this commerce subscription entry
	 */
	@Override
	public int getSubscriptionStatus() {
		return model.getSubscriptionStatus();
	}

	/**
	 * Returns the subscription type of this commerce subscription entry.
	 *
	 * @return the subscription type of this commerce subscription entry
	 */
	@Override
	public String getSubscriptionType() {
		return model.getSubscriptionType();
	}

	/**
	 * Returns the subscription type settings of this commerce subscription entry.
	 *
	 * @return the subscription type settings of this commerce subscription entry
	 */
	@Override
	public String getSubscriptionTypeSettings() {
		return model.getSubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSubscriptionTypeSettingsUnicodeProperties() {

		return model.getSubscriptionTypeSettingsUnicodeProperties();
	}

	/**
	 * Returns the user ID of this commerce subscription entry.
	 *
	 * @return the user ID of this commerce subscription entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce subscription entry.
	 *
	 * @return the user name of this commerce subscription entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce subscription entry.
	 *
	 * @return the user uuid of this commerce subscription entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce subscription entry.
	 *
	 * @return the uuid of this commerce subscription entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce order item ID of this commerce subscription entry.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce subscription entry
	 */
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		model.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Sets the commerce subscription entry ID of this commerce subscription entry.
	 *
	 * @param commerceSubscriptionEntryId the commerce subscription entry ID of this commerce subscription entry
	 */
	@Override
	public void setCommerceSubscriptionEntryId(
		long commerceSubscriptionEntryId) {

		model.setCommerceSubscriptionEntryId(commerceSubscriptionEntryId);
	}

	/**
	 * Sets the company ID of this commerce subscription entry.
	 *
	 * @param companyId the company ID of this commerce subscription entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance uuid of this commerce subscription entry.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this commerce subscription entry
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		model.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this commerce subscription entry.
	 *
	 * @param CProductId the c product ID of this commerce subscription entry
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce subscription entry.
	 *
	 * @param createDate the create date of this commerce subscription entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the current cycle of this commerce subscription entry.
	 *
	 * @param currentCycle the current cycle of this commerce subscription entry
	 */
	@Override
	public void setCurrentCycle(long currentCycle) {
		model.setCurrentCycle(currentCycle);
	}

	/**
	 * Sets the delivery current cycle of this commerce subscription entry.
	 *
	 * @param deliveryCurrentCycle the delivery current cycle of this commerce subscription entry
	 */
	@Override
	public void setDeliveryCurrentCycle(long deliveryCurrentCycle) {
		model.setDeliveryCurrentCycle(deliveryCurrentCycle);
	}

	/**
	 * Sets the delivery last iteration date of this commerce subscription entry.
	 *
	 * @param deliveryLastIterationDate the delivery last iteration date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryLastIterationDate(Date deliveryLastIterationDate) {
		model.setDeliveryLastIterationDate(deliveryLastIterationDate);
	}

	/**
	 * Sets the delivery max subscription cycles of this commerce subscription entry.
	 *
	 * @param deliveryMaxSubscriptionCycles the delivery max subscription cycles of this commerce subscription entry
	 */
	@Override
	public void setDeliveryMaxSubscriptionCycles(
		long deliveryMaxSubscriptionCycles) {

		model.setDeliveryMaxSubscriptionCycles(deliveryMaxSubscriptionCycles);
	}

	/**
	 * Sets the delivery next iteration date of this commerce subscription entry.
	 *
	 * @param deliveryNextIterationDate the delivery next iteration date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryNextIterationDate(Date deliveryNextIterationDate) {
		model.setDeliveryNextIterationDate(deliveryNextIterationDate);
	}

	/**
	 * Sets the delivery start date of this commerce subscription entry.
	 *
	 * @param deliveryStartDate the delivery start date of this commerce subscription entry
	 */
	@Override
	public void setDeliveryStartDate(Date deliveryStartDate) {
		model.setDeliveryStartDate(deliveryStartDate);
	}

	/**
	 * Sets the delivery subscription length of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionLength the delivery subscription length of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionLength(int deliverySubscriptionLength) {
		model.setDeliverySubscriptionLength(deliverySubscriptionLength);
	}

	/**
	 * Sets the delivery subscription status of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionStatus the delivery subscription status of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionStatus(int deliverySubscriptionStatus) {
		model.setDeliverySubscriptionStatus(deliverySubscriptionStatus);
	}

	/**
	 * Sets the delivery subscription type of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionType the delivery subscription type of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionType(String deliverySubscriptionType) {
		model.setDeliverySubscriptionType(deliverySubscriptionType);
	}

	/**
	 * Sets the delivery subscription type settings of this commerce subscription entry.
	 *
	 * @param deliverySubscriptionTypeSettings the delivery subscription type settings of this commerce subscription entry
	 */
	@Override
	public void setDeliverySubscriptionTypeSettings(
		String deliverySubscriptionTypeSettings) {

		model.setDeliverySubscriptionTypeSettings(
			deliverySubscriptionTypeSettings);
	}

	@Override
	public void setDeliverySubscriptionTypeSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			deliverySubscriptionTypeSettingsUnicodeProperties) {

		model.setDeliverySubscriptionTypeSettingsUnicodeProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
	}

	/**
	 * Sets the group ID of this commerce subscription entry.
	 *
	 * @param groupId the group ID of this commerce subscription entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last iteration date of this commerce subscription entry.
	 *
	 * @param lastIterationDate the last iteration date of this commerce subscription entry
	 */
	@Override
	public void setLastIterationDate(Date lastIterationDate) {
		model.setLastIterationDate(lastIterationDate);
	}

	/**
	 * Sets the max subscription cycles of this commerce subscription entry.
	 *
	 * @param maxSubscriptionCycles the max subscription cycles of this commerce subscription entry
	 */
	@Override
	public void setMaxSubscriptionCycles(long maxSubscriptionCycles) {
		model.setMaxSubscriptionCycles(maxSubscriptionCycles);
	}

	/**
	 * Sets the modified date of this commerce subscription entry.
	 *
	 * @param modifiedDate the modified date of this commerce subscription entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce subscription entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce subscription entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the next iteration date of this commerce subscription entry.
	 *
	 * @param nextIterationDate the next iteration date of this commerce subscription entry
	 */
	@Override
	public void setNextIterationDate(Date nextIterationDate) {
		model.setNextIterationDate(nextIterationDate);
	}

	/**
	 * Sets the primary key of this commerce subscription entry.
	 *
	 * @param primaryKey the primary key of this commerce subscription entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this commerce subscription entry.
	 *
	 * @param startDate the start date of this commerce subscription entry
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the subscription length of this commerce subscription entry.
	 *
	 * @param subscriptionLength the subscription length of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionLength(int subscriptionLength) {
		model.setSubscriptionLength(subscriptionLength);
	}

	/**
	 * Sets the subscription status of this commerce subscription entry.
	 *
	 * @param subscriptionStatus the subscription status of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionStatus(int subscriptionStatus) {
		model.setSubscriptionStatus(subscriptionStatus);
	}

	/**
	 * Sets the subscription type of this commerce subscription entry.
	 *
	 * @param subscriptionType the subscription type of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionType(String subscriptionType) {
		model.setSubscriptionType(subscriptionType);
	}

	/**
	 * Sets the subscription type settings of this commerce subscription entry.
	 *
	 * @param subscriptionTypeSettings the subscription type settings of this commerce subscription entry
	 */
	@Override
	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		model.setSubscriptionTypeSettings(subscriptionTypeSettings);
	}

	@Override
	public void setSubscriptionTypeSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			subscriptionTypeSettingsUnicodeProperties) {

		model.setSubscriptionTypeSettingsUnicodeProperties(
			subscriptionTypeSettingsUnicodeProperties);
	}

	/**
	 * Sets the user ID of this commerce subscription entry.
	 *
	 * @param userId the user ID of this commerce subscription entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce subscription entry.
	 *
	 * @param userName the user name of this commerce subscription entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce subscription entry.
	 *
	 * @param userUuid the user uuid of this commerce subscription entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce subscription entry.
	 *
	 * @param uuid the uuid of this commerce subscription entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceSubscriptionEntryWrapper wrap(
		CommerceSubscriptionEntry commerceSubscriptionEntry) {

		return new CommerceSubscriptionEntryWrapper(commerceSubscriptionEntry);
	}

}