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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

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
public class UserNotificationDeliveryWrapper implements UserNotificationDelivery,
	ModelWrapper<UserNotificationDelivery> {
	public UserNotificationDeliveryWrapper(
		UserNotificationDelivery userNotificationDelivery) {
		_userNotificationDelivery = userNotificationDelivery;
	}

	@Override
	public Class<?> getModelClass() {
		return UserNotificationDelivery.class;
	}

	@Override
	public String getModelClassName() {
		return UserNotificationDelivery.class.getName();
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
		attributes.put("deliver", getDeliver());

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

	@Override
	public java.lang.Object clone() {
		return new UserNotificationDeliveryWrapper((UserNotificationDelivery)_userNotificationDelivery.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return _userNotificationDelivery.compareTo(userNotificationDelivery);
	}

	/**
	* Returns the fully qualified class name of this user notification delivery.
	*
	* @return the fully qualified class name of this user notification delivery
	*/
	@Override
	public java.lang.String getClassName() {
		return _userNotificationDelivery.getClassName();
	}

	/**
	* Returns the class name ID of this user notification delivery.
	*
	* @return the class name ID of this user notification delivery
	*/
	@Override
	public long getClassNameId() {
		return _userNotificationDelivery.getClassNameId();
	}

	/**
	* Returns the company ID of this user notification delivery.
	*
	* @return the company ID of this user notification delivery
	*/
	@Override
	public long getCompanyId() {
		return _userNotificationDelivery.getCompanyId();
	}

	/**
	* Returns the deliver of this user notification delivery.
	*
	* @return the deliver of this user notification delivery
	*/
	@Override
	public boolean getDeliver() {
		return _userNotificationDelivery.getDeliver();
	}

	/**
	* Returns the delivery type of this user notification delivery.
	*
	* @return the delivery type of this user notification delivery
	*/
	@Override
	public int getDeliveryType() {
		return _userNotificationDelivery.getDeliveryType();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userNotificationDelivery.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this user notification delivery.
	*
	* @return the mvcc version of this user notification delivery
	*/
	@Override
	public long getMvccVersion() {
		return _userNotificationDelivery.getMvccVersion();
	}

	/**
	* Returns the notification type of this user notification delivery.
	*
	* @return the notification type of this user notification delivery
	*/
	@Override
	public int getNotificationType() {
		return _userNotificationDelivery.getNotificationType();
	}

	/**
	* Returns the portlet ID of this user notification delivery.
	*
	* @return the portlet ID of this user notification delivery
	*/
	@Override
	public java.lang.String getPortletId() {
		return _userNotificationDelivery.getPortletId();
	}

	/**
	* Returns the primary key of this user notification delivery.
	*
	* @return the primary key of this user notification delivery
	*/
	@Override
	public long getPrimaryKey() {
		return _userNotificationDelivery.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userNotificationDelivery.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this user notification delivery.
	*
	* @return the user ID of this user notification delivery
	*/
	@Override
	public long getUserId() {
		return _userNotificationDelivery.getUserId();
	}

	/**
	* Returns the user notification delivery ID of this user notification delivery.
	*
	* @return the user notification delivery ID of this user notification delivery
	*/
	@Override
	public long getUserNotificationDeliveryId() {
		return _userNotificationDelivery.getUserNotificationDeliveryId();
	}

	/**
	* Returns the user uuid of this user notification delivery.
	*
	* @return the user uuid of this user notification delivery
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _userNotificationDelivery.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _userNotificationDelivery.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _userNotificationDelivery.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this user notification delivery is deliver.
	*
	* @return <code>true</code> if this user notification delivery is deliver; <code>false</code> otherwise
	*/
	@Override
	public boolean isDeliver() {
		return _userNotificationDelivery.isDeliver();
	}

	@Override
	public boolean isEscapedModel() {
		return _userNotificationDelivery.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userNotificationDelivery.isNew();
	}

	@Override
	public void persist() {
		_userNotificationDelivery.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userNotificationDelivery.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_userNotificationDelivery.setClassName(className);
	}

	/**
	* Sets the class name ID of this user notification delivery.
	*
	* @param classNameId the class name ID of this user notification delivery
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_userNotificationDelivery.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this user notification delivery.
	*
	* @param companyId the company ID of this user notification delivery
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userNotificationDelivery.setCompanyId(companyId);
	}

	/**
	* Sets whether this user notification delivery is deliver.
	*
	* @param deliver the deliver of this user notification delivery
	*/
	@Override
	public void setDeliver(boolean deliver) {
		_userNotificationDelivery.setDeliver(deliver);
	}

	/**
	* Sets the delivery type of this user notification delivery.
	*
	* @param deliveryType the delivery type of this user notification delivery
	*/
	@Override
	public void setDeliveryType(int deliveryType) {
		_userNotificationDelivery.setDeliveryType(deliveryType);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userNotificationDelivery.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userNotificationDelivery.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userNotificationDelivery.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this user notification delivery.
	*
	* @param mvccVersion the mvcc version of this user notification delivery
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userNotificationDelivery.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_userNotificationDelivery.setNew(n);
	}

	/**
	* Sets the notification type of this user notification delivery.
	*
	* @param notificationType the notification type of this user notification delivery
	*/
	@Override
	public void setNotificationType(int notificationType) {
		_userNotificationDelivery.setNotificationType(notificationType);
	}

	/**
	* Sets the portlet ID of this user notification delivery.
	*
	* @param portletId the portlet ID of this user notification delivery
	*/
	@Override
	public void setPortletId(java.lang.String portletId) {
		_userNotificationDelivery.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this user notification delivery.
	*
	* @param primaryKey the primary key of this user notification delivery
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userNotificationDelivery.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userNotificationDelivery.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this user notification delivery.
	*
	* @param userId the user ID of this user notification delivery
	*/
	@Override
	public void setUserId(long userId) {
		_userNotificationDelivery.setUserId(userId);
	}

	/**
	* Sets the user notification delivery ID of this user notification delivery.
	*
	* @param userNotificationDeliveryId the user notification delivery ID of this user notification delivery
	*/
	@Override
	public void setUserNotificationDeliveryId(long userNotificationDeliveryId) {
		_userNotificationDelivery.setUserNotificationDeliveryId(userNotificationDeliveryId);
	}

	/**
	* Sets the user uuid of this user notification delivery.
	*
	* @param userUuid the user uuid of this user notification delivery
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userNotificationDelivery.setUserUuid(userUuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.UserNotificationDelivery> toCacheModel() {
		return _userNotificationDelivery.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery toEscapedModel() {
		return new UserNotificationDeliveryWrapper(_userNotificationDelivery.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userNotificationDelivery.toString();
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery toUnescapedModel() {
		return new UserNotificationDeliveryWrapper(_userNotificationDelivery.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _userNotificationDelivery.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserNotificationDeliveryWrapper)) {
			return false;
		}

		UserNotificationDeliveryWrapper userNotificationDeliveryWrapper = (UserNotificationDeliveryWrapper)obj;

		if (Validator.equals(_userNotificationDelivery,
					userNotificationDeliveryWrapper._userNotificationDelivery)) {
			return true;
		}

		return false;
	}

	@Override
	public UserNotificationDelivery getWrappedModel() {
		return _userNotificationDelivery;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userNotificationDelivery.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userNotificationDelivery.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userNotificationDelivery.resetOriginalValues();
	}

	private final UserNotificationDelivery _userNotificationDelivery;
}