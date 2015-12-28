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

package com.liferay.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SAPEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryService
 * @generated
 */
@ProviderType
public class SAPEntryServiceWrapper implements SAPEntryService,
	ServiceWrapper<SAPEntryService> {
	public SAPEntryServiceWrapper(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		java.lang.String allowedServiceSignatures, boolean defaultSAPEntry,
		boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.addSAPEntry(allowedServiceSignatures,
			defaultSAPEntry, enabled, name, titleMap, serviceContext);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.deleteSAPEntry(sapEntry);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.deleteSAPEntry(sapEntryId);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.fetchSAPEntry(companyId, name);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {
		return _sapEntryService.getCompanySAPEntries(companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.SAPEntry> obc) {
		return _sapEntryService.getCompanySAPEntries(companyId, start, end, obc);
	}

	@Override
	public int getCompanySAPEntriesCount(long companyId) {
		return _sapEntryService.getCompanySAPEntriesCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _sapEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.getSAPEntry(companyId, name);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.getSAPEntry(sapEntryId);
	}

	@Override
	public com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		long sapEntryId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sapEntryService.updateSAPEntry(sapEntryId,
			allowedServiceSignatures, defaultSAPEntry, enabled, name, titleMap,
			serviceContext);
	}

	@Override
	public SAPEntryService getWrappedService() {
		return _sapEntryService;
	}

	@Override
	public void setWrappedService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	private SAPEntryService _sapEntryService;
}