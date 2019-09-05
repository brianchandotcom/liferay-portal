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

package com.liferay.portlet.asset.model.impl;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetVocabulary in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetVocabularyCacheModel
	implements CacheModel<AssetVocabulary>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetVocabularyCacheModel)) {
			return false;
		}

		AssetVocabularyCacheModel assetVocabularyCacheModel =
			(AssetVocabularyCacheModel)obj;

		if (vocabularyId == assetVocabularyCacheModel.vocabularyId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, vocabularyId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", vocabularyId=");
		sb.append(vocabularyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetVocabulary toEntityModel() {
		AssetVocabularyImpl assetVocabularyImpl = new AssetVocabularyImpl();

		if (uuid == null) {
			assetVocabularyImpl.setUuid("");
		}
		else {
			assetVocabularyImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			assetVocabularyImpl.setExternalReferenceCode("");
		}
		else {
			assetVocabularyImpl.setExternalReferenceCode(externalReferenceCode);
		}

		assetVocabularyImpl.setVocabularyId(vocabularyId);
		assetVocabularyImpl.setGroupId(groupId);
		assetVocabularyImpl.setCompanyId(companyId);
		assetVocabularyImpl.setUserId(userId);

		if (userName == null) {
			assetVocabularyImpl.setUserName("");
		}
		else {
			assetVocabularyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetVocabularyImpl.setCreateDate(null);
		}
		else {
			assetVocabularyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetVocabularyImpl.setModifiedDate(null);
		}
		else {
			assetVocabularyImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			assetVocabularyImpl.setName("");
		}
		else {
			assetVocabularyImpl.setName(name);
		}

		if (title == null) {
			assetVocabularyImpl.setTitle("");
		}
		else {
			assetVocabularyImpl.setTitle(title);
		}

		if (description == null) {
			assetVocabularyImpl.setDescription("");
		}
		else {
			assetVocabularyImpl.setDescription(description);
		}

		if (settings == null) {
			assetVocabularyImpl.setSettings("");
		}
		else {
			assetVocabularyImpl.setSettings(settings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			assetVocabularyImpl.setLastPublishDate(null);
		}
		else {
			assetVocabularyImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		assetVocabularyImpl.resetOriginalValues();

		return assetVocabularyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		vocabularyId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		settings = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(vocabularyId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

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

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public String externalReferenceCode;
	public long vocabularyId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String title;
	public String description;
	public String settings;
	public long lastPublishDate;

}