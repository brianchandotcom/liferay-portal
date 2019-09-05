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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.MembershipRequestServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MembershipRequestSoap implements Serializable {

	public static MembershipRequestSoap toSoapModel(MembershipRequest model) {
		MembershipRequestSoap soapModel = new MembershipRequestSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setMembershipRequestId(model.getMembershipRequestId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setComments(model.getComments());
		soapModel.setReplyComments(model.getReplyComments());
		soapModel.setReplyDate(model.getReplyDate());
		soapModel.setReplierUserId(model.getReplierUserId());
		soapModel.setStatusId(model.getStatusId());

		return soapModel;
	}

	public static MembershipRequestSoap[] toSoapModels(
		MembershipRequest[] models) {

		MembershipRequestSoap[] soapModels =
			new MembershipRequestSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MembershipRequestSoap[][] toSoapModels(
		MembershipRequest[][] models) {

		MembershipRequestSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new MembershipRequestSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MembershipRequestSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MembershipRequestSoap[] toSoapModels(
		List<MembershipRequest> models) {

		List<MembershipRequestSoap> soapModels =
			new ArrayList<MembershipRequestSoap>(models.size());

		for (MembershipRequest model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MembershipRequestSoap[soapModels.size()]);
	}

	public MembershipRequestSoap() {
	}

	public long getPrimaryKey() {
		return _membershipRequestId;
	}

	public void setPrimaryKey(long pk) {
		setMembershipRequestId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getMembershipRequestId() {
		return _membershipRequestId;
	}

	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequestId = membershipRequestId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getReplyComments() {
		return _replyComments;
	}

	public void setReplyComments(String replyComments) {
		_replyComments = replyComments;
	}

	public Date getReplyDate() {
		return _replyDate;
	}

	public void setReplyDate(Date replyDate) {
		_replyDate = replyDate;
	}

	public long getReplierUserId() {
		return _replierUserId;
	}

	public void setReplierUserId(long replierUserId) {
		_replierUserId = replierUserId;
	}

	public long getStatusId() {
		return _statusId;
	}

	public void setStatusId(long statusId) {
		_statusId = statusId;
	}

	private long _mvccVersion;
	private long _membershipRequestId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private String _comments;
	private String _replyComments;
	private Date _replyDate;
	private long _replierUserId;
	private long _statusId;

}