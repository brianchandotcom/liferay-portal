/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(service = OrganizationImporter.class)
public class OrganizationImporter {

	public void importOrganizations(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_importOrganization(jsonArray.getJSONObject(i), 0, serviceContext);
		}
	}

	private void _importOrganization(
			JSONObject jsonObject, long parentOrganizationId,
			ServiceContext serviceContext)
		throws PortalException {

		String name = jsonObject.getString("name");

		Organization organization = _organizationLocalService.fetchOrganization(
			serviceContext.getCompanyId(), name);

		if (organization != null) {
			return;
		}

		String twoLetterISOCode = jsonObject.getString("twoLetterISOCode");

		Country country = _countryService.getCountryByA2(
			serviceContext.getCompanyId(), twoLetterISOCode);

		organization = _organizationLocalService.addOrganization(
			null, serviceContext.getUserId(), parentOrganizationId, name,
			OrganizationConstants.TYPE_ORGANIZATION, 0, country.getCountryId(),
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			false, serviceContext);

		JSONArray suborganizationsJSONArray = jsonObject.getJSONArray(
			"suborganizations");

		if (suborganizationsJSONArray != null) {
			for (int i = 0; i < suborganizationsJSONArray.length(); i++) {
				_importOrganization(
					suborganizationsJSONArray.getJSONObject(i),
					organization.getOrganizationId(), serviceContext);
			}
		}
	}

	@Reference
	private CountryService _countryService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}