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
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalizationVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LVEntryLocalizationVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationVersionCacheModel
	implements CacheModel<LVEntryLocalizationVersion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryLocalizationVersionCacheModel)) {
			return false;
		}

		LVEntryLocalizationVersionCacheModel
			lvEntryLocalizationVersionCacheModel =
				(LVEntryLocalizationVersionCacheModel)obj;

		if (lvEntryLocalizationVersionId ==
				lvEntryLocalizationVersionCacheModel.
					lvEntryLocalizationVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, lvEntryLocalizationVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{lvEntryLocalizationVersionId=");
		sb.append(lvEntryLocalizationVersionId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", lvEntryLocalizationId=");
		sb.append(lvEntryLocalizationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", lvEntryId=");
		sb.append(lvEntryId);
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
	public LVEntryLocalizationVersion toEntityModel() {
		LVEntryLocalizationVersionImpl lvEntryLocalizationVersionImpl =
			new LVEntryLocalizationVersionImpl();

		lvEntryLocalizationVersionImpl.setLvEntryLocalizationVersionId(
			lvEntryLocalizationVersionId);
		lvEntryLocalizationVersionImpl.setVersion(version);
		lvEntryLocalizationVersionImpl.setLvEntryLocalizationId(
			lvEntryLocalizationId);
		lvEntryLocalizationVersionImpl.setCompanyId(companyId);
		lvEntryLocalizationVersionImpl.setLvEntryId(lvEntryId);

		if (languageId == null) {
			lvEntryLocalizationVersionImpl.setLanguageId("");
		}
		else {
			lvEntryLocalizationVersionImpl.setLanguageId(languageId);
		}

		if (title == null) {
			lvEntryLocalizationVersionImpl.setTitle("");
		}
		else {
			lvEntryLocalizationVersionImpl.setTitle(title);
		}

		if (content == null) {
			lvEntryLocalizationVersionImpl.setContent("");
		}
		else {
			lvEntryLocalizationVersionImpl.setContent(content);
		}

		lvEntryLocalizationVersionImpl.resetOriginalValues();

		return lvEntryLocalizationVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		lvEntryLocalizationVersionId = objectInput.readLong();

		version = objectInput.readInt();

		lvEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		lvEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(lvEntryLocalizationVersionId);

		objectOutput.writeInt(version);

		objectOutput.writeLong(lvEntryLocalizationId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(lvEntryId);

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

	public long lvEntryLocalizationVersionId;
	public int version;
	public long lvEntryLocalizationId;
	public long companyId;
	public long lvEntryId;
	public String languageId;
	public String title;
	public String content;

}