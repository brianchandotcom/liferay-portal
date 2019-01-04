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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserNotificationDelivery}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDelivery
 * @generated
 */
@ProviderType
public class UserNotificationDeliveryWrapper extends BaseModelWrapper<UserNotificationDelivery>
	implements UserNotificationDelivery, ModelWrapper<UserNotificationDelivery> {
	public UserNotificationDeliveryWrapper(
		UserNotificationDelivery userNotificationDelivery) {
		super(userNotificationDelivery);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("userNotificationDeliveryId",
			getUserNotificationDeliveryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("portletId", getPortletId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("notificationType", getNotificationType());
		attributes.put("deliveryType", getDeliveryType());
		attributes.put("deliver", isDeliver());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long userNotificationDeliveryId = (Long)attributes.get(
				"userNotificationDeliveryId");

		if (userNotificationDeliveryId != null) {
			setUserNotificationDeliveryId(userNotificationDeliveryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Integer notificationType = (Integer)attributes.get("notificationType");

		if (notificationType != null) {
			setNotificationType(notificationType);
		}

		Integer deliveryType = (Integer)attributes.get("deliveryType");

		if (deliveryType != null) {
			setDeliveryType(deliveryType);
		}

		Boolean deliver = (Boolean)attributes.get("deliver");

		if (deliver != null) {
			setDeliver(deliver);
		}
	}

	/**
	* Returns the fully qualified class name of this user notification delivery.
	*
	* @return the fully qualified class name of this user notification delivery
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this user notification delivery.
	*
	* @return the class name ID of this user notification delivery
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the company ID of this user notification delivery.
	*
	* @return the company ID of this user notification delivery
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the deliver of this user notification delivery.
	*
	* @return the deliver of this user notification delivery
	*/
	@Override
	public boolean getDeliver() {
		return model.getDeliver();
	}

	/**
	* Returns the delivery type of this user notification delivery.
	*
	* @return the delivery type of this user notification delivery
	*/
	@Override
	public int getDeliveryType() {
		return model.getDeliveryType();
	}

	/**
	* Returns the mvcc version of this user notification delivery.
	*
	* @return the mvcc version of this user notification delivery
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the notification type of this user notification delivery.
	*
	* @return the notification type of this user notification delivery
	*/
	@Override
	public int getNotificationType() {
		return model.getNotificationType();
	}

	/**
	* Returns the portlet ID of this user notification delivery.
	*
	* @return the portlet ID of this user notification delivery
	*/
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	* Returns the primary key of this user notification delivery.
	*
	* @return the primary key of this user notification delivery
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this user notification delivery.
	*
	* @return the user ID of this user notification delivery
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user notification delivery ID of this user notification delivery.
	*
	* @return the user notification delivery ID of this user notification delivery
	*/
	@Override
	public long getUserNotificationDeliveryId() {
		return model.getUserNotificationDeliveryId();
	}

	/**
	* Returns the user uuid of this user notification delivery.
	*
	* @return the user uuid of this user notification delivery
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns <code>true</code> if this user notification delivery is deliver.
	*
	* @return <code>true</code> if this user notification delivery is deliver; <code>false</code> otherwise
	*/
	@Override
	public boolean isDeliver() {
		return model.isDeliver();
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
	* Sets the class name ID of this user notification delivery.
	*
	* @param classNameId the class name ID of this user notification delivery
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this user notification delivery.
	*
	* @param companyId the company ID of this user notification delivery
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets whether this user notification delivery is deliver.
	*
	* @param deliver the deliver of this user notification delivery
	*/
	@Override
	public void setDeliver(boolean deliver) {
		model.setDeliver(deliver);
	}

	/**
	* Sets the delivery type of this user notification delivery.
	*
	* @param deliveryType the delivery type of this user notification delivery
	*/
	@Override
	public void setDeliveryType(int deliveryType) {
		model.setDeliveryType(deliveryType);
	}

	/**
	* Sets the mvcc version of this user notification delivery.
	*
	* @param mvccVersion the mvcc version of this user notification delivery
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the notification type of this user notification delivery.
	*
	* @param notificationType the notification type of this user notification delivery
	*/
	@Override
	public void setNotificationType(int notificationType) {
		model.setNotificationType(notificationType);
	}

	/**
	* Sets the portlet ID of this user notification delivery.
	*
	* @param portletId the portlet ID of this user notification delivery
	*/
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this user notification delivery.
	*
	* @param primaryKey the primary key of this user notification delivery
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this user notification delivery.
	*
	* @param userId the user ID of this user notification delivery
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user notification delivery ID of this user notification delivery.
	*
	* @param userNotificationDeliveryId the user notification delivery ID of this user notification delivery
	*/
	@Override
	public void setUserNotificationDeliveryId(long userNotificationDeliveryId) {
		model.setUserNotificationDeliveryId(userNotificationDeliveryId);
	}

	/**
	* Sets the user uuid of this user notification delivery.
	*
	* @param userUuid the user uuid of this user notification delivery
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UserNotificationDeliveryWrapper wrap(
		UserNotificationDelivery userNotificationDelivery) {
		return new UserNotificationDeliveryWrapper(userNotificationDelivery);
	}
}