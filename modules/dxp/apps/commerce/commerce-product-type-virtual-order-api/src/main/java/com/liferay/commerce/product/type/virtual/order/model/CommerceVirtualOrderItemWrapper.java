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

package com.liferay.commerce.product.type.virtual.order.model;

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
 * This class is a wrapper for {@link CommerceVirtualOrderItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItem
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemWrapper implements CommerceVirtualOrderItem,
	ModelWrapper<CommerceVirtualOrderItem> {
	public CommerceVirtualOrderItemWrapper(
		CommerceVirtualOrderItem commerceVirtualOrderItem) {
		_commerceVirtualOrderItem = commerceVirtualOrderItem;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceVirtualOrderItem.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceVirtualOrderItem.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceVirtualOrderItemId",
			getCommerceVirtualOrderItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("activationStatus", getActivationStatus());
		attributes.put("duration", getDuration());
		attributes.put("usages", getUsages());
		attributes.put("maxUsages", getMaxUsages());
		attributes.put("active", isActive());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceVirtualOrderItemId = (Long)attributes.get(
				"commerceVirtualOrderItemId");

		if (commerceVirtualOrderItemId != null) {
			setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
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

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		Integer activationStatus = (Integer)attributes.get("activationStatus");

		if (activationStatus != null) {
			setActivationStatus(activationStatus);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Integer usages = (Integer)attributes.get("usages");

		if (usages != null) {
			setUsages(usages);
		}

		Integer maxUsages = (Integer)attributes.get("maxUsages");

		if (maxUsages != null) {
			setMaxUsages(maxUsages);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}
	}

	@Override
	public Object clone() {
		return new CommerceVirtualOrderItemWrapper((CommerceVirtualOrderItem)_commerceVirtualOrderItem.clone());
	}

	@Override
	public int compareTo(CommerceVirtualOrderItem commerceVirtualOrderItem) {
		return _commerceVirtualOrderItem.compareTo(commerceVirtualOrderItem);
	}

	/**
	* Returns the activation status of this commerce virtual order item.
	*
	* @return the activation status of this commerce virtual order item
	*/
	@Override
	public int getActivationStatus() {
		return _commerceVirtualOrderItem.getActivationStatus();
	}

	/**
	* Returns the active of this commerce virtual order item.
	*
	* @return the active of this commerce virtual order item
	*/
	@Override
	public boolean getActive() {
		return _commerceVirtualOrderItem.getActive();
	}

	@Override
	public com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItem.getCommerceOrderItem();
	}

	/**
	* Returns the commerce order item ID of this commerce virtual order item.
	*
	* @return the commerce order item ID of this commerce virtual order item
	*/
	@Override
	public long getCommerceOrderItemId() {
		return _commerceVirtualOrderItem.getCommerceOrderItemId();
	}

	/**
	* Returns the commerce virtual order item ID of this commerce virtual order item.
	*
	* @return the commerce virtual order item ID of this commerce virtual order item
	*/
	@Override
	public long getCommerceVirtualOrderItemId() {
		return _commerceVirtualOrderItem.getCommerceVirtualOrderItemId();
	}

	/**
	* Returns the company ID of this commerce virtual order item.
	*
	* @return the company ID of this commerce virtual order item
	*/
	@Override
	public long getCompanyId() {
		return _commerceVirtualOrderItem.getCompanyId();
	}

	/**
	* Returns the create date of this commerce virtual order item.
	*
	* @return the create date of this commerce virtual order item
	*/
	@Override
	public Date getCreateDate() {
		return _commerceVirtualOrderItem.getCreateDate();
	}

	/**
	* Returns the duration of this commerce virtual order item.
	*
	* @return the duration of this commerce virtual order item
	*/
	@Override
	public long getDuration() {
		return _commerceVirtualOrderItem.getDuration();
	}

	/**
	* Returns the end date of this commerce virtual order item.
	*
	* @return the end date of this commerce virtual order item
	*/
	@Override
	public Date getEndDate() {
		return _commerceVirtualOrderItem.getEndDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceVirtualOrderItem.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceVirtualOrderItem.getFileEntry();
	}

	/**
	* Returns the file entry ID of this commerce virtual order item.
	*
	* @return the file entry ID of this commerce virtual order item
	*/
	@Override
	public long getFileEntryId() {
		return _commerceVirtualOrderItem.getFileEntryId();
	}

	/**
	* Returns the group ID of this commerce virtual order item.
	*
	* @return the group ID of this commerce virtual order item
	*/
	@Override
	public long getGroupId() {
		return _commerceVirtualOrderItem.getGroupId();
	}

	/**
	* Returns the max usages of this commerce virtual order item.
	*
	* @return the max usages of this commerce virtual order item
	*/
	@Override
	public int getMaxUsages() {
		return _commerceVirtualOrderItem.getMaxUsages();
	}

	/**
	* Returns the modified date of this commerce virtual order item.
	*
	* @return the modified date of this commerce virtual order item
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceVirtualOrderItem.getModifiedDate();
	}

	/**
	* Returns the primary key of this commerce virtual order item.
	*
	* @return the primary key of this commerce virtual order item
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceVirtualOrderItem.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceVirtualOrderItem.getPrimaryKeyObj();
	}

	/**
	* Returns the start date of this commerce virtual order item.
	*
	* @return the start date of this commerce virtual order item
	*/
	@Override
	public Date getStartDate() {
		return _commerceVirtualOrderItem.getStartDate();
	}

	/**
	* Returns the url of this commerce virtual order item.
	*
	* @return the url of this commerce virtual order item
	*/
	@Override
	public String getUrl() {
		return _commerceVirtualOrderItem.getUrl();
	}

	/**
	* Returns the usages of this commerce virtual order item.
	*
	* @return the usages of this commerce virtual order item
	*/
	@Override
	public int getUsages() {
		return _commerceVirtualOrderItem.getUsages();
	}

	/**
	* Returns the user ID of this commerce virtual order item.
	*
	* @return the user ID of this commerce virtual order item
	*/
	@Override
	public long getUserId() {
		return _commerceVirtualOrderItem.getUserId();
	}

	/**
	* Returns the user name of this commerce virtual order item.
	*
	* @return the user name of this commerce virtual order item
	*/
	@Override
	public String getUserName() {
		return _commerceVirtualOrderItem.getUserName();
	}

	/**
	* Returns the user uuid of this commerce virtual order item.
	*
	* @return the user uuid of this commerce virtual order item
	*/
	@Override
	public String getUserUuid() {
		return _commerceVirtualOrderItem.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce virtual order item.
	*
	* @return the uuid of this commerce virtual order item
	*/
	@Override
	public String getUuid() {
		return _commerceVirtualOrderItem.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceVirtualOrderItem.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce virtual order item is active.
	*
	* @return <code>true</code> if this commerce virtual order item is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _commerceVirtualOrderItem.isActive();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceVirtualOrderItem.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceVirtualOrderItem.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceVirtualOrderItem.isNew();
	}

	@Override
	public void persist() {
		_commerceVirtualOrderItem.persist();
	}

	/**
	* Sets the activation status of this commerce virtual order item.
	*
	* @param activationStatus the activation status of this commerce virtual order item
	*/
	@Override
	public void setActivationStatus(int activationStatus) {
		_commerceVirtualOrderItem.setActivationStatus(activationStatus);
	}

	/**
	* Sets whether this commerce virtual order item is active.
	*
	* @param active the active of this commerce virtual order item
	*/
	@Override
	public void setActive(boolean active) {
		_commerceVirtualOrderItem.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceVirtualOrderItem.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce order item ID of this commerce virtual order item.
	*
	* @param commerceOrderItemId the commerce order item ID of this commerce virtual order item
	*/
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		_commerceVirtualOrderItem.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	* Sets the commerce virtual order item ID of this commerce virtual order item.
	*
	* @param commerceVirtualOrderItemId the commerce virtual order item ID of this commerce virtual order item
	*/
	@Override
	public void setCommerceVirtualOrderItemId(long commerceVirtualOrderItemId) {
		_commerceVirtualOrderItem.setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
	}

	/**
	* Sets the company ID of this commerce virtual order item.
	*
	* @param companyId the company ID of this commerce virtual order item
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceVirtualOrderItem.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce virtual order item.
	*
	* @param createDate the create date of this commerce virtual order item
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceVirtualOrderItem.setCreateDate(createDate);
	}

	/**
	* Sets the duration of this commerce virtual order item.
	*
	* @param duration the duration of this commerce virtual order item
	*/
	@Override
	public void setDuration(long duration) {
		_commerceVirtualOrderItem.setDuration(duration);
	}

	/**
	* Sets the end date of this commerce virtual order item.
	*
	* @param endDate the end date of this commerce virtual order item
	*/
	@Override
	public void setEndDate(Date endDate) {
		_commerceVirtualOrderItem.setEndDate(endDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceVirtualOrderItem.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceVirtualOrderItem.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceVirtualOrderItem.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this commerce virtual order item.
	*
	* @param fileEntryId the file entry ID of this commerce virtual order item
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_commerceVirtualOrderItem.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this commerce virtual order item.
	*
	* @param groupId the group ID of this commerce virtual order item
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceVirtualOrderItem.setGroupId(groupId);
	}

	/**
	* Sets the max usages of this commerce virtual order item.
	*
	* @param maxUsages the max usages of this commerce virtual order item
	*/
	@Override
	public void setMaxUsages(int maxUsages) {
		_commerceVirtualOrderItem.setMaxUsages(maxUsages);
	}

	/**
	* Sets the modified date of this commerce virtual order item.
	*
	* @param modifiedDate the modified date of this commerce virtual order item
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceVirtualOrderItem.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceVirtualOrderItem.setNew(n);
	}

	/**
	* Sets the primary key of this commerce virtual order item.
	*
	* @param primaryKey the primary key of this commerce virtual order item
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceVirtualOrderItem.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceVirtualOrderItem.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the start date of this commerce virtual order item.
	*
	* @param startDate the start date of this commerce virtual order item
	*/
	@Override
	public void setStartDate(Date startDate) {
		_commerceVirtualOrderItem.setStartDate(startDate);
	}

	/**
	* Sets the url of this commerce virtual order item.
	*
	* @param url the url of this commerce virtual order item
	*/
	@Override
	public void setUrl(String url) {
		_commerceVirtualOrderItem.setUrl(url);
	}

	/**
	* Sets the usages of this commerce virtual order item.
	*
	* @param usages the usages of this commerce virtual order item
	*/
	@Override
	public void setUsages(int usages) {
		_commerceVirtualOrderItem.setUsages(usages);
	}

	/**
	* Sets the user ID of this commerce virtual order item.
	*
	* @param userId the user ID of this commerce virtual order item
	*/
	@Override
	public void setUserId(long userId) {
		_commerceVirtualOrderItem.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce virtual order item.
	*
	* @param userName the user name of this commerce virtual order item
	*/
	@Override
	public void setUserName(String userName) {
		_commerceVirtualOrderItem.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce virtual order item.
	*
	* @param userUuid the user uuid of this commerce virtual order item
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_commerceVirtualOrderItem.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce virtual order item.
	*
	* @param uuid the uuid of this commerce virtual order item
	*/
	@Override
	public void setUuid(String uuid) {
		_commerceVirtualOrderItem.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceVirtualOrderItem> toCacheModel() {
		return _commerceVirtualOrderItem.toCacheModel();
	}

	@Override
	public CommerceVirtualOrderItem toEscapedModel() {
		return new CommerceVirtualOrderItemWrapper(_commerceVirtualOrderItem.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceVirtualOrderItem.toString();
	}

	@Override
	public CommerceVirtualOrderItem toUnescapedModel() {
		return new CommerceVirtualOrderItemWrapper(_commerceVirtualOrderItem.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceVirtualOrderItem.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceVirtualOrderItemWrapper)) {
			return false;
		}

		CommerceVirtualOrderItemWrapper commerceVirtualOrderItemWrapper = (CommerceVirtualOrderItemWrapper)obj;

		if (Objects.equals(_commerceVirtualOrderItem,
					commerceVirtualOrderItemWrapper._commerceVirtualOrderItem)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceVirtualOrderItem.getStagedModelType();
	}

	@Override
	public CommerceVirtualOrderItem getWrappedModel() {
		return _commerceVirtualOrderItem;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceVirtualOrderItem.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceVirtualOrderItem.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceVirtualOrderItem.resetOriginalValues();
	}

	private final CommerceVirtualOrderItem _commerceVirtualOrderItem;
}