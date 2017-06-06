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

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntryRepositoryDefiner extends BaseRepositoryDefiner {

	public static final String CLASS_NAME =
		TemporaryFileEntryRepository.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean isExternalRepository() {
		return false;
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		DocumentRepository documentRepository = capabilityRegistry.getTarget();

		capabilityRegistry.addExportedCapability(
			BulkOperationCapability.class,
			_portalCapabilityLocator.getBulkOperationCapability(
				documentRepository));
		capabilityRegistry.addExportedCapability(
			TemporaryFileEntriesCapability.class,
			_portalCapabilityLocator.getTemporaryFileEntriesCapability(
				documentRepository));

		capabilityRegistry.addSupportedCapability(
			WorkflowCapability.class,
			_portalCapabilityLocator.getWorkflowCapability(
				documentRepository, WorkflowCapability.OperationMode.MINIMAL));
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected PortalCapabilityLocator portalCapabilityLocator;

	private static PortalCapabilityLocator _portalCapabilityLocator =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalCapabilityLocator.class,
			TemporaryFileEntryRepositoryDefiner.class,
			"_portalCapabilityLocator", false);

	private RepositoryFactory _repositoryFactory;

}