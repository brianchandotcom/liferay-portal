/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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