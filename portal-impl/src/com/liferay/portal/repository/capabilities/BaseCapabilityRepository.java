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