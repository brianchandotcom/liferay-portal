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

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.RepositoryException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Adolfo Pérez
 * @author Mika Koivisto
 */
public class ExternalRepositoryFactoryUtil {

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.portal.repository.registry.RepositoryDefinitionCatalogUtil#getExternalRepositoryClassNames(
	 *             )}
	 */
	@Deprecated
	public static String[] getExternalRepositoryClassNames() {
		Set<String> classNames = _externalRepositoryFactories.keySet();

		return classNames.toArray(new String[0]);
	}

	public static BaseRepository getInstance(String className)
		throws Exception {

		ExternalRepositoryFactory externalRepositoryFactory =
			_externalRepositoryFactories.get(className);

		BaseRepository baseRepository = null;

		if (externalRepositoryFactory != null) {
			baseRepository = externalRepositoryFactory.getInstance();
		}

		if (baseRepository != null) {
			return baseRepository;
		}

		throw new RepositoryException(
			"Repository with class name " + className + " is unavailable");
	}

	public static void registerExternalRepositoryFactory(
		String className, ExternalRepositoryFactory externalRepositoryFactory) {

		_externalRepositoryFactories.put(className, externalRepositoryFactory);
	}

	public static void unregisterExternalRepositoryFactory(String className) {
		_externalRepositoryFactories.remove(className);
	}

	private static final ConcurrentMap<String, ExternalRepositoryFactory>
		_externalRepositoryFactories = new ConcurrentHashMap<>();

}