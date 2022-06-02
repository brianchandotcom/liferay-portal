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

package com.liferay.client.extension.model.impl;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
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
 * The cache model class for representing ClientExtensionEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ClientExtensionEntryRelCacheModel
	implements CacheModel<ClientExtensionEntryRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ClientExtensionEntryRelCacheModel)) {
			return false;
		}

		ClientExtensionEntryRelCacheModel clientExtensionEntryRelCacheModel =
			(ClientExtensionEntryRelCacheModel)object;

		if ((clientExtensionEntryRelId ==
				clientExtensionEntryRelCacheModel.clientExtensionEntryRelId) &&
			(mvccVersion == clientExtensionEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, clientExtensionEntryRelId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", clientExtensionEntryRelId=");
		sb.append(clientExtensionEntryRelId);
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
		sb.append(", cetExternalReferenceCode=");
		sb.append(cetExternalReferenceCode);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ClientExtensionEntryRel toEntityModel() {
		ClientExtensionEntryRelImpl clientExtensionEntryRelImpl =
			new ClientExtensionEntryRelImpl();

		clientExtensionEntryRelImpl.setMvccVersion(mvccVersion);
		clientExtensionEntryRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			clientExtensionEntryRelImpl.setUuid("");
		}
		else {
			clientExtensionEntryRelImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			clientExtensionEntryRelImpl.setExternalReferenceCode("");
		}
		else {
			clientExtensionEntryRelImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		clientExtensionEntryRelImpl.setClientExtensionEntryRelId(
			clientExtensionEntryRelId);
		clientExtensionEntryRelImpl.setCompanyId(companyId);
		clientExtensionEntryRelImpl.setUserId(userId);

		if (userName == null) {
			clientExtensionEntryRelImpl.setUserName("");
		}
		else {
			clientExtensionEntryRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			clientExtensionEntryRelImpl.setCreateDate(null);
		}
		else {
			clientExtensionEntryRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			clientExtensionEntryRelImpl.setModifiedDate(null);
		}
		else {
			clientExtensionEntryRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		clientExtensionEntryRelImpl.setClassNameId(classNameId);
		clientExtensionEntryRelImpl.setClassPK(classPK);

		if (cetExternalReferenceCode == null) {
			clientExtensionEntryRelImpl.setCETExternalReferenceCode("");
		}
		else {
			clientExtensionEntryRelImpl.setCETExternalReferenceCode(
				cetExternalReferenceCode);
		}

		if (type == null) {
			clientExtensionEntryRelImpl.setType("");
		}
		else {
			clientExtensionEntryRelImpl.setType(type);
		}

		clientExtensionEntryRelImpl.resetOriginalValues();

		return clientExtensionEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		clientExtensionEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		cetExternalReferenceCode = objectInput.readUTF();
		type = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(clientExtensionEntryRelId);

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

		if (cetExternalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cetExternalReferenceCode);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long clientExtensionEntryRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String cetExternalReferenceCode;
	public String type;

}