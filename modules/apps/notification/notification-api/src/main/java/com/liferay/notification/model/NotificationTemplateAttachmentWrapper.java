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
 * This class is a wrapper for {@link NotificationTemplateAttachment}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateAttachment
 * @generated
 */
public class NotificationTemplateAttachmentWrapper
	extends BaseModelWrapper<NotificationTemplateAttachment>
	implements ModelWrapper<NotificationTemplateAttachment>,
			   NotificationTemplateAttachment {

	public NotificationTemplateAttachmentWrapper(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		super(notificationTemplateAttachment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"notificationTemplateAttachmentId",
			getNotificationTemplateAttachmentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("notificationTemplateId", getNotificationTemplateId());
		attributes.put("objectFieldId", getObjectFieldId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long notificationTemplateAttachmentId = (Long)attributes.get(
			"notificationTemplateAttachmentId");

		if (notificationTemplateAttachmentId != null) {
			setNotificationTemplateAttachmentId(
				notificationTemplateAttachmentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long notificationTemplateId = (Long)attributes.get(
			"notificationTemplateId");

		if (notificationTemplateId != null) {
			setNotificationTemplateId(notificationTemplateId);
		}

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
		}
	}

	@Override
	public NotificationTemplateAttachment cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this notification template attachment.
	 *
	 * @return the company ID of this notification template attachment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this notification template attachment.
	 *
	 * @return the mvcc version of this notification template attachment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification template attachment ID of this notification template attachment.
	 *
	 * @return the notification template attachment ID of this notification template attachment
	 */
	@Override
	public long getNotificationTemplateAttachmentId() {
		return model.getNotificationTemplateAttachmentId();
	}

	/**
	 * Returns the notification template ID of this notification template attachment.
	 *
	 * @return the notification template ID of this notification template attachment
	 */
	@Override
	public long getNotificationTemplateId() {
		return model.getNotificationTemplateId();
	}

	/**
	 * Returns the object field ID of this notification template attachment.
	 *
	 * @return the object field ID of this notification template attachment
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the primary key of this notification template attachment.
	 *
	 * @return the primary key of this notification template attachment
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
	 * Sets the company ID of this notification template attachment.
	 *
	 * @param companyId the company ID of this notification template attachment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this notification template attachment.
	 *
	 * @param mvccVersion the mvcc version of this notification template attachment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification template attachment ID of this notification template attachment.
	 *
	 * @param notificationTemplateAttachmentId the notification template attachment ID of this notification template attachment
	 */
	@Override
	public void setNotificationTemplateAttachmentId(
		long notificationTemplateAttachmentId) {

		model.setNotificationTemplateAttachmentId(
			notificationTemplateAttachmentId);
	}

	/**
	 * Sets the notification template ID of this notification template attachment.
	 *
	 * @param notificationTemplateId the notification template ID of this notification template attachment
	 */
	@Override
	public void setNotificationTemplateId(long notificationTemplateId) {
		model.setNotificationTemplateId(notificationTemplateId);
	}

	/**
	 * Sets the object field ID of this notification template attachment.
	 *
	 * @param objectFieldId the object field ID of this notification template attachment
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the primary key of this notification template attachment.
	 *
	 * @param primaryKey the primary key of this notification template attachment
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
	protected NotificationTemplateAttachmentWrapper wrap(
		NotificationTemplateAttachment notificationTemplateAttachment) {

		return new NotificationTemplateAttachmentWrapper(
			notificationTemplateAttachment);
	}

}