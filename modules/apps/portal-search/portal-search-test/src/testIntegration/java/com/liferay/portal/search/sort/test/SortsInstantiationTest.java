/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.sort.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class SortsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFieldSort1() {
		FieldSort fieldSort = _sorts.field("field");

		Assert.assertNotNull(fieldSort);
	}

	@Test
	public void testFieldSort2() {
		SortOrder sortOrder = null;

		FieldSort fieldSort = _sorts.field("field", sortOrder);

		Assert.assertNotNull(fieldSort);
	}

	@Test
	public void testGeoDistanceSort() {
		GeoDistanceSort geoDistanceSort = _sorts.geoDistance("field");

		Assert.assertNotNull(geoDistanceSort);
	}

	@Test
	public void testNestedSort() {
		NestedSort nestedSort = _sorts.nested("path");

		Assert.assertNotNull(nestedSort);
	}

	@Test
	public void testScoreSort() {
		ScoreSort scoreSort = _sorts.score();

		Assert.assertNotNull(scoreSort);
	}

	@Test
	public void testScriptSort() {
		Script script = null;
		ScriptSort.ScriptSortType scriptSortType = null;

		ScriptSort scriptSort = _sorts.script(script, scriptSortType);

		Assert.assertNotNull(scriptSort);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Inject
	private static Sorts _sorts;

}