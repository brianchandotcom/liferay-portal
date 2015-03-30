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

package com.liferay.portal.kernel.lar.xstream;

import com.liferay.portal.kernel.lar.xstream.bundle.xstreamconverterregistryutil.TestXStreamConverter;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.test.AtomicState;

import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class XStreamConverterRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.xstreamconverterregistryutil"));

	@BeforeClass
	public static void setUpClass() {
		_atomicStateUtil = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicStateUtil.close();
	}

	@Test
	public void testGetXStreamConverters() {
		boolean found = false;

		String TestXStreamConverterClassName =
			TestXStreamConverter.class.getName();

		Set<XStreamConverter> xStreamConverters =
			XStreamConverterRegistryUtil.getXStreamConverters();

		for (XStreamConverter xStreamConverter : xStreamConverters) {
			Class<? extends XStreamConverter> clazz =
				xStreamConverter.getClass();

			String className = clazz.getName();

			if (TestXStreamConverterClassName.equals(className)) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

	private static AtomicState _atomicStateUtil;

}