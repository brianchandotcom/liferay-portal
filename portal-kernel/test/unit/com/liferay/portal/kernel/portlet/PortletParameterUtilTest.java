/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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