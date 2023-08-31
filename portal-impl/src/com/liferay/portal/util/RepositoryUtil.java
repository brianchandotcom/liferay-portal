/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class RepositoryUtil {

	public static RepositoryEventTrigger getFolderRepositoryEventTrigger(
			long folderId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getFolderLocalRepository(folderId);

		return getRepositoryEventTrigger(localRepository);
	}

	public static RepositoryEventTrigger getRepositoryEventTrigger(
			long repositoryId)
		throws PortalException {

		return getRepositoryEventTrigger(
			RepositoryProviderUtil.getLocalRepository(repositoryId));
	}

	public static boolean isExternalRepository(long repositoryId) {
		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			repositoryId);

		if (repository == null) {
			return false;
		}

		Collection<String> externalRepositoryClassNames =
			RepositoryClassDefinitionCatalogUtil.
				getExternalRepositoryClassNames(repository.getCompanyId());

		return externalRepositoryClassNames.contains(repository.getClassName());
	}

	protected static RepositoryEventTrigger getRepositoryEventTrigger(
		LocalRepository localRepository) {

		if (localRepository.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			return localRepository.getCapability(
				RepositoryEventTriggerCapability.class);
		}

		return _dummyRepositoryEventTrigger;
	}

	private static final RepositoryEventTrigger _dummyRepositoryEventTrigger =
		new RepositoryEventTrigger() {

			@Override
			public <S extends RepositoryEventType, T> void trigger(
				Class<S> repositoryEventTypeClass, Class<T> modelClass,
				T model) {
			}

		};

}