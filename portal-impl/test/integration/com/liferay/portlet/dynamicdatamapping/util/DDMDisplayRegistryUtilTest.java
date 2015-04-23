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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.dynamicdatamapping.util.bundle.ddmdisplayregistryutil.TestDDMDisplay;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class DDMDisplayRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.ddmdisplayregistryutil"));

	@Test
	public void testGetDDMDisplay() {
		String testClassName = TestDDMDisplay.class.getName();

		DDMDisplay dDMDisplay = DDMDisplayRegistryUtil.getDDMDisplay(
			testClassName);

		Class<?> clazz = dDMDisplay.getClass();

		Assert.assertEquals(testClassName, clazz.getName());
	}

	@Test
	public void testGetDDMDisplays() {
		String testClassName = TestDDMDisplay.class.getName();

		List<DDMDisplay> dDMDisplays = DDMDisplayRegistryUtil.getDDMDisplays();

		for (DDMDisplay dDMDisplay : dDMDisplays) {
			Class<?> clazz = dDMDisplay.getClass();

			if (testClassName.equals(clazz.getName())) {
				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testGetPortletIds() {
		String testClassName = TestDDMDisplay.class.getName();

		String[] portletIds = DDMDisplayRegistryUtil.getPortletIds();

		for (String portletId : portletIds) {
			if (testClassName.equals(portletId)) {
				return;
			}
		}

		Assert.fail();
	}

}