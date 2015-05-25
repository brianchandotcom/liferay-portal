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

package com.liferay.service.access.control.profile.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SACPEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryService
 * @generated
 */
@ProviderType
public class SACPEntryServiceWrapper implements SACPEntryService,
	ServiceWrapper<SACPEntryService> {
	public SACPEntryServiceWrapper(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		long companyId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.addSACPEntry(companyId, allowedServices, name,
			titleMap, serviceContext);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.deleteSACPEntry(sacpEntry);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.deleteSACPEntry(sacpEntryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _sacpEntryService.getBeanIdentifier();
	}

	@Override
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {
		return _sacpEntryService.getCompanySACPEntries(companyId, start, end);
	}

	@Override
	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc) {
		return _sacpEntryService.getCompanySACPEntries(companyId, start, end,
			obc);
	}

	@Override
	public int getCompanySACPEntriesCount(long companyId) {
		return _sacpEntryService.getCompanySACPEntriesCount(companyId);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.getSACPEntry(companyId, name);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.getSACPEntry(sacpEntryId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_sacpEntryService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sacpEntryService.updateSACPEntry(sacpEntryId, allowedServices,
			name, titleMap, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SACPEntryService getWrappedSACPEntryService() {
		return _sacpEntryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSACPEntryService(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	@Override
	public SACPEntryService getWrappedService() {
		return _sacpEntryService;
	}

	@Override
	public void setWrappedService(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	private SACPEntryService _sacpEntryService;
}