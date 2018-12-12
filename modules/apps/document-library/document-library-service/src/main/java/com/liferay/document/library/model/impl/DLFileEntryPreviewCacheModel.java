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

import com.liferay.document.library.model.DLFileEntryPreview;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLFileEntryPreview in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPreview
 * @generated
 */
@ProviderType
public class DLFileEntryPreviewCacheModel implements CacheModel<DLFileEntryPreview>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileEntryPreviewCacheModel)) {
			return false;
		}

		DLFileEntryPreviewCacheModel dlFileEntryPreviewCacheModel = (DLFileEntryPreviewCacheModel)obj;

		if (fileEntryPreviewId == dlFileEntryPreviewCacheModel.fileEntryPreviewId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fileEntryPreviewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{fileEntryPreviewId=");
		sb.append(fileEntryPreviewId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", previewType=");
		sb.append(previewType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLFileEntryPreview toEntityModel() {
		DLFileEntryPreviewImpl dlFileEntryPreviewImpl = new DLFileEntryPreviewImpl();

		dlFileEntryPreviewImpl.setFileEntryPreviewId(fileEntryPreviewId);
		dlFileEntryPreviewImpl.setGroupId(groupId);
		dlFileEntryPreviewImpl.setFileEntryId(fileEntryId);
		dlFileEntryPreviewImpl.setFileVersionId(fileVersionId);
		dlFileEntryPreviewImpl.setPreviewType(previewType);

		dlFileEntryPreviewImpl.resetOriginalValues();

		return dlFileEntryPreviewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		fileEntryPreviewId = objectInput.readLong();

		groupId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		fileVersionId = objectInput.readLong();

		previewType = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(fileEntryPreviewId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(fileVersionId);

		objectOutput.writeInt(previewType);
	}

	public long fileEntryPreviewId;
	public long groupId;
	public long fileEntryId;
	public long fileVersionId;
	public int previewType;
}