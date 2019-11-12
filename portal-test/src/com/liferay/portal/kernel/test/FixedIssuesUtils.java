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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assume;

/**
 * @author     Zsolt Balogh
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class FixedIssuesUtils {

	public static void assumeIssueIsFixed(String issue) {
		Assume.assumeTrue(isIssueFixed(issue));
	}

	public static void assumeIssueIsNotFixed(String issue) {
		Assume.assumeFalse(isIssueFixed(issue));
	}

	public static boolean isIssueFixed(String issue) {
		return ArrayUtil.contains(_fixedIssuesUtils._fixedIssues, issue);
	}

	private FixedIssuesUtils() {
		_fixedIssues = StringUtil.split(
			GetterUtil.getString(System.getProperty("fixed.issues")));
	}

	private static final FixedIssuesUtils _fixedIssuesUtils =
		new FixedIssuesUtils();

	private final String[] _fixedIssues;

}