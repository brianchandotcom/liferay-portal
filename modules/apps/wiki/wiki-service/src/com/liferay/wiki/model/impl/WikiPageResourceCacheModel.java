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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.wiki.model.WikiPageResource;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing WikiPageResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResource
 * @generated
 */
@ProviderType
public class WikiPageResourceCacheModel implements CacheModel<WikiPageResource>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WikiPageResourceCacheModel)) {
			return false;
		}

		WikiPageResourceCacheModel wikiPageResourceCacheModel = (WikiPageResourceCacheModel)obj;

		if (resourcePrimKey == wikiPageResourceCacheModel.resourcePrimKey) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, resourcePrimKey);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", nodeId=");
		sb.append(nodeId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public WikiPageResource toEntityModel() {
		WikiPageResourceImpl wikiPageResourceImpl = new WikiPageResourceImpl();

		if (uuid == null) {
			wikiPageResourceImpl.setUuid(StringPool.BLANK);
		}
		else {
			wikiPageResourceImpl.setUuid(uuid);
		}

		wikiPageResourceImpl.setResourcePrimKey(resourcePrimKey);
		wikiPageResourceImpl.setGroupId(groupId);
		wikiPageResourceImpl.setNodeId(nodeId);

		if (title == null) {
			wikiPageResourceImpl.setTitle(StringPool.BLANK);
		}
		else {
			wikiPageResourceImpl.setTitle(title);
		}

		wikiPageResourceImpl.setCompanyId(companyId);

		wikiPageResourceImpl.resetOriginalValues();

		return wikiPageResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		resourcePrimKey = objectInput.readLong();
		groupId = objectInput.readLong();
		nodeId = objectInput.readLong();
		title = objectInput.readUTF();
		companyId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(resourcePrimKey);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(nodeId);

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long resourcePrimKey;
	public long groupId;
	public long nodeId;
	public String title;
	public long companyId;
}