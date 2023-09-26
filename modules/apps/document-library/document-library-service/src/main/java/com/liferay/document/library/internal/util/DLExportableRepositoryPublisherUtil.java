/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.util;

import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;

import java.util.Collection;
import java.util.HashSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Shuyang Zhou
 */
public class DLExportableRepositoryPublisherUtil {

	public static Collection<Long> publish(long groupId) {
		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(groupId);

		_dlExportableRepositoryPublishers.forEach(
			dlExportableRepositoryPublisher ->
				dlExportableRepositoryPublisher.publish(
					groupId, exportableRepositoryIds::add));

		return exportableRepositoryIds;
	}

	private static final ServiceTrackerList<DLExportableRepositoryPublisher>
		_dlExportableRepositoryPublishers;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DLExportableRepositoryPublisherUtil.class);

		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundle.getBundleContext(), DLExportableRepositoryPublisher.class);
	}

}