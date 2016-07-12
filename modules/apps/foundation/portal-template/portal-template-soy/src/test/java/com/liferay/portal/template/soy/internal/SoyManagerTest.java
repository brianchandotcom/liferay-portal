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
import com.liferay.portal.kernel.template.TemplateException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		_soyManagerTestHelper.setUp();
	}

	@After
	public void tearDown() {
		_soyManagerTestHelper.tearDown();
	}

	@Test
	public void testProcessMultiTemplateAllResources() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate(
			Arrays.asList(
				"multi.soy", "simple.soy", "context.soy", "multi-context.soy"));

		template.put("namespace", "soy.multiTest.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProcessMultiTemplateEmptyList() throws Exception {
		List<String> list = Collections.emptyList();

		Template template = _soyManagerTestHelper.getTemplate(list);

		template.processTemplate(new UnsyncStringWriter());
	}

	@Test
	public void testProcessMultiTemplateSimple() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate(
			Arrays.asList("multi.soy", "simple.soy"));

		template.put("namespace", "soy.multiTest.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test
	public void testProcessMultiTemplateWithContext() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate(
			Arrays.asList("multi-context.soy", "context.soy"));

		template.put("name", "Bruno Basto");
		template.put("namespace", "soy.multiTest.withContext");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Hello. My name is Bruno Basto.", unsyncStringWriter.toString());
	}

	@Test(expected = TemplateException.class)
	public void testProcessMultiTemplateWithoutNamespace() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate(
			Collections.singletonList("simple.soy"));

		template.processTemplate(new UnsyncStringWriter());
	}

	@Test
	public void testProcessTemplateSimple() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("simple.soy");

		template.put("namespace", "soy.test.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals("Hello.", unsyncStringWriter.toString());
	}

	@Test
	public void testProcessTemplateWithContext() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("context.soy");

		template.put("name", "Bruno Basto");
		template.put("namespace", "soy.test.withContext");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		Assert.assertEquals(
			"Hello. My name is Bruno Basto.", unsyncStringWriter.toString());
	}

	@Test(expected = TemplateException.class)
	public void testProcessTemplateWithoutNamespace() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("simple.soy");

		template.processTemplate(new UnsyncStringWriter());
	}

	private final SoyManagerTestHelper _soyManagerTestHelper =
		new SoyManagerTestHelper();

}