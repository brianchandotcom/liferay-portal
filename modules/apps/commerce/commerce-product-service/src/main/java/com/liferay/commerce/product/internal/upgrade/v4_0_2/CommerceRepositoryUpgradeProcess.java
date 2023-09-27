/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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