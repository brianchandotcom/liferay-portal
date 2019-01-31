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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LVEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryLocalization
 * @generated
 */
@ProviderType
public class LVEntryLocalizationCacheModel implements CacheModel<LVEntryLocalization>,
	Externalizable, MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LVEntryLocalizationCacheModel)) {
			return false;
		}

		LVEntryLocalizationCacheModel lvEntryLocalizationCacheModel = (LVEntryLocalizationCacheModel)obj;

		if ((lvEntryLocalizationId == lvEntryLocalizationCacheModel.lvEntryLocalizationId) &&
				(mvccVersion == lvEntryLocalizationCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, lvEntryLocalizationId);

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
		sb.append(", headId=");
		sb.append(headId);
		sb.append(", lvEntryLocalizationId=");
		sb.append(lvEntryLocalizationId);
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
	public LVEntryLocalization toEntityModel() {
		LVEntryLocalizationImpl lvEntryLocalizationImpl = new LVEntryLocalizationImpl();

		lvEntryLocalizationImpl.setMvccVersion(mvccVersion);
		lvEntryLocalizationImpl.setHeadId(headId);
		lvEntryLocalizationImpl.setLvEntryLocalizationId(lvEntryLocalizationId);
		lvEntryLocalizationImpl.setLvEntryId(lvEntryId);

		if (languageId == null) {
			lvEntryLocalizationImpl.setLanguageId("");
		}
		else {
			lvEntryLocalizationImpl.setLanguageId(languageId);
		}

		if (title == null) {
			lvEntryLocalizationImpl.setTitle("");
		}
		else {
			lvEntryLocalizationImpl.setTitle(title);
		}

		if (content == null) {
			lvEntryLocalizationImpl.setContent("");
		}
		else {
			lvEntryLocalizationImpl.setContent(content);
		}

		lvEntryLocalizationImpl.setHead(head);

		lvEntryLocalizationImpl.resetOriginalValues();

		return lvEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		headId = objectInput.readLong();

		lvEntryLocalizationId = objectInput.readLong();

		lvEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		content = objectInput.readUTF();

		head = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(headId);

		objectOutput.writeLong(lvEntryLocalizationId);

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

		objectOutput.writeBoolean(head);
	}

	public long mvccVersion;
	public long headId;
	public long lvEntryLocalizationId;
	public long lvEntryId;
	public String languageId;
	public String title;
	public String content;
	public boolean head;
}