/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionDiagramSetting in entity cache.
 *
 * @author Andrea Sbarra
 * @generated
 */
public class CPDefinitionDiagramSettingCacheModel
	implements CacheModel<CPDefinitionDiagramSetting>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramSettingCacheModel)) {
			return false;
		}

		CPDefinitionDiagramSettingCacheModel
			cpDefinitionDiagramSettingCacheModel =
				(CPDefinitionDiagramSettingCacheModel)object;

		if (CPDefinitionDiagramSettingId ==
				cpDefinitionDiagramSettingCacheModel.
					CPDefinitionDiagramSettingId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionDiagramSettingId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionDiagramSettingId=");
		sb.append(CPDefinitionDiagramSettingId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CPAttachmentFileEntryId=");
		sb.append(CPAttachmentFileEntryId);
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
	public CPDefinitionDiagramSetting toEntityModel() {
		CPDefinitionDiagramSettingImpl cpDefinitionDiagramSettingImpl =
			new CPDefinitionDiagramSettingImpl();

		if (uuid == null) {
			cpDefinitionDiagramSettingImpl.setUuid("");
		}
		else {
			cpDefinitionDiagramSettingImpl.setUuid(uuid);
		}

		cpDefinitionDiagramSettingImpl.setCPDefinitionDiagramSettingId(
			CPDefinitionDiagramSettingId);
		cpDefinitionDiagramSettingImpl.setCompanyId(companyId);
		cpDefinitionDiagramSettingImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionDiagramSettingImpl.setUserName("");
		}
		else {
			cpDefinitionDiagramSettingImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionDiagramSettingImpl.setCreateDate(null);
		}
		else {
			cpDefinitionDiagramSettingImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionDiagramSettingImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionDiagramSettingImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		cpDefinitionDiagramSettingImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionDiagramSettingImpl.setCPAttachmentFileEntryId(
			CPAttachmentFileEntryId);

		if (color == null) {
			cpDefinitionDiagramSettingImpl.setColor("");
		}
		else {
			cpDefinitionDiagramSettingImpl.setColor(color);
		}

		cpDefinitionDiagramSettingImpl.setRadius(radius);

		if (type == null) {
			cpDefinitionDiagramSettingImpl.setType("");
		}
		else {
			cpDefinitionDiagramSettingImpl.setType(type);
		}

		cpDefinitionDiagramSettingImpl.resetOriginalValues();

		return cpDefinitionDiagramSettingImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionDiagramSettingId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CPAttachmentFileEntryId = objectInput.readLong();
		color = objectInput.readUTF();

		radius = objectInput.readDouble();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionDiagramSettingId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CPAttachmentFileEntryId);

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

	public String uuid;
	public long CPDefinitionDiagramSettingId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long CPAttachmentFileEntryId;
	public String color;
	public double radius;
	public String type;

}