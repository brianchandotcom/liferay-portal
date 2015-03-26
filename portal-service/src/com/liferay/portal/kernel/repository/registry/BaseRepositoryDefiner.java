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

package com.liferay.portal.kernel.repository.registry;

import com.liferay.portal.kernel.repository.DocumentRepository;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRepositoryDefiner implements RepositoryDefiner {

	@Override
	public abstract String getClassName();

	@Override
	public String[] getSupportedConfigurations() {
		return _EMPTY_CONFIGURATIONS;
	}

	@Override
	public String[][] getSupportedParameters() {
		return _EMPTY_PARAMETERS;
	}

	@Override
	public abstract boolean isExternalRepository();

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {
	}

	@Override
	public abstract void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry);

	private static final String[] _EMPTY_CONFIGURATIONS = {};

	private static final String[][] _EMPTY_PARAMETERS = {};

}