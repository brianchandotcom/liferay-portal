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

package com.liferay.portal.search.tuning.synonyms.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.synonyms.storage.SynonymSetsDatabaseImporter;

/**
 * @author Bryan Engler
 */
public class SynonymSetsDatabaseImporterUpgradeProcess extends UpgradeProcess {

	public SynonymSetsDatabaseImporterUpgradeProcess(
		CompanyLocalService companyLocalService,
		SynonymSetsDatabaseImporter synonymSetsDatabaseImporter) {

		_companyLocalService = companyLocalService;
		_synonymSetsDatabaseImporter = synonymSetsDatabaseImporter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _synonymSetsDatabaseImporter.populateDatabase(
				company.getCompanyId()));
	}

	private final CompanyLocalService _companyLocalService;
	private final SynonymSetsDatabaseImporter _synonymSetsDatabaseImporter;

}