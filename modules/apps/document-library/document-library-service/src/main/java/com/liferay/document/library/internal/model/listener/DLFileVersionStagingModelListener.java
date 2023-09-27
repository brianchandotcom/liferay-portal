/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.internal.helper.DLExportableRepositoryPublisherHelper;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.model.listener.StagingModelListener;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class DLFileVersionStagingModelListener
	extends BaseModelListener<DLFileVersion> {

	@Override
	public void onAfterCreate(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		if (dlFileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = dlFileVersion.getFileEntry();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return;
		}

		Collection<Long> exportableRepositoryIds =
			_dlExportableRepositoryPublisherHelper.publish(
				dlFileEntry.getGroupId());

		if (!exportableRepositoryIds.contains(dlFileEntry.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterCreate(dlFileEntry);
	}

	@Override
	public void onAfterUpdate(
			DLFileVersion originalDLFileVersion, DLFileVersion dlFileVersion)
		throws ModelListenerException {

		if ((dlFileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			(dlFileVersion.getStatus() != WorkflowConstants.STATUS_IN_TRASH)) {

			return;
		}

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = dlFileVersion.getFileEntry();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return;
		}

		Collection<Long> exportableRepositoryIds =
			_dlExportableRepositoryPublisherHelper.publish(
				dlFileEntry.getGroupId());

		if (!exportableRepositoryIds.contains(dlFileEntry.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterUpdate(dlFileEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionStagingModelListener.class);

	@Reference
	private DLExportableRepositoryPublisherHelper
		_dlExportableRepositoryPublisherHelper;

	@Reference
	private StagingModelListener<DLFileEntry> _stagingModelListener;

}