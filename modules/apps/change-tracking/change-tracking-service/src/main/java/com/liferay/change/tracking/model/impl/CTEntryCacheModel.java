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

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CTEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTEntryCacheModel
	implements CacheModel<CTEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTEntryCacheModel)) {
			return false;
		}

		CTEntryCacheModel ctEntryCacheModel = (CTEntryCacheModel)obj;

		if ((ctEntryId == ctEntryCacheModel.ctEntryId) &&
			(mvccVersion == ctEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctEntryId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctEntryId=");
		sb.append(ctEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);
		sb.append(", modelClassPK=");
		sb.append(modelClassPK);
		sb.append(", modelMvccVersion=");
		sb.append(modelMvccVersion);
		sb.append(", modelResourcePrimKey=");
		sb.append(modelResourcePrimKey);
		sb.append(", changeType=");
		sb.append(changeType);
		sb.append(", collision=");
		sb.append(collision);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTEntry toEntityModel() {
		CTEntryImpl ctEntryImpl = new CTEntryImpl();

		ctEntryImpl.setMvccVersion(mvccVersion);
		ctEntryImpl.setCtEntryId(ctEntryId);
		ctEntryImpl.setCompanyId(companyId);
		ctEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			ctEntryImpl.setCreateDate(null);
		}
		else {
			ctEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ctEntryImpl.setModifiedDate(null);
		}
		else {
			ctEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		ctEntryImpl.setCtCollectionId(ctCollectionId);
		ctEntryImpl.setModelClassNameId(modelClassNameId);
		ctEntryImpl.setModelClassPK(modelClassPK);
		ctEntryImpl.setModelMvccVersion(modelMvccVersion);
		ctEntryImpl.setModelResourcePrimKey(modelResourcePrimKey);
		ctEntryImpl.setChangeType(changeType);
		ctEntryImpl.setCollision(collision);
		ctEntryImpl.setStatus(status);

		ctEntryImpl.resetOriginalValues();

		return ctEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		modelClassNameId = objectInput.readLong();

		modelClassPK = objectInput.readLong();

		modelMvccVersion = objectInput.readLong();

		modelResourcePrimKey = objectInput.readLong();

		changeType = objectInput.readInt();

		collision = objectInput.readBoolean();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(modelClassNameId);

		objectOutput.writeLong(modelClassPK);

		objectOutput.writeLong(modelMvccVersion);

		objectOutput.writeLong(modelResourcePrimKey);

		objectOutput.writeInt(changeType);

		objectOutput.writeBoolean(collision);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long ctEntryId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long ctCollectionId;
	public long modelClassNameId;
	public long modelClassPK;
	public long modelMvccVersion;
	public long modelResourcePrimKey;
	public int changeType;
	public boolean collision;
	public int status;

}