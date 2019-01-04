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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivitySetting}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetting
 * @generated
 */
@ProviderType
public class SocialActivitySettingWrapper extends BaseModelWrapper<SocialActivitySetting>
	implements SocialActivitySetting, ModelWrapper<SocialActivitySetting> {
	public SocialActivitySettingWrapper(
		SocialActivitySetting socialActivitySetting) {
		super(socialActivitySetting);
	}

	/**
	* Returns the activity setting ID of this social activity setting.
	*
	* @return the activity setting ID of this social activity setting
	*/
	@Override
	public long getActivitySettingId() {
		return model.getActivitySettingId();
	}

	/**
	* Returns the activity type of this social activity setting.
	*
	* @return the activity type of this social activity setting
	*/
	@Override
	public int getActivityType() {
		return model.getActivityType();
	}

	/**
	* Returns the fully qualified class name of this social activity setting.
	*
	* @return the fully qualified class name of this social activity setting
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this social activity setting.
	*
	* @return the class name ID of this social activity setting
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the company ID of this social activity setting.
	*
	* @return the company ID of this social activity setting
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the group ID of this social activity setting.
	*
	* @return the group ID of this social activity setting
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the name of this social activity setting.
	*
	* @return the name of this social activity setting
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this social activity setting.
	*
	* @return the primary key of this social activity setting
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the value of this social activity setting.
	*
	* @return the value of this social activity setting
	*/
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the activity setting ID of this social activity setting.
	*
	* @param activitySettingId the activity setting ID of this social activity setting
	*/
	@Override
	public void setActivitySettingId(long activitySettingId) {
		model.setActivitySettingId(activitySettingId);
	}

	/**
	* Sets the activity type of this social activity setting.
	*
	* @param activityType the activity type of this social activity setting
	*/
	@Override
	public void setActivityType(int activityType) {
		model.setActivityType(activityType);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this social activity setting.
	*
	* @param classNameId the class name ID of this social activity setting
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this social activity setting.
	*
	* @param companyId the company ID of this social activity setting
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the group ID of this social activity setting.
	*
	* @param groupId the group ID of this social activity setting
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the name of this social activity setting.
	*
	* @param name the name of this social activity setting
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this social activity setting.
	*
	* @param primaryKey the primary key of this social activity setting
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the value of this social activity setting.
	*
	* @param value the value of this social activity setting
	*/
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected SocialActivitySettingWrapper wrap(
		SocialActivitySetting socialActivitySetting) {
		return new SocialActivitySettingWrapper(socialActivitySetting);
	}
}