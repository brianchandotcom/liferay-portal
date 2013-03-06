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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.ModelWrapper;

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

		attributes.put("discussionId", getDiscussionId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("threadId", getThreadId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long discussionId = (Long)attributes.get("discussionId");

		if (discussionId != null) {
			setDiscussionId(discussionId);
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