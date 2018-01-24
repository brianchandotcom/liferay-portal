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
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bruno Basto
 */
public class SoyManagerTest {

	@Before
	public void setUp() throws Exception {
		_soyTestHelper.setUp();
	}

	@After
	public void tearDown() {
		_soyTestHelper.tearDown();
	}

	@Test
	public void testGetName() {
		SoyManager soyManager = _soyTestHelper.getSoyManager();

		Assert.assertEquals(
			TemplateConstants.LANG_TYPE_SOY, soyManager.getName());
	}

	@Test
	public void testProcessMultiTemplateAllResources() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate(
			"multi.soy", "simple.soy", "context.soy", "multi-context.soy");

		template.put("namespace", "soy.multiTest.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProcessMultiTemplateEmptyList() throws Exception {
		_soyTestHelper.getSoyTemplate();
	}

	@Test
	public void testProcessMultiTemplateSimple() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate(
			"multi.soy", "simple.soy");

		template.put("namespace", "soy.multiTest.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test
	public void testProcessMultiTemplateWithContext() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate(
			"multi-context.soy", "context.soy");

		template.put("name", "Bruno Basto");
		template.put("namespace", "soy.multiTest.withContext");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Hello. My name is Bruno Basto.", unsyncStringWriter.toString());
	}

	@Test(expected = TemplateException.class)
	public void testProcessMultiTemplateWithoutNamespace() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate("simple.soy");

		template.processTemplate(new UnsyncStringWriter());
	}

	@Test
	public void testProcessTemplateSimple() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate("simple.soy");

		template.put("namespace", "soy.test.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test
	public void testProcessTemplateWithContext() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate("context.soy");

		template.put("name", "Bruno Basto");
		template.put("namespace", "soy.test.withContext");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Hello. My name is Bruno Basto.", unsyncStringWriter.toString());
	}

	@Test(expected = TemplateException.class)
	public void testProcessTemplateWithoutNamespace() throws Exception {
		Template template = _soyTestHelper.getSoyTemplate("simple.soy");

		template.processTemplate(new UnsyncStringWriter());
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();

}