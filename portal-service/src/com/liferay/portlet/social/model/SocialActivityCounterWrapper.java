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

package com.liferay.portlet.social.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityCounter}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityCounter
 * @generated
 */
public class SocialActivityCounterWrapper implements SocialActivityCounter,
	ModelWrapper<SocialActivityCounter> {
	public SocialActivityCounterWrapper(
		SocialActivityCounter socialActivityCounter) {
		_socialActivityCounter = socialActivityCounter;
	}

	public Class<?> getModelClass() {
		return SocialActivityCounter.class;
	}

	public String getModelClassName() {
		return SocialActivityCounter.class.getName();
	}

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

	/**
	* Returns the primary key of this social activity counter.
	*
	* @return the primary key of this social activity counter
	*/
	public long getPrimaryKey() {
		return _socialActivityCounter.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity counter.
	*
	* @param primaryKey the primary key of this social activity counter
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialActivityCounter.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity counter ID of this social activity counter.
	*
	* @return the activity counter ID of this social activity counter
	*/
	public long getActivityCounterId() {
		return _socialActivityCounter.getActivityCounterId();
	}

	/**
	* Sets the activity counter ID of this social activity counter.
	*
	* @param activityCounterId the activity counter ID of this social activity counter
	*/
	public void setActivityCounterId(long activityCounterId) {
		_socialActivityCounter.setActivityCounterId(activityCounterId);
	}

	/**
	* Returns the group ID of this social activity counter.
	*
	* @return the group ID of this social activity counter
	*/
	public long getGroupId() {
		return _socialActivityCounter.getGroupId();
	}

	/**
	* Sets the group ID of this social activity counter.
	*
	* @param groupId the group ID of this social activity counter
	*/
	public void setGroupId(long groupId) {
		_socialActivityCounter.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity counter.
	*
	* @return the company ID of this social activity counter
	*/
	public long getCompanyId() {
		return _socialActivityCounter.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity counter.
	*
	* @param companyId the company ID of this social activity counter
	*/
	public void setCompanyId(long companyId) {
		_socialActivityCounter.setCompanyId(companyId);
	}

	/**
	* Returns the fully qualified class name of this social activity counter.
	*
	* @return the fully qualified class name of this social activity counter
	*/
	public java.lang.String getClassName() {
		return _socialActivityCounter.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_socialActivityCounter.setClassName(className);
	}

	/**
	* Returns the class name ID of this social activity counter.
	*
	* @return the class name ID of this social activity counter
	*/
	public long getClassNameId() {
		return _socialActivityCounter.getClassNameId();
	}

	/**
	* Sets the class name ID of this social activity counter.
	*
	* @param classNameId the class name ID of this social activity counter
	*/
	public void setClassNameId(long classNameId) {
		_socialActivityCounter.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this social activity counter.
	*
	* @return the class p k of this social activity counter
	*/
	public long getClassPK() {
		return _socialActivityCounter.getClassPK();
	}

	/**
	* Sets the class p k of this social activity counter.
	*
	* @param classPK the class p k of this social activity counter
	*/
	public void setClassPK(long classPK) {
		_socialActivityCounter.setClassPK(classPK);
	}

	/**
	* Returns the name of this social activity counter.
	*
	* @return the name of this social activity counter
	*/
	public java.lang.String getName() {
		return _socialActivityCounter.getName();
	}

	/**
	* Sets the name of this social activity counter.
	*
	* @param name the name of this social activity counter
	*/
	public void setName(java.lang.String name) {
		_socialActivityCounter.setName(name);
	}

	/**
	* Returns the owner type of this social activity counter.
	*
	* @return the owner type of this social activity counter
	*/
	public int getOwnerType() {
		return _socialActivityCounter.getOwnerType();
	}

	/**
	* Sets the owner type of this social activity counter.
	*
	* @param ownerType the owner type of this social activity counter
	*/
	public void setOwnerType(int ownerType) {
		_socialActivityCounter.setOwnerType(ownerType);
	}

	/**
	* Returns the current value of this social activity counter.
	*
	* @return the current value of this social activity counter
	*/
	public int getCurrentValue() {
		return _socialActivityCounter.getCurrentValue();
	}

	/**
	* Sets the current value of this social activity counter.
	*
	* @param currentValue the current value of this social activity counter
	*/
	public void setCurrentValue(int currentValue) {
		_socialActivityCounter.setCurrentValue(currentValue);
	}

	/**
	* Returns the total value of this social activity counter.
	*
	* @return the total value of this social activity counter
	*/
	public int getTotalValue() {
		return _socialActivityCounter.getTotalValue();
	}

	/**
	* Sets the total value of this social activity counter.
	*
	* @param totalValue the total value of this social activity counter
	*/
	public void setTotalValue(int totalValue) {
		_socialActivityCounter.setTotalValue(totalValue);
	}

	/**
	* Returns the grace value of this social activity counter.
	*
	* @return the grace value of this social activity counter
	*/
	public int getGraceValue() {
		return _socialActivityCounter.getGraceValue();
	}

	/**
	* Sets the grace value of this social activity counter.
	*
	* @param graceValue the grace value of this social activity counter
	*/
	public void setGraceValue(int graceValue) {
		_socialActivityCounter.setGraceValue(graceValue);
	}

	/**
	* Returns the start period of this social activity counter.
	*
	* @return the start period of this social activity counter
	*/
	public int getStartPeriod() {
		return _socialActivityCounter.getStartPeriod();
	}

	/**
	* Sets the start period of this social activity counter.
	*
	* @param startPeriod the start period of this social activity counter
	*/
	public void setStartPeriod(int startPeriod) {
		_socialActivityCounter.setStartPeriod(startPeriod);
	}

	/**
	* Returns the end period of this social activity counter.
	*
	* @return the end period of this social activity counter
	*/
	public int getEndPeriod() {
		return _socialActivityCounter.getEndPeriod();
	}

	/**
	* Sets the end period of this social activity counter.
	*
	* @param endPeriod the end period of this social activity counter
	*/
	public void setEndPeriod(int endPeriod) {
		_socialActivityCounter.setEndPeriod(endPeriod);
	}

	/**
	* Returns the active of this social activity counter.
	*
	* @return the active of this social activity counter
	*/
	public boolean getActive() {
		return _socialActivityCounter.getActive();
	}

	/**
	* Returns <code>true</code> if this social activity counter is active.
	*
	* @return <code>true</code> if this social activity counter is active; <code>false</code> otherwise
	*/
	public boolean isActive() {
		return _socialActivityCounter.isActive();
	}

	/**
	* Sets whether this social activity counter is active.
	*
	* @param active the active of this social activity counter
	*/
	public void setActive(boolean active) {
		_socialActivityCounter.setActive(active);
	}

	public boolean isNew() {
		return _socialActivityCounter.isNew();
	}

	public void setNew(boolean n) {
		_socialActivityCounter.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivityCounter.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivityCounter.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivityCounter.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityCounter.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityCounter.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityCounter.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityCounter.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityCounterWrapper((SocialActivityCounter)_socialActivityCounter.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter) {
		return _socialActivityCounter.compareTo(socialActivityCounter);
	}

	@Override
	public int hashCode() {
		return _socialActivityCounter.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityCounter> toCacheModel() {
		return _socialActivityCounter.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialActivityCounter toEscapedModel() {
		return new SocialActivityCounterWrapper(_socialActivityCounter.toEscapedModel());
	}

	public com.liferay.portlet.social.model.SocialActivityCounter toUnescapedModel() {
		return new SocialActivityCounterWrapper(_socialActivityCounter.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityCounter.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivityCounter.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounter.persist();
	}

	public boolean isActivePeriod(int periodLength) {
		return _socialActivityCounter.isActivePeriod(periodLength);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialActivityCounter getWrappedSocialActivityCounter() {
		return _socialActivityCounter;
	}

	public SocialActivityCounter getWrappedModel() {
		return _socialActivityCounter;
	}

	public void resetOriginalValues() {
		_socialActivityCounter.resetOriginalValues();
	}

	private SocialActivityCounter _socialActivityCounter;
}