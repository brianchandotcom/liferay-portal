/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.dynamiccss;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicCSSUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testPropagateQueryString() {
		String[] propagatedQueryStrings = {
			"@import url(//main?test=1);", "@import url(\"//main?test=1\");",
			"@import url('//main?test=1');", "@import url(//main?p=2&test=1);",
			"@import url(\"//main?p=2&test=1\");",
			"@import url('//main?p=2&test=1');",
			"@import url(http://main?test=1);",
			"@import url(\"http://main?test=1\");",
			"@import url('http://main?p=2&test=1');",
			"import \"file\"; @import url(http://main?test=1);",
			"import \"file\"; @import url(\"http://main?test=1\");",
			"import \"file\"; @import url('http://main?p=2&test=1');"
		};
		String[] contents = {
			"@import url(//main);", "@import url(\"//main\");",
			"@import url('//main');", "@import url(//main?p=2);",
			"@import url(\"//main?p=2\");", "@import url('//main?p=2');",
			"@import url(http://main);", "@import url(\"http://main\");",
			"@import url('http://main?p=2');",
			"import \"file\"; @import url(http://main);",
			"import \"file\"; @import url(\"http://main\");",
			"import \"file\"; @import url('http://main?p=2');"
		};

		for (int i = 0; i < propagatedQueryStrings.length; i++) {
			Assert.assertEquals(
				propagatedQueryStrings[i],
				DynamicCSSUtil.propagateQueryString(contents[i], "test=1"));
		}
	}

}