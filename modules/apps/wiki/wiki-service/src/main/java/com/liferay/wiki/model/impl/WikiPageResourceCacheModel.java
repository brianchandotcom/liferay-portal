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

package com.liferay.wiki.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.wiki.model.WikiPageResource;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing WikiPageResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WikiPageResourceCacheModel
	implements CacheModel<WikiPageResource>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WikiPageResourceCacheModel)) {
			return false;
		}

		WikiPageResourceCacheModel wikiPageResourceCacheModel =
			(WikiPageResourceCacheModel)obj;

		if ((resourcePrimKey == wikiPageResourceCacheModel.resourcePrimKey) &&
			(mvccVersion == wikiPageResourceCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, resourcePrimKey);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", nodeId=");
		sb.append(nodeId);
		sb.append(", title=");
		sb.append(title);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WikiPageResource toEntityModel() {
		WikiPageResourceImpl wikiPageResourceImpl = new WikiPageResourceImpl();

		wikiPageResourceImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			wikiPageResourceImpl.setUuid("");
		}
		else {
			wikiPageResourceImpl.setUuid(uuid);
		}

		wikiPageResourceImpl.setResourcePrimKey(resourcePrimKey);
		wikiPageResourceImpl.setGroupId(groupId);
		wikiPageResourceImpl.setCompanyId(companyId);
		wikiPageResourceImpl.setNodeId(nodeId);

		if (title == null) {
			wikiPageResourceImpl.setTitle("");
		}
		else {
			wikiPageResourceImpl.setTitle(title);
		}

		wikiPageResourceImpl.resetOriginalValues();

		return wikiPageResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		resourcePrimKey = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		nodeId = objectInput.readLong();
		title = objectInput.readUTF();
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

		objectOutput.writeLong(resourcePrimKey);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(nodeId);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long resourcePrimKey;
	public long groupId;
	public long companyId;
	public long nodeId;
	public String title;

}