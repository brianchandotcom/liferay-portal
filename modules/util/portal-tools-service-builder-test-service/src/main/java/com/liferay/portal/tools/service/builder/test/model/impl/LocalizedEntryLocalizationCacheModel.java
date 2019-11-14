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

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.model.LocalizedEntryLocalization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LocalizedEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LocalizedEntryLocalizationCacheModel
	implements CacheModel<LocalizedEntryLocalization>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedEntryLocalizationCacheModel)) {
			return false;
		}

		LocalizedEntryLocalizationCacheModel
			localizedEntryLocalizationCacheModel =
				(LocalizedEntryLocalizationCacheModel)obj;

		if ((localizedEntryLocalizationId ==
				localizedEntryLocalizationCacheModel.
					localizedEntryLocalizationId) &&
			(mvccVersion == localizedEntryLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, localizedEntryLocalizationId);

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", localizedEntryLocalizationId=");
		sb.append(localizedEntryLocalizationId);
		sb.append(", localizedEntryId=");
		sb.append(localizedEntryId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", content=");
		sb.append(content);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LocalizedEntryLocalization toEntityModel() {
		LocalizedEntryLocalizationImpl localizedEntryLocalizationImpl =
			new LocalizedEntryLocalizationImpl();

		localizedEntryLocalizationImpl.setMvccVersion(mvccVersion);
		localizedEntryLocalizationImpl.setLocalizedEntryLocalizationId(
			localizedEntryLocalizationId);
		localizedEntryLocalizationImpl.setLocalizedEntryId(localizedEntryId);

		if (languageId == null) {
			localizedEntryLocalizationImpl.setLanguageId("");
		}
		else {
			localizedEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			localizedEntryLocalizationImpl.setTitle("");
		}
		else {
			localizedEntryLocalizationImpl.setTitle(title);
		}

		if (content == null) {
			localizedEntryLocalizationImpl.setContent("");
		}
		else {
			localizedEntryLocalizationImpl.setContent(content);
		}

		localizedEntryLocalizationImpl.resetOriginalValues();

		return localizedEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		localizedEntryLocalizationId = objectInput.readLong();

		localizedEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(localizedEntryLocalizationId);

		objectOutput.writeLong(localizedEntryId);

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (content == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(content);
		}
	}

	public long mvccVersion;
	public long localizedEntryLocalizationId;
	public long localizedEntryId;
	public String languageId;
	public String title;
	public String content;

}