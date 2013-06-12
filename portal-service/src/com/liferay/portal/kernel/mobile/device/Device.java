/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import java.util.Map;

/**
 * @author Milen Dyankov
 * @author Michael C. Han
 */
public interface Device extends Serializable {

	public String getBrand();

	public String getBrowser();

	public String getBrowserVersion();

	public Map<String, Capability> getCapabilities();

	public String getCapability(String name);

	public String getModel();

	public String getOS();

	public String getOSVersion();

	public String getPointingMethod();

	public Dimensions getScreenPhysicalSize();

	public Dimensions getScreenResolution();

	/**
	 * @deprecated As of 6.2.0 please use {@link #getScreenResolution()} instead
	 */
	@Deprecated
	public Dimensions getScreenSize();

	public boolean hasQwertyKeyboard();

	public boolean isTablet();

}