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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommercePriceList}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceList
 * @generated
 */
@ProviderType
public class CommercePriceListWrapper implements CommercePriceList,
	ModelWrapper<CommercePriceList> {
	public CommercePriceListWrapper(CommercePriceList commercePriceList) {
		_commercePriceList = commercePriceList;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceList.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceList.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("parentCommercePriceListId",
			getParentCommercePriceListId());
		attributes.put("commerceCurrencyId", getCommerceCurrencyId());
		attributes.put("name", getName());
		attributes.put("priority", getPriority());
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
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

		Long parentCommercePriceListId = (Long)attributes.get(
				"parentCommercePriceListId");

		if (parentCommercePriceListId != null) {
			setParentCommercePriceListId(parentCommercePriceListId);
		}

		Long commerceCurrencyId = (Long)attributes.get("commerceCurrencyId");

		if (commerceCurrencyId != null) {
			setCommerceCurrencyId(commerceCurrencyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
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
	public java.lang.Object clone() {
		return new CommercePriceListWrapper((CommercePriceList)_commercePriceList.clone());
	}

	@Override
	public int compareTo(CommercePriceList commercePriceList) {
		return _commercePriceList.compareTo(commercePriceList);
	}

	@Override
	public com.liferay.commerce.currency.model.CommerceCurrency getCommerceCurrency()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceList.getCommerceCurrency();
	}

	/**
	* Returns the commerce currency ID of this commerce price list.
	*
	* @return the commerce currency ID of this commerce price list
	*/
	@Override
	public long getCommerceCurrencyId() {
		return _commercePriceList.getCommerceCurrencyId();
	}

	/**
	* Returns the commerce price list ID of this commerce price list.
	*
	* @return the commerce price list ID of this commerce price list
	*/
	@Override
	public long getCommercePriceListId() {
		return _commercePriceList.getCommercePriceListId();
	}

	/**
	* Returns the company ID of this commerce price list.
	*
	* @return the company ID of this commerce price list
	*/
	@Override
	public long getCompanyId() {
		return _commercePriceList.getCompanyId();
	}

	/**
	* Returns the create date of this commerce price list.
	*
	* @return the create date of this commerce price list
	*/
	@Override
	public Date getCreateDate() {
		return _commercePriceList.getCreateDate();
	}

	/**
	* Returns the display date of this commerce price list.
	*
	* @return the display date of this commerce price list
	*/
	@Override
	public Date getDisplayDate() {
		return _commercePriceList.getDisplayDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceList.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this commerce price list.
	*
	* @return the expiration date of this commerce price list
	*/
	@Override
	public Date getExpirationDate() {
		return _commercePriceList.getExpirationDate();
	}

	/**
	* Returns the group ID of this commerce price list.
	*
	* @return the group ID of this commerce price list
	*/
	@Override
	public long getGroupId() {
		return _commercePriceList.getGroupId();
	}

	/**
	* Returns the last publish date of this commerce price list.
	*
	* @return the last publish date of this commerce price list
	*/
	@Override
	public Date getLastPublishDate() {
		return _commercePriceList.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce price list.
	*
	* @return the modified date of this commerce price list
	*/
	@Override
	public Date getModifiedDate() {
		return _commercePriceList.getModifiedDate();
	}

	/**
	* Returns the name of this commerce price list.
	*
	* @return the name of this commerce price list
	*/
	@Override
	public java.lang.String getName() {
		return _commercePriceList.getName();
	}

	/**
	* Returns the parent commerce price list ID of this commerce price list.
	*
	* @return the parent commerce price list ID of this commerce price list
	*/
	@Override
	public long getParentCommercePriceListId() {
		return _commercePriceList.getParentCommercePriceListId();
	}

	/**
	* Returns the primary key of this commerce price list.
	*
	* @return the primary key of this commerce price list
	*/
	@Override
	public long getPrimaryKey() {
		return _commercePriceList.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceList.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this commerce price list.
	*
	* @return the priority of this commerce price list
	*/
	@Override
	public double getPriority() {
		return _commercePriceList.getPriority();
	}

	/**
	* Returns the status of this commerce price list.
	*
	* @return the status of this commerce price list
	*/
	@Override
	public int getStatus() {
		return _commercePriceList.getStatus();
	}

	/**
	* Returns the status by user ID of this commerce price list.
	*
	* @return the status by user ID of this commerce price list
	*/
	@Override
	public long getStatusByUserId() {
		return _commercePriceList.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this commerce price list.
	*
	* @return the status by user name of this commerce price list
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _commercePriceList.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this commerce price list.
	*
	* @return the status by user uuid of this commerce price list
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _commercePriceList.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this commerce price list.
	*
	* @return the status date of this commerce price list
	*/
	@Override
	public Date getStatusDate() {
		return _commercePriceList.getStatusDate();
	}

	/**
	* Returns the user ID of this commerce price list.
	*
	* @return the user ID of this commerce price list
	*/
	@Override
	public long getUserId() {
		return _commercePriceList.getUserId();
	}

	/**
	* Returns the user name of this commerce price list.
	*
	* @return the user name of this commerce price list
	*/
	@Override
	public java.lang.String getUserName() {
		return _commercePriceList.getUserName();
	}

	/**
	* Returns the user uuid of this commerce price list.
	*
	* @return the user uuid of this commerce price list
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commercePriceList.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce price list.
	*
	* @return the uuid of this commerce price list
	*/
	@Override
	public java.lang.String getUuid() {
		return _commercePriceList.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceList.hashCode();
	}

	/**
	* Returns <code>true</code> if this commerce price list is approved.
	*
	* @return <code>true</code> if this commerce price list is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _commercePriceList.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceList.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this commerce price list is denied.
	*
	* @return <code>true</code> if this commerce price list is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _commercePriceList.isDenied();
	}

	/**
	* Returns <code>true</code> if this commerce price list is a draft.
	*
	* @return <code>true</code> if this commerce price list is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _commercePriceList.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceList.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this commerce price list is expired.
	*
	* @return <code>true</code> if this commerce price list is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _commercePriceList.isExpired();
	}

	/**
	* Returns <code>true</code> if this commerce price list is inactive.
	*
	* @return <code>true</code> if this commerce price list is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _commercePriceList.isInactive();
	}

	/**
	* Returns <code>true</code> if this commerce price list is incomplete.
	*
	* @return <code>true</code> if this commerce price list is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _commercePriceList.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _commercePriceList.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce price list is pending.
	*
	* @return <code>true</code> if this commerce price list is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _commercePriceList.isPending();
	}

	/**
	* Returns <code>true</code> if this commerce price list is scheduled.
	*
	* @return <code>true</code> if this commerce price list is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _commercePriceList.isScheduled();
	}

	@Override
	public void persist() {
		_commercePriceList.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceList.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce currency ID of this commerce price list.
	*
	* @param commerceCurrencyId the commerce currency ID of this commerce price list
	*/
	@Override
	public void setCommerceCurrencyId(long commerceCurrencyId) {
		_commercePriceList.setCommerceCurrencyId(commerceCurrencyId);
	}

	/**
	* Sets the commerce price list ID of this commerce price list.
	*
	* @param commercePriceListId the commerce price list ID of this commerce price list
	*/
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceList.setCommercePriceListId(commercePriceListId);
	}

	/**
	* Sets the company ID of this commerce price list.
	*
	* @param companyId the company ID of this commerce price list
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceList.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce price list.
	*
	* @param createDate the create date of this commerce price list
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceList.setCreateDate(createDate);
	}

	/**
	* Sets the display date of this commerce price list.
	*
	* @param displayDate the display date of this commerce price list
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_commercePriceList.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commercePriceList.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceList.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceList.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this commerce price list.
	*
	* @param expirationDate the expiration date of this commerce price list
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commercePriceList.setExpirationDate(expirationDate);
	}

	/**
	* Sets the group ID of this commerce price list.
	*
	* @param groupId the group ID of this commerce price list
	*/
	@Override
	public void setGroupId(long groupId) {
		_commercePriceList.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this commerce price list.
	*
	* @param lastPublishDate the last publish date of this commerce price list
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceList.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce price list.
	*
	* @param modifiedDate the modified date of this commerce price list
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceList.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this commerce price list.
	*
	* @param name the name of this commerce price list
	*/
	@Override
	public void setName(java.lang.String name) {
		_commercePriceList.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceList.setNew(n);
	}

	/**
	* Sets the parent commerce price list ID of this commerce price list.
	*
	* @param parentCommercePriceListId the parent commerce price list ID of this commerce price list
	*/
	@Override
	public void setParentCommercePriceListId(long parentCommercePriceListId) {
		_commercePriceList.setParentCommercePriceListId(parentCommercePriceListId);
	}

	/**
	* Sets the primary key of this commerce price list.
	*
	* @param primaryKey the primary key of this commerce price list
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceList.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceList.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this commerce price list.
	*
	* @param priority the priority of this commerce price list
	*/
	@Override
	public void setPriority(double priority) {
		_commercePriceList.setPriority(priority);
	}

	/**
	* Sets the status of this commerce price list.
	*
	* @param status the status of this commerce price list
	*/
	@Override
	public void setStatus(int status) {
		_commercePriceList.setStatus(status);
	}

	/**
	* Sets the status by user ID of this commerce price list.
	*
	* @param statusByUserId the status by user ID of this commerce price list
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commercePriceList.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this commerce price list.
	*
	* @param statusByUserName the status by user name of this commerce price list
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_commercePriceList.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this commerce price list.
	*
	* @param statusByUserUuid the status by user uuid of this commerce price list
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_commercePriceList.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this commerce price list.
	*
	* @param statusDate the status date of this commerce price list
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_commercePriceList.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this commerce price list.
	*
	* @param userId the user ID of this commerce price list
	*/
	@Override
	public void setUserId(long userId) {
		_commercePriceList.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce price list.
	*
	* @param userName the user name of this commerce price list
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commercePriceList.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce price list.
	*
	* @param userUuid the user uuid of this commerce price list
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commercePriceList.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce price list.
	*
	* @param uuid the uuid of this commerce price list
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commercePriceList.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceList> toCacheModel() {
		return _commercePriceList.toCacheModel();
	}

	@Override
	public CommercePriceList toEscapedModel() {
		return new CommercePriceListWrapper(_commercePriceList.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _commercePriceList.toString();
	}

	@Override
	public CommercePriceList toUnescapedModel() {
		return new CommercePriceListWrapper(_commercePriceList.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _commercePriceList.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommercePriceListWrapper)) {
			return false;
		}

		CommercePriceListWrapper commercePriceListWrapper = (CommercePriceListWrapper)obj;

		if (Objects.equals(_commercePriceList,
					commercePriceListWrapper._commercePriceList)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceList.getStagedModelType();
	}

	@Override
	public CommercePriceList getWrappedModel() {
		return _commercePriceList;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceList.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceList.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceList.resetOriginalValues();
	}

	private final CommercePriceList _commercePriceList;
}