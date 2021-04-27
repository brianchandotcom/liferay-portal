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

		cacheFieldEntryImpl.setNickName(_nickName);

		return cacheFieldEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		cacheFieldEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
		name = objectInput.readUTF();

		_nickName = (String)objectInput.readObject();
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

		objectOutput.writeObject(_nickName);
	}

	public long cacheFieldEntryId;
	public long groupId;
	public String name;
	public String _nickName;

}