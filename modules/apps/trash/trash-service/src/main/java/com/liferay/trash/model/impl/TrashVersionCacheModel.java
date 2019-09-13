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

package com.liferay.trash.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.trash.model.TrashVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing TrashVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashVersionCacheModel
	implements CacheModel<TrashVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TrashVersionCacheModel)) {
			return false;
		}

		TrashVersionCacheModel trashVersionCacheModel =
			(TrashVersionCacheModel)obj;

		if ((versionId == trashVersionCacheModel.versionId) &&
			(mvccVersion == trashVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, versionId);

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", versionId=");
		sb.append(versionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", entryId=");
		sb.append(entryId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TrashVersion toEntityModel() {
		TrashVersionImpl trashVersionImpl = new TrashVersionImpl();

		trashVersionImpl.setMvccVersion(mvccVersion);
		trashVersionImpl.setVersionId(versionId);
		trashVersionImpl.setCompanyId(companyId);
		trashVersionImpl.setEntryId(entryId);
		trashVersionImpl.setClassNameId(classNameId);
		trashVersionImpl.setClassPK(classPK);

		if (typeSettings == null) {
			trashVersionImpl.setTypeSettings("");
		}
		else {
			trashVersionImpl.setTypeSettings(typeSettings);
		}

		trashVersionImpl.setStatus(status);

		trashVersionImpl.resetOriginalValues();

		return trashVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		versionId = objectInput.readLong();

		companyId = objectInput.readLong();

		entryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		typeSettings = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(versionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(entryId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long versionId;
	public long companyId;
	public long entryId;
	public long classNameId;
	public long classPK;
	public String typeSettings;
	public int status;

}