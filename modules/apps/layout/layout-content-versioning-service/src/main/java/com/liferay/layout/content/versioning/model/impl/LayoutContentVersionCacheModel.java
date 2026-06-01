/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.model.impl;

import com.liferay.layout.content.versioning.model.LayoutContentVersion;
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
 * The cache model class for representing LayoutContentVersion in entity cache.
 *
 * @author Lourdes Fernández Besada
 * @generated
 */
public class LayoutContentVersionCacheModel
	implements CacheModel<LayoutContentVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutContentVersionCacheModel)) {
			return false;
		}

		LayoutContentVersionCacheModel layoutContentVersionCacheModel =
			(LayoutContentVersionCacheModel)object;

		if ((layoutContentVersionId ==
				layoutContentVersionCacheModel.layoutContentVersionId) &&
			(mvccVersion == layoutContentVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutContentVersionId);

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", layoutContentVersionId=");
		sb.append(layoutContentVersionId);
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
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", name=");
		sb.append(name);
		sb.append(", version=");
		sb.append(version);
		sb.append(", specSchemaVersion=");
		sb.append(specSchemaVersion);
		sb.append(", data=");
		sb.append(data);
		sb.append(", dataHash=");
		sb.append(dataHash);
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
	public LayoutContentVersion toEntityModel() {
		LayoutContentVersionImpl layoutContentVersionImpl =
			new LayoutContentVersionImpl();

		layoutContentVersionImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			layoutContentVersionImpl.setExternalReferenceCode("");
		}
		else {
			layoutContentVersionImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		layoutContentVersionImpl.setLayoutContentVersionId(
			layoutContentVersionId);
		layoutContentVersionImpl.setGroupId(groupId);
		layoutContentVersionImpl.setCompanyId(companyId);
		layoutContentVersionImpl.setUserId(userId);

		if (userName == null) {
			layoutContentVersionImpl.setUserName("");
		}
		else {
			layoutContentVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutContentVersionImpl.setCreateDate(null);
		}
		else {
			layoutContentVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutContentVersionImpl.setModifiedDate(null);
		}
		else {
			layoutContentVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutContentVersionImpl.setPlid(plid);

		if (name == null) {
			layoutContentVersionImpl.setName("");
		}
		else {
			layoutContentVersionImpl.setName(name);
		}

		layoutContentVersionImpl.setVersion(version);

		if (specSchemaVersion == null) {
			layoutContentVersionImpl.setSpecSchemaVersion("");
		}
		else {
			layoutContentVersionImpl.setSpecSchemaVersion(specSchemaVersion);
		}

		if (data == null) {
			layoutContentVersionImpl.setData("");
		}
		else {
			layoutContentVersionImpl.setData(data);
		}

		if (dataHash == null) {
			layoutContentVersionImpl.setDataHash("");
		}
		else {
			layoutContentVersionImpl.setDataHash(dataHash);
		}

		layoutContentVersionImpl.setStatus(status);
		layoutContentVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			layoutContentVersionImpl.setStatusByUserName("");
		}
		else {
			layoutContentVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			layoutContentVersionImpl.setStatusDate(null);
		}
		else {
			layoutContentVersionImpl.setStatusDate(new Date(statusDate));
		}

		layoutContentVersionImpl.resetOriginalValues();

		return layoutContentVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		layoutContentVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		plid = objectInput.readLong();
		name = objectInput.readUTF();

		version = objectInput.readInt();
		specSchemaVersion = objectInput.readUTF();
		data = objectInput.readUTF();
		dataHash = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(layoutContentVersionId);

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

		objectOutput.writeLong(plid);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(version);

		if (specSchemaVersion == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(specSchemaVersion);
		}

		if (data == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(data);
		}

		if (dataHash == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(dataHash);
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
	public String externalReferenceCode;
	public long layoutContentVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long plid;
	public String name;
	public int version;
	public String specSchemaVersion;
	public String data;
	public String dataHash;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1503786054