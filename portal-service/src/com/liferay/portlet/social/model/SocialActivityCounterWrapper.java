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

package com.liferay.portlet.social.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityCounter}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounter
 * @generated
 */
@ProviderType
public class SocialActivityCounterWrapper implements SocialActivityCounter,
	ModelWrapper<SocialActivityCounter> {
	public SocialActivityCounterWrapper(
		SocialActivityCounter socialActivityCounter) {
		_socialActivityCounter = socialActivityCounter;
	}

	@Override
	public Class<?> getModelClass() {
		return SocialActivityCounter.class;
	}

	@Override
	public String getModelClassName() {
		return SocialActivityCounter.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityCounterId", getActivityCounterId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("name", getName());
		attributes.put("ownerType", getOwnerType());
		attributes.put("currentValue", getCurrentValue());
		attributes.put("totalValue", getTotalValue());
		attributes.put("graceValue", getGraceValue());
		attributes.put("startPeriod", getStartPeriod());
		attributes.put("endPeriod", getEndPeriod());
		attributes.put("active", getActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityCounterId = (Long)attributes.get("activityCounterId");

		if (activityCounterId != null) {
			setActivityCounterId(activityCounterId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		Integer currentValue = (Integer)attributes.get("currentValue");

		if (currentValue != null) {
			setCurrentValue(currentValue);
		}

		Integer totalValue = (Integer)attributes.get("totalValue");

		if (totalValue != null) {
			setTotalValue(totalValue);
		}

		Integer graceValue = (Integer)attributes.get("graceValue");

		if (graceValue != null) {
			setGraceValue(graceValue);
		}

		Integer startPeriod = (Integer)attributes.get("startPeriod");

		if (startPeriod != null) {
			setStartPeriod(startPeriod);
		}

		Integer endPeriod = (Integer)attributes.get("endPeriod");

		if (endPeriod != null) {
			setEndPeriod(endPeriod);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityCounterWrapper((SocialActivityCounter)_socialActivityCounter.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter) {
		return _socialActivityCounter.compareTo(socialActivityCounter);
	}

	/**
	* Returns the active of this social activity counter.
	*
	* @return the active of this social activity counter
	*/
	@Override
	public boolean getActive() {
		return _socialActivityCounter.getActive();
	}

	/**
	* Returns the activity counter ID of this social activity counter.
	*
	* @return the activity counter ID of this social activity counter
	*/
	@Override
	public long getActivityCounterId() {
		return _socialActivityCounter.getActivityCounterId();
	}

	/**
	* Returns the fully qualified class name of this social activity counter.
	*
	* @return the fully qualified class name of this social activity counter
	*/
	@Override
	public java.lang.String getClassName() {
		return _socialActivityCounter.getClassName();
	}

	/**
	* Returns the class name ID of this social activity counter.
	*
	* @return the class name ID of this social activity counter
	*/
	@Override
	public long getClassNameId() {
		return _socialActivityCounter.getClassNameId();
	}

	/**
	* Returns the class p k of this social activity counter.
	*
	* @return the class p k of this social activity counter
	*/
	@Override
	public long getClassPK() {
		return _socialActivityCounter.getClassPK();
	}

	/**
	* Returns the company ID of this social activity counter.
	*
	* @return the company ID of this social activity counter
	*/
	@Override
	public long getCompanyId() {
		return _socialActivityCounter.getCompanyId();
	}

	/**
	* Returns the current value of this social activity counter.
	*
	* @return the current value of this social activity counter
	*/
	@Override
	public int getCurrentValue() {
		return _socialActivityCounter.getCurrentValue();
	}

	/**
	* Returns the end period of this social activity counter.
	*
	* @return the end period of this social activity counter
	*/
	@Override
	public int getEndPeriod() {
		return _socialActivityCounter.getEndPeriod();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityCounter.getExpandoBridge();
	}

	/**
	* Returns the grace value of this social activity counter.
	*
	* @return the grace value of this social activity counter
	*/
	@Override
	public int getGraceValue() {
		return _socialActivityCounter.getGraceValue();
	}

	/**
	* Returns the group ID of this social activity counter.
	*
	* @return the group ID of this social activity counter
	*/
	@Override
	public long getGroupId() {
		return _socialActivityCounter.getGroupId();
	}

	/**
	* Returns the name of this social activity counter.
	*
	* @return the name of this social activity counter
	*/
	@Override
	public java.lang.String getName() {
		return _socialActivityCounter.getName();
	}

	/**
	* Returns the owner type of this social activity counter.
	*
	* @return the owner type of this social activity counter
	*/
	@Override
	public int getOwnerType() {
		return _socialActivityCounter.getOwnerType();
	}

	/**
	* Returns the primary key of this social activity counter.
	*
	* @return the primary key of this social activity counter
	*/
	@Override
	public long getPrimaryKey() {
		return _socialActivityCounter.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityCounter.getPrimaryKeyObj();
	}

	/**
	* Returns the start period of this social activity counter.
	*
	* @return the start period of this social activity counter
	*/
	@Override
	public int getStartPeriod() {
		return _socialActivityCounter.getStartPeriod();
	}

	/**
	* Returns the total value of this social activity counter.
	*
	* @return the total value of this social activity counter
	*/
	@Override
	public int getTotalValue() {
		return _socialActivityCounter.getTotalValue();
	}

	@Override
	public int hashCode() {
		return _socialActivityCounter.hashCode();
	}

	/**
	* Returns <code>true</code> if this social activity counter is active.
	*
	* @return <code>true</code> if this social activity counter is active; <code>false</code> otherwise
	*/
	@Override
	public boolean isActive() {
		return _socialActivityCounter.isActive();
	}

	@Override
	public boolean isActivePeriod(int periodLength) {
		return _socialActivityCounter.isActivePeriod(periodLength);
	}

	@Override
	public boolean isCachedModel() {
		return _socialActivityCounter.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _socialActivityCounter.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _socialActivityCounter.isNew();
	}

	@Override
	public void persist() {
		_socialActivityCounter.persist();
	}

	/**
	* Sets whether this social activity counter is active.
	*
	* @param active the active of this social activity counter
	*/
	@Override
	public void setActive(boolean active) {
		_socialActivityCounter.setActive(active);
	}

	/**
	* Sets the activity counter ID of this social activity counter.
	*
	* @param activityCounterId the activity counter ID of this social activity counter
	*/
	@Override
	public void setActivityCounterId(long activityCounterId) {
		_socialActivityCounter.setActivityCounterId(activityCounterId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_socialActivityCounter.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_socialActivityCounter.setClassName(className);
	}

	/**
	* Sets the class name ID of this social activity counter.
	*
	* @param classNameId the class name ID of this social activity counter
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_socialActivityCounter.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this social activity counter.
	*
	* @param classPK the class p k of this social activity counter
	*/
	@Override
	public void setClassPK(long classPK) {
		_socialActivityCounter.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this social activity counter.
	*
	* @param companyId the company ID of this social activity counter
	*/
	@Override
	public void setCompanyId(long companyId) {
		_socialActivityCounter.setCompanyId(companyId);
	}

	/**
	* Sets the current value of this social activity counter.
	*
	* @param currentValue the current value of this social activity counter
	*/
	@Override
	public void setCurrentValue(int currentValue) {
		_socialActivityCounter.setCurrentValue(currentValue);
	}

	/**
	* Sets the end period of this social activity counter.
	*
	* @param endPeriod the end period of this social activity counter
	*/
	@Override
	public void setEndPeriod(int endPeriod) {
		_socialActivityCounter.setEndPeriod(endPeriod);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_socialActivityCounter.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_socialActivityCounter.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityCounter.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the grace value of this social activity counter.
	*
	* @param graceValue the grace value of this social activity counter
	*/
	@Override
	public void setGraceValue(int graceValue) {
		_socialActivityCounter.setGraceValue(graceValue);
	}

	/**
	* Sets the group ID of this social activity counter.
	*
	* @param groupId the group ID of this social activity counter
	*/
	@Override
	public void setGroupId(long groupId) {
		_socialActivityCounter.setGroupId(groupId);
	}

	/**
	* Sets the name of this social activity counter.
	*
	* @param name the name of this social activity counter
	*/
	@Override
	public void setName(java.lang.String name) {
		_socialActivityCounter.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_socialActivityCounter.setNew(n);
	}

	/**
	* Sets the owner type of this social activity counter.
	*
	* @param ownerType the owner type of this social activity counter
	*/
	@Override
	public void setOwnerType(int ownerType) {
		_socialActivityCounter.setOwnerType(ownerType);
	}

	/**
	* Sets the primary key of this social activity counter.
	*
	* @param primaryKey the primary key of this social activity counter
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_socialActivityCounter.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityCounter.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the start period of this social activity counter.
	*
	* @param startPeriod the start period of this social activity counter
	*/
	@Override
	public void setStartPeriod(int startPeriod) {
		_socialActivityCounter.setStartPeriod(startPeriod);
	}

	/**
	* Sets the total value of this social activity counter.
	*
	* @param totalValue the total value of this social activity counter
	*/
	@Override
	public void setTotalValue(int totalValue) {
		_socialActivityCounter.setTotalValue(totalValue);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityCounter> toCacheModel() {
		return _socialActivityCounter.toCacheModel();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityCounter toEscapedModel() {
		return new SocialActivityCounterWrapper(_socialActivityCounter.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityCounter.toString();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityCounter toUnescapedModel() {
		return new SocialActivityCounterWrapper(_socialActivityCounter.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _socialActivityCounter.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityCounterWrapper)) {
			return false;
		}

		SocialActivityCounterWrapper socialActivityCounterWrapper = (SocialActivityCounterWrapper)obj;

		if (Validator.equals(_socialActivityCounter,
					socialActivityCounterWrapper._socialActivityCounter)) {
			return true;
		}

		return false;
	}

	@Override
	public SocialActivityCounter getWrappedModel() {
		return _socialActivityCounter;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _socialActivityCounter.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _socialActivityCounter.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_socialActivityCounter.resetOriginalValues();
	}

	private final SocialActivityCounter _socialActivityCounter;
}