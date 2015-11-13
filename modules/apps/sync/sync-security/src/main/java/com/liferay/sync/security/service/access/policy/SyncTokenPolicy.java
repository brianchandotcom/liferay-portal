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

package com.liferay.sync.security.service.access.policy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.service.SAPEntryLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class SyncTokenPolicy {

	public static final String POLICY_NAME = "SYNC_TOKEN";

	@Activate
	public void activated() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			long companyId = company.getCompanyId();

			try {
				addPolicy(company.getCompanyId());
			}
			catch (PortalException e) {
				throw new Exception(
					"Unable to add SAP token policy for company " + companyId,
					e);
			}
		}
	}

	protected void addPolicy(long companyId) throws PortalException {
		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, POLICY_NAME);

		if (sapEntry != null) {
			return;
		}

		long userId = _userLocalService.getDefaultUserId(companyId);
		String allowedServiceSignatures = _POLICY_SERVICES;
		boolean defaultSAPEntry = false;
		boolean enabled = true;
		String name = POLICY_NAME;
		Map<Locale, String> titleMap = new HashMap<>();
		titleMap.put(LocaleUtil.getDefault(), POLICY_NAME);

		ServiceContext serviceContext = new ServiceContext();

		_sapEntryLocalService.addSAPEntry(
			userId, allowedServiceSignatures, defaultSAPEntry, enabled, name,
			titleMap, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {

		_sapEntryLocalService = sapEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final String _POLICY_SERVICES = "com.liferay.sync.service.*";

	private CompanyLocalService _companyLocalService;
	private SAPEntryLocalService _sapEntryLocalService;
	private UserLocalService _userLocalService;

}