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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.mobile.model.DeviceProfile;
import com.liferay.portal.mobile.model.DeviceProfileRule;
import com.liferay.portal.mobile.service.DeviceProfileRuleLocalServiceUtil;

import java.util.Collection;
import java.util.Collections;

/**
 * The model implementation for the DeviceProfile service. Represents a row in the &quot;DeviceProfile&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.model.DeviceProfile} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class DeviceProfileImpl extends DeviceProfileBaseImpl {

	public static final DeviceProfile UNKNOWN_DEVICE_PROFILE =
		new DeviceProfileImpl();

	public DeviceProfileImpl() {
		setDescription(StringPool.BLANK);
		setDeviceProfileId(0);
		setName(StringPool.BLANK);
	}

	public Collection<DeviceProfileRule> getDeviceProfileRules()
		throws PortalException, SystemException {

		if (getDeviceProfileId() <= 0) {
			return Collections.emptyList();
		}
		else {
			return DeviceProfileRuleLocalServiceUtil.getDeviceProfileRules(
				getDeviceProfileId());
		}
	}
}