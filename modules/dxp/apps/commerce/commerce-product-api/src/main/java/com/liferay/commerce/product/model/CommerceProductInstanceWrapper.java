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
 * This class is a wrapper for {@link CommerceProductInstance}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductInstance
 * @generated
 */
@ProviderType
public class CommerceProductInstanceWrapper implements CommerceProductInstance,
	ModelWrapper<CommerceProductInstance> {
	public CommerceProductInstanceWrapper(
		CommerceProductInstance commerceProductInstance) {
		_commerceProductInstance = commerceProductInstance;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductInstance.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductInstance.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("commerceProductInstanceId",
			getCommerceProductInstanceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductDefinitionId",
			getCommerceProductDefinitionId());
		attributes.put("sku", getSku());
		attributes.put("LSIN", getLSIN());
		attributes.put("DDMContent", getDDMContent());
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

		Long commerceProductInstanceId = (Long)attributes.get(
				"commerceProductInstanceId");

		if (commerceProductInstanceId != null) {
			setCommerceProductInstanceId(commerceProductInstanceId);
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

		Long commerceProductDefinitionId = (Long)attributes.get(
				"commerceProductDefinitionId");

		if (commerceProductDefinitionId != null) {
			setCommerceProductDefinitionId(commerceProductDefinitionId);
		}

		String sku = (String)attributes.get("sku");

		if (sku != null) {
			setSku(sku);
		}

		String LSIN = (String)attributes.get("LSIN");

		if (LSIN != null) {
			setLSIN(LSIN);
		}

		String DDMContent = (String)attributes.get("DDMContent");

		if (DDMContent != null) {
			setDDMContent(DDMContent);
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
	public CommerceProductInstance toEscapedModel() {
		return new CommerceProductInstanceWrapper(_commerceProductInstance.toEscapedModel());
	}

	@Override
	public CommerceProductInstance toUnescapedModel() {
		return new CommerceProductInstanceWrapper(_commerceProductInstance.toUnescapedModel());
	}

	/**
	* Returns <code>true</code> if this commerce product instance is approved.
	*
	* @return <code>true</code> if this commerce product instance is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _commerceProductInstance.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceProductInstance.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is denied.
	*
	* @return <code>true</code> if this commerce product instance is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _commerceProductInstance.isDenied();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is a draft.
	*
	* @return <code>true</code> if this commerce product instance is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _commerceProductInstance.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceProductInstance.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is expired.
	*
	* @return <code>true</code> if this commerce product instance is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _commerceProductInstance.isExpired();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is in the Recycle Bin.
	*
	* @return <code>true</code> if this commerce product instance is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _commerceProductInstance.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this commerce product instance is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this commerce product instance is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _commerceProductInstance.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _commerceProductInstance.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _commerceProductInstance.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is inactive.
	*
	* @return <code>true</code> if this commerce product instance is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _commerceProductInstance.isInactive();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is incomplete.
	*
	* @return <code>true</code> if this commerce product instance is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _commerceProductInstance.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _commerceProductInstance.isNew();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is pending.
	*
	* @return <code>true</code> if this commerce product instance is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _commerceProductInstance.isPending();
	}

	/**
	* Returns <code>true</code> if this commerce product instance is scheduled.
	*
	* @return <code>true</code> if this commerce product instance is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _commerceProductInstance.isScheduled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceProductInstance.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceProductInstance> toCacheModel() {
		return _commerceProductInstance.toCacheModel();
	}

	/**
	* Returns the trash handler for this commerce product instance.
	*
	* @return the trash handler for this commerce product instance
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _commerceProductInstance.getTrashHandler();
	}

	/**
	* Returns the trash entry created when this commerce product instance was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this commerce product instance.
	*
	* @return the trash entry created when this commerce product instance was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductInstance.getTrashEntry();
	}

	@Override
	public int compareTo(CommerceProductInstance commerceProductInstance) {
		return _commerceProductInstance.compareTo(commerceProductInstance);
	}

	/**
	* Returns the status of this commerce product instance.
	*
	* @return the status of this commerce product instance
	*/
	@Override
	public int getStatus() {
		return _commerceProductInstance.getStatus();
	}

	@Override
	public int hashCode() {
		return _commerceProductInstance.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductInstance.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CommerceProductInstanceWrapper((CommerceProductInstance)_commerceProductInstance.clone());
	}

	/**
	* Returns the ddm content of this commerce product instance.
	*
	* @return the ddm content of this commerce product instance
	*/
	@Override
	public java.lang.String getDDMContent() {
		return _commerceProductInstance.getDDMContent();
	}

	/**
	* Returns the lsin of this commerce product instance.
	*
	* @return the lsin of this commerce product instance
	*/
	@Override
	public java.lang.String getLSIN() {
		return _commerceProductInstance.getLSIN();
	}

	/**
	* Returns the sku of this commerce product instance.
	*
	* @return the sku of this commerce product instance
	*/
	@Override
	public java.lang.String getSku() {
		return _commerceProductInstance.getSku();
	}

	/**
	* Returns the status by user name of this commerce product instance.
	*
	* @return the status by user name of this commerce product instance
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _commerceProductInstance.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this commerce product instance.
	*
	* @return the status by user uuid of this commerce product instance
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _commerceProductInstance.getStatusByUserUuid();
	}

	/**
	* Returns the user name of this commerce product instance.
	*
	* @return the user name of this commerce product instance
	*/
	@Override
	public java.lang.String getUserName() {
		return _commerceProductInstance.getUserName();
	}

	/**
	* Returns the user uuid of this commerce product instance.
	*
	* @return the user uuid of this commerce product instance
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _commerceProductInstance.getUserUuid();
	}

	/**
	* Returns the uuid of this commerce product instance.
	*
	* @return the uuid of this commerce product instance
	*/
	@Override
	public java.lang.String getUuid() {
		return _commerceProductInstance.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _commerceProductInstance.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _commerceProductInstance.toXmlString();
	}

	/**
	* Returns the create date of this commerce product instance.
	*
	* @return the create date of this commerce product instance
	*/
	@Override
	public Date getCreateDate() {
		return _commerceProductInstance.getCreateDate();
	}

	/**
	* Returns the display date of this commerce product instance.
	*
	* @return the display date of this commerce product instance
	*/
	@Override
	public Date getDisplayDate() {
		return _commerceProductInstance.getDisplayDate();
	}

	/**
	* Returns the expiration date of this commerce product instance.
	*
	* @return the expiration date of this commerce product instance
	*/
	@Override
	public Date getExpirationDate() {
		return _commerceProductInstance.getExpirationDate();
	}

	/**
	* Returns the last publish date of this commerce product instance.
	*
	* @return the last publish date of this commerce product instance
	*/
	@Override
	public Date getLastPublishDate() {
		return _commerceProductInstance.getLastPublishDate();
	}

	/**
	* Returns the modified date of this commerce product instance.
	*
	* @return the modified date of this commerce product instance
	*/
	@Override
	public Date getModifiedDate() {
		return _commerceProductInstance.getModifiedDate();
	}

	/**
	* Returns the status date of this commerce product instance.
	*
	* @return the status date of this commerce product instance
	*/
	@Override
	public Date getStatusDate() {
		return _commerceProductInstance.getStatusDate();
	}

	/**
	* Returns the commerce product definition ID of this commerce product instance.
	*
	* @return the commerce product definition ID of this commerce product instance
	*/
	@Override
	public long getCommerceProductDefinitionId() {
		return _commerceProductInstance.getCommerceProductDefinitionId();
	}

	/**
	* Returns the commerce product instance ID of this commerce product instance.
	*
	* @return the commerce product instance ID of this commerce product instance
	*/
	@Override
	public long getCommerceProductInstanceId() {
		return _commerceProductInstance.getCommerceProductInstanceId();
	}

	/**
	* Returns the company ID of this commerce product instance.
	*
	* @return the company ID of this commerce product instance
	*/
	@Override
	public long getCompanyId() {
		return _commerceProductInstance.getCompanyId();
	}

	/**
	* Returns the group ID of this commerce product instance.
	*
	* @return the group ID of this commerce product instance
	*/
	@Override
	public long getGroupId() {
		return _commerceProductInstance.getGroupId();
	}

	/**
	* Returns the primary key of this commerce product instance.
	*
	* @return the primary key of this commerce product instance
	*/
	@Override
	public long getPrimaryKey() {
		return _commerceProductInstance.getPrimaryKey();
	}

	/**
	* Returns the status by user ID of this commerce product instance.
	*
	* @return the status by user ID of this commerce product instance
	*/
	@Override
	public long getStatusByUserId() {
		return _commerceProductInstance.getStatusByUserId();
	}

	/**
	* Returns the class primary key of the trash entry for this commerce product instance.
	*
	* @return the class primary key of the trash entry for this commerce product instance
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _commerceProductInstance.getTrashEntryClassPK();
	}

	/**
	* Returns the user ID of this commerce product instance.
	*
	* @return the user ID of this commerce product instance
	*/
	@Override
	public long getUserId() {
		return _commerceProductInstance.getUserId();
	}

	@Override
	public void persist() {
		_commerceProductInstance.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceProductInstance.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce product definition ID of this commerce product instance.
	*
	* @param commerceProductDefinitionId the commerce product definition ID of this commerce product instance
	*/
	@Override
	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_commerceProductInstance.setCommerceProductDefinitionId(commerceProductDefinitionId);
	}

	/**
	* Sets the commerce product instance ID of this commerce product instance.
	*
	* @param commerceProductInstanceId the commerce product instance ID of this commerce product instance
	*/
	@Override
	public void setCommerceProductInstanceId(long commerceProductInstanceId) {
		_commerceProductInstance.setCommerceProductInstanceId(commerceProductInstanceId);
	}

	/**
	* Sets the company ID of this commerce product instance.
	*
	* @param companyId the company ID of this commerce product instance
	*/
	@Override
	public void setCompanyId(long companyId) {
		_commerceProductInstance.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this commerce product instance.
	*
	* @param createDate the create date of this commerce product instance
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_commerceProductInstance.setCreateDate(createDate);
	}

	/**
	* Sets the ddm content of this commerce product instance.
	*
	* @param DDMContent the ddm content of this commerce product instance
	*/
	@Override
	public void setDDMContent(java.lang.String DDMContent) {
		_commerceProductInstance.setDDMContent(DDMContent);
	}

	/**
	* Sets the display date of this commerce product instance.
	*
	* @param displayDate the display date of this commerce product instance
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_commerceProductInstance.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceProductInstance.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_commerceProductInstance.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceProductInstance.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this commerce product instance.
	*
	* @param expirationDate the expiration date of this commerce product instance
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_commerceProductInstance.setExpirationDate(expirationDate);
	}

	/**
	* Sets the group ID of this commerce product instance.
	*
	* @param groupId the group ID of this commerce product instance
	*/
	@Override
	public void setGroupId(long groupId) {
		_commerceProductInstance.setGroupId(groupId);
	}

	/**
	* Sets the lsin of this commerce product instance.
	*
	* @param LSIN the lsin of this commerce product instance
	*/
	@Override
	public void setLSIN(java.lang.String LSIN) {
		_commerceProductInstance.setLSIN(LSIN);
	}

	/**
	* Sets the last publish date of this commerce product instance.
	*
	* @param lastPublishDate the last publish date of this commerce product instance
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_commerceProductInstance.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this commerce product instance.
	*
	* @param modifiedDate the modified date of this commerce product instance
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceProductInstance.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceProductInstance.setNew(n);
	}

	/**
	* Sets the primary key of this commerce product instance.
	*
	* @param primaryKey the primary key of this commerce product instance
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceProductInstance.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceProductInstance.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sku of this commerce product instance.
	*
	* @param sku the sku of this commerce product instance
	*/
	@Override
	public void setSku(java.lang.String sku) {
		_commerceProductInstance.setSku(sku);
	}

	/**
	* Sets the status of this commerce product instance.
	*
	* @param status the status of this commerce product instance
	*/
	@Override
	public void setStatus(int status) {
		_commerceProductInstance.setStatus(status);
	}

	/**
	* Sets the status by user ID of this commerce product instance.
	*
	* @param statusByUserId the status by user ID of this commerce product instance
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_commerceProductInstance.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this commerce product instance.
	*
	* @param statusByUserName the status by user name of this commerce product instance
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_commerceProductInstance.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this commerce product instance.
	*
	* @param statusByUserUuid the status by user uuid of this commerce product instance
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_commerceProductInstance.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this commerce product instance.
	*
	* @param statusDate the status date of this commerce product instance
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_commerceProductInstance.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this commerce product instance.
	*
	* @param userId the user ID of this commerce product instance
	*/
	@Override
	public void setUserId(long userId) {
		_commerceProductInstance.setUserId(userId);
	}

	/**
	* Sets the user name of this commerce product instance.
	*
	* @param userName the user name of this commerce product instance
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_commerceProductInstance.setUserName(userName);
	}

	/**
	* Sets the user uuid of this commerce product instance.
	*
	* @param userUuid the user uuid of this commerce product instance
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_commerceProductInstance.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this commerce product instance.
	*
	* @param uuid the uuid of this commerce product instance
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_commerceProductInstance.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductInstanceWrapper)) {
			return false;
		}

		CommerceProductInstanceWrapper commerceProductInstanceWrapper = (CommerceProductInstanceWrapper)obj;

		if (Objects.equals(_commerceProductInstance,
					commerceProductInstanceWrapper._commerceProductInstance)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceProductInstance.getStagedModelType();
	}

	@Override
	public CommerceProductInstance getWrappedModel() {
		return _commerceProductInstance;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceProductInstance.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceProductInstance.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceProductInstance.resetOriginalValues();
	}

	private final CommerceProductInstance _commerceProductInstance;
}