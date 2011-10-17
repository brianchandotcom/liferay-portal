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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThread
 * @generated
 */
public class MBThreadWrapper implements MBThread, ModelWrapper<MBThread> {
	public MBThreadWrapper(MBThread mbThread) {
		_mbThread = mbThread;
	}

	public Class<?> getModelClass() {
		return MBThread.class;
	}

	public String getModelClassName() {
		return MBThread.class.getName();
	}

	/**
	* Returns the primary key of this message boards thread.
	*
	* @return the primary key of this message boards thread
	*/
	public long getPrimaryKey() {
		return _mbThread.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards thread.
	*
	* @param primaryKey the primary key of this message boards thread
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbThread.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the thread ID of this message boards thread.
	*
	* @return the thread ID of this message boards thread
	*/
	public long getThreadId() {
		return _mbThread.getThreadId();
	}

	/**
	* Sets the thread ID of this message boards thread.
	*
	* @param threadId the thread ID of this message boards thread
	*/
	public void setThreadId(long threadId) {
		_mbThread.setThreadId(threadId);
	}

	/**
	* Returns the group ID of this message boards thread.
	*
	* @return the group ID of this message boards thread
	*/
	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	/**
	* Sets the group ID of this message boards thread.
	*
	* @param groupId the group ID of this message boards thread
	*/
	public void setGroupId(long groupId) {
		_mbThread.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message boards thread.
	*
	* @return the company ID of this message boards thread
	*/
	public long getCompanyId() {
		return _mbThread.getCompanyId();
	}

	/**
	* Sets the company ID of this message boards thread.
	*
	* @param companyId the company ID of this message boards thread
	*/
	public void setCompanyId(long companyId) {
		_mbThread.setCompanyId(companyId);
	}

	/**
	* Returns the category ID of this message boards thread.
	*
	* @return the category ID of this message boards thread
	*/
	public long getCategoryId() {
		return _mbThread.getCategoryId();
	}

	/**
	* Sets the category ID of this message boards thread.
	*
	* @param categoryId the category ID of this message boards thread
	*/
	public void setCategoryId(long categoryId) {
		_mbThread.setCategoryId(categoryId);
	}

	/**
	* Returns the root message ID of this message boards thread.
	*
	* @return the root message ID of this message boards thread
	*/
	public long getRootMessageId() {
		return _mbThread.getRootMessageId();
	}

	/**
	* Sets the root message ID of this message boards thread.
	*
	* @param rootMessageId the root message ID of this message boards thread
	*/
	public void setRootMessageId(long rootMessageId) {
		_mbThread.setRootMessageId(rootMessageId);
	}

	/**
	* Returns the root message user ID of this message boards thread.
	*
	* @return the root message user ID of this message boards thread
	*/
	public long getRootMessageUserId() {
		return _mbThread.getRootMessageUserId();
	}

	/**
	* Sets the root message user ID of this message boards thread.
	*
	* @param rootMessageUserId the root message user ID of this message boards thread
	*/
	public void setRootMessageUserId(long rootMessageUserId) {
		_mbThread.setRootMessageUserId(rootMessageUserId);
	}

	/**
	* Returns the root message user uuid of this message boards thread.
	*
	* @return the root message user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getRootMessageUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getRootMessageUserUuid();
	}

	/**
	* Sets the root message user uuid of this message boards thread.
	*
	* @param rootMessageUserUuid the root message user uuid of this message boards thread
	*/
	public void setRootMessageUserUuid(java.lang.String rootMessageUserUuid) {
		_mbThread.setRootMessageUserUuid(rootMessageUserUuid);
	}

	/**
	* Returns the message count of this message boards thread.
	*
	* @return the message count of this message boards thread
	*/
	public int getMessageCount() {
		return _mbThread.getMessageCount();
	}

	/**
	* Sets the message count of this message boards thread.
	*
	* @param messageCount the message count of this message boards thread
	*/
	public void setMessageCount(int messageCount) {
		_mbThread.setMessageCount(messageCount);
	}

	/**
	* Returns the view count of this message boards thread.
	*
	* @return the view count of this message boards thread
	*/
	public int getViewCount() {
		return _mbThread.getViewCount();
	}

	/**
	* Sets the view count of this message boards thread.
	*
	* @param viewCount the view count of this message boards thread
	*/
	public void setViewCount(int viewCount) {
		_mbThread.setViewCount(viewCount);
	}

	/**
	* Returns the last post by user ID of this message boards thread.
	*
	* @return the last post by user ID of this message boards thread
	*/
	public long getLastPostByUserId() {
		return _mbThread.getLastPostByUserId();
	}

	/**
	* Sets the last post by user ID of this message boards thread.
	*
	* @param lastPostByUserId the last post by user ID of this message boards thread
	*/
	public void setLastPostByUserId(long lastPostByUserId) {
		_mbThread.setLastPostByUserId(lastPostByUserId);
	}

	/**
	* Returns the last post by user uuid of this message boards thread.
	*
	* @return the last post by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getLastPostByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getLastPostByUserUuid();
	}

	/**
	* Sets the last post by user uuid of this message boards thread.
	*
	* @param lastPostByUserUuid the last post by user uuid of this message boards thread
	*/
	public void setLastPostByUserUuid(java.lang.String lastPostByUserUuid) {
		_mbThread.setLastPostByUserUuid(lastPostByUserUuid);
	}

	/**
	* Returns the last post date of this message boards thread.
	*
	* @return the last post date of this message boards thread
	*/
	public java.util.Date getLastPostDate() {
		return _mbThread.getLastPostDate();
	}

	/**
	* Sets the last post date of this message boards thread.
	*
	* @param lastPostDate the last post date of this message boards thread
	*/
	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbThread.setLastPostDate(lastPostDate);
	}

	/**
	* Returns the priority of this message boards thread.
	*
	* @return the priority of this message boards thread
	*/
	public double getPriority() {
		return _mbThread.getPriority();
	}

	/**
	* Sets the priority of this message boards thread.
	*
	* @param priority the priority of this message boards thread
	*/
	public void setPriority(double priority) {
		_mbThread.setPriority(priority);
	}

	/**
	* Returns the question of this message boards thread.
	*
	* @return the question of this message boards thread
	*/
	public boolean getQuestion() {
		return _mbThread.getQuestion();
	}

	/**
	* Returns <code>true</code> if this message boards thread is question.
	*
	* @return <code>true</code> if this message boards thread is question; <code>false</code> otherwise
	*/
	public boolean isQuestion() {
		return _mbThread.isQuestion();
	}

	/**
	* Sets whether this message boards thread is question.
	*
	* @param question the question of this message boards thread
	*/
	public void setQuestion(boolean question) {
		_mbThread.setQuestion(question);
	}

	/**
	* Returns the status of this message boards thread.
	*
	* @return the status of this message boards thread
	*/
	public int getStatus() {
		return _mbThread.getStatus();
	}

	/**
	* Sets the status of this message boards thread.
	*
	* @param status the status of this message boards thread
	*/
	public void setStatus(int status) {
		_mbThread.setStatus(status);
	}

	/**
	* Returns the status by user ID of this message boards thread.
	*
	* @return the status by user ID of this message boards thread
	*/
	public long getStatusByUserId() {
		return _mbThread.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this message boards thread.
	*
	* @param statusByUserId the status by user ID of this message boards thread
	*/
	public void setStatusByUserId(long statusByUserId) {
		_mbThread.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this message boards thread.
	*
	* @return the status by user uuid of this message boards thread
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThread.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this message boards thread.
	*
	* @param statusByUserUuid the status by user uuid of this message boards thread
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbThread.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this message boards thread.
	*
	* @return the status by user name of this message boards thread
	*/
	public java.lang.String getStatusByUserName() {
		return _mbThread.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this message boards thread.
	*
	* @param statusByUserName the status by user name of this message boards thread
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbThread.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this message boards thread.
	*
	* @return the status date of this message boards thread
	*/
	public java.util.Date getStatusDate() {
		return _mbThread.getStatusDate();
	}

	/**
	* Sets the status date of this message boards thread.
	*
	* @param statusDate the status date of this message boards thread
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_mbThread.setStatusDate(statusDate);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _mbThread.getApproved();
	}

	/**
	* Returns <code>true</code> if this message boards thread is approved.
	*
	* @return <code>true</code> if this message boards thread is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _mbThread.isApproved();
	}

	/**
	* Returns <code>true</code> if this message boards thread is a draft.
	*
	* @return <code>true</code> if this message boards thread is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _mbThread.isDraft();
	}

	/**
	* Returns <code>true</code> if this message boards thread is expired.
	*
	* @return <code>true</code> if this message boards thread is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _mbThread.isExpired();
	}

	/**
	* Returns <code>true</code> if this message boards thread is pending.
	*
	* @return <code>true</code> if this message boards thread is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _mbThread.isPending();
	}

	public boolean isNew() {
		return _mbThread.isNew();
	}

	public void setNew(boolean n) {
		_mbThread.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbThread.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbThread.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbThread.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThread.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbThread.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThread.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThread.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBThreadWrapper((MBThread)_mbThread.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return _mbThread.compareTo(mbThread);
	}

	@Override
	public int hashCode() {
		return _mbThread.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBThread> toCacheModel() {
		return _mbThread.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBThread toEscapedModel() {
		return new MBThreadWrapper(_mbThread.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbThread.toString();
	}

	public java.lang.String toXmlString() {
		return _mbThread.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbThread.persist();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbThread.getAttachmentsDir();
	}

	public com.liferay.portal.model.Lock getLock() {
		return _mbThread.getLock();
	}

	public boolean hasLock(long userId) {
		return _mbThread.hasLock(userId);
	}

	public boolean isLocked() {
		return _mbThread.isLocked();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MBThread getWrappedMBThread() {
		return _mbThread;
	}

	public MBThread getWrappedModel() {
		return _mbThread;
	}

	public void resetOriginalValues() {
		_mbThread.resetOriginalValues();
	}

	private MBThread _mbThread;
}