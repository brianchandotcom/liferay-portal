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

package com.liferay.portal.soy;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
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
		_soyManagerTestHelper.setUp();
	}

	@After
	public void tearDown() {
		_soyManagerTestHelper.tearDown();
	}

	@Test
	public void testProcessTemplateSimple() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("simple.soy");

		template.put("namespace", "soy.test.simple");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("Hello World!", result);
	}

	@Test
	public void testProcessTemplateWithContext() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("context.soy");

		template.put("namespace", "soy.test.withContext");

		template.put("name", "Bruno Basto");

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("Hello! My name is Bruno Basto.", result);
	}

	@Test(expected = TemplateException.class)
	public void testProcessTemplateWithoutNamespace() throws Exception {
		Template template = _soyManagerTestHelper.getTemplate("simple.soy");

		template.processTemplate(new UnsyncStringWriter());
	}

	private final SoyManagerTestHelper _soyManagerTestHelper =
		new SoyManagerTestHelper();

}