/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
	implements SocialActivityAchievement {
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
	* Returns the unlock date of this social activity achievement.
	*
	* @return the unlock date of this social activity achievement
	*/
	public java.util.Date getUnlockDate() {
		return _socialActivityAchievement.getUnlockDate();
	}

	/**
	* Sets the unlock date of this social activity achievement.
	*
	* @param unlockDate the unlock date of this social activity achievement
	*/
	public void setUnlockDate(java.util.Date unlockDate) {
		_socialActivityAchievement.setUnlockDate(unlockDate);
	}

	/**
	* Returns the unlocked by of this social activity achievement.
	*
	* @return the unlocked by of this social activity achievement
	*/
	public long getUnlockedBy() {
		return _socialActivityAchievement.getUnlockedBy();
	}

	/**
	* Sets the unlocked by of this social activity achievement.
	*
	* @param unlockedBy the unlocked by of this social activity achievement
	*/
	public void setUnlockedBy(long unlockedBy) {
		_socialActivityAchievement.setUnlockedBy(unlockedBy);
	}

	/**
	* Returns the first unlock of this social activity achievement.
	*
	* @return the first unlock of this social activity achievement
	*/
	public boolean getFirstUnlock() {
		return _socialActivityAchievement.getFirstUnlock();
	}

	/**
	* Returns <code>true</code> if this social activity achievement is first unlock.
	*
	* @return <code>true</code> if this social activity achievement is first unlock; <code>false</code> otherwise
	*/
	public boolean isFirstUnlock() {
		return _socialActivityAchievement.isFirstUnlock();
	}

	/**
	* Sets whether this social activity achievement is first unlock.
	*
	* @param firstUnlock the first unlock of this social activity achievement
	*/
	public void setFirstUnlock(boolean firstUnlock) {
		_socialActivityAchievement.setFirstUnlock(firstUnlock);
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

	public void setEscapedModel(boolean escapedModel) {
		_socialActivityAchievement.setEscapedModel(escapedModel);
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

	public SocialActivityAchievement getWrappedSocialActivityAchievement() {
		return _socialActivityAchievement;
	}

	public void resetOriginalValues() {
		_socialActivityAchievement.resetOriginalValues();
	}

	private SocialActivityAchievement _socialActivityAchievement;
}