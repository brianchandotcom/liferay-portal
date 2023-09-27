/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.invitation.invite.members.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MemberRequest}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MemberRequest
 * @generated
 */
public class MemberRequestWrapper
	extends BaseModelWrapper<MemberRequest>
	implements MemberRequest, ModelWrapper<MemberRequest> {

	public MemberRequestWrapper(MemberRequest memberRequest) {
		super(memberRequest);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("memberRequestId", getMemberRequestId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("key", getKey());
		attributes.put("receiverUserId", getReceiverUserId());
		attributes.put("invitedRoleId", getInvitedRoleId());
		attributes.put("invitedTeamId", getInvitedTeamId());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long memberRequestId = (Long)attributes.get("memberRequestId");

		if (memberRequestId != null) {
			setMemberRequestId(memberRequestId);
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

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Long receiverUserId = (Long)attributes.get("receiverUserId");

		if (receiverUserId != null) {
			setReceiverUserId(receiverUserId);
		}

		Long invitedRoleId = (Long)attributes.get("invitedRoleId");

		if (invitedRoleId != null) {
			setInvitedRoleId(invitedRoleId);
		}

		Long invitedTeamId = (Long)attributes.get("invitedTeamId");

		if (invitedTeamId != null) {
			setInvitedTeamId(invitedTeamId);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public MemberRequest cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this member request.
	 *
	 * @return the company ID of this member request
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this member request.
	 *
	 * @return the create date of this member request
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this member request.
	 *
	 * @return the group ID of this member request
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the invited role ID of this member request.
	 *
	 * @return the invited role ID of this member request
	 */
	@Override
	public long getInvitedRoleId() {
		return model.getInvitedRoleId();
	}

	/**
	 * Returns the invited team ID of this member request.
	 *
	 * @return the invited team ID of this member request
	 */
	@Override
	public long getInvitedTeamId() {
		return model.getInvitedTeamId();
	}

	/**
	 * Returns the key of this member request.
	 *
	 * @return the key of this member request
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the member request ID of this member request.
	 *
	 * @return the member request ID of this member request
	 */
	@Override
	public long getMemberRequestId() {
		return model.getMemberRequestId();
	}

	/**
	 * Returns the modified date of this member request.
	 *
	 * @return the modified date of this member request
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this member request.
	 *
	 * @return the primary key of this member request
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the receiver user ID of this member request.
	 *
	 * @return the receiver user ID of this member request
	 */
	@Override
	public long getReceiverUserId() {
		return model.getReceiverUserId();
	}

	/**
	 * Returns the receiver user uuid of this member request.
	 *
	 * @return the receiver user uuid of this member request
	 */
	@Override
	public String getReceiverUserUuid() {
		return model.getReceiverUserUuid();
	}

	/**
	 * Returns the status of this member request.
	 *
	 * @return the status of this member request
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this member request.
	 *
	 * @return the user ID of this member request
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this member request.
	 *
	 * @return the user name of this member request
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this member request.
	 *
	 * @return the user uuid of this member request
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
	 * Sets the company ID of this member request.
	 *
	 * @param companyId the company ID of this member request
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this member request.
	 *
	 * @param createDate the create date of this member request
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this member request.
	 *
	 * @param groupId the group ID of this member request
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the invited role ID of this member request.
	 *
	 * @param invitedRoleId the invited role ID of this member request
	 */
	@Override
	public void setInvitedRoleId(long invitedRoleId) {
		model.setInvitedRoleId(invitedRoleId);
	}

	/**
	 * Sets the invited team ID of this member request.
	 *
	 * @param invitedTeamId the invited team ID of this member request
	 */
	@Override
	public void setInvitedTeamId(long invitedTeamId) {
		model.setInvitedTeamId(invitedTeamId);
	}

	/**
	 * Sets the key of this member request.
	 *
	 * @param key the key of this member request
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the member request ID of this member request.
	 *
	 * @param memberRequestId the member request ID of this member request
	 */
	@Override
	public void setMemberRequestId(long memberRequestId) {
		model.setMemberRequestId(memberRequestId);
	}

	/**
	 * Sets the modified date of this member request.
	 *
	 * @param modifiedDate the modified date of this member request
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this member request.
	 *
	 * @param primaryKey the primary key of this member request
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the receiver user ID of this member request.
	 *
	 * @param receiverUserId the receiver user ID of this member request
	 */
	@Override
	public void setReceiverUserId(long receiverUserId) {
		model.setReceiverUserId(receiverUserId);
	}

	/**
	 * Sets the receiver user uuid of this member request.
	 *
	 * @param receiverUserUuid the receiver user uuid of this member request
	 */
	@Override
	public void setReceiverUserUuid(String receiverUserUuid) {
		model.setReceiverUserUuid(receiverUserUuid);
	}

	/**
	 * Sets the status of this member request.
	 *
	 * @param status the status of this member request
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this member request.
	 *
	 * @param userId the user ID of this member request
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this member request.
	 *
	 * @param userName the user name of this member request
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this member request.
	 *
	 * @param userUuid the user uuid of this member request
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected MemberRequestWrapper wrap(MemberRequest memberRequest) {
		return new MemberRequestWrapper(memberRequest);
	}

}