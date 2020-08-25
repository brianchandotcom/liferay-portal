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

package com.liferay.commerce.price.list.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceTierPriceEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntry
 * @generated
 */
public class CommerceTierPriceEntryWrapper
	implements CommerceTierPriceEntry, ModelWrapper<CommerceTierPriceEntry> {

	public CommerceTierPriceEntryWrapper(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		_commerceTierPriceEntry = commerceTierPriceEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTierPriceEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTierPriceEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"commerceTierPriceEntryId", getCommerceTierPriceEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceEntryId", getCommercePriceEntryId());
		attributes.put("price", getPrice());
		attributes.put("promoPrice", getPromoPrice());
		attributes.put("discountDiscovery", isDiscountDiscovery());
		attributes.put("discountLevel1", getDiscountLevel1());
		attributes.put("discountLevel2", getDiscountLevel2());
		attributes.put("discountLevel3", getDiscountLevel3());
		attributes.put("discountLevel4", getDiscountLevel4());
		attributes.put("minQuantity", getMinQuantity());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceTierPriceEntryId = (Long)attributes.get(
			"commerceTierPriceEntryId");

		if (commerceTierPriceEntryId != null) {
			setCommerceTierPriceEntryId(commerceTierPriceEntryId);
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

		Long commercePriceEntryId = (Long)attributes.get(
			"commercePriceEntryId");

		if (commercePriceEntryId != null) {
			setCommercePriceEntryId(commercePriceEntryId);
		}

		BigDecimal price = (BigDecimal)attributes.get("price");

		if (price != null) {
			setPrice(price);
		}

		BigDecimal promoPrice = (BigDecimal)attributes.get("promoPrice");

		if (promoPrice != null) {
			setPromoPrice(promoPrice);
		}

		Boolean discountDiscovery = (Boolean)attributes.get(
			"discountDiscovery");

		if (discountDiscovery != null) {
			setDiscountDiscovery(discountDiscovery);
		}

		BigDecimal discountLevel1 = (BigDecimal)attributes.get(
			"discountLevel1");

		if (discountLevel1 != null) {
			setDiscountLevel1(discountLevel1);
		}

		BigDecimal discountLevel2 = (BigDecimal)attributes.get(
			"discountLevel2");

		if (discountLevel2 != null) {
			setDiscountLevel2(discountLevel2);
		}

		BigDecimal discountLevel3 = (BigDecimal)attributes.get(
			"discountLevel3");

		if (discountLevel3 != null) {
			setDiscountLevel3(discountLevel3);
		}

		BigDecimal discountLevel4 = (BigDecimal)attributes.get(
			"discountLevel4");

		if (discountLevel4 != null) {
			setDiscountLevel4(discountLevel4);
		}

		Integer minQuantity = (Integer)attributes.get("minQuantity");

		if (minQuantity != null) {
			setMinQuantity(minQuantity);
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
	public Object clone() {
		return new CommerceTierPriceEntryWrapper(
			(CommerceTierPriceEntry)_commerceTierPriceEntry.clone());
	}

	@Override
	public int compareTo(CommerceTierPriceEntry commerceTierPriceEntry) {
		return _commerceTierPriceEntry.compareTo(commerceTierPriceEntry);
	}

	@Override
	public CommercePriceEntry getCommercePriceEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntry.getCommercePriceEntry();
	}

	/**
	 * Returns the commerce price entry ID of this commerce tier price entry.
	 *
	 * @return the commerce price entry ID of this commerce tier price entry
	 */
	@Override
	public long getCommercePriceEntryId() {
		return _commerceTierPriceEntry.getCommercePriceEntryId();
	}

	/**
	 * Returns the commerce tier price entry ID of this commerce tier price entry.
	 *
	 * @return the commerce tier price entry ID of this commerce tier price entry
	 */
	@Override
	public long getCommerceTierPriceEntryId() {
		return _commerceTierPriceEntry.getCommerceTierPriceEntryId();
	}

	/**
	 * Returns the company ID of this commerce tier price entry.
	 *
	 * @return the company ID of this commerce tier price entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceTierPriceEntry.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce tier price entry.
	 *
	 * @return the create date of this commerce tier price entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceTierPriceEntry.getCreateDate();
	}

	/**
	 * Returns the discount discovery of this commerce tier price entry.
	 *
	 * @return the discount discovery of this commerce tier price entry
	 */
	@Override
	public boolean getDiscountDiscovery() {
		return _commerceTierPriceEntry.getDiscountDiscovery();
	}

	/**
	 * Returns the discount level1 of this commerce tier price entry.
	 *
	 * @return the discount level1 of this commerce tier price entry
	 */
	@Override
	public BigDecimal getDiscountLevel1() {
		return _commerceTierPriceEntry.getDiscountLevel1();
	}

	/**
	 * Returns the discount level2 of this commerce tier price entry.
	 *
	 * @return the discount level2 of this commerce tier price entry
	 */
	@Override
	public BigDecimal getDiscountLevel2() {
		return _commerceTierPriceEntry.getDiscountLevel2();
	}

	/**
	 * Returns the discount level3 of this commerce tier price entry.
	 *
	 * @return the discount level3 of this commerce tier price entry
	 */
	@Override
	public BigDecimal getDiscountLevel3() {
		return _commerceTierPriceEntry.getDiscountLevel3();
	}

	/**
	 * Returns the discount level4 of this commerce tier price entry.
	 *
	 * @return the discount level4 of this commerce tier price entry
	 */
	@Override
	public BigDecimal getDiscountLevel4() {
		return _commerceTierPriceEntry.getDiscountLevel4();
	}

	/**
	 * Returns the display date of this commerce tier price entry.
	 *
	 * @return the display date of this commerce tier price entry
	 */
	@Override
	public Date getDisplayDate() {
		return _commerceTierPriceEntry.getDisplayDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTierPriceEntry.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this commerce tier price entry.
	 *
	 * @return the expiration date of this commerce tier price entry
	 */
	@Override
	public Date getExpirationDate() {
		return _commerceTierPriceEntry.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce tier price entry.
	 *
	 * @return the external reference code of this commerce tier price entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commerceTierPriceEntry.getExternalReferenceCode();
	}

	/**
	 * Returns the last publish date of this commerce tier price entry.
	 *
	 * @return the last publish date of this commerce tier price entry
	 */
	@Override
	public Date getLastPublishDate() {
		return _commerceTierPriceEntry.getLastPublishDate();
	}

	/**
	 * Returns the min quantity of this commerce tier price entry.
	 *
	 * @return the min quantity of this commerce tier price entry
	 */
	@Override
	public int getMinQuantity() {
		return _commerceTierPriceEntry.getMinQuantity();
	}

	/**
	 * Returns the modified date of this commerce tier price entry.
	 *
	 * @return the modified date of this commerce tier price entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceTierPriceEntry.getModifiedDate();
	}

	/**
	 * Returns the price of this commerce tier price entry.
	 *
	 * @return the price of this commerce tier price entry
	 */
	@Override
	public BigDecimal getPrice() {
		return _commerceTierPriceEntry.getPrice();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getPriceMoney(
			long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntry.getPriceMoney(commerceCurrencyId);
	}

	/**
	 * Returns the primary key of this commerce tier price entry.
	 *
	 * @return the primary key of this commerce tier price entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceTierPriceEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTierPriceEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the promo price of this commerce tier price entry.
	 *
	 * @return the promo price of this commerce tier price entry
	 */
	@Override
	public BigDecimal getPromoPrice() {
		return _commerceTierPriceEntry.getPromoPrice();
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceMoney getPromoPriceMoney(
			long commerceCurrencyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntry.getPromoPriceMoney(commerceCurrencyId);
	}

	/**
	 * Returns the status of this commerce tier price entry.
	 *
	 * @return the status of this commerce tier price entry
	 */
	@Override
	public int getStatus() {
		return _commerceTierPriceEntry.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce tier price entry.
	 *
	 * @return the status by user ID of this commerce tier price entry
	 */
	@Override
	public long getStatusByUserId() {
		return _commerceTierPriceEntry.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce tier price entry.
	 *
	 * @return the status by user name of this commerce tier price entry
	 */
	@Override
	public String getStatusByUserName() {
		return _commerceTierPriceEntry.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce tier price entry.
	 *
	 * @return the status by user uuid of this commerce tier price entry
	 */
	@Override
	public String getStatusByUserUuid() {
		return _commerceTierPriceEntry.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce tier price entry.
	 *
	 * @return the status date of this commerce tier price entry
	 */
	@Override
	public Date getStatusDate() {
		return _commerceTierPriceEntry.getStatusDate();
	}

	/**
	 * Returns the user ID of this commerce tier price entry.
	 *
	 * @return the user ID of this commerce tier price entry
	 */
	@Override
	public long getUserId() {
		return _commerceTierPriceEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce tier price entry.
	 *
	 * @return the user name of this commerce tier price entry
	 */
	@Override
	public String getUserName() {
		return _commerceTierPriceEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce tier price entry.
	 *
	 * @return the user uuid of this commerce tier price entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceTierPriceEntry.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce tier price entry.
	 *
	 * @return the uuid of this commerce tier price entry
	 */
	@Override
	public String getUuid() {
		return _commerceTierPriceEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTierPriceEntry.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is approved.
	 *
	 * @return <code>true</code> if this commerce tier price entry is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return _commerceTierPriceEntry.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTierPriceEntry.isCachedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is denied.
	 *
	 * @return <code>true</code> if this commerce tier price entry is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return _commerceTierPriceEntry.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is discount discovery.
	 *
	 * @return <code>true</code> if this commerce tier price entry is discount discovery; <code>false</code> otherwise
	 */
	@Override
	public boolean isDiscountDiscovery() {
		return _commerceTierPriceEntry.isDiscountDiscovery();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is a draft.
	 *
	 * @return <code>true</code> if this commerce tier price entry is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return _commerceTierPriceEntry.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTierPriceEntry.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is expired.
	 *
	 * @return <code>true</code> if this commerce tier price entry is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return _commerceTierPriceEntry.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is inactive.
	 *
	 * @return <code>true</code> if this commerce tier price entry is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return _commerceTierPriceEntry.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is incomplete.
	 *
	 * @return <code>true</code> if this commerce tier price entry is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return _commerceTierPriceEntry.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _commerceTierPriceEntry.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is pending.
	 *
	 * @return <code>true</code> if this commerce tier price entry is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return _commerceTierPriceEntry.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce tier price entry is scheduled.
	 *
	 * @return <code>true</code> if this commerce tier price entry is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return _commerceTierPriceEntry.isScheduled();
	}

	@Override
	public void persist() {
		_commerceTierPriceEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTierPriceEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce price entry ID of this commerce tier price entry.
	 *
	 * @param commercePriceEntryId the commerce price entry ID of this commerce tier price entry
	 */
	@Override
	public void setCommercePriceEntryId(long commercePriceEntryId) {
		_commerceTierPriceEntry.setCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	 * Sets the commerce tier price entry ID of this commerce tier price entry.
	 *
	 * @param commerceTierPriceEntryId the commerce tier price entry ID of this commerce tier price entry
	 */
	@Override
	public void setCommerceTierPriceEntryId(long commerceTierPriceEntryId) {
		_commerceTierPriceEntry.setCommerceTierPriceEntryId(
			commerceTierPriceEntryId);
	}

	/**
	 * Sets the company ID of this commerce tier price entry.
	 *
	 * @param companyId the company ID of this commerce tier price entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceTierPriceEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce tier price entry.
	 *
	 * @param createDate the create date of this commerce tier price entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTierPriceEntry.setCreateDate(createDate);
	}

	/**
	 * Sets whether this commerce tier price entry is discount discovery.
	 *
	 * @param discountDiscovery the discount discovery of this commerce tier price entry
	 */
	@Override
	public void setDiscountDiscovery(boolean discountDiscovery) {
		_commerceTierPriceEntry.setDiscountDiscovery(discountDiscovery);
	}

	/**
	 * Sets the discount level1 of this commerce tier price entry.
	 *
	 * @param discountLevel1 the discount level1 of this commerce tier price entry
	 */
	@Override
	public void setDiscountLevel1(BigDecimal discountLevel1) {
		_commerceTierPriceEntry.setDiscountLevel1(discountLevel1);
	}

	/**
	 * Sets the discount level2 of this commerce tier price entry.
	 *
	 * @param discountLevel2 the discount level2 of this commerce tier price entry
	 */
	@Override
	public void setDiscountLevel2(BigDecimal discountLevel2) {
		_commerceTierPriceEntry.setDiscountLevel2(discountLevel2);
	}

	/**
	 * Sets the discount level3 of this commerce tier price entry.
	 *
	 * @param discountLevel3 the discount level3 of this commerce tier price entry
	 */
	@Override
	public void setDiscountLevel3(BigDecimal discountLevel3) {
		_commerceTierPriceEntry.setDiscountLevel3(discountLevel3);
	}

	/**
	 * Sets the discount level4 of this commerce tier price entry.
	 *
	 * @param discountLevel4 the discount level4 of this commerce tier price entry
	 */
	@Override
	public void setDiscountLevel4(BigDecimal discountLevel4) {
		_commerceTierPriceEntry.setDiscountLevel4(discountLevel4);
	}

	/**
	 * Sets the display date of this commerce tier price entry.
	 *
	 * @param displayDate the display date of this commerce tier price entry
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		_commerceTierPriceEntry.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceTierPriceEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTierPriceEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the expiration date of this commerce tier price entry.
	 *
	 * @param expirationDate the expiration date of this commerce tier price entry
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commerceTierPriceEntry.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce tier price entry.
	 *
	 * @param externalReferenceCode the external reference code of this commerce tier price entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commerceTierPriceEntry.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the last publish date of this commerce tier price entry.
	 *
	 * @param lastPublishDate the last publish date of this commerce tier price entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceTierPriceEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the min quantity of this commerce tier price entry.
	 *
	 * @param minQuantity the min quantity of this commerce tier price entry
	 */
	@Override
	public void setMinQuantity(int minQuantity) {
		_commerceTierPriceEntry.setMinQuantity(minQuantity);
	}

	/**
	 * Sets the modified date of this commerce tier price entry.
	 *
	 * @param modifiedDate the modified date of this commerce tier price entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTierPriceEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTierPriceEntry.setNew(n);
	}

	/**
	 * Sets the price of this commerce tier price entry.
	 *
	 * @param price the price of this commerce tier price entry
	 */
	@Override
	public void setPrice(BigDecimal price) {
		_commerceTierPriceEntry.setPrice(price);
	}

	/**
	 * Sets the primary key of this commerce tier price entry.
	 *
	 * @param primaryKey the primary key of this commerce tier price entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTierPriceEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTierPriceEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the promo price of this commerce tier price entry.
	 *
	 * @param promoPrice the promo price of this commerce tier price entry
	 */
	@Override
	public void setPromoPrice(BigDecimal promoPrice) {
		_commerceTierPriceEntry.setPromoPrice(promoPrice);
	}

	/**
	 * Sets the status of this commerce tier price entry.
	 *
	 * @param status the status of this commerce tier price entry
	 */
	@Override
	public void setStatus(int status) {
		_commerceTierPriceEntry.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce tier price entry.
	 *
	 * @param statusByUserId the status by user ID of this commerce tier price entry
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commerceTierPriceEntry.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce tier price entry.
	 *
	 * @param statusByUserName the status by user name of this commerce tier price entry
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_commerceTierPriceEntry.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce tier price entry.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce tier price entry
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_commerceTierPriceEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce tier price entry.
	 *
	 * @param statusDate the status date of this commerce tier price entry
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		_commerceTierPriceEntry.setStatusDate(statusDate);
	}

	/**
	 * Sets the user ID of this commerce tier price entry.
	 *
	 * @param userId the user ID of this commerce tier price entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceTierPriceEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce tier price entry.
	 *
	 * @param userName the user name of this commerce tier price entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceTierPriceEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce tier price entry.
	 *
	 * @param userUuid the user uuid of this commerce tier price entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceTierPriceEntry.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce tier price entry.
	 *
	 * @param uuid the uuid of this commerce tier price entry
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceTierPriceEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTierPriceEntry>
		toCacheModel() {

		return _commerceTierPriceEntry.toCacheModel();
	}

	@Override
	public CommerceTierPriceEntry toEscapedModel() {
		return new CommerceTierPriceEntryWrapper(
			_commerceTierPriceEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceTierPriceEntry.toString();
	}

	@Override
	public CommerceTierPriceEntry toUnescapedModel() {
		return new CommerceTierPriceEntryWrapper(
			_commerceTierPriceEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceTierPriceEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTierPriceEntryWrapper)) {
			return false;
		}

		CommerceTierPriceEntryWrapper commerceTierPriceEntryWrapper =
			(CommerceTierPriceEntryWrapper)object;

		if (Objects.equals(
				_commerceTierPriceEntry,
				commerceTierPriceEntryWrapper._commerceTierPriceEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceTierPriceEntry.getStagedModelType();
	}

	@Override
	public CommerceTierPriceEntry getWrappedModel() {
		return _commerceTierPriceEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTierPriceEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTierPriceEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTierPriceEntry.resetOriginalValues();
	}

	private final CommerceTierPriceEntry _commerceTierPriceEntry;

}