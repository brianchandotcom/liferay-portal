/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v4_0_3;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Michael Bowerman
 */
public class CommerceRepositoryUpgradeProcess extends UpgradeProcess {

	public CommerceRepositoryUpgradeProcess(
		CompanyLocalService companyLocalService, Portal portal,
		RepositoryLocalService repositoryLocalService) {

		_companyLocalService = companyLocalService;
		_portal = portal;
		_repositoryLocalService = repositoryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("ChangesetEntry")) {
			return;
		}

		long classNameId = _portal.getClassNameId(Repository.class.getName());

		_companyLocalService.forEachCompanyId(
			companyId -> _upgradeCommerceRepository(companyId, classNameId));
	}

	private void _upgradeCommerceRepository(long companyId, long classNameId)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		Repository repository = _repositoryLocalService.fetchRepository(
			company.getGroupId(), "image.default.company.logo",
			CPConstants.SERVICE_NAME_PRODUCT);

		if (repository == null) {
			return;
		}

		runSQL(
			StringBundler.concat(
				"delete from ChangesetEntry where classNameId = ", classNameId,
				" and classPK = ", repository.getRepositoryId()));
	}

	private final CompanyLocalService _companyLocalService;
	private final Portal _portal;
	private final RepositoryLocalService _repositoryLocalService;

}