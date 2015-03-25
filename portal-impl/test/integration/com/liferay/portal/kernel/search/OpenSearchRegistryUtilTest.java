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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.bundle.opensearchregistryutil.TestOpenSearch;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class OpenSearchRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.opensearchregistryutil"));

	@Test
	public void testGetOpenSearch() {
		OpenSearch openSearch = OpenSearchRegistryUtil.getOpenSearch(
			TestOpenSearch.class);

		String testOpenSearchClassName = TestOpenSearch.class.getName();

		Assert.assertEquals(testOpenSearchClassName, openSearch.getClassName());
	}

	@Test
	public void testGetOpenSearch2() {
		OpenSearch openSearch = OpenSearchRegistryUtil.getOpenSearch(
			TestOpenSearch.class.getName());

		String testOpenSearchClassName = TestOpenSearch.class.getName();

		Assert.assertEquals(testOpenSearchClassName, openSearch.getClassName());
	}

	@Test
	public void testGetOpenSearchInstances() {
		List<OpenSearch> openSearches =
			OpenSearchRegistryUtil.getOpenSearchInstances();

		String testOpenSearchClassName = TestOpenSearch.class.getName();

		String openSearchClassName = null;

		for (OpenSearch openSearch : openSearches) {
			openSearchClassName = openSearch.getClassName();

			if (testOpenSearchClassName.equals(openSearchClassName)) {
				Assert.assertTrue(true);

				return;
			}
		}

		Assert.fail();
	}

}