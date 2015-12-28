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
 * This class is a wrapper for {@link SocialActivitySet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySet
 * @generated
 */
@ProviderType
public class SocialActivitySetWrapper implements SocialActivitySet,
	ModelWrapper<SocialActivitySet> {
	public SocialActivitySetWrapper(SocialActivitySet socialActivitySet) {
		_socialActivitySet = socialActivitySet;
	}

	@Override
	public Class<?> getModelClass() {
		return SocialActivitySet.class;
	}

	@Override
	public String getModelClassName() {
		return SocialActivitySet.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

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
	public java.lang.Object clone() {
		return new SocialActivitySetWrapper((SocialActivitySet)_socialActivitySet.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return _socialActivitySet.compareTo(socialActivitySet);
	}

	/**
	* Returns the activity count of this social activity set.
	*
	* @return the activity count of this social activity set
	*/
	@Override
	public int getActivityCount() {
		return _socialActivitySet.getActivityCount();
	}

	/**
	* Returns the activity set ID of this social activity set.
	*
	* @return the activity set ID of this social activity set
	*/
	@Override
	public long getActivitySetId() {
		return _socialActivitySet.getActivitySetId();
	}

	/**
	* Returns the fully qualified class name of this social activity set.
	*
	* @return the fully qualified class name of this social activity set
	*/
	@Override
	public java.lang.String getClassName() {
		return _socialActivitySet.getClassName();
	}

	/**
	* Returns the class name ID of this social activity set.
	*
	* @return the class name ID of this social activity set
	*/
	@Override
	public long getClassNameId() {
		return _socialActivitySet.getClassNameId();
	}

	/**
	* Returns the class p k of this social activity set.
	*
	* @return the class p k of this social activity set
	*/
	@Override
	public long getClassPK() {
		return _socialActivitySet.getClassPK();
	}

	/**
	* Returns the company ID of this social activity set.
	*
	* @return the company ID of this social activity set
	*/
	@Override
	public long getCompanyId() {
		return _socialActivitySet.getCompanyId();
	}

	/**
	* Returns the create date of this social activity set.
	*
	* @return the create date of this social activity set
	*/
	@Override
	public long getCreateDate() {
		return _socialActivitySet.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivitySet.getExpandoBridge();
	}

	/**
	* Returns the extra data of this social activity set.
	*
	* @return the extra data of this social activity set
	*/
	@Override
	public java.lang.String getExtraData() {
		return _socialActivitySet.getExtraData();
	}

	/**
	* Returns the group ID of this social activity set.
	*
	* @return the group ID of this social activity set
	*/
	@Override
	public long getGroupId() {
		return _socialActivitySet.getGroupId();
	}

	/**
	* Returns the modified date of this social activity set.
	*
	* @return the modified date of this social activity set
	*/
	@Override
	public long getModifiedDate() {
		return _socialActivitySet.getModifiedDate();
	}

	/**
	* Returns the primary key of this social activity set.
	*
	* @return the primary key of this social activity set
	*/
	@Override
	public long getPrimaryKey() {
		return _socialActivitySet.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivitySet.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this social activity set.
	*
	* @return the type of this social activity set
	*/
	@Override
	public int getType() {
		return _socialActivitySet.getType();
	}

	/**
	* Returns the user ID of this social activity set.
	*
	* @return the user ID of this social activity set
	*/
	@Override
	public long getUserId() {
		return _socialActivitySet.getUserId();
	}

	/**
	* Returns the user uuid of this social activity set.
	*
	* @return the user uuid of this social activity set
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _socialActivitySet.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _socialActivitySet.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _socialActivitySet.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _socialActivitySet.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _socialActivitySet.isNew();
	}

	@Override
	public void persist() {
		_socialActivitySet.persist();
	}

	/**
	* Sets the activity count of this social activity set.
	*
	* @param activityCount the activity count of this social activity set
	*/
	@Override
	public void setActivityCount(int activityCount) {
		_socialActivitySet.setActivityCount(activityCount);
	}

	/**
	* Sets the activity set ID of this social activity set.
	*
	* @param activitySetId the activity set ID of this social activity set
	*/
	@Override
	public void setActivitySetId(long activitySetId) {
		_socialActivitySet.setActivitySetId(activitySetId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_socialActivitySet.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_socialActivitySet.setClassName(className);
	}

	/**
	* Sets the class name ID of this social activity set.
	*
	* @param classNameId the class name ID of this social activity set
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_socialActivitySet.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this social activity set.
	*
	* @param classPK the class p k of this social activity set
	*/
	@Override
	public void setClassPK(long classPK) {
		_socialActivitySet.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this social activity set.
	*
	* @param companyId the company ID of this social activity set
	*/
	@Override
	public void setCompanyId(long companyId) {
		_socialActivitySet.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this social activity set.
	*
	* @param createDate the create date of this social activity set
	*/
	@Override
	public void setCreateDate(long createDate) {
		_socialActivitySet.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_socialActivitySet.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_socialActivitySet.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivitySet.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the extra data of this social activity set.
	*
	* @param extraData the extra data of this social activity set
	*/
	@Override
	public void setExtraData(java.lang.String extraData) {
		_socialActivitySet.setExtraData(extraData);
	}

	/**
	* Sets the group ID of this social activity set.
	*
	* @param groupId the group ID of this social activity set
	*/
	@Override
	public void setGroupId(long groupId) {
		_socialActivitySet.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this social activity set.
	*
	* @param modifiedDate the modified date of this social activity set
	*/
	@Override
	public void setModifiedDate(long modifiedDate) {
		_socialActivitySet.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_socialActivitySet.setNew(n);
	}

	/**
	* Sets the primary key of this social activity set.
	*
	* @param primaryKey the primary key of this social activity set
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_socialActivitySet.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivitySet.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this social activity set.
	*
	* @param type the type of this social activity set
	*/
	@Override
	public void setType(int type) {
		_socialActivitySet.setType(type);
	}

	/**
	* Sets the user ID of this social activity set.
	*
	* @param userId the user ID of this social activity set
	*/
	@Override
	public void setUserId(long userId) {
		_socialActivitySet.setUserId(userId);
	}

	/**
	* Sets the user uuid of this social activity set.
	*
	* @param userUuid the user uuid of this social activity set
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivitySet.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivitySet> toCacheModel() {
		return _socialActivitySet.toCacheModel();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet toEscapedModel() {
		return new SocialActivitySetWrapper(_socialActivitySet.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivitySet.toString();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet toUnescapedModel() {
		return new SocialActivitySetWrapper(_socialActivitySet.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _socialActivitySet.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SocialActivitySetWrapper)) {
			return false;
		}

		SocialActivitySetWrapper socialActivitySetWrapper = (SocialActivitySetWrapper)obj;

		if (Validator.equals(_socialActivitySet,
					socialActivitySetWrapper._socialActivitySet)) {
			return true;
		}

		return false;
	}

	@Override
	public SocialActivitySet getWrappedModel() {
		return _socialActivitySet;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _socialActivitySet.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _socialActivitySet.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_socialActivitySet.resetOriginalValues();
	}

	private final SocialActivitySet _socialActivitySet;
}