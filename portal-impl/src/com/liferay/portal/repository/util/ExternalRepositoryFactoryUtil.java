/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.util;

import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.RepositoryException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Adolfo Pérez
 * @author Mika Koivisto
 */
public class ExternalRepositoryFactoryUtil {

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