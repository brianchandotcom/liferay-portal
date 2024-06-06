/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade;

import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

/**
 * @author Almir Ferreira
 */
public abstract class BaseRankingUpgradeProcessTestCase
	extends BaseCTUpgradeProcessTestCase {

	@Override
	protected CTModel<?> addCTModel() {
		JSONStorageEntry jsonStorageEntry =
			_jsonStorageEntryLocalService.createJSONStorageEntry(
				_counterLocalService.increment());

		jsonStorageEntry.setClassNameId(
			_portal.getClassNameId(Ranking.class.getName()));
		jsonStorageEntry.setClassPK(1);
		jsonStorageEntry.setKey("inactive");
		jsonStorageEntry.setValue(true);

		return _jsonStorageEntryLocalService.updateJSONStorageEntry(
			jsonStorageEntry);
	}

	@Override
	protected CTService<?> getCTService() {
		return _jsonStorageEntryLocalService;
	}

	protected abstract String getUpgradeStepClassName();

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, getUpgradeStepClassName());

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) {
		JSONStorageEntry jsonStorageEntry = (JSONStorageEntry)ctModel;

		jsonStorageEntry.setValue(false);

		return _jsonStorageEntryLocalService.updateJSONStorageEntry(
			jsonStorageEntry);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.portal.search.tuning.rankings.web.internal.upgrade.registry.PortalSearchTuningRankingsWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private JSONStorageEntryLocalService _jsonStorageEntryLocalService;

	@Inject
	private Portal _portal;

}