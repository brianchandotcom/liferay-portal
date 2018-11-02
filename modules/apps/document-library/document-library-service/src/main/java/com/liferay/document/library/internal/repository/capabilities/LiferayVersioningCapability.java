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

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	property = "repository.class.name=com.liferay.portal.repository.liferayrepository.LiferayRepository",
	service = Capability.class
)
public class LiferayVersioningCapability
	implements Capability, RepositoryWrapperAware {

	@Activate
	public void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		return new LocalRepositoryWrapper(localRepository) {

			@Override
			public FileEntry updateFileEntry(
					long userId, long fileEntryId, String sourceFileName,
					String mimeType, String title, String description,
					String changeLog,
					DLVersionNumberIncrease dlVersionNumberIncrease, File file,
					ServiceContext serviceContext)
				throws PortalException {

				return _purgeVersions(
					super.updateFileEntry(
						userId, fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, dlVersionNumberIncrease, file,
						serviceContext));
			}

			@Override
			public FileEntry updateFileEntry(
					long userId, long fileEntryId, String sourceFileName,
					String mimeType, String title, String description,
					String changeLog,
					DLVersionNumberIncrease dlVersionNumberIncrease,
					InputStream is, long size, ServiceContext serviceContext)
				throws PortalException {

				return _purgeVersions(
					super.updateFileEntry(
						userId, fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, dlVersionNumberIncrease, is,
						size, serviceContext));
			}

		};
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		return new RepositoryWrapper(repository) {

			@Override
			public FileEntry updateFileEntry(
					long userId, long fileEntryId, String sourceFileName,
					String mimeType, String title, String description,
					String changeLog,
					DLVersionNumberIncrease dlVersionNumberIncrease, File file,
					ServiceContext serviceContext)
				throws PortalException {

				return _purgeVersions(
					super.updateFileEntry(
						userId, fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, dlVersionNumberIncrease, file,
						serviceContext));
			}

			@Override
			public FileEntry updateFileEntry(
					long userId, long fileEntryId, String sourceFileName,
					String mimeType, String title, String description,
					String changeLog,
					DLVersionNumberIncrease dlVersionNumberIncrease,
					InputStream is, long size, ServiceContext serviceContext)
				throws PortalException {

				return _purgeVersions(
					super.updateFileEntry(
						userId, fileEntryId, sourceFileName, mimeType, title,
						description, changeLog, dlVersionNumberIncrease, is,
						size, serviceContext));
			}

		};
	}

	private FileEntry _purgeVersions(FileEntry fileEntry)
		throws PortalException {

		long maximumNumberOfVersions =
			_dlConfiguration.maximumNumberOfVersions();

		int status = WorkflowConstants.STATUS_ANY;

		long numberOfVersions = fileEntry.getFileVersionsCount(status);

		if (numberOfVersions > maximumNumberOfVersions) {
			List<FileVersion> fileVersions = fileEntry.getFileVersions(status);

			long numberOfVersionsToDelete =
				numberOfVersions - maximumNumberOfVersions;

			for (int i = 0; i < numberOfVersionsToDelete; i++) {
				FileVersion fileVersion = fileVersions.get(
					(int)(numberOfVersions - i - 1));

				_dlAppService.deleteFileVersion(
					fileEntry.getFileEntryId(), fileVersion.getVersion());
			}
		}

		return fileEntry;
	}

	@Reference
	private DLAppService _dlAppService;

	private DLConfiguration _dlConfiguration;

}