/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.scope.logic;

import com.liferay.oauth2.provider.scope.ScopeChecker;

import java.lang.reflect.Method;

import java.util.function.Function;

/**
 * @author Carlos Correa
 * @author Stian Sigvartsen
 */
public interface ScopeLogic {

	public boolean check(
		Function<String, Object> propertyAccessorFunction,
		Class<?> resourceClass, Method resourceMethod,
		ScopeChecker scopeChecker);

}