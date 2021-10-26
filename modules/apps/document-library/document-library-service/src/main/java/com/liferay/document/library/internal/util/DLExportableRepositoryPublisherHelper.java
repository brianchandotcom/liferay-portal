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

package com.liferay.document.library.internal.util;

import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.Collection;
import java.util.HashSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Shuyang Zhou
 */
@Component(service = DLExportableRepositoryPublisherHelper.class)
public class DLExportableRepositoryPublisherHelper {

	public Collection<Long> publish(long groupId) {
		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(groupId);

		_dlExportableRepositoryPublishers.forEach(
			dlExportableRepositoryPublisher ->
				dlExportableRepositoryPublisher.publish(
					groupId, exportableRepositoryIds::add));

		return exportableRepositoryIds;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundleContext, DLExportableRepositoryPublisher.class);
	}

	@Deactivate
	protected void deactivate() {
		_dlExportableRepositoryPublishers.close();
	}

	private ServiceTrackerList<DLExportableRepositoryPublisher>
		_dlExportableRepositoryPublishers;

}