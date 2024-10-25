/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v21_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

/**
 * @author Rachael Koestartyo
 */
@RunWith(Arquillian.class)
public class UpgradeFaroProjectUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			JSONFactoryUtil.createJSONObject(), instant.toEpochMilli());

		faroProject.setDataSourceConnected(true);

		faroProject = _faroProjectLocalService.addFaroProject(faroProject);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		Assert.assertTrue(faroProject.isDataSourceConnected());
	}

	private FaroProject _mockFaroProject(
		JSONObject subscriptionJSONObject, long subscriptionModifiedTime) {

		FaroProject faroProject = Mockito.mock(FaroProject.class);

		Mockito.when(
			faroProject.getState()
		).thenReturn(
			FaroProjectConstants.STATE_READY
		);

		Mockito.when(
			faroProject.getSubscription()
		).thenReturn(
			subscriptionJSONObject.put(
				"name", "Liferay Analytics Cloud Business"
			).toString()
		);

		Mockito.when(
			faroProject.getSubscriptionModifiedTime()
		).thenReturn(
			subscriptionModifiedTime
		);

		return faroProject;
	}

	private static final String _CLASS_NAME =
		"com.liferay.osb.faro.internal.upgrade.v21_0_0." +
			"UpgradeFaroProjectUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.osb.faro.internal.upgrade.registry.FaroServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private FaroProjectLocalService _faroProjectLocalService;

}