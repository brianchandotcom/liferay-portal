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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryCreatorRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;

/**
 * @author Adolfo Pérez
 */
public class LiferayRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getClassName() {
		return LiferayRepository.class.getName();
	}

	@Override
	public boolean isExternalRepository() {
		return false;
	}

	@Override
	public void registerCapabilities(CapabilityRegistry capabilityRegistry) {
		capabilityRegistry.addExportedCapability(
			TrashCapability.class, _liferayTrashCapability);
	}

	@Override
	public void registerRepositoryCreator(
		RepositoryCreatorRegistry repositoryCreatorRegistry) {

		repositoryCreatorRegistry.setRepositoryCreator(_repositoryCreator);
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		_liferayTrashCapability.registerRepositoryEventListeners(
			repositoryEventRegistry);
	}

	public void setRepositoryCreator(RepositoryCreator repositoryCreator) {
		_repositoryCreator = repositoryCreator;
	}

	private LiferayTrashCapability _liferayTrashCapability =
		new LiferayTrashCapability();
	private RepositoryCreator _repositoryCreator;

}