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

package com.liferay.commerce.notification.model;

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
 * This class is a wrapper for {@link CommerceNotificationAttachment}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationAttachment
 * @generated
 */
public class CommerceNotificationAttachmentWrapper
	implements CommerceNotificationAttachment,
			   ModelWrapper<CommerceNotificationAttachment> {

	public CommerceNotificationAttachmentWrapper(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		_commerceNotificationAttachment = commerceNotificationAttachment;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceNotificationAttachment.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceNotificationAttachment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put(
			"commerceNotificationAttachmentId",
			getCommerceNotificationAttachmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceNotificationQueueEntryId",
			getCommerceNotificationQueueEntryId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("deleteOnSend", isDeleteOnSend());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commerceNotificationAttachmentId = (Long)attributes.get(
			"commerceNotificationAttachmentId");

		if (commerceNotificationAttachmentId != null) {
			setCommerceNotificationAttachmentId(
				commerceNotificationAttachmentId);
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

		Long commerceNotificationQueueEntryId = (Long)attributes.get(
			"commerceNotificationQueueEntryId");

		if (commerceNotificationQueueEntryId != null) {
			setCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Boolean deleteOnSend = (Boolean)attributes.get("deleteOnSend");

		if (deleteOnSend != null) {
			setDeleteOnSend(deleteOnSend);
		}
	}

	@Override
	public Object clone() {
		return new CommerceNotificationAttachmentWrapper(
			(CommerceNotificationAttachment)
				_commerceNotificationAttachment.clone());
	}

	@Override
	public int compareTo(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		return _commerceNotificationAttachment.compareTo(
			commerceNotificationAttachment);
	}

	/**
	 * Returns the commerce notification attachment ID of this commerce notification attachment.
	 *
	 * @return the commerce notification attachment ID of this commerce notification attachment
	 */
	@Override
	public long getCommerceNotificationAttachmentId() {
		return _commerceNotificationAttachment.
			getCommerceNotificationAttachmentId();
	}

	/**
	 * Returns the commerce notification queue entry ID of this commerce notification attachment.
	 *
	 * @return the commerce notification queue entry ID of this commerce notification attachment
	 */
	@Override
	public long getCommerceNotificationQueueEntryId() {
		return _commerceNotificationAttachment.
			getCommerceNotificationQueueEntryId();
	}

	/**
	 * Returns the company ID of this commerce notification attachment.
	 *
	 * @return the company ID of this commerce notification attachment
	 */
	@Override
	public long getCompanyId() {
		return _commerceNotificationAttachment.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification attachment.
	 *
	 * @return the create date of this commerce notification attachment
	 */
	@Override
	public Date getCreateDate() {
		return _commerceNotificationAttachment.getCreateDate();
	}

	/**
	 * Returns the delete on send of this commerce notification attachment.
	 *
	 * @return the delete on send of this commerce notification attachment
	 */
	@Override
	public boolean getDeleteOnSend() {
		return _commerceNotificationAttachment.getDeleteOnSend();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceNotificationAttachment.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceNotificationAttachment.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this commerce notification attachment.
	 *
	 * @return the file entry ID of this commerce notification attachment
	 */
	@Override
	public long getFileEntryId() {
		return _commerceNotificationAttachment.getFileEntryId();
	}

	/**
	 * Returns the group ID of this commerce notification attachment.
	 *
	 * @return the group ID of this commerce notification attachment
	 */
	@Override
	public long getGroupId() {
		return _commerceNotificationAttachment.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification attachment.
	 *
	 * @return the modified date of this commerce notification attachment
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceNotificationAttachment.getModifiedDate();
	}

	/**
	 * Returns the primary key of this commerce notification attachment.
	 *
	 * @return the primary key of this commerce notification attachment
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceNotificationAttachment.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceNotificationAttachment.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this commerce notification attachment.
	 *
	 * @return the user ID of this commerce notification attachment
	 */
	@Override
	public long getUserId() {
		return _commerceNotificationAttachment.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification attachment.
	 *
	 * @return the user name of this commerce notification attachment
	 */
	@Override
	public String getUserName() {
		return _commerceNotificationAttachment.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification attachment.
	 *
	 * @return the user uuid of this commerce notification attachment
	 */
	@Override
	public String getUserUuid() {
		return _commerceNotificationAttachment.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce notification attachment.
	 *
	 * @return the uuid of this commerce notification attachment
	 */
	@Override
	public String getUuid() {
		return _commerceNotificationAttachment.getUuid();
	}

	@Override
	public int hashCode() {
		return _commerceNotificationAttachment.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceNotificationAttachment.isCachedModel();
	}

	/**
	 * Returns <code>true</code> if this commerce notification attachment is delete on send.
	 *
	 * @return <code>true</code> if this commerce notification attachment is delete on send; <code>false</code> otherwise
	 */
	@Override
	public boolean isDeleteOnSend() {
		return _commerceNotificationAttachment.isDeleteOnSend();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceNotificationAttachment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceNotificationAttachment.isNew();
	}

	@Override
	public void persist() {
		_commerceNotificationAttachment.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceNotificationAttachment.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce notification attachment ID of this commerce notification attachment.
	 *
	 * @param commerceNotificationAttachmentId the commerce notification attachment ID of this commerce notification attachment
	 */
	@Override
	public void setCommerceNotificationAttachmentId(
		long commerceNotificationAttachmentId) {

		_commerceNotificationAttachment.setCommerceNotificationAttachmentId(
			commerceNotificationAttachmentId);
	}

	/**
	 * Sets the commerce notification queue entry ID of this commerce notification attachment.
	 *
	 * @param commerceNotificationQueueEntryId the commerce notification queue entry ID of this commerce notification attachment
	 */
	@Override
	public void setCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		_commerceNotificationAttachment.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Sets the company ID of this commerce notification attachment.
	 *
	 * @param companyId the company ID of this commerce notification attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceNotificationAttachment.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification attachment.
	 *
	 * @param createDate the create date of this commerce notification attachment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceNotificationAttachment.setCreateDate(createDate);
	}

	/**
	 * Sets whether this commerce notification attachment is delete on send.
	 *
	 * @param deleteOnSend the delete on send of this commerce notification attachment
	 */
	@Override
	public void setDeleteOnSend(boolean deleteOnSend) {
		_commerceNotificationAttachment.setDeleteOnSend(deleteOnSend);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceNotificationAttachment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceNotificationAttachment.setExpandoBridgeAttributes(
			expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceNotificationAttachment.setExpandoBridgeAttributes(
			serviceContext);
	}

	/**
	 * Sets the file entry ID of this commerce notification attachment.
	 *
	 * @param fileEntryId the file entry ID of this commerce notification attachment
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		_commerceNotificationAttachment.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this commerce notification attachment.
	 *
	 * @param groupId the group ID of this commerce notification attachment
	 */
	@Override
	public void setGroupId(long groupId) {
		_commerceNotificationAttachment.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification attachment.
	 *
	 * @param modifiedDate the modified date of this commerce notification attachment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceNotificationAttachment.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceNotificationAttachment.setNew(n);
	}

	/**
	 * Sets the primary key of this commerce notification attachment.
	 *
	 * @param primaryKey the primary key of this commerce notification attachment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceNotificationAttachment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceNotificationAttachment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this commerce notification attachment.
	 *
	 * @param userId the user ID of this commerce notification attachment
	 */
	@Override
	public void setUserId(long userId) {
		_commerceNotificationAttachment.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification attachment.
	 *
	 * @param userName the user name of this commerce notification attachment
	 */
	@Override
	public void setUserName(String userName) {
		_commerceNotificationAttachment.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification attachment.
	 *
	 * @param userUuid the user uuid of this commerce notification attachment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceNotificationAttachment.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce notification attachment.
	 *
	 * @param uuid the uuid of this commerce notification attachment
	 */
	@Override
	public void setUuid(String uuid) {
		_commerceNotificationAttachment.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<CommerceNotificationAttachment> toCacheModel() {

		return _commerceNotificationAttachment.toCacheModel();
	}

	@Override
	public CommerceNotificationAttachment toEscapedModel() {
		return new CommerceNotificationAttachmentWrapper(
			_commerceNotificationAttachment.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceNotificationAttachment.toString();
	}

	@Override
	public CommerceNotificationAttachment toUnescapedModel() {
		return new CommerceNotificationAttachmentWrapper(
			_commerceNotificationAttachment.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceNotificationAttachment.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceNotificationAttachmentWrapper)) {
			return false;
		}

		CommerceNotificationAttachmentWrapper
			commerceNotificationAttachmentWrapper =
				(CommerceNotificationAttachmentWrapper)object;

		if (Objects.equals(
				_commerceNotificationAttachment,
				commerceNotificationAttachmentWrapper.
					_commerceNotificationAttachment)) {

			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _commerceNotificationAttachment.getStagedModelType();
	}

	@Override
	public CommerceNotificationAttachment getWrappedModel() {
		return _commerceNotificationAttachment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceNotificationAttachment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceNotificationAttachment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceNotificationAttachment.resetOriginalValues();
	}

	private final CommerceNotificationAttachment
		_commerceNotificationAttachment;

}