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

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.tuning.rankings.storage.RankingsDatabaseImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v1_0_0.RankingsDatabaseImporterUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class RankingsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new RankingsDatabaseImporterUpgradeProcess(
				_companyLocalService, _rankingsDatabaseImporter));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private RankingsDatabaseImporter _rankingsDatabaseImporter;

}