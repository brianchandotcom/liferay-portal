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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLSync;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLSync in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLSync
 * @generated
 */
public class DLSyncCacheModel implements CacheModel<DLSync>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{syncId=");
		sb.append(syncId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", fileId=");
		sb.append(fileId);
		sb.append(", fileUuid=");
		sb.append(fileUuid);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", parentFolderId=");
		sb.append(parentFolderId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", event=");
		sb.append(event);
		sb.append(", type=");
		sb.append(type);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	public DLSync toEntityModel() {
		DLSyncImpl dlSyncImpl = new DLSyncImpl();

		dlSyncImpl.setSyncId(syncId);
		dlSyncImpl.setCompanyId(companyId);
		dlSyncImpl.setCreateDate(createDate);
		dlSyncImpl.setModifiedDate(modifiedDate);
		dlSyncImpl.setFileId(fileId);

		if (fileUuid == null) {
			dlSyncImpl.setFileUuid(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setFileUuid(fileUuid);
		}

		dlSyncImpl.setRepositoryId(repositoryId);
		dlSyncImpl.setParentFolderId(parentFolderId);

		if (name == null) {
			dlSyncImpl.setName(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setName(name);
		}

		if (description == null) {
			dlSyncImpl.setDescription(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setDescription(description);
		}

		if (event == null) {
			dlSyncImpl.setEvent(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setEvent(event);
		}

		if (type == null) {
			dlSyncImpl.setType(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setType(type);
		}

		if (version == null) {
			dlSyncImpl.setVersion(StringPool.BLANK);
		}
		else {
			dlSyncImpl.setVersion(version);
		}

		dlSyncImpl.resetOriginalValues();

		return dlSyncImpl;
	}

	public void readExternal(ObjectInput objectInput) throws IOException {
		syncId = objectInput.readLong();
		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		fileId = objectInput.readLong();
		fileUuid = objectInput.readUTF();
		repositoryId = objectInput.readLong();
		parentFolderId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		event = objectInput.readUTF();
		type = objectInput.readUTF();
		version = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(syncId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(fileId);

		if (fileUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(fileUuid);
		}

		objectOutput.writeLong(repositoryId);
		objectOutput.writeLong(parentFolderId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (event == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(event);
		}

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}
	}

	public long syncId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long fileId;
	public String fileUuid;
	public long repositoryId;
	public long parentFolderId;
	public String name;
	public String description;
	public String event;
	public String type;
	public String version;
}