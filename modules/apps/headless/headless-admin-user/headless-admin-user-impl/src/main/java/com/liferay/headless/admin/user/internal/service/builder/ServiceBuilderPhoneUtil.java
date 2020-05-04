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

package com.liferay.headless.admin.user.internal.service.builder;

import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.portal.kernel.service.PhoneLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderPhoneUtil {

	public static com.liferay.portal.kernel.model.Phone toServiceBuilderPhone(
		Phone phone, String type) {

		String number = phone.getPhoneNumber();
		String extension = phone.getExtension();

		if (Validator.isNull(number) && Validator.isNull(extension)) {
			return null;
		}

		com.liferay.portal.kernel.model.Phone serviceBuilderPhone =
			PhoneLocalServiceUtil.createPhone(
				GetterUtil.getLong(phone.getId()));

		serviceBuilderPhone.setNumber(number);
		serviceBuilderPhone.setExtension(extension);
		serviceBuilderPhone.setTypeId(
			ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
				"other", phone.getPhoneType(), type));
		serviceBuilderPhone.setPrimary(
			GetterUtil.getBoolean(phone.getPrimary()));

		return serviceBuilderPhone;
	}

}