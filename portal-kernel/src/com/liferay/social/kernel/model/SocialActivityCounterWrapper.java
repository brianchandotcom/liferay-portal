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

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class SocialActivityCounterWrapper
	extends BaseModelWrapper<SocialActivityCounter>
	implements ModelWrapper<SocialActivityCounter>, SocialActivityCounter {

	public SocialActivityCounterWrapper(
		SocialActivityCounter socialActivityCounter) {

		super(socialActivityCounter);
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
		attributes.put("active", isActive());

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

	/**
	 * Returns the active of this social activity counter.
	 *
	 * @return the active of this social activity counter
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the activity counter ID of this social activity counter.
	 *
	 * @return the activity counter ID of this social activity counter
	 */
	@Override
	public long getActivityCounterId() {
		return model.getActivityCounterId();
	}

	/**
	 * Returns the fully qualified class name of this social activity counter.
	 *
	 * @return the fully qualified class name of this social activity counter
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this social activity counter.
	 *
	 * @return the class name ID of this social activity counter
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this social activity counter.
	 *
	 * @return the class pk of this social activity counter
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this social activity counter.
	 *
	 * @return the company ID of this social activity counter
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the current value of this social activity counter.
	 *
	 * @return the current value of this social activity counter
	 */
	@Override
	public int getCurrentValue() {
		return model.getCurrentValue();
	}

	/**
	 * Returns the end period of this social activity counter.
	 *
	 * @return the end period of this social activity counter
	 */
	@Override
	public int getEndPeriod() {
		return model.getEndPeriod();
	}

	/**
	 * Returns the grace value of this social activity counter.
	 *
	 * @return the grace value of this social activity counter
	 */
	@Override
	public int getGraceValue() {
		return model.getGraceValue();
	}

	/**
	 * Returns the group ID of this social activity counter.
	 *
	 * @return the group ID of this social activity counter
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the name of this social activity counter.
	 *
	 * @return the name of this social activity counter
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the owner type of this social activity counter.
	 *
	 * @return the owner type of this social activity counter
	 */
	@Override
	public int getOwnerType() {
		return model.getOwnerType();
	}

	/**
	 * Returns the primary key of this social activity counter.
	 *
	 * @return the primary key of this social activity counter
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start period of this social activity counter.
	 *
	 * @return the start period of this social activity counter
	 */
	@Override
	public int getStartPeriod() {
		return model.getStartPeriod();
	}

	/**
	 * Returns the total value of this social activity counter.
	 *
	 * @return the total value of this social activity counter
	 */
	@Override
	public int getTotalValue() {
		return model.getTotalValue();
	}

	/**
	 * Returns <code>true</code> if this social activity counter is active.
	 *
	 * @return <code>true</code> if this social activity counter is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	@Override
	public boolean isActivePeriod(int periodLength) {
		return model.isActivePeriod(periodLength);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social activity counter model instance should use the <code>SocialActivityCounter</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this social activity counter is active.
	 *
	 * @param active the active of this social activity counter
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the activity counter ID of this social activity counter.
	 *
	 * @param activityCounterId the activity counter ID of this social activity counter
	 */
	@Override
	public void setActivityCounterId(long activityCounterId) {
		model.setActivityCounterId(activityCounterId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this social activity counter.
	 *
	 * @param classNameId the class name ID of this social activity counter
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this social activity counter.
	 *
	 * @param classPK the class pk of this social activity counter
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this social activity counter.
	 *
	 * @param companyId the company ID of this social activity counter
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the current value of this social activity counter.
	 *
	 * @param currentValue the current value of this social activity counter
	 */
	@Override
	public void setCurrentValue(int currentValue) {
		model.setCurrentValue(currentValue);
	}

	/**
	 * Sets the end period of this social activity counter.
	 *
	 * @param endPeriod the end period of this social activity counter
	 */
	@Override
	public void setEndPeriod(int endPeriod) {
		model.setEndPeriod(endPeriod);
	}

	/**
	 * Sets the grace value of this social activity counter.
	 *
	 * @param graceValue the grace value of this social activity counter
	 */
	@Override
	public void setGraceValue(int graceValue) {
		model.setGraceValue(graceValue);
	}

	/**
	 * Sets the group ID of this social activity counter.
	 *
	 * @param groupId the group ID of this social activity counter
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the name of this social activity counter.
	 *
	 * @param name the name of this social activity counter
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the owner type of this social activity counter.
	 *
	 * @param ownerType the owner type of this social activity counter
	 */
	@Override
	public void setOwnerType(int ownerType) {
		model.setOwnerType(ownerType);
	}

	/**
	 * Sets the primary key of this social activity counter.
	 *
	 * @param primaryKey the primary key of this social activity counter
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start period of this social activity counter.
	 *
	 * @param startPeriod the start period of this social activity counter
	 */
	@Override
	public void setStartPeriod(int startPeriod) {
		model.setStartPeriod(startPeriod);
	}

	/**
	 * Sets the total value of this social activity counter.
	 *
	 * @param totalValue the total value of this social activity counter
	 */
	@Override
	public void setTotalValue(int totalValue) {
		model.setTotalValue(totalValue);
	}

	@Override
	protected SocialActivityCounterWrapper wrap(
		SocialActivityCounter socialActivityCounter) {

		return new SocialActivityCounterWrapper(socialActivityCounter);
	}

}