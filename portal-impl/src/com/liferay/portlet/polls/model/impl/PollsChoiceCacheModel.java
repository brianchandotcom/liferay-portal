/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.polls.model.PollsChoice;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PollsChoice in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoice
 * @generated
 */
public class PollsChoiceCacheModel implements CacheModel<PollsChoice>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", choiceId=");
		sb.append(choiceId);
		sb.append(", questionId=");
		sb.append(questionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	public PollsChoice toEntityModel() {
		PollsChoiceImpl pollsChoiceImpl = new PollsChoiceImpl();

		if (uuid == null) {
			pollsChoiceImpl.setUuid(StringPool.BLANK);
		}
		else {
			pollsChoiceImpl.setUuid(uuid);
		}

		pollsChoiceImpl.setChoiceId(choiceId);
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

		pollsChoiceImpl.resetOriginalValues();

		return pollsChoiceImpl;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		choiceId = objectInput.readLong();

		questionId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(choiceId);

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
	}

	public String uuid;
	public long choiceId;
	public long questionId;
	public String name;
	public String description;
}