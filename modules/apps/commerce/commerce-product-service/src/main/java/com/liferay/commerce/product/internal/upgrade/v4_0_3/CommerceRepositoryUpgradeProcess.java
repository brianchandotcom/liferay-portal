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