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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;

import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodrigo Paulino
 */
@RunWith(PowerMockRunner.class)
public class DDMFormInstanceExpirationStatusUtilTest extends PowerMockito {

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

	private DDMFormInstance _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceSettings _mockDDMFormInstanceSettings(
		String expirationDate, boolean neverExpire) {

		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		if (expirationDate != null) {
			when(
				ddmFormInstanceSettings.expirationDate()
			).thenReturn(
				expirationDate
			);
		}

		when(
			ddmFormInstanceSettings.neverExpire()
		).thenReturn(
			neverExpire
		);

		return ddmFormInstanceSettings;
	}

}