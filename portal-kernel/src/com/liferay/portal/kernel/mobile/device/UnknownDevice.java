/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import java.util.Collections;
import java.util.Map;

/**
 * Class represents unknown device
 *
 * @author Milen Dyankov
 */
public class UnknownDevice extends BaseDevice {

	public static UnknownDevice getInstance() {
		return _unknownDevice;
	}

	@Override
	public String getBrand() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public String getBrowser() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public String getBrowserVersion() {
		return VersionableName.UNKNOWN.getName();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<String, Capability> getCapabilities() {
		return Collections.emptyMap();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getCapability(String name) {
		return null;
	}

	@Override
	public String getModel() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public String getOS() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public String getOSVersion() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public String getPointingMethod() {
		return VersionableName.UNKNOWN.getName();
	}

	@Override
	public Dimensions getScreenPhysicalSize() {
		return Dimensions.UNKNOWN;
	}

	@Override
	public Dimensions getScreenResolution() {
		return Dimensions.UNKNOWN;
	}

	@Override
	public boolean hasQwertyKeyboard() {
		return true;
	}

	@Override
	public boolean isTablet() {
		return false;
	}

	private UnknownDevice() {
	}

	private static final UnknownDevice _unknownDevice = new UnknownDevice();

}