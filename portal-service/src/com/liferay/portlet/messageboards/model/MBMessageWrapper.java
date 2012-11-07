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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBMessage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessage
 * @generated
 */
public class MBMessageWrapper implements MBMessage, ModelWrapper<MBMessage> {
	public MBMessageWrapper(MBMessage mbMessage) {
		_mbMessage = mbMessage;
	}

	public Class<?> getModelClass() {
		return MBMessage.class;
	}

	public String getModelClassName() {
		return MBMessage.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("messageId", getMessageId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("categoryId", getCategoryId());
		attributes.put("threadId", getThreadId());
		attributes.put("rootMessageId", getRootMessageId());
		attributes.put("parentMessageId", getParentMessageId());
		attributes.put("subject", getSubject());
		attributes.put("body", getBody());
		attributes.put("format", getFormat());
		attributes.put("attachments", getAttachments());
		attributes.put("anonymous", getAnonymous());
		attributes.put("priority", getPriority());
		attributes.put("allowPingbacks", getAllowPingbacks());
		attributes.put("answer", getAnswer());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long messageId = (Long)attributes.get("messageId");

		if (messageId != null) {
			setMessageId(messageId);
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

		Long categoryId = (Long)attributes.get("categoryId");

		if (categoryId != null) {
			setCategoryId(categoryId);
		}

		Long threadId = (Long)attributes.get("threadId");

		if (threadId != null) {
			setThreadId(threadId);
		}

		Long rootMessageId = (Long)attributes.get("rootMessageId");

		if (rootMessageId != null) {
			setRootMessageId(rootMessageId);
		}

		Long parentMessageId = (Long)attributes.get("parentMessageId");

		if (parentMessageId != null) {
			setParentMessageId(parentMessageId);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}

		String format = (String)attributes.get("format");

		if (format != null) {
			setFormat(format);
		}

		Boolean attachments = (Boolean)attributes.get("attachments");

		if (attachments != null) {
			setAttachments(attachments);
		}

		Boolean anonymous = (Boolean)attributes.get("anonymous");

		if (anonymous != null) {
			setAnonymous(anonymous);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Boolean allowPingbacks = (Boolean)attributes.get("allowPingbacks");

		if (allowPingbacks != null) {
			setAllowPingbacks(allowPingbacks);
		}

		Boolean answer = (Boolean)attributes.get("answer");

		if (answer != null) {
			setAnswer(answer);
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
	* Returns the primary key of this message-boards message.
	*
	* @return the primary key of this message-boards message
	*/
	public long getPrimaryKey() {
		return _mbMessage.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message-boards message.
	*
	* @param primaryKey the primary key of this message-boards message
	*/
	public void setPrimaryKey(long primaryKey) {
		_mbMessage.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this message-boards message.
	*
	* @return the uuid of this message-boards message
	*/
	public java.lang.String getUuid() {
		return _mbMessage.getUuid();
	}

	/**
	* Sets the uuid of this message-boards message.
	*
	* @param uuid the uuid of this message-boards message
	*/
	public void setUuid(java.lang.String uuid) {
		_mbMessage.setUuid(uuid);
	}

	/**
	* Returns the message ID of this message-boards message.
	*
	* @return the message ID of this message-boards message
	*/
	public long getMessageId() {
		return _mbMessage.getMessageId();
	}

	/**
	* Sets the message ID of this message-boards message.
	*
	* @param messageId the message ID of this message-boards message
	*/
	public void setMessageId(long messageId) {
		_mbMessage.setMessageId(messageId);
	}

	/**
	* Returns the group ID of this message-boards message.
	*
	* @return the group ID of this message-boards message
	*/
	public long getGroupId() {
		return _mbMessage.getGroupId();
	}

	/**
	* Sets the group ID of this message-boards message.
	*
	* @param groupId the group ID of this message-boards message
	*/
	public void setGroupId(long groupId) {
		_mbMessage.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this message-boards message.
	*
	* @return the company ID of this message-boards message
	*/
	public long getCompanyId() {
		return _mbMessage.getCompanyId();
	}

	/**
	* Sets the company ID of this message-boards message.
	*
	* @param companyId the company ID of this message-boards message
	*/
	public void setCompanyId(long companyId) {
		_mbMessage.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this message-boards message.
	*
	* @return the user ID of this message-boards message
	*/
	public long getUserId() {
		return _mbMessage.getUserId();
	}

	/**
	* Sets the user ID of this message-boards message.
	*
	* @param userId the user ID of this message-boards message
	*/
	public void setUserId(long userId) {
		_mbMessage.setUserId(userId);
	}

	/**
	* Returns the user uuid of this message-boards message.
	*
	* @return the user uuid of this message-boards message
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getUserUuid();
	}

	/**
	* Sets the user uuid of this message-boards message.
	*
	* @param userUuid the user uuid of this message-boards message
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_mbMessage.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this message-boards message.
	*
	* @return the user name of this message-boards message
	*/
	public java.lang.String getUserName() {
		return _mbMessage.getUserName();
	}

	/**
	* Sets the user name of this message-boards message.
	*
	* @param userName the user name of this message-boards message
	*/
	public void setUserName(java.lang.String userName) {
		_mbMessage.setUserName(userName);
	}

	/**
	* Returns the create date of this message-boards message.
	*
	* @return the create date of this message-boards message
	*/
	public java.util.Date getCreateDate() {
		return _mbMessage.getCreateDate();
	}

	/**
	* Sets the create date of this message-boards message.
	*
	* @param createDate the create date of this message-boards message
	*/
	public void setCreateDate(java.util.Date createDate) {
		_mbMessage.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this message-boards message.
	*
	* @return the modified date of this message-boards message
	*/
	public java.util.Date getModifiedDate() {
		return _mbMessage.getModifiedDate();
	}

	/**
	* Sets the modified date of this message-boards message.
	*
	* @param modifiedDate the modified date of this message-boards message
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbMessage.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the fully qualified class name of this message-boards message.
	*
	* @return the fully qualified class name of this message-boards message
	*/
	public java.lang.String getClassName() {
		return _mbMessage.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_mbMessage.setClassName(className);
	}

	/**
	* Returns the class name ID of this message-boards message.
	*
	* @return the class name ID of this message-boards message
	*/
	public long getClassNameId() {
		return _mbMessage.getClassNameId();
	}

	/**
	* Sets the class name ID of this message-boards message.
	*
	* @param classNameId the class name ID of this message-boards message
	*/
	public void setClassNameId(long classNameId) {
		_mbMessage.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this message-boards message.
	*
	* @return the class p k of this message-boards message
	*/
	public long getClassPK() {
		return _mbMessage.getClassPK();
	}

	/**
	* Sets the class p k of this message-boards message.
	*
	* @param classPK the class p k of this message-boards message
	*/
	public void setClassPK(long classPK) {
		_mbMessage.setClassPK(classPK);
	}

	/**
	* Returns the category ID of this message-boards message.
	*
	* @return the category ID of this message-boards message
	*/
	public long getCategoryId() {
		return _mbMessage.getCategoryId();
	}

	/**
	* Sets the category ID of this message-boards message.
	*
	* @param categoryId the category ID of this message-boards message
	*/
	public void setCategoryId(long categoryId) {
		_mbMessage.setCategoryId(categoryId);
	}

	/**
	* Returns the thread ID of this message-boards message.
	*
	* @return the thread ID of this message-boards message
	*/
	public long getThreadId() {
		return _mbMessage.getThreadId();
	}

	/**
	* Sets the thread ID of this message-boards message.
	*
	* @param threadId the thread ID of this message-boards message
	*/
	public void setThreadId(long threadId) {
		_mbMessage.setThreadId(threadId);
	}

	/**
	* Returns the root message ID of this message-boards message.
	*
	* @return the root message ID of this message-boards message
	*/
	public long getRootMessageId() {
		return _mbMessage.getRootMessageId();
	}

	/**
	* Sets the root message ID of this message-boards message.
	*
	* @param rootMessageId the root message ID of this message-boards message
	*/
	public void setRootMessageId(long rootMessageId) {
		_mbMessage.setRootMessageId(rootMessageId);
	}

	/**
	* Returns the parent message ID of this message-boards message.
	*
	* @return the parent message ID of this message-boards message
	*/
	public long getParentMessageId() {
		return _mbMessage.getParentMessageId();
	}

	/**
	* Sets the parent message ID of this message-boards message.
	*
	* @param parentMessageId the parent message ID of this message-boards message
	*/
	public void setParentMessageId(long parentMessageId) {
		_mbMessage.setParentMessageId(parentMessageId);
	}

	/**
	* Returns the subject of this message-boards message.
	*
	* @return the subject of this message-boards message
	*/
	public java.lang.String getSubject() {
		return _mbMessage.getSubject();
	}

	/**
	* Sets the subject of this message-boards message.
	*
	* @param subject the subject of this message-boards message
	*/
	public void setSubject(java.lang.String subject) {
		_mbMessage.setSubject(subject);
	}

	/**
	* Returns the body of this message-boards message.
	*
	* @return the body of this message-boards message
	*/
	public java.lang.String getBody() {
		return _mbMessage.getBody();
	}

	/**
	* Sets the body of this message-boards message.
	*
	* @param body the body of this message-boards message
	*/
	public void setBody(java.lang.String body) {
		_mbMessage.setBody(body);
	}

	/**
	* Returns the format of this message-boards message.
	*
	* @return the format of this message-boards message
	*/
	public java.lang.String getFormat() {
		return _mbMessage.getFormat();
	}

	/**
	* Sets the format of this message-boards message.
	*
	* @param format the format of this message-boards message
	*/
	public void setFormat(java.lang.String format) {
		_mbMessage.setFormat(format);
	}

	/**
	* Returns the attachments of this message-boards message.
	*
	* @return the attachments of this message-boards message
	*/
	public boolean getAttachments() {
		return _mbMessage.getAttachments();
	}

	/**
	* Returns <code>true</code> if this message-boards message is attachments.
	*
	* @return <code>true</code> if this message-boards message is attachments; <code>false</code> otherwise
	*/
	public boolean isAttachments() {
		return _mbMessage.isAttachments();
	}

	/**
	* Sets whether this message-boards message is attachments.
	*
	* @param attachments the attachments of this message-boards message
	*/
	public void setAttachments(boolean attachments) {
		_mbMessage.setAttachments(attachments);
	}

	/**
	* Returns the anonymous of this message-boards message.
	*
	* @return the anonymous of this message-boards message
	*/
	public boolean getAnonymous() {
		return _mbMessage.getAnonymous();
	}

	/**
	* Returns <code>true</code> if this message-boards message is anonymous.
	*
	* @return <code>true</code> if this message-boards message is anonymous; <code>false</code> otherwise
	*/
	public boolean isAnonymous() {
		return _mbMessage.isAnonymous();
	}

	/**
	* Sets whether this message-boards message is anonymous.
	*
	* @param anonymous the anonymous of this message-boards message
	*/
	public void setAnonymous(boolean anonymous) {
		_mbMessage.setAnonymous(anonymous);
	}

	/**
	* Returns the priority of this message-boards message.
	*
	* @return the priority of this message-boards message
	*/
	public double getPriority() {
		return _mbMessage.getPriority();
	}

	/**
	* Sets the priority of this message-boards message.
	*
	* @param priority the priority of this message-boards message
	*/
	public void setPriority(double priority) {
		_mbMessage.setPriority(priority);
	}

	/**
	* Returns the allow pingbacks of this message-boards message.
	*
	* @return the allow pingbacks of this message-boards message
	*/
	public boolean getAllowPingbacks() {
		return _mbMessage.getAllowPingbacks();
	}

	/**
	* Returns <code>true</code> if this message-boards message is allow pingbacks.
	*
	* @return <code>true</code> if this message-boards message is allow pingbacks; <code>false</code> otherwise
	*/
	public boolean isAllowPingbacks() {
		return _mbMessage.isAllowPingbacks();
	}

	/**
	* Sets whether this message-boards message is allow pingbacks.
	*
	* @param allowPingbacks the allow pingbacks of this message-boards message
	*/
	public void setAllowPingbacks(boolean allowPingbacks) {
		_mbMessage.setAllowPingbacks(allowPingbacks);
	}

	/**
	* Returns the answer of this message-boards message.
	*
	* @return the answer of this message-boards message
	*/
	public boolean getAnswer() {
		return _mbMessage.getAnswer();
	}

	/**
	* Returns <code>true</code> if this message-boards message is answer.
	*
	* @return <code>true</code> if this message-boards message is answer; <code>false</code> otherwise
	*/
	public boolean isAnswer() {
		return _mbMessage.isAnswer();
	}

	/**
	* Sets whether this message-boards message is answer.
	*
	* @param answer the answer of this message-boards message
	*/
	public void setAnswer(boolean answer) {
		_mbMessage.setAnswer(answer);
	}

	/**
	* Returns the status of this message-boards message.
	*
	* @return the status of this message-boards message
	*/
	public int getStatus() {
		return _mbMessage.getStatus();
	}

	/**
	* Sets the status of this message-boards message.
	*
	* @param status the status of this message-boards message
	*/
	public void setStatus(int status) {
		_mbMessage.setStatus(status);
	}

	/**
	* Returns the status by user ID of this message-boards message.
	*
	* @return the status by user ID of this message-boards message
	*/
	public long getStatusByUserId() {
		return _mbMessage.getStatusByUserId();
	}

	/**
	* Sets the status by user ID of this message-boards message.
	*
	* @param statusByUserId the status by user ID of this message-boards message
	*/
	public void setStatusByUserId(long statusByUserId) {
		_mbMessage.setStatusByUserId(statusByUserId);
	}

	/**
	* Returns the status by user uuid of this message-boards message.
	*
	* @return the status by user uuid of this message-boards message
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getStatusByUserUuid();
	}

	/**
	* Sets the status by user uuid of this message-boards message.
	*
	* @param statusByUserUuid the status by user uuid of this message-boards message
	*/
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbMessage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Returns the status by user name of this message-boards message.
	*
	* @return the status by user name of this message-boards message
	*/
	public java.lang.String getStatusByUserName() {
		return _mbMessage.getStatusByUserName();
	}

	/**
	* Sets the status by user name of this message-boards message.
	*
	* @param statusByUserName the status by user name of this message-boards message
	*/
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbMessage.setStatusByUserName(statusByUserName);
	}

	/**
	* Returns the status date of this message-boards message.
	*
	* @return the status date of this message-boards message
	*/
	public java.util.Date getStatusDate() {
		return _mbMessage.getStatusDate();
	}

	/**
	* Sets the status date of this message-boards message.
	*
	* @param statusDate the status date of this message-boards message
	*/
	public void setStatusDate(java.util.Date statusDate) {
		_mbMessage.setStatusDate(statusDate);
	}

	/**
	* @deprecated Renamed to {@link #isApproved()}
	*/
	public boolean getApproved() {
		return _mbMessage.getApproved();
	}

	/**
	* Returns <code>true</code> if this message-boards message is approved.
	*
	* @return <code>true</code> if this message-boards message is approved; <code>false</code> otherwise
	*/
	public boolean isApproved() {
		return _mbMessage.isApproved();
	}

	/**
	* Returns <code>true</code> if this message-boards message is denied.
	*
	* @return <code>true</code> if this message-boards message is denied; <code>false</code> otherwise
	*/
	public boolean isDenied() {
		return _mbMessage.isDenied();
	}

	/**
	* Returns <code>true</code> if this message-boards message is a draft.
	*
	* @return <code>true</code> if this message-boards message is a draft; <code>false</code> otherwise
	*/
	public boolean isDraft() {
		return _mbMessage.isDraft();
	}

	/**
	* Returns <code>true</code> if this message-boards message is expired.
	*
	* @return <code>true</code> if this message-boards message is expired; <code>false</code> otherwise
	*/
	public boolean isExpired() {
		return _mbMessage.isExpired();
	}

	/**
	* Returns <code>true</code> if this message-boards message is inactive.
	*
	* @return <code>true</code> if this message-boards message is inactive; <code>false</code> otherwise
	*/
	public boolean isInactive() {
		return _mbMessage.isInactive();
	}

	/**
	* Returns <code>true</code> if this message-boards message is incomplete.
	*
	* @return <code>true</code> if this message-boards message is incomplete; <code>false</code> otherwise
	*/
	public boolean isIncomplete() {
		return _mbMessage.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this message-boards message is in the Recycle Bin.
	*
	* @return <code>true</code> if this message-boards message is in the Recycle Bin; <code>false</code> otherwise
	*/
	public boolean isInTrash() {
		return _mbMessage.isInTrash();
	}

	/**
	* Returns <code>true</code> if this message-boards message is pending.
	*
	* @return <code>true</code> if this message-boards message is pending; <code>false</code> otherwise
	*/
	public boolean isPending() {
		return _mbMessage.isPending();
	}

	/**
	* Returns <code>true</code> if this message-boards message is scheduled.
	*
	* @return <code>true</code> if this message-boards message is scheduled; <code>false</code> otherwise
	*/
	public boolean isScheduled() {
		return _mbMessage.isScheduled();
	}

	public boolean isNew() {
		return _mbMessage.isNew();
	}

	public void setNew(boolean n) {
		_mbMessage.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbMessage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbMessage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbMessage.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbMessage.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_mbMessage.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbMessage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbMessage.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MBMessageWrapper((MBMessage)_mbMessage.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage) {
		return _mbMessage.compareTo(mbMessage);
	}

	@Override
	public int hashCode() {
		return _mbMessage.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBMessage> toCacheModel() {
		return _mbMessage.toCacheModel();
	}

	public com.liferay.portlet.messageboards.model.MBMessage toEscapedModel() {
		return new MBMessageWrapper(_mbMessage.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbMessage.toString();
	}

	public java.lang.String toXmlString() {
		return _mbMessage.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessage.persist();
	}

	public java.lang.String[] getAssetTagNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getAssetTagNames();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbMessage.getAttachmentsDir();
	}

	public java.lang.String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getAttachmentsFiles();
	}

	public java.lang.String getBody(boolean translate) {
		return _mbMessage.getBody(translate);
	}

	public com.liferay.portlet.messageboards.model.MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getCategory();
	}

	public java.lang.String getDeletedAttachmentsDir() {
		return _mbMessage.getDeletedAttachmentsDir();
	}

	public java.lang.String[] getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getDeletedAttachmentsFiles();
	}

	public com.liferay.portlet.messageboards.model.MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.getThread();
	}

	public java.lang.String getThreadAttachmentsDir() {
		return _mbMessage.getThreadAttachmentsDir();
	}

	public java.lang.String getWorkflowClassName() {
		return _mbMessage.getWorkflowClassName();
	}

	public boolean isDiscussion() {
		return _mbMessage.isDiscussion();
	}

	public boolean isFormatBBCode() {
		return _mbMessage.isFormatBBCode();
	}

	public boolean isInTrashThread()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessage.isInTrashThread();
	}

	public boolean isReply() {
		return _mbMessage.isReply();
	}

	public boolean isRoot() {
		return _mbMessage.isRoot();
	}

	public void setAttachmentsDir(java.lang.String attachmentsDir) {
		_mbMessage.setAttachmentsDir(attachmentsDir);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public MBMessage getWrappedMBMessage() {
		return _mbMessage;
	}

	public MBMessage getWrappedModel() {
		return _mbMessage;
	}

	public void resetOriginalValues() {
		_mbMessage.resetOriginalValues();
	}

	private MBMessage _mbMessage;
}