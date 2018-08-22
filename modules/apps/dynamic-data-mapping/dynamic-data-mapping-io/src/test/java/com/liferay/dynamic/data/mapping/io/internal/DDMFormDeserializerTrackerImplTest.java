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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;

import java.util.Map;
import java.util.TreeMap;

import mockit.Deencapsulation;
import mockit.Mocked;

import mockit.integration.junit4.JMockit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(JMockit.class)
public class DDMFormDeserializerTrackerImplTest {

	@After
	public void tearDown() {
		_ddmFormDeserializers = null;
	}

	@Test
	public void testAddDDMFormDeserializer() {
		DDMFormDeserializerTrackerImpl ddmFormDeserializerTracker =
			_addDeserializerType();

		_ddmFormDeserializers = Deencapsulation.getField(
			ddmFormDeserializerTracker, "_ddmFormDeserializers");

		Assert.assertEquals(
			_ddmFormDeserializers.toString(), 1, _ddmFormDeserializers.size());
	}

	@Test
	public void testDeactivate() {
		DDMFormDeserializerTrackerImpl ddmFormDeserializerTracker =
			_addDeserializerType();

		_ddmFormDeserializers = Deencapsulation.getField(
			ddmFormDeserializerTracker, "_ddmFormDeserializers");

		Assert.assertNotEquals(
			_ddmFormDeserializers.toString(), 0, _ddmFormDeserializers.size());

		ddmFormDeserializerTracker.deactivate();

		Assert.assertEquals(
			_ddmFormDeserializers.toString(), 0, _ddmFormDeserializers.size());
	}

	@Test
	public void testGetDDMFormDeserializer() {
		DDMFormDeserializerTrackerImpl ddmFormDeserializerTracker =
			_addDeserializerType();

		Assert.assertNotNull(
			ddmFormDeserializerTracker.getDDMFormDeserializer("json"));
	}

	@Test
	public void testRemoveDDMFormDeserializer() {
		DDMFormDeserializerTrackerImpl ddmFormDeserializerTracker =
			_addDeserializerType();

		ddmFormDeserializerTracker.removeDDMFormDeserializer(
			_ddmFormJSONDeserializer, _properties);

		_ddmFormDeserializers = Deencapsulation.getField(
			ddmFormDeserializerTracker, "_ddmFormDeserializers");

		Assert.assertNull(
			ddmFormDeserializerTracker.getDDMFormDeserializer("json"));
	}

	private DDMFormDeserializerTrackerImpl _addDeserializerType() {
		DDMFormDeserializerTrackerImpl ddmFormDeserializerTracker =
			new DDMFormDeserializerTrackerImpl();

		_properties = new TreeMap<>();

		_properties.put("ddm.form.deserializer.type", "json");

		ddmFormDeserializerTracker.addDDMFormDeserializer(
			_ddmFormJSONDeserializer, _properties);

		return ddmFormDeserializerTracker;
	}

	private Map<String, DDMFormDeserializer> _ddmFormDeserializers;

	@Mocked
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;

	private Map<String, Object> _properties;

}