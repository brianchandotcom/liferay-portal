/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.portlet.configuration.icon;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcela Cunha
 */
public class ImportDataDefinitionPortletConfigurationIconTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetJspPath() {
		Assert.assertEquals(
			"/configuration/icon/import_data_definition.jsp",
			_importDataDefinitionPortletConfigurationIcon.getJspPath());
	}

	@Test
	public void testIsShow() {
		Assert.assertTrue(
			_importDataDefinitionPortletConfigurationIcon.isShow(null));
	}

	private final ImportDataDefinitionPortletConfigurationIcon
		_importDataDefinitionPortletConfigurationIcon =
			new ImportDataDefinitionPortletConfigurationIcon();

}