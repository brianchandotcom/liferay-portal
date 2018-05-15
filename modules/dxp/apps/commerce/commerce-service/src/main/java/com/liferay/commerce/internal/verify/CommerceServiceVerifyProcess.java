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

package com.liferay.commerce.internal.verify;

import com.liferay.commerce.service.CommerceCountryLocalService;
import com.liferay.commerce.service.CommerceWarehouseLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.commerce.service",
	service = {CommerceServiceVerifyProcess.class, VerifyProcess.class}
)
public class CommerceServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyGroups();
	}

	protected void verifyCountries(Group group, ServiceContext serviceContext)
		throws Exception {

		if (_commerceCountryLocalService.getCommerceCountriesCount(
				group.getGroupId()) == 0) {

			_commerceCountryLocalService.importDefaultCountries(serviceContext);
		}
	}

	protected void verifyDefaultWarehouse(ServiceContext serviceContext)
		throws PortalException {

		_commerceWarehouseLocalService.importDefaultCommerceWarehouse(
			serviceContext);
	}

	protected void verifyGroups() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				List<Group> groups = _groupLocalService.getGroups(
					company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				for (Group group : groups) {
					serviceContext.setLanguageId(group.getDefaultLanguageId());
					serviceContext.setScopeGroupId(group.getGroupId());
					serviceContext.setUserId(group.getCreatorUserId());

					verifyCountries(group, serviceContext);
					verifyDefaultWarehouse(serviceContext);
				}
			}
		}
	}

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

	@Reference
	private CommerceWarehouseLocalService _commerceWarehouseLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}