/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class EmailAddressValidatorFactory {

	public static EmailAddressValidator getInstance() {
		return _emailAddressValidator;
	}

	private EmailAddressValidatorFactory() {
	}

	private static volatile EmailAddressValidator _emailAddressValidator =
		ServiceProxyFactory.newServiceTrackedInstance(
			EmailAddressValidator.class, EmailAddressValidatorFactory.class,
			"_emailAddressValidator", false, true);

}