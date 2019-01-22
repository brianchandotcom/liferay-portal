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

package com.liferay.document.library.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.model.FileVersionPreview;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing FileVersionPreview in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FileVersionPreview
 * @generated
 */
@ProviderType
public class FileVersionPreviewCacheModel implements CacheModel<FileVersionPreview>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FileVersionPreviewCacheModel)) {
			return false;
		}

		FileVersionPreviewCacheModel fileVersionPreviewCacheModel = (FileVersionPreviewCacheModel)obj;

		if (fileVersionPreviewId == fileVersionPreviewCacheModel.fileVersionPreviewId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fileVersionPreviewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{fileVersionPreviewId=");
		sb.append(fileVersionPreviewId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", previewStatus=");
		sb.append(previewStatus);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FileVersionPreview toEntityModel() {
		FileVersionPreviewImpl fileVersionPreviewImpl = new FileVersionPreviewImpl();

		fileVersionPreviewImpl.setFileVersionPreviewId(fileVersionPreviewId);
		fileVersionPreviewImpl.setGroupId(groupId);
		fileVersionPreviewImpl.setFileEntryId(fileEntryId);
		fileVersionPreviewImpl.setFileVersionId(fileVersionId);
		fileVersionPreviewImpl.setPreviewStatus(previewStatus);

		fileVersionPreviewImpl.resetOriginalValues();

		return fileVersionPreviewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		fileVersionPreviewId = objectInput.readLong();

		groupId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		fileVersionId = objectInput.readLong();

		previewStatus = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(fileVersionPreviewId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(fileVersionId);

		objectOutput.writeInt(previewStatus);
	}

	public long fileVersionPreviewId;
	public long groupId;
	public long fileEntryId;
	public long fileVersionId;
	public int previewStatus;
}