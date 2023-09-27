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
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBThread
 * @generated
 */
public class MBThreadWrapper
	extends BaseModelWrapper<MBThread>
	implements MBThread, ModelWrapper<MBThread> {

	public MBThreadWrapper(MBThread mbThread) {
		super(mbThread);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("title", getTitle());
		attributes.put("lastPostByUserId", getLastPostByUserId());
		attributes.put("lastPostDate", getLastPostDate());
		attributes.put("priority", getPriority());
		attributes.put("question", isQuestion());
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

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
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
	public MBThread cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public long getAttachmentsFolderId() {
		return model.getAttachmentsFolderId();
	}

	@Override
	public MBCategory getCategory()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCategory();
	}

	/**
	 * Returns the category ID of this message boards thread.
	 *
	 * @return the category ID of this message boards thread
	 */
	@Override
	public long getCategoryId() {
		return model.getCategoryId();
	}

	/**
	 * Returns the company ID of this message boards thread.
	 *
	 * @return the company ID of this message boards thread
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this message boards thread.
	 *
	 * @return the container model ID of this message boards thread
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this message boards thread.
	 *
	 * @return the container name of this message boards thread
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this message boards thread.
	 *
	 * @return the create date of this message boards thread
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards thread.
	 *
	 * @return the ct collection ID of this message boards thread
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this message boards thread.
	 *
	 * @return the group ID of this message boards thread
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last post by user ID of this message boards thread.
	 *
	 * @return the last post by user ID of this message boards thread
	 */
	@Override
	public long getLastPostByUserId() {
		return model.getLastPostByUserId();
	}

	/**
	 * Returns the last post by user uuid of this message boards thread.
	 *
	 * @return the last post by user uuid of this message boards thread
	 */
	@Override
	public String getLastPostByUserUuid() {
		return model.getLastPostByUserUuid();
	}

	/**
	 * Returns the last post date of this message boards thread.
	 *
	 * @return the last post date of this message boards thread
	 */
	@Override
	public Date getLastPostDate() {
		return model.getLastPostDate();
	}

	/**
	 * Returns the last publish date of this message boards thread.
	 *
	 * @return the last publish date of this message boards thread
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock getLock() {
		return model.getLock();
	}

	@Override
	public int getMessageCount() {
		return model.getMessageCount();
	}

	/**
	 * Returns the modified date of this message boards thread.
	 *
	 * @return the modified date of this message boards thread
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards thread.
	 *
	 * @return the mvcc version of this message boards thread
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this message boards thread.
	 *
	 * @return the parent container model ID of this message boards thread
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	@Override
	public long[] getParticipantUserIds() {
		return model.getParticipantUserIds();
	}

	/**
	 * Returns the primary key of this message boards thread.
	 *
	 * @return the primary key of this message boards thread
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this message boards thread.
	 *
	 * @return the priority of this message boards thread
	 */
	@Override
	public double getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the question of this message boards thread.
	 *
	 * @return the question of this message boards thread
	 */
	@Override
	public boolean getQuestion() {
		return model.getQuestion();
	}

	/**
	 * Returns the root message ID of this message boards thread.
	 *
	 * @return the root message ID of this message boards thread
	 */
	@Override
	public long getRootMessageId() {
		return model.getRootMessageId();
	}

	/**
	 * Returns the root message user ID of this message boards thread.
	 *
	 * @return the root message user ID of this message boards thread
	 */
	@Override
	public long getRootMessageUserId() {
		return model.getRootMessageUserId();
	}

	/**
	 * Returns the root message user uuid of this message boards thread.
	 *
	 * @return the root message user uuid of this message boards thread
	 */
	@Override
	public String getRootMessageUserUuid() {
		return model.getRootMessageUserUuid();
	}

	/**
	 * Returns the status of this message boards thread.
	 *
	 * @return the status of this message boards thread
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the status by user ID of this message boards thread.
	 *
	 * @return the status by user ID of this message boards thread
	 */
	@Override
	public long getStatusByUserId() {
		return model.getStatusByUserId();
	}

	/**
	 * Returns the status by user name of this message boards thread.
	 *
	 * @return the status by user name of this message boards thread
	 */
	@Override
	public String getStatusByUserName() {
		return model.getStatusByUserName();
	}

	/**
	 * Returns the status by user uuid of this message boards thread.
	 *
	 * @return the status by user uuid of this message boards thread
	 */
	@Override
	public String getStatusByUserUuid() {
		return model.getStatusByUserUuid();
	}

	/**
	 * Returns the status date of this message boards thread.
	 *
	 * @return the status date of this message boards thread
	 */
	@Override
	public Date getStatusDate() {
		return model.getStatusDate();
	}

	/**
	 * Returns the thread ID of this message boards thread.
	 *
	 * @return the thread ID of this message boards thread
	 */
	@Override
	public long getThreadId() {
		return model.getThreadId();
	}

	/**
	 * Returns the title of this message boards thread.
	 *
	 * @return the title of this message boards thread
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Returns the class primary key of the trash entry for this message boards thread.
	 *
	 * @return the class primary key of the trash entry for this message boards thread
	 */
	@Override
	public long getTrashEntryClassPK() {
		return model.getTrashEntryClassPK();
	}

	/**
	 * Returns the user ID of this message boards thread.
	 *
	 * @return the user ID of this message boards thread
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards thread.
	 *
	 * @return the user name of this message boards thread
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards thread.
	 *
	 * @return the user uuid of this message boards thread
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards thread.
	 *
	 * @return the uuid of this message boards thread
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public long getViewCount() {
		return model.getViewCount();
	}

	@Override
	public boolean hasLock(long userId) {
		return model.hasLock(userId);
	}

	/**
	 * Returns <code>true</code> if this message boards thread is approved.
	 *
	 * @return <code>true</code> if this message boards thread is approved; <code>false</code> otherwise
	 */
	@Override
	public boolean isApproved() {
		return model.isApproved();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is denied.
	 *
	 * @return <code>true</code> if this message boards thread is denied; <code>false</code> otherwise
	 */
	@Override
	public boolean isDenied() {
		return model.isDenied();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is a draft.
	 *
	 * @return <code>true</code> if this message boards thread is a draft; <code>false</code> otherwise
	 */
	@Override
	public boolean isDraft() {
		return model.isDraft();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is expired.
	 *
	 * @return <code>true</code> if this message boards thread is expired; <code>false</code> otherwise
	 */
	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is inactive.
	 *
	 * @return <code>true</code> if this message boards thread is inactive; <code>false</code> otherwise
	 */
	@Override
	public boolean isInactive() {
		return model.isInactive();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is incomplete.
	 *
	 * @return <code>true</code> if this message boards thread is incomplete; <code>false</code> otherwise
	 */
	@Override
	public boolean isIncomplete() {
		return model.isIncomplete();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is in the Recycle Bin.
	 *
	 * @return <code>true</code> if this message boards thread is in the Recycle Bin; <code>false</code> otherwise
	 */
	@Override
	public boolean isInTrash() {
		return model.isInTrash();
	}

	@Override
	public boolean isLocked() {
		return model.isLocked();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is pending.
	 *
	 * @return <code>true</code> if this message boards thread is pending; <code>false</code> otherwise
	 */
	@Override
	public boolean isPending() {
		return model.isPending();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is question.
	 *
	 * @return <code>true</code> if this message boards thread is question; <code>false</code> otherwise
	 */
	@Override
	public boolean isQuestion() {
		return model.isQuestion();
	}

	/**
	 * Returns <code>true</code> if this message boards thread is scheduled.
	 *
	 * @return <code>true</code> if this message boards thread is scheduled; <code>false</code> otherwise
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
	 * Sets the category ID of this message boards thread.
	 *
	 * @param categoryId the category ID of this message boards thread
	 */
	@Override
	public void setCategoryId(long categoryId) {
		model.setCategoryId(categoryId);
	}

	/**
	 * Sets the company ID of this message boards thread.
	 *
	 * @param companyId the company ID of this message boards thread
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this message boards thread.
	 *
	 * @param containerModelId the container model ID of this message boards thread
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this message boards thread.
	 *
	 * @param createDate the create date of this message boards thread
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards thread.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards thread
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this message boards thread.
	 *
	 * @param groupId the group ID of this message boards thread
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last post by user ID of this message boards thread.
	 *
	 * @param lastPostByUserId the last post by user ID of this message boards thread
	 */
	@Override
	public void setLastPostByUserId(long lastPostByUserId) {
		model.setLastPostByUserId(lastPostByUserId);
	}

	/**
	 * Sets the last post by user uuid of this message boards thread.
	 *
	 * @param lastPostByUserUuid the last post by user uuid of this message boards thread
	 */
	@Override
	public void setLastPostByUserUuid(String lastPostByUserUuid) {
		model.setLastPostByUserUuid(lastPostByUserUuid);
	}

	/**
	 * Sets the last post date of this message boards thread.
	 *
	 * @param lastPostDate the last post date of this message boards thread
	 */
	@Override
	public void setLastPostDate(Date lastPostDate) {
		model.setLastPostDate(lastPostDate);
	}

	/**
	 * Sets the last publish date of this message boards thread.
	 *
	 * @param lastPublishDate the last publish date of this message boards thread
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this message boards thread.
	 *
	 * @param modifiedDate the modified date of this message boards thread
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards thread.
	 *
	 * @param mvccVersion the mvcc version of this message boards thread
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this message boards thread.
	 *
	 * @param parentContainerModelId the parent container model ID of this message boards thread
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the primary key of this message boards thread.
	 *
	 * @param primaryKey the primary key of this message boards thread
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this message boards thread.
	 *
	 * @param priority the priority of this message boards thread
	 */
	@Override
	public void setPriority(double priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets whether this message boards thread is question.
	 *
	 * @param question the question of this message boards thread
	 */
	@Override
	public void setQuestion(boolean question) {
		model.setQuestion(question);
	}

	/**
	 * Sets the root message ID of this message boards thread.
	 *
	 * @param rootMessageId the root message ID of this message boards thread
	 */
	@Override
	public void setRootMessageId(long rootMessageId) {
		model.setRootMessageId(rootMessageId);
	}

	/**
	 * Sets the root message user ID of this message boards thread.
	 *
	 * @param rootMessageUserId the root message user ID of this message boards thread
	 */
	@Override
	public void setRootMessageUserId(long rootMessageUserId) {
		model.setRootMessageUserId(rootMessageUserId);
	}

	/**
	 * Sets the root message user uuid of this message boards thread.
	 *
	 * @param rootMessageUserUuid the root message user uuid of this message boards thread
	 */
	@Override
	public void setRootMessageUserUuid(String rootMessageUserUuid) {
		model.setRootMessageUserUuid(rootMessageUserUuid);
	}

	/**
	 * Sets the status of this message boards thread.
	 *
	 * @param status the status of this message boards thread
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the status by user ID of this message boards thread.
	 *
	 * @param statusByUserId the status by user ID of this message boards thread
	 */
	@Override
	public void setStatusByUserId(long statusByUserId) {
		model.setStatusByUserId(statusByUserId);
	}

	/**
	 * Sets the status by user name of this message boards thread.
	 *
	 * @param statusByUserName the status by user name of this message boards thread
	 */
	@Override
	public void setStatusByUserName(String statusByUserName) {
		model.setStatusByUserName(statusByUserName);
	}

	/**
	 * Sets the status by user uuid of this message boards thread.
	 *
	 * @param statusByUserUuid the status by user uuid of this message boards thread
	 */
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		model.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	 * Sets the status date of this message boards thread.
	 *
	 * @param statusDate the status date of this message boards thread
	 */
	@Override
	public void setStatusDate(Date statusDate) {
		model.setStatusDate(statusDate);
	}

	/**
	 * Sets the thread ID of this message boards thread.
	 *
	 * @param threadId the thread ID of this message boards thread
	 */
	@Override
	public void setThreadId(long threadId) {
		model.setThreadId(threadId);
	}

	/**
	 * Sets the title of this message boards thread.
	 *
	 * @param title the title of this message boards thread
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * Sets the user ID of this message boards thread.
	 *
	 * @param userId the user ID of this message boards thread
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards thread.
	 *
	 * @param userName the user name of this message boards thread
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards thread.
	 *
	 * @param userUuid the user uuid of this message boards thread
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards thread.
	 *
	 * @param uuid the uuid of this message boards thread
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
	public Map<String, Function<MBThread, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBThread, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBThreadWrapper wrap(MBThread mbThread) {
		return new MBThreadWrapper(mbThread);
	}

}