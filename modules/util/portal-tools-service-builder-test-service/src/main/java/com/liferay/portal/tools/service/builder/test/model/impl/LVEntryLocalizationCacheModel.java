/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.tools.service.builder.test.model.LVEntryLocalization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LVEntryLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryLocalizationCacheModel
	implements CacheModel<LVEntryLocalization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LVEntryLocalizationCacheModel)) {
			return false;
		}

		LVEntryLocalizationCacheModel lvEntryLocalizationCacheModel =
			(LVEntryLocalizationCacheModel)object;

		if ((lvEntryLocalizationId ==
				lvEntryLocalizationCacheModel.lvEntryLocalizationId) &&
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
		StringBundler sb = new StringBundler(17);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", headId=");
		sb.append(headId);
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
	public LVEntryLocalization toEntityModel() {
		LVEntryLocalizationImpl lvEntryLocalizationImpl =
			new LVEntryLocalizationImpl();

		lvEntryLocalizationImpl.setMvccVersion(mvccVersion);
		lvEntryLocalizationImpl.setHeadId(headId);
		lvEntryLocalizationImpl.setHead(head);
		lvEntryLocalizationImpl.setLvEntryLocalizationId(lvEntryLocalizationId);
		lvEntryLocalizationImpl.setCompanyId(companyId);
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

		lvEntryLocalizationImpl.resetOriginalValues();

		return lvEntryLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		headId = objectInput.readLong();

		head = objectInput.readBoolean();

		lvEntryLocalizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		lvEntryId = objectInput.readLong();
		languageId = objectInput.readUTF();
		title = objectInput.readUTF();
		content = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(headId);

		objectOutput.writeBoolean(head);

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

	public long mvccVersion;
	public long headId;
	public boolean head;
	public long lvEntryLocalizationId;
	public long companyId;
	public long lvEntryId;
	public String languageId;
	public String title;
	public String content;

}