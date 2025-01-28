/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Carolina Barbosa
 */
public class DDMFormUtil {

	public static User getDDMFormDefaultUser(long companyId)
		throws PortalException {

		return UserLocalServiceUtil.getUserByExternalReferenceCode(
			DDMFormConstants.DDM_FORM_DEFAULT_USER_EXTERNAL_REFERENCE_CODE,
			companyId);
	}

}