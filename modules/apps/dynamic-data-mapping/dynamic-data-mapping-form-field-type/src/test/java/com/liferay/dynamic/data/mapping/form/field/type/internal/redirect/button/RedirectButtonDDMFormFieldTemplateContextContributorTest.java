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

package com.liferay.dynamic.data.mapping.form.field.type.internal.redirect.button;

import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rodrigo Paulino
 */
public class RedirectButtonDDMFormFieldTemplateContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		PortletURLFactoryUtil portletURLFactoryUtil =
			new PortletURLFactoryUtil();

		PortletURLFactory portletURLFactory = Mockito.mock(
			PortletURLFactory.class);

		LiferayPortletURL mockLiferayPortletURL = new MockLiferayPortletURL();

		mockLiferayPortletURL.setPortletId(_PORTLET_ID);

		Mockito.doReturn(
			mockLiferayPortletURL
		).when(
			portletURLFactory
		).create(
			Matchers.any(PortletRequest.class), Mockito.anyString(),
			Mockito.anyString()
		);

		Mockito.doReturn(
			mockLiferayPortletURL
		).when(
			portletURLFactory
		).create(
			Matchers.any(HttpServletRequest.class), Mockito.anyString(),
			Mockito.anyLong(), Mockito.anyString()
		);

		portletURLFactoryUtil.setPortletURLFactory(portletURLFactory);
	}

	@Test
	public void testGetParametersWithMessageArgumentsAndParameters() {
		_mockLanguageUtilFormat(
			"message1", new Object[] {"messageArgument1", "messageArgument2"});

		Map<String, Object> parameters =
			_redirectButtonDDMFormFieldTemplateContextContributor.getParameters(
				DDMFormTestUtil.createRedirectButtonDDMFormField(
					new Object[] {
						DDMFormValuesTestUtil.createLocalizedValue(
							"buttonLabel", LocaleUtil.US)
					},
					new Object[] {"message1"},
					new Object[] {"messageArgument1", "messageArgument2"},
					new Object[] {"mvcRenderCommandName"},
					StringUtil.randomString(),
					new Object[] {"parameterName=parameterValue"},
					new Object[] {_PORTLET_ID},
					new Object[] {
						DDMFormValuesTestUtil.createLocalizedValue(
							"title", LocaleUtil.US)
					}),
				_mockDDMFormFieldRenderingContext());

		Assert.assertEquals("buttonLabel", parameters.get("buttonLabel"));
		Assert.assertEquals(
			"messageArgument1,messageArgument2,message1",
			parameters.get("message"));
		Assert.assertEquals(
			"http//localhost/test?portletId_mvcRenderCommandName=" +
				"mvcRenderCommandName;portletId_parameterName=parameterValue",
			parameters.get("redirectURL"));
		Assert.assertEquals("title", parameters.get("title"));
	}

	@Test
	public void testGetParametersWithoutMessageArgumentsAndParameters() {
		_mockLanguageUtilFormat("message2", new Object[0]);

		Map<String, Object> parameters =
			_redirectButtonDDMFormFieldTemplateContextContributor.getParameters(
				DDMFormTestUtil.createRedirectButtonDDMFormField(
					new Object[] {
						DDMFormValuesTestUtil.createLocalizedValue(
							"buttonLabel", LocaleUtil.US)
					},
					new Object[] {"message2"}, new Object[0],
					new Object[] {"mvcRenderCommandName"},
					StringUtil.randomString(), new Object[0],
					new Object[] {_PORTLET_ID},
					new Object[] {
						DDMFormValuesTestUtil.createLocalizedValue(
							"title", LocaleUtil.US)
					}),
				_mockDDMFormFieldRenderingContext());

		Assert.assertEquals("buttonLabel", parameters.get("buttonLabel"));
		Assert.assertEquals("message2", parameters.get("message"));
		Assert.assertEquals(
			"http//localhost/test?portletId_mvcRenderCommandName=" +
				"mvcRenderCommandName",
			parameters.get("redirectURL"));
		Assert.assertEquals("title", parameters.get("title"));
	}

	private DDMFormFieldRenderingContext _mockDDMFormFieldRenderingContext() {
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, Mockito.mock(ThemeDisplay.class));

		ddmFormFieldRenderingContext.setHttpServletRequest(httpServletRequest);

		ddmFormFieldRenderingContext.setLocale(LocaleUtil.US);

		return ddmFormFieldRenderingContext;
	}

	private void _mockLanguageUtilFormat(
		String message, Object[] messageArguments) {

		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.format(
				Matchers.any(Locale.class), Matchers.eq(message),
				Matchers.eq(messageArguments))
		).thenReturn(
			StringUtil.merge(
				ArrayUtil.append(messageArguments, message), StringPool.COMMA)
		);

		languageUtil.setLanguage(language);
	}

	private static final String _PORTLET_ID = "portletId";

	private final RedirectButtonDDMFormFieldTemplateContextContributor
		_redirectButtonDDMFormFieldTemplateContextContributor =
			new RedirectButtonDDMFormFieldTemplateContextContributor();

}