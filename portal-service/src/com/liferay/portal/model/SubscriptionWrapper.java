/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Subscription}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Subscription
 * @generated
 */
public class SubscriptionWrapper implements Subscription,
	ModelWrapper<Subscription> {
	public SubscriptionWrapper(Subscription subscription) {
		_subscription = subscription;
	}

	public Class<?> getModelClass() {
		return Subscription.class;
	}

	public String getModelClassName() {
		return Subscription.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("subscriptionId", getSubscriptionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("frequency", getFrequency());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long subscriptionId = (Long)attributes.get("subscriptionId");

		if (subscriptionId != null) {
			setSubscriptionId(subscriptionId);
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

		String frequency = (String)attributes.get("frequency");

		if (frequency != null) {
			setFrequency(frequency);
		}
	}

	/**
	* Returns the primary key of this subscription.
	*
	* @return the primary key of this subscription
	*/
	public long getPrimaryKey() {
		return _subscription.getPrimaryKey();
	}

	/**
	* Sets the primary key of this subscription.
	*
	* @param primaryKey the primary key of this subscription
	*/
	public void setPrimaryKey(long primaryKey) {
		_subscription.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the subscription ID of this subscription.
	*
	* @return the subscription ID of this subscription
	*/
	public long getSubscriptionId() {
		return _subscription.getSubscriptionId();
	}

	/**
	* Sets the subscription ID of this subscription.
	*
	* @param subscriptionId the subscription ID of this subscription
	*/
	public void setSubscriptionId(long subscriptionId) {
		_subscription.setSubscriptionId(subscriptionId);
	}

	/**
	* Returns the company ID of this subscription.
	*
	* @return the company ID of this subscription
	*/
	public long getCompanyId() {
		return _subscription.getCompanyId();
	}

	/**
	* Sets the company ID of this subscription.
	*
	* @param companyId the company ID of this subscription
	*/
	public void setCompanyId(long companyId) {
		_subscription.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this subscription.
	*
	* @return the user ID of this subscription
	*/
	public long getUserId() {
		return _subscription.getUserId();
	}

	/**
	* Sets the user ID of this subscription.
	*
	* @param userId the user ID of this subscription
	*/
	public void setUserId(long userId) {
		_subscription.setUserId(userId);
	}

	/**
	* Returns the user uuid of this subscription.
	*
	* @return the user uuid of this subscription
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscription.getUserUuid();
	}

	/**
	* Sets the user uuid of this subscription.
	*
	* @param userUuid the user uuid of this subscription
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_subscription.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this subscription.
	*
	* @return the user name of this subscription
	*/
	public java.lang.String getUserName() {
		return _subscription.getUserName();
	}

	/**
	* Sets the user name of this subscription.
	*
	* @param userName the user name of this subscription
	*/
	public void setUserName(java.lang.String userName) {
		_subscription.setUserName(userName);
	}

	/**
	* Returns the create date of this subscription.
	*
	* @return the create date of this subscription
	*/
	public java.util.Date getCreateDate() {
		return _subscription.getCreateDate();
	}

	/**
	* Sets the create date of this subscription.
	*
	* @param createDate the create date of this subscription
	*/
	public void setCreateDate(java.util.Date createDate) {
		_subscription.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this subscription.
	*
	* @return the modified date of this subscription
	*/
	public java.util.Date getModifiedDate() {
		return _subscription.getModifiedDate();
	}

	/**
	* Sets the modified date of this subscription.
	*
	* @param modifiedDate the modified date of this subscription
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_subscription.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this subscription.
	*
	* @return the fully qualified class name of this subscription
	*/
	public java.lang.String getClassName() {
		return _subscription.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_subscription.setClassName(className);
	}

	/**
	* Returns the class name ID of this subscription.
	*
	* @return the class name ID of this subscription
	*/
	public long getClassNameId() {
		return _subscription.getClassNameId();
	}

	/**
	* Sets the class name ID of this subscription.
	*
	* @param classNameId the class name ID of this subscription
	*/
	public void setClassNameId(long classNameId) {
		_subscription.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this subscription.
	*
	* @return the class p k of this subscription
	*/
	public long getClassPK() {
		return _subscription.getClassPK();
	}

	/**
	* Sets the class p k of this subscription.
	*
	* @param classPK the class p k of this subscription
	*/
	public void setClassPK(long classPK) {
		_subscription.setClassPK(classPK);
	}

	/**
	* Returns the frequency of this subscription.
	*
	* @return the frequency of this subscription
	*/
	public java.lang.String getFrequency() {
		return _subscription.getFrequency();
	}

	/**
	* Sets the frequency of this subscription.
	*
	* @param frequency the frequency of this subscription
	*/
	public void setFrequency(java.lang.String frequency) {
		_subscription.setFrequency(frequency);
	}

	public boolean isNew() {
		return _subscription.isNew();
	}

	public void setNew(boolean n) {
		_subscription.setNew(n);
	}

	public boolean isCachedModel() {
		return _subscription.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_subscription.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _subscription.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _subscription.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_subscription.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _subscription.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_subscription.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SubscriptionWrapper((Subscription)_subscription.clone());
	}

	public int compareTo(com.liferay.portal.model.Subscription subscription) {
		return _subscription.compareTo(subscription);
	}

	@Override
	public int hashCode() {
		return _subscription.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Subscription> toCacheModel() {
		return _subscription.toCacheModel();
	}

	public com.liferay.portal.model.Subscription toEscapedModel() {
		return new SubscriptionWrapper(_subscription.toEscapedModel());
	}

	public com.liferay.portal.model.Subscription toUnescapedModel() {
		return new SubscriptionWrapper(_subscription.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _subscription.toString();
	}

	public java.lang.String toXmlString() {
		return _subscription.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_subscription.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Subscription getWrappedSubscription() {
		return _subscription;
	}

	public Subscription getWrappedModel() {
		return _subscription;
	}

	public void resetOriginalValues() {
		_subscription.resetOriginalValues();
	}

	private Subscription _subscription;
}