/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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