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
import com.liferay.portal.kernel.repository.capabilities.BaseCapabilityProvider;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.util.ClassUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityRepository<T> extends BaseCapabilityProvider
	implements DocumentRepository, CapabilityRegistry {

	public BaseCapabilityRepository(T repository) {
		_repository = repository;
	}

	@Override
	public <S extends Capability, T extends S> void addExportedCapability(
		Class<S> capabilityClass, T capability) {

		super.addExportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability, T extends S> void addSupportedCapability(
		Class<S> capabilityClass, T capability) {

		super.addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public DocumentRepository getDocumentRepository() {
		return this;
	}

	public T getRepository() {
		return _repository;
	}

	public abstract long getRepositoryId();

	@Override
	protected String getProviderKey() {
		return String.format(
			"%s:%s", ClassUtil.getClassName(getRepository()),
			getRepositoryId());
	}

	private T _repository;

}