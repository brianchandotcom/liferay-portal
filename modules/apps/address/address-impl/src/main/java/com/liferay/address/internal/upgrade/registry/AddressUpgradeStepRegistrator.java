/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.registry;

import com.liferay.address.internal.upgrade.v1_0_0.CountryUpgradeProcess;
import com.liferay.address.internal.upgrade.v1_0_1.CountryRegionUpgradeProcess;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = UpgradeStepRegistrator.class)
public class AddressUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0", new CountryUpgradeProcess(_companyLocalService));

		registry.register(
			"1.0.0", "1.0.1",
			new CountryRegionUpgradeProcess(
				_companyLocalService, _counterLocalService,
				_countryLocalService, _regionLocalService));

		registry.register(
			"1.0.1", "1.0.2",
			new com.liferay.address.internal.upgrade.v1_0_2.
				CountryUpgradeProcess(
					_companyLocalService, _counterLocalService,
					_countryLocalService, _jsonFactory, _regionLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private RegionLocalService _regionLocalService;

}