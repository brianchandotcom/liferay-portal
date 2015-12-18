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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.ServiceReference;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class CustomJspBagRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.customjspbagregistryutil"));

	@Test
	public void testGetCustomJspBags() {
		Map<ServiceReference<CustomJspBag>, CustomJspBag> customJspBags =
			CustomJspBagRegistryUtil.getCustomJspBags();

		for (Entry<ServiceReference<CustomJspBag>, CustomJspBag> entry :
				customJspBags.entrySet()) {

			ServiceReference<CustomJspBag> serviceReference = entry.getKey();

			String contextId = GetterUtil.getString(
				serviceReference.getProperty("context.id"));

			if (!contextId.equals("TestCustomJspBag")) {
				continue;
			}

			return;
		}

		Assert.fail();
	}

}