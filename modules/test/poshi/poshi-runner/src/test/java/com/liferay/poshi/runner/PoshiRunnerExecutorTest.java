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

package com.liferay.poshi.runner;

import com.liferay.poshi.core.PoshiContext;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PoshiRunnerExecutorTest {

	@Test
	public void testGetVarMethodValue() throws Exception {
		Object value = PoshiRunnerExecutor.getVarMethodValue(
			"MathUtil#quotient('3', '1')", PoshiContext.getDefaultNamespace());

		Assert.assertEquals(
			"getVarMethodValue is failing", "3", value.toString());

		value = PoshiRunnerExecutor.getVarMethodValue(
			"StringUtil#endsWith('The fox jumped over the dog', 'dog')",
			PoshiContext.getDefaultNamespace());

		Assert.assertEquals("getVarMethodValue is failing", true, value);
	}

}