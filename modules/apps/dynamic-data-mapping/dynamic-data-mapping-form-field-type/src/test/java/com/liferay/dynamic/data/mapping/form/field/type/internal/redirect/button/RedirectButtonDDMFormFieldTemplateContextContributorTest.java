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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodrigo Paulino
 */
@PrepareForTest(
	{
		LanguageUtil.class, RequestBackedPortletURLFactoryUtil.class,
		ResourceBundleUtil.class
	}
)
@RunWith(PowerMockRunner.class)
public class RedirectButtonDDMFormFieldTemplateContextContributorTest {

	@Before
	public void setUp() throws Exception {
		_setUpRequestBackedPortletURLFactoryUtil();
		_setUpResourceBundleUtil();
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
			PowerMockito.mock(DDMFormFieldRenderingContext.class);

		PowerMockito.when(
			ddmFormFieldRenderingContext.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		return ddmFormFieldRenderingContext;
	}

	private void _mockLanguageUtilFormat(
		String message, Object[] messageArguments) {

		PowerMockito.mockStatic(LanguageUtil.class);

		PowerMockito.when(
			LanguageUtil.format(
				Matchers.any(ResourceBundle.class), Matchers.eq(message),
				Matchers.eq(messageArguments))
		).thenReturn(
			StringUtil.merge(
				ArrayUtil.append(messageArguments, message), StringPool.COMMA)
		);
	}

	private PortletURL _mockPortletURL() {
		MockLiferayPortletURL mockLiferayPortletURL =
			new MockLiferayPortletURL();

		mockLiferayPortletURL.setPortletId(_PORTLET_ID);

		return mockLiferayPortletURL;
	}

	private RequestBackedPortletURLFactory
		_mockRequestBackedPortletURLFactory() {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			PowerMockito.mock(RequestBackedPortletURLFactory.class);

		PowerMockito.when(
			requestBackedPortletURLFactory.createActionURL(Matchers.anyString())
		).thenReturn(
			_mockPortletURL()
		);

		return requestBackedPortletURLFactory;
	}

	private void _setUpRequestBackedPortletURLFactoryUtil() {
		PowerMockito.mockStatic(RequestBackedPortletURLFactoryUtil.class);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			_mockRequestBackedPortletURLFactory();

		PowerMockito.when(
			RequestBackedPortletURLFactoryUtil.create(
				Matchers.any(HttpServletRequest.class))
		).thenReturn(
			requestBackedPortletURLFactory
		);
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.any(Locale.class), Matchers.any(Class.class))
		).thenReturn(
			PowerMockito.mock(ResourceBundle.class)
		);
	}

	private static final String _PORTLET_ID = "portletId";

	private final RedirectButtonDDMFormFieldTemplateContextContributor
		_redirectButtonDDMFormFieldTemplateContextContributor =
			new RedirectButtonDDMFormFieldTemplateContextContributor();

}