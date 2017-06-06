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

package com.liferay.document.library.internal.capabilities;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.CommentCapability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.repository.capabilities.ConfigurationCapabilityImpl;
import com.liferay.portal.repository.capabilities.LiferayBulkOperationCapability;
import com.liferay.portal.repository.capabilities.LiferayCommentCapability;
import com.liferay.portal.repository.capabilities.LiferayProcessorCapability;
import com.liferay.portal.repository.capabilities.LiferayRelatedModelCapability;
import com.liferay.portal.repository.capabilities.LiferaySyncCapability;
import com.liferay.portal.repository.capabilities.LiferayThumbnailCapability;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;
import com.liferay.portal.repository.capabilities.LiferayWorkflowCapability;
import com.liferay.portal.repository.capabilities.MinimalWorkflowCapability;
import com.liferay.portal.repository.capabilities.TemporaryFileEntriesCapabilityImpl;
import com.liferay.portal.repository.capabilities.util.DLAppServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileVersionServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.capabilities.util.GroupServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;
import com.liferay.trash.kernel.service.TrashEntryLocalService;
import com.liferay.trash.kernel.service.TrashVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = PortalCapabilityLocator.class)
public class PortalCapabilityLocatorImpl implements PortalCapabilityLocator {

	@Override
	public BulkOperationCapability getBulkOperationCapability(
		DocumentRepository documentRepository) {

		return new LiferayBulkOperationCapability(
			documentRepository,
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFolderServiceAdapter.create(documentRepository));
	}

	@Override
	public CommentCapability getCommentCapability(
		DocumentRepository documentRepository) {

		return _commentCapability;
	}

	@Override
	public ConfigurationCapability getConfigurationCapability(
		DocumentRepository documentRepository) {

		return new ConfigurationCapabilityImpl(
			documentRepository,
			RepositoryServiceAdapter.create(documentRepository));
	}

	@Override
	public ProcessorCapability getProcessorCapability(
		DocumentRepository documentRepository,
		ProcessorCapability.ResourceGenerationStrategy
			resourceGenerationStrategy) {

		if (resourceGenerationStrategy ==
				ProcessorCapability.
					ResourceGenerationStrategy.ALWAYS_GENERATE) {

			return _alwaysGeneratingProcessorCapability;
		}

		return _reusingProcessorCapability;
	}

	@Override
	public RelatedModelCapability getRelatedModelCapability(
		DocumentRepository documentRepository) {

		RepositoryEntryChecker repositoryEntryChecker =
			new RepositoryEntryChecker(documentRepository);

		return new LiferayRelatedModelCapability(
			_repositoryEntryConverter, repositoryEntryChecker);
	}

	@Override
	public SyncCapability getSyncCapability(
		DocumentRepository documentRepository) {

		return new LiferaySyncCapability(
			GroupServiceAdapter.create(documentRepository),
			_dlSyncEventLocalService);
	}

	@Override
	public TemporaryFileEntriesCapability getTemporaryFileEntriesCapability(
		DocumentRepository documentRepository) {

		return new TemporaryFileEntriesCapabilityImpl(documentRepository);
	}

	@Override
	public ThumbnailCapability getThumbnailCapability(
		DocumentRepository documentRepository) {

		RepositoryEntryChecker repositoryEntryChecker =
			new RepositoryEntryChecker(documentRepository);

		return new LiferayThumbnailCapability(
			_repositoryEntryConverter, repositoryEntryChecker);
	}

	@Override
	public TrashCapability getTrashCapability(
		DocumentRepository documentRepository) {

		return new LiferayTrashCapability(
			_dlAppHelperLocalService,
			DLAppServiceAdapter.create(documentRepository),
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFolderServiceAdapter.create(documentRepository),
			RepositoryServiceAdapter.create(documentRepository),
			_trashEntryLocalService, _trashVersionLocalService);
	}

	@Override
	public WorkflowCapability getWorkflowCapability(
		DocumentRepository documentRepository,
		WorkflowCapability.OperationMode operationMode) {

		if (operationMode == WorkflowCapability.OperationMode.MINIMAL) {
			return new MinimalWorkflowCapability(
				DLFileEntryServiceAdapter.create(documentRepository));
		}

		return new LiferayWorkflowCapability(
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFileVersionServiceAdapter.create(documentRepository));
	}

	private final CommentCapability _commentCapability =
		new LiferayCommentCapability();

	@Reference
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Reference
	private DLSyncEventLocalService _dlSyncEventLocalService;

	private final ProcessorCapability _reusingProcessorCapability =
		new LiferayProcessorCapability();
	private final ProcessorCapability _alwaysGeneratingProcessorCapability =
		new LiferayProcessorCapability(
			ProcessorCapability.ResourceGenerationStrategy.ALWAYS_GENERATE);
	private final RepositoryEntryConverter _repositoryEntryConverter =
		new RepositoryEntryConverter();

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}