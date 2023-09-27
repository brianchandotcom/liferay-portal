/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing NullConvertibleEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class NullConvertibleEntryCacheModel
	implements CacheModel<NullConvertibleEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NullConvertibleEntryCacheModel)) {
			return false;
		}

		NullConvertibleEntryCacheModel nullConvertibleEntryCacheModel =
			(NullConvertibleEntryCacheModel)object;

		if (nullConvertibleEntryId ==
				nullConvertibleEntryCacheModel.nullConvertibleEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, nullConvertibleEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{nullConvertibleEntryId=");
		sb.append(nullConvertibleEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NullConvertibleEntry toEntityModel() {
		NullConvertibleEntryImpl nullConvertibleEntryImpl =
			new NullConvertibleEntryImpl();

		nullConvertibleEntryImpl.setNullConvertibleEntryId(
			nullConvertibleEntryId);

		if (name == null) {
			nullConvertibleEntryImpl.setName("");
		}
		else {
			nullConvertibleEntryImpl.setName(name);
		}

		nullConvertibleEntryImpl.resetOriginalValues();

		return nullConvertibleEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		nullConvertibleEntryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(nullConvertibleEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long nullConvertibleEntryId;
	public String name;

}