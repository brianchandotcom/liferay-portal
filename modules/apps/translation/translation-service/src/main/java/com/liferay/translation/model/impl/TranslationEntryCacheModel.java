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

package com.liferay.translation.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.translation.model.TranslationEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing TranslationEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TranslationEntryCacheModel
	implements CacheModel<TranslationEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TranslationEntryCacheModel)) {
			return false;
		}

		TranslationEntryCacheModel translationEntryCacheModel =
			(TranslationEntryCacheModel)object;

		if ((translationEntryId ==
				translationEntryCacheModel.translationEntryId) &&
			(mvccVersion == translationEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, translationEntryId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", translationEntryId=");
		sb.append(translationEntryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", content=");
		sb.append(content);
		sb.append(", contentType=");
		sb.append(contentType);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TranslationEntry toEntityModel() {
		TranslationEntryImpl translationEntryImpl = new TranslationEntryImpl();

		translationEntryImpl.setMvccVersion(mvccVersion);
		translationEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			translationEntryImpl.setUuid("");
		}
		else {
			translationEntryImpl.setUuid(uuid);
		}

		translationEntryImpl.setTranslationEntryId(translationEntryId);
		translationEntryImpl.setGroupId(groupId);
		translationEntryImpl.setCompanyId(companyId);
		translationEntryImpl.setUserId(userId);

		if (userName == null) {
			translationEntryImpl.setUserName("");
		}
		else {
			translationEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			translationEntryImpl.setCreateDate(null);
		}
		else {
			translationEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			translationEntryImpl.setModifiedDate(null);
		}
		else {
			translationEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		translationEntryImpl.setClassNameId(classNameId);
		translationEntryImpl.setClassPK(classPK);

		if (content == null) {
			translationEntryImpl.setContent("");
		}
		else {
			translationEntryImpl.setContent(content);
		}

		if (contentType == null) {
			translationEntryImpl.setContentType("");
		}
		else {
			translationEntryImpl.setContentType(contentType);
		}

		if (languageId == null) {
			translationEntryImpl.setLanguageId("");
		}
		else {
			translationEntryImpl.setLanguageId(languageId);
		}

		translationEntryImpl.setStatus(status);
		translationEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			translationEntryImpl.setStatusByUserName("");
		}
		else {
			translationEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			translationEntryImpl.setStatusDate(null);
		}
		else {
			translationEntryImpl.setStatusDate(new Date(statusDate));
		}

		translationEntryImpl.resetOriginalValues();

		return translationEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		translationEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		content = (String)objectInput.readObject();
		contentType = objectInput.readUTF();
		languageId = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(translationEntryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (content == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(content);
		}

		if (contentType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contentType);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long translationEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String content;
	public String contentType;
	public String languageId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}