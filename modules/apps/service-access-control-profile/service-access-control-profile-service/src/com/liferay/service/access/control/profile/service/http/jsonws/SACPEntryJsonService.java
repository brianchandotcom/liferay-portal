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

package com.liferay.service.access.control.profile.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import com.liferay.service.access.control.profile.service.SACPEntryService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for SACPEntry. This utility wraps
 * {@link com.liferay.service.access.control.profile.service.impl.SACPEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=sacp", "json.web.service.context.path=SACPEntry"}, service = SACPEntryJsonService.class)
@JSONWebService
@ProviderType
public class SACPEntryJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.control.profile.service.impl.SACPEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		java.lang.String allowedServiceSignatures, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addSACPEntry(allowedServiceSignatures, name, titleMap,
			serviceContext);
	}

	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteSACPEntry(sacpEntry);
	}

	public com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteSACPEntry(sacpEntryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {
		return _service.getCompanySACPEntries(companyId, start, end);
	}

	public java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc) {
		return _service.getCompanySACPEntries(companyId, start, end, obc);
	}

	public int getCompanySACPEntriesCount(long companyId) {
		return _service.getCompanySACPEntriesCount(companyId);
	}

	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getSACPEntry(companyId, name);
	}

	public com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getSACPEntry(sacpEntryId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServiceSignatures,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateSACPEntry(sacpEntryId, allowedServiceSignatures,
			name, titleMap, serviceContext);
	}

	@Reference
	protected void setService(SACPEntryService service) {
		_service = service;
	}

	private SACPEntryService _service;
}