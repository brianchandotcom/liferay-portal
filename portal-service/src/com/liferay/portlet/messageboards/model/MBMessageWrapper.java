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

package com.liferay.portlet.messageboards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MBMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessage
 * @generated
 */
@ProviderType
public class MBMessageWrapper implements MBMessage, ModelWrapper<MBMessage> {
	public MBMessageWrapper(MBMessage mbMessage) {
		_mbMessage = mbMessage;
	}

	@Override
	public Class<?> getModelClass() {
		return MBMessage.class;
	}

	@Override
	public String getModelClassName() {
		return MBMessage.class.getName();
	}

	@Override
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
		attributes.put("anonymous", getAnonymous());
		attributes.put("priority", getPriority());
		attributes.put("allowPingbacks", getAllowPingbacks());
		attributes.put("answer", getAnswer());
		attributes.put("lastPublishDate", getLastPublishDate());
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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
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

	@Override
	public com.liferay.portal.kernel.repository.model.Folder addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.addAttachmentsFolder();
	}

	@Override
	public java.lang.Object clone() {
		return new MBMessageWrapper((MBMessage)_mbMessage.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage) {
		return _mbMessage.compareTo(mbMessage);
	}

	/**
	* Returns the allow pingbacks of this message-boards message.
	*
	* @return the allow pingbacks of this message-boards message
	*/
	@Override
	public boolean getAllowPingbacks() {
		return _mbMessage.getAllowPingbacks();
	}

	/**
	* Returns the anonymous of this message-boards message.
	*
	* @return the anonymous of this message-boards message
	*/
	@Override
	public boolean getAnonymous() {
		return _mbMessage.getAnonymous();
	}

	/**
	* Returns the answer of this message-boards message.
	*
	* @return the answer of this message-boards message
	*/
	@Override
	public boolean getAnswer() {
		return _mbMessage.getAnswer();
	}

	@Override
	public java.lang.String[] getAssetTagNames() {
		return _mbMessage.getAssetTagNames();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getAttachmentsFileEntriesCount();
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getAttachmentsFolderId();
	}

	/**
	* Returns the body of this message-boards message.
	*
	* @return the body of this message-boards message
	*/
	@Override
	public java.lang.String getBody() {
		return _mbMessage.getBody();
	}

	@Override
	public java.lang.String getBody(boolean translate) {
		return _mbMessage.getBody(translate);
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getCategory();
	}

	/**
	* Returns the category ID of this message-boards message.
	*
	* @return the category ID of this message-boards message
	*/
	@Override
	public long getCategoryId() {
		return _mbMessage.getCategoryId();
	}

	/**
	* Returns the fully qualified class name of this message-boards message.
	*
	* @return the fully qualified class name of this message-boards message
	*/
	@Override
	public java.lang.String getClassName() {
		return _mbMessage.getClassName();
	}

	/**
	* Returns the class name ID of this message-boards message.
	*
	* @return the class name ID of this message-boards message
	*/
	@Override
	public long getClassNameId() {
		return _mbMessage.getClassNameId();
	}

	/**
	* Returns the class p k of this message-boards message.
	*
	* @return the class p k of this message-boards message
	*/
	@Override
	public long getClassPK() {
		return _mbMessage.getClassPK();
	}

	/**
	* Returns the company ID of this message-boards message.
	*
	* @return the company ID of this message-boards message
	*/
	@Override
	public long getCompanyId() {
		return _mbMessage.getCompanyId();
	}

	/**
	* Returns the create date of this message-boards message.
	*
	* @return the create date of this message-boards message
	*/
	@Override
	public Date getCreateDate() {
		return _mbMessage.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getDeletedAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getDeletedAttachmentsFileEntries(start, end);
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getDeletedAttachmentsFileEntriesCount();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _mbMessage.getExpandoBridge();
	}

	/**
	* Returns the format of this message-boards message.
	*
	* @return the format of this message-boards message
	*/
	@Override
	public java.lang.String getFormat() {
		return _mbMessage.getFormat();
	}

	/**
	* Returns the group ID of this message-boards message.
	*
	* @return the group ID of this message-boards message
	*/
	@Override
	public long getGroupId() {
		return _mbMessage.getGroupId();
	}

	/**
	* Returns the last publish date of this message-boards message.
	*
	* @return the last publish date of this message-boards message
	*/
	@Override
	public Date getLastPublishDate() {
		return _mbMessage.getLastPublishDate();
	}

	/**
	* Returns the message ID of this message-boards message.
	*
	* @return the message ID of this message-boards message
	*/
	@Override
	public long getMessageId() {
		return _mbMessage.getMessageId();
	}

	/**
	* Returns the modified date of this message-boards message.
	*
	* @return the modified date of this message-boards message
	*/
	@Override
	public Date getModifiedDate() {
		return _mbMessage.getModifiedDate();
	}

	/**
	* Returns the parent message ID of this message-boards message.
	*
	* @return the parent message ID of this message-boards message
	*/
	@Override
	public long getParentMessageId() {
		return _mbMessage.getParentMessageId();
	}

	/**
	* Returns the primary key of this message-boards message.
	*
	* @return the primary key of this message-boards message
	*/
	@Override
	public long getPrimaryKey() {
		return _mbMessage.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _mbMessage.getPrimaryKeyObj();
	}

	/**
	* Returns the priority of this message-boards message.
	*
	* @return the priority of this message-boards message
	*/
	@Override
	public double getPriority() {
		return _mbMessage.getPriority();
	}

	/**
	* Returns the root message ID of this message-boards message.
	*
	* @return the root message ID of this message-boards message
	*/
	@Override
	public long getRootMessageId() {
		return _mbMessage.getRootMessageId();
	}

	/**
	* Returns the status of this message-boards message.
	*
	* @return the status of this message-boards message
	*/
	@Override
	public int getStatus() {
		return _mbMessage.getStatus();
	}

	/**
	* Returns the status by user ID of this message-boards message.
	*
	* @return the status by user ID of this message-boards message
	*/
	@Override
	public long getStatusByUserId() {
		return _mbMessage.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this message-boards message.
	*
	* @return the status by user name of this message-boards message
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _mbMessage.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this message-boards message.
	*
	* @return the status by user uuid of this message-boards message
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _mbMessage.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this message-boards message.
	*
	* @return the status date of this message-boards message
	*/
	@Override
	public Date getStatusDate() {
		return _mbMessage.getStatusDate();
	}

	/**
	* Returns the subject of this message-boards message.
	*
	* @return the subject of this message-boards message
	*/
	@Override
	public java.lang.String getSubject() {
		return _mbMessage.getSubject();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getThread();
	}

	@Override
	public long getThreadAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getThreadAttachmentsFolderId();
	}

	/**
	* Returns the thread ID of this message-boards message.
	*
	* @return the thread ID of this message-boards message
	*/
	@Override
	public long getThreadId() {
		return _mbMessage.getThreadId();
	}

	/**
	* Returns the trash entry created when this message-boards message was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this message-boards message.
	*
	* @return the trash entry created when this message-boards message was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _mbMessage.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this message-boards message.
	*
	* @return the class primary key of the trash entry for this message-boards message
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _mbMessage.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this message-boards message.
	*
	* @return the trash handler for this message-boards message
	*/
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _mbMessage.getTrashHandler();
	}

	/**
	* Returns the user ID of this message-boards message.
	*
	* @return the user ID of this message-boards message
	*/
	@Override
	public long getUserId() {
		return _mbMessage.getUserId();
	}

	/**
	* Returns the user name of this message-boards message.
	*
	* @return the user name of this message-boards message
	*/
	@Override
	public java.lang.String getUserName() {
		return _mbMessage.getUserName();
	}

	/**
	* Returns the user uuid of this message-boards message.
	*
	* @return the user uuid of this message-boards message
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _mbMessage.getUserUuid();
	}

	/**
	* Returns the uuid of this message-boards message.
	*
	* @return the uuid of this message-boards message
	*/
	@Override
	public java.lang.String getUuid() {
		return _mbMessage.getUuid();
	}

	@Override
	public java.lang.String getWorkflowClassName() {
		return _mbMessage.getWorkflowClassName();
	}

	@Override
	public int hashCode() {
		return _mbMessage.hashCode();
	}

	/**
	* Returns <code>true</code> if this message-boards message is allow pingbacks.
	*
	* @return <code>true</code> if this message-boards message is allow pingbacks; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowPingbacks() {
		return _mbMessage.isAllowPingbacks();
	}

	/**
	* Returns <code>true</code> if this message-boards message is anonymous.
	*
	* @return <code>true</code> if this message-boards message is anonymous; <code>false</code> otherwise
	*/
	@Override
	public boolean isAnonymous() {
		return _mbMessage.isAnonymous();
	}

	/**
	* Returns <code>true</code> if this message-boards message is answer.
	*
	* @return <code>true</code> if this message-boards message is answer; <code>false</code> otherwise
	*/
	@Override
	public boolean isAnswer() {
		return _mbMessage.isAnswer();
	}

	/**
	* Returns <code>true</code> if this message-boards message is approved.
	*
	* @return <code>true</code> if this message-boards message is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _mbMessage.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _mbMessage.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this message-boards message is denied.
	*
	* @return <code>true</code> if this message-boards message is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _mbMessage.isDenied();
	}

	@Override
	public boolean isDiscussion() {
		return _mbMessage.isDiscussion();
	}

	/**
	* Returns <code>true</code> if this message-boards message is a draft.
	*
	* @return <code>true</code> if this message-boards message is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _mbMessage.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _mbMessage.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this message-boards message is expired.
	*
	* @return <code>true</code> if this message-boards message is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _mbMessage.isExpired();
	}

	@Override
	public boolean isFormatBBCode() {
		return _mbMessage.isFormatBBCode();
	}

	/**
	* Returns <code>true</code> if this message-boards message is in the Recycle Bin.
	*
	* @return <code>true</code> if this message-boards message is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _mbMessage.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this message-boards message is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this message-boards message is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _mbMessage.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _mbMessage.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _mbMessage.isInTrashImplicitly();
	}

	/**
	* Returns <code>true</code> if this message-boards message is inactive.
	*
	* @return <code>true</code> if this message-boards message is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _mbMessage.isInactive();
	}

	/**
	* Returns <code>true</code> if this message-boards message is incomplete.
	*
	* @return <code>true</code> if this message-boards message is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _mbMessage.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _mbMessage.isNew();
	}

	/**
	* Returns <code>true</code> if this message-boards message is pending.
	*
	* @return <code>true</code> if this message-boards message is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _mbMessage.isPending();
	}

	@Override
	public boolean isReply() {
		return _mbMessage.isReply();
	}

	@Override
	public boolean isRoot() {
		return _mbMessage.isRoot();
	}

	/**
	* Returns <code>true</code> if this message-boards message is scheduled.
	*
	* @return <code>true</code> if this message-boards message is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _mbMessage.isScheduled();
	}

	@Override
	public void persist() {
		_mbMessage.persist();
	}

	/**
	* Sets whether this message-boards message is allow pingbacks.
	*
	* @param allowPingbacks the allow pingbacks of this message-boards message
	*/
	@Override
	public void setAllowPingbacks(boolean allowPingbacks) {
		_mbMessage.setAllowPingbacks(allowPingbacks);
	}

	/**
	* Sets whether this message-boards message is anonymous.
	*
	* @param anonymous the anonymous of this message-boards message
	*/
	@Override
	public void setAnonymous(boolean anonymous) {
		_mbMessage.setAnonymous(anonymous);
	}

	/**
	* Sets whether this message-boards message is answer.
	*
	* @param answer the answer of this message-boards message
	*/
	@Override
	public void setAnswer(boolean answer) {
		_mbMessage.setAnswer(answer);
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_mbMessage.setAttachmentsFolderId(attachmentsFolderId);
	}

	/**
	* Sets the body of this message-boards message.
	*
	* @param body the body of this message-boards message
	*/
	@Override
	public void setBody(java.lang.String body) {
		_mbMessage.setBody(body);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_mbMessage.setCachedModel(cachedModel);
	}

	/**
	* Sets the category ID of this message-boards message.
	*
	* @param categoryId the category ID of this message-boards message
	*/
	@Override
	public void setCategoryId(long categoryId) {
		_mbMessage.setCategoryId(categoryId);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_mbMessage.setClassName(className);
	}

	/**
	* Sets the class name ID of this message-boards message.
	*
	* @param classNameId the class name ID of this message-boards message
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_mbMessage.setClassNameId(classNameId);
	}

	/**
	* Sets the class p k of this message-boards message.
	*
	* @param classPK the class p k of this message-boards message
	*/
	@Override
	public void setClassPK(long classPK) {
		_mbMessage.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this message-boards message.
	*
	* @param companyId the company ID of this message-boards message
	*/
	@Override
	public void setCompanyId(long companyId) {
		_mbMessage.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this message-boards message.
	*
	* @param createDate the create date of this message-boards message
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_mbMessage.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_mbMessage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_mbMessage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_mbMessage.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the format of this message-boards message.
	*
	* @param format the format of this message-boards message
	*/
	@Override
	public void setFormat(java.lang.String format) {
		_mbMessage.setFormat(format);
	}

	/**
	* Sets the group ID of this message-boards message.
	*
	* @param groupId the group ID of this message-boards message
	*/
	@Override
	public void setGroupId(long groupId) {
		_mbMessage.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this message-boards message.
	*
	* @param lastPublishDate the last publish date of this message-boards message
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_mbMessage.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the message ID of this message-boards message.
	*
	* @param messageId the message ID of this message-boards message
	*/
	@Override
	public void setMessageId(long messageId) {
		_mbMessage.setMessageId(messageId);
	}

	/**
	* Sets the modified date of this message-boards message.
	*
	* @param modifiedDate the modified date of this message-boards message
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_mbMessage.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_mbMessage.setNew(n);
	}

	/**
	* Sets the parent message ID of this message-boards message.
	*
	* @param parentMessageId the parent message ID of this message-boards message
	*/
	@Override
	public void setParentMessageId(long parentMessageId) {
		_mbMessage.setParentMessageId(parentMessageId);
	}

	/**
	* Sets the primary key of this message-boards message.
	*
	* @param primaryKey the primary key of this message-boards message
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_mbMessage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_mbMessage.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this message-boards message.
	*
	* @param priority the priority of this message-boards message
	*/
	@Override
	public void setPriority(double priority) {
		_mbMessage.setPriority(priority);
	}

	/**
	* Sets the root message ID of this message-boards message.
	*
	* @param rootMessageId the root message ID of this message-boards message
	*/
	@Override
	public void setRootMessageId(long rootMessageId) {
		_mbMessage.setRootMessageId(rootMessageId);
	}

	/**
	* Sets the status of this message-boards message.
	*
	* @param status the status of this message-boards message
	*/
	@Override
	public void setStatus(int status) {
		_mbMessage.setStatus(status);
	}

	/**
	* Sets the status by user ID of this message-boards message.
	*
	* @param statusByUserId the status by user ID of this message-boards message
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_mbMessage.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this message-boards message.
	*
	* @param statusByUserName the status by user name of this message-boards message
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbMessage.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this message-boards message.
	*
	* @param statusByUserUuid the status by user uuid of this message-boards message
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbMessage.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this message-boards message.
	*
	* @param statusDate the status date of this message-boards message
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_mbMessage.setStatusDate(statusDate);
	}

	/**
	* Sets the subject of this message-boards message.
	*
	* @param subject the subject of this message-boards message
	*/
	@Override
	public void setSubject(java.lang.String subject) {
		_mbMessage.setSubject(subject);
	}

	/**
	* Sets the thread ID of this message-boards message.
	*
	* @param threadId the thread ID of this message-boards message
	*/
	@Override
	public void setThreadId(long threadId) {
		_mbMessage.setThreadId(threadId);
	}

	/**
	* Sets the user ID of this message-boards message.
	*
	* @param userId the user ID of this message-boards message
	*/
	@Override
	public void setUserId(long userId) {
		_mbMessage.setUserId(userId);
	}

	/**
	* Sets the user name of this message-boards message.
	*
	* @param userName the user name of this message-boards message
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_mbMessage.setUserName(userName);
	}

	/**
	* Sets the user uuid of this message-boards message.
	*
	* @param userUuid the user uuid of this message-boards message
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_mbMessage.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this message-boards message.
	*
	* @param uuid the uuid of this message-boards message
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_mbMessage.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.messageboards.model.MBMessage> toCacheModel() {
		return _mbMessage.toCacheModel();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage toEscapedModel() {
		return new MBMessageWrapper(_mbMessage.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _mbMessage.toString();
	}

	@Override
	public com.liferay.portlet.messageboards.model.MBMessage toUnescapedModel() {
		return new MBMessageWrapper(_mbMessage.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _mbMessage.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBMessageWrapper)) {
			return false;
		}

		MBMessageWrapper mbMessageWrapper = (MBMessageWrapper)obj;

		if (Validator.equals(_mbMessage, mbMessageWrapper._mbMessage)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _mbMessage.getStagedModelType();
	}

	@Override
	public MBMessage getWrappedModel() {
		return _mbMessage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _mbMessage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _mbMessage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_mbMessage.resetOriginalValues();
	}

	private final MBMessage _mbMessage;
}