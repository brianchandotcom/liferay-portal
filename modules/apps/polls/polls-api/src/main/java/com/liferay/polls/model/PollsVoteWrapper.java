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

package com.liferay.polls.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PollsVote}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsVote
 * @generated
 */
@ProviderType
public class PollsVoteWrapper implements PollsVote, ModelWrapper<PollsVote> {
	public PollsVoteWrapper(PollsVote pollsVote) {
		_pollsVote = pollsVote;
	}

	@Override
	public Class<?> getModelClass() {
		return PollsVote.class;
	}

	@Override
	public String getModelClassName() {
		return PollsVote.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("voteId", getVoteId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("questionId", getQuestionId());
		attributes.put("choiceId", getChoiceId());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("voteDate", getVoteDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long voteId = (Long)attributes.get("voteId");

		if (voteId != null) {
			setVoteId(voteId);
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

		Long questionId = (Long)attributes.get("questionId");

		if (questionId != null) {
			setQuestionId(questionId);
		}

		Long choiceId = (Long)attributes.get("choiceId");

		if (choiceId != null) {
			setChoiceId(choiceId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Date voteDate = (Date)attributes.get("voteDate");

		if (voteDate != null) {
			setVoteDate(voteDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new PollsVoteWrapper((PollsVote)_pollsVote.clone());
	}

	@Override
	public int compareTo(com.liferay.polls.model.PollsVote pollsVote) {
		return _pollsVote.compareTo(pollsVote);
	}

	@Override
	public com.liferay.polls.model.PollsChoice getChoice()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _pollsVote.getChoice();
	}

	/**
	* Returns the choice ID of this polls vote.
	*
	* @return the choice ID of this polls vote
	*/
	@Override
	public long getChoiceId() {
		return _pollsVote.getChoiceId();
	}

	/**
	* Returns the company ID of this polls vote.
	*
	* @return the company ID of this polls vote
	*/
	@Override
	public long getCompanyId() {
		return _pollsVote.getCompanyId();
	}

	/**
	* Returns the create date of this polls vote.
	*
	* @return the create date of this polls vote
	*/
	@Override
	public Date getCreateDate() {
		return _pollsVote.getCreateDate();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _pollsVote.getExpandoBridge();
	}

	/**
	* Returns the group ID of this polls vote.
	*
	* @return the group ID of this polls vote
	*/
	@Override
	public long getGroupId() {
		return _pollsVote.getGroupId();
	}

	/**
	* Returns the last publish date of this polls vote.
	*
	* @return the last publish date of this polls vote
	*/
	@Override
	public Date getLastPublishDate() {
		return _pollsVote.getLastPublishDate();
	}

	/**
	* Returns the modified date of this polls vote.
	*
	* @return the modified date of this polls vote
	*/
	@Override
	public Date getModifiedDate() {
		return _pollsVote.getModifiedDate();
	}

	/**
	* Returns the primary key of this polls vote.
	*
	* @return the primary key of this polls vote
	*/
	@Override
	public long getPrimaryKey() {
		return _pollsVote.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _pollsVote.getPrimaryKeyObj();
	}

	/**
	* Returns the question ID of this polls vote.
	*
	* @return the question ID of this polls vote
	*/
	@Override
	public long getQuestionId() {
		return _pollsVote.getQuestionId();
	}

	/**
	* Returns the user ID of this polls vote.
	*
	* @return the user ID of this polls vote
	*/
	@Override
	public long getUserId() {
		return _pollsVote.getUserId();
	}

	/**
	* Returns the user name of this polls vote.
	*
	* @return the user name of this polls vote
	*/
	@Override
	public java.lang.String getUserName() {
		return _pollsVote.getUserName();
	}

	/**
	* Returns the user uuid of this polls vote.
	*
	* @return the user uuid of this polls vote
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _pollsVote.getUserUuid();
	}

	/**
	* Returns the uuid of this polls vote.
	*
	* @return the uuid of this polls vote
	*/
	@Override
	public java.lang.String getUuid() {
		return _pollsVote.getUuid();
	}

	/**
	* Returns the vote date of this polls vote.
	*
	* @return the vote date of this polls vote
	*/
	@Override
	public Date getVoteDate() {
		return _pollsVote.getVoteDate();
	}

	/**
	* Returns the vote ID of this polls vote.
	*
	* @return the vote ID of this polls vote
	*/
	@Override
	public long getVoteId() {
		return _pollsVote.getVoteId();
	}

	@Override
	public int hashCode() {
		return _pollsVote.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _pollsVote.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _pollsVote.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _pollsVote.isNew();
	}

	@Override
	public void persist() {
		_pollsVote.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_pollsVote.setCachedModel(cachedModel);
	}

	/**
	* Sets the choice ID of this polls vote.
	*
	* @param choiceId the choice ID of this polls vote
	*/
	@Override
	public void setChoiceId(long choiceId) {
		_pollsVote.setChoiceId(choiceId);
	}

	/**
	* Sets the company ID of this polls vote.
	*
	* @param companyId the company ID of this polls vote
	*/
	@Override
	public void setCompanyId(long companyId) {
		_pollsVote.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this polls vote.
	*
	* @param createDate the create date of this polls vote
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_pollsVote.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_pollsVote.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_pollsVote.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_pollsVote.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this polls vote.
	*
	* @param groupId the group ID of this polls vote
	*/
	@Override
	public void setGroupId(long groupId) {
		_pollsVote.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this polls vote.
	*
	* @param lastPublishDate the last publish date of this polls vote
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_pollsVote.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this polls vote.
	*
	* @param modifiedDate the modified date of this polls vote
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_pollsVote.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_pollsVote.setNew(n);
	}

	/**
	* Sets the primary key of this polls vote.
	*
	* @param primaryKey the primary key of this polls vote
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_pollsVote.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_pollsVote.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the question ID of this polls vote.
	*
	* @param questionId the question ID of this polls vote
	*/
	@Override
	public void setQuestionId(long questionId) {
		_pollsVote.setQuestionId(questionId);
	}

	/**
	* Sets the user ID of this polls vote.
	*
	* @param userId the user ID of this polls vote
	*/
	@Override
	public void setUserId(long userId) {
		_pollsVote.setUserId(userId);
	}

	/**
	* Sets the user name of this polls vote.
	*
	* @param userName the user name of this polls vote
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_pollsVote.setUserName(userName);
	}

	/**
	* Sets the user uuid of this polls vote.
	*
	* @param userUuid the user uuid of this polls vote
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_pollsVote.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this polls vote.
	*
	* @param uuid the uuid of this polls vote
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_pollsVote.setUuid(uuid);
	}

	/**
	* Sets the vote date of this polls vote.
	*
	* @param voteDate the vote date of this polls vote
	*/
	@Override
	public void setVoteDate(Date voteDate) {
		_pollsVote.setVoteDate(voteDate);
	}

	/**
	* Sets the vote ID of this polls vote.
	*
	* @param voteId the vote ID of this polls vote
	*/
	@Override
	public void setVoteId(long voteId) {
		_pollsVote.setVoteId(voteId);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.polls.model.PollsVote> toCacheModel() {
		return _pollsVote.toCacheModel();
	}

	@Override
	public com.liferay.polls.model.PollsVote toEscapedModel() {
		return new PollsVoteWrapper(_pollsVote.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _pollsVote.toString();
	}

	@Override
	public com.liferay.polls.model.PollsVote toUnescapedModel() {
		return new PollsVoteWrapper(_pollsVote.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _pollsVote.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PollsVoteWrapper)) {
			return false;
		}

		PollsVoteWrapper pollsVoteWrapper = (PollsVoteWrapper)obj;

		if (Validator.equals(_pollsVote, pollsVoteWrapper._pollsVote)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _pollsVote.getStagedModelType();
	}

	@Override
	public PollsVote getWrappedModel() {
		return _pollsVote;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _pollsVote.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _pollsVote.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_pollsVote.resetOriginalValues();
	}

	private final PollsVote _pollsVote;
}