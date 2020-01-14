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
 * Provides a wrapper for {@link DepotEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryService
 * @generated
 */
public class DepotEntryServiceWrapper
	implements DepotEntryService, ServiceWrapper<DepotEntryService> {

	public DepotEntryServiceWrapper(DepotEntryService depotEntryService) {
		_depotEntryService = depotEntryService;
	}

	@Override
	public com.liferay.depot.model.DepotEntry addDepotEntry(
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryService.addDepotEntry(
			nameMap, descriptionMap, serviceContext);
	}

	@Override
	public com.liferay.depot.model.DepotEntry deleteDepotEntry(
			long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryService.deleteDepotEntry(depotEntryId);
	}

	@Override
	public com.liferay.depot.model.DepotEntry getDepotEntry(long depotEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryService.getDepotEntry(depotEntryId);
	}

	@Override
	public com.liferay.depot.model.DepotEntry getGroupDepotEntry(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryService.getGroupDepotEntry(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.depot.model.DepotEntry updateDepotEntry(
			long depotEntryId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Map<String, Boolean> depotAppCustomizationMap,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryService.updateDepotEntry(
			depotEntryId, nameMap, descriptionMap, depotAppCustomizationMap,
			typeSettingsProperties, serviceContext);
	}

	@Override
	public DepotEntryService getWrappedService() {
		return _depotEntryService;
	}

	@Override
	public void setWrappedService(DepotEntryService depotEntryService) {
		_depotEntryService = depotEntryService;
	}

	private DepotEntryService _depotEntryService;

}