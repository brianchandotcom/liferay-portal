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
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing VersionedEntryVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class VersionedEntryVersionCacheModel
	implements CacheModel<VersionedEntryVersion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryVersionCacheModel)) {
			return false;
		}

		VersionedEntryVersionCacheModel versionedEntryVersionCacheModel =
			(VersionedEntryVersionCacheModel)obj;

		if (versionedEntryVersionId ==
				versionedEntryVersionCacheModel.versionedEntryVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, versionedEntryVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{versionedEntryVersionId=");
		sb.append(versionedEntryVersionId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", versionedEntryId=");
		sb.append(versionedEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VersionedEntryVersion toEntityModel() {
		VersionedEntryVersionImpl versionedEntryVersionImpl =
			new VersionedEntryVersionImpl();

		versionedEntryVersionImpl.setVersionedEntryVersionId(
			versionedEntryVersionId);
		versionedEntryVersionImpl.setVersion(version);
		versionedEntryVersionImpl.setVersionedEntryId(versionedEntryId);
		versionedEntryVersionImpl.setGroupId(groupId);

		versionedEntryVersionImpl.resetOriginalValues();

		return versionedEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		versionedEntryVersionId = objectInput.readLong();

		version = objectInput.readInt();

		versionedEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(versionedEntryVersionId);

		objectOutput.writeInt(version);

		objectOutput.writeLong(versionedEntryId);

		objectOutput.writeLong(groupId);
	}

	public long versionedEntryVersionId;
	public int version;
	public long versionedEntryId;
	public long groupId;

}