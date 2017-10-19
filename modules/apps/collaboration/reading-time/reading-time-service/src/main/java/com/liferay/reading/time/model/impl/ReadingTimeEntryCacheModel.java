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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

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
 * @see ReadingTimeEntry
 * @generated
 */
@ProviderType
public class ReadingTimeEntryCacheModel implements CacheModel<ReadingTimeEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ReadingTimeEntryCacheModel)) {
			return false;
		}

		ReadingTimeEntryCacheModel readingTimeEntryCacheModel = (ReadingTimeEntryCacheModel)obj;

		if (readingTimeEntryId == readingTimeEntryCacheModel.readingTimeEntryId) {
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
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", readingTimeEntryId=");
		sb.append(readingTimeEntryId);
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
		sb.append(", readingTimeInSeconds=");
		sb.append(readingTimeInSeconds);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ReadingTimeEntry toEntityModel() {
		ReadingTimeEntryImpl readingTimeEntryImpl = new ReadingTimeEntryImpl();

		if (uuid == null) {
			readingTimeEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			readingTimeEntryImpl.setUuid(uuid);
		}

		readingTimeEntryImpl.setReadingTimeEntryId(readingTimeEntryId);
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
		readingTimeEntryImpl.setReadingTimeInSeconds(readingTimeInSeconds);

		readingTimeEntryImpl.resetOriginalValues();

		return readingTimeEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		readingTimeEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		readingTimeInSeconds = objectInput.readLong();
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

		objectOutput.writeLong(readingTimeEntryId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(readingTimeInSeconds);
	}

	public String uuid;
	public long readingTimeEntryId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long readingTimeInSeconds;
}