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

import com.liferay.petra.string.StringPool;

/**
 * @author     Brian Wing Shun Chan
 * @author     Eduardo García
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.rss.util.RSSUtil}
 */
@Deprecated
public class RSSUtil {

	public static final String ATOM = "atom";

	public static final String DISPLAY_STYLE_ABSTRACT = "abstract";

	public static final String DISPLAY_STYLE_DEFAULT =
		_getDisplayStyleDefault();

	public static final String DISPLAY_STYLE_FULL_CONTENT = "full-content";

	public static final String DISPLAY_STYLE_TITLE = "title";

	public static final String[] DISPLAY_STYLES = {
		DISPLAY_STYLE_ABSTRACT, DISPLAY_STYLE_FULL_CONTENT, DISPLAY_STYLE_TITLE
	};

	public static final String ENTRY_TYPE_DEFAULT = "html";

	public static final String FEED_TYPE_DEFAULT = _getFeedTypeDefault();

	public static final String[] FEED_TYPES = _getFeedTypes();

	public static final String FORMAT_DEFAULT = getFeedTypeFormat(
		FEED_TYPE_DEFAULT);

	public static final String RSS = "rss";

	public static final double VERSION_DEFAULT = getFeedTypeVersion(
		FEED_TYPE_DEFAULT);

	public static String getFeedType(String type, double version) {
		return type + StringPool.UNDERLINE + version;
	}

	public static String getFeedTypeFormat(String feedType) {
		if (Validator.isNotNull(feedType)) {
			String[] parts = StringUtil.split(feedType, StringPool.UNDERLINE);

			if (parts.length == 2) {
				return GetterUtil.getString(parts[0], FORMAT_DEFAULT);
			}
		}

		return FORMAT_DEFAULT;
	}

	public static String getFeedTypeName(String feedType) {
		String type = getFeedTypeFormat(feedType);

		if (type.equals(ATOM)) {
			type = "Atom";
		}
		else if (type.equals(RSS)) {
			type = "RSS";
		}

		double version = getFeedTypeVersion(feedType);

		return type + StringPool.SPACE + version;
	}

	public static double getFeedTypeVersion(String feedType) {
		if (Validator.isNotNull(feedType)) {
			String[] parts = StringUtil.split(feedType, StringPool.UNDERLINE);

			if (parts.length == 2) {
				return GetterUtil.getDouble(parts[1], VERSION_DEFAULT);
			}
		}

		return VERSION_DEFAULT;
	}

	public static String getFormatType(String format) {
		if (format == null) {
			return FORMAT_DEFAULT;
		}

		int x = format.indexOf(ATOM);

		if (x >= 0) {
			return ATOM;
		}

		int y = format.indexOf(RSS);

		if (y >= 0) {
			return RSS;
		}

		return FORMAT_DEFAULT;
	}

	public static double getFormatVersion(String format) {
		if (format == null) {
			return VERSION_DEFAULT;
		}

		int x = format.indexOf("10");

		if (x >= 0) {
			return 1.0;
		}

		int y = format.indexOf("20");

		if (y >= 0) {
			return 2.0;
		}

		return VERSION_DEFAULT;
	}

	private static String _getDisplayStyleDefault() {
		return GetterUtil.getString(
			PropsUtil.get(PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT),
			DISPLAY_STYLE_FULL_CONTENT);
	}

	private static String _getFeedTypeDefault() {
		return GetterUtil.getString(
			PropsUtil.get(PropsKeys.RSS_FEED_TYPE_DEFAULT),
			getFeedType(ATOM, 1.0));
	}

	private static String[] _getFeedTypes() {
		return GetterUtil.getStringValues(
			PropsUtil.getArray(PropsKeys.RSS_FEED_TYPES),
			new String[] {FEED_TYPE_DEFAULT});
	}

}