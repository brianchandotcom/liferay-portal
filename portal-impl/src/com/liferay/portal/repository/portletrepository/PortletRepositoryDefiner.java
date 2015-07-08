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

package com.liferay.portal.repository.portletrepository;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.capabilities.LiferayRelatedModelCapability;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;
import com.liferay.portal.repository.capabilities.MinimalWorkflowCapability;
import com.liferay.portal.repository.capabilities.util.DLAppServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;

/**
 * @author Adolfo Pérez
 */
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

		DLAppServiceAdapter dlAppServiceAdapter = DLAppServiceAdapter.create(
			documentRepository);

		DLFileEntryServiceAdapter dlFileEntryServiceAdapter =
			DLFileEntryServiceAdapter.create(documentRepository);

		DLFolderServiceAdapter dlFolderServiceAdapter =
			DLFolderServiceAdapter.create(documentRepository);

		RepositoryServiceAdapter repositoryServiceAdapter =
			RepositoryServiceAdapter.create(documentRepository);

		capabilityRegistry.addExportedCapability(
			RelatedModelCapability.class,

			new LiferayRelatedModelCapability(
				new RepositoryEntryConverter(),
				new RepositoryEntryChecker(documentRepository)));

		TrashCapability trashCapability = new LiferayTrashCapability(
			dlAppServiceAdapter, DLAppHelperLocalServiceUtil.getService(),
			dlFileEntryServiceAdapter, dlFolderServiceAdapter,
			repositoryServiceAdapter, TrashEntryLocalServiceUtil.getService(),
			TrashVersionLocalServiceUtil.getService());

		capabilityRegistry.addExportedCapability(
			TrashCapability.class, trashCapability);

		capabilityRegistry.addExportedCapability(
			WorkflowCapability.class,
			new MinimalWorkflowCapability(dlFileEntryServiceAdapter));
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private RepositoryFactory _repositoryFactory;

}