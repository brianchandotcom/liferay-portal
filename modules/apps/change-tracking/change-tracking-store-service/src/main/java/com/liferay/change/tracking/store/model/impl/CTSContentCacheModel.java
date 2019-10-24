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

package com.liferay.change.tracking.store.model.impl;

import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTSContent in entity cache.
 *
 * @author Shuyang Zhou
 * @generated
 */
public class CTSContentCacheModel
	implements CacheModel<CTSContent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTSContentCacheModel)) {
			return false;
		}

		CTSContentCacheModel ctsContentCacheModel = (CTSContentCacheModel)obj;

		if ((ctsContentId == ctsContentCacheModel.ctsContentId) &&
			(mvccVersion == ctsContentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctsContentId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", ctsContentId=");
		sb.append(ctsContentId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append(", storeType=");
		sb.append(storeType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTSContent toEntityModel() {
		CTSContentImpl ctsContentImpl = new CTSContentImpl();

		ctsContentImpl.setMvccVersion(mvccVersion);
		ctsContentImpl.setCtCollectionId(ctCollectionId);
		ctsContentImpl.setCtsContentId(ctsContentId);
		ctsContentImpl.setCompanyId(companyId);
		ctsContentImpl.setRepositoryId(repositoryId);

		if (path == null) {
			ctsContentImpl.setPath("");
		}
		else {
			ctsContentImpl.setPath(path);
		}

		if (version == null) {
			ctsContentImpl.setVersion("");
		}
		else {
			ctsContentImpl.setVersion(version);
		}

		ctsContentImpl.setSize(size);

		if (storeType == null) {
			ctsContentImpl.setStoreType("");
		}
		else {
			ctsContentImpl.setStoreType(storeType);
		}

		ctsContentImpl.resetOriginalValues();

		return ctsContentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		ctsContentId = objectInput.readLong();

		companyId = objectInput.readLong();

		repositoryId = objectInput.readLong();
		path = objectInput.readUTF();
		version = objectInput.readUTF();

		size = objectInput.readLong();
		storeType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(ctsContentId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(repositoryId);

		if (path == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(path);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(size);

		if (storeType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(storeType);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long ctsContentId;
	public long companyId;
	public long repositoryId;
	public String path;
	public String version;
	public long size;
	public String storeType;

}