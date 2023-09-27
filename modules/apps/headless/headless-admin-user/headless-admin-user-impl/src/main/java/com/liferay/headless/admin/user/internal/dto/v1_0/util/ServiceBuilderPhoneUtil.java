/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

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
		serviceBuilderPhone.setListTypeId(
			ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
				"other", phone.getPhoneType(), type));
		serviceBuilderPhone.setPrimary(
			GetterUtil.getBoolean(phone.getPrimary()));

		return serviceBuilderPhone;
	}

}