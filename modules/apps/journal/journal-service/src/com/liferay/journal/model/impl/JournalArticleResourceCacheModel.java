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

package com.liferay.journal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.model.JournalArticleResource;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JournalArticleResource in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResource
 * @generated
 */
@ProviderType
public class JournalArticleResourceCacheModel implements CacheModel<JournalArticleResource>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalArticleResourceCacheModel)) {
			return false;
		}

		JournalArticleResourceCacheModel journalArticleResourceCacheModel = (JournalArticleResourceCacheModel)obj;

		if (resourcePrimKey == journalArticleResourceCacheModel.resourcePrimKey) {
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
		StringBundler sb = new StringBundler(11);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public JournalArticleResource toEntityModel() {
		JournalArticleResourceImpl journalArticleResourceImpl = new JournalArticleResourceImpl();

		if (uuid == null) {
			journalArticleResourceImpl.setUuid(StringPool.BLANK);
		}
		else {
			journalArticleResourceImpl.setUuid(uuid);
		}

		journalArticleResourceImpl.setResourcePrimKey(resourcePrimKey);
		journalArticleResourceImpl.setGroupId(groupId);

		if (articleId == null) {
			journalArticleResourceImpl.setArticleId(StringPool.BLANK);
		}
		else {
			journalArticleResourceImpl.setArticleId(articleId);
		}

		journalArticleResourceImpl.setCompanyId(companyId);

		journalArticleResourceImpl.resetOriginalValues();

		return journalArticleResourceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		resourcePrimKey = objectInput.readLong();
		groupId = objectInput.readLong();
		articleId = objectInput.readUTF();
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

		if (articleId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(articleId);
		}

		objectOutput.writeLong(companyId);
	}

	public String uuid;
	public long resourcePrimKey;
	public long groupId;
	public String articleId;
	public long companyId;
}