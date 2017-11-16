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

package com.liferay.asset.display.template.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.display.template.model.AssetDisplayTemplate;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetDisplayTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayTemplate
 * @generated
 */
@ProviderType
public class AssetDisplayTemplateCacheModel implements CacheModel<AssetDisplayTemplate>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayTemplateCacheModel)) {
			return false;
		}

		AssetDisplayTemplateCacheModel assetDisplayTemplateCacheModel = (AssetDisplayTemplateCacheModel)obj;

		if (assetDisplayTemplateId == assetDisplayTemplateCacheModel.assetDisplayTemplateId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetDisplayTemplateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{assetDisplayTemplateId=");
		sb.append(assetDisplayTemplateId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", DDMTemplateId=");
		sb.append(DDMTemplateId);
		sb.append(", main=");
		sb.append(main);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetDisplayTemplate toEntityModel() {
		AssetDisplayTemplateImpl assetDisplayTemplateImpl = new AssetDisplayTemplateImpl();

		assetDisplayTemplateImpl.setAssetDisplayTemplateId(assetDisplayTemplateId);
		assetDisplayTemplateImpl.setGroupId(groupId);
		assetDisplayTemplateImpl.setCompanyId(companyId);
		assetDisplayTemplateImpl.setUserId(userId);

		if (userName == null) {
			assetDisplayTemplateImpl.setUserName("");
		}
		else {
			assetDisplayTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetDisplayTemplateImpl.setCreateDate(null);
		}
		else {
			assetDisplayTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetDisplayTemplateImpl.setModifiedDate(null);
		}
		else {
			assetDisplayTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			assetDisplayTemplateImpl.setName("");
		}
		else {
			assetDisplayTemplateImpl.setName(name);
		}

		assetDisplayTemplateImpl.setClassNameId(classNameId);
		assetDisplayTemplateImpl.setDDMTemplateId(DDMTemplateId);
		assetDisplayTemplateImpl.setMain(main);

		assetDisplayTemplateImpl.resetOriginalValues();

		return assetDisplayTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		assetDisplayTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		classNameId = objectInput.readLong();

		DDMTemplateId = objectInput.readLong();

		main = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(assetDisplayTemplateId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(DDMTemplateId);

		objectOutput.writeBoolean(main);
	}

	public long assetDisplayTemplateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public long classNameId;
	public long DDMTemplateId;
	public boolean main;
}