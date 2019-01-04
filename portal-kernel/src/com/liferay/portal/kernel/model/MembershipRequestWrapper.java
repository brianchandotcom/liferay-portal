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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MembershipRequest}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequest
 * @generated
 */
@ProviderType
public class MembershipRequestWrapper extends BaseModelWrapper<MembershipRequest>
	implements MembershipRequest, ModelWrapper<MembershipRequest> {
	public MembershipRequestWrapper(MembershipRequest membershipRequest) {
		super(membershipRequest);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("membershipRequestId", getMembershipRequestId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("comments", getComments());
		attributes.put("replyComments", getReplyComments());
		attributes.put("replyDate", getReplyDate());
		attributes.put("replierUserId", getReplierUserId());
		attributes.put("statusId", getStatusId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long membershipRequestId = (Long)attributes.get("membershipRequestId");

		if (membershipRequestId != null) {
			setMembershipRequestId(membershipRequestId);
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

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		String replyComments = (String)attributes.get("replyComments");

		if (replyComments != null) {
			setReplyComments(replyComments);
		}

		Date replyDate = (Date)attributes.get("replyDate");

		if (replyDate != null) {
			setReplyDate(replyDate);
		}

		Long replierUserId = (Long)attributes.get("replierUserId");

		if (replierUserId != null) {
			setReplierUserId(replierUserId);
		}

		Long statusId = (Long)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}
	}

	/**
	* Returns the comments of this membership request.
	*
	* @return the comments of this membership request
	*/
	@Override
	public String getComments() {
		return model.getComments();
	}

	/**
	* Returns the company ID of this membership request.
	*
	* @return the company ID of this membership request
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this membership request.
	*
	* @return the create date of this membership request
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the group ID of this membership request.
	*
	* @return the group ID of this membership request
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the membership request ID of this membership request.
	*
	* @return the membership request ID of this membership request
	*/
	@Override
	public long getMembershipRequestId() {
		return model.getMembershipRequestId();
	}

	/**
	* Returns the mvcc version of this membership request.
	*
	* @return the mvcc version of this membership request
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this membership request.
	*
	* @return the primary key of this membership request
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the replier user ID of this membership request.
	*
	* @return the replier user ID of this membership request
	*/
	@Override
	public long getReplierUserId() {
		return model.getReplierUserId();
	}

	/**
	* Returns the replier user uuid of this membership request.
	*
	* @return the replier user uuid of this membership request
	*/
	@Override
	public String getReplierUserUuid() {
		return model.getReplierUserUuid();
	}

	/**
	* Returns the reply comments of this membership request.
	*
	* @return the reply comments of this membership request
	*/
	@Override
	public String getReplyComments() {
		return model.getReplyComments();
	}

	/**
	* Returns the reply date of this membership request.
	*
	* @return the reply date of this membership request
	*/
	@Override
	public Date getReplyDate() {
		return model.getReplyDate();
	}

	/**
	* Returns the status ID of this membership request.
	*
	* @return the status ID of this membership request
	*/
	@Override
	public long getStatusId() {
		return model.getStatusId();
	}

	/**
	* Returns the user ID of this membership request.
	*
	* @return the user ID of this membership request
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user uuid of this membership request.
	*
	* @return the user uuid of this membership request
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the comments of this membership request.
	*
	* @param comments the comments of this membership request
	*/
	@Override
	public void setComments(String comments) {
		model.setComments(comments);
	}

	/**
	* Sets the company ID of this membership request.
	*
	* @param companyId the company ID of this membership request
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this membership request.
	*
	* @param createDate the create date of this membership request
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the group ID of this membership request.
	*
	* @param groupId the group ID of this membership request
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the membership request ID of this membership request.
	*
	* @param membershipRequestId the membership request ID of this membership request
	*/
	@Override
	public void setMembershipRequestId(long membershipRequestId) {
		model.setMembershipRequestId(membershipRequestId);
	}

	/**
	* Sets the mvcc version of this membership request.
	*
	* @param mvccVersion the mvcc version of this membership request
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this membership request.
	*
	* @param primaryKey the primary key of this membership request
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the replier user ID of this membership request.
	*
	* @param replierUserId the replier user ID of this membership request
	*/
	@Override
	public void setReplierUserId(long replierUserId) {
		model.setReplierUserId(replierUserId);
	}

	/**
	* Sets the replier user uuid of this membership request.
	*
	* @param replierUserUuid the replier user uuid of this membership request
	*/
	@Override
	public void setReplierUserUuid(String replierUserUuid) {
		model.setReplierUserUuid(replierUserUuid);
	}

	/**
	* Sets the reply comments of this membership request.
	*
	* @param replyComments the reply comments of this membership request
	*/
	@Override
	public void setReplyComments(String replyComments) {
		model.setReplyComments(replyComments);
	}

	/**
	* Sets the reply date of this membership request.
	*
	* @param replyDate the reply date of this membership request
	*/
	@Override
	public void setReplyDate(Date replyDate) {
		model.setReplyDate(replyDate);
	}

	/**
	* Sets the status ID of this membership request.
	*
	* @param statusId the status ID of this membership request
	*/
	@Override
	public void setStatusId(long statusId) {
		model.setStatusId(statusId);
	}

	/**
	* Sets the user ID of this membership request.
	*
	* @param userId the user ID of this membership request
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user uuid of this membership request.
	*
	* @param userUuid the user uuid of this membership request
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected MembershipRequestWrapper wrap(MembershipRequest membershipRequest) {
		return new MembershipRequestWrapper(membershipRequest);
	}
}