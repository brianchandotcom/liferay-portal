/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBDiscussion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBDiscussion
 * @generated
 */
public class MBDiscussionWrapper implements MBDiscussion,
	ModelWrapper<MBDiscussion> {
	public MBDiscussionWrapper(MBDiscussion mbDiscussion) {
		_mbDiscussion = mbDiscussion;
	}

	public Class<?> getModelClass() {
		return MBDiscussion.class;
	}

	public String getModelClassName() {
		return MBDiscussion.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("discussionId", getDiscussionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("threadId", getThreadId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long discussionId = (Long)attributes.get("discussionId");

		if (discussionId != null) {
			setDiscussionId(discussionId);
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

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

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

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
		}
	}

	/**
	* Returns the primary key of this message boards discussion.
	*
	* @return the primary key of this message boards discussion
	*/
	public long getPrimaryKey() {
		return _mbDiscussion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards discussion.
	*
	* @param primaryKey the primary key of this message boards discussion
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbDiscussion.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message boards discussion.
	*
	* @return the uuid of this message boards discussion
	*/
	public java.lang.String getUuid() {
		return _mbDiscussion.getUuid();
	}

	/**
	* Sets the uuid of this message boards discussion.
	*
	* @param uuid the uuid of this message boards discussion
	*/
	public void setUuid(java.lang.String uuid) {
		_mbDiscussion.setUuid(uuid);
	}

	/**
	* Returns the discussion ID of this message boards discussion.
	*
	* @return the discussion ID of this message boards discussion
	*/
	public long getDiscussionId() {
		return _mbDiscussion.getDiscussionId();
	}

	/**
	* Sets the discussion ID of this message boards discussion.
	*
	* @param discussionId the discussion ID of this message boards discussion
	*/
	public void setDiscussionId(long discussionId) {
		_mbDiscussion.setDiscussionId(discussionId);
	}

	/**
	* Returns the group ID of this message boards discussion.
	*
	* @return the group ID of this message boards discussion
	*/
	public long getGroupId() {
		return _mbDiscussion.getGroupId();
	}

	/**
	* Sets the group ID of this message boards discussion.
	*
	* @param groupId the group ID of this message boards discussion
	*/
	public void setGroupId(long groupId) {
		_mbDiscussion.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards discussion.
	*
	* @return the company ID of this message boards discussion
	*/
	public long getCompanyId() {
		return _mbDiscussion.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards discussion.
	*
	* @param companyId the company ID of this message boards discussion
	*/
	public void setCompanyId(long companyId) {
		_mbDiscussion.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message boards discussion.
	*
	* @return the user ID of this message boards discussion
	*/
	public long getUserId() {
		return _mbDiscussion.getUserId();
	}

	/**
	* Sets the user ID of this message boards discussion.
	*
	* @param userId the user ID of this message boards discussion
	*/
	public void setUserId(long userId) {
		_mbDiscussion.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards discussion.
	*
	* @return the user uuid of this message boards discussion
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbDiscussion.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards discussion.
	*
	* @param userUuid the user uuid of this message boards discussion
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbDiscussion.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message boards discussion.
	*
	* @return the user name of this message boards discussion
	*/
	public java.lang.String getUserName() {
		return _mbDiscussion.getUserName();
	}

	/**
	* Sets the user name of this message boards discussion.
	*
	* @param userName the user name of this message boards discussion
	*/
	public void setUserName(java.lang.String userName) {
		_mbDiscussion.setUserName(userName);
	}

	/**
	* Returns the create date of this message boards discussion.
	*
	* @return the create date of this message boards discussion
	*/
	public java.util.Date getCreateDate() {
		return _mbDiscussion.getCreateDate();
	}

	/**
	* Sets the create date of this message boards discussion.
	*
	* @param createDate the create date of this message boards discussion
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mbDiscussion.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards discussion.
	*
	* @return the modified date of this message boards discussion
	*/
	public java.util.Date getModifiedDate() {
		return _mbDiscussion.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards discussion.
	*
	* @param modifiedDate the modified date of this message boards discussion
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbDiscussion.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this message boards discussion.
	*
	* @return the fully qualified class name of this message boards discussion
	*/
	public java.lang.String getClassName() {
		return _mbDiscussion.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_mbDiscussion.setClassName(className);
	}

	/**
	* Returns the class name ID of this message boards discussion.
	*
	* @return the class name ID of this message boards discussion
	*/
	public long getClassNameId() {
		return _mbDiscussion.getClassNameId();
	}

	/**
	* Sets the class name ID of this message boards discussion.
	*
	* @param classNameId the class name ID of this message boards discussion
	*/
	public void setClassNameId(long classNameId) {
		_mbDiscussion.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this message boards discussion.
	*
	* @return the class p k of this message boards discussion
	*/
	public long getClassPK() {
		return _mbDiscussion.getClassPK();
	}

	/**
	* Sets the class p k of this message boards discussion.
	*
	* @param classPK the class p k of this message boards discussion
	*/
	public void setClassPK(long classPK) {
		_mbDiscussion.setClassPK(classPK);
	}

	/**
	* Returns the thread ID of this message boards discussion.
	*
	* @return the thread ID of this message boards discussion
	*/
	public long getThreadId() {
		return _mbDiscussion.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards discussion.
	*
	* @param threadId the thread ID of this message boards discussion
	*/
	public void setThreadId(long threadId) {
		_mbDiscussion.setThreadId(threadId);
	}

	public boolean isNew() {
		return _mbDiscussion.isNew();
	}

	public void setNew(boolean n) {
		_mbDiscussion.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbDiscussion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbDiscussion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbDiscussion.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbDiscussion.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbDiscussion.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbDiscussion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbDiscussion.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbDiscussion.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbDiscussion.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBDiscussionWrapper((MBDiscussion)_mbDiscussion.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion) {
		return _mbDiscussion.compareTo(mbDiscussion);
	}

	@Override
	public int hashCode() {
		return _mbDiscussion.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBDiscussion> toCacheModel() {
		return _mbDiscussion.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion toEscapedModel() {
		return new MBDiscussionWrapper(_mbDiscussion.toEscapedModel());
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion toUnescapedModel() {
		return new MBDiscussionWrapper(_mbDiscussion.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbDiscussion.toString();
	}

	public java.lang.String toXmlString() {
		return _mbDiscussion.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbDiscussion.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MBDiscussion getWrappedMBDiscussion() {
		return _mbDiscussion;
	}

	public MBDiscussion getWrappedModel() {
		return _mbDiscussion;
	}

	public void resetOriginalValues() {
		_mbDiscussion.resetOriginalValues();
	}

	private MBDiscussion _mbDiscussion;
}