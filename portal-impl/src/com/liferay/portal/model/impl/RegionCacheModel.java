/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.Region;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Region in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegionCacheModel
	implements CacheModel<Region>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegionCacheModel)) {
			return false;
		}

		RegionCacheModel regionCacheModel = (RegionCacheModel)object;

		if ((regionId == regionCacheModel.regionId) &&
			(mvccVersion == regionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, regionId);

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
		StringBundler sb = new StringBundler(31);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append(", regionId=");
		sb.append(regionId);
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
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", active=");
		sb.append(active);
		sb.append(", name=");
		sb.append(name);
		sb.append(", position=");
		sb.append(position);
		sb.append(", regionCode=");
		sb.append(regionCode);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Region toEntityModel() {
		RegionImpl regionImpl = new RegionImpl();

		regionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			regionImpl.setUuid("");
		}
		else {
			regionImpl.setUuid(uuid);
		}

		if (defaultLanguageId == null) {
			regionImpl.setDefaultLanguageId("");
		}
		else {
			regionImpl.setDefaultLanguageId(defaultLanguageId);
		}

		regionImpl.setRegionId(regionId);
		regionImpl.setCompanyId(companyId);
		regionImpl.setUserId(userId);

		if (userName == null) {
			regionImpl.setUserName("");
		}
		else {
			regionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			regionImpl.setCreateDate(null);
		}
		else {
			regionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			regionImpl.setModifiedDate(null);
		}
		else {
			regionImpl.setModifiedDate(new Date(modifiedDate));
		}

		regionImpl.setCountryId(countryId);
		regionImpl.setActive(active);

		if (name == null) {
			regionImpl.setName("");
		}
		else {
			regionImpl.setName(name);
		}

		regionImpl.setPosition(position);

		if (regionCode == null) {
			regionImpl.setRegionCode("");
		}
		else {
			regionImpl.setRegionCode(regionCode);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			regionImpl.setLastPublishDate(null);
		}
		else {
			regionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		regionImpl.resetOriginalValues();

		return regionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		defaultLanguageId = objectInput.readUTF();

		regionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		countryId = objectInput.readLong();

		active = objectInput.readBoolean();
		name = objectInput.readUTF();

		position = objectInput.readDouble();
		regionCode = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}

		objectOutput.writeLong(regionId);

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

		objectOutput.writeLong(countryId);

		objectOutput.writeBoolean(active);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(position);

		if (regionCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(regionCode);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public String defaultLanguageId;
	public long regionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long countryId;
	public boolean active;
	public String name;
	public double position;
	public String regionCode;
	public long lastPublishDate;

}