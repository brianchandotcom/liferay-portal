/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v3_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class RankingJSONStorageEntryUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
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

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		JSONStorageEntry jsonStorageEntry = (JSONStorageEntry)ctModel;

		jsonStorageEntry.setValue(false);

		return _jsonStorageEntryLocalService.updateJSONStorageEntry(
			jsonStorageEntry);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.search.tuning.rankings.web.internal.upgrade." +
			"v3_0_0.RankingJSONStorageEntryUpgradeProcess";

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