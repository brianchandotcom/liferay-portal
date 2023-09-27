/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityRepository<R>
	implements DocumentRepository {

	public BaseCapabilityRepository(
		R repository, CapabilityProvider capabilityProvider) {

		_repository = repository;
		_capabilityProvider = capabilityProvider;
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		return _capabilityProvider.getCapability(capabilityClass);
	}

	@Override
	public abstract long getRepositoryId();

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _capabilityProvider.isCapabilityProvided(capabilityClass);
	}

	protected R getRepository() {
		return _repository;
	}

	private final CapabilityProvider _capabilityProvider;
	private final R _repository;

}