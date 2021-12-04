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

	public DepotEntryGroupRelServiceWrapper() {
		this(null);
	}

	public DepotEntryGroupRelServiceWrapper(
		DepotEntryGroupRelService depotEntryGroupRelService) {

		_depotEntryGroupRelService = depotEntryGroupRelService;
	}

	@Override
	public com.liferay.depot.model.DepotEntryGroupRel addDepotEntryGroupRel(
			long depotEntryId, long toGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.addDepotEntryGroupRel(
			depotEntryId, toGroupId);
	}

	@Override
	public com.liferay.depot.model.DepotEntryGroupRel deleteDepotEntryGroupRel(
			long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.deleteDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	@Override
	public java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
			getDepotEntryGroupRels(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.getDepotEntryGroupRels(
			groupId, start, end);
	}

	@Override
	public int getDepotEntryGroupRelsCount(
			com.liferay.depot.model.DepotEntry depotEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.getDepotEntryGroupRelsCount(
			depotEntry);
	}

	@Override
	public int getDepotEntryGroupRelsCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.getDepotEntryGroupRelsCount(groupId);
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
	public com.liferay.depot.model.DepotEntryGroupRel
			updateDDMStructuresAvailable(
				long depotEntryGroupRelId, boolean ddmStructuresAvailable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.updateDDMStructuresAvailable(
			depotEntryGroupRelId, ddmStructuresAvailable);
	}

	@Override
	public com.liferay.depot.model.DepotEntryGroupRel updateSearchable(
			long depotEntryGroupRelId, boolean searchable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelService.updateSearchable(
			depotEntryGroupRelId, searchable);
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