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

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class CommerceTierPriceEntryWrapper implements CommerceTierPriceEntry,
	ModelWrapper<CommerceTierPriceEntry> {
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
		attributes.put("commerceTierPriceEntryId", getCommerceTierPriceEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceEntryId", getCommercePriceEntryId());
		attributes.put("price", getPrice());
		attributes.put("promoPrice", getPromoPrice());
		attributes.put("minQuantity", getMinQuantity());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceTierPriceEntryId = (Long)attributes.get(
				"commerceTierPriceEntryId");

		if (commerceTierPriceEntryId != null) {
			setCommerceTierPriceEntryId(commerceTierPriceEntryId);
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

		Long commercePriceEntryId = (Long)attributes.get("commercePriceEntryId");

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

		Integer minQuantity = (Integer)attributes.get("minQuantity");

		if (minQuantity != null) {
			setMinQuantity(minQuantity);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceTierPriceEntryWrapper((CommerceTierPriceEntry)_commerceTierPriceEntry.clone());
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

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTierPriceEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tier price entry.
	*
	* @return the group ID of this commerce tier price entry
	*/
	@Override
	public long getGroupId() {
		return _commerceTierPriceEntry.getGroupId();
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

	@Override
	public boolean isCachedModel() {
		return _commerceTierPriceEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTierPriceEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTierPriceEntry.isNew();
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
		_commerceTierPriceEntry.setCommerceTierPriceEntryId(commerceTierPriceEntryId);
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
	* Sets the group ID of this commerce tier price entry.
	*
	* @param groupId the group ID of this commerce tier price entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTierPriceEntry.setGroupId(groupId);
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
	public com.liferay.portal.kernel.model.CacheModel<CommerceTierPriceEntry> toCacheModel() {
		return _commerceTierPriceEntry.toCacheModel();
	}

	@Override
	public CommerceTierPriceEntry toEscapedModel() {
		return new CommerceTierPriceEntryWrapper(_commerceTierPriceEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceTierPriceEntry.toString();
	}

	@Override
	public CommerceTierPriceEntry toUnescapedModel() {
		return new CommerceTierPriceEntryWrapper(_commerceTierPriceEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceTierPriceEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTierPriceEntryWrapper)) {
			return false;
		}

		CommerceTierPriceEntryWrapper commerceTierPriceEntryWrapper = (CommerceTierPriceEntryWrapper)obj;

		if (Objects.equals(_commerceTierPriceEntry,
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