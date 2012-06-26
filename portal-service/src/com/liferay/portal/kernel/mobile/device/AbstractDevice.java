/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mobile.device;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * Abstract class containing common methods for all devices
 *
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public abstract class AbstractDevice implements Device {

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{brand=");
		sb.append(getBrand());
		sb.append(", browser=");
		sb.append(getBrowser());
		sb.append(", browserVersion=");
		sb.append(getBrowserVersion());
		sb.append(", capabilities=");
		sb.append(getCapabilities());
		sb.append(", model=");
		sb.append(getModel());
		sb.append(", os=");
		sb.append(getOS());
		sb.append(", osVersion=");
		sb.append(getOSVersion());
		sb.append(", pointingMethod=");
		sb.append(getPointingMethod());
		sb.append(", qwertyKeyboard=");
		sb.append(hasQwertyKeyboard());
		sb.append(", screenSize=");
		sb.append(getScreenSize());
		sb.append(", tablet=");
		sb.append(isTablet());
		sb.append("}");

		return sb.toString();
	}

}