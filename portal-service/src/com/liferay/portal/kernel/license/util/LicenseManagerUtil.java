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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Amos Fong
 */
public class LicenseManagerUtil {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link LicenseManager#STATE_ABSENT}
	 */
	@Deprecated
	public static final int STATE_ABSENT = LicenseManager.STATE_ABSENT;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link LicenseManager#STATE_EXPIRED}
	 */
	@Deprecated
	public static final int STATE_EXPIRED = LicenseManager.STATE_EXPIRED;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link LicenseManager#STATE_GOOD}
	 */
	@Deprecated
	public static final int STATE_GOOD = LicenseManager.STATE_GOOD;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             LicenseManager#STATE_INACTIVE}
	 */
	@Deprecated
	public static final int STATE_INACTIVE = LicenseManager.STATE_INACTIVE;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link LicenseManager#STATE_INVALID}
	 */
	@Deprecated
	public static final int STATE_INVALID = LicenseManager.STATE_INVALID;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             LicenseManager#STATE_OVERLOAD}
	 */
	@Deprecated
	public static final int STATE_OVERLOAD = LicenseManager.STATE_OVERLOAD;

	public static void checkLicense(String productId) {
		getLicenseManager().checkLicense(productId);
	}

	public static List<Map<String, String>> getClusterLicenseProperties(
		String clusterNodeId) {

		return getLicenseManager().getClusterLicenseProperties(clusterNodeId);
	}

	public static String getHostName() {
		return getLicenseManager().getHostName();
	}

	public static Set<String> getIpAddresses() {
		return getLicenseManager().getIpAddresses();
	}

	public static LicenseInfo getLicenseInfo(String productId) {
		return getLicenseManager().getLicenseInfo(productId);
	}

	public static LicenseManager getLicenseManager() {
		PortalRuntimePermission.checkGetBeanProperty(LicenseManagerUtil.class);

		return _licenseManager;
	}

	public static List<Map<String, String>> getLicenseProperties() {
		return getLicenseManager().getLicenseProperties();
	}

	public static Map<String, String> getLicenseProperties(String productId) {
		return getLicenseManager().getLicenseProperties(productId);
	}

	public static int getLicenseState(Map<String, String> licenseProperties) {
		return getLicenseManager().getLicenseState(licenseProperties);
	}

	public static int getLicenseState(String productId) {
		return getLicenseManager().getLicenseState(productId);
	}

	public static Set<String> getMacAddresses() {
		return getLicenseManager().getMacAddresses();
	}

	public static void registerLicense(JSONObject jsonObject) throws Exception {
		getLicenseManager().registerLicense(jsonObject);
	}

	public void setLicenseManager(LicenseManager licenseManager) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_licenseManager = licenseManager;
	}

	private static LicenseManager _licenseManager;

}