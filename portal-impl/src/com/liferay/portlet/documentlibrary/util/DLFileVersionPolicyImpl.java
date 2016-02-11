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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.util.DLFileVersionPolicy;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

/**
 * @author Adolfo Pérez
 */
public class DLFileVersionPolicyImpl implements DLFileVersionPolicy {

	public void destroy() {
		_serviceTrackerList.close();
	}

	@Override
	public boolean isKeepFileVersionLabel(
			DLFileVersion lastDLFileVersion, DLFileVersion latestDLFileVersion,
			ServiceContext serviceContext)
		throws PortalException {

		for (DLFileVersionPolicy dlFileVersionPolicy : _serviceTrackerList) {
			if (!dlFileVersionPolicy.isKeepFileVersionLabel(
					lastDLFileVersion, latestDLFileVersion, serviceContext)) {

				return false;
			}
		}

		return true;
	}

	private final ServiceTrackerList<DLFileVersionPolicy> _serviceTrackerList =
		ServiceTrackerCollections.openList(DLFileVersionPolicy.class);

}