/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.form.validation.util.DateParameterUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.portal.kernel.exception.PortalException;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.TimeZone;

/**
 * @author Rodrigo Paulino
 */
public class DDMFormInstanceExpirationStatusUtil {

	public static boolean isFormExpired(
			DDMFormInstance ddmFormInstance, TimeZone timeZone)
		throws PortalException {

		if (ddmFormInstance == null) {
			return false;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		if (ddmFormInstanceSettings.neverExpire()) {
			return false;
		}

		LocalDate localDate = DateParameterUtil.getLocalDate(
			ddmFormInstanceSettings.expirationDate());

		if (timeZone == null) {
			timeZone = TimeZone.getDefault();
		}

		return !localDate.isAfter(LocalDate.now(ZoneId.of(timeZone.getID())));
	}

}