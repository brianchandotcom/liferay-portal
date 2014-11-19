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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class MinimalWorkflowCapability implements WorkflowCapability {

	@Override
	public void addFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void revertFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		doUpdateStatus(userId, fileEntry, serviceContext);
	}

	protected void doUpdateStatus(
			long userId, FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = Collections.emptyMap();

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryLocalServiceUtil.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	}

}