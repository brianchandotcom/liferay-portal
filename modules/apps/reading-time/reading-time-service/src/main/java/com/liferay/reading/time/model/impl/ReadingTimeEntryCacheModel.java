/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
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
	implements CacheModel<ReadingTimeEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReadingTimeEntryCacheModel)) {
			return false;
		}

		ReadingTimeEntryCacheModel readingTimeEntryCacheModel =
			(ReadingTimeEntryCacheModel)object;

		if ((readingTimeEntryId ==
				readingTimeEntryCacheModel.readingTimeEntryId) &&
			(mvccVersion == readingTimeEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, readingTimeEntryId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		readingTimeEntryImpl.setMvccVersion(mvccVersion);
		readingTimeEntryImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
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