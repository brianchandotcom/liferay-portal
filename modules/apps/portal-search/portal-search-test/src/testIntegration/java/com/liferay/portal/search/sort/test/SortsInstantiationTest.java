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

	@Inject
	private static Sorts _sorts;

}