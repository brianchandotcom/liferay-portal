/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model.impl;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.compat700.model.ConvertNullEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ConvertNullEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ConvertNullEntryCacheModel
	implements CacheModel<ConvertNullEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ConvertNullEntryCacheModel)) {
			return false;
		}

		ConvertNullEntryCacheModel convertNullEntryCacheModel =
			(ConvertNullEntryCacheModel)object;

		if (convertNullEntryId ==
				convertNullEntryCacheModel.convertNullEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, convertNullEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{convertNullEntryId=");
		sb.append(convertNullEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ConvertNullEntry toEntityModel() {
		ConvertNullEntryImpl convertNullEntryImpl = new ConvertNullEntryImpl();

		convertNullEntryImpl.setConvertNullEntryId(convertNullEntryId);

		if (name == null) {
			convertNullEntryImpl.setName("");
		}
		else {
			convertNullEntryImpl.setName(name);
		}

		convertNullEntryImpl.resetOriginalValues();

		return convertNullEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		convertNullEntryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(convertNullEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long convertNullEntryId;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:1944982870