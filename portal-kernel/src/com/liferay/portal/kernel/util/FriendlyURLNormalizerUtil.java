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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.regex.Pattern;

/**
 * @author Julio Camarero
 * @author Samuel Kong
 */
public class FriendlyURLNormalizerUtil {

	public static FriendlyURLNormalizer getFriendlyURLNormalizer() {
		if (_serviceTracker == null) {
			Registry registry = RegistryUtil.getRegistry();

			_serviceTracker = registry.trackServices(
				FriendlyURLNormalizer.class);

			_serviceTracker.open();
		}

		return _serviceTracker.getService();
	}

	public static String normalize(String friendlyURL) {
		return getFriendlyURLNormalizer().normalize(friendlyURL);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String normalize(
		String friendlyURL, Pattern friendlyURLPattern) {

		return getFriendlyURLNormalizer().normalize(
			friendlyURL, friendlyURLPattern);
	}

	public static String normalizeWithEncoding(String friendlyURL) {
		return getFriendlyURLNormalizer().normalizeWithEncoding(friendlyURL);
	}

	public static String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		return getFriendlyURLNormalizer().normalizeWithPeriodsAndSlashes(
			friendlyURL);
	}

	@Deprecated
	public void setFriendlyURLNormalizer(
		FriendlyURLNormalizer friendlyURLNormalizer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_friendlyURLNormalizer = friendlyURLNormalizer;
	}

	private static FriendlyURLNormalizer _friendlyURLNormalizer;

	private static ServiceTracker<FriendlyURLNormalizer, FriendlyURLNormalizer>
		_serviceTracker;

}