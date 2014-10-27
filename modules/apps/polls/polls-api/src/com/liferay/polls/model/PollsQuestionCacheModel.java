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

import com.liferay.polls.model.PollsQuestion;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PollsQuestion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PollsQuestion
 * @generated
 */
@ProviderType
public class PollsQuestionCacheModel implements CacheModel<PollsQuestion>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", questionId=");
		sb.append(questionId);
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
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastVoteDate=");
		sb.append(lastVoteDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PollsQuestion toEntityModel() {
		PollsQuestion pollsQuestion = new PollsQuestion();

		if (uuid == null) {
			pollsQuestion.setUuid(StringPool.BLANK);
		}
		else {
			pollsQuestion.setUuid(uuid);
		}

		pollsQuestion.setQuestionId(questionId);
		pollsQuestion.setGroupId(groupId);
		pollsQuestion.setCompanyId(companyId);
		pollsQuestion.setUserId(userId);

		if (userName == null) {
			pollsQuestion.setUserName(StringPool.BLANK);
		}
		else {
			pollsQuestion.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			pollsQuestion.setCreateDate(null);
		}
		else {
			pollsQuestion.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			pollsQuestion.setModifiedDate(null);
		}
		else {
			pollsQuestion.setModifiedDate(new Date(modifiedDate));
		}

		if (title == null) {
			pollsQuestion.setTitle(StringPool.BLANK);
		}
		else {
			pollsQuestion.setTitle(title);
		}

		if (description == null) {
			pollsQuestion.setDescription(StringPool.BLANK);
		}
		else {
			pollsQuestion.setDescription(description);
		}

		if (expirationDate == Long.MIN_VALUE) {
			pollsQuestion.setExpirationDate(null);
		}
		else {
			pollsQuestion.setExpirationDate(new Date(expirationDate));
		}

		if (lastVoteDate == Long.MIN_VALUE) {
			pollsQuestion.setLastVoteDate(null);
		}
		else {
			pollsQuestion.setLastVoteDate(new Date(lastVoteDate));
		}

		pollsQuestion.resetOriginalValues();

		return pollsQuestion;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		questionId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		lastVoteDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(questionId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastVoteDate);
	}

	public String uuid;
	public long questionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String title;
	public String description;
	public long expirationDate;
	public long lastVoteDate;
}