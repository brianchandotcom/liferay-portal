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

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.model.listener.StagingModelListener;

import java.util.Collection;
import java.util.HashSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
		catch (PortalException pe) {
			_log.error(pe, pe);

			return;
		}

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			dlFileEntry.getGroupId());

		if (!exportableRepositoryIds.contains(dlFileEntry.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterCreate(dlFileEntry);
	}

	@Override
	public void onAfterUpdate(DLFileVersion dlFileVersion)
		throws ModelListenerException {

		if ((dlFileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			(dlFileVersion.getStatus() != WorkflowConstants.STATUS_IN_TRASH)) {

			return;
		}

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = dlFileVersion.getFileEntry();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return;
		}

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			dlFileEntry.getGroupId());

		if (!exportableRepositoryIds.contains(dlFileEntry.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterUpdate(dlFileEntry);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundleContext, DLExportableRepositoryPublisher.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlExportableRepositoryPublishers != null) {
			_dlExportableRepositoryPublishers.close();
		}
	}

	private Collection<Long> _getExportableRepositoryIds(long groupId) {
		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(groupId);

		for (DLExportableRepositoryPublisher dlExportableRepositoryPublisher :
				_dlExportableRepositoryPublishers) {

			dlExportableRepositoryPublisher.publish(
				groupId, exportableRepositoryIds::add);
		}

		return exportableRepositoryIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionStagingModelListener.class);

	private ServiceTrackerList
		<DLExportableRepositoryPublisher, DLExportableRepositoryPublisher>
			_dlExportableRepositoryPublishers;

	@Reference
	private StagingModelListener<DLFileEntry> _stagingModelListener;

}