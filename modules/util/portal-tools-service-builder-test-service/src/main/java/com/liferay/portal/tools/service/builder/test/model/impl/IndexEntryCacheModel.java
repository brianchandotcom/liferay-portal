/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing IndexEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class IndexEntryCacheModel
	implements CacheModel<IndexEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IndexEntryCacheModel)) {
			return false;
		}

		IndexEntryCacheModel indexEntryCacheModel =
			(IndexEntryCacheModel)object;

		if (indexEntryId == indexEntryCacheModel.indexEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, indexEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{indexEntryId=");
		sb.append(indexEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public IndexEntry toEntityModel() {
		IndexEntryImpl indexEntryImpl = new IndexEntryImpl();

		indexEntryImpl.setIndexEntryId(indexEntryId);
		indexEntryImpl.setCompanyId(companyId);

		if (name == null) {
			indexEntryImpl.setName("");
		}
		else {
			indexEntryImpl.setName(name);
		}

		indexEntryImpl.resetOriginalValues();

		return indexEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		indexEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(indexEntryId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long indexEntryId;
	public long companyId;
	public String name;

}