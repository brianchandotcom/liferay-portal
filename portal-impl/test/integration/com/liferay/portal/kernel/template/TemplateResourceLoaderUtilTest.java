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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.template.bundle.templateresourceloaderutil.TestTemplateResource;
import com.liferay.portal.kernel.template.bundle.templateresourceloaderutil.TestTemplateResourceLoader;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class TemplateResourceLoaderUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.templateresourceloaderutil"));

	@Test
	public void testGetTemplateResource() throws TemplateException {
		TemplateResource templateResource =
			TemplateResourceLoaderUtil.getTemplateResource(
				TestTemplateResourceLoader.TEST_TEMPLATE_RESOURCE_LOADER_NAME,
				TestTemplateResource.TEST_TEMPLATE_RESOURCE_TEMPLATE_ID);

		Class<?> clazz = templateResource.getClass();

		Assert.assertEquals(
			TestTemplateResource.class.getName(), clazz.getName());
	}

	@Test
	public void testGetTemplateResourceLoader() throws TemplateException {
		TemplateResourceLoader templateResourceLoader =
			TemplateResourceLoaderUtil.getTemplateResourceLoader(
				TestTemplateResourceLoader.TEST_TEMPLATE_RESOURCE_LOADER_NAME);

		Class<?> clazz = templateResourceLoader.getClass();

		Assert.assertEquals(
			TestTemplateResourceLoader.class.getName(), clazz.getName());
	}

	@Test
	public void testGetTemplateResourceLoaderNames() {
		Set<String> templateResourceLoaderNames =
			TemplateResourceLoaderUtil.getTemplateResourceLoaderNames();

		Assert.assertTrue(
			templateResourceLoaderNames.toString(),
			templateResourceLoaderNames.contains(
				TestTemplateResourceLoader.TEST_TEMPLATE_RESOURCE_LOADER_NAME));
	}

	@Test
	public void testHasTemplateResource() throws TemplateException {
		Assert.assertTrue(
			TemplateResourceLoaderUtil.hasTemplateResource(
				TestTemplateResourceLoader.TEST_TEMPLATE_RESOURCE_LOADER_NAME,
				TestTemplateResource.TEST_TEMPLATE_RESOURCE_TEMPLATE_ID));
	}

	@Test
	public void testHasTemplateResourceLoader() {
		Assert.assertTrue(
			TemplateResourceLoaderUtil.hasTemplateResourceLoader(
				TestTemplateResourceLoader.TEST_TEMPLATE_RESOURCE_LOADER_NAME));
	}

}