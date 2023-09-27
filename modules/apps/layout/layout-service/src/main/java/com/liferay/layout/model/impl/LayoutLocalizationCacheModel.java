/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.model.impl;

import com.liferay.layout.model.LayoutLocalization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutLocalization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutLocalizationCacheModel
	implements CacheModel<LayoutLocalization>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutLocalizationCacheModel)) {
			return false;
		}

		LayoutLocalizationCacheModel layoutLocalizationCacheModel =
			(LayoutLocalizationCacheModel)object;

		if ((layoutLocalizationId ==
				layoutLocalizationCacheModel.layoutLocalizationId) &&
			(mvccVersion == layoutLocalizationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutLocalizationId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutLocalizationId=");
		sb.append(layoutLocalizationId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", content=");
		sb.append(content);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutLocalization toEntityModel() {
		LayoutLocalizationImpl layoutLocalizationImpl =
			new LayoutLocalizationImpl();

		layoutLocalizationImpl.setMvccVersion(mvccVersion);
		layoutLocalizationImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			layoutLocalizationImpl.setUuid("");
		}
		else {
			layoutLocalizationImpl.setUuid(uuid);
		}

		layoutLocalizationImpl.setLayoutLocalizationId(layoutLocalizationId);
		layoutLocalizationImpl.setGroupId(groupId);
		layoutLocalizationImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			layoutLocalizationImpl.setCreateDate(null);
		}
		else {
			layoutLocalizationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutLocalizationImpl.setModifiedDate(null);
		}
		else {
			layoutLocalizationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (content == null) {
			layoutLocalizationImpl.setContent("");
		}
		else {
			layoutLocalizationImpl.setContent(content);
		}

		if (languageId == null) {
			layoutLocalizationImpl.setLanguageId("");
		}
		else {
			layoutLocalizationImpl.setLanguageId(languageId);
		}

		layoutLocalizationImpl.setPlid(plid);

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutLocalizationImpl.setLastPublishDate(null);
		}
		else {
			layoutLocalizationImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutLocalizationImpl.resetOriginalValues();

		return layoutLocalizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutLocalizationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		content = (String)objectInput.readObject();
		languageId = objectInput.readUTF();

		plid = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(layoutLocalizationId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (content == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(content);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeLong(plid);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long layoutLocalizationId;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String content;
	public String languageId;
	public long plid;
	public long lastPublishDate;

}