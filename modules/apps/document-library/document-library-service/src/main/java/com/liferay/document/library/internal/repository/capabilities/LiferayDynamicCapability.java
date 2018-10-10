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

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.DynamicCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alejandro Tardín
 */
public class LiferayDynamicCapability
	implements DynamicCapability, RepositoryEventAware, RepositoryWrapperAware {

	public void addCapability(Capability capability) {
		_dynamicCapabilities.add(capability);

		_updateRepositoryWrappers();
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {
	}

	public void removeCapability(Capability capability) {
		_dynamicCapabilities.remove(capability);
		_updateRepositoryWrappers();
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		LocalRepository wrappedLocalRepository = localRepository;

		for (Capability capability : _dynamicCapabilities) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedLocalRepository =
					repositoryWrapperAware.wrapLocalRepository(
						wrappedLocalRepository);
			}
		}

		LocalRepositoryWrapper localRepositoryWrapper =
			new LocalRepositoryWrapper(wrappedLocalRepository);

		_localRepositoriesMap.put(localRepositoryWrapper, localRepository);

		return localRepositoryWrapper;
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		Repository wrappedRepository = repository;

		for (Capability capability : _dynamicCapabilities) {
			if (capability instanceof RepositoryWrapperAware) {
				RepositoryWrapperAware repositoryWrapperAware =
					(RepositoryWrapperAware)capability;

				wrappedRepository = repositoryWrapperAware.wrapRepository(
					wrappedRepository);
			}
		}

		RepositoryWrapper repositoryWrapper = new RepositoryWrapper(
			wrappedRepository);

		_repositoriesMap.put(repositoryWrapper, repository);

		return repositoryWrapper;
	}

	private void _updateRepositoryWrappers() {
		for (Map.Entry<LocalRepositoryWrapper, LocalRepository>
				localRepositoryEntry : _localRepositoriesMap.entrySet()) {

			LocalRepository originalLocalRepository =
				localRepositoryEntry.getValue();

			LocalRepositoryWrapper localRepositoryWrapper =
				localRepositoryEntry.getKey();

			localRepositoryWrapper.setLocalRepository(
				wrapLocalRepository(originalLocalRepository));
		}

		for (Map.Entry<RepositoryWrapper, Repository> repositoryWrapperEntry :
				_repositoriesMap.entrySet()) {

			Repository originalRepository = repositoryWrapperEntry.getValue();
			RepositoryWrapper repositoryWrapper =
				repositoryWrapperEntry.getKey();

			repositoryWrapper.setRepository(wrapRepository(originalRepository));
		}
	}

	private final Set<Capability> _dynamicCapabilities = new HashSet<>();
	private final Map<LocalRepositoryWrapper, LocalRepository>
		_localRepositoriesMap = new HashMap<>();
	private final Map<RepositoryWrapper, Repository> _repositoriesMap =
		new HashMap<>();

}