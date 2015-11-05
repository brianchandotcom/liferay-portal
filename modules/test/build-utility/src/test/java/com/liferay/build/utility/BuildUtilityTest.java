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

package com.liferay.build.utility;

import org.junit.Assert;
import org.junit.Test;

/**
* @author Peter Yoo
*/
public class BuildUtilityTest {

	@Test
	public void testExpandSlaveRange() {
		Assert.assertEquals(
			"cloud-10-50-0-151,cloud-10-50-0-152, cloud-10-50-0-153," +
				"cloud-10-50-0-154,cloud-10-50-0-155,cloud-10-50-0-156",
			BuildUtility.expandSlaveRange("cloud-10-50-0-151..156"));		
		Assert.assertEquals(
			"cloud-10-50-0-47,cloud-10-50-0-0,cloud-10-50-0-1," +
				"cloud-10-50-0-2,cloud-10-50-0-49,cloud-10-50-0-50",
			BuildUtility.expandSlaveRange(
				"cloud-10-50-0-47, cloud-10-50-0-0..2, cloud-10-50-0-49..50"));
	}
}
