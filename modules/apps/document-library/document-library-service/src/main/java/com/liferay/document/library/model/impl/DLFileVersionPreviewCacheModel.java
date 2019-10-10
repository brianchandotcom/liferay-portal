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

import com.liferay.document.library.model.DLFileVersionPreview;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLFileVersionPreview in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileVersionPreviewCacheModel
	implements CacheModel<DLFileVersionPreview>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLFileVersionPreviewCacheModel)) {
			return false;
		}

		DLFileVersionPreviewCacheModel dlFileVersionPreviewCacheModel =
			(DLFileVersionPreviewCacheModel)obj;

		if (dlFileVersionPreviewId ==
				dlFileVersionPreviewCacheModel.dlFileVersionPreviewId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, dlFileVersionPreviewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{dlFileVersionPreviewId=");
		sb.append(dlFileVersionPreviewId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
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
	public DLFileVersionPreview toEntityModel() {
		DLFileVersionPreviewImpl dlFileVersionPreviewImpl =
			new DLFileVersionPreviewImpl();

		dlFileVersionPreviewImpl.setDlFileVersionPreviewId(
			dlFileVersionPreviewId);
		dlFileVersionPreviewImpl.setGroupId(groupId);
		dlFileVersionPreviewImpl.setCompanyId(companyId);
		dlFileVersionPreviewImpl.setFileEntryId(fileEntryId);
		dlFileVersionPreviewImpl.setFileVersionId(fileVersionId);
		dlFileVersionPreviewImpl.setPreviewStatus(previewStatus);

		dlFileVersionPreviewImpl.resetOriginalValues();

		return dlFileVersionPreviewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		dlFileVersionPreviewId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		fileEntryId = objectInput.readLong();

		fileVersionId = objectInput.readLong();

		previewStatus = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(dlFileVersionPreviewId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(fileEntryId);

		objectOutput.writeLong(fileVersionId);

		objectOutput.writeInt(previewStatus);
	}

	public long dlFileVersionPreviewId;
	public long groupId;
	public long companyId;
	public long fileEntryId;
	public long fileVersionId;
	public int previewStatus;

}