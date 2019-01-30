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
import com.liferay.portal.template.soy.constants.SoyTemplateConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

		soyTemplate.clear();

		Set<String> keys = soyTemplate.keySet();

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

		soyTemplate.put("key", "value");

		Assert.assertEquals("value", soyTemplate.get("key"));
	}

	@Test
	public void testPutWithSameValue() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		String value = "value";

		soyTemplate.put("key", value);
		soyTemplate.put("key", value);

		Assert.assertEquals("value", soyTemplate.get("key"));
	}

	@Test
	public void testRemove() {
		SoyTemplate soyTemplate = _soyTestHelper.getSoyTemplate("ijdata.soy");

		soyTemplate.put("key1", "value1");
		soyTemplate.put("key2", "value2");

		soyTemplate.remove("key2");

		Assert.assertTrue(soyTemplate.containsKey("key1"));
		Assert.assertEquals("value1", soyTemplate.get("key1"));

		Assert.assertFalse(soyTemplate.containsKey("key2"));
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();

}