/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class WidgetTemplatesTemplateViewUsagesDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetDDMTemplateUsageNameWithNullLayout() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		Object displayContext = mockLiferayPortletRenderRequest.getAttribute(
			"com.liferay.template.web.internal.display.context." +
				"WidgetTemplatesTemplateViewUsagesDisplayContext");

		Assert.assertEquals(
			StringPool.DASH,
			ReflectionTestUtil.invoke(
				displayContext, "getDDMTemplateUsageName",
				new Class<?>[] {Layout.class}, (Object)null));
	}

	@Test
	public void testGetDDMTemplateUsageTypeWithNullLayout() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		Object displayContext = mockLiferayPortletRenderRequest.getAttribute(
			"com.liferay.template.web.internal.display.context." +
				"WidgetTemplatesTemplateViewUsagesDisplayContext");

		Assert.assertEquals(
			"embedded",
			ReflectionTestUtil.invoke(
				displayContext, "getDDMTemplateUsageType",
				new Class<?>[] {Layout.class}, (Object)null));
	}

	@Inject(filter = "mvc.command.name=/template/view_widget_templates_usages")
	private MVCRenderCommand _mvcRenderCommand;

}