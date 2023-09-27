/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public interface ScreenNameValidator {

	public String getAUIValidatorJS();

	public String getDescription(Locale locale);

	public boolean validate(long companyId, String screenName);

}