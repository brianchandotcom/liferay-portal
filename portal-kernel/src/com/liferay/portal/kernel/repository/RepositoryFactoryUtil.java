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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryUtil {

	public static LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		return getRepositoryFactory().createLocalRepository(repositoryId);
	}

	public static Repository createRepository(long repositoryId)
		throws PortalException {

		return getRepositoryFactory().createRepository(repositoryId);
	}

	public static RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private static RepositoryFactory _repositoryFactory;

}