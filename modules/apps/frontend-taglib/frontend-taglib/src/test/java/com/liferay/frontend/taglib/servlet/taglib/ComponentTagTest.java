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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolvedPackageNameUtil;
import com.liferay.frontend.js.module.launcher.JSModuleLauncher;
import com.liferay.frontend.taglib.internal.util.ServicesProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.io.StringWriter;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Cristina González
 */
public class ComponentTagTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PortalClassLoaderUtil.setClassLoader(PortalImpl.class.getClassLoader());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testDoEndTag() throws Exception {
		ServicesProvider servicesProvider = new ServicesProvider();

		servicesProvider.setJsModuleLauncher(
			Mockito.mock(JSModuleLauncher.class));

		ComponentTag componentTag = new ComponentTag();

		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		componentTag.setComponentId("componentId");
		componentTag.setContext(Collections.singletonMap("name", "value"));
		componentTag.setModule("module");
		componentTag.setPageContext(
			new MockPageContext(_getServletContext(), httpServletRequest));

		componentTag.doEndTag();

		Assert.assertEquals(
			_read("dependencies/component_tag.html"),
			_toString(
				(ScriptData)httpServletRequest.getAttribute(
					WebKeys.AUI_SCRIPT_DATA)));
	}

	private HttpServletRequest _getHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setNamespace("namespace");

		LiferayPortletConfig liferayPortletConfig = Mockito.mock(
			LiferayPortletConfig.class);

		Mockito.when(
			liferayPortletConfig.getPortletId()
		).thenReturn(
			"portletId"
		);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, liferayPortletConfig);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private ServletContext _getServletContext() {
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		servletContext.setAttribute(
			NPMResolvedPackageNameUtil.class.getName(),
			"NPMResolvedPackageNameUtil");

		return servletContext;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		return new String(Files.readAllBytes(Paths.get(url.toURI())));
	}

	private String _toString(ScriptData scriptData) throws Exception {
		StringWriter stringWriter = new StringWriter();

		scriptData.writeTo(stringWriter);

		StringBuffer stringBuffer = stringWriter.getBuffer();

		return stringBuffer.toString();
	}

}