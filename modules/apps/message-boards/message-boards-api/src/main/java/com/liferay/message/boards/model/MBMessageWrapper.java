/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link MBMessage}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMessage
 * @generated
 */
public class MBMessageWrapper
	extends BaseModelWrapper<MBMessage>
	implements MBMessage, ModelWrapper<MBMessage> {

	public MBMessageWrapper(MBMessage mbMessage) {
		super(mbMessage);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
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
		attributes.put("treePath", getTreePath());
		attributes.put("subject", getSubject());
		attributes.put("urlSubject", getUrlSubject());
		attributes.put("body", getBody());
		attributes.put("format", getFormat());
		attributes.put("anonymous", isAnonymous());
		attributes.put("priority", getPriority());
		attributes.put("allowPingbacks", isAllowPingbacks());
		attributes.put("answer", isAnswer());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
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

		String treePath = (String)attributes.get("treePath");

		if (treePath != null) {
			setTreePath(treePath);
		}

		String subject = (String)attributes.get("subject");

		if (subject != null) {
			setSubject(subject);
		}

		String urlSubject = (String)attributes.get("urlSubject");

		if (urlSubject != null) {
			setUrlSubject(urlSubject);
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
	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.addAttachmentsFolder();
	}

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.buildTreePath();
	}

	@Override
	public MBMessage cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the allow pingbacks of this message-boards message.
	 *
	 * @return the allow pingbacks of this message-boards message
	 */
	@Override
	public boolean getAllowPingbacks() {
		return model.getAllowPingbacks();
	}

	/**
	 * Returns the anonymous of this message-boards message.
	 *
	 * @return the anonymous of this message-boards message
	 */
	@Override
	public boolean getAnonymous() {
		return model.getAnonymous();
	}

	/**
	 * Returns the answer of this message-boards message.
	 *
	 * @return the answer of this message-boards message
	 */
	@Override
	public boolean getAnswer() {
		return model.getAnswer();
	}

	@Override
	public String[] getAssetTagNames() {
		return model.getAssetTagNames();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntries(start, end);
	}

	@Override
	public int getAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntriesCount();
	}

	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			getAttachmentsFileEntryByExternalReferenceCode(
				String externalReferenceCode, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFileEntryByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	@Override
	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAttachmentsFolderId();
	}

	/**
	 * Returns the body of this message-boards message.
	 *
	 * @return the body of this message-boards message
	 */
	@Override
	public String getBody() {
		return model.getBody();
	}

	@Override
	public String getBody(boolean translate) {
		return model.getBody(translate);
	}

	@Override
	public MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCategory();
	}

	/**
	 * Returns the category ID of this message-boards message.
	 *
	 * @return the category ID of this message-boards message
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the fully qualified class name of this message-boards message.
	 *
	 * @return the fully qualified class name of this message-boards message
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this message-boards message.
	 *
	 * @return the class name ID of this message-boards message
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this message-boards message.
	 *
	 * @return the class pk of this message-boards message
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this message-boards message.
	 *
	 * @return the company ID of this message-boards message
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this message-boards message.
	 *
	 * @return the create date of this message-boards message
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message-boards message.
	 *
	 * @return the ct collection ID of this message-boards message
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntries();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntries(start, end);
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDeletedAttachmentsFileEntriesCount();
	}

	/**
	 * Returns the external reference code of this message-boards message.
	 *
	 * @return the external reference code of this message-boards message
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the format of this message-boards message.
	 *
	 * @return the format of this message-boards message
	 */
	@Override
	public String getFormat() {
		return model.getFormat();
	}

	/**
	 * Returns the group ID of this message-boards message.
	 *
	 * @return the group ID of this message-boards message
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this message-boards message.
	 *
	 * @return the last publish date of this message-boards message
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the message ID of this message-boards message.
	 *
	 * @return the message ID of this message-boards message
	 */
	@Override
	public long getMessageId() {
		return model.getMessageId();
	}

	/**
	 * Returns the modified date of this message-boards message.
	 *
	 * @return the modified date of this message-boards message
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message-boards message.
	 *
	 * @return the mvcc version of this message-boards message
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent message ID of this message-boards message.
	 *
	 * @return the parent message ID of this message-boards message
	 */
	@Override
	public long getParentMessageId() {
		return model.getParentMessageId();
	}

	/**
	 * Returns the primary key of this message-boards message.
	 *
	 * @return the primary key of this message-boards message
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this message-boards message.
	 *
	 * @return the priority of this message-boards message
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the root message ID of this message-boards message.
	 *
	 * @return the root message ID of this message-boards message
	 */
	@Override
	public long getRootMessageId() {
		return model.getRootMessageId();
	}

	/**
	 * Returns the status of this message-boards message.
	 *
	 * @return the status of this message-boards message
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this message-boards message.
	 *
	 * @return the status by user ID of this message-boards message
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this message-boards message.
	 *
	 * @return the status by user name of this message-boards message
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this message-boards message.
	 *
	 * @return the status by user uuid of this message-boards message
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this message-boards message.
	 *
	 * @return the status date of this message-boards message
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the subject of this message-boards message.
	 *
	 * @return the subject of this message-boards message
	 */
	@Override
	public String getSubject() {
		return model.getSubject();
	}

	@Override
	public MBThread getThread()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getThread();
	}

	@Override
	public long getThreadAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getThreadAttachmentsFolderId();
	}

	/**
	 * Returns the thread ID of this message-boards message.
	 *
	 * @return the thread ID of this message-boards message
	 */
	@Override
	public long getThreadId() {
		return model.getThreadId();
	}

	/**
	 * Returns the class primary key of the trash entry for this message-boards message.
	 *
	 * @return the class primary key of the trash entry for this message-boards message
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the tree path of this message-boards message.
	 *
	 * @return the tree path of this message-boards message
	 */
	@Override
	public String getTreePath() {
		return model.getTreePath();
	}

	/**
	 * Returns the url subject of this message-boards message.
	 *
	 * @return the url subject of this message-boards message
	 */
	@Override
	public String getUrlSubject() {
		return model.getUrlSubject();
	}

	/**
	 * Returns the user ID of this message-boards message.
	 *
	 * @return the user ID of this message-boards message
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message-boards message.
	 *
	 * @return the user name of this message-boards message
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message-boards message.
	 *
	 * @return the user uuid of this message-boards message
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message-boards message.
	 *
	 * @return the uuid of this message-boards message
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public String getWorkflowClassName() {
		return model.getWorkflowClassName();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is allow pingbacks.
	 *
	 * @return <code>true</code> if this message-boards message is allow pingbacks; <code>false</code> otherwise
	 */
	@Override
	public boolean isAllowPingbacks() {
		return model.isAllowPingbacks();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is anonymous.
	 *
	 * @return <code>true</code> if this message-boards message is anonymous; <code>false</code> otherwise
	 */
	@Override
	public boolean isAnonymous() {
		return model.isAnonymous();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is answer.
	 *
	 * @return <code>true</code> if this message-boards message is answer; <code>false</code> otherwise
	 */
	@Override
	public boolean isAnswer() {
		return model.isAnswer();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is approved.
	 *
	 * @return <code>true</code> if this message-boards message is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is denied.
	 *
	 * @return <code>true</code> if this message-boards message is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	@Override
	public boolean isDiscussion() {
		return model.isDiscussion();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is a draft.
	 *
	 * @return <code>true</code> if this message-boards message is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is expired.
	 *
	 * @return <code>true</code> if this message-boards message is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	@Override
	public boolean isFormatBBCode() {
		return model.isFormatBBCode();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is inactive.
	 *
	 * @return <code>true</code> if this message-boards message is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is incomplete.
	 *
	 * @return <code>true</code> if this message-boards message is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this message-boards message is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is pending.
	 *
	 * @return <code>true</code> if this message-boards message is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	@Override
	public boolean isReply() {
		return model.isReply();
	}

	@Override
	public boolean isRoot() {
		return model.isRoot();
	}

	/**
	 * Returns <code>true</code> if this message-boards message is scheduled.
	 *
	 * @return <code>true</code> if this message-boards message is scheduled; <code>false</code> otherwise
	 */
	@Override
	public boolean isScheduled() {
		return model.isScheduled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this message-boards message is allow pingbacks.
	 *
	 * @param allowPingbacks the allow pingbacks of this message-boards message
	 */
	@Override
	public void setAllowPingbacks(boolean allowPingbacks) {
		model.setAllowPingbacks(allowPingbacks);
	}

	/**
	 * Sets whether this message-boards message is anonymous.
	 *
	 * @param anonymous the anonymous of this message-boards message
	 */
	@Override
	public void setAnonymous(boolean anonymous) {
		model.setAnonymous(anonymous);
	}

	/**
	 * Sets whether this message-boards message is answer.
	 *
	 * @param answer the answer of this message-boards message
	 */
	@Override
	public void setAnswer(boolean answer) {
		model.setAnswer(answer);
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		model.setAttachmentsFolderId(attachmentsFolderId);
	}

	/**
	 * Sets the body of this message-boards message.
	 *
	 * @param body the body of this message-boards message
	 */
	@Override
	public void setBody(String body) {
		model.setBody(body);
	}

	/**
	 * Sets the category ID of this message-boards message.
	 *
	 * @param categoryId the category ID of this message-boards message
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this message-boards message.
	 *
	 * @param classNameId the class name ID of this message-boards message
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this message-boards message.
	 *
	 * @param classPK the class pk of this message-boards message
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this message-boards message.
	 *
	 * @param companyId the company ID of this message-boards message
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this message-boards message.
	 *
	 * @param createDate the create date of this message-boards message
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message-boards message.
	 *
	 * @param ctCollectionId the ct collection ID of this message-boards message
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the external reference code of this message-boards message.
	 *
	 * @param externalReferenceCode the external reference code of this message-boards message
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the format of this message-boards message.
	 *
	 * @param format the format of this message-boards message
	 */
	@Override
	public void setFormat(String format) {
		model.setFormat(format);
	}

	/**
	 * Sets the group ID of this message-boards message.
	 *
	 * @param groupId the group ID of this message-boards message
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this message-boards message.
	 *
	 * @param lastPublishDate the last publish date of this message-boards message
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the message ID of this message-boards message.
	 *
	 * @param messageId the message ID of this message-boards message
	 */
	@Override
	public void setMessageId(long messageId) {
		model.setMessageId(messageId);
	}

	/**
	 * Sets the modified date of this message-boards message.
	 *
	 * @param modifiedDate the modified date of this message-boards message
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message-boards message.
	 *
	 * @param mvccVersion the mvcc version of this message-boards message
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent message ID of this message-boards message.
	 *
	 * @param parentMessageId the parent message ID of this message-boards message
	 */
	@Override
	public void setParentMessageId(long parentMessageId) {
		model.setParentMessageId(parentMessageId);
	}

	/**
	 * Sets the primary key of this message-boards message.
	 *
	 * @param primaryKey the primary key of this message-boards message
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this message-boards message.
	 *
	 * @param priority the priority of this message-boards message
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the root message ID of this message-boards message.
	 *
	 * @param rootMessageId the root message ID of this message-boards message
	 */
	@Override
	public void setRootMessageId(long rootMessageId) {
		model.setRootMessageId(rootMessageId);
	}

	/**
	 * Sets the status of this message-boards message.
	 *
	 * @param status the status of this message-boards message
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this message-boards message.
	 *
	 * @param statusByUserId the status by user ID of this message-boards message
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this message-boards message.
	 *
	 * @param statusByUserName the status by user name of this message-boards message
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this message-boards message.
	 *
	 * @param statusByUserUuid the status by user uuid of this message-boards message
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this message-boards message.
	 *
	 * @param statusDate the status date of this message-boards message
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the subject of this message-boards message.
	 *
	 * @param subject the subject of this message-boards message
	 */
	@Override
	public void setSubject(String subject) {
		model.setSubject(subject);
	}

	/**
	 * Sets the thread ID of this message-boards message.
	 *
	 * @param threadId the thread ID of this message-boards message
	 */
	@Override
	public void setThreadId(long threadId) {
		model.setThreadId(threadId);
	}

	/**
	 * Sets the tree path of this message-boards message.
	 *
	 * @param treePath the tree path of this message-boards message
	 */
	@Override
	public void setTreePath(String treePath) {
		model.setTreePath(treePath);
	}

	/**
	 * Sets the url subject of this message-boards message.
	 *
	 * @param urlSubject the url subject of this message-boards message
	 */
	@Override
	public void setUrlSubject(String urlSubject) {
		model.setUrlSubject(urlSubject);
	}

	/**
	 * Sets the user ID of this message-boards message.
	 *
	 * @param userId the user ID of this message-boards message
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message-boards message.
	 *
	 * @param userName the user name of this message-boards message
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message-boards message.
	 *
	 * @param userUuid the user uuid of this message-boards message
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message-boards message.
	 *
	 * @param uuid the uuid of this message-boards message
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public void updateTreePath(String treePath) {
		model.updateTreePath(treePath);
	}

	@Override
	public Map<String, Function<MBMessage, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBMessage, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBMessageWrapper wrap(MBMessage mbMessage) {
		return new MBMessageWrapper(mbMessage);
	}

}