/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.portlet.action;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mateus Xavier
 */
public class RenderStructureFieldMVCResourceCommandTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateDDMFormFieldRenderingContext() {
		String maliciousScript = "'\"></option><img onerror=alert(123) src=x>";
		HttpServletRequest mockHttpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			mockHttpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			mockHttpServletRequest.getParameter("namespace")
		).thenReturn(
			maliciousScript
		);

		Mockito.when(
			mockHttpServletRequest.getParameter("portletNamespace")
		).thenReturn(
			maliciousScript
		);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		RenderStructureFieldMVCResourceCommand
			renderStructureFieldMVCResourceCommand =
				new RenderStructureFieldMVCResourceCommand();

		ReflectionTestUtil.setFieldValue(
			renderStructureFieldMVCResourceCommand, "_portal", _portal);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			renderStructureFieldMVCResourceCommand.
				createDDMFormFieldRenderingContext(
					mockHttpServletRequest,
					Mockito.mock(HttpServletResponse.class));

		Assert.assertEquals(
			HtmlUtil.escapeAttribute(maliciousScript),
			ddmFormFieldRenderingContext.getNamespace());
		Assert.assertEquals(
			HtmlUtil.escapeAttribute(maliciousScript),
			ddmFormFieldRenderingContext.getPortletNamespace());
	}

	private final Portal _portal = Mockito.mock(Portal.class);

}