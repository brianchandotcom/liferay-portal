/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.CacheFieldEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CacheFieldEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheFieldEntryCacheModel
	implements CacheModel<CacheFieldEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheFieldEntryCacheModel)) {
			return false;
		}

		CacheFieldEntryCacheModel cacheFieldEntryCacheModel =
			(CacheFieldEntryCacheModel)object;

		if (cacheFieldEntryId == cacheFieldEntryCacheModel.cacheFieldEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cacheFieldEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{cacheFieldEntryId=");
		sb.append(cacheFieldEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CacheFieldEntry toEntityModel() {
		CacheFieldEntryImpl cacheFieldEntryImpl = new CacheFieldEntryImpl();

		cacheFieldEntryImpl.setCacheFieldEntryId(cacheFieldEntryId);
		cacheFieldEntryImpl.setGroupId(groupId);

		if (name == null) {
			cacheFieldEntryImpl.setName("");
		}
		else {
			cacheFieldEntryImpl.setName(name);
		}

		cacheFieldEntryImpl.resetOriginalValues();

		cacheFieldEntryImpl.setNickname(_nickname);

		return cacheFieldEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		cacheFieldEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
		name = objectInput.readUTF();

		_nickname = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cacheFieldEntryId);

		objectOutput.writeLong(groupId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeObject(_nickname);
	}

	public long cacheFieldEntryId;
	public long groupId;
	public String name;
	public String _nickname;

}