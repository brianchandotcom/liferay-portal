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

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
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
 * The cache model class for representing CSDiagramSetting in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CSDiagramSettingCacheModel
	implements CacheModel<CSDiagramSetting>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CSDiagramSettingCacheModel)) {
			return false;
		}

		CSDiagramSettingCacheModel csDiagramSettingCacheModel =
			(CSDiagramSettingCacheModel)object;

		if ((CSDiagramSettingId ==
				csDiagramSettingCacheModel.CSDiagramSettingId) &&
			(mvccVersion == csDiagramSettingCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CSDiagramSettingId);

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
		sb.append(", CSDiagramSettingId=");
		sb.append(CSDiagramSettingId);
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
		sb.append(", CPAttachmentFileEntryId=");
		sb.append(CPAttachmentFileEntryId);
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", color=");
		sb.append(color);
		sb.append(", radius=");
		sb.append(radius);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CSDiagramSetting toEntityModel() {
		CSDiagramSettingImpl csDiagramSettingImpl = new CSDiagramSettingImpl();

		csDiagramSettingImpl.setMvccVersion(mvccVersion);
		csDiagramSettingImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			csDiagramSettingImpl.setUuid("");
		}
		else {
			csDiagramSettingImpl.setUuid(uuid);
		}

		csDiagramSettingImpl.setCSDiagramSettingId(CSDiagramSettingId);
		csDiagramSettingImpl.setCompanyId(companyId);
		csDiagramSettingImpl.setUserId(userId);

		if (userName == null) {
			csDiagramSettingImpl.setUserName("");
		}
		else {
			csDiagramSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			csDiagramSettingImpl.setCreateDate(null);
		}
		else {
			csDiagramSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			csDiagramSettingImpl.setModifiedDate(null);
		}
		else {
			csDiagramSettingImpl.setModifiedDate(new Date(modifiedDate));
		}

		csDiagramSettingImpl.setCPAttachmentFileEntryId(
			CPAttachmentFileEntryId);
		csDiagramSettingImpl.setCPDefinitionId(CPDefinitionId);

		if (color == null) {
			csDiagramSettingImpl.setColor("");
		}
		else {
			csDiagramSettingImpl.setColor(color);
		}

		csDiagramSettingImpl.setRadius(radius);

		if (type == null) {
			csDiagramSettingImpl.setType("");
		}
		else {
			csDiagramSettingImpl.setType(type);
		}

		csDiagramSettingImpl.resetOriginalValues();

		return csDiagramSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CSDiagramSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPAttachmentFileEntryId = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
		color = objectInput.readUTF();

		radius = objectInput.readDouble();
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

		objectOutput.writeLong(CSDiagramSettingId);

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

		objectOutput.writeLong(CPAttachmentFileEntryId);

		objectOutput.writeLong(CPDefinitionId);

		if (color == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(color);
		}

		objectOutput.writeDouble(radius);

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
	public long CSDiagramSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPAttachmentFileEntryId;
	public long CPDefinitionId;
	public String color;
	public double radius;
	public String type;

}