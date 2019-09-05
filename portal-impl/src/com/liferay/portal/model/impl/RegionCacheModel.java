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

/**
 * The cache model class for representing Region in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RegionCacheModel
	implements CacheModel<Region>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RegionCacheModel)) {
			return false;
		}

		RegionCacheModel regionCacheModel = (RegionCacheModel)obj;

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
		StringBundler sb = new StringBundler(13);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", regionCode=");
		sb.append(regionCode);
		sb.append(", name=");
		sb.append(name);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Region toEntityModel() {
		RegionImpl regionImpl = new RegionImpl();

		regionImpl.setMvccVersion(mvccVersion);
		regionImpl.setRegionId(regionId);
		regionImpl.setCountryId(countryId);

		if (regionCode == null) {
			regionImpl.setRegionCode("");
		}
		else {
			regionImpl.setRegionCode(regionCode);
		}

		if (name == null) {
			regionImpl.setName("");
		}
		else {
			regionImpl.setName(name);
		}

		regionImpl.setActive(active);

		regionImpl.resetOriginalValues();

		return regionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		regionId = objectInput.readLong();

		countryId = objectInput.readLong();
		regionCode = objectInput.readUTF();
		name = objectInput.readUTF();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(regionId);

		objectOutput.writeLong(countryId);

		if (regionCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(regionCode);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long regionId;
	public long countryId;
	public String regionCode;
	public String name;
	public boolean active;

}