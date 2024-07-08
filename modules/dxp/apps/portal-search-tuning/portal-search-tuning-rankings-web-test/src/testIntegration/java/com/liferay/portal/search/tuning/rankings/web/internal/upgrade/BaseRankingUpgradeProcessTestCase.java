/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade;

import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.model.JSONStorageEntry;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.search.tuning.rankings.index.Ranking;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;

/**
 * @author Almir Ferreira
 */
public abstract class BaseRankingUpgradeProcessTestCase
	extends BaseCTUpgradeProcessTestCase {

	@BeforeClass
	public static void setUp() throws Exception {
		companyId = TestPropsValues.getCompanyId();
		rankingClassNameId = classNameLocalService.getClassNameId(
			Ranking.class);
		rankingIndexName = _rankingIndexNameBuilder.getRankingIndexName(
			companyId);
	}

	@After
	public void tearDown() {
		Iterator<Long> iterator = _jsonStorageEntryClassPKs.iterator();

		while (iterator.hasNext()) {
			jsonStorageEntryLocalService.deleteJSONStorageEntries(
				rankingClassNameId, iterator.next());

			iterator.remove();
		}
	}

	@Override
	protected CTModel<?> addCTModel() {
		JSONStorageEntry jsonStorageEntry =
			jsonStorageEntryLocalService.createJSONStorageEntry(
				counterLocalService.increment());

		jsonStorageEntry.setClassNameId(rankingClassNameId);
		jsonStorageEntry.setClassPK(1);
		jsonStorageEntry.setKey("inactive");
		jsonStorageEntry.setValue(true);

		return jsonStorageEntryLocalService.updateJSONStorageEntry(
			jsonStorageEntry);
	}

	protected void addRanking(long classNameId, long classPK) {
		jsonStorageEntryLocalService.addJSONStorageEntries(
			companyId, classNameId, classPK,
			JSONUtil.put(
				"aliases", ""
			).put(
				"groupExternalReferenceCode", ""
			).put(
				"hiddenDocumentIds", ""
			).put(
				"inactive", "false"
			).put(
				"indexName", rankingIndexName
			).put(
				"name", "test"
			).put(
				"pins", ""
			).put(
				"queryString", "test"
			).put(
				"rankingDocumentId",
				Ranking.class.getName() + "_PORTLET_" + classPK
			).put(
				"sxpBlueprintExternalReferenceCode", ""
			).toString());

		_jsonStorageEntryClassPKs.add(classPK);
	}

	@Override
	protected CTService<?> getCTService() {
		return jsonStorageEntryLocalService;
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

		return jsonStorageEntryLocalService.updateJSONStorageEntry(
			jsonStorageEntry);
	}

	@Inject
	protected static ClassNameLocalService classNameLocalService;

	protected static long companyId;
	protected static long rankingClassNameId;
	protected static RankingIndexName rankingIndexName;

	@Inject
	protected CounterLocalService counterLocalService;

	@Inject
	protected JSONStorageEntryLocalService jsonStorageEntryLocalService;

	@Inject
	private static RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Inject(
		filter = "(&(component.name=com.liferay.portal.search.tuning.rankings.web.internal.upgrade.registry.PortalSearchTuningRankingsWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private final List<Long> _jsonStorageEntryClassPKs = new ArrayList<>();

}