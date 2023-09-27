/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model.impl;

import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ChangesetEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ChangesetEntryCacheModel
	implements CacheModel<ChangesetEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ChangesetEntryCacheModel)) {
			return false;
		}

		ChangesetEntryCacheModel changesetEntryCacheModel =
			(ChangesetEntryCacheModel)object;

		if (changesetEntryId == changesetEntryCacheModel.changesetEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, changesetEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{changesetEntryId=");
		sb.append(changesetEntryId);
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
		sb.append(", changesetCollectionId=");
		sb.append(changesetCollectionId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ChangesetEntry toEntityModel() {
		ChangesetEntryImpl changesetEntryImpl = new ChangesetEntryImpl();

		changesetEntryImpl.setChangesetEntryId(changesetEntryId);
		changesetEntryImpl.setGroupId(groupId);
		changesetEntryImpl.setCompanyId(companyId);
		changesetEntryImpl.setUserId(userId);

		if (userName == null) {
			changesetEntryImpl.setUserName("");
		}
		else {
			changesetEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			changesetEntryImpl.setCreateDate(null);
		}
		else {
			changesetEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			changesetEntryImpl.setModifiedDate(null);
		}
		else {
			changesetEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		changesetEntryImpl.setChangesetCollectionId(changesetCollectionId);
		changesetEntryImpl.setClassNameId(classNameId);
		changesetEntryImpl.setClassPK(classPK);

		changesetEntryImpl.resetOriginalValues();

		return changesetEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		changesetEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		changesetCollectionId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(changesetEntryId);

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

		objectOutput.writeLong(changesetCollectionId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long changesetEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long changesetCollectionId;
	public long classNameId;
	public long classPK;

}