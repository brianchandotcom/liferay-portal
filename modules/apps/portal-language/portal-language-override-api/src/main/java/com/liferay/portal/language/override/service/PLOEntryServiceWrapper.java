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

package com.liferay.portal.language.override.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PLOEntryService}.
 *
 * @author Drew Brokke
 * @see PLOEntryService
 * @generated
 */
public class PLOEntryServiceWrapper
	implements PLOEntryService, ServiceWrapper<PLOEntryService> {

	public PLOEntryServiceWrapper() {
		this(null);
	}

	public PLOEntryServiceWrapper(PLOEntryService ploEntryService) {
		_ploEntryService = ploEntryService;
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry
			addOrUpdatePLOEntry(String key, String languageId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryService.addOrUpdatePLOEntry(key, languageId, value);
	}

	@Override
	public void deletePLOEntries(String key)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ploEntryService.deletePLOEntries(key);
	}

	@Override
	public com.liferay.portal.language.override.model.PLOEntry deletePLOEntry(
			String key, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryService.deletePLOEntry(key, languageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ploEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.language.override.model.PLOEntry>
			getPLOEntries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryService.getPLOEntries(companyId);
	}

	@Override
	public int getPLOEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ploEntryService.getPLOEntriesCount(companyId);
	}

	@Override
	public void setPLOEntries(
			String key, java.util.Map<java.util.Locale, String> localizationMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ploEntryService.setPLOEntries(key, localizationMap);
	}

	@Override
	public PLOEntryService getWrappedService() {
		return _ploEntryService;
	}

	@Override
	public void setWrappedService(PLOEntryService ploEntryService) {
		_ploEntryService = ploEntryService;
	}

	private PLOEntryService _ploEntryService;

}