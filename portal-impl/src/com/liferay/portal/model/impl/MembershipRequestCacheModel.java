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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.MembershipRequest;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MembershipRequest in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequest
 * @generated
 */
@ProviderType
public class MembershipRequestCacheModel implements CacheModel<MembershipRequest>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MembershipRequestCacheModel)) {
			return false;
		}

		MembershipRequestCacheModel membershipRequestCacheModel = (MembershipRequestCacheModel)obj;

		if ((membershipRequestId == membershipRequestCacheModel.membershipRequestId) &&
				(mvccVersion == membershipRequestCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, membershipRequestId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", membershipRequestId=");
		sb.append(membershipRequestId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", comments=");
		sb.append(comments);
		sb.append(", replyComments=");
		sb.append(replyComments);
		sb.append(", replyDate=");
		sb.append(replyDate);
		sb.append(", replierUserId=");
		sb.append(replierUserId);
		sb.append(", statusId=");
		sb.append(statusId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MembershipRequest toEntityModel() {
		MembershipRequestImpl membershipRequestImpl = new MembershipRequestImpl();

		membershipRequestImpl.setMvccVersion(mvccVersion);
		membershipRequestImpl.setMembershipRequestId(membershipRequestId);
		membershipRequestImpl.setGroupId(groupId);
		membershipRequestImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			membershipRequestImpl.setCreateDate(null);
		}
		else {
			membershipRequestImpl.setCreateDate(new Date(createDate));
		}

		if (comments == null) {
			membershipRequestImpl.setComments(StringPool.BLANK);
		}
		else {
			membershipRequestImpl.setComments(comments);
		}

		if (replyComments == null) {
			membershipRequestImpl.setReplyComments(StringPool.BLANK);
		}
		else {
			membershipRequestImpl.setReplyComments(replyComments);
		}

		if (replyDate == Long.MIN_VALUE) {
			membershipRequestImpl.setReplyDate(null);
		}
		else {
			membershipRequestImpl.setReplyDate(new Date(replyDate));
		}

		membershipRequestImpl.setReplierUserId(replierUserId);
		membershipRequestImpl.setStatusId(statusId);
		membershipRequestImpl.setCompanyId(companyId);

		membershipRequestImpl.resetOriginalValues();

		return membershipRequestImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		membershipRequestId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		comments = objectInput.readUTF();
		replyComments = objectInput.readUTF();
		replyDate = objectInput.readLong();
		replierUserId = objectInput.readLong();
		statusId = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);
		objectOutput.writeLong(membershipRequestId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		if (comments == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(comments);
		}

		if (replyComments == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(replyComments);
		}

		objectOutput.writeLong(replyDate);
		objectOutput.writeLong(replierUserId);
		objectOutput.writeLong(statusId);
		objectOutput.writeLong(companyId);
	}

	public long mvccVersion;
	public long membershipRequestId;
	public long groupId;
	public long userId;
	public long createDate;
	public String comments;
	public String replyComments;
	public long replyDate;
	public long replierUserId;
	public long statusId;
	public long companyId;
}