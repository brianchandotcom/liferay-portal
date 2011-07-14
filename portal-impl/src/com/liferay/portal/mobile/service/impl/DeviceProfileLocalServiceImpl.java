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
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.mobile.service.base.DeviceProfileLocalServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the device profile local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.service.DeviceProfileLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Edward C. Han
 * @see com.liferay.portal.mobile.service.base.DeviceProfileLocalServiceBaseImpl
 * @see com.liferay.portal.mobile.service.DeviceProfileLocalServiceUtil
 */
public class DeviceProfileLocalServiceImpl
	extends DeviceProfileLocalServiceBaseImpl {

	public DeviceProfile addDeviceProfile(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws PortalException, SystemException{

		long deviceProfileId = counterLocalService.increment();

		DeviceProfile deviceProfile = createDeviceProfile(deviceProfileId);

		deviceProfile.setNameMap(nameMap);
		deviceProfile.setDescriptionMap(descriptionMap);

		return updateDeviceProfile(deviceProfile, false);
	}

	@Override
	public void deleteDeviceProfile(DeviceProfile deviceProfile)
		throws PortalException, SystemException {

		deviceProfileRuleLocalService.deleteDeviceProfileRules(
			deviceProfile.getDeviceProfileId());

		super.deleteDeviceProfile(deviceProfile);
	}

	@Override
	public void deleteDeviceProfile(long deviceProfileId)
		throws PortalException, SystemException {

		DeviceProfile deviceProfile =
			deviceProfilePersistence.fetchByPrimaryKey(deviceProfileId);

		if (deviceProfile != null) {
			deleteDeviceProfile(deviceProfile);
		}
	}

	public DeviceProfile fetchByPrimaryKey(long deviceProfileId)
		throws SystemException {

		return deviceProfilePersistence.fetchByPrimaryKey(deviceProfileId);
	}
}