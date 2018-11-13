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

import com.liferay.portal.template.soy.constants.SoyTemplateConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class SoyContextImplTest {

	@Test
	public void testClear() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.put("key", "value");
		soyContextImpl.putInjectedData("injectedKey", "injectedValue");

		soyContextImpl.clear();

		Assert.assertEquals(0, soyContextImpl.size());
		Assert.assertEquals(null, soyContextImpl.get("key"));
		Assert.assertEquals(
			null, soyContextImpl.getInjectedData("injectedKey"));
	}

	@Test
	public void testClearInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.putInjectedData("injectedKey", "injectedValue");

		Assert.assertEquals(1, soyContextImpl.size());

		soyContextImpl.clearInjectedData();

		Assert.assertEquals(0, soyContextImpl.size());
		Assert.assertEquals(
			null, soyContextImpl.getInjectedData("injectedKey"));
	}

	@Test
	public void testCreateInjectedSoyTemplateRecord() {
		Set<String> restrictedVariables = new HashSet<>();

		restrictedVariables.add("restrictedKey");

		SoyContextImpl soyContextImpl = new SoyContextImpl(
			Collections.emptyMap(), restrictedVariables);

		soyContextImpl.putInjectedData("key", "value");
		soyContextImpl.putInjectedData("restrictedKey", "restrictedValue");

		SoyTemplateRecord soyTemplateRecord =
			soyContextImpl.createInjectedSoyTemplateRecord();

		Assert.assertEquals("value", soyTemplateRecord.get("key"));
		Assert.assertNull(soyTemplateRecord.get("restrictedKey"));
	}

	@Test
	public void testCreateInjectedSoyTemplateRecordWithNullValues() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.putInjectedData("key", null);

		SoyTemplateRecord soyTemplateRecord =
			soyContextImpl.createInjectedSoyTemplateRecord();

		Assert.assertEquals(null, soyTemplateRecord.get("key"));
	}

	@Test
	public void testCreateSoyTemplateRecord() {
		Set<String> restrictedVariables = new HashSet<>();

		restrictedVariables.add("restrictedKey");

		SoyContextImpl soyContextImpl = new SoyContextImpl(
			Collections.emptyMap(), restrictedVariables);

		soyContextImpl.put("key", "value");
		soyContextImpl.put("restrictedKey", "restrictedValue");

		SoyTemplateRecord soyTemplateRecord =
			soyContextImpl.createSoyTemplateRecord();

		Assert.assertEquals("value", soyTemplateRecord.get("key"));
		Assert.assertNull(soyTemplateRecord.get("restrictedKey"));
	}

	@Test
	public void testCreateSoyTemplateRecordWithNullValues() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.put("key", null);

		SoyTemplateRecord soyTemplateRecord =
			soyContextImpl.createInjectedSoyTemplateRecord();

		Assert.assertEquals(null, soyTemplateRecord.get("key"));
	}

	@Test
	public void testOverrideInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.putInjectedData("injectedKey", "injectedValue");

		Map<String, Object> injectedData = new HashMap<>();

		injectedData.put("injectedKey", "overrideValue");

		soyContextImpl.put(SoyTemplateConstants.INJECTED_DATA, injectedData);

		Assert.assertEquals(
			"overrideValue", soyContextImpl.getInjectedData("injectedKey"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPutAllThrowsWithInvalidInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		Map<String, Object> data = new HashMap<>();

		data.put(SoyTemplateConstants.INJECTED_DATA, new Object());

		soyContextImpl.putAll(data);
	}

	@Test
	public void testPutInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.putInjectedData("injectedKey", "injectedValue");

		Map<String, Object> injectedData =
			(Map<String, Object>)soyContextImpl.get(
				SoyTemplateConstants.INJECTED_DATA);

		Assert.assertEquals(injectedData.toString(), 1, injectedData.size());
		Assert.assertEquals("injectedValue", injectedData.get("injectedKey"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPutThrowsWithInvalidInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.put(SoyTemplateConstants.INJECTED_DATA, new Object());
	}

	@Test
	public void testRemoveInjectedData() {
		SoyContextImpl soyContextImpl = new SoyContextImpl();

		soyContextImpl.putInjectedData("injectedKey", "injectedValue");

		soyContextImpl.remove(SoyTemplateConstants.INJECTED_DATA);

		Assert.assertEquals(
			null, soyContextImpl.getInjectedData("injectedKey"));
	}

}