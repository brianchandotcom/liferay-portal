/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.RepositoryEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing RepositoryEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see RepositoryEntry
 * @generated
 */
public class RepositoryEntryCacheModel implements CacheModel<RepositoryEntry>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", repositoryEntryId=");
		sb.append(repositoryEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", mappedId=");
		sb.append(mappedId);
		sb.append(", manualCheckInRequired=");
		sb.append(manualCheckInRequired);
		sb.append("}");

		return sb.toString();
	}

	public RepositoryEntry toEntityModel() {
		RepositoryEntryImpl repositoryEntryImpl = new RepositoryEntryImpl();

		if (uuid == null) {
			repositoryEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			repositoryEntryImpl.setUuid(uuid);
		}

		repositoryEntryImpl.setRepositoryEntryId(repositoryEntryId);
		repositoryEntryImpl.setGroupId(groupId);
		repositoryEntryImpl.setRepositoryId(repositoryId);

		if (mappedId == null) {
			repositoryEntryImpl.setMappedId(StringPool.BLANK);
		}
		else {
			repositoryEntryImpl.setMappedId(mappedId);
		}

		repositoryEntryImpl.setManualCheckInRequired(manualCheckInRequired);

		repositoryEntryImpl.resetOriginalValues();

		return repositoryEntryImpl;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		repositoryEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		repositoryId = objectInput.readLong();
		mappedId = objectInput.readUTF();

		manualCheckInRequired = objectInput.readBoolean();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(repositoryEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(repositoryId);

		if (mappedId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(mappedId);
		}

		objectOutput.writeBoolean(manualCheckInRequired);
	}

	public String uuid;
	public long repositoryEntryId;
	public long groupId;
	public long repositoryId;
	public String mappedId;
	public boolean manualCheckInRequired;
}