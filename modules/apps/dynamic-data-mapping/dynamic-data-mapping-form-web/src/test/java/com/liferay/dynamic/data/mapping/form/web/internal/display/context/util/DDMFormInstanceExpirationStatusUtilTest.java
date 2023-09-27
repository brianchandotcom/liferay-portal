/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.DateFormatFactoryImpl;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormInstanceExpirationStatusUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpDateFormatFactory();
	}

	@Test
	public void testFormExpired() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettings("1987-09-22", false);

		Assert.assertTrue(
			DDMFormInstanceExpirationStatusUtil.isFormExpired(
				_mockDDMFormInstance(ddmFormInstanceSettings), null));
		Assert.assertTrue(
			DDMFormInstanceExpirationStatusUtil.isFormExpired(
				_mockDDMFormInstance(ddmFormInstanceSettings),
				TimeZone.getDefault()));
	}

	@Test
	public void testFormNotExpiredWithInexistentForm() throws Exception {
		Assert.assertFalse(
			DDMFormInstanceExpirationStatusUtil.isFormExpired(null, null));
	}

	@Test
	public void testFormNotExpiredWithNeverExpireSetting() throws Exception {
		DDMFormInstanceSettings ddmFormInstanceSettings =
			_mockDDMFormInstanceSettings(null, true);

		Assert.assertFalse(
			DDMFormInstanceExpirationStatusUtil.isFormExpired(
				_mockDDMFormInstance(ddmFormInstanceSettings), null));
	}

	private static void _setUpDateFormatFactory() {
		ReflectionTestUtil.setFieldValue(
			DateFormatFactoryUtil.class, "_fastDateFormatFactory",
			new DateFormatFactoryImpl());
	}

	private DDMFormInstance _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceSettings _mockDDMFormInstanceSettings(
		String expirationDate, boolean neverExpire) {

		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		if (expirationDate != null) {
			Mockito.when(
				ddmFormInstanceSettings.expirationDate()
			).thenReturn(
				expirationDate
			);
		}

		Mockito.when(
			ddmFormInstanceSettings.neverExpire()
		).thenReturn(
			neverExpire
		);

		return ddmFormInstanceSettings;
	}

}