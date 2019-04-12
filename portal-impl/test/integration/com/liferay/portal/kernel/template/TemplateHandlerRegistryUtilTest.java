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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

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
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.templatehandlerregistryutil"));

	@Test
	public void testGetClassNameIds() {
		long classNameId = PortalUtil.getClassNameId(
			TestTemplateHandler.class.getName());

		Assert.assertTrue(
			ArrayUtil.contains(
				TemplateHandlerRegistryUtil.getClassNameIds(), classNameId));
	}

	@Test
	public void testGetTemplateHandlerByClassName() {
		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				TestTemplateHandler.class.getName());

		Assert.assertEquals(
			TestTemplateHandler.class.getName(),
			templateHandler.getClassName());
	}

	@Test
	public void testGetTemplateHandlerByClassNameId() {
		long classNameId = PortalUtil.getClassNameId(
			TestTemplateHandler.class.getName());

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

		Assert.assertEquals(
			TestTemplateHandler.class.getName(),
			templateHandler.getClassName());
	}

	@Test
	public void testGetTemplateHandlers() {
		boolean exists = false;

		List<TemplateHandler> templateHandlers =
			TemplateHandlerRegistryUtil.getTemplateHandlers();

		for (TemplateHandler templateHandler : templateHandlers) {
			String className = templateHandler.getClassName();

			if (className.equals(TestTemplateHandler.class.getName())) {
				exists = true;

				break;
			}
		}

		Assert.assertTrue(exists);
	}

}