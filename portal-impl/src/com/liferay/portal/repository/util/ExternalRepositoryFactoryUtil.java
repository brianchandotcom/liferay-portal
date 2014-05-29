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

/**
 * @author Adolfo Pérez
 * @author Mika Koivisto
 */
public class ExternalRepositoryFactoryUtil {

	public static String[] getExternalRepositoryClassNames() {
		Set<String> classNames = externalRepositoryFactories.keySet();

		return classNames.toArray(new String[classNames.size()]);
	}

	public static BaseRepository getInstance(String className)
		throws Exception {

		ExternalRepositoryFactory externalRepositoryFactory =
			externalRepositoryFactories.get(className);

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

		externalRepositoryFactories.put(className, externalRepositoryFactory);
	}

	public static void unregisterExternalRepositoryFactory(String className) {
		externalRepositoryFactories.remove(className);
	}

	private static ConcurrentHashMap<String, ExternalRepositoryFactory>
		externalRepositoryFactories =
			new ConcurrentHashMap<String, ExternalRepositoryFactory>();

	static {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			ExternalRepositoryFactory externalRepositoryFactory =
				new ExternalRepositoryFactoryImpl(className, classLoader);

			externalRepositoryFactories.put(
				className, externalRepositoryFactory);
		}
	}

}