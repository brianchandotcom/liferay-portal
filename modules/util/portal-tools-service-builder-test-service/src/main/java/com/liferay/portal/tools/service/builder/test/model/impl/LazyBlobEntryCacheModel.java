/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.model.LazyBlobEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LazyBlobEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LazyBlobEntryCacheModel
	implements CacheModel<LazyBlobEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LazyBlobEntryCacheModel)) {
			return false;
		}

		LazyBlobEntryCacheModel lazyBlobEntryCacheModel =
			(LazyBlobEntryCacheModel)object;

		if ((lazyBlobEntryId == lazyBlobEntryCacheModel.lazyBlobEntryId) &&
			(mvccVersion == lazyBlobEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, lazyBlobEntryId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", lazyBlobEntryId=");
		sb.append(lazyBlobEntryId);
		sb.append(", groupId=");
		sb.append(groupId);

		return sb.toString();
	}

	@Override
	public LazyBlobEntry toEntityModel() {
		LazyBlobEntryImpl lazyBlobEntryImpl = new LazyBlobEntryImpl();

		lazyBlobEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			lazyBlobEntryImpl.setUuid("");
		}
		else {
			lazyBlobEntryImpl.setUuid(uuid);
		}

		lazyBlobEntryImpl.setLazyBlobEntryId(lazyBlobEntryId);
		lazyBlobEntryImpl.setGroupId(groupId);

		lazyBlobEntryImpl.resetOriginalValues();

		return lazyBlobEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		lazyBlobEntryId = objectInput.readLong();

		groupId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(lazyBlobEntryId);

		objectOutput.writeLong(groupId);
	}

	public long mvccVersion;
	public String uuid;
	public long lazyBlobEntryId;
	public long groupId;

}
// LIFERAY-SERVICE-BUILDER-HASH:560821198