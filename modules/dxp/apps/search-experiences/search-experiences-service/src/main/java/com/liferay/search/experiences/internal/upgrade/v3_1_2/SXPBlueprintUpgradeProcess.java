/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.upgrade.v3_1_2;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.search.experiences.internal.util.SXPBlueprintUpgradeContributorStorageSchemaUtil;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.List;

/**
 * @author Joshua Cords
 */
public class SXPBlueprintUpgradeProcess extends UpgradeProcess {

	public SXPBlueprintUpgradeProcess(
		CompanyLocalService companyLocalService,
		SXPBlueprintLocalService sxpBlueprintLocalService) {

		_companyLocalService = companyLocalService;
		_sxpBlueprintLocalService = sxpBlueprintLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				List<SXPBlueprint> sxpBlueprints =
					_sxpBlueprintLocalService.getSXPBlueprints(companyId);

				for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
					sxpBlueprint =
						SXPBlueprintUpgradeContributorStorageSchemaUtil.
							upgradeContributorStorageSchema(sxpBlueprint);

					_sxpBlueprintLocalService.updateSXPBlueprint(sxpBlueprint);
				}
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final SXPBlueprintLocalService _sxpBlueprintLocalService;

}