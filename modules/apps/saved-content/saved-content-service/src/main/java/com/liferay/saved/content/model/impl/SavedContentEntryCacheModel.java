/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.saved.content.model.SavedContentEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SavedContentEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SavedContentEntryCacheModel
	implements CacheModel<SavedContentEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SavedContentEntryCacheModel)) {
			return false;
		}

		SavedContentEntryCacheModel savedContentEntryCacheModel =
			(SavedContentEntryCacheModel)object;

		if ((savedContentEntryId ==
				savedContentEntryCacheModel.savedContentEntryId) &&
			(mvccVersion == savedContentEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, savedContentEntryId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", savedContentEntryId=");
		sb.append(savedContentEntryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SavedContentEntry toEntityModel() {
		SavedContentEntryImpl savedContentEntryImpl =
			new SavedContentEntryImpl();

		savedContentEntryImpl.setMvccVersion(mvccVersion);
		savedContentEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			savedContentEntryImpl.setUuid("");
		}
		else {
			savedContentEntryImpl.setUuid(uuid);
		}

		savedContentEntryImpl.setSavedContentEntryId(savedContentEntryId);
		savedContentEntryImpl.setGroupId(groupId);
		savedContentEntryImpl.setCompanyId(companyId);
		savedContentEntryImpl.setUserId(userId);

		if (userName == null) {
			savedContentEntryImpl.setUserName("");
		}
		else {
			savedContentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			savedContentEntryImpl.setCreateDate(null);
		}
		else {
			savedContentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			savedContentEntryImpl.setModifiedDate(null);
		}
		else {
			savedContentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		savedContentEntryImpl.setClassNameId(classNameId);
		savedContentEntryImpl.setClassPK(classPK);

		savedContentEntryImpl.resetOriginalValues();

		return savedContentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		savedContentEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
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

		objectOutput.writeLong(savedContentEntryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long savedContentEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;

}