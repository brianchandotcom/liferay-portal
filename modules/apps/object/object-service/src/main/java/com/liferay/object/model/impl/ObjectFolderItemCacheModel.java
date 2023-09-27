/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFolderItem;
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
 * The cache model class for representing ObjectFolderItem in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class ObjectFolderItemCacheModel
	implements CacheModel<ObjectFolderItem>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ObjectFolderItemCacheModel)) {
			return false;
		}

		ObjectFolderItemCacheModel objectFolderItemCacheModel =
			(ObjectFolderItemCacheModel)object;

		if ((objectFolderItemId ==
				objectFolderItemCacheModel.objectFolderItemId) &&
			(mvccVersion == objectFolderItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, objectFolderItemId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", objectFolderItemId=");
		sb.append(objectFolderItemId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", objectFolderId=");
		sb.append(objectFolderId);
		sb.append(", positionX=");
		sb.append(positionX);
		sb.append(", positionY=");
		sb.append(positionY);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ObjectFolderItem toEntityModel() {
		ObjectFolderItemImpl objectFolderItemImpl = new ObjectFolderItemImpl();

		objectFolderItemImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			objectFolderItemImpl.setUuid("");
		}
		else {
			objectFolderItemImpl.setUuid(uuid);
		}

		objectFolderItemImpl.setObjectFolderItemId(objectFolderItemId);
		objectFolderItemImpl.setCompanyId(companyId);
		objectFolderItemImpl.setUserId(userId);

		if (userName == null) {
			objectFolderItemImpl.setUserName("");
		}
		else {
			objectFolderItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			objectFolderItemImpl.setCreateDate(null);
		}
		else {
			objectFolderItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			objectFolderItemImpl.setModifiedDate(null);
		}
		else {
			objectFolderItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		objectFolderItemImpl.setObjectDefinitionId(objectDefinitionId);
		objectFolderItemImpl.setObjectFolderId(objectFolderId);
		objectFolderItemImpl.setPositionX(positionX);
		objectFolderItemImpl.setPositionY(positionY);

		objectFolderItemImpl.resetOriginalValues();

		return objectFolderItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		objectFolderItemId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();

		objectFolderId = objectInput.readLong();

		positionX = objectInput.readInt();

		positionY = objectInput.readInt();
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

		objectOutput.writeLong(objectFolderItemId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(objectDefinitionId);

		objectOutput.writeLong(objectFolderId);

		objectOutput.writeInt(positionX);

		objectOutput.writeInt(positionY);
	}

	public long mvccVersion;
	public String uuid;
	public long objectFolderItemId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public long objectFolderId;
	public int positionX;
	public int positionY;

}