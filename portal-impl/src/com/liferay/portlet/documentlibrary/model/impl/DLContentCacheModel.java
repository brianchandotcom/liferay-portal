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

package com.liferay.portlet.documentlibrary.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.documentlibrary.model.DLContent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing DLContent in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DLContent
 * @generated
 */
@ProviderType
public class DLContentCacheModel implements CacheModel<DLContent>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLContentCacheModel)) {
			return false;
		}

		DLContentCacheModel dlContentCacheModel = (DLContentCacheModel)obj;

		if (contentId == dlContentCacheModel.contentId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contentId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{contentId=");
		sb.append(contentId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", repositoryId=");
		sb.append(repositoryId);
		sb.append(", path=");
		sb.append(path);
		sb.append(", version=");
		sb.append(version);
		sb.append(", size=");
		sb.append(size);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DLContent toEntityModel() {
		DLContentImpl dlContentImpl = new DLContentImpl();

		dlContentImpl.setContentId(contentId);
		dlContentImpl.setGroupId(groupId);
		dlContentImpl.setRepositoryId(repositoryId);

		if (path == null) {
			dlContentImpl.setPath(StringPool.BLANK);
		}
		else {
			dlContentImpl.setPath(path);
		}

		if (version == null) {
			dlContentImpl.setVersion(StringPool.BLANK);
		}
		else {
			dlContentImpl.setVersion(version);
		}

		dlContentImpl.setSize(size);
		dlContentImpl.setCompanyId(companyId);

		dlContentImpl.resetOriginalValues();

		return dlContentImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contentId = objectInput.readLong();
		groupId = objectInput.readLong();
		repositoryId = objectInput.readLong();
		path = objectInput.readUTF();
		version = objectInput.readUTF();
		size = objectInput.readLong();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(contentId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(repositoryId);

		if (path == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(path);
		}

		if (version == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(version);
		}

		objectOutput.writeLong(size);
		objectOutput.writeLong(companyId);
	}

	public long contentId;
	public long groupId;
	public long repositoryId;
	public String path;
	public String version;
	public long size;
	public long companyId;
}