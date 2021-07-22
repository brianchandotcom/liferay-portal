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

package com.liferay.journal.web.internal.portlet.configuration.icon;

import com.liferay.journal.web.internal.configuration.FFImportStructureConfiguration;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletResponse;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcela Cunha
 */
@PrepareForTest({ResourceBundleUtil.class, LanguageUtil.class})
@RunWith(PowerMockRunner.class)
public class ImportStructurePortletConfigurationIconTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_importStructurePortletConfigurationIcon,
			"_ffImportStructureConfiguration", _ffImportStructureConfiguration);

		setUpPortletRequest();
		setUpLanguageUtil();
		setUpResourceBundleUtil();
		setUpThemeDisplay();
	}

	@Test
	public void testGetMessage() {
		Assert.assertEquals(
			"Import Structure",
			_importStructurePortletConfigurationIcon.getMessage(
				_portletRequest));
	}

	@Test
	public void testGetUrl() {
		Assert.assertEquals(
			"javascript:;",
			_importStructurePortletConfigurationIcon.getURL(
				_portletRequest, new MockPortletResponse()));
	}

	@Test
	public void testIsShowWithFFImportStructureConfigurationDisabled() {
		mockFFImportStructureConfiguration(false);

		Assert.assertFalse(
			_importStructurePortletConfigurationIcon.isShow(_portletRequest));
	}

	@Test
	public void testIsShowWithFFImportStructureConfigurationEnabled() {
		mockFFImportStructureConfiguration(true);

		Assert.assertTrue(
			_importStructurePortletConfigurationIcon.isShow(_portletRequest));
	}

	protected void mockFFImportStructureConfiguration(boolean enabled) {
		Mockito.when(
			_ffImportStructureConfiguration.enabled()
		).thenReturn(
			enabled
		);
	}

	protected void setUpLanguageUtil() {
		PowerMockito.mockStatic(LanguageUtil.class);

		Mockito.when(
			LanguageUtil.get(
				Matchers.any(ResourceBundle.class),
				Matchers.eq("import-structure"))
		).thenReturn(
			"Import Structure"
		);
	}

	protected void setUpPortletRequest() {
		Mockito.when(
			_portletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);
	}

	protected void setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				Matchers.any(Locale.class), Matchers.any(Class.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	protected void setUpThemeDisplay() {
		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);
	}

	private final FFImportStructureConfiguration
		_ffImportStructureConfiguration = Mockito.mock(
			FFImportStructureConfiguration.class);
	private final ImportStructurePortletConfigurationIcon
		_importStructurePortletConfigurationIcon =
			new ImportStructurePortletConfigurationIcon();
	private final PortletRequest _portletRequest = Mockito.mock(
		PortletRequest.class);
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}