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

package com.liferay.oauth2.provider.rest.internal.scope.logic;

import com.liferay.oauth2.provider.rest.internal.jaxrs.feature.RequiresScopeAnnotationFinder;
import com.liferay.oauth2.provider.scope.RequiresNoScope;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.Method;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 * @author Stian Sigvartsen
 */
@Component(
	property = "oauth2.scope.checker.type=annotations",
	service = ScopeLogic.class
)
public class AnnotationScopeLogic implements ScopeLogic {

	@Override
	public boolean check(
		Function<String, Object> propertyAccessorFunction,
		Class<?> resourceClass, Method resourceMethod,
		ScopeChecker scopeChecker) {

		RequiresNoScope requiresNoScope =
			RequiresScopeAnnotationFinder.getScopeAnnotation(
				resourceMethod, RequiresNoScope.class);
		RequiresScope requiresScope =
			RequiresScopeAnnotationFinder.getScopeAnnotation(
				resourceMethod, RequiresScope.class);

		if ((requiresNoScope != null) && (requiresScope != null)) {
			StringBundler sb = new StringBundler(6);

			Class<?> declaringClass = resourceMethod.getDeclaringClass();

			sb.append("Method ");
			sb.append(declaringClass.getName());
			sb.append(StringPool.POUND);
			sb.append(resourceMethod.getName());
			sb.append("has both @RequiresNoScope and @RequiresScope ");
			sb.append("annotations defined");

			throw new RuntimeException(sb.toString());
		}

		if ((requiresNoScope != null) ||
			_checkRequiresScope(scopeChecker, requiresScope)) {

			return true;
		}

		requiresNoScope = RequiresScopeAnnotationFinder.getScopeAnnotation(
			resourceClass, RequiresNoScope.class);
		requiresScope = RequiresScopeAnnotationFinder.getScopeAnnotation(
			resourceClass, RequiresScope.class);

		if ((requiresNoScope != null) && (requiresScope != null)) {
			throw new RuntimeException(
				StringBundler.concat(
					"Class ", resourceClass.getName(),
					"has both @RequiresNoScope and @RequiresScope annotations ",
					"defined"));
		}

		if ((requiresNoScope != null) ||
			_checkRequiresScope(scopeChecker, requiresScope)) {

			return true;
		}

		return false;
	}

	private boolean _checkRequiresScope(
		ScopeChecker scopeChecker, RequiresScope requiresScope) {

		if (requiresScope != null) {
			if (requiresScope.allNeeded()) {
				return scopeChecker.checkAllScopes(requiresScope.value());
			}

			return scopeChecker.checkAnyScope(requiresScope.value());
		}

		return false;
	}

}