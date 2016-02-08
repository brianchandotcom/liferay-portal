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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		String className = getRepositoryClassName(repositoryId);

		RepositoryFactory repositoryFactory = getRepositoryFactory(className);

		return repositoryFactory.createLocalRepository(repositoryId);
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		String className = getRepositoryClassName(repositoryId);

		RepositoryFactory repositoryFactory = getRepositoryFactory(className);

		return repositoryFactory.createRepository(repositoryId);
	}

	protected String getRepositoryClassName(long repositoryId) {
		com.liferay.portal.kernel.model.Repository repository =
			_repositoryLocalService.fetchRepository(repositoryId);

		if (repository != null) {
			return repository.getClassName();
		}

		return LiferayRepository.class.getName();
	}

	protected RepositoryFactory getRepositoryFactory(String className) {
		RepositoryClassDefinition repositoryDefinition =
			_repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
				className);

		if (repositoryDefinition == null) {
			throw new UndeployedExternalRepositoryException(className);
		}

		return repositoryDefinition;
	}

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

}