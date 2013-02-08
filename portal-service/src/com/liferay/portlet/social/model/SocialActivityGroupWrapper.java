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
 * This class is a wrapper for {@link SocialActivityGroup}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityGroup
 * @generated
 */
public class SocialActivityGroupWrapper implements SocialActivityGroup,
	ModelWrapper<SocialActivityGroup> {
	public SocialActivityGroupWrapper(SocialActivityGroup socialActivityGroup) {
		_socialActivityGroup = socialActivityGroup;
	}

	public Class<?> getModelClass() {
		return SocialActivityGroup.class;
	}

	public String getModelClassName() {
		return SocialActivityGroup.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("activityGroupId", getActivityGroupId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("type", getType());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long activityGroupId = (Long)attributes.get("activityGroupId");

		if (activityGroupId != null) {
			setActivityGroupId(activityGroupId);
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
	}

	/**
	* Returns the primary key of this social activity group.
	*
	* @return the primary key of this social activity group
	*/
	public long getPrimaryKey() {
		return _socialActivityGroup.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social activity group.
	*
	* @param primaryKey the primary key of this social activity group
	*/
	public void setPrimaryKey(long primaryKey) {
		_socialActivityGroup.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the activity group ID of this social activity group.
	*
	* @return the activity group ID of this social activity group
	*/
	public long getActivityGroupId() {
		return _socialActivityGroup.getActivityGroupId();
	}

	/**
	* Sets the activity group ID of this social activity group.
	*
	* @param activityGroupId the activity group ID of this social activity group
	*/
	public void setActivityGroupId(long activityGroupId) {
		_socialActivityGroup.setActivityGroupId(activityGroupId);
	}

	/**
	* Returns the group ID of this social activity group.
	*
	* @return the group ID of this social activity group
	*/
	public long getGroupId() {
		return _socialActivityGroup.getGroupId();
	}

	/**
	* Sets the group ID of this social activity group.
	*
	* @param groupId the group ID of this social activity group
	*/
	public void setGroupId(long groupId) {
		_socialActivityGroup.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this social activity group.
	*
	* @return the company ID of this social activity group
	*/
	public long getCompanyId() {
		return _socialActivityGroup.getCompanyId();
	}

	/**
	* Sets the company ID of this social activity group.
	*
	* @param companyId the company ID of this social activity group
	*/
	public void setCompanyId(long companyId) {
		_socialActivityGroup.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this social activity group.
	*
	* @return the user ID of this social activity group
	*/
	public long getUserId() {
		return _socialActivityGroup.getUserId();
	}

	/**
	* Sets the user ID of this social activity group.
	*
	* @param userId the user ID of this social activity group
	*/
	public void setUserId(long userId) {
		_socialActivityGroup.setUserId(userId);
	}

	/**
	* Returns the user uuid of this social activity group.
	*
	* @return the user uuid of this social activity group
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityGroup.getUserUuid();
	}

	/**
	* Sets the user uuid of this social activity group.
	*
	* @param userUuid the user uuid of this social activity group
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialActivityGroup.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this social activity group.
	*
	* @return the create date of this social activity group
	*/
	public long getCreateDate() {
		return _socialActivityGroup.getCreateDate();
	}

	/**
	* Sets the create date of this social activity group.
	*
	* @param createDate the create date of this social activity group
	*/
	public void setCreateDate(long createDate) {
		_socialActivityGroup.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this social activity group.
	*
	* @return the modified date of this social activity group
	*/
	public long getModifiedDate() {
		return _socialActivityGroup.getModifiedDate();
	}

	/**
	* Sets the modified date of this social activity group.
	*
	* @param modifiedDate the modified date of this social activity group
	*/
	public void setModifiedDate(long modifiedDate) {
		_socialActivityGroup.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this social activity group.
	*
	* @return the fully qualified class name of this social activity group
	*/
	public java.lang.String getClassName() {
		return _socialActivityGroup.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_socialActivityGroup.setClassName(className);
	}

	/**
	* Returns the class name ID of this social activity group.
	*
	* @return the class name ID of this social activity group
	*/
	public long getClassNameId() {
		return _socialActivityGroup.getClassNameId();
	}

	/**
	* Sets the class name ID of this social activity group.
	*
	* @param classNameId the class name ID of this social activity group
	*/
	public void setClassNameId(long classNameId) {
		_socialActivityGroup.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this social activity group.
	*
	* @return the class p k of this social activity group
	*/
	public long getClassPK() {
		return _socialActivityGroup.getClassPK();
	}

	/**
	* Sets the class p k of this social activity group.
	*
	* @param classPK the class p k of this social activity group
	*/
	public void setClassPK(long classPK) {
		_socialActivityGroup.setClassPK(classPK);
	}

	/**
	* Returns the type of this social activity group.
	*
	* @return the type of this social activity group
	*/
	public int getType() {
		return _socialActivityGroup.getType();
	}

	/**
	* Sets the type of this social activity group.
	*
	* @param type the type of this social activity group
	*/
	public void setType(int type) {
		_socialActivityGroup.setType(type);
	}

	public boolean isNew() {
		return _socialActivityGroup.isNew();
	}

	public void setNew(boolean n) {
		_socialActivityGroup.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialActivityGroup.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialActivityGroup.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialActivityGroup.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialActivityGroup.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_socialActivityGroup.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialActivityGroup.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialActivityGroup.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SocialActivityGroupWrapper((SocialActivityGroup)_socialActivityGroup.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialActivityGroup socialActivityGroup) {
		return _socialActivityGroup.compareTo(socialActivityGroup);
	}

	@Override
	public int hashCode() {
		return _socialActivityGroup.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.social.model.SocialActivityGroup> toCacheModel() {
		return _socialActivityGroup.toCacheModel();
	}

	public com.liferay.portlet.social.model.SocialActivityGroup toEscapedModel() {
		return new SocialActivityGroupWrapper(_socialActivityGroup.toEscapedModel());
	}

	public com.liferay.portlet.social.model.SocialActivityGroup toUnescapedModel() {
		return new SocialActivityGroupWrapper(_socialActivityGroup.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _socialActivityGroup.toString();
	}

	public java.lang.String toXmlString() {
		return _socialActivityGroup.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityGroup.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public SocialActivityGroup getWrappedSocialActivityGroup() {
		return _socialActivityGroup;
	}

	public SocialActivityGroup getWrappedModel() {
		return _socialActivityGroup;
	}

	public void resetOriginalValues() {
		_socialActivityGroup.resetOriginalValues();
	}

	private SocialActivityGroup _socialActivityGroup;
}