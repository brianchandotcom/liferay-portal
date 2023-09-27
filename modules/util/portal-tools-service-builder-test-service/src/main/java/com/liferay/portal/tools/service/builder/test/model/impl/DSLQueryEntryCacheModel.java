/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.DSLQueryEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DSLQueryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DSLQueryEntryCacheModel
	implements CacheModel<DSLQueryEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DSLQueryEntryCacheModel)) {
			return false;
		}

		DSLQueryEntryCacheModel dslQueryEntryCacheModel =
			(DSLQueryEntryCacheModel)object;

		if (dslQueryEntryId == dslQueryEntryCacheModel.dslQueryEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dslQueryEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{dslQueryEntryId=");
		sb.append(dslQueryEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DSLQueryEntry toEntityModel() {
		DSLQueryEntryImpl dslQueryEntryImpl = new DSLQueryEntryImpl();

		dslQueryEntryImpl.setDslQueryEntryId(dslQueryEntryId);

		if (name == null) {
			dslQueryEntryImpl.setName("");
		}
		else {
			dslQueryEntryImpl.setName(name);
		}

		dslQueryEntryImpl.resetOriginalValues();

		return dslQueryEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		dslQueryEntryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(dslQueryEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long dslQueryEntryId;
	public String name;

}