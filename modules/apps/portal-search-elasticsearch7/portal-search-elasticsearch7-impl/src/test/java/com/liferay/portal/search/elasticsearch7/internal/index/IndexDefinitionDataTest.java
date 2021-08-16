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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.spi.index.IndexDefinition;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class IndexDefinitionDataTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMissingIndexSettingsResource() {
		String resourceName = RandomTestUtil.randomString();

		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			"Unable to load resource: " + resourceName);

		new IndexDefinitionData(
			Mockito.mock(IndexDefinition.class),
			HashMapBuilder.put(
				IndexDefinition.PROPERTY_KEY_INDEX_NAME,
				(Object)RandomTestUtil.randomString()
			).put(
				IndexDefinition.PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME,
				resourceName
			).build());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

}