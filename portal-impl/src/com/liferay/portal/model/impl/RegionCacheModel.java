/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Region;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Region in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Region
 * @generated
 */
public class RegionCacheModel implements CacheModel<Region>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{regionId=");
		sb.append(regionId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", regionCode=");
		sb.append(regionCode);
		sb.append(", name=");
		sb.append(name);
		sb.append(", active=");
		sb.append(active);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Region toEntityModel() {
		RegionImpl regionImpl = new RegionImpl();

		regionImpl.setRegionId(regionId);
		regionImpl.setCountryId(countryId);

		if (regionCode == null) {
			regionImpl.setRegionCode(StringPool.BLANK);
		}
		else {
			regionImpl.setRegionCode(regionCode);
		}

		if (name == null) {
			regionImpl.setName(StringPool.BLANK);
		}
		else {
			regionImpl.setName(name);
		}

		regionImpl.setActive(active);
		regionImpl.setMvccVersion(mvccVersion);

		regionImpl.resetOriginalValues();

		return regionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		regionId = objectInput.readLong();
		countryId = objectInput.readLong();
		regionCode = objectInput.readUTF();
		name = objectInput.readUTF();
		active = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(regionId);
		objectOutput.writeLong(countryId);

		if (regionCode == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(regionCode);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(mvccVersion);
	}

	public long regionId;
	public long countryId;
	public String regionCode;
	public String name;
	public boolean active;
	public long mvccVersion;
}