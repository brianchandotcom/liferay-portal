/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

		attributes.put("userNotificationDeliveryId",
			getUserNotificationDeliveryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("type", getType());
		attributes.put("email", getEmail());
		attributes.put("sms", getSms());
		attributes.put("website", getWebsite());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean email = (Boolean)attributes.get("email");

		if (email != null) {
			setEmail(email);
		}

		Boolean sms = (Boolean)attributes.get("sms");

		if (sms != null) {
			setSms(sms);
		}

		Boolean website = (Boolean)attributes.get("website");

		if (website != null) {
			setWebsite(website);
		}
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

	/**
	* Sets the primary key of this user notification delivery.
	*
	* @param primaryKey the primary key of this user notification delivery
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userNotificationDelivery.setPrimaryKey(primaryKey);
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
	* Sets the user notification delivery ID of this user notification delivery.
	*
	* @param userNotificationDeliveryId the user notification delivery ID of this user notification delivery
	*/
	@Override
	public void setUserNotificationDeliveryId(long userNotificationDeliveryId) {
		_userNotificationDelivery.setUserNotificationDeliveryId(userNotificationDeliveryId);
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
	* Sets the company ID of this user notification delivery.
	*
	* @param companyId the company ID of this user notification delivery
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userNotificationDelivery.setCompanyId(companyId);
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
	* Sets the user ID of this user notification delivery.
	*
	* @param userId the user ID of this user notification delivery
	*/
	@Override
	public void setUserId(long userId) {
		_userNotificationDelivery.setUserId(userId);
	}

	/**
	* Returns the user uuid of this user notification delivery.
	*
	* @return the user uuid of this user notification delivery
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userNotificationDelivery.getUserUuid();
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

	/**
	* Returns the fully qualified class name of this user notification delivery.
	*
	* @return the fully qualified class name of this user notification delivery
	*/
	@Override
	public java.lang.String getClassName() {
		return _userNotificationDelivery.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_userNotificationDelivery.setClassName(className);
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
	* Sets the class name ID of this user notification delivery.
	*
	* @param classNameId the class name ID of this user notification delivery
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_userNotificationDelivery.setClassNameId(classNameId);
	}

	/**
	* Returns the type of this user notification delivery.
	*
	* @return the type of this user notification delivery
	*/
	@Override
	public int getType() {
		return _userNotificationDelivery.getType();
	}

	/**
	* Sets the type of this user notification delivery.
	*
	* @param type the type of this user notification delivery
	*/
	@Override
	public void setType(int type) {
		_userNotificationDelivery.setType(type);
	}

	/**
	* Returns the email of this user notification delivery.
	*
	* @return the email of this user notification delivery
	*/
	@Override
	public boolean getEmail() {
		return _userNotificationDelivery.getEmail();
	}

	/**
	* Returns <code>true</code> if this user notification delivery is email.
	*
	* @return <code>true</code> if this user notification delivery is email; <code>false</code> otherwise
	*/
	@Override
	public boolean isEmail() {
		return _userNotificationDelivery.isEmail();
	}

	/**
	* Sets whether this user notification delivery is email.
	*
	* @param email the email of this user notification delivery
	*/
	@Override
	public void setEmail(boolean email) {
		_userNotificationDelivery.setEmail(email);
	}

	/**
	* Returns the sms of this user notification delivery.
	*
	* @return the sms of this user notification delivery
	*/
	@Override
	public boolean getSms() {
		return _userNotificationDelivery.getSms();
	}

	/**
	* Returns <code>true</code> if this user notification delivery is sms.
	*
	* @return <code>true</code> if this user notification delivery is sms; <code>false</code> otherwise
	*/
	@Override
	public boolean isSms() {
		return _userNotificationDelivery.isSms();
	}

	/**
	* Sets whether this user notification delivery is sms.
	*
	* @param sms the sms of this user notification delivery
	*/
	@Override
	public void setSms(boolean sms) {
		_userNotificationDelivery.setSms(sms);
	}

	/**
	* Returns the website of this user notification delivery.
	*
	* @return the website of this user notification delivery
	*/
	@Override
	public boolean getWebsite() {
		return _userNotificationDelivery.getWebsite();
	}

	/**
	* Returns <code>true</code> if this user notification delivery is website.
	*
	* @return <code>true</code> if this user notification delivery is website; <code>false</code> otherwise
	*/
	@Override
	public boolean isWebsite() {
		return _userNotificationDelivery.isWebsite();
	}

	/**
	* Sets whether this user notification delivery is website.
	*
	* @param website the website of this user notification delivery
	*/
	@Override
	public void setWebsite(boolean website) {
		_userNotificationDelivery.setWebsite(website);
	}

	@Override
	public boolean isNew() {
		return _userNotificationDelivery.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userNotificationDelivery.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userNotificationDelivery.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userNotificationDelivery.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userNotificationDelivery.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userNotificationDelivery.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userNotificationDelivery.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userNotificationDelivery.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
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

	@Override
	public java.lang.Object clone() {
		return new UserNotificationDeliveryWrapper((UserNotificationDelivery)_userNotificationDelivery.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.UserNotificationDelivery userNotificationDelivery) {
		return _userNotificationDelivery.compareTo(userNotificationDelivery);
	}

	@Override
	public int hashCode() {
		return _userNotificationDelivery.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserNotificationDelivery> toCacheModel() {
		return _userNotificationDelivery.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery toEscapedModel() {
		return new UserNotificationDeliveryWrapper(_userNotificationDelivery.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserNotificationDelivery toUnescapedModel() {
		return new UserNotificationDeliveryWrapper(_userNotificationDelivery.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userNotificationDelivery.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userNotificationDelivery.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userNotificationDelivery.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public UserNotificationDelivery getWrappedUserNotificationDelivery() {
		return _userNotificationDelivery;
	}

	@Override
	public UserNotificationDelivery getWrappedModel() {
		return _userNotificationDelivery;
	}

	@Override
	public void resetOriginalValues() {
		_userNotificationDelivery.resetOriginalValues();
	}

	private UserNotificationDelivery _userNotificationDelivery;
}