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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author André de Oliveira
 */
public class AttributeDefinitionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.doReturn(
			_ID
		).when(
			_attributeDefinition
		).getID();

		Mockito.doReturn(
			_properties
		).when(
			_configuration
		).getProperties();
	}

	@Test
	public void testDefaultValueArray() {
		_mockCardinality(Integer.MAX_VALUE);

		_mockDefaultValue("A", "B", "C");

		_assertDefaultValue("A", "B", "C");
	}

	@Test
	public void testDefaultValueBlankString() {
		_mockDefaultValue(StringPool.BLANK);

		_assertDefaultValue(StringPool.BLANK);
	}

	@Test
	public void testDefaultValueEmpty() {
		Mockito.doReturn(
			new String[0]
		).when(
			_attributeDefinition
		).getDefaultValue();

		_assertDefaultValue(StringPool.BLANK);
	}

	@Test
	public void testDefaultValueWithPipesArray() {
		_mockCardinality(42);

		_mockDefaultValue("A|B|C");

		_assertDefaultValue("A", "B", "C");
	}

	@Test
	public void testDefaultValueWithPipesString() {
		_mockDefaultValue("A|B|C");

		_assertDefaultValue("A|B|C");
	}

	@Test
	public void testPropertyArray() {
		_mockCardinality(2);

		_mockProperty(new Object[] {false, true});

		_assertProperty("false", "true");
	}

	@Test
	public void testPropertyEmpty() {
		_assertProperty();
	}

	@Test
	public void testPropertyObject() {
		_mockProperty(42);

		_assertProperty("42");
	}

	@Test
	public void testPropertyVector() {
		_mockCardinality(-3);

		_mockProperty(new Vector<Integer>(Arrays.asList(1, 2, 3)));

		_assertProperty("1", "2", "3");
	}

	private void _assertDefaultValue(String... expecteds) {
		Assert.assertArrayEquals(
			expecteds,
			AttributeDefinitionUtil.getDefaultValue(_attributeDefinition));
	}

	private void _assertProperty(String... expecteds) {
		Assert.assertArrayEquals(
			expecteds,
			AttributeDefinitionUtil.getPropertyStringArray(
				_attributeDefinition, _configuration));
	}

	private void _mockCardinality(int value) {
		Mockito.doReturn(
			value
		).when(
			_attributeDefinition
		).getCardinality();
	}

	private void _mockDefaultValue(String... value) {
		Mockito.doReturn(
			value
		).when(
			_attributeDefinition
		).getDefaultValue();
	}

	private void _mockProperty(Object value) {
		_properties.put(_ID, value);
	}

	private static final String _ID = RandomTestUtil.randomString();

	@Mock
	private AttributeDefinition _attributeDefinition;

	@Mock
	private Configuration _configuration;

	private final Dictionary<String, Object> _properties = new Hashtable<>();

}