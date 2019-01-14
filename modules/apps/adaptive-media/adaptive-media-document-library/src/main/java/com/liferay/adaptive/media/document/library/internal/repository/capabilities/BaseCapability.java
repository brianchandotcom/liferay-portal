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

package com.liferay.adaptive.media.document.library.internal.repository.capabilities;

import com.liferay.adaptive.media.document.library.internal.util.AMCleanUpOnUpdateAndCheckInThreadLocal;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.processor.AMAsyncProcessor;
import com.liferay.adaptive.media.processor.AMAsyncProcessorLocator;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseCapability
	implements Capability, RepositoryEventAware {

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Add.class, FileEntry.class,
			this::_updateAdaptiveMedia);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			this::_deleteAdaptiveMedia);
		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Update.class, FileEntry.class,
			this::_updateAdaptiveMedia);
	}

	@Reference(unbind = "-")
	public void setAMAsyncProcessorLocator(
		AMAsyncProcessorLocator amAsyncProcessorLocator) {

		_amAsyncProcessorLocator = amAsyncProcessorLocator;
	}

	@Reference(unbind = "-")
	public void setAMImageEntryLocalService(
		AMImageEntryLocalService amImageEntryLocalService) {

		_amImageEntryLocalService = amImageEntryLocalService;
	}

	private void _deleteAdaptiveMedia(FileEntry fileEntry) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		try {
			AMAsyncProcessor<FileVersion, ?> amAsyncProcessor =
				_amAsyncProcessorLocator.locateForClass(FileVersion.class);

			List<FileVersion> fileVersions = fileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (FileVersion fileVersion : fileVersions) {
				amAsyncProcessor.triggerCleanUp(
					fileVersion,
					String.valueOf(fileVersion.getFileVersionId()));
			}
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private void _updateAdaptiveMedia(FileEntry fileEntry) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		try {
			FileVersion latestFileVersion = fileEntry.getLatestFileVersion(
				true);

			if (AMCleanUpOnUpdateAndCheckInThreadLocal.isEnabled()) {
				_amImageEntryLocalService.deleteAMImageEntryFileVersion(
					latestFileVersion);
			}

			AMAsyncProcessor<FileVersion, ?> amAsyncProcessor =
				_amAsyncProcessorLocator.locateForClass(FileVersion.class);

			amAsyncProcessor.triggerProcess(
				latestFileVersion,
				String.valueOf(latestFileVersion.getFileVersionId()));
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private AMAsyncProcessorLocator _amAsyncProcessorLocator;
	private AMImageEntryLocalService _amImageEntryLocalService;

}