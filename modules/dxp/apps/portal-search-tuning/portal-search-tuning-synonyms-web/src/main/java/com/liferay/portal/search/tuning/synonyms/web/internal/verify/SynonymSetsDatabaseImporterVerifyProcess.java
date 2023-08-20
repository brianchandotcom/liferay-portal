/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.tuning.synonyms.storage.SynonymSetsDatabaseImporter;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class SynonymSetsDatabaseImporterVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_companyLocalService.forEachCompany(
			company -> _synonymSetsDatabaseImporter.populateDatabase(
				company.getCompanyId()));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SynonymSetsDatabaseImporter _synonymSetsDatabaseImporter;

}