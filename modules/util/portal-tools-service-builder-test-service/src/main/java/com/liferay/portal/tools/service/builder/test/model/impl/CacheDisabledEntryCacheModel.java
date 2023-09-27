/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CacheDisabledEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheDisabledEntryCacheModel
	implements CacheModel<CacheDisabledEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheDisabledEntryCacheModel)) {
			return false;
		}

		CacheDisabledEntryCacheModel cacheDisabledEntryCacheModel =
			(CacheDisabledEntryCacheModel)object;

		if (cacheDisabledEntryId ==
				cacheDisabledEntryCacheModel.cacheDisabledEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cacheDisabledEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{cacheDisabledEntryId=");
		sb.append(cacheDisabledEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CacheDisabledEntry toEntityModel() {
		CacheDisabledEntryImpl cacheDisabledEntryImpl =
			new CacheDisabledEntryImpl();

		cacheDisabledEntryImpl.setCacheDisabledEntryId(cacheDisabledEntryId);

		if (name == null) {
			cacheDisabledEntryImpl.setName("");
		}
		else {
			cacheDisabledEntryImpl.setName(name);
		}

		cacheDisabledEntryImpl.resetOriginalValues();

		return cacheDisabledEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cacheDisabledEntryId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cacheDisabledEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long cacheDisabledEntryId;
	public String name;

}