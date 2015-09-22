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

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.model.PollsChoice;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PollsChoice in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoice
 * @generated
 */
@ProviderType
public class PollsChoiceCacheModel implements CacheModel<PollsChoice>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PollsChoiceCacheModel)) {
			return false;
		}

		PollsChoiceCacheModel pollsChoiceCacheModel = (PollsChoiceCacheModel)obj;

		if (choiceId == pollsChoiceCacheModel.choiceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, choiceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", choiceId=");
		sb.append(choiceId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PollsChoice toEntityModel() {
		PollsChoiceImpl pollsChoiceImpl = new PollsChoiceImpl();

		if (uuid == null) {
			pollsChoiceImpl.setUuid(StringPool.BLANK);
		}
		else {
			pollsChoiceImpl.setUuid(uuid);
		}

		pollsChoiceImpl.setChoiceId(choiceId);
		pollsChoiceImpl.setGroupId(groupId);
		pollsChoiceImpl.setUserId(userId);

		if (userName == null) {
			pollsChoiceImpl.setUserName(StringPool.BLANK);
		}
		else {
			pollsChoiceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			pollsChoiceImpl.setCreateDate(null);
		}
		else {
			pollsChoiceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			pollsChoiceImpl.setModifiedDate(null);
		}
		else {
			pollsChoiceImpl.setModifiedDate(new Date(modifiedDate));
		}

		pollsChoiceImpl.setQuestionId(questionId);

		if (name == null) {
			pollsChoiceImpl.setName(StringPool.BLANK);
		}
		else {
			pollsChoiceImpl.setName(name);
		}

		if (description == null) {
			pollsChoiceImpl.setDescription(StringPool.BLANK);
		}
		else {
			pollsChoiceImpl.setDescription(description);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			pollsChoiceImpl.setLastPublishDate(null);
		}
		else {
			pollsChoiceImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		pollsChoiceImpl.setCompanyId(companyId);

		pollsChoiceImpl.resetOriginalValues();

		return pollsChoiceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		choiceId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		questionId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
		companyId = objectInput.readLong();
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

		objectOutput.writeLong(choiceId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(questionId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(lastPublishDate);
		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long choiceId;
	public long groupId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long questionId;
	public String name;
	public String description;
	public long lastPublishDate;
	public long companyId;
}