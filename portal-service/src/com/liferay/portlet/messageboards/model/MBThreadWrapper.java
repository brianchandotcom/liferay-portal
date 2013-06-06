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
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThread
 * @generated
 */
public class MBThreadWrapper implements MBThread, ModelWrapper<MBThread> {
	public MBThreadWrapper(MBThread mbThread) {
		_mbThread = mbThread;
	}

	@Override
	public Class<?> getModelClass() {
		return MBThread.class;
	}

	@Override
	public String getModelClassName() {
		return MBThread.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("threadId", getThreadId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("categoryId", getCategoryId());
		attributes.put("rootMessageId", getRootMessageId());
		attributes.put("rootMessageUserId", getRootMessageUserId());
		attributes.put("messageCount", getMessageCount());
		attributes.put("viewCount", getViewCount());
		attributes.put("lastPostByUserId", getLastPostByUserId());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("priority", getPriority());
		attributes.put("question", getQuestion());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
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

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		Long rootMessageId = (Long)attributes.get("rootMessageId");

		if (rootMessageId != null) {
			setRootMessageId(rootMessageId);
		}

		Long rootMessageUserId = (Long)attributes.get("rootMessageUserId");

		if (rootMessageUserId != null) {
			setRootMessageUserId(rootMessageUserId);
		}

		Integer messageCount = (Integer)attributes.get("messageCount");

		if (messageCount != null) {
			setMessageCount(messageCount);
		}

		Integer viewCount = (Integer)attributes.get("viewCount");

		if (viewCount != null) {
			setViewCount(viewCount);
		}

		Long lastPostByUserId = (Long)attributes.get("lastPostByUserId");

		if (lastPostByUserId != null) {
			setLastPostByUserId(lastPostByUserId);
		}

		Date lastPostDate = (Date)attributes.get("lastPostDate");

		if (lastPostDate != null) {
			setLastPostDate(lastPostDate);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean question = (Boolean)attributes.get("question");

		if (question != null) {
			setQuestion(question);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	/**
	* Returns the primary key of this message boards thread.
	*
	* @return the primary key of this message boards thread
	*/
	@Override
	public long getPrimaryKey() {
		return _mbThread.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards thread.
	*
	* @param primaryKey the primary key of this message boards thread
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbThread.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message boards thread.
	*
	* @return the uuid of this message boards thread
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbThread.getUuid();
	}

	/**
	* Sets the uuid of this message boards thread.
	*
	* @param uuid the uuid of this message boards thread
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbThread.setUuid(uuid);
	}

	/**
	* Returns the thread ID of this message boards thread.
	*
	* @return the thread ID of this message boards thread
	*/
	@Override
	public long getThreadId() {
		return _mbThread.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards thread.
	*
	* @param threadId the thread ID of this message boards thread
	*/
	@Override
	public void setThreadId(long threadId) {
		_mbThread.setThreadId(threadId);
	}

	/**
	* Returns the group ID of this message boards thread.
	*
	* @return the group ID of this message boards thread
	*/
	@Override
	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	/**
	* Sets the group ID of this message boards thread.
	*
	* @param groupId the group ID of this message boards thread
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbThread.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards thread.
	*
	* @return the company ID of this message boards thread
	*/
	@Override
	public long getCompanyId() {
		return _mbThread.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards thread.
	*
	* @param companyId the company ID of this message boards thread
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbThread.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message boards thread.
	*
	* @return the user ID of this message boards thread
	*/
	@Override
	public long getUserId() {
		return _mbThread.getUserId();
	}

	/**
	* Sets the user ID of this message boards thread.
	*
	* @param userId the user ID of this message boards thread
	*/
	@Override
	public void setUserId(long userId) {
		_mbThread.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message boards thread.
	*
	* @return the user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getUserUuid();
	}

	/**
	* Sets the user uuid of this message boards thread.
	*
	* @param userUuid the user uuid of this message boards thread
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbThread.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message boards thread.
	*
	* @return the user name of this message boards thread
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbThread.getUserName();
	}

	/**
	* Sets the user name of this message boards thread.
	*
	* @param userName the user name of this message boards thread
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbThread.setUserName(userName);
	}

	/**
	* Returns the create date of this message boards thread.
	*
	* @return the create date of this message boards thread
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _mbThread.getCreateDate();
	}

	/**
	* Sets the create date of this message boards thread.
	*
	* @param createDate the create date of this message boards thread
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_mbThread.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message boards thread.
	*
	* @return the modified date of this message boards thread
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _mbThread.getModifiedDate();
	}

	/**
	* Sets the modified date of this message boards thread.
	*
	* @param modifiedDate the modified date of this message boards thread
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbThread.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the category ID of this message boards thread.
	*
	* @return the category ID of this message boards thread
	*/
	@Override
	public long getCategoryId() {
		return _mbThread.getCategoryId();
	}

	/**
	* Sets the category ID of this message boards thread.
	*
	* @param categoryId the category ID of this message boards thread
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_mbThread.setCategoryId(categoryId);
	}

	/**
	* Returns the root message ID of this message boards thread.
	*
	* @return the root message ID of this message boards thread
	*/
	@Override
	public long getRootMessageId() {
		return _mbThread.getRootMessageId();
	}

	/**
	* Sets the root message ID of this message boards thread.
	*
	* @param rootMessageId the root message ID of this message boards thread
	*/
	@Override
	public void setRootMessageId(long rootMessageId) {
		_mbThread.setRootMessageId(rootMessageId);
	}

	/**
	* Returns the root message user ID of this message boards thread.
	*
	* @return the root message user ID of this message boards thread
	*/
	@Override
	public long getRootMessageUserId() {
		return _mbThread.getRootMessageUserId();
	}

	/**
	* Sets the root message user ID of this message boards thread.
	*
	* @param rootMessageUserId the root message user ID of this message boards thread
	*/
	@Override
	public void setRootMessageUserId(long rootMessageUserId) {
		_mbThread.setRootMessageUserId(rootMessageUserId);
	}

	/**
	* Returns the root message user uuid of this message boards thread.
	*
	* @return the root message user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getRootMessageUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getRootMessageUserUuid();
	}

	/**
	* Sets the root message user uuid of this message boards thread.
	*
	* @param rootMessageUserUuid the root message user uuid of this message boards thread
	*/
	@Override
	public void setRootMessageUserUuid(java.lang.String rootMessageUserUuid) {
		_mbThread.setRootMessageUserUuid(rootMessageUserUuid);
	}

	/**
	* Returns the message count of this message boards thread.
	*
	* @return the message count of this message boards thread
	*/
	@Override
	public int getMessageCount() {
		return _mbThread.getMessageCount();
	}

	/**
	* Sets the message count of this message boards thread.
	*
	* @param messageCount the message count of this message boards thread
	*/
	@Override
	public void setMessageCount(int messageCount) {
		_mbThread.setMessageCount(messageCount);
	}

	/**
	* Returns the view count of this message boards thread.
	*
	* @return the view count of this message boards thread
	*/
	@Override
	public int getViewCount() {
		return _mbThread.getViewCount();
	}

	/**
	* Sets the view count of this message boards thread.
	*
	* @param viewCount the view count of this message boards thread
	*/
	@Override
	public void setViewCount(int viewCount) {
		_mbThread.setViewCount(viewCount);
	}

	/**
	* Returns the last post by user ID of this message boards thread.
	*
	* @return the last post by user ID of this message boards thread
	*/
	@Override
	public long getLastPostByUserId() {
		return _mbThread.getLastPostByUserId();
	}

	/**
	* Sets the last post by user ID of this message boards thread.
	*
	* @param lastPostByUserId the last post by user ID of this message boards thread
	*/
	@Override
	public void setLastPostByUserId(long lastPostByUserId) {
		_mbThread.setLastPostByUserId(lastPostByUserId);
	}

	/**
	* Returns the last post by user uuid of this message boards thread.
	*
	* @return the last post by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getLastPostByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getLastPostByUserUuid();
	}

	/**
	* Sets the last post by user uuid of this message boards thread.
	*
	* @param lastPostByUserUuid the last post by user uuid of this message boards thread
	*/
	@Override
	public void setLastPostByUserUuid(java.lang.String lastPostByUserUuid) {
		_mbThread.setLastPostByUserUuid(lastPostByUserUuid);
	}

	/**
	* Returns the last post date of this message boards thread.
	*
	* @return the last post date of this message boards thread
	*/
	@Override
	public java.util.Date getLastPostDate() {
		return _mbThread.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards thread.
	*
	* @param lastPostDate the last post date of this message boards thread
	*/
	@Override
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbThread.setLastPostDate(lastPostDate);
	}

	/**
	* Returns the priority of this message boards thread.
	*
	* @return the priority of this message boards thread
	*/
	@Override
	public double getPriority() {
		return _mbThread.getPriority();
	}

	/**
	* Sets the priority of this message boards thread.
	*
	* @param priority the priority of this message boards thread
	*/
	@Override
	public void setPriority(double priority) {
		_mbThread.setPriority(priority);
	}

	/**
	* Returns the question of this message boards thread.
	*
	* @return the question of this message boards thread
	*/
	@Override
	public boolean getQuestion() {
		return _mbThread.getQuestion();
	}

	/**
	* Returns <code>true</code> if this message boards thread is question.
	*
	* @return <code>true</code> if this message boards thread is question; <code>false</code> otherwise
	*/
	@Override
	public boolean isQuestion() {
		return _mbThread.isQuestion();
	}

	/**
	* Sets whether this message boards thread is question.
	*
	* @param question the question of this message boards thread
	*/
	@Override
	public void setQuestion(boolean question) {
		_mbThread.setQuestion(question);
	}

	/**
	* Returns the status of this message boards thread.
	*
	* @return the status of this message boards thread
	*/
	@Override
	public int getStatus() {
		return _mbThread.getStatus();
	}

	/**
	* Sets the status of this message boards thread.
	*
	* @param status the status of this message boards thread
	*/
	@Override
	public void setStatus(int status) {
		_mbThread.setStatus(status);
	}

	/**
	* Returns the status by user ID of this message boards thread.
	*
	* @return the status by user ID of this message boards thread
	*/
	@Override
	public long getStatusByUserId() {
		return _mbThread.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this message boards thread.
	*
	* @param statusByUserId the status by user ID of this message boards thread
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_mbThread.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this message boards thread.
	*
	* @return the status by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this message boards thread.
	*
	* @param statusByUserUuid the status by user uuid of this message boards thread
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbThread.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this message boards thread.
	*
	* @return the status by user name of this message boards thread
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _mbThread.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this message boards thread.
	*
	* @param statusByUserName the status by user name of this message boards thread
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbThread.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this message boards thread.
	*
	* @return the status date of this message boards thread
	*/
	@Override
	public java.util.Date getStatusDate() {
		return _mbThread.getStatusDate();
	}

	/**
	* Sets the status date of this message boards thread.
	*
	* @param statusDate the status date of this message boards thread
	*/
	@Override
	public void setStatusDate(java.util.Date statusDate) {
		_mbThread.setStatusDate(statusDate);
	}

	/**
	* @deprecated As of 6.1.0, replaced by {@link #isApproved()}
	*/
	@Override
	public boolean getApproved() {
		return _mbThread.getApproved();
	}

	/**
	* Returns <code>true</code> if this message boards thread is approved.
	*
	* @return <code>true</code> if this message boards thread is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _mbThread.isApproved();
	}

	/**
	* Returns <code>true</code> if this message boards thread is denied.
	*
	* @return <code>true</code> if this message boards thread is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _mbThread.isDenied();
	}

	/**
	* Returns <code>true</code> if this message boards thread is a draft.
	*
	* @return <code>true</code> if this message boards thread is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _mbThread.isDraft();
	}

	/**
	* Returns <code>true</code> if this message boards thread is expired.
	*
	* @return <code>true</code> if this message boards thread is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _mbThread.isExpired();
	}

	/**
	* Returns <code>true</code> if this message boards thread is inactive.
	*
	* @return <code>true</code> if this message boards thread is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _mbThread.isInactive();
	}

	/**
	* Returns <code>true</code> if this message boards thread is incomplete.
	*
	* @return <code>true</code> if this message boards thread is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _mbThread.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this message boards thread is in the Recycle Bin.
	*
	* @return <code>true</code> if this message boards thread is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _mbThread.isInTrash();
	}

	/**
	* Returns <code>true</code> if this message boards thread is pending.
	*
	* @return <code>true</code> if this message boards thread is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _mbThread.isPending();
	}

	/**
	* Returns <code>true</code> if this message boards thread is scheduled.
	*
	* @return <code>true</code> if this message boards thread is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _mbThread.isScheduled();
	}

	/**
	* Returns the container model ID of this message boards thread.
	*
	* @return the container model ID of this message boards thread
	*/
	@Override
	public long getContainerModelId() {
		return _mbThread.getContainerModelId();
	}

	/**
	* Sets the container model ID of this message boards thread.
	*
	* @param container model ID of this message boards thread
	*/
	@Override
	public void setContainerModelId(long containerModelId) {
		_mbThread.setContainerModelId(containerModelId);
	}

	/**
	* Returns the container name of this message boards thread.
	*
	* @return the container name of this message boards thread
	*/
	@Override
	public java.lang.String getContainerModelName() {
		return _mbThread.getContainerModelName();
	}

	/**
	* Returns the parent container model ID of this message boards thread.
	*
	* @return the parent container model ID of this message boards thread
	*/
	@Override
	public long getParentContainerModelId() {
		return _mbThread.getParentContainerModelId();
	}

	/**
	* Sets the parent container model ID of this message boards thread.
	*
	* @param parent container model ID of this message boards thread
	*/
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		_mbThread.setParentContainerModelId(parentContainerModelId);
	}

	@Override
	public boolean isNew() {
		return _mbThread.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_mbThread.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _mbThread.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbThread.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _mbThread.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThread.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbThread.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThread.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbThread.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_mbThread.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThread.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBThreadWrapper((MBThread)_mbThread.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return _mbThread.compareTo(mbThread);
	}

	@Override
	public int hashCode() {
		return _mbThread.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBThread> toCacheModel() {
		return _mbThread.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThread toEscapedModel() {
		return new MBThreadWrapper(_mbThread.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThread toUnescapedModel() {
		return new MBThreadWrapper(_mbThread.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbThread.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbThread.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbThread.persist();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.addAttachmentsFolder();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getAttachmentsFolderId();
	}

	@Override
	public com.liferay.portal.model.Lock getLock() {
		return _mbThread.getLock();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getTrashContainer() {
		return _mbThread.getTrashContainer();
	}

	@Override
	public boolean hasLock(long userId) {
		return _mbThread.hasLock(userId);
	}

	@Override
	public boolean isInTrashContainer() {
		return _mbThread.isInTrashContainer();
	}

	@Override
	public boolean isLocked() {
		return _mbThread.isLocked();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MBThread getWrappedMBThread() {
		return _mbThread;
	}

	@Override
	public MBThread getWrappedModel() {
		return _mbThread;
	}

	@Override
	public void resetOriginalValues() {
		_mbThread.resetOriginalValues();
	}

	private MBThread _mbThread;
}