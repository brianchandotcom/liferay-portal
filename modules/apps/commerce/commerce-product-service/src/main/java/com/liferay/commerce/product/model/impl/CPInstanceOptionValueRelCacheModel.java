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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
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
 * The cache model class for representing CPInstanceOptionValueRel in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPInstanceOptionValueRelCacheModel
	implements CacheModel<CPInstanceOptionValueRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPInstanceOptionValueRelCacheModel)) {
			return false;
		}

		CPInstanceOptionValueRelCacheModel cpInstanceOptionValueRelCacheModel =
			(CPInstanceOptionValueRelCacheModel)object;

		if ((CPInstanceOptionValueRelId ==
				cpInstanceOptionValueRelCacheModel.
					CPInstanceOptionValueRelId) &&
			(mvccVersion == cpInstanceOptionValueRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPInstanceOptionValueRelId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPInstanceOptionValueRelId=");
		sb.append(CPInstanceOptionValueRelId);
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
		sb.append(", CPDefinitionOptionRelId=");
		sb.append(CPDefinitionOptionRelId);
		sb.append(", CPDefinitionOptionValueRelId=");
		sb.append(CPDefinitionOptionValueRelId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPInstanceOptionValueRel toEntityModel() {
		CPInstanceOptionValueRelImpl cpInstanceOptionValueRelImpl =
			new CPInstanceOptionValueRelImpl();

		cpInstanceOptionValueRelImpl.setMvccVersion(mvccVersion);
		cpInstanceOptionValueRelImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpInstanceOptionValueRelImpl.setUuid("");
		}
		else {
			cpInstanceOptionValueRelImpl.setUuid(uuid);
		}

		cpInstanceOptionValueRelImpl.setCPInstanceOptionValueRelId(
			CPInstanceOptionValueRelId);
		cpInstanceOptionValueRelImpl.setGroupId(groupId);
		cpInstanceOptionValueRelImpl.setCompanyId(companyId);
		cpInstanceOptionValueRelImpl.setUserId(userId);

		if (userName == null) {
			cpInstanceOptionValueRelImpl.setUserName("");
		}
		else {
			cpInstanceOptionValueRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpInstanceOptionValueRelImpl.setCreateDate(null);
		}
		else {
			cpInstanceOptionValueRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpInstanceOptionValueRelImpl.setModifiedDate(null);
		}
		else {
			cpInstanceOptionValueRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpInstanceOptionValueRelImpl.setCPDefinitionOptionRelId(
			CPDefinitionOptionRelId);
		cpInstanceOptionValueRelImpl.setCPDefinitionOptionValueRelId(
			CPDefinitionOptionValueRelId);
		cpInstanceOptionValueRelImpl.setCPInstanceId(CPInstanceId);

		cpInstanceOptionValueRelImpl.resetOriginalValues();

		return cpInstanceOptionValueRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPInstanceOptionValueRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionOptionRelId = objectInput.readLong();

		CPDefinitionOptionValueRelId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();
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

		objectOutput.writeLong(CPInstanceOptionValueRelId);

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

		objectOutput.writeLong(CPDefinitionOptionRelId);

		objectOutput.writeLong(CPDefinitionOptionValueRelId);

		objectOutput.writeLong(CPInstanceId);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long CPInstanceOptionValueRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionOptionRelId;
	public long CPDefinitionOptionValueRelId;
	public long CPInstanceId;

}