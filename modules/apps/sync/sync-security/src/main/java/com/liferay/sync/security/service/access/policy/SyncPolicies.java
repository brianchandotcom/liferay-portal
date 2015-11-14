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

	public static final String[] POLICY_NAMES = {"SYNC_DEFAULT", "SYNC_TOKEN"};

	@Activate
	public void activated() throws Exception {
		for (Company company : _companyLocalService.getCompanies()) {
			long defaultUserId = _userLocalService.getDefaultUserId(
				company.getCompanyId());

			for (int i = 0; i < POLICY_NAMES.length; i++) {
				SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
					company.getCompanyId(), POLICY_NAMES[i]);

				if (sapEntry != null) {
					continue;
				}

				try {
					Map<Locale, String> map = new HashMap<>();

					map.put(LocaleUtil.getDefault(), POLICY_NAMES[i]);

					_sapEntryLocalService.addSAPEntry(
						defaultUserId, _ALLOWED_SERVICE_SIGNATURES[i],
						_DEFAULT_SAP_ENTRY[i], true, POLICY_NAMES[i], map,
						new ServiceContext());
				}
				catch (PortalException e) {
					throw new Exception(
						"Unable to add SAP default policy for company " +
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

	private static final String[] _ALLOWED_SERVICE_SIGNATURES =
		{"com.liferay.sync.service.SyncDLObjectService#getSyncContext",
			"com.liferay.sync.service.*"
		};

	private static final boolean[] _DEFAULT_SAP_ENTRY = {true, false};

	private CompanyLocalService _companyLocalService;
	private SAPEntryLocalService _sapEntryLocalService;
	private UserLocalService _userLocalService;

}