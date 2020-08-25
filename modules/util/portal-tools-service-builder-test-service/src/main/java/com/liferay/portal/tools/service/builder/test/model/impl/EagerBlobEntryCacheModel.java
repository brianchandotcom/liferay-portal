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
import com.liferay.portal.tools.service.builder.test.model.EagerBlobEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing EagerBlobEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EagerBlobEntryCacheModel
	implements CacheModel<EagerBlobEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EagerBlobEntryCacheModel)) {
			return false;
		}

		EagerBlobEntryCacheModel eagerBlobEntryCacheModel =
			(EagerBlobEntryCacheModel)object;

		if (eagerBlobEntryId == eagerBlobEntryCacheModel.eagerBlobEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, eagerBlobEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", eagerBlobEntryId=");
		sb.append(eagerBlobEntryId);
		sb.append(", groupId=");
		sb.append(groupId);

		return sb.toString();
	}

	@Override
	public EagerBlobEntry toEntityModel() {
		EagerBlobEntryImpl eagerBlobEntryImpl = new EagerBlobEntryImpl();

		if (uuid == null) {
			eagerBlobEntryImpl.setUuid("");
		}
		else {
			eagerBlobEntryImpl.setUuid(uuid);
		}

		eagerBlobEntryImpl.setEagerBlobEntryId(eagerBlobEntryId);
		eagerBlobEntryImpl.setGroupId(groupId);

		eagerBlobEntryImpl.resetOriginalValues();

		return eagerBlobEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		eagerBlobEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(eagerBlobEntryId);

		objectOutput.writeLong(groupId);
	}

	public String uuid;
	public long eagerBlobEntryId;
	public long groupId;

}