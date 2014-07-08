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

import org.junit.Assert;
import org.junit.Test;

public class DynamicCSSUtilTest {

	@Test
	public void testPropagateQueryString() {
		DynamicCSSUtil cssUtil = new DynamicCSSUtil();

		String query = "test=1";

		String url = "@import url(//main);";
		String expected = "@import url(//main?test=1);";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url(\"//main\");";
		expected = "@import url(\"//main?test=1\");";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url('//main');";
		expected = "@import url('//main?test=1');";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url(//main?p=2);";
		expected = "@import url(//main?p=2&test=1);";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url(\"//main?p=2\");";
		expected = "@import url(\"//main?p=2&test=1\");";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url('//main?p=2');";
		expected = "@import url('//main?p=2&test=1');";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url(http://main);";
		expected = "@import url(http://main?test=1);";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url(\"http://main\");";
		expected = "@import url(\"http://main?test=1\");";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));

		url = "@import url('http://main?p=2');";
		expected = "@import url('http://main?p=2&test=1');";

		Assert.assertEquals(expected, cssUtil.propagateQueryString(url, query));
	}
}