/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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