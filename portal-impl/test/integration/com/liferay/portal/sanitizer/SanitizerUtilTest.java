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

package com.liferay.portal.sanitizer;

import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.sanitizer.bundle.sanitizerimpl.TestSanitizer;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;
import com.liferay.portal.util.test.AtomicState;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class SanitizerUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule("bundle.sanitizerimpl"));

	@BeforeClass
	public static void setUpClass() {
		_atomicState = new AtomicState();
	}

	@AfterClass
	public static void tearDownClass() {
		_atomicState.close();
	}

	@Test
	public void testSanitize1() throws SanitizerException {
		String value = SanitizerUtil.sanitize(
			1, 1, 1, TestSanitizer.class.getName(), 1, "contentType", "s");

		Assert.assertEquals("1:1", value);
	}

	@Test
	public void testSanitize2() throws SanitizerException {
		String value = SanitizerUtil.sanitize(
			1, 1, 1, TestSanitizer.class.getName(), 1, "contentType",
			Sanitizer.MODE_ALL, "s", new HashMap<String, Object>());

		Assert.assertEquals("1:1", value);
	}

	@Test
	public void testSanitize3() throws SanitizerException {
		String value = SanitizerUtil.sanitize(
			1, 1, 1, TestSanitizer.class.getName(), 1, "contentType",
			new String[] {
				Sanitizer.MODE_ALL, Sanitizer.MODE_BAD_WORDS, Sanitizer.MODE_XSS
			},
			"s", new HashMap<String, Object>());

		Assert.assertEquals("1:1", value);
	}

	private static AtomicState _atomicState;

}