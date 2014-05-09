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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class PingbackServiceContextFunctionTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpLanguage();
		setUpPortal();
		setUpPortlet();
	}

	@Test
	public void testBuildServiceContext() {
		long companyId = ServiceTestUtil.randomLong();

		long groupId = ServiceTestUtil.randomLong();

		PingbackServiceContextFunction function =
			new PingbackServiceContextFunction(
				companyId, groupId, "__UrlTitle__");

		ServiceContext serviceContext = function.apply(
			BlogsEntry.class.getName());

		Assert.assertEquals(
			"__pingbackUserName__",
			serviceContext.getAttribute("pingbackUserName"));

		Assert.assertEquals(
			"__LayoutFullURL__/-/__FriendlyURLMapping__/__UrlTitle__",
			serviceContext.getAttribute("redirect"));

		Assert.assertEquals(
			"__LayoutFullURL__", serviceContext.getLayoutFullURL());
	}

	protected void setUpLanguage() {
		whenLanguageGetThenReturn("pingback", "__pingbackUserName__");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpPortal() throws Exception {
		Portal portal = Mockito.mock(Portal.class);

		when(
			portal.getLayoutFullURL(
				Matchers.anyLong(), Matchers.eq(PortletKeys.BLOGS))
		).thenReturn(
			"__LayoutFullURL__"
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	protected void setUpPortlet() throws Exception {
		Portlet portlet = Mockito.mock(Portlet.class);

		when(
			portlet.getFriendlyURLMapping()
		).thenReturn(
			"__FriendlyURLMapping__"
		);

		PortletLocalService portletLocalService = Mockito.mock(
			PortletLocalService.class);

		when(
			portletLocalService.getPortletById(
				Matchers.anyLong(), Matchers.eq(PortletKeys.BLOGS))
		).thenReturn(
			portlet
		);

		mockStatic(PortletLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(PortletLocalServiceUtil.class, "getService")
		).toReturn(
			portletLocalService
		);
	}

	protected void whenLanguageGetThenReturn(String key, String toBeReturned) {
		when(
			_language.get((Locale)Matchers.any(), Matchers.eq(key))
		).thenReturn(
			toBeReturned
		);
	}

	@Mock
	private Language _language;

}