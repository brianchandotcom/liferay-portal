/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.headless.admin.user.dto.v1_0.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderEmailAddressUtil {

	public static com.liferay.portal.kernel.model.EmailAddress
		toServiceBuilderEmailAddress(EmailAddress emailAddress, String type) {

		String address = emailAddress.getEmailAddress();

		if (Validator.isNull(address)) {
			return null;
		}

		com.liferay.portal.kernel.model.EmailAddress
			serviceBuilderEmailAddress =
				EmailAddressLocalServiceUtil.createEmailAddress(
					GetterUtil.getLong(emailAddress.getId()));

		serviceBuilderEmailAddress.setAddress(address);
		serviceBuilderEmailAddress.setListTypeId(
			ServiceBuilderListTypeUtil.toServiceBuilderListTypeId(
				"email-address", emailAddress.getType(), type));
		serviceBuilderEmailAddress.setPrimary(
			GetterUtil.getBoolean(emailAddress.getPrimary()));

		return serviceBuilderEmailAddress;
	}

}