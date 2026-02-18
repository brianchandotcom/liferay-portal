/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_3;

import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Tancredi Covioli
 */
public class CountryResourcePermissionUpgradeProcess extends UpgradeProcess {

	public CountryResourcePermissionUpgradeProcess(
		CompanyLocalService companyLocalService,
		CountryLocalService countryLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_companyLocalService = companyLocalService;
		_countryLocalService = countryLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				PortletKeys.PORTAL, "MANAGE_COUNTRIES");

		if (resourceAction == null) {
			return;
		}

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					PortletKeys.PORTAL)) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction)) {

				continue;
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(), PortletKeys.PORTAL,
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(), ActionKeys.ADD_COUNTRY)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(), PortletKeys.PORTAL,
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(), ActionKeys.ADD_COUNTRY);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.DELETE)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.DELETE);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.PERMISSIONS)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.PERMISSIONS);
			}

			if (!_resourcePermissionLocalService.hasResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.UPDATE)) {

				_resourcePermissionLocalService.addResourcePermission(
					resourcePermission.getCompanyId(), Country.class.getName(),
					resourcePermission.getScope(),
					String.valueOf(resourcePermission.getCompanyId()),
					resourcePermission.getRoleId(), ActionKeys.UPDATE);
			}

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), "MANAGE_COUNTRIES");
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);

		_companyLocalService.forEachCompany(
			company -> {
				User user = company.getGuestUser();

				for (Country country :
						_countryLocalService.getCompanyCountries(
							company.getCompanyId())) {

					long count =
						_resourcePermissionLocalService.
							getResourcePermissionsCount(
								country.getCompanyId(), Country.class.getName(),
								ResourceConstants.SCOPE_INDIVIDUAL,
								String.valueOf(country.getPrimaryKeyObj()));

					if (count > 0) {
						continue;
					}

					_resourceLocalService.addResources(
						country.getCompanyId(), 0, user.getUserId(),
						Country.class.getName(), country.getCountryId(), false,
						false, false);
				}
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final CountryLocalService _countryLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}