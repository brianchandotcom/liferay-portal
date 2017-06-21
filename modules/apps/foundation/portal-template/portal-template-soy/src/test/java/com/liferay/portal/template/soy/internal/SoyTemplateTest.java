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
import com.liferay.portal.template.soy.utils.SoyTemplateConstants;

import java.util.HashMap;
import java.util.Map;

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
		SoyTemplate template = _soyTestHelper.getSoyTemplate("ijdata.soy");

		template.put("key1", "value1");
		template.put("key2", "value2");

		template.putInjectedData("injectedKey", "injectedValue");

		template.clear();

		SoyMapData soyMapData = template.getSoyMapData();

		Assert.assertEquals(0, soyMapData.getKeys().size());
		Assert.assertEquals(0, template.keySet().size());

		SoyMapData soyMapInjectedData = template.getSoyMapInjectedData();

		Assert.assertEquals(0, soyMapInjectedData.getKeys().size());
	}

	/**
	 * Tests if data injected with the Injected Data API is rendered.
	 */
	@Test
	public void testProcessTemplateWithInjectedData() throws Exception {
		SoyTemplate template = _soyTestHelper.getSoyTemplate("ijdata.soy");

		template.put("namespace", "soy.test.ijdata");

		Map<String, Object> injectedData = new HashMap<>();

		injectedData.put("hasData", true);

		template.put(SoyTemplateConstants.INJECTED_DATA, injectedData);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Injected Data: true", unsyncStringWriter.toString());
	}

	@Test
	public void testPut() {
		SoyTemplate template = _soyTestHelper.getSoyTemplate("ijdata.soy");

		SoyMapData soyMapData = template.getSoyMapData();

		template.put("key", "value");

		Assert.assertEquals("value", soyMapData.getString("key"));
	}

	@Test
	public void testPutRestrictedVariable() {
		SoyTemplate template = Mockito.spy(
			_soyTestHelper.getSoyTemplate("ijdata.soy"));

		TemplateContextHelper templateContextHelper = Mockito.mock(
			TemplateContextHelper.class);

		Mockito.when(
			template.getTemplateContextHelper()
		).thenReturn(
			templateContextHelper
		);

		Mockito.when(
			templateContextHelper.getRestrictedVariables()
		).thenReturn(
			Sets.newSet("restrictedKey")
		);

		template.put("restrictedKey", "restrictedValue");

		Mockito.verify(
			template, Mockito.times(0)).getSoyMapValue("restrictedValue");

		SoyMapData soyMapData = template.getSoyMapData();

		Assert.assertFalse("value", soyMapData.hasField("restrictedKey"));
	}

	@Test
	public void testPutWithSameValue() {
		SoyTemplate template = Mockito.spy(
			_soyTestHelper.getSoyTemplate("ijdata.soy"));

		String value = "value";

		template.put("key", value);
		template.put("key", value);

		Mockito.verify(template, Mockito.times(1)).getSoyMapValue(value);

		SoyMapData soyMapData = template.getSoyMapData();

		Assert.assertEquals("value", soyMapData.getString("key"));
	}

	@Test
	public void testRemove() {
		SoyTemplate template = _soyTestHelper.getSoyTemplate("ijdata.soy");

		template.put("key1", "value1");
		template.put("key2", "value2");

		template.putInjectedData("injectedKey", "injectedValue");

		template.remove("key2");

		SoyMapData soyMapData = template.getSoyMapData();

		Assert.assertTrue(soyMapData.hasField("key1"));
		Assert.assertEquals("value1", soyMapData.getString("key1"));

		SoyMapData soyMapInjectedData = template.getSoyMapInjectedData();

		Assert.assertEquals(
			"injectedValue", soyMapInjectedData.getString("injectedKey"));
	}

	@Test
	public void testRemoveInjectedData() {
		SoyTemplate template = _soyTestHelper.getSoyTemplate("ijdata.soy");

		template.putInjectedData("injectedKey", "injectedValue");

		template.remove(SoyTemplateConstants.INJECTED_DATA);

		SoyMapData soyMapInjectedData = template.getSoyMapInjectedData();

		Assert.assertEquals(0, soyMapInjectedData.getKeys().size());
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();

}