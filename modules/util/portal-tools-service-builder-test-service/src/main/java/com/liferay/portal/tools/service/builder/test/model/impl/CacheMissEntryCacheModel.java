/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.model.CacheMissEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CacheMissEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CacheMissEntryCacheModel
	implements CacheModel<CacheMissEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheMissEntryCacheModel)) {
			return false;
		}

		CacheMissEntryCacheModel cacheMissEntryCacheModel =
			(CacheMissEntryCacheModel)object;

		if ((cacheMissEntryId == cacheMissEntryCacheModel.cacheMissEntryId) &&
			(mvccVersion == cacheMissEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, cacheMissEntryId);

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
		StringBundler sb = new StringBundler(7);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", cacheMissEntryId=");
		sb.append(cacheMissEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CacheMissEntry toEntityModel() {
		CacheMissEntryImpl cacheMissEntryImpl = new CacheMissEntryImpl();

		cacheMissEntryImpl.setMvccVersion(mvccVersion);
		cacheMissEntryImpl.setCtCollectionId(ctCollectionId);
		cacheMissEntryImpl.setCacheMissEntryId(cacheMissEntryId);

		cacheMissEntryImpl.resetOriginalValues();

		return cacheMissEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		cacheMissEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(cacheMissEntryId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long cacheMissEntryId;

}