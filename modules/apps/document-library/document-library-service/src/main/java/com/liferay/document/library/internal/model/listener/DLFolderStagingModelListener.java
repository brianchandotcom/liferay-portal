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
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
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
@Component(immediate = true, service = ModelListener.class)
public class DLFolderStagingModelListener extends BaseModelListener<DLFolder> {

	@Override
	public void onAfterCreate(DLFolder dlFolder) throws ModelListenerException {
		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			dlFolder.getGroupId());

		if (!exportableRepositoryIds.contains(dlFolder.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterCreate(dlFolder);
	}

	@Override
	public void onAfterRemove(DLFolder dlFolder) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(dlFolder);
	}

	@Override
	public void onAfterUpdate(DLFolder dlFolder) throws ModelListenerException {
		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			dlFolder.getGroupId());

		if (!exportableRepositoryIds.contains(dlFolder.getRepositoryId())) {
			return;
		}

		_stagingModelListener.onAfterUpdate(dlFolder);
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

	private ServiceTrackerList
		<DLExportableRepositoryPublisher, DLExportableRepositoryPublisher>
			_dlExportableRepositoryPublishers;

	@Reference
	private StagingModelListener<DLFolder> _stagingModelListener;

}