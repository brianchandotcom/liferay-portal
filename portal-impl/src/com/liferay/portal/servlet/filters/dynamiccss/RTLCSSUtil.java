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

package com.liferay.portal.servlet.filters.dynamiccss;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author Eduardo Garcia
 */
public class RTLCSSUtil {

	public static String getRtlCss(String fileName, String css)
		throws Exception {

		Context context = Context.enter();

		String rtlCss = css;

		try {
			ScriptableObject scope = context.initStandardObjects();

			context.evaluateString(scope, _jsScript, "script", 1, null);

			Function function = (Function)scope.get("r2", scope);

			Object result = function.call(
				context, scope, scope, new Object[] {css});

			rtlCss = (String)Context.jsToJava(result, String.class);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse " + fileName + " to RTL");
			}
		}
		finally {
			Context.exit();
		}

		return rtlCss;
	}

	public static void init() {
		try {
			_jsScript = StringUtil.read(
				ClassLoaderUtil.getPortalClassLoader(),
				"com/liferay/portal/servlet/filters/dynamiccss" +
					"/dependencies/r2.js");
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static boolean isExcludedPath(String filePath) {
		for (Pattern pattern : _RTL_CSS_EXCLUDED_PATHS_PATTERNS) {
			Matcher matcher = pattern.matcher(filePath);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(RTLCSSUtil.class);

	private static Pattern[] _RTL_CSS_EXCLUDED_PATHS_PATTERNS;

	static {
		String[] rtlCssExcludedPathsRegexp =
			PropsValues.RTL_CSS_EXCLUDED_PATHS_REGEXP;

		_RTL_CSS_EXCLUDED_PATHS_PATTERNS =
			new Pattern[rtlCssExcludedPathsRegexp.length];

		for (int i = 0; i < rtlCssExcludedPathsRegexp.length; i++) {
			_RTL_CSS_EXCLUDED_PATHS_PATTERNS[i] = Pattern.compile(
				rtlCssExcludedPathsRegexp[i]);
		}
	}

	private static String _jsScript;

}