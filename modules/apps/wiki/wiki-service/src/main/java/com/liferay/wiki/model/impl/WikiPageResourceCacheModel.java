/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof WikiPageResourceCacheModel)) {
			return false;
		}

		WikiPageResourceCacheModel wikiPageResourceCacheModel =
			(WikiPageResourceCacheModel)object;

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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
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
		wikiPageResourceImpl.setCtCollectionId(ctCollectionId);

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

		ctCollectionId = objectInput.readLong();
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

		objectOutput.writeLong(ctCollectionId);

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
	public long ctCollectionId;
	public String uuid;
	public long resourcePrimKey;
	public long groupId;
	public long companyId;
	public long nodeId;
	public String title;

}