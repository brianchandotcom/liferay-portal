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

package com.liferay.portal.license.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.license.LicenseInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Amos Fong
 */
public interface LicenseManager {

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