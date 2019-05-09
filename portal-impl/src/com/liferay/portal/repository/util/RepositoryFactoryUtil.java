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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author     Mika Koivisto
 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
 *             ExternalRepositoryFactoryUtil}
 */
@Deprecated
public class RepositoryFactoryUtil {

	public static BaseRepository getInstance(String className)
		throws Exception {

		RepositoryFactory repositoryFactory = _repositoryFactories.get(
			className);

		BaseRepository baseRepository = null;

		if (repositoryFactory != null) {
			baseRepository = repositoryFactory.getInstance();
		}

		if (baseRepository != null) {
			return baseRepository;
		}

		throw new RepositoryException(
			"Repository with class name " + className + " is unavailable");
	}

	public static String[] getRepositoryClassNames() {
		Set<String> classNames = _repositoryFactories.keySet();

		return classNames.toArray(new String[0]);
	}

	public static void registerRepositoryFactory(
		String className, RepositoryFactory repositoryFactory) {

		_repositoryFactories.put(className, repositoryFactory);
	}

	public static void unregisterRepositoryFactory(String className) {
		_repositoryFactories.remove(className);
	}

	private static final ConcurrentMap<String, RepositoryFactory>
		_repositoryFactories =
			new ConcurrentHashMap<String, RepositoryFactory>() {
				{
					ClassLoader classLoader =
						PortalClassLoaderUtil.getClassLoader();

					for (String className : PropsValues.DL_REPOSITORY_IMPL) {
						RepositoryFactory repositoryFactory =
							new RepositoryFactoryImpl(className, classLoader);

						put(className, repositoryFactory);
					}
				}
			};

}