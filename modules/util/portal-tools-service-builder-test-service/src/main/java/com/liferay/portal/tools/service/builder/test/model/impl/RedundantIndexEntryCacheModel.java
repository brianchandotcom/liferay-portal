/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.RedundantIndexEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RedundantIndexEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RedundantIndexEntryCacheModel
	implements CacheModel<RedundantIndexEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RedundantIndexEntryCacheModel)) {
			return false;
		}

		RedundantIndexEntryCacheModel redundantIndexEntryCacheModel =
			(RedundantIndexEntryCacheModel)object;

		if (redundantIndexEntryId ==
				redundantIndexEntryCacheModel.redundantIndexEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, redundantIndexEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{redundantIndexEntryId=");
		sb.append(redundantIndexEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RedundantIndexEntry toEntityModel() {
		RedundantIndexEntryImpl redundantIndexEntryImpl =
			new RedundantIndexEntryImpl();

		redundantIndexEntryImpl.setRedundantIndexEntryId(redundantIndexEntryId);
		redundantIndexEntryImpl.setCompanyId(companyId);

		if (name == null) {
			redundantIndexEntryImpl.setName("");
		}
		else {
			redundantIndexEntryImpl.setName(name);
		}

		redundantIndexEntryImpl.resetOriginalValues();

		return redundantIndexEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		redundantIndexEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(redundantIndexEntryId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long redundantIndexEntryId;
	public long companyId;
	public String name;

}