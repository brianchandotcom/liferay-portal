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
 * This class is a wrapper for {@link SocialActivityAchievement}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityAchievement
 * @generated
 */
public class SocialActivityAchievementWrapper
	implements SocialActivityAchievement,
		ModelWrapper<SocialActivityAchievement> {
	public SocialActivityAchievementWrapper(
		SocialActivityAchievement socialActivityAchievement) {
		_socialActivityAchievement = socialActivityAchievement;
	}

	public Class<?> getModelClass() {
		return SocialActivityAchievement.class;
	}

	public String getModelClassName() {
		return SocialActivityAchievement.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityAchievementId", getActivityAchievementId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("name", getName());
		attributes.put("firstInGroup", getFirstInGroup());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityAchievementId = (Long)attributes.get(
				"activityAchievementId");

		if (activityAchievementId != null) {
			setActivityAchievementId(activityAchievementId);
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

		Long createDate = (Long)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean firstInGroup = (Boolean)attributes.get("firstInGroup");

		if (firstInGroup != null) {
			setFirstInGroup(firstInGroup);
		}
	}

	/**
	* Returns the primary key of this social activity achievement.
	*
	* @return the primary key of this social activity achievement
	*/
	public long getPrimaryKey() {
		return _socialActivityAchievement.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity achievement.
	*
	* @param primaryKey the primary key of this social activity achievement
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialActivityAchievement.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity achievement ID of this social activity achievement.
	*
	* @return the activity achievement ID of this social activity achievement
	*/
	public long getActivityAchievementId() {
		return _socialActivityAchievement.getActivityAchievementId();
	}

	/**
	* Sets the activity achievement ID of this social activity achievement.
	*
	* @param activityAchievementId the activity achievement ID of this social activity achievement
	*/
	public void setActivityAchievementId(long activityAchievementId) {
		_socialActivityAchievement.setActivityAchievementId(activityAchievementId);
	}

	/**
	* Returns the group ID of this social activity achievement.
	*
	* @return the group ID of this social activity achievement
	*/
	public long getGroupId() {
		return _socialActivityAchievement.getGroupId();
	}

	/**
	* Sets the group ID of this social activity achievement.
	*
	* @param groupId the group ID of this social activity achievement
	*/
	public void setGroupId(long groupId) {
		_socialActivityAchievement.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity achievement.
	*
	* @return the company ID of this social activity achievement
	*/
	public long getCompanyId() {
		return _socialActivityAchievement.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity achievement.
	*
	* @param companyId the company ID of this social activity achievement
	*/
	public void setCompanyId(long companyId) {
		_socialActivityAchievement.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social activity achievement.
	*
	* @return the user ID of this social activity achievement
	*/
	public long getUserId() {
		return _socialActivityAchievement.getUserId();
	}

	/**
	* Sets the user ID of this social activity achievement.
	*
	* @param userId the user ID of this social activity achievement
	*/
	public void setUserId(long userId) {
		_socialActivityAchievement.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social activity achievement.
	*
	* @return the user uuid of this social activity achievement
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityAchievement.getUserUuid();
	}

	/**
	* Sets the user uuid of this social activity achievement.
	*
	* @param userUuid the user uuid of this social activity achievement
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivityAchievement.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this social activity achievement.
	*
	* @return the create date of this social activity achievement
	*/
	public long getCreateDate() {
		return _socialActivityAchievement.getCreateDate();
	}

	/**
	* Sets the create date of this social activity achievement.
	*
	* @param createDate the create date of this social activity achievement
	*/
	public void setCreateDate(long createDate) {
		_socialActivityAchievement.setCreateDate(createDate);
	}

	/**
	* Returns the name of this social activity achievement.
	*
	* @return the name of this social activity achievement
	*/
	public java.lang.String getName() {
		return _socialActivityAchievement.getName();
	}

	/**
	* Sets the name of this social activity achievement.
	*
	* @param name the name of this social activity achievement
	*/
	public void setName(java.lang.String name) {
		_socialActivityAchievement.setName(name);
	}

	/**
	* Returns the first in group of this social activity achievement.
	*
	* @return the first in group of this social activity achievement
	*/
	public boolean getFirstInGroup() {
		return _socialActivityAchievement.getFirstInGroup();
	}

	/**
	* Returns <code>true</code> if this social activity achievement is first in group.
	*
	* @return <code>true</code> if this social activity achievement is first in group; <code>false</code> otherwise
	*/
	public boolean isFirstInGroup() {
		return _socialActivityAchievement.isFirstInGroup();
	}

	/**
	* Sets whether this social activity achievement is first in group.
	*
	* @param firstInGroup the first in group of this social activity achievement
	*/
	public void setFirstInGroup(boolean firstInGroup) {
		_socialActivityAchievement.setFirstInGroup(firstInGroup);
	}

	public boolean isNew() {
		return _socialActivityAchievement.isNew();
	}

	public void setNew(boolean n) {
		_socialActivityAchievement.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivityAchievement.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivityAchievement.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivityAchievement.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityAchievement.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityAchievement.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityAchievement.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityAchievement.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityAchievementWrapper((SocialActivityAchievement)_socialActivityAchievement.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement) {
		return _socialActivityAchievement.compareTo(socialActivityAchievement);
	}

	@Override
	public int hashCode() {
		return _socialActivityAchievement.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityAchievement> toCacheModel() {
		return _socialActivityAchievement.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialActivityAchievement toEscapedModel() {
		return new SocialActivityAchievementWrapper(_socialActivityAchievement.toEscapedModel());
	}

	public com.liferay.portlet.social.model.SocialActivityAchievement toUnescapedModel() {
		return new SocialActivityAchievementWrapper(_socialActivityAchievement.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityAchievement.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivityAchievement.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityAchievement.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialActivityAchievement getWrappedSocialActivityAchievement() {
		return _socialActivityAchievement;
	}

	public SocialActivityAchievement getWrappedModel() {
		return _socialActivityAchievement;
	}

	public void resetOriginalValues() {
		_socialActivityAchievement.resetOriginalValues();
	}

	private SocialActivityAchievement _socialActivityAchievement;
}