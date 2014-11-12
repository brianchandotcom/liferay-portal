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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class LiferayWorkflowRepositoryWrapper extends RepositoryWrapper {

	public LiferayWorkflowRepositoryWrapper(Repository repository) {
		super(repository);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		DLFileVersion dlFileVersion = _getWorkflowDLFileVersion(
			fileEntryId, serviceContext);

		super.revertFileEntry(fileEntryId, version, serviceContext);

		_startWorkflowInstance(_getUserId(), dlFileVersion, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		DLFileVersion dlFileVersion = _getWorkflowDLFileVersion(
			fileEntryId, serviceContext);

		FileEntry fileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		_startWorkflowInstance(userId, dlFileVersion, serviceContext);

		return fileEntry;
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileVersion dlFileVersion = _getWorkflowDLFileVersion(
			fileEntryId, serviceContext);

		FileEntry fileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		_startWorkflowInstance(userId, dlFileVersion, serviceContext);

		return fileEntry;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean,
	 *             java.io.File, com.liferay.portal.service.ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			_getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean,
	 *             java.io.InputStream, long,
	 *             com.liferay.portal.service.ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			_getUserId(), fileEntryId, sourceFileName, mimeType, title,
			description, changeLog, majorVersion, is, size, serviceContext);
	}

	/**
	 * See {@link com.liferay.portal.service.BaseServiceImpl#getUserId()}
	 */
	private long _getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (name == null) {
			throw new PrincipalException();
		}

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}
		else {
			for (int i = 0; i < BaseServiceImpl.ANONYMOUS_NAMES.length; i++) {
				if (StringUtil.equalsIgnoreCase(
						name, BaseServiceImpl.ANONYMOUS_NAMES[i])) {

					throw new PrincipalException(
						"Principal cannot be " +
							BaseServiceImpl.ANONYMOUS_NAMES[i]);
				}
			}
		}

		return GetterUtil.getLong(name);
	}

	/**
	 * See {@link com.liferay.portlet.documentlibrary.service.impl.DLFileEntryLocalServiceImpl#updateFileEntry(long, long, String, String, String, String, String, String, boolean, String, long, java.util.Map, java.io.File, java.io.InputStream, long, com.liferay.portal.service.ServiceContext)}
	 */
	private DLFileVersion _getWorkflowDLFileVersion(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		boolean checkedOut = dlFileEntry.isCheckedOut();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getLatestFileVersion(
				fileEntryId, !checkedOut);

		boolean autoCheckIn = !checkedOut && dlFileVersion.isApproved();

		int workflowAction = serviceContext.getWorkflowAction();

		if (!autoCheckIn && !checkedOut &&
			(workflowAction == WorkflowConstants.ACTION_PUBLISH)) {

			return dlFileVersion;
		}
		else {
			return null;
		}
	}

	private void _startWorkflowInstance(
			long userId, DLFileVersion dlFileVersion,
			ServiceContext serviceContext)
		throws PortalException {

		if (dlFileVersion == null) {
			return;
		}

		String syncEvent = DLSyncConstants.EVENT_UPDATE;

		if (dlFileVersion.getVersion().equals(
				DLFileEntryConstants.VERSION_DEFAULT)) {

			syncEvent = DLSyncConstants.EVENT_ADD;
		}

		DLUtil.startWorkflowInstance(
			userId, dlFileVersion, syncEvent, serviceContext);
	}

}