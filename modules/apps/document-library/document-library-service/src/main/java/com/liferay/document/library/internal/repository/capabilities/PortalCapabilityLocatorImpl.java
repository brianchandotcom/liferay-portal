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

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.security.io.InputStreamSanitizer;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.CommentCapability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.DynamicCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.repository.capabilities.util.DLAppServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileVersionServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.capabilities.util.GroupServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = {CacheRegistryItem.class, PortalCapabilityLocator.class})
public class PortalCapabilityLocatorImpl
	implements CacheRegistryItem, PortalCapabilityLocator {

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
	public DynamicCapability getDynamicCapability(
		DocumentRepository documentRepository, String repositoryClassName) {

		return _liferayDynamicCapabilities.computeIfAbsent(
			documentRepository,
			key -> new LiferayDynamicCapability(
				_bundleContext, repositoryClassName));
	}

	@Override
	public ProcessorCapability getProcessorCapability(
		DocumentRepository documentRepository,
		ProcessorCapability.ResourceGenerationStrategy
			resourceGenerationStrategy) {

		if (resourceGenerationStrategy ==
				ProcessorCapability.ResourceGenerationStrategy.
					ALWAYS_GENERATE) {

			return _alwaysGeneratingProcessorCapability;
		}

		return _reusingProcessorCapability;
	}

	@Override
	public String getRegistryName() {
		Class<?> clazz = getClass();

		return clazz.getName();
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
	public RepositoryEventTriggerCapability getRepositoryEventTriggerCapability(
		DocumentRepository documentRepository,
		RepositoryEventTrigger repositoryEventTrigger) {

		return new LiferayRepositoryEventTriggerCapability(
			repositoryEventTrigger);
	}

	@Override
	public SyncCapability getSyncCapability(
		DocumentRepository documentRepository) {

		return new LiferaySyncCapability(
			GroupServiceAdapter.create(documentRepository),
			_dlSyncEventLocalService, _messageBus);
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

	@Override
	public void invalidate() {
		_clearLiferayDynamicCapabilities();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_alwaysGeneratingProcessorCapability = new LiferayProcessorCapability(
			ProcessorCapability.ResourceGenerationStrategy.ALWAYS_GENERATE,
			_dlFileVersionPreviewLocalService, _inputStreamSanitizer);
		_reusingProcessorCapability = new LiferayProcessorCapability(
			ProcessorCapability.ResourceGenerationStrategy.REUSE,
			_dlFileVersionPreviewLocalService, _inputStreamSanitizer);
	}

	@Deactivate
	protected void deactivate() {
		_clearLiferayDynamicCapabilities();
	}

	private void _clearLiferayDynamicCapabilities() {
		for (LiferayDynamicCapability liferayDynamicCapability :
				_liferayDynamicCapabilities.values()) {

			liferayDynamicCapability.clear();
		}

		_liferayDynamicCapabilities.clear();
	}

	private ProcessorCapability _alwaysGeneratingProcessorCapability;
	private BundleContext _bundleContext;
	private final CommentCapability _commentCapability =
		new LiferayCommentCapability();

	@Reference
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Reference
	private DLFileVersionPreviewLocalService _dlFileVersionPreviewLocalService;

	@Reference
	private DLSyncEventLocalService _dlSyncEventLocalService;

	@Reference
	private InputStreamSanitizer _inputStreamSanitizer;

	private final Map<DocumentRepository, LiferayDynamicCapability>
		_liferayDynamicCapabilities = new ConcurrentHashMap<>();

	@Reference
	private MessageBus _messageBus;

	private final RepositoryEntryConverter _repositoryEntryConverter =
		new RepositoryEntryConverter();
	private ProcessorCapability _reusingProcessorCapability;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}