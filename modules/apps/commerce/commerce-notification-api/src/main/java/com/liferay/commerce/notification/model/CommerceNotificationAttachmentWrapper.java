/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceNotificationAttachment>
	implements CommerceNotificationAttachment,
			   ModelWrapper<CommerceNotificationAttachment> {

	public CommerceNotificationAttachmentWrapper(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		super(commerceNotificationAttachment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	public CommerceNotificationAttachment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce notification attachment ID of this commerce notification attachment.
	 *
	 * @return the commerce notification attachment ID of this commerce notification attachment
	 */
	@Override
	public long getCommerceNotificationAttachmentId() {
		return model.getCommerceNotificationAttachmentId();
	}

	/**
	 * Returns the commerce notification queue entry ID of this commerce notification attachment.
	 *
	 * @return the commerce notification queue entry ID of this commerce notification attachment
	 */
	@Override
	public long getCommerceNotificationQueueEntryId() {
		return model.getCommerceNotificationQueueEntryId();
	}

	/**
	 * Returns the company ID of this commerce notification attachment.
	 *
	 * @return the company ID of this commerce notification attachment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce notification attachment.
	 *
	 * @return the create date of this commerce notification attachment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the delete on send of this commerce notification attachment.
	 *
	 * @return the delete on send of this commerce notification attachment
	 */
	@Override
	public boolean getDeleteOnSend() {
		return model.getDeleteOnSend();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getFileEntry();
	}

	/**
	 * Returns the file entry ID of this commerce notification attachment.
	 *
	 * @return the file entry ID of this commerce notification attachment
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the group ID of this commerce notification attachment.
	 *
	 * @return the group ID of this commerce notification attachment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce notification attachment.
	 *
	 * @return the modified date of this commerce notification attachment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce notification attachment.
	 *
	 * @return the mvcc version of this commerce notification attachment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce notification attachment.
	 *
	 * @return the primary key of this commerce notification attachment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce notification attachment.
	 *
	 * @return the user ID of this commerce notification attachment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce notification attachment.
	 *
	 * @return the user name of this commerce notification attachment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce notification attachment.
	 *
	 * @return the user uuid of this commerce notification attachment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce notification attachment.
	 *
	 * @return the uuid of this commerce notification attachment
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this commerce notification attachment is delete on send.
	 *
	 * @return <code>true</code> if this commerce notification attachment is delete on send; <code>false</code> otherwise
	 */
	@Override
	public boolean isDeleteOnSend() {
		return model.isDeleteOnSend();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce notification attachment ID of this commerce notification attachment.
	 *
	 * @param commerceNotificationAttachmentId the commerce notification attachment ID of this commerce notification attachment
	 */
	@Override
	public void setCommerceNotificationAttachmentId(
		long commerceNotificationAttachmentId) {

		model.setCommerceNotificationAttachmentId(
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

		model.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
	}

	/**
	 * Sets the company ID of this commerce notification attachment.
	 *
	 * @param companyId the company ID of this commerce notification attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce notification attachment.
	 *
	 * @param createDate the create date of this commerce notification attachment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this commerce notification attachment is delete on send.
	 *
	 * @param deleteOnSend the delete on send of this commerce notification attachment
	 */
	@Override
	public void setDeleteOnSend(boolean deleteOnSend) {
		model.setDeleteOnSend(deleteOnSend);
	}

	/**
	 * Sets the file entry ID of this commerce notification attachment.
	 *
	 * @param fileEntryId the file entry ID of this commerce notification attachment
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the group ID of this commerce notification attachment.
	 *
	 * @param groupId the group ID of this commerce notification attachment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce notification attachment.
	 *
	 * @param modifiedDate the modified date of this commerce notification attachment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce notification attachment.
	 *
	 * @param mvccVersion the mvcc version of this commerce notification attachment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce notification attachment.
	 *
	 * @param primaryKey the primary key of this commerce notification attachment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce notification attachment.
	 *
	 * @param userId the user ID of this commerce notification attachment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce notification attachment.
	 *
	 * @param userName the user name of this commerce notification attachment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce notification attachment.
	 *
	 * @param userUuid the user uuid of this commerce notification attachment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce notification attachment.
	 *
	 * @param uuid the uuid of this commerce notification attachment
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceNotificationAttachmentWrapper wrap(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		return new CommerceNotificationAttachmentWrapper(
			commerceNotificationAttachment);
	}

}