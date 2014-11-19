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

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Andrew Betts
 */
@PrepareForTest({PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class TreePathUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(PropsUtil.class);

		when(
			PropsUtil.get(
				PropsKeys.MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE)
		).thenReturn(
			"10000"
		);
	}

	@Test
	public void testGetPrimaryKeys() {
		String treePath = "";

		Assert.assertEquals(null, TreePathUtil.getPrimaryKeys(treePath));

		treePath = "/";

		Assert.assertArrayEquals(
			new long[0], TreePathUtil.getPrimaryKeys(treePath));

		treePath = "123/456/789";

		Assert.assertArrayEquals(
			new long[]{123, 456, 789}, TreePathUtil.getPrimaryKeys(treePath));

		treePath = "/123/456/789/";

		Assert.assertArrayEquals(
			new long[]{123, 456, 789}, TreePathUtil.getPrimaryKeys(treePath));
	}

}