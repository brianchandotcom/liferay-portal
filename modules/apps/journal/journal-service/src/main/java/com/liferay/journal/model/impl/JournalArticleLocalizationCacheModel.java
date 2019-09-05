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

import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing JournalArticleLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleLocalizationCacheModel
	implements CacheModel<JournalArticleLocalization>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalArticleLocalizationCacheModel)) {
			return false;
		}

		JournalArticleLocalizationCacheModel
			journalArticleLocalizationCacheModel =
				(JournalArticleLocalizationCacheModel)obj;

		if ((articleLocalizationId ==
				journalArticleLocalizationCacheModel.articleLocalizationId) &&
			(mvccVersion == journalArticleLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, articleLocalizationId);

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
		sb.append(", articleLocalizationId=");
		sb.append(articleLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", articlePK=");
		sb.append(articlePK);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public JournalArticleLocalization toEntityModel() {
		JournalArticleLocalizationImpl journalArticleLocalizationImpl =
			new JournalArticleLocalizationImpl();

		journalArticleLocalizationImpl.setMvccVersion(mvccVersion);
		journalArticleLocalizationImpl.setArticleLocalizationId(
			articleLocalizationId);
		journalArticleLocalizationImpl.setCompanyId(companyId);
		journalArticleLocalizationImpl.setArticlePK(articlePK);

		if (title == null) {
			journalArticleLocalizationImpl.setTitle("");
		}
		else {
			journalArticleLocalizationImpl.setTitle(title);
		}

		if (description == null) {
			journalArticleLocalizationImpl.setDescription("");
		}
		else {
			journalArticleLocalizationImpl.setDescription(description);
		}

		if (languageId == null) {
			journalArticleLocalizationImpl.setLanguageId("");
		}
		else {
			journalArticleLocalizationImpl.setLanguageId(languageId);
		}

		journalArticleLocalizationImpl.resetOriginalValues();

		return journalArticleLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		articleLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		articlePK = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		languageId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(articleLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(articlePK);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}
	}

	public long mvccVersion;
	public long articleLocalizationId;
	public long companyId;
	public long articlePK;
	public String title;
	public String description;
	public String languageId;

}