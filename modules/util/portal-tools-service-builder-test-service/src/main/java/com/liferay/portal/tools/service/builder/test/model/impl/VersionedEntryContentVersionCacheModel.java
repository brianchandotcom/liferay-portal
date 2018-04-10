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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing VersionedEntryContentVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentVersion
 * @generated
 */
@ProviderType
public class VersionedEntryContentVersionCacheModel implements CacheModel<VersionedEntryContentVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryContentVersionCacheModel)) {
			return false;
		}

		VersionedEntryContentVersionCacheModel versionedEntryContentVersionCacheModel =
			(VersionedEntryContentVersionCacheModel)obj;

		if (versionedEntryContentVersionId == versionedEntryContentVersionCacheModel.versionedEntryContentVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, versionedEntryContentVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{versionedEntryContentVersionId=");
		sb.append(versionedEntryContentVersionId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", versionedEntryContentId=");
		sb.append(versionedEntryContentId);
		sb.append(", versionedEntryId=");
		sb.append(versionedEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", content=");
		sb.append(content);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VersionedEntryContentVersion toEntityModel() {
		VersionedEntryContentVersionImpl versionedEntryContentVersionImpl = new VersionedEntryContentVersionImpl();

		versionedEntryContentVersionImpl.setVersionedEntryContentVersionId(versionedEntryContentVersionId);
		versionedEntryContentVersionImpl.setVersion(version);
		versionedEntryContentVersionImpl.setVersionedEntryContentId(versionedEntryContentId);
		versionedEntryContentVersionImpl.setVersionedEntryId(versionedEntryId);

		if (languageId == null) {
			versionedEntryContentVersionImpl.setLanguageId("");
		}
		else {
			versionedEntryContentVersionImpl.setLanguageId(languageId);
		}

		if (content == null) {
			versionedEntryContentVersionImpl.setContent("");
		}
		else {
			versionedEntryContentVersionImpl.setContent(content);
		}

		versionedEntryContentVersionImpl.resetOriginalValues();

		return versionedEntryContentVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		versionedEntryContentVersionId = objectInput.readLong();

		version = objectInput.readInt();

		versionedEntryContentId = objectInput.readLong();

		versionedEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(versionedEntryContentVersionId);

		objectOutput.writeInt(version);

		objectOutput.writeLong(versionedEntryContentId);

		objectOutput.writeLong(versionedEntryId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}
	}

	public long versionedEntryContentVersionId;
	public int version;
	public long versionedEntryContentId;
	public long versionedEntryId;
	public String languageId;
	public String content;
}