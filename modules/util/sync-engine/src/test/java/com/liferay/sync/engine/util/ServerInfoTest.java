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

package com.liferay.sync.engine.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class ServerInfoTest {

	@Test
	public void testIsCompatible() {
		Assert.assertTrue(ServerInfo.isCompatible("6.2.0.5", "6.2.0.4"));
		Assert.assertTrue(
			ServerInfo.isCompatible("6.2.10.5", "6.2.0.4", "7.0.0.1"));
		Assert.assertTrue(
			ServerInfo.isCompatible("7.0.0.1", "6.2.0.4", "7.0.0.1"));

		Assert.assertFalse(ServerInfo.isCompatible("6.2.10.5", "7.0.0.1"));
		Assert.assertFalse(
			ServerInfo.isCompatible("6.2.10.5", "6.2.0.6", "7.0.0.1"));
	}

}