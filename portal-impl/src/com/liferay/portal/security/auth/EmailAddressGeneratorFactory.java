/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.EmailAddressGenerator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Amos Fong
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class EmailAddressGeneratorFactory {

	public static EmailAddressGenerator getInstance() {
		return _emailAddressGenerator;
	}

	private static volatile EmailAddressGenerator _emailAddressGenerator =
		ServiceProxyFactory.newServiceTrackedInstance(
			EmailAddressGenerator.class, EmailAddressGeneratorFactory.class,
			"_emailAddressGenerator", false, true);

}