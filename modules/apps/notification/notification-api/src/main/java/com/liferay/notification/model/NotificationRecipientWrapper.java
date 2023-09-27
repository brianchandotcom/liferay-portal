/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link NotificationRecipient}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationRecipient
 * @generated
 */
public class NotificationRecipientWrapper
	extends BaseModelWrapper<NotificationRecipient>
	implements ModelWrapper<NotificationRecipient>, NotificationRecipient {

	public NotificationRecipientWrapper(
		NotificationRecipient notificationRecipient) {

		super(notificationRecipient);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("notificationRecipientId", getNotificationRecipientId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

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

		Long notificationRecipientId = (Long)attributes.get(
			"notificationRecipientId");

		if (notificationRecipientId != null) {
			setNotificationRecipientId(notificationRecipientId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public NotificationRecipient cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this notification recipient.
	 *
	 * @return the fully qualified class name of this notification recipient
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this notification recipient.
	 *
	 * @return the class name ID of this notification recipient
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this notification recipient.
	 *
	 * @return the class pk of this notification recipient
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this notification recipient.
	 *
	 * @return the company ID of this notification recipient
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this notification recipient.
	 *
	 * @return the create date of this notification recipient
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this notification recipient.
	 *
	 * @return the modified date of this notification recipient
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this notification recipient.
	 *
	 * @return the mvcc version of this notification recipient
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the notification recipient ID of this notification recipient.
	 *
	 * @return the notification recipient ID of this notification recipient
	 */
	@Override
	public long getNotificationRecipientId() {
		return model.getNotificationRecipientId();
	}

	@Override
	public java.util.List<NotificationRecipientSetting>
		getNotificationRecipientSettings() {

		return model.getNotificationRecipientSettings();
	}

	/**
	 * Returns the primary key of this notification recipient.
	 *
	 * @return the primary key of this notification recipient
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this notification recipient.
	 *
	 * @return the user ID of this notification recipient
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this notification recipient.
	 *
	 * @return the user name of this notification recipient
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this notification recipient.
	 *
	 * @return the user uuid of this notification recipient
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this notification recipient.
	 *
	 * @return the uuid of this notification recipient
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this notification recipient.
	 *
	 * @param classNameId the class name ID of this notification recipient
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this notification recipient.
	 *
	 * @param classPK the class pk of this notification recipient
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this notification recipient.
	 *
	 * @param companyId the company ID of this notification recipient
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this notification recipient.
	 *
	 * @param createDate the create date of this notification recipient
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this notification recipient.
	 *
	 * @param modifiedDate the modified date of this notification recipient
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this notification recipient.
	 *
	 * @param mvccVersion the mvcc version of this notification recipient
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the notification recipient ID of this notification recipient.
	 *
	 * @param notificationRecipientId the notification recipient ID of this notification recipient
	 */
	@Override
	public void setNotificationRecipientId(long notificationRecipientId) {
		model.setNotificationRecipientId(notificationRecipientId);
	}

	/**
	 * Sets the primary key of this notification recipient.
	 *
	 * @param primaryKey the primary key of this notification recipient
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this notification recipient.
	 *
	 * @param userId the user ID of this notification recipient
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this notification recipient.
	 *
	 * @param userName the user name of this notification recipient
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this notification recipient.
	 *
	 * @param userUuid the user uuid of this notification recipient
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this notification recipient.
	 *
	 * @param uuid the uuid of this notification recipient
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
	protected NotificationRecipientWrapper wrap(
		NotificationRecipient notificationRecipient) {

		return new NotificationRecipientWrapper(notificationRecipient);
	}

}