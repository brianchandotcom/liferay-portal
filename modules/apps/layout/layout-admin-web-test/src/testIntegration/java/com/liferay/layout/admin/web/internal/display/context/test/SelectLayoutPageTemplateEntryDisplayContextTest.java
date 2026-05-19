/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SelectLayoutPageTemplateEntryDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetTypes() throws Exception {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		List<String> types = ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				"com.liferay.layout.admin.web.internal.display.context." +
					"SelectLayoutPageTemplateEntryDisplayContext"),
			"getTypes", new Class<?>[0]);

		Assert.assertEquals(types.toString(), _TYPES.length, types.size());
		Assert.assertTrue(types.containsAll(Arrays.asList(_TYPES)));
	}

	private static final String[] _TYPES = {
		LayoutConstants.TYPE_EMBEDDED,
		LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
		LayoutConstants.TYPE_LINK_TO_LAYOUT, LayoutConstants.TYPE_NODE,
		LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
		LayoutConstants.TYPE_URL
	};

	@Inject(
		filter = "component.name=com.liferay.layout.admin.web.internal.portlet.action.SelectLayoutPageTemplateEntryMVCRenderCommand"
	)
	private MVCRenderCommand _mvcRenderCommand;

}