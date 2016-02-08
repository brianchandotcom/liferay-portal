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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.RepositoryServiceUtil;

/**
 * @author Iván Zaera
 */
public class RepositoryServiceAdapter {

	public static RepositoryServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new RepositoryServiceAdapter(
				RepositoryLocalServiceUtil.getService());
		}

		return new RepositoryServiceAdapter(
			RepositoryLocalServiceUtil.getService(),
			RepositoryServiceUtil.getService());
	}

	public RepositoryServiceAdapter(
		RepositoryLocalService repositoryLocalService) {

		this(repositoryLocalService, null);
	}

	public RepositoryServiceAdapter(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService) {

		_repositoryLocalService = repositoryLocalService;
		_repositoryService = repositoryService;
	}

	public Repository fetchRepository(long repositoryId)
		throws PortalException {

		Repository repository = null;

		if (_repositoryService != null) {
			repository = _repositoryLocalService.fetchRepository(repositoryId);

			if (repository != null) {
				repository = _repositoryService.getRepository(repositoryId);
			}
		}
		else {
			repository = _repositoryLocalService.fetchRepository(repositoryId);
		}

		return repository;
	}

	public Repository getRepository(long repositoryId) throws PortalException {
		Repository repository = null;

		if (_repositoryService != null) {
			repository = _repositoryService.getRepository(repositoryId);
		}
		else {
			repository = _repositoryLocalService.getRepository(repositoryId);
		}

		return repository;
	}

	public Repository updateRepository(Repository repository)
		throws PrincipalException {

		if (_repositoryService != null) {
			throw new PrincipalException("Repository service is not null");
		}

		return _repositoryLocalService.updateRepository(repository);
	}

	private final RepositoryLocalService _repositoryLocalService;
	private final RepositoryService _repositoryService;

}