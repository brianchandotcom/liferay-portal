/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

/**
 * @author Alan Huang
 */
public class JavaTestInfoAnnotationCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/test/") &&
			!absolutePath.contains("/testIntegration/")) {

			return content;
		}

		int x = -1;

		while (true) {
			x = content.indexOf("\n\t@TestInfo(\"", x + 1);

			if (x == -1) {
				return content;
			}

			int y = content.indexOf("\")", x);

			if (y == -1) {
				return content;
			}

			String testInfo = content.substring(x + 13, y);

			String[] array = testInfo.split(",");

			if (array.length < 2) {
				return content;
			}

			Arrays.sort(array, new NaturalOrderStringComparator());

			String newTestInfo = StringUtil.merge(array);

			if (!testInfo.equals(newTestInfo)) {
				return StringUtil.replaceFirst(
					content, testInfo, newTestInfo, x);
			}
		}
	}

}