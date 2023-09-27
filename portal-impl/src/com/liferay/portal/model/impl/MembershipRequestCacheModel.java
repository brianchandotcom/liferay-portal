/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.MembershipRequest;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MembershipRequest in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MembershipRequestCacheModel
	implements CacheModel<MembershipRequest>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MembershipRequestCacheModel)) {
			return false;
		}

		MembershipRequestCacheModel membershipRequestCacheModel =
			(MembershipRequestCacheModel)object;

		if ((membershipRequestId ==
				membershipRequestCacheModel.membershipRequestId) &&
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MembershipRequest toEntityModel() {
		MembershipRequestImpl membershipRequestImpl =
			new MembershipRequestImpl();

		membershipRequestImpl.setMvccVersion(mvccVersion);
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
			membershipRequestImpl.setComments("");
		}
		else {
			membershipRequestImpl.setComments(comments);
		}

		if (replyComments == null) {
			membershipRequestImpl.setReplyComments("");
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

		membershipRequestImpl.resetOriginalValues();

		return membershipRequestImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		membershipRequestId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		comments = objectInput.readUTF();
		replyComments = objectInput.readUTF();
		replyDate = objectInput.readLong();

		replierUserId = objectInput.readLong();

		statusId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(membershipRequestId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		if (comments == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(comments);
		}

		if (replyComments == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(replyComments);
		}

		objectOutput.writeLong(replyDate);

		objectOutput.writeLong(replierUserId);

		objectOutput.writeLong(statusId);
	}

	public long mvccVersion;
	public long membershipRequestId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public String comments;
	public String replyComments;
	public long replyDate;
	public long replierUserId;
	public long statusId;

}