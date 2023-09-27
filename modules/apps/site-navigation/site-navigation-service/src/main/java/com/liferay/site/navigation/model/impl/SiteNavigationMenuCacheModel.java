/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.site.navigation.model.SiteNavigationMenu;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SiteNavigationMenu in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuCacheModel
	implements CacheModel<SiteNavigationMenu>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SiteNavigationMenuCacheModel)) {
			return false;
		}

		SiteNavigationMenuCacheModel siteNavigationMenuCacheModel =
			(SiteNavigationMenuCacheModel)object;

		if ((siteNavigationMenuId ==
				siteNavigationMenuCacheModel.siteNavigationMenuId) &&
			(mvccVersion == siteNavigationMenuCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, siteNavigationMenuId);

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
		sb.append(", siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append(", auto=");
		sb.append(auto);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SiteNavigationMenu toEntityModel() {
		SiteNavigationMenuImpl siteNavigationMenuImpl =
			new SiteNavigationMenuImpl();

		siteNavigationMenuImpl.setMvccVersion(mvccVersion);
		siteNavigationMenuImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			siteNavigationMenuImpl.setUuid("");
		}
		else {
			siteNavigationMenuImpl.setUuid(uuid);
		}

		siteNavigationMenuImpl.setSiteNavigationMenuId(siteNavigationMenuId);
		siteNavigationMenuImpl.setGroupId(groupId);
		siteNavigationMenuImpl.setCompanyId(companyId);
		siteNavigationMenuImpl.setUserId(userId);

		if (userName == null) {
			siteNavigationMenuImpl.setUserName("");
		}
		else {
			siteNavigationMenuImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			siteNavigationMenuImpl.setCreateDate(null);
		}
		else {
			siteNavigationMenuImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			siteNavigationMenuImpl.setModifiedDate(null);
		}
		else {
			siteNavigationMenuImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			siteNavigationMenuImpl.setName("");
		}
		else {
			siteNavigationMenuImpl.setName(name);
		}

		siteNavigationMenuImpl.setType(type);
		siteNavigationMenuImpl.setAuto(auto);

		if (lastPublishDate == Long.MIN_VALUE) {
			siteNavigationMenuImpl.setLastPublishDate(null);
		}
		else {
			siteNavigationMenuImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		siteNavigationMenuImpl.resetOriginalValues();

		return siteNavigationMenuImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		siteNavigationMenuId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		type = objectInput.readInt();

		auto = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(siteNavigationMenuId);

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

		objectOutput.writeInt(type);

		objectOutput.writeBoolean(auto);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public long siteNavigationMenuId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public int type;
	public boolean auto;
	public long lastPublishDate;

}