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

import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.document.library.service.DLFileVersionPreviewLocalService;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.repository.liferayrepository.LiferayProcessorLocalRepositoryWrapper;
import com.liferay.portal.repository.liferayrepository.LiferayProcessorRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

/**
 * @author Adolfo Pérez
 */
public class LiferayProcessorCapability
	implements ProcessorCapability, RepositoryEventAware,
			   RepositoryWrapperAware {

	public LiferayProcessorCapability(
		ResourceGenerationStrategy resourceGenerationStrategy,
		DLFileVersionPreviewLocalService dlFileVersionPreviewLocalService) {

		_resourceGenerationStrategy = resourceGenerationStrategy;
		_dlFileVersionPreviewLocalService = dlFileVersionPreviewLocalService;
	}

	@Override
	public void cleanUp(FileEntry fileEntry) {
		DLProcessorRegistryUtil.cleanUp(fileEntry);
	}

	@Override
	public void cleanUp(FileVersion fileVersion) {
		DLProcessorRegistryUtil.cleanUp(fileVersion);
	}

	@Override
	public void copy(FileEntry fileEntry, FileVersion fileVersion) {
		if (_resourceGenerationStrategy == ResourceGenerationStrategy.REUSE) {
			registerDLProcessorCallback(fileEntry, fileVersion);
		}
		else {
			generateNew(fileEntry);
		}
	}

	@Override
	public void generateNew(FileEntry fileEntry) {
		registerDLProcessorCallback(fileEntry, null);
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			fileEntry -> {
				_dlFileVersionPreviewLocalService.
					deleteDLFileEntryFileVersionPreviews(
						fileEntry.getFileEntryId());

				cleanUp(fileEntry);
			});
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		return new LiferayProcessorLocalRepositoryWrapper(
			localRepository, this);
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		return new LiferayProcessorRepositoryWrapper(repository, this);
	}

	protected void registerDLProcessorCallback(
		final FileEntry fileEntry, final FileVersion fileVersion) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				DLProcessorRegistryUtil.trigger(fileEntry, fileVersion, true);

				return null;
			});
	}

	private final DLFileVersionPreviewLocalService
		_dlFileVersionPreviewLocalService;
	private final ResourceGenerationStrategy _resourceGenerationStrategy;

}