/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing DynamicQueryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DynamicQueryEntryCacheModel
	implements CacheModel<DynamicQueryEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DynamicQueryEntryCacheModel)) {
			return false;
		}

		DynamicQueryEntryCacheModel dynamicQueryEntryCacheModel =
			(DynamicQueryEntryCacheModel)object;

		if (dynamicQueryEntryId ==
				dynamicQueryEntryCacheModel.dynamicQueryEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dynamicQueryEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{dynamicQueryEntryId=");
		sb.append(dynamicQueryEntryId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", amount=");
		sb.append(amount);
		sb.append(", description=");
		sb.append(description);
		sb.append(", name=");
		sb.append(name);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DynamicQueryEntry toEntityModel() {
		DynamicQueryEntryImpl dynamicQueryEntryImpl =
			new DynamicQueryEntryImpl();

		dynamicQueryEntryImpl.setDynamicQueryEntryId(dynamicQueryEntryId);

		if (createDate == Long.MIN_VALUE) {
			dynamicQueryEntryImpl.setCreateDate(null);
		}
		else {
			dynamicQueryEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			dynamicQueryEntryImpl.setModifiedDate(null);
		}
		else {
			dynamicQueryEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		dynamicQueryEntryImpl.setAmount(amount);

		if (description == null) {
			dynamicQueryEntryImpl.setDescription("");
		}
		else {
			dynamicQueryEntryImpl.setDescription(description);
		}

		if (name == null) {
			dynamicQueryEntryImpl.setName("");
		}
		else {
			dynamicQueryEntryImpl.setName(name);
		}

		dynamicQueryEntryImpl.setStatus(status);

		dynamicQueryEntryImpl.resetOriginalValues();

		return dynamicQueryEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		dynamicQueryEntryId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		amount = objectInput.readLong();
		description = objectInput.readUTF();
		name = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(dynamicQueryEntryId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(amount);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(status);
	}

	public long dynamicQueryEntryId;
	public long createDate;
	public long modifiedDate;
	public long amount;
	public String description;
	public String name;
	public int status;

}
// LIFERAY-SERVICE-BUILDER-HASH:-53652654