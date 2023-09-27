/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.FullNameValidator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Amos Fong
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class FullNameValidatorFactory {

	public static FullNameValidator getInstance() {
		return _fullNameValidator;
	}

	private FullNameValidatorFactory() {
	}

	private static volatile FullNameValidator _fullNameValidator =
		ServiceProxyFactory.newServiceTrackedInstance(
			FullNameValidator.class, FullNameValidatorFactory.class,
			"_fullNameValidator", false, true);

}