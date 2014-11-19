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

package com.liferay.portal.repository.temporaryrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.capabilities.LiferayBulkOperationCapability;
import com.liferay.portal.repository.capabilities.MinimalWorkflowCapability;
import com.liferay.portal.repository.capabilities.TemporaryFileEntriesCapabilityImpl;
import com.liferay.portal.repository.liferayrepository.LiferayWorkflowLocalRepositoryWrapper;
import com.liferay.portal.repository.liferayrepository.LiferayWorkflowRepositoryWrapper;

/**
 * @author Iván Zaera
 */
public class TemporaryRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getClassName() {
		return TemporaryRepository.class.getName();
	}

	@Override
	public boolean isExternalRepository() {
		return false;
	}

	@Override
	public void registerCapabilities(CapabilityRegistry capabilityRegistry) {
		DocumentRepository documentRepository =
			capabilityRegistry.getDocumentRepository();

		if (documentRepository instanceof LocalRepository) {
			capabilityRegistry.addExportedCapability(
				BulkOperationCapability.class,
				new LiferayBulkOperationCapability(
					documentRepository.getRepositoryId()));
			capabilityRegistry.addExportedCapability(
				TemporaryFileEntriesCapability.class,
				new TemporaryFileEntriesCapabilityImpl(
					(LocalRepository)documentRepository));
		}

		capabilityRegistry.addSupportedCapability(
			WorkflowCapability.class, new MinimalWorkflowCapability());
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = new TemporaryRepositoryFactory(repositoryFactory);
	}

	private RepositoryFactory _repositoryFactory;
	private final WorkflowCapability _workflowCapability =
		new MinimalWorkflowCapability();

	private class TemporaryRepositoryFactory implements RepositoryFactory {

		public TemporaryRepositoryFactory(RepositoryFactory repositoryFactory) {
			_repositoryFactory = repositoryFactory;
		}

		@Override
		public LocalRepository createLocalRepository(long repositoryId)
			throws PortalException {

			return new LiferayWorkflowLocalRepositoryWrapper(
				_repositoryFactory.createLocalRepository(repositoryId),
				_workflowCapability);
		}

		@Override
		public Repository createRepository(long repositoryId)
			throws PortalException {

			return new LiferayWorkflowRepositoryWrapper(
				_repositoryFactory.createRepository(repositoryId),
				_workflowCapability);
		}

		private final RepositoryFactory _repositoryFactory;

	}

}