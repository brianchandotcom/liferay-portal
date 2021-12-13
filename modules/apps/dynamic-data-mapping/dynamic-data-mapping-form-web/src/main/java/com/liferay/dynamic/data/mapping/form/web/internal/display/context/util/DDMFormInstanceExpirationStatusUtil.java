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