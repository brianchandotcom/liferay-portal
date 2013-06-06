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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityLimit}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimit
 * @generated
 */
public class SocialActivityLimitWrapper implements SocialActivityLimit,
	ModelWrapper<SocialActivityLimit> {
	public SocialActivityLimitWrapper(SocialActivityLimit socialActivityLimit) {
		_socialActivityLimit = socialActivityLimit;
	}

	@Override
	public Class<?> getModelClass() {
		return SocialActivityLimit.class;
	}

	@Override
	public String getModelClassName() {
		return SocialActivityLimit.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityLimitId", getActivityLimitId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("activityType", getActivityType());
		attributes.put("activityCounterName", getActivityCounterName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityLimitId = (Long)attributes.get("activityLimitId");

		if (activityLimitId != null) {
			setActivityLimitId(activityLimitId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer activityType = (Integer)attributes.get("activityType");

		if (activityType != null) {
			setActivityType(activityType);
		}

		String activityCounterName = (String)attributes.get(
				"activityCounterName");

		if (activityCounterName != null) {
			setActivityCounterName(activityCounterName);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	/**
	* Returns the primary key of this social activity limit.
	*
	* @return the primary key of this social activity limit
	*/
	@Override
	public long getPrimaryKey() {
		return _socialActivityLimit.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity limit.
	*
	* @param primaryKey the primary key of this social activity limit
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_socialActivityLimit.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity limit ID of this social activity limit.
	*
	* @return the activity limit ID of this social activity limit
	*/
	@Override
	public long getActivityLimitId() {
		return _socialActivityLimit.getActivityLimitId();
	}

	/**
	* Sets the activity limit ID of this social activity limit.
	*
	* @param activityLimitId the activity limit ID of this social activity limit
	*/
	@Override
	public void setActivityLimitId(long activityLimitId) {
		_socialActivityLimit.setActivityLimitId(activityLimitId);
	}

	/**
	* Returns the group ID of this social activity limit.
	*
	* @return the group ID of this social activity limit
	*/
	@Override
	public long getGroupId() {
		return _socialActivityLimit.getGroupId();
	}

	/**
	* Sets the group ID of this social activity limit.
	*
	* @param groupId the group ID of this social activity limit
	*/
	@Override
	public void setGroupId(long groupId) {
		_socialActivityLimit.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity limit.
	*
	* @return the company ID of this social activity limit
	*/
	@Override
	public long getCompanyId() {
		return _socialActivityLimit.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity limit.
	*
	* @param companyId the company ID of this social activity limit
	*/
	@Override
	public void setCompanyId(long companyId) {
		_socialActivityLimit.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social activity limit.
	*
	* @return the user ID of this social activity limit
	*/
	@Override
	public long getUserId() {
		return _socialActivityLimit.getUserId();
	}

	/**
	* Sets the user ID of this social activity limit.
	*
	* @param userId the user ID of this social activity limit
	*/
	@Override
	public void setUserId(long userId) {
		_socialActivityLimit.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social activity limit.
	*
	* @return the user uuid of this social activity limit
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLimit.getUserUuid();
	}

	/**
	* Sets the user uuid of this social activity limit.
	*
	* @param userUuid the user uuid of this social activity limit
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivityLimit.setUserUuid(userUuid);
	}

	/**
	* Returns the fully qualified class name of this social activity limit.
	*
	* @return the fully qualified class name of this social activity limit
	*/
	@Override
	public java.lang.String getClassName() {
		return _socialActivityLimit.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_socialActivityLimit.setClassName(className);
	}

	/**
	* Returns the class name ID of this social activity limit.
	*
	* @return the class name ID of this social activity limit
	*/
	@Override
	public long getClassNameId() {
		return _socialActivityLimit.getClassNameId();
	}

	/**
	* Sets the class name ID of this social activity limit.
	*
	* @param classNameId the class name ID of this social activity limit
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_socialActivityLimit.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this social activity limit.
	*
	* @return the class p k of this social activity limit
	*/
	@Override
	public long getClassPK() {
		return _socialActivityLimit.getClassPK();
	}

	/**
	* Sets the class p k of this social activity limit.
	*
	* @param classPK the class p k of this social activity limit
	*/
	@Override
	public void setClassPK(long classPK) {
		_socialActivityLimit.setClassPK(classPK);
	}

	/**
	* Returns the activity type of this social activity limit.
	*
	* @return the activity type of this social activity limit
	*/
	@Override
	public int getActivityType() {
		return _socialActivityLimit.getActivityType();
	}

	/**
	* Sets the activity type of this social activity limit.
	*
	* @param activityType the activity type of this social activity limit
	*/
	@Override
	public void setActivityType(int activityType) {
		_socialActivityLimit.setActivityType(activityType);
	}

	/**
	* Returns the activity counter name of this social activity limit.
	*
	* @return the activity counter name of this social activity limit
	*/
	@Override
	public java.lang.String getActivityCounterName() {
		return _socialActivityLimit.getActivityCounterName();
	}

	/**
	* Sets the activity counter name of this social activity limit.
	*
	* @param activityCounterName the activity counter name of this social activity limit
	*/
	@Override
	public void setActivityCounterName(java.lang.String activityCounterName) {
		_socialActivityLimit.setActivityCounterName(activityCounterName);
	}

	/**
	* Returns the value of this social activity limit.
	*
	* @return the value of this social activity limit
	*/
	@Override
	public java.lang.String getValue() {
		return _socialActivityLimit.getValue();
	}

	/**
	* Sets the value of this social activity limit.
	*
	* @param value the value of this social activity limit
	*/
	@Override
	public void setValue(java.lang.String value) {
		_socialActivityLimit.setValue(value);
	}

	@Override
	public boolean isNew() {
		return _socialActivityLimit.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_socialActivityLimit.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _socialActivityLimit.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_socialActivityLimit.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _socialActivityLimit.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityLimit.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityLimit.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityLimit.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_socialActivityLimit.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_socialActivityLimit.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityLimit.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityLimitWrapper((SocialActivityLimit)_socialActivityLimit.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityLimit socialActivityLimit) {
		return _socialActivityLimit.compareTo(socialActivityLimit);
	}

	@Override
	public int hashCode() {
		return _socialActivityLimit.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityLimit> toCacheModel() {
		return _socialActivityLimit.toCacheModel();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityLimit toEscapedModel() {
		return new SocialActivityLimitWrapper(_socialActivityLimit.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityLimit toUnescapedModel() {
		return new SocialActivityLimitWrapper(_socialActivityLimit.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityLimit.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _socialActivityLimit.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLimit.persist();
	}

	@Override
	public int getCount() {
		return _socialActivityLimit.getCount();
	}

	@Override
	public int getCount(int limitPeriod) {
		return _socialActivityLimit.getCount(limitPeriod);
	}

	@Override
	public void setCount(int limitPeriod, int count) {
		_socialActivityLimit.setCount(limitPeriod, count);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public SocialActivityLimit getWrappedSocialActivityLimit() {
		return _socialActivityLimit;
	}

	@Override
	public SocialActivityLimit getWrappedModel() {
		return _socialActivityLimit;
	}

	@Override
	public void resetOriginalValues() {
		_socialActivityLimit.resetOriginalValues();
	}

	private SocialActivityLimit _socialActivityLimit;
}