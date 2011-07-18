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
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.mobile.service.base.DeviceProfileActionLocalServiceBaseImpl;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the device profile action local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.service.DeviceProfileActionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.portal.mobile.service.base.DeviceProfileActionLocalServiceBaseImpl
 * @see com.liferay.portal.mobile.service.DeviceProfileActionLocalServiceUtil
 */
public class DeviceProfileActionLocalServiceImpl
	extends DeviceProfileActionLocalServiceBaseImpl {

	public DeviceProfileAction addDeviceProfileAction(
			long deviceProfileId, long deviceProfileRuleId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, UnicodeProperties typeSettingsProperties)
		throws PortalException, SystemException {

		String typeSettings = typeSettingsProperties.toString();

		return addDeviceProfileAction(
			deviceProfileId, deviceProfileRuleId, nameMap, descriptionMap, type,
			typeSettings);
	}

	public DeviceProfileAction addDeviceProfileAction(
			long deviceProfileId, long deviceProfileRuleId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String typeSettings)
		throws PortalException, SystemException {

		long deviceProfileActionId = counterLocalService.increment();

		DeviceProfileAction deviceProfileAction =
			deviceProfileActionPersistence.create(deviceProfileActionId);

		deviceProfileAction.setDeviceProfileId(deviceProfileId);
		deviceProfileAction.setDeviceProfileRuleId(deviceProfileRuleId);
		deviceProfileAction.setNameMap(nameMap);
		deviceProfileAction.setDescriptionMap(descriptionMap);
		deviceProfileAction.setType(type);
		deviceProfileAction.setTypeSettings(typeSettings);

		return updateDeviceProfileAction(deviceProfileAction, false);
	}

	public void deleteDeviceProfileActions(long deviceProfileRuleId)
		throws SystemException {

		Collection<DeviceProfileAction> deviceProfileActions =
			getDeviceProfileActions(deviceProfileRuleId);

		for (DeviceProfileAction deviceProfileAction : deviceProfileActions) {
			deleteDeviceProfileAction(deviceProfileAction);
		}
	}

	public DeviceProfileAction fetchDeviceProfileAction(
			long deviceProfileActionId)
		throws SystemException {

		return deviceProfileActionPersistence.fetchByPrimaryKey(
			deviceProfileActionId);
	}

	public List<DeviceProfileAction> getDeviceProfileActions(
			long deviceProfileRuleId)
		throws SystemException {

		return deviceProfileActionPersistence.findByDeviceProfileRuleId(
			deviceProfileRuleId);
	}

	public List<DeviceProfileAction> getDeviceProfileActions(
			long deviceProfileRuleId, int start, int end)
		throws SystemException {

		return deviceProfileActionPersistence.findByDeviceProfileRuleId(
			deviceProfileRuleId, start, end);
	}

	public int getDeviceProfileActionsCount(long deviceProfileRuleId)
		throws SystemException {

		return deviceProfileActionPersistence.countByDeviceProfileRuleId(
			deviceProfileRuleId);
	}

}