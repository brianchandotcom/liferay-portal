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

package com.liferay.portal.mobile.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.mobile.service.base.DeviceProfileRuleLocalServiceBaseImpl;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the device profile rule local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.service.DeviceProfileRuleLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.portal.mobile.service.base.DeviceProfileRuleLocalServiceBaseImpl
 * @see com.liferay.portal.mobile.service.DeviceProfileRuleLocalServiceUtil
 */
public class DeviceProfileRuleLocalServiceImpl
	extends DeviceProfileRuleLocalServiceBaseImpl {

	public DeviceProfileRule addDeviceProfileRule(
			long deviceProfileId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String rule,
			UnicodeProperties ruleTypeSettingsProperties)
		throws PortalException, SystemException {

		String ruleTypeSettings = ruleTypeSettingsProperties.toString();

		return addDeviceProfileRule(
			deviceProfileId, nameMap, descriptionMap, rule,
			ruleTypeSettings);
	}

	public DeviceProfileRule addDeviceProfileRule(
			long deviceProfileId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String rule,
			String ruleTypeSettings)
		throws PortalException, SystemException {

		long deviceProfileRuleId = counterLocalService.increment();

		DeviceProfileRule deviceProfileRule =
			deviceProfileRulePersistence.create(deviceProfileRuleId);

		deviceProfileRule.setNameMap(nameMap);
		deviceProfileRule.setDescriptionMap(descriptionMap);
		deviceProfileRule.setDeviceProfileId(deviceProfileId);
		deviceProfileRule.setDeviceProfileRuleId(deviceProfileRuleId);
		deviceProfileRule.setRuleType(rule);
		deviceProfileRule.setRuleTypeSettings(ruleTypeSettings);

		return updateDeviceProfileRule(deviceProfileRule, false);
	}

	@Override
	public void deleteDeviceProfileRule(DeviceProfileRule deviceProfileRule)
		throws SystemException {

		deviceProfileActionLocalService.deleteDeviceProfileActions(
			deviceProfileRule.getDeviceProfileRuleId());

		super.deleteDeviceProfileRule(deviceProfileRule);
	}

	@Override
	public void deleteDeviceProfileRule(long deviceProfileRuleId)
		throws SystemException {

		DeviceProfileRule deviceProfileRule = fetchDeviceProfileRule(
			deviceProfileRuleId);

		if (deviceProfileRule != null) {
			deleteDeviceProfileRule(deviceProfileRule);
		}
	}

	public void deleteDeviceProfileRules(long deviceProfileId)
		throws PortalException, SystemException {

		Collection<DeviceProfileRule> deviceProfileRules =
			getDeviceProfileRules(deviceProfileId);

		for (DeviceProfileRule deviceProfileRule : deviceProfileRules) {
			deleteDeviceProfileRule(deviceProfileRule);
		}
	}

	public DeviceProfileRule fetchDeviceProfileRule(
			long deviceProfileRuleId)
		throws SystemException {

		return deviceProfileRulePersistence.fetchByPrimaryKey(
			deviceProfileRuleId);
	}

	public List<DeviceProfileRule> getDeviceProfileRules(
			long deviceProfileId)
		throws SystemException {

		return deviceProfileRulePersistence.findByDeviceProfileId(
			deviceProfileId);
	}

	public List<DeviceProfileRule> getDeviceProfileRules(
			long deviceProfileId, int start, int end)
		throws SystemException {

		return deviceProfileRulePersistence.findByDeviceProfileId(
			deviceProfileId, start, end);
	}

	public int getDeviceProfileRulesCount(long deviceProfileId)
		throws SystemException {

		return deviceProfileRulePersistence.countByDeviceProfileId(
			deviceProfileId);
	}
}