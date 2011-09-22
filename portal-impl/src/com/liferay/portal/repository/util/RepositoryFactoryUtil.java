/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mika Koivisto
 */
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
		return _repositoryFactories.keySet().toArray(new String[] {});
	}

	public static void registerRepositoryFactory(
		String className, RepositoryFactory repositoryFactory) {

		_repositoryFactories.put(className, repositoryFactory);
	}

	public static void unregisterRepositoryFactory(String className) {
		_repositoryFactories.remove(className);
	}

	private static ConcurrentHashMap<String, RepositoryFactory>
		_repositoryFactories =
			new ConcurrentHashMap<String, RepositoryFactory>();

	static {
		for (String className : PropsValues.DL_REPOSITORY_IMPL) {
			RepositoryFactory repositoryFactory = new RepositoryFactoryImpl(
				className);

			_repositoryFactories.put(className, repositoryFactory);
		}
	}

}