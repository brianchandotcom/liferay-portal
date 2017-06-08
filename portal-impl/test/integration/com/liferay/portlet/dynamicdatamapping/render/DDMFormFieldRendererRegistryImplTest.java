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

package com.liferay.portlet.dynamicdatamapping.render;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.dynamicdatamapping.render.bundle.ddmformfieldrendererregistryimpl.TestDDMFormFieldRenderer;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class DDMFormFieldRendererRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.ddmformfieldrendererregistryimpl"));

	@Test
	public void testGetDDMFormFieldRenderer() {
		DDMFormFieldRendererRegistry ddmFormFieldRendererRegistry =
			DDMFormFieldRendererRegistryUtil.getDDMFormFieldRendererRegistry();

		DDMFormFieldRenderer ddmFormFieldRenderer =
			ddmFormFieldRendererRegistry.getDDMFormFieldRenderer(
				TestDDMFormFieldRenderer.SUPPORTED_DDM_FIELD_TYPE);

		Class<?> clazz = ddmFormFieldRenderer.getClass();

		Assert.assertEquals(TestDDMFormFieldRenderer.class.getName(), clazz.
			getName());
	}

}