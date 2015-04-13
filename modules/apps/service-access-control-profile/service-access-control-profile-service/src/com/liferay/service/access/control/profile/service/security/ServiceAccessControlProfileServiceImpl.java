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

package com.liferay.service.access.control.profile.service.security;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.ac.ServiceAccessControlProfile;
import com.liferay.portal.kernel.security.ac.ServiceAccessControlProfileService;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.SACPEntryService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = ServiceAccessControlProfileService.class)
public class ServiceAccessControlProfileServiceImpl
	implements ServiceAccessControlProfileService {

	@Override
	public ServiceAccessControlProfile getServiceAccessControlProfile(
		long companyId, String name) {

		try {
			return toServiceAccessControlProfile(
				_sacpEntryService.getSACPEntry(companyId, name));
		}
		catch (PortalException e) {
			return null;
		}
	}

	@Override
	public List<ServiceAccessControlProfile> getServiceAccessControlProfiles(
		long companyId, int start, int end) {

		return toServiceAccessControlProfile(
			_sacpEntryService.getCompanySACPEntries(companyId, start, end));
	}

	@Override
	public int getServiceAccessControlProfilesCount(long companyId) {
		return _sacpEntryService.getCompanySACPEntriesCount(companyId);
	}

	@Reference(unbind = "-")
	protected void setSACPEntryService(SACPEntryService sacpEntryService) {
		_sacpEntryService = sacpEntryService;
	}

	protected List<ServiceAccessControlProfile> toServiceAccessControlProfile(
		List<SACPEntry> sacpEntries) {

		if (sacpEntries == null) {
			return null;
		}

		List<ServiceAccessControlProfile> serviceAccessControlProfiles =
			new ArrayList<>(sacpEntries.size());

		for (SACPEntry sacpEntry : sacpEntries) {
			serviceAccessControlProfiles.add(
				toServiceAccessControlProfile(sacpEntry));
		}

		return serviceAccessControlProfiles;
	}

	protected ServiceAccessControlProfile toServiceAccessControlProfile(
		SACPEntry sacpEntry) {

		if (sacpEntry != null) {
			return new ServiceAccessControlProfileImpl(sacpEntry);
		}

		return null;
	}

	private SACPEntryService _sacpEntryService;

}