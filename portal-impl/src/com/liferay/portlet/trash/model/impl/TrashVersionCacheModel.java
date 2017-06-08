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

package com.liferay.portlet.trash.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.trash.kernel.model.TrashVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing TrashVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see TrashVersion
 * @deprecated As of 7.0.0, replaced by {@link
           com.liferay.trash.model.impl.TrashVersionImpl}
 * @generated
 */
@Deprecated
@ProviderType
public class TrashVersionCacheModel implements CacheModel<TrashVersion>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TrashVersionCacheModel)) {
			return false;
		}

		TrashVersionCacheModel trashVersionCacheModel = (TrashVersionCacheModel)obj;

		if (versionId == trashVersionCacheModel.versionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, versionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{versionId=");
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

		trashVersionImpl.setVersionId(versionId);
		trashVersionImpl.setCompanyId(companyId);
		trashVersionImpl.setEntryId(entryId);
		trashVersionImpl.setClassNameId(classNameId);
		trashVersionImpl.setClassPK(classPK);

		if (typeSettings == null) {
			trashVersionImpl.setTypeSettings(StringPool.BLANK);
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
		versionId = objectInput.readLong();

		companyId = objectInput.readLong();

		entryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		typeSettings = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(versionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(entryId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeInt(status);
	}

	public long versionId;
	public long companyId;
	public long entryId;
	public long classNameId;
	public long classPK;
	public String typeSettings;
	public int status;
}