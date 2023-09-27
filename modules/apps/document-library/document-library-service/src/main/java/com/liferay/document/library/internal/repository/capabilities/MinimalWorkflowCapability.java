/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
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

		_updateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, FileEntry fileEntry,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			ServiceContext serviceContext)
		throws PortalException {

		_updateStatus(userId, fileEntry, serviceContext);
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

		_updateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			ServiceContext serviceContext)
		throws PortalException {

		_updateStatus(userId, fileEntry, serviceContext);
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

	private void _updateStatus(
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