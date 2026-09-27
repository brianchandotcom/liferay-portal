/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.headless.admin.fragment.dto.v1_0.Configuration;
import com.liferay.headless.admin.site.dto.v1_0.FragmentConfigurationFieldValue;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rubén Pulido
 */
public class ConfigurationUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	@TestInfo("LPD-103947")
	public void testToConfiguration() throws Exception {
		_testToConfigurationFieldTypeUnknownIllegalStateException();
	}

	private void _testToConfigurationFieldTypeUnknownIllegalStateException()
		throws Exception {

		Assert.assertThrows(
			IllegalStateException.class,
			() -> _toConfiguration(
				StringUtil.read(
					getClass(),
					"dependencies/configuration_field_type_unknown.json")));
	}

	private Configuration _toConfiguration(String configurationJSON)
		throws Exception {

		FragmentEntry fragmentEntry = Mockito.mock(FragmentEntry.class);

		Mockito.when(
			fragmentEntry.getConfiguration()
		).thenReturn(
			configurationJSON
		);

		return ConfigurationUtil.toConfiguration(
			_fragmentConfigurationFieldValueDTOConverter, fragmentEntry);
	}

	private static final DTOConverter
		<FragmentConfigurationField, FragmentConfigurationFieldValue>
			_fragmentConfigurationFieldValueDTOConverter = Mockito.mock(
				DTOConverter.class);

}