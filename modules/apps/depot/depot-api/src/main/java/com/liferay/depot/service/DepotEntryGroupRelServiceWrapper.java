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

package com.liferay.depot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotEntryGroupRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelService
 * @generated
 */
public class DepotEntryGroupRelServiceWrapper
	implements DepotEntryGroupRelService,
			   ServiceWrapper<DepotEntryGroupRelService> {

	public DepotEntryGroupRelServiceWrapper(
		DepotEntryGroupRelService depotEntryGroupRelService) {

		_depotEntryGroupRelService = depotEntryGroupRelService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DepotEntryGroupRelServiceUtil} to access the depot entry group rel remote service. Add custom service methods to <code>com.liferay.depot.service.impl.DepotEntryGroupRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
			getDepotEntryGroupRels(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.getDepotEntryGroupRels(
			groupId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public DepotEntryGroupRelService getWrappedService() {
		return _depotEntryGroupRelService;
	}

	@Override
	public void setWrappedService(
		DepotEntryGroupRelService depotEntryGroupRelService) {

		_depotEntryGroupRelService = depotEntryGroupRelService;
	}

	private DepotEntryGroupRelService _depotEntryGroupRelService;

}