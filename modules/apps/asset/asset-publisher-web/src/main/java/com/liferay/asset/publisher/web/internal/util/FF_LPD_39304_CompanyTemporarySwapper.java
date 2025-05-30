/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

/**
 * TODO Remove this class when removing feature flag LPD-39304.
 *
 * @author Attila Bakay
 */
public class FF_LPD_39304_CompanyTemporarySwapper {

	public static SafeCloseable setCompanyIdWithSafeCloseable(long companyId) {
		return CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId);
	}

}