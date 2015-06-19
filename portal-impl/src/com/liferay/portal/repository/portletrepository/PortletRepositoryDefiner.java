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
import com.liferay.portal.kernel.repository.LocalRepository;
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
import com.liferay.portal.repository.capabilities.util.TrashEntryServiceAdapter;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;
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

		DLAppServiceAdapter dlAppServiceAdapter = null;
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter = null;
		DLFolderServiceAdapter dlFolderServiceAdapter = null;
		RepositoryServiceAdapter repositoryServiceAdapter = null;
		TrashEntryServiceAdapter trashEntryServiceAdapter = null;

		if (documentRepository instanceof LocalRepository) {
			dlAppServiceAdapter = new DLAppServiceAdapter(
				DLAppLocalServiceUtil.getService());

			dlFileEntryServiceAdapter = new DLFileEntryServiceAdapter(
				DLFileEntryLocalServiceUtil.getService());

			dlFolderServiceAdapter = new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService());

			repositoryServiceAdapter = new RepositoryServiceAdapter(
				RepositoryLocalServiceUtil.getService());

			trashEntryServiceAdapter = new TrashEntryServiceAdapter(
				TrashEntryLocalServiceUtil.getService());
		}
		else {
			dlAppServiceAdapter = new DLAppServiceAdapter(
				DLAppLocalServiceUtil.getService(),
				DLAppServiceUtil.getService());

			dlFileEntryServiceAdapter = new DLFileEntryServiceAdapter(
				DLFileEntryLocalServiceUtil.getService(),
				DLFileEntryServiceUtil.getService());

			dlFolderServiceAdapter = new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService(),
				DLFolderServiceUtil.getService());

			repositoryServiceAdapter = new RepositoryServiceAdapter(
				RepositoryLocalServiceUtil.getService(),
				RepositoryServiceUtil.getService());

			trashEntryServiceAdapter = new TrashEntryServiceAdapter(
				TrashEntryLocalServiceUtil.getService(),
				TrashEntryServiceUtil.getService());
		}

		capabilityRegistry.addExportedCapability(
			RelatedModelCapability.class,

			new LiferayRelatedModelCapability(
				new RepositoryEntryConverter(),
				new RepositoryEntryChecker(documentRepository)));

		TrashCapability trashCapability = new LiferayTrashCapability(
			dlAppServiceAdapter, DLAppHelperLocalServiceUtil.getService(),
			dlFileEntryServiceAdapter, dlFolderServiceAdapter,
			repositoryServiceAdapter, trashEntryServiceAdapter,
			TrashVersionLocalServiceUtil.getService());

		capabilityRegistry.addExportedCapability(
			TrashCapability.class, trashCapability);

		capabilityRegistry.addExportedCapability(
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