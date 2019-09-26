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

package com.liferay.asset.display.page.model.impl;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
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
 * The cache model class for representing AssetDisplayPageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetDisplayPageEntryCacheModel
	implements CacheModel<AssetDisplayPageEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetDisplayPageEntryCacheModel)) {
			return false;
		}

		AssetDisplayPageEntryCacheModel assetDisplayPageEntryCacheModel =
			(AssetDisplayPageEntryCacheModel)obj;

		if ((assetDisplayPageEntryId ==
				assetDisplayPageEntryCacheModel.assetDisplayPageEntryId) &&
			(mvccVersion == assetDisplayPageEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, assetDisplayPageEntryId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", assetDisplayPageEntryId=");
		sb.append(assetDisplayPageEntryId);
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
		sb.append(", layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", plid=");
		sb.append(plid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetDisplayPageEntry toEntityModel() {
		AssetDisplayPageEntryImpl assetDisplayPageEntryImpl =
			new AssetDisplayPageEntryImpl();

		assetDisplayPageEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			assetDisplayPageEntryImpl.setUuid("");
		}
		else {
			assetDisplayPageEntryImpl.setUuid(uuid);
		}

		assetDisplayPageEntryImpl.setAssetDisplayPageEntryId(
			assetDisplayPageEntryId);
		assetDisplayPageEntryImpl.setGroupId(groupId);
		assetDisplayPageEntryImpl.setCompanyId(companyId);
		assetDisplayPageEntryImpl.setUserId(userId);

		if (userName == null) {
			assetDisplayPageEntryImpl.setUserName("");
		}
		else {
			assetDisplayPageEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetDisplayPageEntryImpl.setCreateDate(null);
		}
		else {
			assetDisplayPageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetDisplayPageEntryImpl.setModifiedDate(null);
		}
		else {
			assetDisplayPageEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		assetDisplayPageEntryImpl.setClassNameId(classNameId);
		assetDisplayPageEntryImpl.setClassPK(classPK);
		assetDisplayPageEntryImpl.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		assetDisplayPageEntryImpl.setType(type);
		assetDisplayPageEntryImpl.setPlid(plid);

		assetDisplayPageEntryImpl.resetOriginalValues();

		return assetDisplayPageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		assetDisplayPageEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		layoutPageTemplateEntryId = objectInput.readLong();

		type = objectInput.readInt();

		plid = objectInput.readLong();
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

		objectOutput.writeLong(assetDisplayPageEntryId);

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

		objectOutput.writeLong(layoutPageTemplateEntryId);

		objectOutput.writeInt(type);

		objectOutput.writeLong(plid);
	}

	public long mvccVersion;
	public String uuid;
	public long assetDisplayPageEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long layoutPageTemplateEntryId;
	public int type;
	public long plid;

}