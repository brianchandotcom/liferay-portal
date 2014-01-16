/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
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
public class MembershipRequestCacheModel implements CacheModel<MembershipRequest>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{membershipRequestId=");
		sb.append(membershipRequestId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
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
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MembershipRequest toEntityModel() {
		MembershipRequestImpl membershipRequestImpl = new MembershipRequestImpl();

		membershipRequestImpl.setMembershipRequestId(membershipRequestId);
		membershipRequestImpl.setGroupId(groupId);
		membershipRequestImpl.setCompanyId(companyId);
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
		membershipRequestImpl.setMvccVersion(mvccVersion);

		membershipRequestImpl.resetOriginalValues();

		return membershipRequestImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		membershipRequestId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		comments = objectInput.readUTF();
		replyComments = objectInput.readUTF();
		replyDate = objectInput.readLong();
		replierUserId = objectInput.readLong();
		statusId = objectInput.readInt();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(membershipRequestId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
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
		objectOutput.writeInt(statusId);
		objectOutput.writeLong(mvccVersion);
	}

	public long membershipRequestId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public String comments;
	public String replyComments;
	public long replyDate;
	public long replierUserId;
	public int statusId;
	public long mvccVersion;
}