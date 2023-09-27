/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SiteNavigationMenuItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuItemCacheModel
	implements CacheModel<SiteNavigationMenuItem>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SiteNavigationMenuItemCacheModel)) {
			return false;
		}

		SiteNavigationMenuItemCacheModel siteNavigationMenuItemCacheModel =
			(SiteNavigationMenuItemCacheModel)object;

		if ((siteNavigationMenuItemId ==
				siteNavigationMenuItemCacheModel.siteNavigationMenuItemId) &&
			(mvccVersion == siteNavigationMenuItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, siteNavigationMenuItemId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", siteNavigationMenuItemId=");
		sb.append(siteNavigationMenuItemId);
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
		sb.append(", siteNavigationMenuId=");
		sb.append(siteNavigationMenuId);
		sb.append(", parentSiteNavigationMenuItemId=");
		sb.append(parentSiteNavigationMenuItemId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", order=");
		sb.append(order);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SiteNavigationMenuItem toEntityModel() {
		SiteNavigationMenuItemImpl siteNavigationMenuItemImpl =
			new SiteNavigationMenuItemImpl();

		siteNavigationMenuItemImpl.setMvccVersion(mvccVersion);
		siteNavigationMenuItemImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			siteNavigationMenuItemImpl.setUuid("");
		}
		else {
			siteNavigationMenuItemImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			siteNavigationMenuItemImpl.setExternalReferenceCode("");
		}
		else {
			siteNavigationMenuItemImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		siteNavigationMenuItemImpl.setSiteNavigationMenuItemId(
			siteNavigationMenuItemId);
		siteNavigationMenuItemImpl.setGroupId(groupId);
		siteNavigationMenuItemImpl.setCompanyId(companyId);
		siteNavigationMenuItemImpl.setUserId(userId);

		if (userName == null) {
			siteNavigationMenuItemImpl.setUserName("");
		}
		else {
			siteNavigationMenuItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			siteNavigationMenuItemImpl.setCreateDate(null);
		}
		else {
			siteNavigationMenuItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			siteNavigationMenuItemImpl.setModifiedDate(null);
		}
		else {
			siteNavigationMenuItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		siteNavigationMenuItemImpl.setSiteNavigationMenuId(
			siteNavigationMenuId);
		siteNavigationMenuItemImpl.setParentSiteNavigationMenuItemId(
			parentSiteNavigationMenuItemId);

		if (name == null) {
			siteNavigationMenuItemImpl.setName("");
		}
		else {
			siteNavigationMenuItemImpl.setName(name);
		}

		if (type == null) {
			siteNavigationMenuItemImpl.setType("");
		}
		else {
			siteNavigationMenuItemImpl.setType(type);
		}

		if (typeSettings == null) {
			siteNavigationMenuItemImpl.setTypeSettings("");
		}
		else {
			siteNavigationMenuItemImpl.setTypeSettings(typeSettings);
		}

		siteNavigationMenuItemImpl.setOrder(order);

		if (lastPublishDate == Long.MIN_VALUE) {
			siteNavigationMenuItemImpl.setLastPublishDate(null);
		}
		else {
			siteNavigationMenuItemImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		siteNavigationMenuItemImpl.resetOriginalValues();

		return siteNavigationMenuItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		siteNavigationMenuItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		siteNavigationMenuId = objectInput.readLong();

		parentSiteNavigationMenuItemId = objectInput.readLong();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
		typeSettings = (String)objectInput.readObject();

		order = objectInput.readInt();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(siteNavigationMenuItemId);

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

		objectOutput.writeLong(siteNavigationMenuId);

		objectOutput.writeLong(parentSiteNavigationMenuItemId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
		}

		objectOutput.writeInt(order);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long siteNavigationMenuItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long siteNavigationMenuId;
	public long parentSiteNavigationMenuItemId;
	public String name;
	public String type;
	public String typeSettings;
	public int order;
	public long lastPublishDate;

}