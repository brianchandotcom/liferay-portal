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
				getExternalRepositoryClassNames();

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