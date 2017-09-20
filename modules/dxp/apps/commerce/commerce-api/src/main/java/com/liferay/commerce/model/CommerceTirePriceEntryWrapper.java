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
 * This class is a wrapper for {@link CommerceTirePriceEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntry
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryWrapper implements CommerceTirePriceEntry,
	ModelWrapper<CommerceTirePriceEntry> {
	public CommerceTirePriceEntryWrapper(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		_commerceTirePriceEntry = commerceTirePriceEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceTirePriceEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceTirePriceEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CommerceTirePriceEntryId", getCommerceTirePriceEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceEntryId", getCommercePriceEntryId());
		attributes.put("price", getPrice());
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

		Long CommerceTirePriceEntryId = (Long)attributes.get(
				"CommerceTirePriceEntryId");

		if (CommerceTirePriceEntryId != null) {
			setCommerceTirePriceEntryId(CommerceTirePriceEntryId);
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

		Double price = (Double)attributes.get("price");

		if (price != null) {
			setPrice(price);
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
	public java.lang.Object clone() {
		return new CommerceTirePriceEntryWrapper((CommerceTirePriceEntry)_commerceTirePriceEntry.clone());
	}

	@Override
	public int compareTo(CommerceTirePriceEntry commerceTirePriceEntry) {
		return _commerceTirePriceEntry.compareTo(commerceTirePriceEntry);
	}

	@Override
	public CommercePriceEntry getCommercePriceEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntry.getCommercePriceEntry();
	}

	/**
	* Returns the commerce price entry ID of this commerce tire price entry.
	*
	* @return the commerce price entry ID of this commerce tire price entry
	*/
	@Override
	public long getCommercePriceEntryId() {
		return _commerceTirePriceEntry.getCommercePriceEntryId();
	}

	/**
	* Returns the commerce tire price entry ID of this commerce tire price entry.
	*
	* @return the commerce tire price entry ID of this commerce tire price entry
	*/
	@Override
	public long getCommerceTirePriceEntryId() {
		return _commerceTirePriceEntry.getCommerceTirePriceEntryId();
	}

	/**
	* Returns the company ID of this commerce tire price entry.
	*
	* @return the company ID of this commerce tire price entry
	*/
	@Override
	public long getCompanyId() {
		return _commerceTirePriceEntry.getCompanyId();
	}

	/**
	* Returns the create date of this commerce tire price entry.
	*
	* @return the create date of this commerce tire price entry
	*/
	@Override
	public Date getCreateDate() {
		return _commerceTirePriceEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceTirePriceEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this commerce tire price entry.
	*
	* @return the group ID of this commerce tire price entry
	*/
	@Override
	public long getGroupId() {
		return _commerceTirePriceEntry.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce tire price entry.
	*
	* @return the last publish date of this commerce tire price entry
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceTirePriceEntry.getLastPublishDate();
	}

	/**
	* Returns the min quantity of this commerce tire price entry.
	*
	* @return the min quantity of this commerce tire price entry
	*/
	@Override
	public int getMinQuantity() {
		return _commerceTirePriceEntry.getMinQuantity();
	}

	/**
	* Returns the modified date of this commerce tire price entry.
	*
	* @return the modified date of this commerce tire price entry
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceTirePriceEntry.getModifiedDate();
	}

	/**
	* Returns the price of this commerce tire price entry.
	*
	* @return the price of this commerce tire price entry
	*/
	@Override
	public double getPrice() {
		return _commerceTirePriceEntry.getPrice();
	}

	/**
	* Returns the primary key of this commerce tire price entry.
	*
	* @return the primary key of this commerce tire price entry
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceTirePriceEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceTirePriceEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this commerce tire price entry.
	*
	* @return the user ID of this commerce tire price entry
	*/
	@Override
	public long getUserId() {
		return _commerceTirePriceEntry.getUserId();
	}

	/**
	* Returns the user name of this commerce tire price entry.
	*
	* @return the user name of this commerce tire price entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceTirePriceEntry.getUserName();
	}

	/**
	* Returns the user uuid of this commerce tire price entry.
	*
	* @return the user uuid of this commerce tire price entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceTirePriceEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce tire price entry.
	*
	* @return the uuid of this commerce tire price entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceTirePriceEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceTirePriceEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceTirePriceEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceTirePriceEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceTirePriceEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceTirePriceEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceTirePriceEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce price entry ID of this commerce tire price entry.
	*
	* @param commercePriceEntryId the commerce price entry ID of this commerce tire price entry
	*/
	@Override
	public void setCommercePriceEntryId(long commercePriceEntryId) {
		_commerceTirePriceEntry.setCommercePriceEntryId(commercePriceEntryId);
	}

	/**
	* Sets the commerce tire price entry ID of this commerce tire price entry.
	*
	* @param CommerceTirePriceEntryId the commerce tire price entry ID of this commerce tire price entry
	*/
	@Override
	public void setCommerceTirePriceEntryId(long CommerceTirePriceEntryId) {
		_commerceTirePriceEntry.setCommerceTirePriceEntryId(CommerceTirePriceEntryId);
	}

	/**
	* Sets the company ID of this commerce tire price entry.
	*
	* @param companyId the company ID of this commerce tire price entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceTirePriceEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce tire price entry.
	*
	* @param createDate the create date of this commerce tire price entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceTirePriceEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceTirePriceEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceTirePriceEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceTirePriceEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this commerce tire price entry.
	*
	* @param groupId the group ID of this commerce tire price entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceTirePriceEntry.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce tire price entry.
	*
	* @param lastPublishDate the last publish date of this commerce tire price entry
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceTirePriceEntry.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the min quantity of this commerce tire price entry.
	*
	* @param minQuantity the min quantity of this commerce tire price entry
	*/
	@Override
	public void setMinQuantity(int minQuantity) {
		_commerceTirePriceEntry.setMinQuantity(minQuantity);
	}

	/**
	* Sets the modified date of this commerce tire price entry.
	*
	* @param modifiedDate the modified date of this commerce tire price entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceTirePriceEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceTirePriceEntry.setNew(n);
	}

	/**
	* Sets the price of this commerce tire price entry.
	*
	* @param price the price of this commerce tire price entry
	*/
	@Override
	public void setPrice(double price) {
		_commerceTirePriceEntry.setPrice(price);
	}

	/**
	* Sets the primary key of this commerce tire price entry.
	*
	* @param primaryKey the primary key of this commerce tire price entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceTirePriceEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceTirePriceEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this commerce tire price entry.
	*
	* @param userId the user ID of this commerce tire price entry
	*/
	@Override
	public void setUserId(long userId) {
		_commerceTirePriceEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce tire price entry.
	*
	* @param userName the user name of this commerce tire price entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceTirePriceEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce tire price entry.
	*
	* @param userUuid the user uuid of this commerce tire price entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceTirePriceEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce tire price entry.
	*
	* @param uuid the uuid of this commerce tire price entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceTirePriceEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceTirePriceEntry> toCacheModel() {
		return _commerceTirePriceEntry.toCacheModel();
	}

	@Override
	public CommerceTirePriceEntry toEscapedModel() {
		return new CommerceTirePriceEntryWrapper(_commerceTirePriceEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commerceTirePriceEntry.toString();
	}

	@Override
	public CommerceTirePriceEntry toUnescapedModel() {
		return new CommerceTirePriceEntryWrapper(_commerceTirePriceEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceTirePriceEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTirePriceEntryWrapper)) {
			return false;
		}

		CommerceTirePriceEntryWrapper commerceTirePriceEntryWrapper = (CommerceTirePriceEntryWrapper)obj;

		if (Objects.equals(_commerceTirePriceEntry,
					commerceTirePriceEntryWrapper._commerceTirePriceEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceTirePriceEntry.getStagedModelType();
	}

	@Override
	public CommerceTirePriceEntry getWrappedModel() {
		return _commerceTirePriceEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceTirePriceEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceTirePriceEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceTirePriceEntry.resetOriginalValues();
	}

	private final CommerceTirePriceEntry _commerceTirePriceEntry;
}