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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class FullNameGeneratorFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.fullnamegeneratorfactory"));

	@Test
	public void testGetFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		Assert.assertEquals(
			"John Stephen Piper",
			fullNameGenerator.getFullName("John", "Stephen", "Piper"));
	}

	@Test
	public void testGetLocalizedFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		Assert.assertEquals(
			"Jacques",
			fullNameGenerator.getLocalizedFullName(
				"James", "middle", "lastname", Locale.FRENCH, 1, 1));

		Assert.assertNotEquals(
			"Jacques",
			fullNameGenerator.getLocalizedFullName(
				"Tom", "middle", "lastname", Locale.CHINESE, 1, 1));
	}

	@Test
	public void testSplitFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String[] splitFullName = fullNameGenerator.splitFullName(
			"John Stephen Piper");

		Assert.assertEquals(
			Arrays.toString(splitFullName), 3, splitFullName.length);
	}

}