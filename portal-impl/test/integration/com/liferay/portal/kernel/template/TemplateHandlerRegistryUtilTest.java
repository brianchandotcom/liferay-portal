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

import com.liferay.portal.kernel.template.bundle.templatehandlerregistryutil.TestTemplateHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class TemplateHandlerRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.templatehandlerregistryutil"));

	@Test
	public void testGetClassNameIds() {
		long[] classNameIds = TemplateHandlerRegistryUtil.getClassNameIds();

		Class<TestTemplateHandler> clazz = TestTemplateHandler.class;

		String testTemplateHandlerClassName = clazz.getName();

		long portalUtilClassNameId = PortalUtil.getClassNameId(
			testTemplateHandlerClassName);

		boolean found = false;

		for (long classNameId : classNameIds) {
			if (portalUtilClassNameId == classNameId) {
				found = true;
			}
		}

		Assert.assertTrue(found);
	}

	@Test
	public void testGetTemplateHandlerByClassId() {
		Class<TestTemplateHandler> clazz = TestTemplateHandler.class;

		String testTemplateHandlerClassName = clazz.getName();

		long portalUtilClassNameId = PortalUtil.getClassNameId(
			testTemplateHandlerClassName);

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				portalUtilClassNameId);

		Assert.assertEquals(
			testTemplateHandlerClassName, templateHandler.getClassName());
	}

	@Test
	public void testGetTemplateHandlerByClassName() {
		Class<TestTemplateHandler> clazz = TestTemplateHandler.class;

		String testTemplateHandlerClassName = clazz.getName();

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				testTemplateHandlerClassName);

		Assert.assertEquals(
			testTemplateHandlerClassName, templateHandler.getClassName());
	}

	@Test
	public void testGetTemplateHandlers() {
		List<TemplateHandler> templateHandlers =
			TemplateHandlerRegistryUtil.getTemplateHandlers();

		Class<TestTemplateHandler> clazz = TestTemplateHandler.class;

		String testTemplateHandlerClassName = clazz.getName();

		boolean found = false;

		for (TemplateHandler templateHandler : templateHandlers) {
			if (testTemplateHandlerClassName.equals(templateHandler.
					getClassName())) {

				found = true;
			}
		}

		Assert.assertTrue(found);
	}

}