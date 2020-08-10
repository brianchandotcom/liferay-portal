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

package com.liferay.commerce.theme.minium;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Marco Leo
 */
public class SiteInitializerDependencyResolverThreadLocal {

	public static SiteInitializerDependencyResolver
		getSiteInitializerDependencyResolver() {

		return _siteInitializerDependencyResolver.get();
	}

	public static void setSiteInitializerDependencyResolver(
		SiteInitializerDependencyResolver siteInitializerDependencyResolver) {

		_siteInitializerDependencyResolver.set(
			siteInitializerDependencyResolver);
	}

	private static final ThreadLocal<SiteInitializerDependencyResolver>
		_siteInitializerDependencyResolver = new CentralizedThreadLocal<>(
			SiteInitializerDependencyResolverThreadLocal.class.getName() +
				"._siteInitializerDependencyResolver");

}