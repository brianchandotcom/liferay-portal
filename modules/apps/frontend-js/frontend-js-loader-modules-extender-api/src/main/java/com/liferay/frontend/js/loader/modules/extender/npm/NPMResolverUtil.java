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

package com.liferay.frontend.js.loader.modules.extender.npm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class provides the same functionality as NPMResolver but without the
 * need to reference any OSGi service or component.
 *
 * @author Iván Zaera Avellón
 * @see NPMResolver
 * @review
 */
public class NPMResolverUtil {

	public static JSPackage getDependencyJSPackage(
		Bundle bundle, String packageName) {

		NPMResolver npmResolver = _getNPMResolver(bundle);

		return npmResolver.getDependencyJSPackage(packageName);
	}

	public static JSPackage getDependencyJSPackage(
		Class<?> clazz, String packageName) {

		return getDependencyJSPackage(
			FrameworkUtil.getBundle(clazz), packageName);
	}

	public static String resolveModuleName(Bundle bundle, String moduleName) {
		NPMResolver npmResolver = _getNPMResolver(bundle);

		return npmResolver.resolveModuleName(moduleName);
	}

	public static String resolveModuleName(Class<?> clazz, String moduleName) {
		return resolveModuleName(FrameworkUtil.getBundle(clazz), moduleName);
	}

	public static void set(Bundle bundle, NPMResolver npmResolver) {
		synchronized (_npmResolverMap) {
			if (npmResolver == null) {
				_npmResolverMap.remove(bundle);
			}
			else {
				_npmResolverMap.put(bundle, npmResolver);
			}
		}
	}

	public JSPackage getJSPackage(Bundle bundle) {
		NPMResolver npmResolver = _getNPMResolver(bundle);

		return npmResolver.getJSPackage();
	}

	public JSPackage getJSPackage(Class<?> clazz) {
		return getJSPackage(FrameworkUtil.getBundle(clazz));
	}

	private static NPMResolver _getNPMResolver(Bundle bundle) {
		NPMResolver npmResolver;

		synchronized (_npmResolverMap) {
			npmResolver = _npmResolverMap.get(bundle);
		}

		if (npmResolver == null) {
			throw new IllegalArgumentException(
				"Bundle " + bundle.getSymbolicName() +
					" does not have an associated NPMResolver");
		}

		return npmResolver;
	}

	private static final Map<Bundle, NPMResolver> _npmResolverMap =
		new ConcurrentHashMap<>();

}