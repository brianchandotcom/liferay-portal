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

package com.liferay.commerce.product.internal.upgrade.v4_0_2;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Michael Bowerman
 */
public class CommerceRepositoryUpgradeProcess extends UpgradeProcess {

	public CommerceRepositoryUpgradeProcess(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _upgradeCommerceRepository(companyId));
	}

	private void _upgradeCommerceRepository(long companyId) throws Exception {
		Company company = _companyLocalService.getCompany(companyId);

		runSQL(
			StringBundler.concat(
				"update Repository set portletId = '",
				CPConstants.SERVICE_NAME_PRODUCT, "' where groupId = ",
				company.getGroupId(), " and name = ",
				"'image.default.company.logo' and (portletId is null or ",
				"portletId = '')"));
	}

	private final CompanyLocalService _companyLocalService;

}