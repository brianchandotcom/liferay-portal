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

package com.liferay.knowledge.base.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KBComment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBComment
 * @generated
 */
public class KBCommentWrapper
	extends BaseModelWrapper<KBComment>
	implements KBComment, ModelWrapper<KBComment> {

	public KBCommentWrapper(KBComment kbComment) {
		super(kbComment);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("kbCommentId", getKbCommentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("content", getContent());
		attributes.put("userRating", getUserRating());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long kbCommentId = (Long)attributes.get("kbCommentId");

		if (kbCommentId != null) {
			setKbCommentId(kbCommentId);
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

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}

		Integer userRating = (Integer)attributes.get("userRating");

		if (userRating != null) {
			setUserRating(userRating);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the fully qualified class name of this kb comment.
	 *
	 * @return the fully qualified class name of this kb comment
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this kb comment.
	 *
	 * @return the class name ID of this kb comment
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this kb comment.
	 *
	 * @return the class pk of this kb comment
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this kb comment.
	 *
	 * @return the company ID of this kb comment
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content of this kb comment.
	 *
	 * @return the content of this kb comment
	 */
	@Override
	public String getContent() {
		return model.getContent();
	}

	/**
	 * Returns the create date of this kb comment.
	 *
	 * @return the create date of this kb comment
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this kb comment.
	 *
	 * @return the group ID of this kb comment
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kb comment ID of this kb comment.
	 *
	 * @return the kb comment ID of this kb comment
	 */
	@Override
	public long getKbCommentId() {
		return model.getKbCommentId();
	}

	/**
	 * Returns the last publish date of this kb comment.
	 *
	 * @return the last publish date of this kb comment
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this kb comment.
	 *
	 * @return the modified date of this kb comment
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kb comment.
	 *
	 * @return the mvcc version of this kb comment
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this kb comment.
	 *
	 * @return the primary key of this kb comment
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this kb comment.
	 *
	 * @return the status of this kb comment
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this kb comment.
	 *
	 * @return the user ID of this kb comment
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kb comment.
	 *
	 * @return the user name of this kb comment
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user rating of this kb comment.
	 *
	 * @return the user rating of this kb comment
	 */
	@Override
	public int getUserRating() {
		return model.getUserRating();
	}

	/**
	 * Returns the user uuid of this kb comment.
	 *
	 * @return the user uuid of this kb comment
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this kb comment.
	 *
	 * @return the uuid of this kb comment
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kb comment model instance should use the <code>KBComment</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this kb comment.
	 *
	 * @param classNameId the class name ID of this kb comment
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this kb comment.
	 *
	 * @param classPK the class pk of this kb comment
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this kb comment.
	 *
	 * @param companyId the company ID of this kb comment
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content of this kb comment.
	 *
	 * @param content the content of this kb comment
	 */
	@Override
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * Sets the create date of this kb comment.
	 *
	 * @param createDate the create date of this kb comment
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this kb comment.
	 *
	 * @param groupId the group ID of this kb comment
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kb comment ID of this kb comment.
	 *
	 * @param kbCommentId the kb comment ID of this kb comment
	 */
	@Override
	public void setKbCommentId(long kbCommentId) {
		model.setKbCommentId(kbCommentId);
	}

	/**
	 * Sets the last publish date of this kb comment.
	 *
	 * @param lastPublishDate the last publish date of this kb comment
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this kb comment.
	 *
	 * @param modifiedDate the modified date of this kb comment
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kb comment.
	 *
	 * @param mvccVersion the mvcc version of this kb comment
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this kb comment.
	 *
	 * @param primaryKey the primary key of this kb comment
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this kb comment.
	 *
	 * @param status the status of this kb comment
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this kb comment.
	 *
	 * @param userId the user ID of this kb comment
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kb comment.
	 *
	 * @param userName the user name of this kb comment
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user rating of this kb comment.
	 *
	 * @param userRating the user rating of this kb comment
	 */
	@Override
	public void setUserRating(int userRating) {
		model.setUserRating(userRating);
	}

	/**
	 * Sets the user uuid of this kb comment.
	 *
	 * @param userUuid the user uuid of this kb comment
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this kb comment.
	 *
	 * @param uuid the uuid of this kb comment
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected KBCommentWrapper wrap(KBComment kbComment) {
		return new KBCommentWrapper(kbComment);
	}

}