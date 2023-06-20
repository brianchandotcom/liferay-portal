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

package com.liferay.portal.upgrade.internal.live;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.upgrade.live.LiveUpgradeSchemaDiff;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Kevin Lee
 */
public class LiveUpgradeSchemaDiffTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_liveUpgradeSchemaDiff = new LiveUpgradeSchemaDiff(
			Arrays.asList("id", "name"));
	}

	@Test
	public void testRecordAddColumns() {
		_liveUpgradeSchemaDiff.recordAddColumns(
			"version LONG default 0 not null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "name"
			).build());
	}

	@Test
	public void testRecordAlterColumnName() {
		_liveUpgradeSchemaDiff.recordAlterColumnName(
			"name", "title VARCHAR(128) not null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "title"
			).build());
	}

	@Test
	public void testRecordAlterColumnType() {
		_liveUpgradeSchemaDiff.recordAlterColumnName(
			"name", "VARCHAR(255) null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "name"
			).build());

		// TODO: Check type

	}

	@Test
	public void testRecordDropColumns() {
		_liveUpgradeSchemaDiff.recordDropColumns("name");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).build());
	}

	private void _checkResultColumnNamesMap(
		Map<String, String> expectedColumnNamesMap) {

		Map<String, String> actualColumnNamesMap =
			_liveUpgradeSchemaDiff.getResultColumnNamesMap();

		Assert.assertEquals(
			actualColumnNamesMap.toString(), expectedColumnNamesMap.size(),
			actualColumnNamesMap.size());

		for (Map.Entry<String, String> entry :
				actualColumnNamesMap.entrySet()) {

			Assert.assertTrue(
				expectedColumnNamesMap.containsKey(entry.getKey()));
			Assert.assertEquals(
				expectedColumnNamesMap.get(entry.getKey()), entry.getValue());
		}
	}

	private LiveUpgradeSchemaDiff _liveUpgradeSchemaDiff;

}