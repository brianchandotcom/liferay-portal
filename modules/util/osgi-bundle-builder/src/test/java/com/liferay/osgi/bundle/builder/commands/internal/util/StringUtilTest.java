/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder.commands.internal.util;

import com.liferay.osgi.bundle.builder.internal.util.StringUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtilTest {

	@Test
	public void testJoin() {
		Assert.assertEquals("foo", StringUtil.join(Arrays.asList("foo"), ','));
		Assert.assertEquals(
			"foo,bar,baz",
			StringUtil.join(Arrays.asList("foo", "bar", "baz"), ','));
	}

}