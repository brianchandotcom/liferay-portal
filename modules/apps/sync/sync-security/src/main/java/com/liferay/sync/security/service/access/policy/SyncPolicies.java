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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.service.SAPEntryLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class SyncPolicies {

	public static final Object[][] POLICIES = new Object[][] {
		{
			"SYNC_DEFAULT",
			"com.liferay.sync.service.SyncDLObjectService#getSyncContext", true
		},
		{"SYNC_TOKEN", "com.liferay.sync.service.*", false}
	};

	@Activate
	public void activated() throws Exception {
		for (Company company : _companyLocalService.getCompanies()) {
			for (Object[] policy : POLICIES) {
				String name = String.valueOf(policy[0]);
				String allowedServiceSignatures = String.valueOf(policy[1]);
				boolean defaultSAPEntry = GetterUtil.getBoolean(policy[2]);

				SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
					company.getCompanyId(), name);

				if (sapEntry != null) {
					continue;
				}

				try {
					Map<Locale, String> map = new HashMap<>();

					map.put(LocaleUtil.getDefault(), name);

					_sapEntryLocalService.addSAPEntry(
						_userLocalService.getDefaultUserId(
							company.getCompanyId()),
						allowedServiceSignatures, defaultSAPEntry, true, name,
						map, new ServiceContext());
				}
				catch (PortalException e) {
					throw new Exception(
						"Unable to add default SAP entry for company " +
							company.getCompanyId(),
						e);
				}
			}
		}
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

	private CompanyLocalService _companyLocalService;
	private SAPEntryLocalService _sapEntryLocalService;
	private UserLocalService _userLocalService;

}