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

package com.liferay.portal.kernel.portlet;

import com.liferay.petra.string.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
public class PortletParameterUtilTest {

	@Test
	public void testAddNamespace() {
		Assert.assertEquals(
			"p_p_id=15",
			PortletParameterUtil.addNamespace("15", StringPool.BLANK));
		Assert.assertEquals(
			"p_p_id=15&_15_param1=value1",
			PortletParameterUtil.addNamespace("15", "param1=value1"));
		Assert.assertEquals(
			"p_p_id=15&_15_param1=value1&_15_param2=value2&_15_param3=value3",
			PortletParameterUtil.addNamespace(
				"15", "param1=value1&param2=value2&param3=value3"));
	}

}