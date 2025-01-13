/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.taglib.servlet.taglib.LayoutCommonTag;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockBodyContent;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockJspWriter;
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

		layoutCommonTag.setPageContext(
			_getPageContext(
				_getMockHttpServletRequest(message), mockHttpServletResponse));

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

	private PageContext _getPageContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PrintWriter printWriter = httpServletResponse.getWriter();

		final JspWriter jspWriter = new MockJspWriter(printWriter);

		httpServletRequest.setAttribute(
			WebKeys.OUTPUT_DATA,
			new OutputData() {

				@Override
				public void addDataSB(
					String outputKey, String webKey, StringBundler sb) {

					try {
						jspWriter.write(sb.toString());
					}
					catch (IOException ioException) {
						ReflectionUtil.throwException(ioException);
					}
				}

			});

		return new MockPageContext() {

			@Override
			public JspWriter getOut() {
				return jspWriter;
			}

			@Override
			public ServletRequest getRequest() {
				return httpServletRequest;
			}

			@Override
			public ServletResponse getResponse() {
				return httpServletResponse;
			}

			@Override
			public BodyContent pushBody() {
				return new MockBodyContent(StringPool.BLANK, printWriter) {

					@Override
					public String getString() {
						return printWriter.toString();
					}

				};
			}

		};
	}

	@DeleteAfterTestRun
	private Group _group;

}