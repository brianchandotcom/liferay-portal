/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.mobile.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.model.CacheModel;

/**
 * The cache model class for representing DeviceProfile in entity cache.
 *
 * @author Edward C. Han
 * @see DeviceProfile
 * @generated
 */
public class DeviceProfileCacheModel implements CacheModel<DeviceProfile> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deviceProfileId=");
		sb.append(deviceProfileId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	public DeviceProfile toEntityModel() {
		DeviceProfileImpl deviceProfileImpl = new DeviceProfileImpl();

		if (uuid == null) {
			deviceProfileImpl.setUuid(StringPool.BLANK);
		}
		else {
			deviceProfileImpl.setUuid(uuid);
		}

		deviceProfileImpl.setDeviceProfileId(deviceProfileId);

		if (name == null) {
			deviceProfileImpl.setName(StringPool.BLANK);
		}
		else {
			deviceProfileImpl.setName(name);
		}

		if (description == null) {
			deviceProfileImpl.setDescription(StringPool.BLANK);
		}
		else {
			deviceProfileImpl.setDescription(description);
		}

		deviceProfileImpl.resetOriginalValues();

		return deviceProfileImpl;
	}

	public String uuid;
	public long deviceProfileId;
	public String name;
	public String description;
}