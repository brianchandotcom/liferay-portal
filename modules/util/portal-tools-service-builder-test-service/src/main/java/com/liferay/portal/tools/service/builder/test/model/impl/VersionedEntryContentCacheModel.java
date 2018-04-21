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
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing VersionedEntryContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContent
 * @generated
 */
@ProviderType
public class VersionedEntryContentCacheModel implements CacheModel<VersionedEntryContent>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VersionedEntryContentCacheModel)) {
			return false;
		}

		VersionedEntryContentCacheModel versionedEntryContentCacheModel = (VersionedEntryContentCacheModel)obj;

		if ((versionedEntryContentId == versionedEntryContentCacheModel.versionedEntryContentId) &&
				(mvccVersion == versionedEntryContentCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, versionedEntryContentId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", versionedEntryContentId=");
		sb.append(versionedEntryContentId);
		sb.append(", versionedEntryId=");
		sb.append(versionedEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", content=");
		sb.append(content);
		sb.append(", headId=");
		sb.append(headId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public VersionedEntryContent toEntityModel() {
		VersionedEntryContentImpl versionedEntryContentImpl = new VersionedEntryContentImpl();

		versionedEntryContentImpl.setMvccVersion(mvccVersion);
		versionedEntryContentImpl.setVersionedEntryContentId(versionedEntryContentId);
		versionedEntryContentImpl.setVersionedEntryId(versionedEntryId);

		if (languageId == null) {
			versionedEntryContentImpl.setLanguageId("");
		}
		else {
			versionedEntryContentImpl.setLanguageId(languageId);
		}

		if (content == null) {
			versionedEntryContentImpl.setContent("");
		}
		else {
			versionedEntryContentImpl.setContent(content);
		}

		versionedEntryContentImpl.setHeadId(headId);

		versionedEntryContentImpl.resetOriginalValues();

		return versionedEntryContentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		versionedEntryContentId = objectInput.readLong();

		versionedEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		content = objectInput.readUTF();

		headId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(headId);
	}

	public long mvccVersion;
	public long versionedEntryContentId;
	public long versionedEntryId;
	public String languageId;
	public String content;
	public long headId;
}