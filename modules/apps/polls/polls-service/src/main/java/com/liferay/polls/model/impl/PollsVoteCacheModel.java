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

package com.liferay.polls.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.polls.model.PollsVote;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PollsVote in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PollsVoteCacheModel
	implements CacheModel<PollsVote>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PollsVoteCacheModel)) {
			return false;
		}

		PollsVoteCacheModel pollsVoteCacheModel = (PollsVoteCacheModel)obj;

		if ((voteId == pollsVoteCacheModel.voteId) &&
			(mvccVersion == pollsVoteCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, voteId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", voteId=");
		sb.append(voteId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", choiceId=");
		sb.append(choiceId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", voteDate=");
		sb.append(voteDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PollsVote toEntityModel() {
		PollsVoteImpl pollsVoteImpl = new PollsVoteImpl();

		pollsVoteImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			pollsVoteImpl.setUuid("");
		}
		else {
			pollsVoteImpl.setUuid(uuid);
		}

		pollsVoteImpl.setVoteId(voteId);
		pollsVoteImpl.setGroupId(groupId);
		pollsVoteImpl.setCompanyId(companyId);
		pollsVoteImpl.setUserId(userId);

		if (userName == null) {
			pollsVoteImpl.setUserName("");
		}
		else {
			pollsVoteImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			pollsVoteImpl.setCreateDate(null);
		}
		else {
			pollsVoteImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			pollsVoteImpl.setModifiedDate(null);
		}
		else {
			pollsVoteImpl.setModifiedDate(new Date(modifiedDate));
		}

		pollsVoteImpl.setQuestionId(questionId);
		pollsVoteImpl.setChoiceId(choiceId);

		if (lastPublishDate == Long.MIN_VALUE) {
			pollsVoteImpl.setLastPublishDate(null);
		}
		else {
			pollsVoteImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		if (voteDate == Long.MIN_VALUE) {
			pollsVoteImpl.setVoteDate(null);
		}
		else {
			pollsVoteImpl.setVoteDate(new Date(voteDate));
		}

		pollsVoteImpl.resetOriginalValues();

		return pollsVoteImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		voteId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		questionId = objectInput.readLong();

		choiceId = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
		voteDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(voteId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(questionId);

		objectOutput.writeLong(choiceId);
		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(voteDate);
	}

	public long mvccVersion;
	public String uuid;
	public long voteId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long questionId;
	public long choiceId;
	public long lastPublishDate;
	public long voteDate;

}