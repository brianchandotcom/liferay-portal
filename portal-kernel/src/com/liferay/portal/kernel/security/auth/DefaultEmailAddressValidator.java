/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Shinn Lok
 */
public class DefaultEmailAddressValidator implements EmailAddressValidator {

	@Override
	public boolean validate(long companyId, String emailAddress) {
		if (!Validator.isEmailAddress(emailAddress) ||
			emailAddress.startsWith("postmaster@") ||
			emailAddress.startsWith("root@")) {

			return false;
		}

		return true;
	}

}