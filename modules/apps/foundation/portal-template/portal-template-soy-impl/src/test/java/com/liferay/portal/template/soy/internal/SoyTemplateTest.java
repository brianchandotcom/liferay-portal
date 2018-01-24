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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.data.SoyMapData;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.soy.constants.SoyTemplateConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

/**
 * @author Bruno Basto
 */
public class SoyTemplateTest {

	@Before
	public void setUp() throws Exception {
		_soyTestHelper.setUp();
	}

	@After
	public void tearDown() {
		_soyTestHelper.tearDown();
	}

	@Test
	public void testClear() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.put("key1", "value1");
		soyTemplate.put("key2", "value2");

		soyTemplate.putInjectedData("injectedKey", "injectedValue");

		soyTemplate.clear();

		SoyMapData soyMapData = soyTemplate.getSoyMapData();

		Set<String> keys = soyMapData.getKeys();

		Assert.assertEquals(keys.toString(), 0, keys.size());

		keys = soyTemplate.keySet();

		Assert.assertEquals(keys.toString(), 0, keys.size());

		SoyMapData injectedSoyMapData = soyTemplate.getSoyMapInjectedData();

		keys = injectedSoyMapData.getKeys();

		Assert.assertEquals(keys.toString(), 0, keys.size());
	}

	/**
	 * Tests if data injected with the Injected Data API is rendered.
	 */
	@Test
	public void testProcessTemplateWithInjectedData() throws Exception {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.put("namespace", "soy.test.ijdata");

		Map<String, Object> injectedData = new HashMap<>();

		injectedData.put("hasData", true);

		soyTemplate.put(SoyTemplateConstants.INJECTED_DATA, injectedData);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		soyTemplate.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Injected Data: true", unsyncStringWriter.toString());
	}

	@Test
	public void testPut() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		SoyMapData soyMapData = soyTemplate.getSoyMapData();

		soyTemplate.put("key", "value");

		Assert.assertEquals("value", soyMapData.getString("key"));
	}

	@Test
	public void testPutRestrictedVariable() {
		SoyTemplate soyTemplate = Mockito.spy(
			_soyTestHelper.getSoyTemplate("ijdata.soy"));

		TemplateContextHelper templateContextHelper = Mockito.mock(
			TemplateContextHelper.class);

		Mockito.when(
			soyTemplate.getTemplateContextHelper()
		).thenReturn(
			templateContextHelper
		);

		Mockito.when(
			templateContextHelper.getRestrictedVariables()
		).thenReturn(
			Sets.newSet("restrictedKey")
		);

		soyTemplate.put("restrictedKey", "restrictedValue");

		Mockito.verify(
			soyTemplate, Mockito.times(0)
		).getSoyMapValue(
			"restrictedValue"
		);

		SoyMapData soyMapData = soyTemplate.getSoyMapData();

		Assert.assertFalse("value", soyMapData.hasField("restrictedKey"));
	}

	@Test
	public void testPutWithSameValue() {
		SoyTemplate soyTemplate = Mockito.spy(
			_soyTestHelper.getSoyTemplate("ijdata.soy"));

		String value = "value";

		soyTemplate.put("key", value);
		soyTemplate.put("key", value);

		Mockito.verify(
			soyTemplate, Mockito.times(1)
		).getSoyMapValue(
			value
		);

		SoyMapData soyMapData = soyTemplate.getSoyMapData();

		Assert.assertEquals("value", soyMapData.getString("key"));
	}

	@Test
	public void testRemove() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.put("key1", "value1");
		soyTemplate.put("key2", "value2");

		soyTemplate.putInjectedData("injectedKey", "injectedValue");

		soyTemplate.remove("key2");

		SoyMapData soyMapData = soyTemplate.getSoyMapData();

		Assert.assertTrue(soyMapData.hasField("key1"));
		Assert.assertEquals("value1", soyMapData.getString("key1"));

		SoyMapData injectedSoyMapData = soyTemplate.getSoyMapInjectedData();

		Assert.assertEquals(
			"injectedValue", injectedSoyMapData.getString("injectedKey"));
	}

	@Test
	public void testRemoveInjectedData() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.putInjectedData("injectedKey", "injectedValue");

		soyTemplate.remove(SoyTemplateConstants.INJECTED_DATA);

		SoyMapData injectedSoyMapData = soyTemplate.getSoyMapInjectedData();

		Set<String> keys = injectedSoyMapData.getKeys();

		Assert.assertEquals(keys.toString(), 0, keys.size());
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();

}