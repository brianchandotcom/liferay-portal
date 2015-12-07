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

package com.liferay.portal.kernel.license.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.LicenseInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Amos Fong
 */
public interface LicenseManager {

	public static final int STATE_ABSENT = 1;

	public static final int STATE_EXPIRED = 2;

	public static final int STATE_GOOD = 3;

	public static final int STATE_INACTIVE = 4;

	public static final int STATE_INVALID = 5;

	public static final int STATE_OVERLOAD = 6;

	public void checkLicense(String productId);

	public List<Map<String, String>> getClusterLicenseProperties(
		String clusterNodeId);

	public String getHostName();

	public Set<String> getIpAddresses();

	public LicenseInfo getLicenseInfo(String productId);

	public List<Map<String, String>> getLicenseProperties();

	public Map<String, String> getLicenseProperties(String productId);

	public int getLicenseState(Map<String, String> licenseProperties);

	public int getLicenseState(String productId);

	public Set<String> getMacAddresses();

	public void registerLicense(JSONObject jsonObject) throws Exception;

}