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

package com.liferay.portal.upgrade.v6_2_0.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Sergio González
 */
public class RSSUtil {

	public static String getFeedType(String type, double version) {
		return type + StringPool.UNDERLINE + version;
	}

	public static String getFormatType(String format) {
		if (format == null) {
			return _getFeedTypeFormat(_FEED_TYPE_DEFAULT);
		}

		int x = format.indexOf("atom");

		if (x >= 0) {
			return "atom";
		}

		int y = format.indexOf("rss");

		if (y >= 0) {
			return "rss";
		}

		return _getFeedTypeFormat(_FEED_TYPE_DEFAULT);
	}

	public static double getFormatVersion(String format) {
		if (format == null) {
			return _getFeedTypeVersion(_FEED_TYPE_DEFAULT);
		}

		int x = format.indexOf("10");

		if (x >= 0) {
			return 1.0;
		}

		int y = format.indexOf("20");

		if (y >= 0) {
			return 2.0;
		}

		return _getFeedTypeVersion(_FEED_TYPE_DEFAULT);
	}

	private static String _getFeedTypeFormat(String feedType) {
		if (Validator.isNotNull(feedType)) {
			String[] parts = StringUtil.split(feedType, StringPool.UNDERLINE);

			if (parts.length == 2) {
				return GetterUtil.getString(
					parts[0], _getFeedTypeFormat(_FEED_TYPE_DEFAULT));
			}
		}

		return _getFeedTypeFormat(_FEED_TYPE_DEFAULT);
	}

	private static double _getFeedTypeVersion(String feedType) {
		if (Validator.isNotNull(feedType)) {
			String[] parts = StringUtil.split(feedType, StringPool.UNDERLINE);

			if (parts.length == 2) {
				return GetterUtil.getDouble(
					parts[1], _getFeedTypeVersion(_FEED_TYPE_DEFAULT));
			}
		}

		return _getFeedTypeVersion(_FEED_TYPE_DEFAULT);
	}

	private static final String _FEED_TYPE_DEFAULT = GetterUtil.getString(
		PropsUtil.get(PropsKeys.RSS_FEED_TYPE_DEFAULT), "atom_1.0");

}