/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.RepositoryEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RepositoryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RepositoryEntryCacheModel
	implements CacheModel<RepositoryEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RepositoryEntryCacheModel)) {
			return false;
		}

		RepositoryEntryCacheModel repositoryEntryCacheModel =
			(RepositoryEntryCacheModel)object;

		if ((repositoryEntryId ==
				repositoryEntryCacheModel.repositoryEntryId) &&
			(mvccVersion == repositoryEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, repositoryEntryId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", repositoryEntryId=");
		sb.append(repositoryEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", mappedId=");
		sb.append(mappedId);
		sb.append(", manualCheckInRequired=");
		sb.append(manualCheckInRequired);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RepositoryEntry toEntityModel() {
		RepositoryEntryImpl repositoryEntryImpl = new RepositoryEntryImpl();

		repositoryEntryImpl.setMvccVersion(mvccVersion);
		repositoryEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			repositoryEntryImpl.setUuid("");
		}
		else {
			repositoryEntryImpl.setUuid(uuid);
		}

		repositoryEntryImpl.setRepositoryEntryId(repositoryEntryId);
		repositoryEntryImpl.setGroupId(groupId);
		repositoryEntryImpl.setCompanyId(companyId);
		repositoryEntryImpl.setUserId(userId);

		if (userName == null) {
			repositoryEntryImpl.setUserName("");
		}
		else {
			repositoryEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			repositoryEntryImpl.setCreateDate(null);
		}
		else {
			repositoryEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			repositoryEntryImpl.setModifiedDate(null);
		}
		else {
			repositoryEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		repositoryEntryImpl.setRepositoryId(repositoryId);

		if (mappedId == null) {
			repositoryEntryImpl.setMappedId("");
		}
		else {
			repositoryEntryImpl.setMappedId(mappedId);
		}

		repositoryEntryImpl.setManualCheckInRequired(manualCheckInRequired);

		if (lastPublishDate == Long.MIN_VALUE) {
			repositoryEntryImpl.setLastPublishDate(null);
		}
		else {
			repositoryEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		repositoryEntryImpl.resetOriginalValues();

		return repositoryEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		repositoryEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		repositoryId = objectInput.readLong();
		mappedId = objectInput.readUTF();

		manualCheckInRequired = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(repositoryEntryId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(repositoryId);

		if (mappedId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mappedId);
		}

		objectOutput.writeBoolean(manualCheckInRequired);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long repositoryEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long repositoryId;
	public String mappedId;
	public boolean manualCheckInRequired;
	public long lastPublishDate;

}