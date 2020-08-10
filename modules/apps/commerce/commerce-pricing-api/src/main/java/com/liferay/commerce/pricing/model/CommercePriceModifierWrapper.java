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

package com.liferay.commerce.pricing.model;

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
 * This class is a wrapper for {@link CommercePriceModifier}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifier
 * @generated
 */
public class CommercePriceModifierWrapper
	implements CommercePriceModifier, ModelWrapper<CommercePriceModifier> {

	public CommercePriceModifierWrapper(
		CommercePriceModifier commercePriceModifier) {

		_commercePriceModifier = commercePriceModifier;
	}

	@Override
	public Class<?> getModelClass() {
		return CommercePriceModifier.class;
	}

	@Override
	public String getModelClassName() {
		return CommercePriceModifier.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commercePriceModifierId", getCommercePriceModifierId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("title", getTitle());
		attributes.put("target", getTarget());
		attributes.put("modifierAmount", getModifierAmount());
		attributes.put("modifierType", getModifierType());
		attributes.put("priority", getPriority());
		attributes.put("active", isActive());
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

		Long commercePriceModifierId = (Long)attributes.get(
			"commercePriceModifierId");

		if (commercePriceModifierId != null) {
			setCommercePriceModifierId(commercePriceModifierId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String target = (String)attributes.get("target");

		if (target != null) {
			setTarget(target);
		}

		BigDecimal modifierAmount = (BigDecimal)attributes.get(
			"modifierAmount");

		if (modifierAmount != null) {
			setModifierAmount(modifierAmount);
		}

		String modifierType = (String)attributes.get("modifierType");

		if (modifierType != null) {
			setModifierType(modifierType);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
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
		return new CommercePriceModifierWrapper(
			(CommercePriceModifier)_commercePriceModifier.clone());
	}

	@Override
	public int compareTo(CommercePriceModifier commercePriceModifier) {
		return _commercePriceModifier.compareTo(commercePriceModifier);
	}

	/**
	 * Returns the active of this commerce price modifier.
	 *
	 * @return the active of this commerce price modifier
	 */
	@Override
	public boolean getActive() {
		return _commercePriceModifier.getActive();
	}

	/**
	 * Returns the commerce price list ID of this commerce price modifier.
	 *
	 * @return the commerce price list ID of this commerce price modifier
	 */
	@Override
	public long getCommercePriceListId() {
		return _commercePriceModifier.getCommercePriceListId();
	}

	/**
	 * Returns the commerce price modifier ID of this commerce price modifier.
	 *
	 * @return the commerce price modifier ID of this commerce price modifier
	 */
	@Override
	public long getCommercePriceModifierId() {
		return _commercePriceModifier.getCommercePriceModifierId();
	}

	/**
	 * Returns the company ID of this commerce price modifier.
	 *
	 * @return the company ID of this commerce price modifier
	 */
	@Override
	public long getCompanyId() {
		return _commercePriceModifier.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price modifier.
	 *
	 * @return the create date of this commerce price modifier
	 */
	@Override
	public Date getCreateDate() {
		return _commercePriceModifier.getCreateDate();
	}

	/**
	 * Returns the display date of this commerce price modifier.
	 *
	 * @return the display date of this commerce price modifier
	 */
	@Override
	public Date getDisplayDate() {
		return _commercePriceModifier.getDisplayDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commercePriceModifier.getExpandoBridge();
	}

	/**
	 * Returns the expiration date of this commerce price modifier.
	 *
	 * @return the expiration date of this commerce price modifier
	 */
	@Override
	public Date getExpirationDate() {
		return _commercePriceModifier.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce price modifier.
	 *
	 * @return the external reference code of this commerce price modifier
	 */
	@Override
	public String getExternalReferenceCode() {
		return _commercePriceModifier.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce price modifier.
	 *
	 * @return the group ID of this commerce price modifier
	 */
	@Override
	public long getGroupId() {
		return _commercePriceModifier.getGroupId();
	}

	/**
	 * Returns the last publish date of this commerce price modifier.
	 *
	 * @return the last publish date of this commerce price modifier
	 */
	@Override
	public Date getLastPublishDate() {
		return _commercePriceModifier.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price modifier.
	 *
	 * @return the modified date of this commerce price modifier
	 */
	@Override
	public Date getModifiedDate() {
		return _commercePriceModifier.getModifiedDate();
	}

	/**
	 * Returns the modifier amount of this commerce price modifier.
	 *
	 * @return the modifier amount of this commerce price modifier
	 */
	@Override
	public BigDecimal getModifierAmount() {
		return _commercePriceModifier.getModifierAmount();
	}

	/**
	 * Returns the modifier type of this commerce price modifier.
	 *
	 * @return the modifier type of this commerce price modifier
	 */
	@Override
	public String getModifierType() {
		return _commercePriceModifier.getModifierType();
	}

	/**
	 * Returns the primary key of this commerce price modifier.
	 *
	 * @return the primary key of this commerce price modifier
	 */
	@Override
	public long getPrimaryKey() {
		return _commercePriceModifier.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commercePriceModifier.getPrimaryKeyObj();
	}

	/**
	 * Returns the priority of this commerce price modifier.
	 *
	 * @return the priority of this commerce price modifier
	 */
	@Override
	public double getPriority() {
		return _commercePriceModifier.getPriority();
	}

	/**
	 * Returns the status of this commerce price modifier.
	 *
	 * @return the status of this commerce price modifier
	 */
	@Override
	public int getStatus() {
		return _commercePriceModifier.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce price modifier.
	 *
	 * @return the status by user ID of this commerce price modifier
	 */
	@Override
	public long getStatusByUserId() {
		return _commercePriceModifier.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce price modifier.
	 *
	 * @return the status by user name of this commerce price modifier
	 */
	@Override
	public String getStatusByUserName() {
		return _commercePriceModifier.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce price modifier.
	 *
	 * @return the status by user uuid of this commerce price modifier
	 */
	@Override
	public String getStatusByUserUuid() {
		return _commercePriceModifier.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce price modifier.
	 *
	 * @return the status date of this commerce price modifier
	 */
	@Override
	public Date getStatusDate() {
		return _commercePriceModifier.getStatusDate();
	}

	/**
	 * Returns the target of this commerce price modifier.
	 *
	 * @return the target of this commerce price modifier
	 */
	@Override
	public String getTarget() {
		return _commercePriceModifier.getTarget();
	}

	/**
	 * Returns the title of this commerce price modifier.
	 *
	 * @return the title of this commerce price modifier
	 */
	@Override
	public String getTitle() {
		return _commercePriceModifier.getTitle();
	}

	/**
	 * Returns the user ID of this commerce price modifier.
	 *
	 * @return the user ID of this commerce price modifier
	 */
	@Override
	public long getUserId() {
		return _commercePriceModifier.getUserId();
	}

	/**
	 * Returns the user name of this commerce price modifier.
	 *
	 * @return the user name of this commerce price modifier
	 */
	@Override
	public String getUserName() {
		return _commercePriceModifier.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price modifier.
	 *
	 * @return the user uuid of this commerce price modifier
	 */
	@Override
	public String getUserUuid() {
		return _commercePriceModifier.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price modifier.
	 *
	 * @return the uuid of this commerce price modifier
	 */
	@Override
	public String getUuid() {
		return _commercePriceModifier.getUuid();
	}

	@Override
	public int hashCode() {
		return _commercePriceModifier.hashCode();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is active.
	 *
	 * @return <code>true</code> if this commerce price modifier is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return _commercePriceModifier.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is approved.
	 *
	 * @return <code>true</code> if this commerce price modifier is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return _commercePriceModifier.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _commercePriceModifier.isCachedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is denied.
	 *
	 * @return <code>true</code> if this commerce price modifier is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return _commercePriceModifier.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is a draft.
	 *
	 * @return <code>true</code> if this commerce price modifier is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return _commercePriceModifier.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _commercePriceModifier.isEscapedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is expired.
	 *
	 * @return <code>true</code> if this commerce price modifier is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return _commercePriceModifier.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is inactive.
	 *
	 * @return <code>true</code> if this commerce price modifier is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return _commercePriceModifier.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is incomplete.
	 *
	 * @return <code>true</code> if this commerce price modifier is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return _commercePriceModifier.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _commercePriceModifier.isNew();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is pending.
	 *
	 * @return <code>true</code> if this commerce price modifier is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return _commercePriceModifier.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is scheduled.
	 *
	 * @return <code>true</code> if this commerce price modifier is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return _commercePriceModifier.isScheduled();
	}

	@Override
	public void persist() {
		_commercePriceModifier.persist();
	}

	/**
	 * Sets whether this commerce price modifier is active.
	 *
	 * @param active the active of this commerce price modifier
	 */
	@Override
	public void setActive(boolean active) {
		_commercePriceModifier.setActive(active);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commercePriceModifier.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce price list ID of this commerce price modifier.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price modifier
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		_commercePriceModifier.setCommercePriceListId(commercePriceListId);
	}

	/**
	 * Sets the commerce price modifier ID of this commerce price modifier.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID of this commerce price modifier
	 */
	@Override
	public void setCommercePriceModifierId(long commercePriceModifierId) {
		_commercePriceModifier.setCommercePriceModifierId(
			commercePriceModifierId);
	}

	/**
	 * Sets the company ID of this commerce price modifier.
	 *
	 * @param companyId the company ID of this commerce price modifier
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commercePriceModifier.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price modifier.
	 *
	 * @param createDate the create date of this commerce price modifier
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commercePriceModifier.setCreateDate(createDate);
	}

	/**
	 * Sets the display date of this commerce price modifier.
	 *
	 * @param displayDate the display date of this commerce price modifier
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		_commercePriceModifier.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commercePriceModifier.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commercePriceModifier.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commercePriceModifier.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the expiration date of this commerce price modifier.
	 *
	 * @param expirationDate the expiration date of this commerce price modifier
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commercePriceModifier.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce price modifier.
	 *
	 * @param externalReferenceCode the external reference code of this commerce price modifier
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_commercePriceModifier.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce price modifier.
	 *
	 * @param groupId the group ID of this commerce price modifier
	 */
	@Override
	public void setGroupId(long groupId) {
		_commercePriceModifier.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this commerce price modifier.
	 *
	 * @param lastPublishDate the last publish date of this commerce price modifier
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commercePriceModifier.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price modifier.
	 *
	 * @param modifiedDate the modified date of this commerce price modifier
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commercePriceModifier.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the modifier amount of this commerce price modifier.
	 *
	 * @param modifierAmount the modifier amount of this commerce price modifier
	 */
	@Override
	public void setModifierAmount(BigDecimal modifierAmount) {
		_commercePriceModifier.setModifierAmount(modifierAmount);
	}

	/**
	 * Sets the modifier type of this commerce price modifier.
	 *
	 * @param modifierType the modifier type of this commerce price modifier
	 */
	@Override
	public void setModifierType(String modifierType) {
		_commercePriceModifier.setModifierType(modifierType);
	}

	@Override
	public void setNew(boolean n) {
		_commercePriceModifier.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce price modifier.
	 *
	 * @param primaryKey the primary key of this commerce price modifier
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commercePriceModifier.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commercePriceModifier.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the priority of this commerce price modifier.
	 *
	 * @param priority the priority of this commerce price modifier
	 */
	@Override
	public void setPriority(double priority) {
		_commercePriceModifier.setPriority(priority);
	}

	/**
	 * Sets the status of this commerce price modifier.
	 *
	 * @param status the status of this commerce price modifier
	 */
	@Override
	public void setStatus(int status) {
		_commercePriceModifier.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce price modifier.
	 *
	 * @param statusByUserId the status by user ID of this commerce price modifier
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commercePriceModifier.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce price modifier.
	 *
	 * @param statusByUserName the status by user name of this commerce price modifier
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_commercePriceModifier.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce price modifier.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce price modifier
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_commercePriceModifier.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce price modifier.
	 *
	 * @param statusDate the status date of this commerce price modifier
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		_commercePriceModifier.setStatusDate(statusDate);
	}

	/**
	 * Sets the target of this commerce price modifier.
	 *
	 * @param target the target of this commerce price modifier
	 */
	@Override
	public void setTarget(String target) {
		_commercePriceModifier.setTarget(target);
	}

	/**
	 * Sets the title of this commerce price modifier.
	 *
	 * @param title the title of this commerce price modifier
	 */
	@Override
	public void setTitle(String title) {
		_commercePriceModifier.setTitle(title);
	}

	/**
	 * Sets the user ID of this commerce price modifier.
	 *
	 * @param userId the user ID of this commerce price modifier
	 */
	@Override
	public void setUserId(long userId) {
		_commercePriceModifier.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price modifier.
	 *
	 * @param userName the user name of this commerce price modifier
	 */
	@Override
	public void setUserName(String userName) {
		_commercePriceModifier.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price modifier.
	 *
	 * @param userUuid the user uuid of this commerce price modifier
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commercePriceModifier.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price modifier.
	 *
	 * @param uuid the uuid of this commerce price modifier
	 */
	@Override
	public void setUuid(String uuid) {
		_commercePriceModifier.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommercePriceModifier>
		toCacheModel() {

		return _commercePriceModifier.toCacheModel();
	}

	@Override
	public CommercePriceModifier toEscapedModel() {
		return new CommercePriceModifierWrapper(
			_commercePriceModifier.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commercePriceModifier.toString();
	}

	@Override
	public CommercePriceModifier toUnescapedModel() {
		return new CommercePriceModifierWrapper(
			_commercePriceModifier.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commercePriceModifier.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceModifierWrapper)) {
			return false;
		}

		CommercePriceModifierWrapper commercePriceModifierWrapper =
			(CommercePriceModifierWrapper)object;

		if (Objects.equals(
				_commercePriceModifier,
				commercePriceModifierWrapper._commercePriceModifier)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commercePriceModifier.getStagedModelType();
	}

	@Override
	public CommercePriceModifier getWrappedModel() {
		return _commercePriceModifier;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commercePriceModifier.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commercePriceModifier.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commercePriceModifier.resetOriginalValues();
	}

	private final CommercePriceModifier _commercePriceModifier;

}