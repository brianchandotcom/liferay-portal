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

package com.liferay.portal.servlet.filters.dynamiccss;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.regex.PatternFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;
import com.liferay.rtl.css.RTLCSSConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eduardo Garcia
 * @author David Truong
 */
public class RTLCSSUtil {

	public static String getRtlCss(String fileName, String css)
		throws Exception {

		String rtlCss = css;

		try {
			RTLCSSConverter rtlCSSConverter = new RTLCSSConverter();

			rtlCss = rtlCSSConverter.process(rtlCss);
		}
		catch (Exception e) {
			_log.error(
				"Unable to generate RTL version for " + fileName +
					StringPool.COMMA_AND_SPACE + e.getMessage());
		}

		return rtlCss;
	}

	public static void init() {
	}

	public static boolean isExcludedPath(String filePath) {
		for (Pattern pattern : _patterns) {
			Matcher matcher = pattern.matcher(filePath);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(RTLCSSUtil.class);

	private static final Pattern[] _patterns = PatternFactory.compile(
		PropsValues.RTL_CSS_EXCLUDED_PATHS_REGEXP);

}