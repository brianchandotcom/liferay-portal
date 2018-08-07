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

import java.util.regex.Pattern;

/**
 * @author Julio Camarero
 * @author Samuel Kong
 */
public class FriendlyURLNormalizerUtil {

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static FriendlyURLNormalizer getFriendlyURLNormalizer() {
		return _friendlyURLNormalizer;
	}

	public static String normalize(String friendlyURL) {
		return _friendlyURLNormalizer.normalize(friendlyURL);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static String normalize(
		String friendlyURL, Pattern friendlyURLPattern) {

		return _friendlyURLNormalizer.normalize(
			friendlyURL, friendlyURLPattern);
	}

	public static String normalizeWithEncoding(String friendlyURL) {
		return _friendlyURLNormalizer.normalizeWithEncoding(friendlyURL);
	}

	public static String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		return _friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
			friendlyURL);
	}

	public void setFriendlyURLNormalizer(
		FriendlyURLNormalizer friendlyURLNormalizer) {

		_friendlyURLNormalizer = friendlyURLNormalizer;
	}

	private static volatile FriendlyURLNormalizer _friendlyURLNormalizer =
		ServiceProxyFactory.newServiceTrackedInstance(
			FriendlyURLNormalizer.class, FriendlyURLNormalizerUtil.class,
			"_friendlyURLNormalizer", true);

}