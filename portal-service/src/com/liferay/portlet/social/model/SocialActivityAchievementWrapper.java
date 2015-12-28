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
 * This class is a wrapper for {@link SocialActivityAchievement}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievement
 * @generated
 */
@ProviderType
public class SocialActivityAchievementWrapper
	implements SocialActivityAchievement,
		ModelWrapper<SocialActivityAchievement> {
	public SocialActivityAchievementWrapper(
		SocialActivityAchievement socialActivityAchievement) {
		_socialActivityAchievement = socialActivityAchievement;
	}

	@Override
	public Class<?> getModelClass() {
		return SocialActivityAchievement.class;
	}

	@Override
	public String getModelClassName() {
		return SocialActivityAchievement.class.getName();
	}

	@Override
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

	@Override
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

	@Override
	public java.lang.Object clone() {
		return new SocialActivityAchievementWrapper((SocialActivityAchievement)_socialActivityAchievement.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement) {
		return _socialActivityAchievement.compareTo(socialActivityAchievement);
	}

	/**
	* Returns the activity achievement ID of this social activity achievement.
	*
	* @return the activity achievement ID of this social activity achievement
	*/
	@Override
	public long getActivityAchievementId() {
		return _socialActivityAchievement.getActivityAchievementId();
	}

	/**
	* Returns the company ID of this social activity achievement.
	*
	* @return the company ID of this social activity achievement
	*/
	@Override
	public long getCompanyId() {
		return _socialActivityAchievement.getCompanyId();
	}

	/**
	* Returns the create date of this social activity achievement.
	*
	* @return the create date of this social activity achievement
	*/
	@Override
	public long getCreateDate() {
		return _socialActivityAchievement.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityAchievement.getExpandoBridge();
	}

	/**
	* Returns the first in group of this social activity achievement.
	*
	* @return the first in group of this social activity achievement
	*/
	@Override
	public boolean getFirstInGroup() {
		return _socialActivityAchievement.getFirstInGroup();
	}

	/**
	* Returns the group ID of this social activity achievement.
	*
	* @return the group ID of this social activity achievement
	*/
	@Override
	public long getGroupId() {
		return _socialActivityAchievement.getGroupId();
	}

	/**
	* Returns the name of this social activity achievement.
	*
	* @return the name of this social activity achievement
	*/
	@Override
	public java.lang.String getName() {
		return _socialActivityAchievement.getName();
	}

	/**
	* Returns the primary key of this social activity achievement.
	*
	* @return the primary key of this social activity achievement
	*/
	@Override
	public long getPrimaryKey() {
		return _socialActivityAchievement.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityAchievement.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this social activity achievement.
	*
	* @return the user ID of this social activity achievement
	*/
	@Override
	public long getUserId() {
		return _socialActivityAchievement.getUserId();
	}

	/**
	* Returns the user uuid of this social activity achievement.
	*
	* @return the user uuid of this social activity achievement
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _socialActivityAchievement.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _socialActivityAchievement.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _socialActivityAchievement.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _socialActivityAchievement.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this social activity achievement is first in group.
	*
	* @return <code>true</code> if this social activity achievement is first in group; <code>false</code> otherwise
	*/
	@Override
	public boolean isFirstInGroup() {
		return _socialActivityAchievement.isFirstInGroup();
	}

	@Override
	public boolean isNew() {
		return _socialActivityAchievement.isNew();
	}

	@Override
	public void persist() {
		_socialActivityAchievement.persist();
	}

	/**
	* Sets the activity achievement ID of this social activity achievement.
	*
	* @param activityAchievementId the activity achievement ID of this social activity achievement
	*/
	@Override
	public void setActivityAchievementId(long activityAchievementId) {
		_socialActivityAchievement.setActivityAchievementId(activityAchievementId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_socialActivityAchievement.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this social activity achievement.
	*
	* @param companyId the company ID of this social activity achievement
	*/
	@Override
	public void setCompanyId(long companyId) {
		_socialActivityAchievement.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this social activity achievement.
	*
	* @param createDate the create date of this social activity achievement
	*/
	@Override
	public void setCreateDate(long createDate) {
		_socialActivityAchievement.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_socialActivityAchievement.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_socialActivityAchievement.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityAchievement.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this social activity achievement is first in group.
	*
	* @param firstInGroup the first in group of this social activity achievement
	*/
	@Override
	public void setFirstInGroup(boolean firstInGroup) {
		_socialActivityAchievement.setFirstInGroup(firstInGroup);
	}

	/**
	* Sets the group ID of this social activity achievement.
	*
	* @param groupId the group ID of this social activity achievement
	*/
	@Override
	public void setGroupId(long groupId) {
		_socialActivityAchievement.setGroupId(groupId);
	}

	/**
	* Sets the name of this social activity achievement.
	*
	* @param name the name of this social activity achievement
	*/
	@Override
	public void setName(java.lang.String name) {
		_socialActivityAchievement.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_socialActivityAchievement.setNew(n);
	}

	/**
	* Sets the primary key of this social activity achievement.
	*
	* @param primaryKey the primary key of this social activity achievement
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_socialActivityAchievement.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityAchievement.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this social activity achievement.
	*
	* @param userId the user ID of this social activity achievement
	*/
	@Override
	public void setUserId(long userId) {
		_socialActivityAchievement.setUserId(userId);
	}

	/**
	* Sets the user uuid of this social activity achievement.
	*
	* @param userUuid the user uuid of this social activity achievement
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivityAchievement.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityAchievement> toCacheModel() {
		return _socialActivityAchievement.toCacheModel();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement toEscapedModel() {
		return new SocialActivityAchievementWrapper(_socialActivityAchievement.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityAchievement.toString();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement toUnescapedModel() {
		return new SocialActivityAchievementWrapper(_socialActivityAchievement.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _socialActivityAchievement.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivityAchievementWrapper)) {
			return false;
		}

		SocialActivityAchievementWrapper socialActivityAchievementWrapper = (SocialActivityAchievementWrapper)obj;

		if (Validator.equals(_socialActivityAchievement,
					socialActivityAchievementWrapper._socialActivityAchievement)) {
			return true;
		}

		return false;
	}

	@Override
	public SocialActivityAchievement getWrappedModel() {
		return _socialActivityAchievement;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _socialActivityAchievement.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _socialActivityAchievement.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_socialActivityAchievement.resetOriginalValues();
	}

	private final SocialActivityAchievement _socialActivityAchievement;
}