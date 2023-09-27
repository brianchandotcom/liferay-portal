/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ResourceBundleEnumerationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testWithEnumeration() {
		Set<String> set = new LinkedHashSet<>(Arrays.asList("key1", "key2"));
		Enumeration<String> enumeration = Collections.enumeration(
			Arrays.asList("key2", "key3"));

		ResourceBundleEnumeration resourceBundleEnumeration =
			new ResourceBundleEnumeration(set, enumeration);

		Assert.assertTrue(resourceBundleEnumeration.hasMoreElements());
		Assert.assertEquals("key1", resourceBundleEnumeration.nextElement());
		Assert.assertEquals("key2", resourceBundleEnumeration.nextElement());
		Assert.assertTrue(resourceBundleEnumeration.hasMoreElements());
		Assert.assertEquals("key3", resourceBundleEnumeration.nextElement());

		try {
			resourceBundleEnumeration.nextElement();

			Assert.fail();
		}
		catch (NoSuchElementException noSuchElementException) {
		}
	}

	@Test
	public void testWithoutEnumeration() {
		Set<String> set = new LinkedHashSet<>(Arrays.asList("key1", "key2"));

		ResourceBundleEnumeration resourceBundleEnumeration =
			new ResourceBundleEnumeration(set, null);

		Assert.assertTrue(resourceBundleEnumeration.hasMoreElements());
		Assert.assertEquals("key1", resourceBundleEnumeration.nextElement());
		Assert.assertEquals("key2", resourceBundleEnumeration.nextElement());

		try {
			resourceBundleEnumeration.nextElement();

			Assert.fail();
		}
		catch (NoSuchElementException noSuchElementException) {
		}
	}

}