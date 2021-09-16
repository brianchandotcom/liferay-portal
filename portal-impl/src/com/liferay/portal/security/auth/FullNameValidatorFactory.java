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