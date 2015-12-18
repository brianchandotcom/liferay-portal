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
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.exportimport.xstream.XStreamConverter;
import com.liferay.portlet.exportimport.xstream.XStreamConverterRegistryUtil;

import java.util.Set;

import org.junit.Assert;
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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.xstreamconverterregistryutil"));

	@Test
	public void testGetXStreamConverters() {
		Set<XStreamConverter> xStreamConverters =
			XStreamConverterRegistryUtil.getXStreamConverters();

		for (XStreamConverter xStreamConverter : xStreamConverters) {
			Class<? extends XStreamConverter> clazz =
				xStreamConverter.getClass();

			String className = clazz.getName();

			if (className.equals(TestXStreamConverter.class.getName())) {
				return;
			}
		}

		Assert.fail();
	}

}