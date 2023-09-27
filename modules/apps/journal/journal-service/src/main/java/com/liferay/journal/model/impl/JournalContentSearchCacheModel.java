/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model.impl;

import com.liferay.journal.model.JournalContentSearch;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JournalContentSearch in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalContentSearchCacheModel
	implements CacheModel<JournalContentSearch>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof JournalContentSearchCacheModel)) {
			return false;
		}

		JournalContentSearchCacheModel journalContentSearchCacheModel =
			(JournalContentSearchCacheModel)object;

		if ((contentSearchId ==
				journalContentSearchCacheModel.contentSearchId) &&
			(mvccVersion == journalContentSearchCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, contentSearchId);

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", contentSearchId=");
		sb.append(contentSearchId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
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
		JournalContentSearchImpl journalContentSearchImpl =
			new JournalContentSearchImpl();

		journalContentSearchImpl.setMvccVersion(mvccVersion);
		journalContentSearchImpl.setCtCollectionId(ctCollectionId);
		journalContentSearchImpl.setContentSearchId(contentSearchId);
		journalContentSearchImpl.setGroupId(groupId);
		journalContentSearchImpl.setCompanyId(companyId);
		journalContentSearchImpl.setPrivateLayout(privateLayout);
		journalContentSearchImpl.setLayoutId(layoutId);

		if (portletId == null) {
			journalContentSearchImpl.setPortletId("");
		}
		else {
			journalContentSearchImpl.setPortletId(portletId);
		}

		if (articleId == null) {
			journalContentSearchImpl.setArticleId("");
		}
		else {
			journalContentSearchImpl.setArticleId(articleId);
		}

		journalContentSearchImpl.resetOriginalValues();

		return journalContentSearchImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		contentSearchId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		privateLayout = objectInput.readBoolean();

		layoutId = objectInput.readLong();
		portletId = objectInput.readUTF();
		articleId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(contentSearchId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeBoolean(privateLayout);

		objectOutput.writeLong(layoutId);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (articleId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(articleId);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long contentSearchId;
	public long groupId;
	public long companyId;
	public boolean privateLayout;
	public long layoutId;
	public String portletId;
	public String articleId;

}