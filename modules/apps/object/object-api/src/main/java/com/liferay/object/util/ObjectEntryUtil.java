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

package com.liferay.object.util;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;

/**
 * @author Mateus Santana
 */
public class ObjectEntryUtil {

	public static long getGroupId(
			ObjectEntry objectEntry, CompanyLocalService companyLocalService)
		throws PortalException {

		// TODO If permission checking works with the group's company ID, then
		// we should ensure it is always set and remove this workaround

		long groupId = objectEntry.getGroupId();

		if (groupId == 0) {
			Company company = companyLocalService.getCompany(
				objectEntry.getCompanyId());

			groupId = company.getGroupId();
		}

		return groupId;
	}

}