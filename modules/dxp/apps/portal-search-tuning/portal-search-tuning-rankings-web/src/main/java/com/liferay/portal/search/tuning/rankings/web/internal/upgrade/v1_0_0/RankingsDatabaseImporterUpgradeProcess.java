/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.rankings.storage.RankingsDatabaseImporter;

/**
 * @author Bryan Engler
 */
public class RankingsDatabaseImporterUpgradeProcess extends UpgradeProcess {

	public RankingsDatabaseImporterUpgradeProcess(
		CompanyLocalService companyLocalService,
		RankingsDatabaseImporter rankingsDatabaseImporter) {

		_companyLocalService = companyLocalService;
		_rankingsDatabaseImporter = rankingsDatabaseImporter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _rankingsDatabaseImporter.populateDatabase(
				company.getCompanyId()));
	}

	private final CompanyLocalService _companyLocalService;
	private final RankingsDatabaseImporter _rankingsDatabaseImporter;

}