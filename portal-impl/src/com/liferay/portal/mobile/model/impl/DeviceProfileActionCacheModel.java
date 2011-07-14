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
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.model.CacheModel;

/**
 * The cache model class for representing DeviceProfileAction in entity cache.
 *
 * @author Edward C. Han
 * @see DeviceProfileAction
 * @generated
 */
public class DeviceProfileActionCacheModel implements CacheModel<DeviceProfileAction> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deviceProfileActionId=");
		sb.append(deviceProfileActionId);
		sb.append(", deviceProfileId=");
		sb.append(deviceProfileId);
		sb.append(", deviceProfileRuleId=");
		sb.append(deviceProfileRuleId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append("}");

		return sb.toString();
	}

	public DeviceProfileAction toEntityModel() {
		DeviceProfileActionImpl deviceProfileActionImpl = new DeviceProfileActionImpl();

		if (uuid == null) {
			deviceProfileActionImpl.setUuid(StringPool.BLANK);
		}
		else {
			deviceProfileActionImpl.setUuid(uuid);
		}

		deviceProfileActionImpl.setDeviceProfileActionId(deviceProfileActionId);
		deviceProfileActionImpl.setDeviceProfileId(deviceProfileId);
		deviceProfileActionImpl.setDeviceProfileRuleId(deviceProfileRuleId);

		if (name == null) {
			deviceProfileActionImpl.setName(StringPool.BLANK);
		}
		else {
			deviceProfileActionImpl.setName(name);
		}

		if (description == null) {
			deviceProfileActionImpl.setDescription(StringPool.BLANK);
		}
		else {
			deviceProfileActionImpl.setDescription(description);
		}

		if (type == null) {
			deviceProfileActionImpl.setType(StringPool.BLANK);
		}
		else {
			deviceProfileActionImpl.setType(type);
		}

		if (typeSettings == null) {
			deviceProfileActionImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			deviceProfileActionImpl.setTypeSettings(typeSettings);
		}

		deviceProfileActionImpl.resetOriginalValues();

		return deviceProfileActionImpl;
	}

	public String uuid;
	public long deviceProfileActionId;
	public long deviceProfileId;
	public long deviceProfileRuleId;
	public String name;
	public String description;
	public String type;
	public String typeSettings;
}