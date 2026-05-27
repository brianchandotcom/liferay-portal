/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.UniqueFinderEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UniqueFinderEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UniqueFinderEntryCacheModel
	implements CacheModel<UniqueFinderEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UniqueFinderEntryCacheModel)) {
			return false;
		}

		UniqueFinderEntryCacheModel uniqueFinderEntryCacheModel =
			(UniqueFinderEntryCacheModel)object;

		if (uniqueFinderEntryId ==
				uniqueFinderEntryCacheModel.uniqueFinderEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, uniqueFinderEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{uniqueFinderEntryId=");
		sb.append(uniqueFinderEntryId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UniqueFinderEntry toEntityModel() {
		UniqueFinderEntryImpl uniqueFinderEntryImpl =
			new UniqueFinderEntryImpl();

		uniqueFinderEntryImpl.setUniqueFinderEntryId(uniqueFinderEntryId);

		if (modifiedDate == Long.MIN_VALUE) {
			uniqueFinderEntryImpl.setModifiedDate(null);
		}
		else {
			uniqueFinderEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			uniqueFinderEntryImpl.setName("");
		}
		else {
			uniqueFinderEntryImpl.setName(name);
		}

		uniqueFinderEntryImpl.resetOriginalValues();

		return uniqueFinderEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uniqueFinderEntryId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(uniqueFinderEntryId);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long uniqueFinderEntryId;
	public long modifiedDate;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:563411135