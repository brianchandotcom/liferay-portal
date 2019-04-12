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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.poller.bundle.pollerprocessorutil.TestPollerProcessor;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class PollerProcessorUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule("bundle.pollerprocessorutil"));

	@Test
	public void testGetPollerProcessor() {
		PollerProcessor pollerProcessor =
			PollerProcessorUtil.getPollerProcessor("PollerProcessorUtilTest");

		Assert.assertNotNull(pollerProcessor);

		Class<?> clazz = pollerProcessor.getClass();

		Assert.assertEquals(
			TestPollerProcessor.class.getName(), clazz.getName());
	}

}