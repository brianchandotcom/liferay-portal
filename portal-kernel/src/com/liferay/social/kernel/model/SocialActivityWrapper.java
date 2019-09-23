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
 * This class is a wrapper for {@link SocialActivity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivity
 * @generated
 */
public class SocialActivityWrapper
	extends BaseModelWrapper<SocialActivity>
	implements ModelWrapper<SocialActivity>, SocialActivity {

	public SocialActivityWrapper(SocialActivity socialActivity) {
		super(socialActivity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityId", getActivityId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("activitySetId", getActivitySetId());
		attributes.put("mirrorActivityId", getMirrorActivityId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("parentClassNameId", getParentClassNameId());
		attributes.put("parentClassPK", getParentClassPK());
		attributes.put("type", getType());
		attributes.put("extraData", getExtraData());
		attributes.put("receiverUserId", getReceiverUserId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityId = (Long)attributes.get("activityId");

		if (activityId != null) {
			setActivityId(activityId);
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

		Long activitySetId = (Long)attributes.get("activitySetId");

		if (activitySetId != null) {
			setActivitySetId(activitySetId);
		}

		Long mirrorActivityId = (Long)attributes.get("mirrorActivityId");

		if (mirrorActivityId != null) {
			setMirrorActivityId(mirrorActivityId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long parentClassNameId = (Long)attributes.get("parentClassNameId");

		if (parentClassNameId != null) {
			setParentClassNameId(parentClassNameId);
		}

		Long parentClassPK = (Long)attributes.get("parentClassPK");

		if (parentClassPK != null) {
			setParentClassPK(parentClassPK);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extraData = (String)attributes.get("extraData");

		if (extraData != null) {
			setExtraData(extraData);
		}

		Long receiverUserId = (Long)attributes.get("receiverUserId");

		if (receiverUserId != null) {
			setReceiverUserId(receiverUserId);
		}
	}

	/**
	 * Returns the activity ID of this social activity.
	 *
	 * @return the activity ID of this social activity
	 */
	@Override
	public long getActivityId() {
		return model.getActivityId();
	}

	/**
	 * Returns the activity set ID of this social activity.
	 *
	 * @return the activity set ID of this social activity
	 */
	@Override
	public long getActivitySetId() {
		return model.getActivitySetId();
	}

	@Override
	public com.liferay.asset.kernel.model.AssetEntry getAssetEntry() {
		return model.getAssetEntry();
	}

	/**
	 * Returns the fully qualified class name of this social activity.
	 *
	 * @return the fully qualified class name of this social activity
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this social activity.
	 *
	 * @return the class name ID of this social activity
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this social activity.
	 *
	 * @return the class pk of this social activity
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this social activity.
	 *
	 * @return the company ID of this social activity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this social activity.
	 *
	 * @return the create date of this social activity
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the extra data of this social activity.
	 *
	 * @return the extra data of this social activity
	 */
	@Override
	public String getExtraData() {
		return model.getExtraData();
	}

	@Override
	public String getExtraDataValue(String key)
		throws com.liferay.portal.kernel.json.JSONException {

		return model.getExtraDataValue(key);
	}

	@Override
	public String getExtraDataValue(String key, java.util.Locale locale)
		throws com.liferay.portal.kernel.json.JSONException {

		return model.getExtraDataValue(key, locale);
	}

	/**
	 * Returns the group ID of this social activity.
	 *
	 * @return the group ID of this social activity
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mirror activity ID of this social activity.
	 *
	 * @return the mirror activity ID of this social activity
	 */
	@Override
	public long getMirrorActivityId() {
		return model.getMirrorActivityId();
	}

	/**
	 * Returns the parent class name ID of this social activity.
	 *
	 * @return the parent class name ID of this social activity
	 */
	@Override
	public long getParentClassNameId() {
		return model.getParentClassNameId();
	}

	/**
	 * Returns the parent class pk of this social activity.
	 *
	 * @return the parent class pk of this social activity
	 */
	@Override
	public long getParentClassPK() {
		return model.getParentClassPK();
	}

	/**
	 * Returns the primary key of this social activity.
	 *
	 * @return the primary key of this social activity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the receiver user ID of this social activity.
	 *
	 * @return the receiver user ID of this social activity
	 */
	@Override
	public long getReceiverUserId() {
		return model.getReceiverUserId();
	}

	/**
	 * Returns the receiver user uuid of this social activity.
	 *
	 * @return the receiver user uuid of this social activity
	 */
	@Override
	public String getReceiverUserUuid() {
		return model.getReceiverUserUuid();
	}

	/**
	 * Returns the type of this social activity.
	 *
	 * @return the type of this social activity
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this social activity.
	 *
	 * @return the user ID of this social activity
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this social activity.
	 *
	 * @return the user uuid of this social activity
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isClassName(String className) {
		return model.isClassName(className);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a social activity model instance should use the <code>SocialActivity</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the activity ID of this social activity.
	 *
	 * @param activityId the activity ID of this social activity
	 */
	@Override
	public void setActivityId(long activityId) {
		model.setActivityId(activityId);
	}

	/**
	 * Sets the activity set ID of this social activity.
	 *
	 * @param activitySetId the activity set ID of this social activity
	 */
	@Override
	public void setActivitySetId(long activitySetId) {
		model.setActivitySetId(activitySetId);
	}

	@Override
	public void setAssetEntry(
		com.liferay.asset.kernel.model.AssetEntry assetEntry) {

		model.setAssetEntry(assetEntry);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this social activity.
	 *
	 * @param classNameId the class name ID of this social activity
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this social activity.
	 *
	 * @param classPK the class pk of this social activity
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this social activity.
	 *
	 * @param companyId the company ID of this social activity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this social activity.
	 *
	 * @param createDate the create date of this social activity
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the extra data of this social activity.
	 *
	 * @param extraData the extra data of this social activity
	 */
	@Override
	public void setExtraData(String extraData) {
		model.setExtraData(extraData);
	}

	@Override
	public void setExtraDataValue(String key, String value)
		throws com.liferay.portal.kernel.json.JSONException {

		model.setExtraDataValue(key, value);
	}

	/**
	 * Sets the group ID of this social activity.
	 *
	 * @param groupId the group ID of this social activity
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mirror activity ID of this social activity.
	 *
	 * @param mirrorActivityId the mirror activity ID of this social activity
	 */
	@Override
	public void setMirrorActivityId(long mirrorActivityId) {
		model.setMirrorActivityId(mirrorActivityId);
	}

	/**
	 * Sets the parent class name ID of this social activity.
	 *
	 * @param parentClassNameId the parent class name ID of this social activity
	 */
	@Override
	public void setParentClassNameId(long parentClassNameId) {
		model.setParentClassNameId(parentClassNameId);
	}

	/**
	 * Sets the parent class pk of this social activity.
	 *
	 * @param parentClassPK the parent class pk of this social activity
	 */
	@Override
	public void setParentClassPK(long parentClassPK) {
		model.setParentClassPK(parentClassPK);
	}

	/**
	 * Sets the primary key of this social activity.
	 *
	 * @param primaryKey the primary key of this social activity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the receiver user ID of this social activity.
	 *
	 * @param receiverUserId the receiver user ID of this social activity
	 */
	@Override
	public void setReceiverUserId(long receiverUserId) {
		model.setReceiverUserId(receiverUserId);
	}

	/**
	 * Sets the receiver user uuid of this social activity.
	 *
	 * @param receiverUserUuid the receiver user uuid of this social activity
	 */
	@Override
	public void setReceiverUserUuid(String receiverUserUuid) {
		model.setReceiverUserUuid(receiverUserUuid);
	}

	/**
	 * Sets the type of this social activity.
	 *
	 * @param type the type of this social activity
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this social activity.
	 *
	 * @param userId the user ID of this social activity
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this social activity.
	 *
	 * @param userUuid the user uuid of this social activity
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SocialActivityWrapper wrap(SocialActivity socialActivity) {
		return new SocialActivityWrapper(socialActivity);
	}

}