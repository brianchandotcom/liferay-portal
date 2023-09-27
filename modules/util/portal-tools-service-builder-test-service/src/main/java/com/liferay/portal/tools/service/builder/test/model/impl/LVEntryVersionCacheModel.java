/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LVEntryVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryVersionCacheModel
	implements CacheModel<LVEntryVersion>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LVEntryVersionCacheModel)) {
			return false;
		}

		LVEntryVersionCacheModel lvEntryVersionCacheModel =
			(LVEntryVersionCacheModel)object;

		if (lvEntryVersionId == lvEntryVersionCacheModel.lvEntryVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, lvEntryVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{lvEntryVersionId=");
		sb.append(lvEntryVersionId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append(", lvEntryId=");
		sb.append(lvEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", uniqueGroupKey=");
		sb.append(uniqueGroupKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LVEntryVersion toEntityModel() {
		LVEntryVersionImpl lvEntryVersionImpl = new LVEntryVersionImpl();

		lvEntryVersionImpl.setLvEntryVersionId(lvEntryVersionId);
		lvEntryVersionImpl.setVersion(version);

		if (uuid == null) {
			lvEntryVersionImpl.setUuid("");
		}
		else {
			lvEntryVersionImpl.setUuid(uuid);
		}

		if (defaultLanguageId == null) {
			lvEntryVersionImpl.setDefaultLanguageId("");
		}
		else {
			lvEntryVersionImpl.setDefaultLanguageId(defaultLanguageId);
		}

		lvEntryVersionImpl.setLvEntryId(lvEntryId);
		lvEntryVersionImpl.setCompanyId(companyId);
		lvEntryVersionImpl.setGroupId(groupId);

		if (uniqueGroupKey == null) {
			lvEntryVersionImpl.setUniqueGroupKey("");
		}
		else {
			lvEntryVersionImpl.setUniqueGroupKey(uniqueGroupKey);
		}

		lvEntryVersionImpl.resetOriginalValues();

		return lvEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		lvEntryVersionId = objectInput.readLong();

		version = objectInput.readInt();
		uuid = objectInput.readUTF();
		defaultLanguageId = objectInput.readUTF();

		lvEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		groupId = objectInput.readLong();
		uniqueGroupKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(lvEntryVersionId);

		objectOutput.writeInt(version);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}

		objectOutput.writeLong(lvEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(groupId);

		if (uniqueGroupKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uniqueGroupKey);
		}
	}

	public long lvEntryVersionId;
	public int version;
	public String uuid;
	public String defaultLanguageId;
	public long lvEntryId;
	public long companyId;
	public long groupId;
	public String uniqueGroupKey;

}