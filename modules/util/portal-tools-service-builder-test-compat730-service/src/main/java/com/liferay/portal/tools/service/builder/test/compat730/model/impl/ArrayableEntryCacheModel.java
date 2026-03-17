/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.compat730.model.ArrayableEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ArrayableEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ArrayableEntryCacheModel
	implements CacheModel<ArrayableEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ArrayableEntryCacheModel)) {
			return false;
		}

		ArrayableEntryCacheModel arrayableEntryCacheModel =
			(ArrayableEntryCacheModel)object;

		if (arrayableEntryId == arrayableEntryCacheModel.arrayableEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, arrayableEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{arrayableEntryId=");
		sb.append(arrayableEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", integer=");
		sb.append(integer);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ArrayableEntry toEntityModel() {
		ArrayableEntryImpl arrayableEntryImpl = new ArrayableEntryImpl();

		arrayableEntryImpl.setArrayableEntryId(arrayableEntryId);
		arrayableEntryImpl.setGroupId(groupId);
		arrayableEntryImpl.setCompanyId(companyId);
		arrayableEntryImpl.setUserId(userId);
		arrayableEntryImpl.setInteger(integer);

		if (name == null) {
			arrayableEntryImpl.setName("");
		}
		else {
			arrayableEntryImpl.setName(name);
		}

		if (type == null) {
			arrayableEntryImpl.setType("");
		}
		else {
			arrayableEntryImpl.setType(type);
		}

		arrayableEntryImpl.resetOriginalValues();

		return arrayableEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		arrayableEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		integer = objectInput.readInt();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(arrayableEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeInt(integer);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long arrayableEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public int integer;
	public String name;
	public String type;

}
// SB-Hash:852949835