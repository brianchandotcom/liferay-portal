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
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.model.CacheModel;

/**
 * The cache model class for representing DeviceProfileRule in entity cache.
 *
 * @author Edward C. Han
 * @see DeviceProfileRule
 * @generated
 */
public class DeviceProfileRuleCacheModel implements CacheModel<DeviceProfileRule> {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", deviceProfileRuleId=");
		sb.append(deviceProfileRuleId);
		sb.append(", deviceProfileId=");
		sb.append(deviceProfileId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", ruleType=");
		sb.append(ruleType);
		sb.append(", ruleTypeSettings=");
		sb.append(ruleTypeSettings);
		sb.append("}");

		return sb.toString();
	}

	public DeviceProfileRule toEntityModel() {
		DeviceProfileRuleImpl deviceProfileRuleImpl = new DeviceProfileRuleImpl();

		if (uuid == null) {
			deviceProfileRuleImpl.setUuid(StringPool.BLANK);
		}
		else {
			deviceProfileRuleImpl.setUuid(uuid);
		}

		deviceProfileRuleImpl.setDeviceProfileRuleId(deviceProfileRuleId);
		deviceProfileRuleImpl.setDeviceProfileId(deviceProfileId);

		if (name == null) {
			deviceProfileRuleImpl.setName(StringPool.BLANK);
		}
		else {
			deviceProfileRuleImpl.setName(name);
		}

		if (description == null) {
			deviceProfileRuleImpl.setDescription(StringPool.BLANK);
		}
		else {
			deviceProfileRuleImpl.setDescription(description);
		}

		if (ruleType == null) {
			deviceProfileRuleImpl.setRuleType(StringPool.BLANK);
		}
		else {
			deviceProfileRuleImpl.setRuleType(ruleType);
		}

		if (ruleTypeSettings == null) {
			deviceProfileRuleImpl.setRuleTypeSettings(StringPool.BLANK);
		}
		else {
			deviceProfileRuleImpl.setRuleTypeSettings(ruleTypeSettings);
		}

		deviceProfileRuleImpl.resetOriginalValues();

		return deviceProfileRuleImpl;
	}

	public String uuid;
	public long deviceProfileRuleId;
	public long deviceProfileId;
	public String name;
	public String description;
	public String ruleType;
	public String ruleTypeSettings;
}