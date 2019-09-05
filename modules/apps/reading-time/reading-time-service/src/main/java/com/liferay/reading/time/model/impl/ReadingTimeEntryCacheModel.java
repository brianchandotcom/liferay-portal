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

package com.liferay.reading.time.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.reading.time.model.ReadingTimeEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ReadingTimeEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ReadingTimeEntryCacheModel
	implements CacheModel<ReadingTimeEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReadingTimeEntryCacheModel)) {
			return false;
		}

		ReadingTimeEntryCacheModel readingTimeEntryCacheModel =
			(ReadingTimeEntryCacheModel)obj;

		if (readingTimeEntryId ==
				readingTimeEntryCacheModel.readingTimeEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, readingTimeEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", readingTimeEntryId=");
		sb.append(readingTimeEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", readingTime=");
		sb.append(readingTime);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ReadingTimeEntry toEntityModel() {
		ReadingTimeEntryImpl readingTimeEntryImpl = new ReadingTimeEntryImpl();

		if (uuid == null) {
			readingTimeEntryImpl.setUuid("");
		}
		else {
			readingTimeEntryImpl.setUuid(uuid);
		}

		readingTimeEntryImpl.setReadingTimeEntryId(readingTimeEntryId);
		readingTimeEntryImpl.setGroupId(groupId);
		readingTimeEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			readingTimeEntryImpl.setCreateDate(null);
		}
		else {
			readingTimeEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			readingTimeEntryImpl.setModifiedDate(null);
		}
		else {
			readingTimeEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		readingTimeEntryImpl.setClassNameId(classNameId);
		readingTimeEntryImpl.setClassPK(classPK);
		readingTimeEntryImpl.setReadingTime(readingTime);

		readingTimeEntryImpl.resetOriginalValues();

		return readingTimeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		readingTimeEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		readingTime = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(readingTimeEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(readingTime);
	}

	public String uuid;
	public long readingTimeEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long readingTime;

}