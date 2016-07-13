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

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
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
		mockCardinality(Integer.MAX_VALUE);

		mockDefaultValue("A", "B", "C");

		assertDefaultValue("A", "B", "C");
	}

	@Test
	public void testDefaultValueBlankString() {
		mockDefaultValue(StringPool.BLANK);

		assertDefaultValue(StringPool.BLANK);
	}

	@Test
	public void testDefaultValueEmpty() {
		Mockito.doReturn(
			new String[0]
		).when(
			_attributeDefinition
		).getDefaultValue();

		assertDefaultValue(StringPool.BLANK);
	}

	@Test
	public void testDefaultValueWithPipesArray() {
		mockCardinality(42);

		mockDefaultValue("A|B|C");

		assertDefaultValue("A", "B", "C");
	}

	@Test
	public void testDefaultValueWithPipesString() {
		mockDefaultValue("A|B|C");

		assertDefaultValue("A|B|C");
	}

	@Test
	public void testPropertyArray() {
		mockCardinality(2);

		mockProperty(new Object[] {false, true});

		assertProperty("false", "true");
	}

	@Test
	public void testPropertyEmpty() {
		assertProperty();
	}

	@Test
	public void testPropertyObject() {
		mockProperty(42);

		assertProperty("42");
	}

	@Test
	public void testPropertyVector() {
		mockCardinality(-3);

		mockProperty(new Vector<Integer>(Arrays.asList(1, 2, 3)));

		assertProperty("1", "2", "3");
	}

	protected void assertDefaultValue(String... expecteds) {
		Assert.assertArrayEquals(
			expecteds,
			AttributeDefinitionUtil.getDefaultValue(_attributeDefinition));
	}

	protected void assertProperty(String... expecteds) {
		Assert.assertArrayEquals(
			expecteds,
			AttributeDefinitionUtil.getProperty(
				_attributeDefinition, _configuration));
	}

	protected void mockCardinality(int value) {
		Mockito.doReturn(
			value
		).when(
			_attributeDefinition
		).getCardinality();
	}

	protected void mockDefaultValue(String... value) {
		Mockito.doReturn(
			value
		).when(
			_attributeDefinition
		).getDefaultValue();
	}

	protected void mockProperty(Object value) {
		_properties.put(_ID, value);
	}

	private static final String _ID = RandomTestUtil.randomString();

	@Mock
	private AttributeDefinition _attributeDefinition;

	@Mock
	private Configuration _configuration;

	private final Dictionary<String, Object> _properties = new Hashtable<>();

}