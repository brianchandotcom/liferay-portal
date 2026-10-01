/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabor Komaromi
 */
public class VulcanPropertyFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSerializeAsFieldWithUnwrappedEntity() throws Exception {
		JsonNode jsonNode = _serialize(null, null);

		Assert.assertTrue(jsonNode.has("entityProperty1"));
		Assert.assertTrue(jsonNode.has("entityProperty2"));
		Assert.assertTrue(jsonNode.has("extendedProperty1"));
		Assert.assertTrue(jsonNode.has("extendedProperty2"));
		Assert.assertEquals(4, jsonNode.size());

		jsonNode = _serialize(
			SetUtil.fromArray("entityProperty1", "extendedProperty1"), null);

		Assert.assertTrue(jsonNode.has("entityProperty1"));
		Assert.assertFalse(jsonNode.has("entityProperty2"));
		Assert.assertTrue(jsonNode.has("extendedProperty1"));
		Assert.assertFalse(jsonNode.has("extendedProperty2"));

		jsonNode = _serialize(
			null, SetUtil.fromArray("entityProperty1", "extendedProperty1"));

		Assert.assertFalse(jsonNode.has("entityProperty1"));
		Assert.assertTrue(jsonNode.has("entityProperty2"));
		Assert.assertFalse(jsonNode.has("extendedProperty1"));
		Assert.assertTrue(jsonNode.has("extendedProperty2"));
	}

	private JsonNode _serialize(
			Set<String> fieldNames, Set<String> restrictFieldNames)
		throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readTree(
			objectMapper.writer(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							VulcanPropertyFilter.of(
								fieldNames, restrictFieldNames));
					}
				}
			).writeValueAsString(
				new TestExtendedEntity()
			));
	}

	@JsonFilter("Liferay.Vulcan")
	private static class TestEntity {

		public String entityProperty1 = RandomTestUtil.randomString();
		public String entityProperty2 = RandomTestUtil.randomString();

	}

	@JsonFilter("Liferay.Vulcan")
	private static class TestExtendedEntity {

		@JsonUnwrapped
		public TestEntity getEntity() {
			return _entity;
		}

		@JsonAnyGetter
		public Map<String, Object> getExtendedProperties() {
			return _extendedProperties;
		}

		private final TestEntity _entity = new TestEntity();
		private final Map<String, Object> _extendedProperties =
			Collections.unmodifiableMap(
				HashMapBuilder.<String, Object>put(
					"extendedProperty1", RandomTestUtil.randomString()
				).put(
					"extendedProperty2", RandomTestUtil.randomString()
				).build());

	}

}