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

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.capabilities.LiferayBulkOperationCapability;
import com.liferay.portal.repository.capabilities.LiferaySyncCapability;
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

		DocumentRepository documentRepository =
			capabilityRegistry.getDocumentRepository();

		BulkOperationCapability bulkOperationCapability =
			new LiferayBulkOperationCapability(
				documentRepository.getRepositoryId());

		capabilityRegistry.addExportedCapability(
			BulkOperationCapability.class, bulkOperationCapability);

		capabilityRegistry.addSupportedCapability(
			SyncCapability.class, _liferaySyncCapability);
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		_liferaySyncCapability.registerRepositoryEventListeners(
			repositoryEventRegistry);
		_liferayTrashCapability.registerRepositoryEventListeners(
			repositoryEventRegistry);
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private final LiferaySyncCapability _liferaySyncCapability =
		new LiferaySyncCapability();
	private final LiferayTrashCapability _liferayTrashCapability =
		new LiferayTrashCapability();
	private RepositoryFactory _repositoryFactory;

}