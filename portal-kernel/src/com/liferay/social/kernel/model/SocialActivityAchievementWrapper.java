/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityAchievement}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievement
 * @generated
 */
public class SocialActivityAchievementWrapper
	extends BaseModelWrapper<SocialActivityAchievement>
	implements ModelWrapper<SocialActivityAchievement>,
			   SocialActivityAchievement {

	public SocialActivityAchievementWrapper(
		SocialActivityAchievement socialActivityAchievement) {

		super(socialActivityAchievement);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("activityAchievementId", getActivityAchievementId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("name", getName());
		attributes.put("firstInGroup", isFirstInGroup());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public SocialActivityAchievement cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the activity achievement ID of this social activity achievement.
	 *
	 * @return the activity achievement ID of this social activity achievement
	 */
	@Override
	public long getActivityAchievementId() {
		return model.getActivityAchievementId();
	}

	/**
	 * Returns the company ID of this social activity achievement.
	 *
	 * @return the company ID of this social activity achievement
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this social activity achievement.
	 *
	 * @return the create date of this social activity achievement
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this social activity achievement.
	 *
	 * @return the ct collection ID of this social activity achievement
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the first in group of this social activity achievement.
	 *
	 * @return the first in group of this social activity achievement
	 */
	@Override
	public boolean getFirstInGroup() {
		return model.getFirstInGroup();
	}

	/**
	 * Returns the group ID of this social activity achievement.
	 *
	 * @return the group ID of this social activity achievement
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this social activity achievement.
	 *
	 * @return the mvcc version of this social activity achievement
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this social activity achievement.
	 *
	 * @return the name of this social activity achievement
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this social activity achievement.
	 *
	 * @return the primary key of this social activity achievement
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this social activity achievement.
	 *
	 * @return the user ID of this social activity achievement
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this social activity achievement.
	 *
	 * @return the user uuid of this social activity achievement
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this social activity achievement is first in group.
	 *
	 * @return <code>true</code> if this social activity achievement is first in group; <code>false</code> otherwise
	 */
	@Override
	public boolean isFirstInGroup() {
		return model.isFirstInGroup();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the activity achievement ID of this social activity achievement.
	 *
	 * @param activityAchievementId the activity achievement ID of this social activity achievement
	 */
	@Override
	public void setActivityAchievementId(long activityAchievementId) {
		model.setActivityAchievementId(activityAchievementId);
	}

	/**
	 * Sets the company ID of this social activity achievement.
	 *
	 * @param companyId the company ID of this social activity achievement
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this social activity achievement.
	 *
	 * @param createDate the create date of this social activity achievement
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this social activity achievement.
	 *
	 * @param ctCollectionId the ct collection ID of this social activity achievement
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets whether this social activity achievement is first in group.
	 *
	 * @param firstInGroup the first in group of this social activity achievement
	 */
	@Override
	public void setFirstInGroup(boolean firstInGroup) {
		model.setFirstInGroup(firstInGroup);
	}

	/**
	 * Sets the group ID of this social activity achievement.
	 *
	 * @param groupId the group ID of this social activity achievement
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this social activity achievement.
	 *
	 * @param mvccVersion the mvcc version of this social activity achievement
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this social activity achievement.
	 *
	 * @param name the name of this social activity achievement
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this social activity achievement.
	 *
	 * @param primaryKey the primary key of this social activity achievement
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this social activity achievement.
	 *
	 * @param userId the user ID of this social activity achievement
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this social activity achievement.
	 *
	 * @param userUuid the user uuid of this social activity achievement
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<SocialActivityAchievement, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SocialActivityAchievement, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected SocialActivityAchievementWrapper wrap(
		SocialActivityAchievement socialActivityAchievement) {

		return new SocialActivityAchievementWrapper(socialActivityAchievement);
	}

}