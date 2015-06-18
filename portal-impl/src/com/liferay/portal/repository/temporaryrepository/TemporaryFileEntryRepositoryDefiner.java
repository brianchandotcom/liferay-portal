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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.capabilities.LiferayBulkOperationCapability;
import com.liferay.portal.repository.capabilities.MinimalWorkflowCapability;
import com.liferay.portal.repository.capabilities.TemporaryFileEntriesCapabilityImpl;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

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

		DLFileEntryServiceAdapter dlFileEntryServiceAdapter = null;
		DLFolderServiceAdapter dlFolderServiceAdapter = null;

		if (documentRepository instanceof LocalRepository) {
			dlFileEntryServiceAdapter = new DLFileEntryServiceAdapter(
				DLFileEntryLocalServiceUtil.getService());

			dlFolderServiceAdapter = new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService());
		}
		else {
			dlFileEntryServiceAdapter = new DLFileEntryServiceAdapter(
				DLFileEntryLocalServiceUtil.getService(),
				DLFileEntryServiceUtil.getService());

			dlFolderServiceAdapter = new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService(),
				DLFolderServiceUtil.getService());
		}

		capabilityRegistry.addExportedCapability(
			BulkOperationCapability.class,
			new LiferayBulkOperationCapability(
				documentRepository, dlFileEntryServiceAdapter,
				dlFolderServiceAdapter));
		capabilityRegistry.addExportedCapability(
			TemporaryFileEntriesCapability.class,
			new TemporaryFileEntriesCapabilityImpl(documentRepository));

		capabilityRegistry.addSupportedCapability(
			WorkflowCapability.class, new MinimalWorkflowCapability());
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