/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Julio Camarero
 * @author Samuel Kong
 */
public class FriendlyURLNormalizerUtil {

	public static String normalize(String friendlyURL) {
		FriendlyURLNormalizer friendlyURLNormalizer =
			_friendlyURLNormalizerSnapshot.get();

		return friendlyURLNormalizer.normalize(friendlyURL);
	}

	public static String normalizeWithEncoding(String friendlyURL) {
		FriendlyURLNormalizer friendlyURLNormalizer =
			_friendlyURLNormalizerSnapshot.get();

		return friendlyURLNormalizer.normalizeWithEncoding(friendlyURL);
	}

	public static String normalizeWithEncodingPeriodsAndSlashes(
		String friendlyURL) {

		FriendlyURLNormalizer friendlyURLNormalizer =
			_friendlyURLNormalizerSnapshot.get();

		return friendlyURLNormalizer.normalizeWithEncodingPeriodsAndSlashes(
			friendlyURL);
	}

	public static String normalizeWithPeriodsAndSlashes(String friendlyURL) {
		FriendlyURLNormalizer friendlyURLNormalizer =
			_friendlyURLNormalizerSnapshot.get();

		return friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
			friendlyURL);
	}

	private static final Snapshot<FriendlyURLNormalizer>
		_friendlyURLNormalizerSnapshot = new Snapshot<>(
			FriendlyURLNormalizerUtil.class, FriendlyURLNormalizer.class);

}