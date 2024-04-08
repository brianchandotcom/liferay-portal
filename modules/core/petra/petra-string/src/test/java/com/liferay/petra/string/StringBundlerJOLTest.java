/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.string;

import org.junit.Assert;
import org.junit.Test;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Shuyang Zhou
 */
public class StringBundlerJOLTest {

	@Test
	public void testStringBundler() {
		ClassLayout classLayout = ClassLayout.parseClass(StringBundler.class);

		Assert.assertEquals(24, classLayout.instanceSize());
		Assert.assertEquals(4, classLayout.getLossesExternal());
	}

}