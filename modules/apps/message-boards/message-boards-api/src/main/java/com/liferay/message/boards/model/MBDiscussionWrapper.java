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
 * This class is a wrapper for {@link MBDiscussion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBDiscussion
 * @generated
 */
public class MBDiscussionWrapper
	extends BaseModelWrapper<MBDiscussion>
	implements MBDiscussion, ModelWrapper<MBDiscussion> {

	public MBDiscussionWrapper(MBDiscussion mbDiscussion) {
		super(mbDiscussion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
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
		attributes.put("lastPublishDate", getLastPublishDate());

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

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public MBDiscussion cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this message boards discussion.
	 *
	 * @return the fully qualified class name of this message boards discussion
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this message boards discussion.
	 *
	 * @return the class name ID of this message boards discussion
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this message boards discussion.
	 *
	 * @return the class pk of this message boards discussion
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this message boards discussion.
	 *
	 * @return the company ID of this message boards discussion
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this message boards discussion.
	 *
	 * @return the create date of this message boards discussion
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards discussion.
	 *
	 * @return the ct collection ID of this message boards discussion
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the discussion ID of this message boards discussion.
	 *
	 * @return the discussion ID of this message boards discussion
	 */
	@Override
	public long getDiscussionId() {
		return model.getDiscussionId();
	}

	/**
	 * Returns the group ID of this message boards discussion.
	 *
	 * @return the group ID of this message boards discussion
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this message boards discussion.
	 *
	 * @return the last publish date of this message boards discussion
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this message boards discussion.
	 *
	 * @return the modified date of this message boards discussion
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards discussion.
	 *
	 * @return the mvcc version of this message boards discussion
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this message boards discussion.
	 *
	 * @return the primary key of this message boards discussion
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the thread ID of this message boards discussion.
	 *
	 * @return the thread ID of this message boards discussion
	 */
	@Override
	public long getThreadId() {
		return model.getThreadId();
	}

	/**
	 * Returns the user ID of this message boards discussion.
	 *
	 * @return the user ID of this message boards discussion
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards discussion.
	 *
	 * @return the user name of this message boards discussion
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards discussion.
	 *
	 * @return the user uuid of this message boards discussion
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards discussion.
	 *
	 * @return the uuid of this message boards discussion
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this message boards discussion.
	 *
	 * @param classNameId the class name ID of this message boards discussion
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this message boards discussion.
	 *
	 * @param classPK the class pk of this message boards discussion
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this message boards discussion.
	 *
	 * @param companyId the company ID of this message boards discussion
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this message boards discussion.
	 *
	 * @param createDate the create date of this message boards discussion
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards discussion.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards discussion
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the discussion ID of this message boards discussion.
	 *
	 * @param discussionId the discussion ID of this message boards discussion
	 */
	@Override
	public void setDiscussionId(long discussionId) {
		model.setDiscussionId(discussionId);
	}

	/**
	 * Sets the group ID of this message boards discussion.
	 *
	 * @param groupId the group ID of this message boards discussion
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this message boards discussion.
	 *
	 * @param lastPublishDate the last publish date of this message boards discussion
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this message boards discussion.
	 *
	 * @param modifiedDate the modified date of this message boards discussion
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards discussion.
	 *
	 * @param mvccVersion the mvcc version of this message boards discussion
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this message boards discussion.
	 *
	 * @param primaryKey the primary key of this message boards discussion
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the thread ID of this message boards discussion.
	 *
	 * @param threadId the thread ID of this message boards discussion
	 */
	@Override
	public void setThreadId(long threadId) {
		model.setThreadId(threadId);
	}

	/**
	 * Sets the user ID of this message boards discussion.
	 *
	 * @param userId the user ID of this message boards discussion
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards discussion.
	 *
	 * @param userName the user name of this message boards discussion
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards discussion.
	 *
	 * @param userUuid the user uuid of this message boards discussion
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards discussion.
	 *
	 * @param uuid the uuid of this message boards discussion
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
	public Map<String, Function<MBDiscussion, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBDiscussion, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBDiscussionWrapper wrap(MBDiscussion mbDiscussion) {
		return new MBDiscussionWrapper(mbDiscussion);
	}

}