/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.UndefinedDefaultOrderEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing UndefinedDefaultOrderEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UndefinedDefaultOrderEntryCacheModel
	implements CacheModel<UndefinedDefaultOrderEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UndefinedDefaultOrderEntryCacheModel)) {
			return false;
		}

		UndefinedDefaultOrderEntryCacheModel
			undefinedDefaultOrderEntryCacheModel =
				(UndefinedDefaultOrderEntryCacheModel)object;

		if (undefinedDefaultOrderEntryId ==
				undefinedDefaultOrderEntryCacheModel.
					undefinedDefaultOrderEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, undefinedDefaultOrderEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{undefinedDefaultOrderEntryId=");
		sb.append(undefinedDefaultOrderEntryId);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public UndefinedDefaultOrderEntry toEntityModel() {
		UndefinedDefaultOrderEntryImpl undefinedDefaultOrderEntryImpl =
			new UndefinedDefaultOrderEntryImpl();

		undefinedDefaultOrderEntryImpl.setUndefinedDefaultOrderEntryId(
			undefinedDefaultOrderEntryId);

		if (modifiedDate == Long.MIN_VALUE) {
			undefinedDefaultOrderEntryImpl.setModifiedDate(null);
		}
		else {
			undefinedDefaultOrderEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (name == null) {
			undefinedDefaultOrderEntryImpl.setName("");
		}
		else {
			undefinedDefaultOrderEntryImpl.setName(name);
		}

		undefinedDefaultOrderEntryImpl.resetOriginalValues();

		return undefinedDefaultOrderEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		undefinedDefaultOrderEntryId = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(undefinedDefaultOrderEntryId);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long undefinedDefaultOrderEntryId;
	public long modifiedDate;
	public String name;

}
// SB-Hash:-2074831340