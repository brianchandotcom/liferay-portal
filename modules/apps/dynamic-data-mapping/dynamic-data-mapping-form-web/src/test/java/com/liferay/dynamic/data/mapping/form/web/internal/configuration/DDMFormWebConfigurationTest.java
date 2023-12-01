/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class DDMFormWebConfigurationTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateDefaultDDMFormWebConfiguration() {
		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurableUtil.createConfigurable(
				DDMFormWebConfiguration.class, new HashMapDictionary<>());

		Assert.assertEquals(1, ddmFormWebConfiguration.autosaveInterval());
		Assert.assertEquals(
			"enabled-with-warning", ddmFormWebConfiguration.csvExport());
		Assert.assertEquals(
			"list", ddmFormWebConfiguration.defaultDisplayView());
		Assert.assertEquals(
			"doc, docx, jpeg, jpg, pdf, png, ppt, pptx, tiff, txt, xls, xlsx",
			ddmFormWebConfiguration.guestUploadFileExtensions());
		Assert.assertEquals(
			25, ddmFormWebConfiguration.guestUploadMaximumFileSize());
		Assert.assertEquals(
			5, ddmFormWebConfiguration.guestUploadMaximumSubmissions());
		Assert.assertEquals(
			5, ddmFormWebConfiguration.maximumRepetitionsForUploadFields());
	}

}