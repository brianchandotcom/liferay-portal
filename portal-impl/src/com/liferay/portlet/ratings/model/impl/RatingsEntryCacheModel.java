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

package com.liferay.portlet.ratings.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.ratings.kernel.model.RatingsEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RatingsEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RatingsEntryCacheModel
	implements CacheModel<RatingsEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RatingsEntryCacheModel)) {
			return false;
		}

		RatingsEntryCacheModel ratingsEntryCacheModel =
			(RatingsEntryCacheModel)obj;

		if (entryId == ratingsEntryCacheModel.entryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, entryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", entryId=");
		sb.append(entryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", score=");
		sb.append(score);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RatingsEntry toEntityModel() {
		RatingsEntryImpl ratingsEntryImpl = new RatingsEntryImpl();

		if (uuid == null) {
			ratingsEntryImpl.setUuid("");
		}
		else {
			ratingsEntryImpl.setUuid(uuid);
		}

		ratingsEntryImpl.setEntryId(entryId);
		ratingsEntryImpl.setCompanyId(companyId);
		ratingsEntryImpl.setUserId(userId);

		if (userName == null) {
			ratingsEntryImpl.setUserName("");
		}
		else {
			ratingsEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ratingsEntryImpl.setCreateDate(null);
		}
		else {
			ratingsEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ratingsEntryImpl.setModifiedDate(null);
		}
		else {
			ratingsEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		ratingsEntryImpl.setClassNameId(classNameId);
		ratingsEntryImpl.setClassPK(classPK);
		ratingsEntryImpl.setScore(score);

		ratingsEntryImpl.resetOriginalValues();

		return ratingsEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		entryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		score = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(entryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeDouble(score);
	}

	public String uuid;
	public long entryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public double score;

}