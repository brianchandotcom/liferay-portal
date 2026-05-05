/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CacheReplicatorEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheReplicatorEntryCacheModel
	implements CacheModel<CacheReplicatorEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheReplicatorEntryCacheModel)) {
			return false;
		}

		CacheReplicatorEntryCacheModel cacheReplicatorEntryCacheModel =
			(CacheReplicatorEntryCacheModel)object;

		if (cacheReplicatorEntryId ==
				cacheReplicatorEntryCacheModel.cacheReplicatorEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cacheReplicatorEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{cacheReplicatorEntryId=");
		sb.append(cacheReplicatorEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CacheReplicatorEntry toEntityModel() {
		CacheReplicatorEntryImpl cacheReplicatorEntryImpl =
			new CacheReplicatorEntryImpl();

		cacheReplicatorEntryImpl.setCacheReplicatorEntryId(
			cacheReplicatorEntryId);
		cacheReplicatorEntryImpl.setCompanyId(companyId);

		if (name == null) {
			cacheReplicatorEntryImpl.setName("");
		}
		else {
			cacheReplicatorEntryImpl.setName(name);
		}

		cacheReplicatorEntryImpl.resetOriginalValues();

		return cacheReplicatorEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cacheReplicatorEntryId = objectInput.readLong();

		companyId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cacheReplicatorEntryId);

		objectOutput.writeLong(companyId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long cacheReplicatorEntryId;
	public long companyId;
	public String name;

}
// LIFERAY-SERVICE-BUILDER-HASH:1346905488