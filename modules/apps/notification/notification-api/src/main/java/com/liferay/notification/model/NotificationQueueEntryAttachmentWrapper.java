/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link NotificationQueueEntryAttachment}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationQueueEntryAttachment
 * @generated
 */
public class NotificationQueueEntryAttachmentWrapper
	extends BaseModelWrapper<NotificationQueueEntryAttachment>
	implements ModelWrapper<NotificationQueueEntryAttachment>,
			   NotificationQueueEntryAttachment {

	public NotificationQueueEntryAttachmentWrapper(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		super(notificationQueueEntryAttachment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"notificationQueueEntryAttachmentId",
			getNotificationQueueEntryAttachmentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put(
			"notificationQueueEntryId", getNotificationQueueEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long notificationQueueEntryAttachmentId = (Long)attributes.get(
			"notificationQueueEntryAttachmentId");

		if (notificationQueueEntryAttachmentId != null) {
			setNotificationQueueEntryAttachmentId(
				notificationQueueEntryAttachmentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Long notificationQueueEntryId = (Long)attributes.get(
			"notificationQueueEntryId");

		if (notificationQueueEntryId != null) {
			setNotificationQueueEntryId(notificationQueueEntryId);
		}
	}

	@Override
	public NotificationQueueEntryAttachment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this notification queue entry attachment.
	 *
	 * @return the company ID of this notification queue entry attachment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the file entry ID of this notification queue entry attachment.
	 *
	 * @return the file entry ID of this notification queue entry attachment
	 */
	@Override
	public long getFileEntryId() {
		return model.getFileEntryId();
	}

	/**
	 * Returns the mvcc version of this notification queue entry attachment.
	 *
	 * @return the mvcc version of this notification queue entry attachment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification queue entry attachment ID of this notification queue entry attachment.
	 *
	 * @return the notification queue entry attachment ID of this notification queue entry attachment
	 */
	@Override
	public long getNotificationQueueEntryAttachmentId() {
		return model.getNotificationQueueEntryAttachmentId();
	}

	/**
	 * Returns the notification queue entry ID of this notification queue entry attachment.
	 *
	 * @return the notification queue entry ID of this notification queue entry attachment
	 */
	@Override
	public long getNotificationQueueEntryId() {
		return model.getNotificationQueueEntryId();
	}

	/**
	 * Returns the primary key of this notification queue entry attachment.
	 *
	 * @return the primary key of this notification queue entry attachment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this notification queue entry attachment.
	 *
	 * @param companyId the company ID of this notification queue entry attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the file entry ID of this notification queue entry attachment.
	 *
	 * @param fileEntryId the file entry ID of this notification queue entry attachment
	 */
	@Override
	public void setFileEntryId(long fileEntryId) {
		model.setFileEntryId(fileEntryId);
	}

	/**
	 * Sets the mvcc version of this notification queue entry attachment.
	 *
	 * @param mvccVersion the mvcc version of this notification queue entry attachment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification queue entry attachment ID of this notification queue entry attachment.
	 *
	 * @param notificationQueueEntryAttachmentId the notification queue entry attachment ID of this notification queue entry attachment
	 */
	@Override
	public void setNotificationQueueEntryAttachmentId(
		long notificationQueueEntryAttachmentId) {

		model.setNotificationQueueEntryAttachmentId(
			notificationQueueEntryAttachmentId);
	}

	/**
	 * Sets the notification queue entry ID of this notification queue entry attachment.
	 *
	 * @param notificationQueueEntryId the notification queue entry ID of this notification queue entry attachment
	 */
	@Override
	public void setNotificationQueueEntryId(long notificationQueueEntryId) {
		model.setNotificationQueueEntryId(notificationQueueEntryId);
	}

	/**
	 * Sets the primary key of this notification queue entry attachment.
	 *
	 * @param primaryKey the primary key of this notification queue entry attachment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected NotificationQueueEntryAttachmentWrapper wrap(
		NotificationQueueEntryAttachment notificationQueueEntryAttachment) {

		return new NotificationQueueEntryAttachmentWrapper(
			notificationQueueEntryAttachment);
	}

}