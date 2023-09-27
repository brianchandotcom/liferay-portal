/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model.impl;

import com.liferay.journal.model.JournalArticleResource;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JournalArticleResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleResourceCacheModel
	implements CacheModel<JournalArticleResource>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JournalArticleResourceCacheModel)) {
			return false;
		}

		JournalArticleResourceCacheModel journalArticleResourceCacheModel =
			(JournalArticleResourceCacheModel)object;

		if ((resourcePrimKey ==
				journalArticleResourceCacheModel.resourcePrimKey) &&
			(mvccVersion == journalArticleResourceCacheModel.mvccVersion)) {

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
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public JournalArticleResource toEntityModel() {
		JournalArticleResourceImpl journalArticleResourceImpl =
			new JournalArticleResourceImpl();

		journalArticleResourceImpl.setMvccVersion(mvccVersion);
		journalArticleResourceImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			journalArticleResourceImpl.setUuid("");
		}
		else {
			journalArticleResourceImpl.setUuid(uuid);
		}

		journalArticleResourceImpl.setResourcePrimKey(resourcePrimKey);
		journalArticleResourceImpl.setGroupId(groupId);
		journalArticleResourceImpl.setCompanyId(companyId);

		if (articleId == null) {
			journalArticleResourceImpl.setArticleId("");
		}
		else {
			journalArticleResourceImpl.setArticleId(articleId);
		}

		journalArticleResourceImpl.resetOriginalValues();

		return journalArticleResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		resourcePrimKey = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		articleId = objectInput.readUTF();
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

		if (articleId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(articleId);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long resourcePrimKey;
	public long groupId;
	public long companyId;
	public String articleId;

}