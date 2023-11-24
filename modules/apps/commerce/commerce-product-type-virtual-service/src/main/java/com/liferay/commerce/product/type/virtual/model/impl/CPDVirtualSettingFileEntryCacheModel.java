/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model.impl;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
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
 * The cache model class for representing CPDVirtualSettingFileEntry in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDVirtualSettingFileEntryCacheModel
	implements CacheModel<CPDVirtualSettingFileEntry>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDVirtualSettingFileEntryCacheModel)) {
			return false;
		}

		CPDVirtualSettingFileEntryCacheModel
			cpdVirtualSettingFileEntryCacheModel =
				(CPDVirtualSettingFileEntryCacheModel)object;

		if ((CPDefinitionVirtualSettingFileEntryId ==
				cpdVirtualSettingFileEntryCacheModel.
					CPDefinitionVirtualSettingFileEntryId) &&
			(mvccVersion == cpdVirtualSettingFileEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionVirtualSettingFileEntryId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionVirtualSettingFileEntryId=");
		sb.append(CPDefinitionVirtualSettingFileEntryId);
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
		sb.append(", CPDefinitionVirtualSettingId=");
		sb.append(CPDefinitionVirtualSettingId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", url=");
		sb.append(url);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDVirtualSettingFileEntry toEntityModel() {
		CPDVirtualSettingFileEntryImpl cpdVirtualSettingFileEntryImpl =
			new CPDVirtualSettingFileEntryImpl();

		cpdVirtualSettingFileEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			cpdVirtualSettingFileEntryImpl.setUuid("");
		}
		else {
			cpdVirtualSettingFileEntryImpl.setUuid(uuid);
		}

		cpdVirtualSettingFileEntryImpl.setCPDefinitionVirtualSettingFileEntryId(
			CPDefinitionVirtualSettingFileEntryId);
		cpdVirtualSettingFileEntryImpl.setGroupId(groupId);
		cpdVirtualSettingFileEntryImpl.setCompanyId(companyId);
		cpdVirtualSettingFileEntryImpl.setUserId(userId);

		if (userName == null) {
			cpdVirtualSettingFileEntryImpl.setUserName("");
		}
		else {
			cpdVirtualSettingFileEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpdVirtualSettingFileEntryImpl.setCreateDate(null);
		}
		else {
			cpdVirtualSettingFileEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpdVirtualSettingFileEntryImpl.setModifiedDate(null);
		}
		else {
			cpdVirtualSettingFileEntryImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpdVirtualSettingFileEntryImpl.setCPDefinitionVirtualSettingId(
			CPDefinitionVirtualSettingId);
		cpdVirtualSettingFileEntryImpl.setFileEntryId(fileEntryId);

		if (url == null) {
			cpdVirtualSettingFileEntryImpl.setUrl("");
		}
		else {
			cpdVirtualSettingFileEntryImpl.setUrl(url);
		}

		if (version == null) {
			cpdVirtualSettingFileEntryImpl.setVersion("");
		}
		else {
			cpdVirtualSettingFileEntryImpl.setVersion(version);
		}

		cpdVirtualSettingFileEntryImpl.resetOriginalValues();

		return cpdVirtualSettingFileEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionVirtualSettingFileEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionVirtualSettingId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		url = objectInput.readUTF();
		version = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionVirtualSettingFileEntryId);

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

		objectOutput.writeLong(CPDefinitionVirtualSettingId);

		objectOutput.writeLong(fileEntryId);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long CPDefinitionVirtualSettingFileEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionVirtualSettingId;
	public long fileEntryId;
	public String url;
	public String version;

}