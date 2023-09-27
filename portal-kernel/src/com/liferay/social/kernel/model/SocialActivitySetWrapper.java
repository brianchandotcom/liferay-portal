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
 * This class is a wrapper for {@link SocialActivitySet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySet
 * @generated
 */
public class SocialActivitySetWrapper
	extends BaseModelWrapper<SocialActivitySet>
	implements ModelWrapper<SocialActivitySet>, SocialActivitySet {

	public SocialActivitySetWrapper(SocialActivitySet socialActivitySet) {
		super(socialActivitySet);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("activitySetId", getActivitySetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("type", getType());
		attributes.put("extraData", getExtraData());
		attributes.put("activityCount", getActivityCount());

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

		Long activitySetId = (Long)attributes.get("activitySetId");

		if (activitySetId != null) {
			setActivitySetId(activitySetId);
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

		Long modifiedDate = (Long)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extraData = (String)attributes.get("extraData");

		if (extraData != null) {
			setExtraData(extraData);
		}

		Integer activityCount = (Integer)attributes.get("activityCount");

		if (activityCount != null) {
			setActivityCount(activityCount);
		}
	}

	@Override
	public SocialActivitySet cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the activity count of this social activity set.
	 *
	 * @return the activity count of this social activity set
	 */
	@Override
	public int getActivityCount() {
		return model.getActivityCount();
	}

	/**
	 * Returns the activity set ID of this social activity set.
	 *
	 * @return the activity set ID of this social activity set
	 */
	@Override
	public long getActivitySetId() {
		return model.getActivitySetId();
	}

	/**
	 * Returns the fully qualified class name of this social activity set.
	 *
	 * @return the fully qualified class name of this social activity set
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this social activity set.
	 *
	 * @return the class name ID of this social activity set
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this social activity set.
	 *
	 * @return the class pk of this social activity set
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this social activity set.
	 *
	 * @return the company ID of this social activity set
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this social activity set.
	 *
	 * @return the create date of this social activity set
	 */
	@Override
	public long getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this social activity set.
	 *
	 * @return the ct collection ID of this social activity set
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the extra data of this social activity set.
	 *
	 * @return the extra data of this social activity set
	 */
	@Override
	public String getExtraData() {
		return model.getExtraData();
	}

	/**
	 * Returns the group ID of this social activity set.
	 *
	 * @return the group ID of this social activity set
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this social activity set.
	 *
	 * @return the modified date of this social activity set
	 */
	@Override
	public long getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this social activity set.
	 *
	 * @return the mvcc version of this social activity set
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this social activity set.
	 *
	 * @return the primary key of this social activity set
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this social activity set.
	 *
	 * @return the type of this social activity set
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this social activity set.
	 *
	 * @return the user ID of this social activity set
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this social activity set.
	 *
	 * @return the user uuid of this social activity set
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the activity count of this social activity set.
	 *
	 * @param activityCount the activity count of this social activity set
	 */
	@Override
	public void setActivityCount(int activityCount) {
		model.setActivityCount(activityCount);
	}

	/**
	 * Sets the activity set ID of this social activity set.
	 *
	 * @param activitySetId the activity set ID of this social activity set
	 */
	@Override
	public void setActivitySetId(long activitySetId) {
		model.setActivitySetId(activitySetId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this social activity set.
	 *
	 * @param classNameId the class name ID of this social activity set
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this social activity set.
	 *
	 * @param classPK the class pk of this social activity set
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this social activity set.
	 *
	 * @param companyId the company ID of this social activity set
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this social activity set.
	 *
	 * @param createDate the create date of this social activity set
	 */
	@Override
	public void setCreateDate(long createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this social activity set.
	 *
	 * @param ctCollectionId the ct collection ID of this social activity set
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the extra data of this social activity set.
	 *
	 * @param extraData the extra data of this social activity set
	 */
	@Override
	public void setExtraData(String extraData) {
		model.setExtraData(extraData);
	}

	/**
	 * Sets the group ID of this social activity set.
	 *
	 * @param groupId the group ID of this social activity set
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this social activity set.
	 *
	 * @param modifiedDate the modified date of this social activity set
	 */
	@Override
	public void setModifiedDate(long modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this social activity set.
	 *
	 * @param mvccVersion the mvcc version of this social activity set
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this social activity set.
	 *
	 * @param primaryKey the primary key of this social activity set
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this social activity set.
	 *
	 * @param type the type of this social activity set
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this social activity set.
	 *
	 * @param userId the user ID of this social activity set
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this social activity set.
	 *
	 * @param userUuid the user uuid of this social activity set
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
	public Map<String, Function<SocialActivitySet, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SocialActivitySet, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected SocialActivitySetWrapper wrap(
		SocialActivitySet socialActivitySet) {

		return new SocialActivitySetWrapper(socialActivitySet);
	}

}