/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.compat740.model.TrashEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing TrashEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashEntryCacheModel
	implements CacheModel<TrashEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TrashEntryCacheModel)) {
			return false;
		}

		TrashEntryCacheModel trashEntryCacheModel =
			(TrashEntryCacheModel)object;

		if (trashEntryId == trashEntryCacheModel.trashEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, trashEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{trashEntryId=");
		sb.append(trashEntryId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TrashEntry toEntityModel() {
		TrashEntryImpl trashEntryImpl = new TrashEntryImpl();

		trashEntryImpl.setTrashEntryId(trashEntryId);
		trashEntryImpl.setGroupId(groupId);
		trashEntryImpl.setCompanyId(companyId);
		trashEntryImpl.setUserId(userId);

		if (userName == null) {
			trashEntryImpl.setUserName("");
		}
		else {
			trashEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			trashEntryImpl.setCreateDate(null);
		}
		else {
			trashEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			trashEntryImpl.setModifiedDate(null);
		}
		else {
			trashEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			trashEntryImpl.setName("");
		}
		else {
			trashEntryImpl.setName(name);
		}

		trashEntryImpl.resetOriginalValues();

		return trashEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		trashEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(trashEntryId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long trashEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:-891811579