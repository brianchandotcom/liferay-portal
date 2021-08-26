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

package com.liferay.headless.commerce.admin.account.internal.util.v1_0;

import com.liferay.headless.commerce.admin.account.dto.v1_0.AccountOrganization;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class AccountOrganizationUtil {

	public static long getOrganizationId(
			OrganizationLocalService organizationLocalService,
			AccountOrganization accountOrganization, long companyId)
		throws PortalException {

		Organization organization;

		if (Validator.isNotNull(
				accountOrganization.getOrganizationExternalReferenceCode())) {

			organization =
				organizationLocalService.fetchOrganizationByReferenceCode(
					companyId,
					accountOrganization.getOrganizationExternalReferenceCode());

			if (organization == null) {
				String organizationExternalReferenceCode =
					accountOrganization.getOrganizationExternalReferenceCode();

				throw new NoSuchOrganizationException(
					"Unable to find organization with external reference " +
						"code " + organizationExternalReferenceCode);
			}
		}
		else {
			organization = organizationLocalService.getOrganization(
				accountOrganization.getOrganizationId());
		}

		return organization.getOrganizationId();
	}

}