/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v2_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.tuning.rankings.web.internal.upgrade.BaseRankingUpgradeProcessTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Almir Ferreira
 */
@RunWith(Arquillian.class)
public class RenameRankingUpgradeProcessTest
	extends BaseRankingUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgradeProcess() throws Exception {
		long classNameId = classNameLocalService.getClassNameId(
			"com.liferay.portal.search.tuning.rankings.web.internal.index." +
				"Ranking");
		long classPK = counterLocalService.increment();

		addRanking(classNameId, classPK);

		runUpgrade();

		JSONObject rankingJSONObject =
			jsonStorageEntryLocalService.getJSONObject(
				rankingClassNameId, classPK);

		Assert.assertNotNull(rankingJSONObject);

		Assert.assertNull(classNameLocalService.fetchClassName(classNameId));
	}

	@Override
	protected String getUpgradeStepClassName() {
		return "com.liferay.portal.search.tuning.rankings.web.internal." +
			"upgrade.v2_0_0.RenameRankingUpgradeProcess";
	}

}