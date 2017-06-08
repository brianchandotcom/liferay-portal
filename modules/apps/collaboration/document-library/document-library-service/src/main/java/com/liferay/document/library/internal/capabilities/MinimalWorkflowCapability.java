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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.capabilities.WorkflowSupport;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.liferayrepository.LiferayWorkflowLocalRepositoryWrapper;
import com.liferay.portal.repository.liferayrepository.LiferayWorkflowRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class MinimalWorkflowCapability
	implements RepositoryWrapperAware, WorkflowCapability, WorkflowSupport {

	public MinimalWorkflowCapability(
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter) {

		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
	}

	@Override
	public void addFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, FileEntry fileEntry, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public int getStatus(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		return dlFileEntry.getStatus();
	}

	@Override
	public void revertFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public LocalRepository wrapLocalRepository(
		LocalRepository localRepository) {

		return new LiferayWorkflowLocalRepositoryWrapper(localRepository, this);
	}

	@Override
	public Repository wrapRepository(Repository repository) {
		return new LiferayWorkflowRepositoryWrapper(repository, this);
	}

	protected void doUpdateStatus(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = Collections.emptyMap();

		FileVersion fileVersion = fileEntry.getFileVersion();

		_dlFileEntryServiceAdapter.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	}

	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;

}