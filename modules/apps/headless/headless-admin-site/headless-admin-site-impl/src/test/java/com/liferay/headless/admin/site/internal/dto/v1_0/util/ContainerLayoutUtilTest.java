/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.Layout;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class ContainerLayoutUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToLayout() {
		Assert.assertNull(ContainerLayoutUtil.toLayout(JSONUtil.put("", "")));
		Assert.assertNull(
			ContainerLayoutUtil.toLayout(JSONUtil.put("other", "value")));

		Layout layout = ContainerLayoutUtil.toLayout(
			JSONUtil.put("align", "align-items-center"));

		Assert.assertNotNull(layout);
		Assert.assertEquals(Layout.Align.CENTER, layout.getAlign());

		layout = ContainerLayoutUtil.toLayout(
			JSONUtil.put("contentDisplay", "flex-row"));

		Assert.assertNotNull(layout);
		Assert.assertEquals(
			Layout.ContentDisplay.FLEX_ROW, layout.getContentDisplay());

		layout = ContainerLayoutUtil.toLayout(
			JSONUtil.put("flexWrap", "flex-wrap"));

		Assert.assertNotNull(layout);
		Assert.assertEquals(Layout.FlexWrap.WRAP, layout.getFlexWrap());

		layout = ContainerLayoutUtil.toLayout(
			JSONUtil.put("justify", "justify-content-center"));

		Assert.assertNotNull(layout);
		Assert.assertEquals(Layout.Justify.CENTER, layout.getJustify());

		layout = ContainerLayoutUtil.toLayout(
			JSONUtil.put("widthType", "fixed"));

		Assert.assertNotNull(layout);
		Assert.assertEquals(Layout.WidthType.FIXED, layout.getWidthType());
	}

}