/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.temporaryrepository;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;

import java.util.function.BiFunction;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntryRepositoryDefiner extends BaseRepositoryDefiner {

	public static final String CLASS_NAME =
		TemporaryFileEntryRepository.class.getName();

	public static BiFunction
		<PortalCapabilityLocator, RepositoryFactory, RepositoryDefiner>
			getFactoryBiFunction() {

		return TemporaryFileEntryRepositoryDefiner::new;
	}

	public TemporaryFileEntryRepositoryDefiner(
		PortalCapabilityLocator portalCapabilityLocator,
		RepositoryFactory repositoryFactory) {

		_portalCapabilityLocator = portalCapabilityLocator;
		_repositoryFactory = repositoryFactory;
	}

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

		if (_portalCapabilityLocator == null) {
			ReflectionUtil.throwException(
				new UndeployedExternalRepositoryException(
					TemporaryFileEntryRepositoryDefiner.class.getName(),
					StringBundler.concat(
						"Repository definer ",
						TemporaryFileEntryRepositoryDefiner.class.getName(),
						" is not initialized")));
		}

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

	private final PortalCapabilityLocator _portalCapabilityLocator;
	private final RepositoryFactory _repositoryFactory;

}