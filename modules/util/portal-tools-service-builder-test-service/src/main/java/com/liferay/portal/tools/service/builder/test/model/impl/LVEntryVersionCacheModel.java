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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryVersionCacheModel)) {
			return false;
		}

		LVEntryVersionCacheModel lvEntryVersionCacheModel =
			(LVEntryVersionCacheModel)obj;

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