/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class IgnoreTestItem {

	public static void assertAndTearDown() throws IOException {
		List<String> lines = _testItemHelper.read();

		Assert.assertEquals(lines.toString(), 1, lines.size());
		Assert.assertEquals(lines.toString(), "test", lines.get(0));
	}

	@Ignore
	@Test
	public void testIgnore() throws Exception {
		_testItemHelper.write("ignore");
	}

	@Test
	public void testNoIgnore() throws Exception {
		_testItemHelper.write("test");
	}

	private static final TestItemHelper _testItemHelper = new TestItemHelper(
		IgnoreTestItem.class);

}