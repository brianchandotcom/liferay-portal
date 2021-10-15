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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.DefaultWorkflowInstance;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Arrays;
import java.util.TimeZone;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Feliphe Marinho
 */
public class WorkflowInstanceViewDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpPortal();

		_setUpFastDateFormatFactory();
		_setUpLiferayPortletRequest();
		_setUpLiferayPortletResponse();
		_setUpPortletPreferences();
		_setUpThemeDisplay();
	}

	@Before
	public void setUp() throws PortalException {
		_workflowInstanceViewDisplayContext = Mockito.spy(
			new WorkflowInstanceViewDisplayContext(
				_liferayPortletRequest, _liferayPortletResponse));

		_language = Mockito.mock(Language.class);

		ReflectionTestUtil.setFieldValue(
			LanguageUtil.class, "_language", _language);
	}

	@Test
	public void testGetStatus() {
		DefaultWorkflowInstance defaultWorkflowInstance =
			new DefaultWorkflowInstance();

		defaultWorkflowInstance.setCurrentNodeNames(
			Arrays.asList("task1", "task2"));

		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class), Mockito.eq("task1"))
		).thenReturn(
			"task1"
		);

		Mockito.when(
			_language.get(
				Mockito.any(HttpServletRequest.class), Mockito.eq("task2"))
		).thenReturn(
			"task2"
		);

		Assert.assertEquals(
			StringUtil.merge(
				Arrays.asList("task1", "task2"), StringPool.COMMA_AND_SPACE),
			_workflowInstanceViewDisplayContext.getStatus(
				defaultWorkflowInstance));

		defaultWorkflowInstance.setCurrentNodeNames(Arrays.asList("task1"));

		Assert.assertEquals(
			"task1",
			_workflowInstanceViewDisplayContext.getStatus(
				defaultWorkflowInstance));
	}

	private static void _setUpFastDateFormatFactory() {
		ReflectionTestUtil.setFieldValue(
			FastDateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new FastDateFormatFactoryImpl());
	}

	private static void _setUpLiferayPortletRequest() {
		_liferayPortletRequest = Mockito.mock(LiferayPortletRequest.class);

		Mockito.when(
			_portal.getHttpServletRequest(_liferayPortletRequest)
		).thenReturn(
			new MockHttpServletRequest()
		);
	}

	private static void _setUpLiferayPortletResponse() {
		_liferayPortletResponse = Mockito.mock(LiferayPortletResponse.class);

		Mockito.when(
			_portal.getHttpServletResponse(_liferayPortletResponse)
		).thenReturn(
			new MockHttpServletResponse()
		);
	}

	private static void _setUpPortal() {
		_portal = Mockito.mock(Portal.class);

		Mockito.when(
			_portal.getPortalURL((PortletRequest)Mockito.any())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private static void _setUpPortletPreferences() {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		PortletPreferencesFactory portletPreferencesFactory = Mockito.mock(
			PortletPreferencesFactory.class);

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			portletPreferencesFactory);

		Mockito.when(
			portletPreferencesFactory.getPortalPreferences(
				Mockito.any(HttpServletRequest.class))
		).thenReturn(
			Mockito.mock(PortalPreferences.class)
		);
	}

	private static void _setUpThemeDisplay() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		Mockito.when(
			themeDisplay.getTimeZone()
		).thenReturn(
			TimeZone.getTimeZone("America/Recife")
		);
	}

	private static LiferayPortletRequest _liferayPortletRequest;
	private static LiferayPortletResponse _liferayPortletResponse;
	private static Portal _portal;

	private Language _language;
	private WorkflowInstanceViewDisplayContext
		_workflowInstanceViewDisplayContext;

}