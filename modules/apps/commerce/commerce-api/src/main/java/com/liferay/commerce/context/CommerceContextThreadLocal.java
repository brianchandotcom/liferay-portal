/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.context;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyCentralizedThreadLocal;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceContextThreadLocal {

	public static CommerceContext get() {
		return _commerceContext.get();
	}

	public static void set(CommerceContext commerceContext) {
		_commerceContext.set(commerceContext);
	}

	public static SafeCloseable setCommerceContextWithSafeCloseable(
		CommerceContext commerceContext) {

		return _commerceContext.setWithSafeCloseable(commerceContext);
	}

	private static final CentralizedThreadLocal<CommerceContext>
		_commerceContext = new CompanyCentralizedThreadLocal<>(
			CommerceContextThreadLocal.class + "._commerceContext");

}