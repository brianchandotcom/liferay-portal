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
 * This class is a wrapper for {@link SocialActivityLimit}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimit
 * @generated
 */
public class SocialActivityLimitWrapper
	extends BaseModelWrapper<SocialActivityLimit>
	implements ModelWrapper<SocialActivityLimit>, SocialActivityLimit {

	public SocialActivityLimitWrapper(SocialActivityLimit socialActivityLimit) {
		super(socialActivityLimit);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("activityLimitId", getActivityLimitId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("activityType", getActivityType());
		attributes.put("activityCounterName", getActivityCounterName());
		attributes.put("value", getValue());

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

		Long activityLimitId = (Long)attributes.get("activityLimitId");

		if (activityLimitId != null) {
			setActivityLimitId(activityLimitId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Integer activityType = (Integer)attributes.get("activityType");

		if (activityType != null) {
			setActivityType(activityType);
		}

		String activityCounterName = (String)attributes.get(
			"activityCounterName");

		if (activityCounterName != null) {
			setActivityCounterName(activityCounterName);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public SocialActivityLimit cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the activity counter name of this social activity limit.
	 *
	 * @return the activity counter name of this social activity limit
	 */
	@Override
	public String getActivityCounterName() {
		return model.getActivityCounterName();
	}

	/**
	 * Returns the activity limit ID of this social activity limit.
	 *
	 * @return the activity limit ID of this social activity limit
	 */
	@Override
	public long getActivityLimitId() {
		return model.getActivityLimitId();
	}

	/**
	 * Returns the activity type of this social activity limit.
	 *
	 * @return the activity type of this social activity limit
	 */
	@Override
	public int getActivityType() {
		return model.getActivityType();
	}

	/**
	 * Returns the fully qualified class name of this social activity limit.
	 *
	 * @return the fully qualified class name of this social activity limit
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this social activity limit.
	 *
	 * @return the class name ID of this social activity limit
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this social activity limit.
	 *
	 * @return the class pk of this social activity limit
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this social activity limit.
	 *
	 * @return the company ID of this social activity limit
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public int getCount() {
		return model.getCount();
	}

	@Override
	public int getCount(int limitPeriod) {
		return model.getCount(limitPeriod);
	}

	/**
	 * Returns the ct collection ID of this social activity limit.
	 *
	 * @return the ct collection ID of this social activity limit
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this social activity limit.
	 *
	 * @return the group ID of this social activity limit
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this social activity limit.
	 *
	 * @return the mvcc version of this social activity limit
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this social activity limit.
	 *
	 * @return the primary key of this social activity limit
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this social activity limit.
	 *
	 * @return the user ID of this social activity limit
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this social activity limit.
	 *
	 * @return the user uuid of this social activity limit
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this social activity limit.
	 *
	 * @return the value of this social activity limit
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
	 * Sets the activity counter name of this social activity limit.
	 *
	 * @param activityCounterName the activity counter name of this social activity limit
	 */
	@Override
	public void setActivityCounterName(String activityCounterName) {
		model.setActivityCounterName(activityCounterName);
	}

	/**
	 * Sets the activity limit ID of this social activity limit.
	 *
	 * @param activityLimitId the activity limit ID of this social activity limit
	 */
	@Override
	public void setActivityLimitId(long activityLimitId) {
		model.setActivityLimitId(activityLimitId);
	}

	/**
	 * Sets the activity type of this social activity limit.
	 *
	 * @param activityType the activity type of this social activity limit
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
	 * Sets the class name ID of this social activity limit.
	 *
	 * @param classNameId the class name ID of this social activity limit
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this social activity limit.
	 *
	 * @param classPK the class pk of this social activity limit
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this social activity limit.
	 *
	 * @param companyId the company ID of this social activity limit
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	@Override
	public void setCount(int limitPeriod, int count) {
		model.setCount(limitPeriod, count);
	}

	/**
	 * Sets the ct collection ID of this social activity limit.
	 *
	 * @param ctCollectionId the ct collection ID of this social activity limit
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this social activity limit.
	 *
	 * @param groupId the group ID of this social activity limit
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this social activity limit.
	 *
	 * @param mvccVersion the mvcc version of this social activity limit
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this social activity limit.
	 *
	 * @param primaryKey the primary key of this social activity limit
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this social activity limit.
	 *
	 * @param userId the user ID of this social activity limit
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this social activity limit.
	 *
	 * @param userUuid the user uuid of this social activity limit
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this social activity limit.
	 *
	 * @param value the value of this social activity limit
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<SocialActivityLimit, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SocialActivityLimit, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected SocialActivityLimitWrapper wrap(
		SocialActivityLimit socialActivityLimit) {

		return new SocialActivityLimitWrapper(socialActivityLimit);
	}

}