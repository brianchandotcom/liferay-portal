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

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;

import java.net.URL;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bruno Basto
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SoyJavaScriptBuilderTest {

	@Before
	public void setUp() throws TemplateException {
		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() {
		Class<?> clazz = getClass();

		TemplateManagerUtil.destroy(clazz.getClassLoader());
	}

	@Test
	public void testBuildTemplateSimple() throws Exception {
		TemplateResource templateResource = getTemplateResource("simple.soy");

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, templateResource, false);

		template.put("namespace", "soy.test.simple");

		List<String> result = SoyJavaScriptBuilder.build((SoyTemplate)template);

		Assert.assertEquals(1, result.size());

		String parsedTemplate = result.get(0);

		Assert.assertNotNull(parsedTemplate);
	}

	@Test
	public void testBuildTemplateWithContext() throws Exception {
		TemplateResource templateResource = getTemplateResource("context.soy");

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY, templateResource, false);

		template.put("namespace", "soy.test.withContext");

		List<String> result = SoyJavaScriptBuilder.build((SoyTemplate)template);

		Assert.assertEquals(1, result.size());

		String parsedTemplate = result.get(0);

		Assert.assertNotNull(parsedTemplate);
	}

	protected TemplateResource getTemplateResource(String name) {
		TemplateResource templateResource = null;

		String resource = _TPL_PATH.concat(name);

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(resource);

		if (url != null) {
			templateResource = new URLTemplateResource(resource, url);
		}

		return templateResource;
	}

	private static final String _TPL_PATH =
		"com/liferay/portal/soy/dependencies/";

}