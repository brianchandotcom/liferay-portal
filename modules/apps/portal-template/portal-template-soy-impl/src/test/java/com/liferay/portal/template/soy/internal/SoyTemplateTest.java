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

		SoyTemplateRecord soyTemplateRecord =
			soyTemplate.getSoyTemplateRecord();

		Set<String> keys = soyTemplateRecord.keys();

		Assert.assertEquals(keys.toString(), 0, keys.size());

		keys = soyTemplate.keySet();

		Assert.assertEquals(keys.toString(), 0, keys.size());

		SoyTemplateRecord injectedSoyTemplateRecord =
			soyTemplate.getInjectedSoyTemplateRecord();

		keys = injectedSoyTemplateRecord.keys();

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

		SoyTemplateRecord soyTemplateRecord =
			soyTemplate.getSoyTemplateRecord();

		soyTemplate.put("key", "value");

		Assert.assertEquals("value", soyTemplateRecord.get("key"));
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

		SoyTemplateRecord soyTemplateRecord =
			soyTemplate.getSoyTemplateRecord();

		Assert.assertNull(
			"restrictedValue", soyTemplateRecord.get("restrictedKey"));
	}

	@Test
	public void testPutWithSameValue() {
		SoyTemplate soyTemplate = Mockito.spy(
			_soyTestHelper.getSoyTemplate("ijdata.soy"));

		String value = "value";

		soyTemplate.put("key", value);
		soyTemplate.put("key", value);

		SoyTemplateRecord soyTemplateRecord =
			soyTemplate.getSoyTemplateRecord();

		Assert.assertEquals("value", soyTemplateRecord.get("key"));
	}

	@Test
	public void testRemove() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.put("key1", "value1");
		soyTemplate.put("key2", "value2");

		soyTemplate.putInjectedData("injectedKey", "injectedValue");

		soyTemplate.remove("key2");

		SoyTemplateRecord soyTemplateRecord =
			soyTemplate.getSoyTemplateRecord();

		Assert.assertTrue(soyTemplateRecord.hasField("key1"));
		Assert.assertEquals("value1", soyTemplateRecord.get("key1"));

		SoyTemplateRecord injectedSoyTemplateRecord =
			soyTemplate.getInjectedSoyTemplateRecord();

		Assert.assertEquals(
			"injectedValue", injectedSoyTemplateRecord.get("injectedKey"));
	}

	@Test
	public void testRemoveInjectedData() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.putInjectedData("injectedKey", "injectedValue");

		soyTemplate.remove(SoyTemplateConstants.INJECTED_DATA);

		SoyTemplateRecord injectedSoyTemplateRecord =
			soyTemplate.getInjectedSoyTemplateRecord();

		Set<String> keys = injectedSoyTemplateRecord.keys();

		Assert.assertEquals(keys.toString(), 0, keys.size());
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();

}