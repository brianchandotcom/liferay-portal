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

package com.liferay.portal.kernel.resource.manager;

import com.liferay.portal.kernel.resource.ClassLoaderResourceRetriever;
import com.liferay.portal.kernel.resource.ResourceRetriever;

/**
 * @author Miguel Pastor
 */
public class ClassLoaderResourceManager implements ResourceManager {

	public ClassLoaderResourceManager(ClassLoader classLoader) {
		if (classLoader != null) {
			_classLoader = classLoader;
		}
		else {
			_classLoader = ClassLoaderResourceManager.class.getClassLoader();
		}
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	@Override
	public ResourceRetriever getResourceRetriever(String location) {
		return new ClassLoaderResourceRetriever(_classLoader, location);
	}

	private final ClassLoader _classLoader;

}