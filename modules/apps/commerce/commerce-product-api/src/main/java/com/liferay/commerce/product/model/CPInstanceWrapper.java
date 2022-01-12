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

package com.liferay.commerce.product.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPInstance}.
 * </p>
 *
 * @author Marco Leo
 * @see CPInstance
 * @generated
 */
public class CPInstanceWrapper
	extends BaseModelWrapper<CPInstance>
	implements CPInstance, ModelWrapper<CPInstance> {

	public CPInstanceWrapper(CPInstance cpInstance) {
		super(cpInstance);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("CPInstanceId", getCPInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("CPInstanceUuid", getCPInstanceUuid());
		attributes.put("sku", getSku());
		attributes.put("gtin", getGtin());
		attributes.put("manufacturerPartNumber", getManufacturerPartNumber());
		attributes.put("purchasable", isPurchasable());
		attributes.put("width", getWidth());
		attributes.put("height", getHeight());
		attributes.put("depth", getDepth());
		attributes.put("weight", getWeight());
		attributes.put("price", getPrice());
		attributes.put("promoPrice", getPromoPrice());
		attributes.put("cost", getCost());
		attributes.put("published", isPublished());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put(
			"overrideSubscriptionInfo", isOverrideSubscriptionInfo());
		attributes.put("subscriptionEnabled", isSubscriptionEnabled());
		attributes.put("subscriptionLength", getSubscriptionLength());
		attributes.put("subscriptionType", getSubscriptionType());
		attributes.put(
			"subscriptionTypeSettings", getSubscriptionTypeSettings());
		attributes.put("maxSubscriptionCycles", getMaxSubscriptionCycles());
		attributes.put(
			"deliverySubscriptionEnabled", isDeliverySubscriptionEnabled());
		attributes.put(
			"deliverySubscriptionLength", getDeliverySubscriptionLength());
		attributes.put(
			"deliverySubscriptionType", getDeliverySubscriptionType());
		attributes.put(
			"deliverySubscriptionTypeSettings",
			getDeliverySubscriptionTypeSettings());
		attributes.put(
			"deliveryMaxSubscriptionCycles",
			getDeliveryMaxSubscriptionCycles());
		attributes.put("unspsc", getUnspsc());
		attributes.put("discontinued", isDiscontinued());
		attributes.put("discontinuedDate", getDiscontinuedDate());
		attributes.put(
			"replacementCPInstanceUuid", getReplacementCPInstanceUuid());
		attributes.put("replacementCProductId", getReplacementCProductId());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long CPInstanceId = (Long)attributes.get("CPInstanceId");

		if (CPInstanceId != null) {
			setCPInstanceId(CPInstanceId);
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

		String CPInstanceUuid = (String)attributes.get("CPInstanceUuid");

		if (CPInstanceUuid != null) {
			setCPInstanceUuid(CPInstanceUuid);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		String gtin = (String)attributes.get("gtin");

		if (gtin != null) {
			setGtin(gtin);
		}

		String manufacturerPartNumber = (String)attributes.get(
			"manufacturerPartNumber");

		if (manufacturerPartNumber != null) {
			setManufacturerPartNumber(manufacturerPartNumber);
		}

		Boolean purchasable = (Boolean)attributes.get("purchasable");

		if (purchasable != null) {
			setPurchasable(purchasable);
		}

		Double width = (Double)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Double height = (Double)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Double depth = (Double)attributes.get("depth");

		if (depth != null) {
			setDepth(depth);
		}

		Double weight = (Double)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}

		BigDecimal price = (BigDecimal)attributes.get("price");

		if (price != null) {
			setPrice(price);
		}

		BigDecimal promoPrice = (BigDecimal)attributes.get("promoPrice");

		if (promoPrice != null) {
			setPromoPrice(promoPrice);
		}

		BigDecimal cost = (BigDecimal)attributes.get("cost");

		if (cost != null) {
			setCost(cost);
		}

		Boolean published = (Boolean)attributes.get("published");

		if (published != null) {
			setPublished(published);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Boolean overrideSubscriptionInfo = (Boolean)attributes.get(
			"overrideSubscriptionInfo");

		if (overrideSubscriptionInfo != null) {
			setOverrideSubscriptionInfo(overrideSubscriptionInfo);
		}

		Boolean subscriptionEnabled = (Boolean)attributes.get(
			"subscriptionEnabled");

		if (subscriptionEnabled != null) {
			setSubscriptionEnabled(subscriptionEnabled);
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

		Long maxSubscriptionCycles = (Long)attributes.get(
			"maxSubscriptionCycles");

		if (maxSubscriptionCycles != null) {
			setMaxSubscriptionCycles(maxSubscriptionCycles);
		}

		Boolean deliverySubscriptionEnabled = (Boolean)attributes.get(
			"deliverySubscriptionEnabled");

		if (deliverySubscriptionEnabled != null) {
			setDeliverySubscriptionEnabled(deliverySubscriptionEnabled);
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

		Long deliveryMaxSubscriptionCycles = (Long)attributes.get(
			"deliveryMaxSubscriptionCycles");

		if (deliveryMaxSubscriptionCycles != null) {
			setDeliveryMaxSubscriptionCycles(deliveryMaxSubscriptionCycles);
		}

		String unspsc = (String)attributes.get("unspsc");

		if (unspsc != null) {
			setUnspsc(unspsc);
		}

		Boolean discontinued = (Boolean)attributes.get("discontinued");

		if (discontinued != null) {
			setDiscontinued(discontinued);
		}

		Date discontinuedDate = (Date)attributes.get("discontinuedDate");

		if (discontinuedDate != null) {
			setDiscontinuedDate(discontinuedDate);
		}

		String replacementCPInstanceUuid = (String)attributes.get(
			"replacementCPInstanceUuid");

		if (replacementCPInstanceUuid != null) {
			setReplacementCPInstanceUuid(replacementCPInstanceUuid);
		}

		Long replacementCProductId = (Long)attributes.get(
			"replacementCProductId");

		if (replacementCProductId != null) {
			setReplacementCProductId(replacementCProductId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public CPInstance cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceCatalog getCommerceCatalog()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceCatalog();
	}

	/**
	 * Returns the company ID of this cp instance.
	 *
	 * @return the company ID of this cp instance
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cost of this cp instance.
	 *
	 * @return the cost of this cp instance
	 */
	@Override
	public BigDecimal getCost() {
		return model.getCost();
	}

	@Override
	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition ID of this cp instance.
	 *
	 * @return the cp definition ID of this cp instance
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the cp instance ID of this cp instance.
	 *
	 * @return the cp instance ID of this cp instance
	 */
	@Override
	public long getCPInstanceId() {
		return model.getCPInstanceId();
	}

	/**
	 * Returns the cp instance uuid of this cp instance.
	 *
	 * @return the cp instance uuid of this cp instance
	 */
	@Override
	public String getCPInstanceUuid() {
		return model.getCPInstanceUuid();
	}

	@Override
	public CPSubscriptionInfo getCPSubscriptionInfo()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPSubscriptionInfo();
	}

	/**
	 * Returns the create date of this cp instance.
	 *
	 * @return the create date of this cp instance
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this cp instance.
	 *
	 * @return the ct collection ID of this cp instance
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the delivery max subscription cycles of this cp instance.
	 *
	 * @return the delivery max subscription cycles of this cp instance
	 */
	@Override
	public long getDeliveryMaxSubscriptionCycles() {
		return model.getDeliveryMaxSubscriptionCycles();
	}

	/**
	 * Returns the delivery subscription enabled of this cp instance.
	 *
	 * @return the delivery subscription enabled of this cp instance
	 */
	@Override
	public boolean getDeliverySubscriptionEnabled() {
		return model.getDeliverySubscriptionEnabled();
	}

	/**
	 * Returns the delivery subscription length of this cp instance.
	 *
	 * @return the delivery subscription length of this cp instance
	 */
	@Override
	public int getDeliverySubscriptionLength() {
		return model.getDeliverySubscriptionLength();
	}

	/**
	 * Returns the delivery subscription type of this cp instance.
	 *
	 * @return the delivery subscription type of this cp instance
	 */
	@Override
	public String getDeliverySubscriptionType() {
		return model.getDeliverySubscriptionType();
	}

	/**
	 * Returns the delivery subscription type settings of this cp instance.
	 *
	 * @return the delivery subscription type settings of this cp instance
	 */
	@Override
	public String getDeliverySubscriptionTypeSettings() {
		return model.getDeliverySubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getDeliverySubscriptionTypeSettingsProperties() {

		return model.getDeliverySubscriptionTypeSettingsProperties();
	}

	/**
	 * Returns the depth of this cp instance.
	 *
	 * @return the depth of this cp instance
	 */
	@Override
	public double getDepth() {
		return model.getDepth();
	}

	/**
	 * Returns the discontinued of this cp instance.
	 *
	 * @return the discontinued of this cp instance
	 */
	@Override
	public boolean getDiscontinued() {
		return model.getDiscontinued();
	}

	/**
	 * Returns the discontinued date of this cp instance.
	 *
	 * @return the discontinued date of this cp instance
	 */
	@Override
	public Date getDiscontinuedDate() {
		return model.getDiscontinuedDate();
	}

	/**
	 * Returns the display date of this cp instance.
	 *
	 * @return the display date of this cp instance
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this cp instance.
	 *
	 * @return the expiration date of this cp instance
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this cp instance.
	 *
	 * @return the external reference code of this cp instance
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this cp instance.
	 *
	 * @return the group ID of this cp instance
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the gtin of this cp instance.
	 *
	 * @return the gtin of this cp instance
	 */
	@Override
	public String getGtin() {
		return model.getGtin();
	}

	/**
	 * Returns the height of this cp instance.
	 *
	 * @return the height of this cp instance
	 */
	@Override
	public double getHeight() {
		return model.getHeight();
	}

	/**
	 * Returns the last publish date of this cp instance.
	 *
	 * @return the last publish date of this cp instance
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the manufacturer part number of this cp instance.
	 *
	 * @return the manufacturer part number of this cp instance
	 */
	@Override
	public String getManufacturerPartNumber() {
		return model.getManufacturerPartNumber();
	}

	/**
	 * Returns the max subscription cycles of this cp instance.
	 *
	 * @return the max subscription cycles of this cp instance
	 */
	@Override
	public long getMaxSubscriptionCycles() {
		return model.getMaxSubscriptionCycles();
	}

	/**
	 * Returns the modified date of this cp instance.
	 *
	 * @return the modified date of this cp instance
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cp instance.
	 *
	 * @return the mvcc version of this cp instance
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the override subscription info of this cp instance.
	 *
	 * @return the override subscription info of this cp instance
	 */
	@Override
	public boolean getOverrideSubscriptionInfo() {
		return model.getOverrideSubscriptionInfo();
	}

	/**
	 * Returns the price of this cp instance.
	 *
	 * @return the price of this cp instance
	 */
	@Override
	public BigDecimal getPrice() {
		return model.getPrice();
	}

	/**
	 * Returns the primary key of this cp instance.
	 *
	 * @return the primary key of this cp instance
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the promo price of this cp instance.
	 *
	 * @return the promo price of this cp instance
	 */
	@Override
	public BigDecimal getPromoPrice() {
		return model.getPromoPrice();
	}

	/**
	 * Returns the published of this cp instance.
	 *
	 * @return the published of this cp instance
	 */
	@Override
	public boolean getPublished() {
		return model.getPublished();
	}

	/**
	 * Returns the purchasable of this cp instance.
	 *
	 * @return the purchasable of this cp instance
	 */
	@Override
	public boolean getPurchasable() {
		return model.getPurchasable();
	}

	/**
	 * Returns the replacement cp instance uuid of this cp instance.
	 *
	 * @return the replacement cp instance uuid of this cp instance
	 */
	@Override
	public String getReplacementCPInstanceUuid() {
		return model.getReplacementCPInstanceUuid();
	}

	/**
	 * Returns the replacement c product ID of this cp instance.
	 *
	 * @return the replacement c product ID of this cp instance
	 */
	@Override
	public long getReplacementCProductId() {
		return model.getReplacementCProductId();
	}

	/**
	 * Returns the sku of this cp instance.
	 *
	 * @return the sku of this cp instance
	 */
	@Override
	public String getSku() {
		return model.getSku();
	}

	/**
	 * Returns the status of this cp instance.
	 *
	 * @return the status of this cp instance
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this cp instance.
	 *
	 * @return the status by user ID of this cp instance
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this cp instance.
	 *
	 * @return the status by user name of this cp instance
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this cp instance.
	 *
	 * @return the status by user uuid of this cp instance
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this cp instance.
	 *
	 * @return the status date of this cp instance
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the subscription enabled of this cp instance.
	 *
	 * @return the subscription enabled of this cp instance
	 */
	@Override
	public boolean getSubscriptionEnabled() {
		return model.getSubscriptionEnabled();
	}

	/**
	 * Returns the subscription length of this cp instance.
	 *
	 * @return the subscription length of this cp instance
	 */
	@Override
	public int getSubscriptionLength() {
		return model.getSubscriptionLength();
	}

	/**
	 * Returns the subscription type of this cp instance.
	 *
	 * @return the subscription type of this cp instance
	 */
	@Override
	public String getSubscriptionType() {
		return model.getSubscriptionType();
	}

	/**
	 * Returns the subscription type settings of this cp instance.
	 *
	 * @return the subscription type settings of this cp instance
	 */
	@Override
	public String getSubscriptionTypeSettings() {
		return model.getSubscriptionTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getSubscriptionTypeSettingsProperties() {

		return model.getSubscriptionTypeSettingsProperties();
	}

	/**
	 * Returns the unspsc of this cp instance.
	 *
	 * @return the unspsc of this cp instance
	 */
	@Override
	public String getUnspsc() {
		return model.getUnspsc();
	}

	/**
	 * Returns the user ID of this cp instance.
	 *
	 * @return the user ID of this cp instance
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp instance.
	 *
	 * @return the user name of this cp instance
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp instance.
	 *
	 * @return the user uuid of this cp instance
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cp instance.
	 *
	 * @return the uuid of this cp instance
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the weight of this cp instance.
	 *
	 * @return the weight of this cp instance
	 */
	@Override
	public double getWeight() {
		return model.getWeight();
	}

	/**
	 * Returns the width of this cp instance.
	 *
	 * @return the width of this cp instance
	 */
	@Override
	public double getWidth() {
		return model.getWidth();
	}

	/**
	 * Returns <code>true</code> if this cp instance is approved.
	 *
	 * @return <code>true</code> if this cp instance is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this cp instance is delivery subscription enabled.
	 *
	 * @return <code>true</code> if this cp instance is delivery subscription enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isDeliverySubscriptionEnabled() {
		return model.isDeliverySubscriptionEnabled();
	}

	/**
	 * Returns <code>true</code> if this cp instance is denied.
	 *
	 * @return <code>true</code> if this cp instance is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this cp instance is discontinued.
	 *
	 * @return <code>true</code> if this cp instance is discontinued; <code>false</code> otherwise
	 */
	@Override
	public boolean isDiscontinued() {
		return model.isDiscontinued();
	}

	/**
	 * Returns <code>true</code> if this cp instance is a draft.
	 *
	 * @return <code>true</code> if this cp instance is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this cp instance is expired.
	 *
	 * @return <code>true</code> if this cp instance is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this cp instance is inactive.
	 *
	 * @return <code>true</code> if this cp instance is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this cp instance is incomplete.
	 *
	 * @return <code>true</code> if this cp instance is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this cp instance is override subscription info.
	 *
	 * @return <code>true</code> if this cp instance is override subscription info; <code>false</code> otherwise
	 */
	@Override
	public boolean isOverrideSubscriptionInfo() {
		return model.isOverrideSubscriptionInfo();
	}

	/**
	 * Returns <code>true</code> if this cp instance is pending.
	 *
	 * @return <code>true</code> if this cp instance is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this cp instance is published.
	 *
	 * @return <code>true</code> if this cp instance is published; <code>false</code> otherwise
	 */
	@Override
	public boolean isPublished() {
		return model.isPublished();
	}

	/**
	 * Returns <code>true</code> if this cp instance is purchasable.
	 *
	 * @return <code>true</code> if this cp instance is purchasable; <code>false</code> otherwise
	 */
	@Override
	public boolean isPurchasable() {
		return model.isPurchasable();
	}

	/**
	 * Returns <code>true</code> if this cp instance is scheduled.
	 *
	 * @return <code>true</code> if this cp instance is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	/**
	 * Returns <code>true</code> if this cp instance is subscription enabled.
	 *
	 * @return <code>true</code> if this cp instance is subscription enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isSubscriptionEnabled() {
		return model.isSubscriptionEnabled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cp instance.
	 *
	 * @param companyId the company ID of this cp instance
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cost of this cp instance.
	 *
	 * @param cost the cost of this cp instance
	 */
	@Override
	public void setCost(BigDecimal cost) {
		model.setCost(cost);
	}

	/**
	 * Sets the cp definition ID of this cp instance.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp instance
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the cp instance ID of this cp instance.
	 *
	 * @param CPInstanceId the cp instance ID of this cp instance
	 */
	@Override
	public void setCPInstanceId(long CPInstanceId) {
		model.setCPInstanceId(CPInstanceId);
	}

	/**
	 * Sets the cp instance uuid of this cp instance.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this cp instance
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		model.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the create date of this cp instance.
	 *
	 * @param createDate the create date of this cp instance
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this cp instance.
	 *
	 * @param ctCollectionId the ct collection ID of this cp instance
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the delivery max subscription cycles of this cp instance.
	 *
	 * @param deliveryMaxSubscriptionCycles the delivery max subscription cycles of this cp instance
	 */
	@Override
	public void setDeliveryMaxSubscriptionCycles(
		long deliveryMaxSubscriptionCycles) {

		model.setDeliveryMaxSubscriptionCycles(deliveryMaxSubscriptionCycles);
	}

	/**
	 * Sets whether this cp instance is delivery subscription enabled.
	 *
	 * @param deliverySubscriptionEnabled the delivery subscription enabled of this cp instance
	 */
	@Override
	public void setDeliverySubscriptionEnabled(
		boolean deliverySubscriptionEnabled) {

		model.setDeliverySubscriptionEnabled(deliverySubscriptionEnabled);
	}

	/**
	 * Sets the delivery subscription length of this cp instance.
	 *
	 * @param deliverySubscriptionLength the delivery subscription length of this cp instance
	 */
	@Override
	public void setDeliverySubscriptionLength(int deliverySubscriptionLength) {
		model.setDeliverySubscriptionLength(deliverySubscriptionLength);
	}

	/**
	 * Sets the delivery subscription type of this cp instance.
	 *
	 * @param deliverySubscriptionType the delivery subscription type of this cp instance
	 */
	@Override
	public void setDeliverySubscriptionType(String deliverySubscriptionType) {
		model.setDeliverySubscriptionType(deliverySubscriptionType);
	}

	/**
	 * Sets the delivery subscription type settings of this cp instance.
	 *
	 * @param deliverySubscriptionTypeSettings the delivery subscription type settings of this cp instance
	 */
	@Override
	public void setDeliverySubscriptionTypeSettings(
		String deliverySubscriptionTypeSettings) {

		model.setDeliverySubscriptionTypeSettings(
			deliverySubscriptionTypeSettings);
	}

	@Override
	public void setDeliverySubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			deliverySubscriptionTypeSettingsUnicodeProperties) {

		model.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
	}

	/**
	 * Sets the depth of this cp instance.
	 *
	 * @param depth the depth of this cp instance
	 */
	@Override
	public void setDepth(double depth) {
		model.setDepth(depth);
	}

	/**
	 * Sets whether this cp instance is discontinued.
	 *
	 * @param discontinued the discontinued of this cp instance
	 */
	@Override
	public void setDiscontinued(boolean discontinued) {
		model.setDiscontinued(discontinued);
	}

	/**
	 * Sets the discontinued date of this cp instance.
	 *
	 * @param discontinuedDate the discontinued date of this cp instance
	 */
	@Override
	public void setDiscontinuedDate(Date discontinuedDate) {
		model.setDiscontinuedDate(discontinuedDate);
	}

	/**
	 * Sets the display date of this cp instance.
	 *
	 * @param displayDate the display date of this cp instance
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this cp instance.
	 *
	 * @param expirationDate the expiration date of this cp instance
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this cp instance.
	 *
	 * @param externalReferenceCode the external reference code of this cp instance
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this cp instance.
	 *
	 * @param groupId the group ID of this cp instance
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the gtin of this cp instance.
	 *
	 * @param gtin the gtin of this cp instance
	 */
	@Override
	public void setGtin(String gtin) {
		model.setGtin(gtin);
	}

	/**
	 * Sets the height of this cp instance.
	 *
	 * @param height the height of this cp instance
	 */
	@Override
	public void setHeight(double height) {
		model.setHeight(height);
	}

	/**
	 * Sets the last publish date of this cp instance.
	 *
	 * @param lastPublishDate the last publish date of this cp instance
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the manufacturer part number of this cp instance.
	 *
	 * @param manufacturerPartNumber the manufacturer part number of this cp instance
	 */
	@Override
	public void setManufacturerPartNumber(String manufacturerPartNumber) {
		model.setManufacturerPartNumber(manufacturerPartNumber);
	}

	/**
	 * Sets the max subscription cycles of this cp instance.
	 *
	 * @param maxSubscriptionCycles the max subscription cycles of this cp instance
	 */
	@Override
	public void setMaxSubscriptionCycles(long maxSubscriptionCycles) {
		model.setMaxSubscriptionCycles(maxSubscriptionCycles);
	}

	/**
	 * Sets the modified date of this cp instance.
	 *
	 * @param modifiedDate the modified date of this cp instance
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cp instance.
	 *
	 * @param mvccVersion the mvcc version of this cp instance
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets whether this cp instance is override subscription info.
	 *
	 * @param overrideSubscriptionInfo the override subscription info of this cp instance
	 */
	@Override
	public void setOverrideSubscriptionInfo(boolean overrideSubscriptionInfo) {
		model.setOverrideSubscriptionInfo(overrideSubscriptionInfo);
	}

	/**
	 * Sets the price of this cp instance.
	 *
	 * @param price the price of this cp instance
	 */
	@Override
	public void setPrice(BigDecimal price) {
		model.setPrice(price);
	}

	/**
	 * Sets the primary key of this cp instance.
	 *
	 * @param primaryKey the primary key of this cp instance
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the promo price of this cp instance.
	 *
	 * @param promoPrice the promo price of this cp instance
	 */
	@Override
	public void setPromoPrice(BigDecimal promoPrice) {
		model.setPromoPrice(promoPrice);
	}

	/**
	 * Sets whether this cp instance is published.
	 *
	 * @param published the published of this cp instance
	 */
	@Override
	public void setPublished(boolean published) {
		model.setPublished(published);
	}

	/**
	 * Sets whether this cp instance is purchasable.
	 *
	 * @param purchasable the purchasable of this cp instance
	 */
	@Override
	public void setPurchasable(boolean purchasable) {
		model.setPurchasable(purchasable);
	}

	/**
	 * Sets the replacement cp instance uuid of this cp instance.
	 *
	 * @param replacementCPInstanceUuid the replacement cp instance uuid of this cp instance
	 */
	@Override
	public void setReplacementCPInstanceUuid(String replacementCPInstanceUuid) {
		model.setReplacementCPInstanceUuid(replacementCPInstanceUuid);
	}

	/**
	 * Sets the replacement c product ID of this cp instance.
	 *
	 * @param replacementCProductId the replacement c product ID of this cp instance
	 */
	@Override
	public void setReplacementCProductId(long replacementCProductId) {
		model.setReplacementCProductId(replacementCProductId);
	}

	/**
	 * Sets the sku of this cp instance.
	 *
	 * @param sku the sku of this cp instance
	 */
	@Override
	public void setSku(String sku) {
		model.setSku(sku);
	}

	/**
	 * Sets the status of this cp instance.
	 *
	 * @param status the status of this cp instance
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this cp instance.
	 *
	 * @param statusByUserId the status by user ID of this cp instance
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this cp instance.
	 *
	 * @param statusByUserName the status by user name of this cp instance
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this cp instance.
	 *
	 * @param statusByUserUuid the status by user uuid of this cp instance
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this cp instance.
	 *
	 * @param statusDate the status date of this cp instance
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets whether this cp instance is subscription enabled.
	 *
	 * @param subscriptionEnabled the subscription enabled of this cp instance
	 */
	@Override
	public void setSubscriptionEnabled(boolean subscriptionEnabled) {
		model.setSubscriptionEnabled(subscriptionEnabled);
	}

	/**
	 * Sets the subscription length of this cp instance.
	 *
	 * @param subscriptionLength the subscription length of this cp instance
	 */
	@Override
	public void setSubscriptionLength(int subscriptionLength) {
		model.setSubscriptionLength(subscriptionLength);
	}

	/**
	 * Sets the subscription type of this cp instance.
	 *
	 * @param subscriptionType the subscription type of this cp instance
	 */
	@Override
	public void setSubscriptionType(String subscriptionType) {
		model.setSubscriptionType(subscriptionType);
	}

	/**
	 * Sets the subscription type settings of this cp instance.
	 *
	 * @param subscriptionTypeSettings the subscription type settings of this cp instance
	 */
	@Override
	public void setSubscriptionTypeSettings(String subscriptionTypeSettings) {
		model.setSubscriptionTypeSettings(subscriptionTypeSettings);
	}

	@Override
	public void setSubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			subscriptionTypeSettingsUnicodeProperties) {

		model.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
	}

	/**
	 * Sets the unspsc of this cp instance.
	 *
	 * @param unspsc the unspsc of this cp instance
	 */
	@Override
	public void setUnspsc(String unspsc) {
		model.setUnspsc(unspsc);
	}

	/**
	 * Sets the user ID of this cp instance.
	 *
	 * @param userId the user ID of this cp instance
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp instance.
	 *
	 * @param userName the user name of this cp instance
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp instance.
	 *
	 * @param userUuid the user uuid of this cp instance
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cp instance.
	 *
	 * @param uuid the uuid of this cp instance
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the weight of this cp instance.
	 *
	 * @param weight the weight of this cp instance
	 */
	@Override
	public void setWeight(double weight) {
		model.setWeight(weight);
	}

	/**
	 * Sets the width of this cp instance.
	 *
	 * @param width the width of this cp instance
	 */
	@Override
	public void setWidth(double width) {
		model.setWidth(width);
	}

	@Override
	public Map<String, Function<CPInstance, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPInstance, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPInstanceWrapper wrap(CPInstance cpInstance) {
		return new CPInstanceWrapper(cpInstance);
	}

}