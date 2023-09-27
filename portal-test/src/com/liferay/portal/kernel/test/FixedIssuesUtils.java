/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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