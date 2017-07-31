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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
public class PortletURLImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testToStringShouldNotReplicateExistingParamValues()
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Layout layout = LayoutTestUtil.addLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setPlid(layout.getPlid());

		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		long plid = themeDisplay.getPlid();

		Map<String, String[]> renderParameters = new HashMap<>();

		String[] values = {"value1", "value2"};

		renderParameters.put("name", values);

		MockHttpServletRequest mockServletRequest =
			new MockHttpServletRequest();

		mockServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		RenderParametersPool.put(
			mockServletRequest, plid, PortletKeys.LOGIN, renderParameters);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			mockServletRequest, PortletKeys.LOGIN, plid,
			PortletRequest.RENDER_PHASE);

		PortletURLImpl portletURLImpl = (PortletURLImpl)portletURL;

		portletURLImpl.setCopyCurrentRenderParameters(true);

		StringBuilder sb = new StringBuilder(10);

		sb.append("http://localhost:8080/web");
		sb.append(_group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());
		sb.append("?p_p_id=");
		sb.append(PortletKeys.LOGIN);
		sb.append("&p_p_lifecycle=0&_");
		sb.append(PortletKeys.LOGIN);
		sb.append("_name=value1&_");
		sb.append(PortletKeys.LOGIN);
		sb.append("_name=value2");

		Assert.assertEquals(sb.toString(), portletURL.toString());

		portletURLImpl.clearCache();

		Assert.assertEquals(sb.toString(), portletURL.toString());

		portletURLImpl.clearCache();

		Assert.assertEquals(sb.toString(), portletURL.toString());
	}

	@DeleteAfterTestRun
	private Group _group;

}