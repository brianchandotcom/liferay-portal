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

package com.liferay.commerce.product.internal.verify;

import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
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
	property = "verify.process.name=com.liferay.commerce.product.service",
	service = {CommerceProductServiceVerifyProcess.class, VerifyProcess.class}
)
public class CommerceProductServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCPMeasurementUnits();
	}

	protected void verifyCPMeasurementUnits() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				List<Group> groups = _groupLocalService.getGroups(
					company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				for (Group group : groups) {
					verifyCPMeasurementUnits(group);
				}
			}
		}
	}

	protected void verifyCPMeasurementUnits(Group group) throws Exception {
		if (_cpMeasurementUnitLocalService.getCPMeasurementUnitsCount(
				group.getGroupId()) == 0) {

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setCompanyId(group.getCompanyId());
			serviceContext.setLanguageId(group.getDefaultLanguageId());
			serviceContext.setScopeGroupId(group.getGroupId());
			serviceContext.setUserId(group.getCreatorUserId());

			_cpMeasurementUnitLocalService.importDefaultValues(serviceContext);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}