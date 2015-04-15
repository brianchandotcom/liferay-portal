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

package com.liferay.portlet.journal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.journal.model.JournalContentSearch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JournalContentSearch in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearch
 * @generated
 */
@ProviderType
public class JournalContentSearchCacheModel implements CacheModel<JournalContentSearch>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalContentSearchCacheModel)) {
			return false;
		}

		JournalContentSearchCacheModel journalContentSearchCacheModel = (JournalContentSearchCacheModel)obj;

		if (contentSearchId == journalContentSearchCacheModel.contentSearchId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contentSearchId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{contentSearchId=");
		sb.append(contentSearchId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public JournalContentSearch toEntityModel() {
		JournalContentSearchImpl journalContentSearchImpl = new JournalContentSearchImpl();

		journalContentSearchImpl.setContentSearchId(contentSearchId);
		journalContentSearchImpl.setGroupId(groupId);
		journalContentSearchImpl.setCompanyId(companyId);
		journalContentSearchImpl.setLayoutId(layoutId);

		if (portletId == null) {
			journalContentSearchImpl.setPortletId(StringPool.BLANK);
		}
		else {
			journalContentSearchImpl.setPortletId(portletId);
		}

		if (articleId == null) {
			journalContentSearchImpl.setArticleId(StringPool.BLANK);
		}
		else {
			journalContentSearchImpl.setArticleId(articleId);
		}

		journalContentSearchImpl.resetOriginalValues();

		return journalContentSearchImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contentSearchId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		layoutId = objectInput.readLong();
		portletId = objectInput.readUTF();
		articleId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(contentSearchId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(layoutId);

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (articleId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(articleId);
		}
	}

	public long contentSearchId;
	public long groupId;
	public long companyId;
	public long layoutId;
	public String portletId;
	public String articleId;
}