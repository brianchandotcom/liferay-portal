/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.taglib.servlet.taglib.LayoutCommonTag;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class LayoutCommonTagTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-45658")
	public void testHtmlRenderingInSessionMessages() throws Exception {
		LayoutCommonTag layoutCommonTag = new LayoutCommonTag();

		layoutCommonTag.setDisplaySessionMessages(true);

		String message =
			"User's input with single quotes and <strong>tags</strong>";

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		layoutCommonTag.setPageContext(new MockPageContext(
			null, _getMockHttpServletRequest(message), mockHttpServletResponse));

		layoutCommonTag.doEndTag();

		String content = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(
			content, content.contains(HtmlUtil.escapeJS(message)));
	}

	private MockHttpServletRequest _getMockHttpServletRequest(String message)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLanguageId(_group.getDefaultLanguageId());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		themeDisplay.setRequest(mockHttpServletRequest);

		SessionMessages.add(
			mockHttpServletRequest, "test_requestProcessedWarning", message);

		return mockHttpServletRequest;
	}

	@DeleteAfterTestRun
	private Group _group;

}