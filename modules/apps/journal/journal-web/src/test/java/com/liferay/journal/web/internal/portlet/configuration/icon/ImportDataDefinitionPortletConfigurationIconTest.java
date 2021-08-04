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

import com.liferay.journal.web.internal.configuration.FFImportDataDefinitionConfiguration;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcela Cunha
 */
@PrepareForTest({ResourceBundleUtil.class, LanguageUtil.class})
@RunWith(PowerMockRunner.class)
public class ImportDataDefinitionPortletConfigurationIconTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_importDataDefinitionPortletConfigurationIcon,
			"_ffImportDataDefinitionConfiguration",
			_ffImportDataDefinitionConfiguration);
	}

	@Test
	public void testGetJspPath() {
		Assert.assertEquals(
			"/configuration/icon/import_data_definition.jsp",
			_importDataDefinitionPortletConfigurationIcon.getJspPath());
	}

	@Test
	public void testIsShowWithFFImportStructureConfigurationDisabled() {
		mockFFImportDataDefinitionConfiguration(false);

		Assert.assertFalse(
			_importDataDefinitionPortletConfigurationIcon.isShow(
				_portletRequest));
	}

	@Test
	public void testIsShowWithFFImportStructureConfigurationEnabled() {
		mockFFImportDataDefinitionConfiguration(true);

		Assert.assertTrue(
			_importDataDefinitionPortletConfigurationIcon.isShow(
				_portletRequest));
	}

	protected void mockFFImportDataDefinitionConfiguration(boolean enabled) {
		Mockito.when(
			_ffImportDataDefinitionConfiguration.enabled()
		).thenReturn(
			enabled
		);
	}

	private final FFImportDataDefinitionConfiguration
		_ffImportDataDefinitionConfiguration = Mockito.mock(
			FFImportDataDefinitionConfiguration.class);
	private final ImportDataDefinitionPortletConfigurationIcon
		_importDataDefinitionPortletConfigurationIcon =
			new ImportDataDefinitionPortletConfigurationIcon();
	private final PortletRequest _portletRequest = Mockito.mock(
		PortletRequest.class);

}