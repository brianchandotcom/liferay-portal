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

package com.liferay.document.library.repository.portlet.repository.internal;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.DynamicCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.portletrepository.PortletRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = RepositoryDefiner.class)
public class PortletRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getClassName() {
		return PortletRepository.class.getName();
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
			RelatedModelCapability.class,
			_portalCapabilityLocator.getRelatedModelCapability(
				documentRepository));

		capabilityRegistry.addExportedCapability(
			TrashCapability.class,
			_portalCapabilityLocator.getTrashCapability(documentRepository));

		capabilityRegistry.addExportedCapability(
			WorkflowCapability.class,
			_portalCapabilityLocator.getWorkflowCapability(
				documentRepository, WorkflowCapability.OperationMode.MINIMAL));

		capabilityRegistry.addSupportedCapability(
			DynamicCapability.class,
			_portalCapabilityLocator.getDynamicCapability(
				documentRepository, getClassName()));

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class,
			_portalCapabilityLocator.getProcessorCapability(
				documentRepository,
				ProcessorCapability.ResourceGenerationStrategy.REUSE));
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	@Reference(
		target = "(repository.target.class.name=com.liferay.portal.repository.portletrepository.PortletRepository)"
	)
	private RepositoryFactory _repositoryFactory;

}