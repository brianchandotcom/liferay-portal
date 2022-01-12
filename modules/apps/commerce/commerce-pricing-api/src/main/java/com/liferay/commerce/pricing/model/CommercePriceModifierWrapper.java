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
 * This class is a wrapper for {@link CommercePriceModifier}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifier
 * @generated
 */
public class CommercePriceModifierWrapper
	extends BaseModelWrapper<CommercePriceModifier>
	implements CommercePriceModifier, ModelWrapper<CommercePriceModifier> {

	public CommercePriceModifierWrapper(
		CommercePriceModifier commercePriceModifier) {

		super(commercePriceModifier);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
	public CommercePriceModifier cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this commerce price modifier.
	 *
	 * @return the active of this commerce price modifier
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the commerce price list ID of this commerce price modifier.
	 *
	 * @return the commerce price list ID of this commerce price modifier
	 */
	@Override
	public long getCommercePriceListId() {
		return model.getCommercePriceListId();
	}

	/**
	 * Returns the commerce price modifier ID of this commerce price modifier.
	 *
	 * @return the commerce price modifier ID of this commerce price modifier
	 */
	@Override
	public long getCommercePriceModifierId() {
		return model.getCommercePriceModifierId();
	}

	/**
	 * Returns the company ID of this commerce price modifier.
	 *
	 * @return the company ID of this commerce price modifier
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price modifier.
	 *
	 * @return the create date of this commerce price modifier
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce price modifier.
	 *
	 * @return the ct collection ID of this commerce price modifier
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the display date of this commerce price modifier.
	 *
	 * @return the display date of this commerce price modifier
	 */
	@Override
	public Date getDisplayDate() {
		return model.getDisplayDate();
	}

	/**
	 * Returns the expiration date of this commerce price modifier.
	 *
	 * @return the expiration date of this commerce price modifier
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the external reference code of this commerce price modifier.
	 *
	 * @return the external reference code of this commerce price modifier
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce price modifier.
	 *
	 * @return the group ID of this commerce price modifier
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this commerce price modifier.
	 *
	 * @return the last publish date of this commerce price modifier
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price modifier.
	 *
	 * @return the modified date of this commerce price modifier
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the modifier amount of this commerce price modifier.
	 *
	 * @return the modifier amount of this commerce price modifier
	 */
	@Override
	public BigDecimal getModifierAmount() {
		return model.getModifierAmount();
	}

	/**
	 * Returns the modifier type of this commerce price modifier.
	 *
	 * @return the modifier type of this commerce price modifier
	 */
	@Override
	public String getModifierType() {
		return model.getModifierType();
	}

	/**
	 * Returns the mvcc version of this commerce price modifier.
	 *
	 * @return the mvcc version of this commerce price modifier
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce price modifier.
	 *
	 * @return the primary key of this commerce price modifier
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce price modifier.
	 *
	 * @return the priority of this commerce price modifier
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the status of this commerce price modifier.
	 *
	 * @return the status of this commerce price modifier
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this commerce price modifier.
	 *
	 * @return the status by user ID of this commerce price modifier
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this commerce price modifier.
	 *
	 * @return the status by user name of this commerce price modifier
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this commerce price modifier.
	 *
	 * @return the status by user uuid of this commerce price modifier
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this commerce price modifier.
	 *
	 * @return the status date of this commerce price modifier
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the target of this commerce price modifier.
	 *
	 * @return the target of this commerce price modifier
	 */
	@Override
	public String getTarget() {
		return model.getTarget();
	}

	/**
	 * Returns the title of this commerce price modifier.
	 *
	 * @return the title of this commerce price modifier
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the user ID of this commerce price modifier.
	 *
	 * @return the user ID of this commerce price modifier
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce price modifier.
	 *
	 * @return the user name of this commerce price modifier
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price modifier.
	 *
	 * @return the user uuid of this commerce price modifier
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price modifier.
	 *
	 * @return the uuid of this commerce price modifier
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is active.
	 *
	 * @return <code>true</code> if this commerce price modifier is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is approved.
	 *
	 * @return <code>true</code> if this commerce price modifier is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is denied.
	 *
	 * @return <code>true</code> if this commerce price modifier is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is a draft.
	 *
	 * @return <code>true</code> if this commerce price modifier is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is expired.
	 *
	 * @return <code>true</code> if this commerce price modifier is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is inactive.
	 *
	 * @return <code>true</code> if this commerce price modifier is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is incomplete.
	 *
	 * @return <code>true</code> if this commerce price modifier is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is pending.
	 *
	 * @return <code>true</code> if this commerce price modifier is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this commerce price modifier is scheduled.
	 *
	 * @return <code>true</code> if this commerce price modifier is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this commerce price modifier is active.
	 *
	 * @param active the active of this commerce price modifier
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the commerce price list ID of this commerce price modifier.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price modifier
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		model.setCommercePriceListId(commercePriceListId);
	}

	/**
	 * Sets the commerce price modifier ID of this commerce price modifier.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID of this commerce price modifier
	 */
	@Override
	public void setCommercePriceModifierId(long commercePriceModifierId) {
		model.setCommercePriceModifierId(commercePriceModifierId);
	}

	/**
	 * Sets the company ID of this commerce price modifier.
	 *
	 * @param companyId the company ID of this commerce price modifier
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price modifier.
	 *
	 * @param createDate the create date of this commerce price modifier
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce price modifier.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce price modifier
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the display date of this commerce price modifier.
	 *
	 * @param displayDate the display date of this commerce price modifier
	 */
	@Override
	public void setDisplayDate(Date displayDate) {
		model.setDisplayDate(displayDate);
	}

	/**
	 * Sets the expiration date of this commerce price modifier.
	 *
	 * @param expirationDate the expiration date of this commerce price modifier
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the external reference code of this commerce price modifier.
	 *
	 * @param externalReferenceCode the external reference code of this commerce price modifier
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce price modifier.
	 *
	 * @param groupId the group ID of this commerce price modifier
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this commerce price modifier.
	 *
	 * @param lastPublishDate the last publish date of this commerce price modifier
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price modifier.
	 *
	 * @param modifiedDate the modified date of this commerce price modifier
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the modifier amount of this commerce price modifier.
	 *
	 * @param modifierAmount the modifier amount of this commerce price modifier
	 */
	@Override
	public void setModifierAmount(BigDecimal modifierAmount) {
		model.setModifierAmount(modifierAmount);
	}

	/**
	 * Sets the modifier type of this commerce price modifier.
	 *
	 * @param modifierType the modifier type of this commerce price modifier
	 */
	@Override
	public void setModifierType(String modifierType) {
		model.setModifierType(modifierType);
	}

	/**
	 * Sets the mvcc version of this commerce price modifier.
	 *
	 * @param mvccVersion the mvcc version of this commerce price modifier
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce price modifier.
	 *
	 * @param primaryKey the primary key of this commerce price modifier
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce price modifier.
	 *
	 * @param priority the priority of this commerce price modifier
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the status of this commerce price modifier.
	 *
	 * @param status the status of this commerce price modifier
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this commerce price modifier.
	 *
	 * @param statusByUserId the status by user ID of this commerce price modifier
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this commerce price modifier.
	 *
	 * @param statusByUserName the status by user name of this commerce price modifier
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this commerce price modifier.
	 *
	 * @param statusByUserUuid the status by user uuid of this commerce price modifier
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this commerce price modifier.
	 *
	 * @param statusDate the status date of this commerce price modifier
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the target of this commerce price modifier.
	 *
	 * @param target the target of this commerce price modifier
	 */
	@Override
	public void setTarget(String target) {
		model.setTarget(target);
	}

	/**
	 * Sets the title of this commerce price modifier.
	 *
	 * @param title the title of this commerce price modifier
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the user ID of this commerce price modifier.
	 *
	 * @param userId the user ID of this commerce price modifier
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price modifier.
	 *
	 * @param userName the user name of this commerce price modifier
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price modifier.
	 *
	 * @param userUuid the user uuid of this commerce price modifier
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price modifier.
	 *
	 * @param uuid the uuid of this commerce price modifier
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CommercePriceModifier, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommercePriceModifier, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommercePriceModifierWrapper wrap(
		CommercePriceModifier commercePriceModifier) {

		return new CommercePriceModifierWrapper(commercePriceModifier);
	}

}