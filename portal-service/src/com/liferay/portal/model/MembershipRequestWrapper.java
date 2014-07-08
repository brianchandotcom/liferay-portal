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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

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
public class MembershipRequestWrapper implements MembershipRequest,
	ModelWrapper<MembershipRequest> {
	public MembershipRequestWrapper(MembershipRequest membershipRequest) {
		_membershipRequest = membershipRequest;
	}

	@Override
	public Class<?> getModelClass() {
		return MembershipRequest.class;
	}

	@Override
	public String getModelClassName() {
		return MembershipRequest.class.getName();
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

		Integer statusId = (Integer)attributes.get("statusId");

		if (statusId != null) {
			setStatusId(statusId);
		}
	}

	/**
	* Returns the primary key of this membership request.
	*
	* @return the primary key of this membership request
	*/
	@Override
	public long getPrimaryKey() {
		return _membershipRequest.getPrimaryKey();
	}

	/**
	* Sets the primary key of this membership request.
	*
	* @param primaryKey the primary key of this membership request
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_membershipRequest.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this membership request.
	*
	* @return the mvcc version of this membership request
	*/
	@Override
	public long getMvccVersion() {
		return _membershipRequest.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this membership request.
	*
	* @param mvccVersion the mvcc version of this membership request
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_membershipRequest.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the membership request ID of this membership request.
	*
	* @return the membership request ID of this membership request
	*/
	@Override
	public long getMembershipRequestId() {
		return _membershipRequest.getMembershipRequestId();
	}

	/**
	* Sets the membership request ID of this membership request.
	*
	* @param membershipRequestId the membership request ID of this membership request
	*/
	@Override
	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequest.setMembershipRequestId(membershipRequestId);
	}

	/**
	* Returns the group ID of this membership request.
	*
	* @return the group ID of this membership request
	*/
	@Override
	public long getGroupId() {
		return _membershipRequest.getGroupId();
	}

	/**
	* Sets the group ID of this membership request.
	*
	* @param groupId the group ID of this membership request
	*/
	@Override
	public void setGroupId(long groupId) {
		_membershipRequest.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this membership request.
	*
	* @return the company ID of this membership request
	*/
	@Override
	public long getCompanyId() {
		return _membershipRequest.getCompanyId();
	}

	/**
	* Sets the company ID of this membership request.
	*
	* @param companyId the company ID of this membership request
	*/
	@Override
	public void setCompanyId(long companyId) {
		_membershipRequest.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this membership request.
	*
	* @return the user ID of this membership request
	*/
	@Override
	public long getUserId() {
		return _membershipRequest.getUserId();
	}

	/**
	* Sets the user ID of this membership request.
	*
	* @param userId the user ID of this membership request
	*/
	@Override
	public void setUserId(long userId) {
		_membershipRequest.setUserId(userId);
	}

	/**
	* Returns the user uuid of this membership request.
	*
	* @return the user uuid of this membership request
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _membershipRequest.getUserUuid();
	}

	/**
	* Sets the user uuid of this membership request.
	*
	* @param userUuid the user uuid of this membership request
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_membershipRequest.setUserUuid(userUuid);
	}

	/**
	* Returns the create date of this membership request.
	*
	* @return the create date of this membership request
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _membershipRequest.getCreateDate();
	}

	/**
	* Sets the create date of this membership request.
	*
	* @param createDate the create date of this membership request
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_membershipRequest.setCreateDate(createDate);
	}

	/**
	* Returns the comments of this membership request.
	*
	* @return the comments of this membership request
	*/
	@Override
	public java.lang.String getComments() {
		return _membershipRequest.getComments();
	}

	/**
	* Sets the comments of this membership request.
	*
	* @param comments the comments of this membership request
	*/
	@Override
	public void setComments(java.lang.String comments) {
		_membershipRequest.setComments(comments);
	}

	/**
	* Returns the reply comments of this membership request.
	*
	* @return the reply comments of this membership request
	*/
	@Override
	public java.lang.String getReplyComments() {
		return _membershipRequest.getReplyComments();
	}

	/**
	* Sets the reply comments of this membership request.
	*
	* @param replyComments the reply comments of this membership request
	*/
	@Override
	public void setReplyComments(java.lang.String replyComments) {
		_membershipRequest.setReplyComments(replyComments);
	}

	/**
	* Returns the reply date of this membership request.
	*
	* @return the reply date of this membership request
	*/
	@Override
	public java.util.Date getReplyDate() {
		return _membershipRequest.getReplyDate();
	}

	/**
	* Sets the reply date of this membership request.
	*
	* @param replyDate the reply date of this membership request
	*/
	@Override
	public void setReplyDate(java.util.Date replyDate) {
		_membershipRequest.setReplyDate(replyDate);
	}

	/**
	* Returns the replier user ID of this membership request.
	*
	* @return the replier user ID of this membership request
	*/
	@Override
	public long getReplierUserId() {
		return _membershipRequest.getReplierUserId();
	}

	/**
	* Sets the replier user ID of this membership request.
	*
	* @param replierUserId the replier user ID of this membership request
	*/
	@Override
	public void setReplierUserId(long replierUserId) {
		_membershipRequest.setReplierUserId(replierUserId);
	}

	/**
	* Returns the replier user uuid of this membership request.
	*
	* @return the replier user uuid of this membership request
	*/
	@Override
	public java.lang.String getReplierUserUuid() {
		return _membershipRequest.getReplierUserUuid();
	}

	/**
	* Sets the replier user uuid of this membership request.
	*
	* @param replierUserUuid the replier user uuid of this membership request
	*/
	@Override
	public void setReplierUserUuid(java.lang.String replierUserUuid) {
		_membershipRequest.setReplierUserUuid(replierUserUuid);
	}

	/**
	* Returns the status ID of this membership request.
	*
	* @return the status ID of this membership request
	*/
	@Override
	public int getStatusId() {
		return _membershipRequest.getStatusId();
	}

	/**
	* Sets the status ID of this membership request.
	*
	* @param statusId the status ID of this membership request
	*/
	@Override
	public void setStatusId(int statusId) {
		_membershipRequest.setStatusId(statusId);
	}

	@Override
	public boolean isNew() {
		return _membershipRequest.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_membershipRequest.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _membershipRequest.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_membershipRequest.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _membershipRequest.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _membershipRequest.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_membershipRequest.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _membershipRequest.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_membershipRequest.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_membershipRequest.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_membershipRequest.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MembershipRequestWrapper((MembershipRequest)_membershipRequest.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequest.compareTo(membershipRequest);
	}

	@Override
	public int hashCode() {
		return _membershipRequest.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.MembershipRequest> toCacheModel() {
		return _membershipRequest.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.MembershipRequest toEscapedModel() {
		return new MembershipRequestWrapper(_membershipRequest.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.MembershipRequest toUnescapedModel() {
		return new MembershipRequestWrapper(_membershipRequest.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _membershipRequest.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _membershipRequest.toXmlString();
	}

	@Override
	public void persist() {
		_membershipRequest.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MembershipRequestWrapper)) {
			return false;
		}

		MembershipRequestWrapper membershipRequestWrapper = (MembershipRequestWrapper)obj;

		if (Validator.equals(_membershipRequest,
					membershipRequestWrapper._membershipRequest)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public MembershipRequest getWrappedMembershipRequest() {
		return _membershipRequest;
	}

	@Override
	public MembershipRequest getWrappedModel() {
		return _membershipRequest;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _membershipRequest.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _membershipRequest.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_membershipRequest.resetOriginalValues();
	}

	private MembershipRequest _membershipRequest;
}