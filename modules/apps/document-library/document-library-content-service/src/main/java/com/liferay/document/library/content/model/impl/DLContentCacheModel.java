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

package com.liferay.document.library.content.model.impl;

import com.liferay.document.library.content.model.DLContent;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLContentCacheModel
	implements CacheModel<DLContent>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLContentCacheModel)) {
			return false;
		}

		DLContentCacheModel dlContentCacheModel = (DLContentCacheModel)obj;

		if ((contentId == dlContentCacheModel.contentId) &&
			(mvccVersion == dlContentCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, contentId);

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
		sb.append(", contentId=");
		sb.append(contentId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLContent toEntityModel() {
		DLContentImpl dlContentImpl = new DLContentImpl();

		dlContentImpl.setMvccVersion(mvccVersion);
		dlContentImpl.setContentId(contentId);
		dlContentImpl.setGroupId(groupId);
		dlContentImpl.setCompanyId(companyId);
		dlContentImpl.setRepositoryId(repositoryId);

		if (path == null) {
			dlContentImpl.setPath("");
		}
		else {
			dlContentImpl.setPath(path);
		}

		if (version == null) {
			dlContentImpl.setVersion("");
		}
		else {
			dlContentImpl.setVersion(version);
		}

		dlContentImpl.setSize(size);

		dlContentImpl.resetOriginalValues();

		return dlContentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		contentId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		repositoryId = objectInput.readLong();
		path = objectInput.readUTF();
		version = objectInput.readUTF();

		size = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(contentId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(repositoryId);

		if (path == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(path);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(size);
	}

	public long mvccVersion;
	public long contentId;
	public long groupId;
	public long companyId;
	public long repositoryId;
	public String path;
	public String version;
	public long size;

}