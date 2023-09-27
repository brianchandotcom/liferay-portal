/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.mobile.device;

import com.liferay.petra.string.StringBundler;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Abstract class containing common methods for all devices
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseDevice implements Device {

	@Override
	public String toString() {
		return StringBundler.concat(
			"{brand=", getBrand(), ", browser=", getBrowser(),
			", browserVersion=", getBrowserVersion(), ", model=", getModel(),
			", os=", getOS(), ", osVersion=", getOSVersion(),
			", pointingMethod=", getPointingMethod(), ", qwertyKeyboard=",
			hasQwertyKeyboard(), ", screenPhysicalSize=",
			getScreenPhysicalSize(), ", screenResolution=",
			getScreenResolution(), ", tablet=", isTablet(), "}");
	}

}