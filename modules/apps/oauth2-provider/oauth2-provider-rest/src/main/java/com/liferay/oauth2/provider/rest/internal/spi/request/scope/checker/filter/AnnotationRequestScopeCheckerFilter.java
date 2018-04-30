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

package com.liferay.oauth2.provider.rest.internal.spi.request.scope.checker.filter;

import com.liferay.oauth2.provider.rest.spi.request.scope.checker.filter.RequestScopeCheckerFilter;
import com.liferay.oauth2.provider.scope.RequiresNoScope;
import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.Method;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Request;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 * @author Tomas Polesovsky
 */
@Component(property = "type=annotation")
public class AnnotationRequestScopeCheckerFilter
	implements RequestScopeCheckerFilter {

	@Override
	public boolean isAllowed(
		ScopeChecker scopeChecker, Request request, ResourceInfo resourceInfo) {

		Method resourceMethod = resourceInfo.getResourceMethod();

		RequiresNoScope requiresNoScope = resourceMethod.getAnnotation(
			RequiresNoScope.class);

		RequiresScope requiresScope = resourceMethod.getAnnotation(
			RequiresScope.class);

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

		if (requiresNoScope != null) {
			return true;
		}

		if (requiresScope != null) {
			if (requiresScope.allNeeded()) {
				return scopeChecker.checkAllScopes(requiresScope.value());
			}
			else {
				return scopeChecker.checkAnyScope(requiresScope.value());
			}
		}

		Class<?> resourceClass = resourceInfo.getResourceClass();

		requiresNoScope = resourceClass.getAnnotation(RequiresNoScope.class);

		requiresScope = resourceClass.getAnnotation(RequiresScope.class);

		if ((requiresNoScope != null) && (requiresScope != null)) {
			StringBundler sb = new StringBundler(4);

			sb.append("Class ");
			sb.append(resourceClass.getName());
			sb.append("has both @RequiresNoScope and @RequiresScope ");
			sb.append("annotations defined");

			throw new RuntimeException(sb.toString());
		}

		if (requiresNoScope != null) {
			return true;
		}

		if (requiresScope != null) {
			if (requiresScope.allNeeded()) {
				return scopeChecker.checkAllScopes(requiresScope.value());
			}
			else {
				return scopeChecker.checkAnyScope(requiresScope.value());
			}
		}

		requiresNoScope = AnnotationLocator.locate(
			resourceClass, RequiresNoScope.class);

		requiresScope = AnnotationLocator.locate(
			resourceClass, RequiresScope.class);

		if ((requiresNoScope != null) && (requiresScope != null)) {
			StringBundler sb = new StringBundler(3);

			sb.append("Class ");
			sb.append(resourceClass.getName());
			sb.append("inherits both @RequiresNoScope and @RequiresScope");

			throw new RuntimeException(sb.toString());
		}

		if (requiresNoScope != null) {
			return true;
		}

		if (requiresScope != null) {
			if (requiresScope.allNeeded()) {
				return scopeChecker.checkAllScopes(requiresScope.value());
			}
			else {
				return scopeChecker.checkAnyScope(requiresScope.value());
			}
		}

		return false;
	}

}