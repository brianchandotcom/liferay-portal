/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.context;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

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

	public static SafeCloseable setWithSafeCloseable(
		CommerceContext commerceContext) {

		return _commerceContext.setWithSafeCloseable(commerceContext);
	}

	private static final CentralizedThreadLocal<CommerceContext>
		_commerceContext = new CentralizedThreadLocal<>(
			CommerceContextThreadLocal.class + "._commerceContext");

}