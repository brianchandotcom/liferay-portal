/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.main;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.web.internal.model.display.contacts.ProjectUsageDisplay;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class FaroUsageDisplayTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testProjectUsageDisplay() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			"Faro Project Test", "KOR-0001", instant.toEpochMilli(),
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime("MMM yyyy", localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						))
				).put(
					"total", 1
				).put(
					"totalSinceLastAnniversary", 1
				)
			).put(
				"individualsCountSinceLastAnniversary", 1
			).put(
				"lastAnniversaryDate", instant.toEpochMilli()
			).put(
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime("MMM yyyy", localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						))
				).put(
					"total", 1
				).put(
					"totalSinceLastAnniversary", 1
				)
			).put(
				"pageViewsCountSinceLastAnniversary", 1
			),
			instant.toEpochMilli(), "weDeployKey");

		ProjectUsageDisplay projectUsageDisplay = new ProjectUsageDisplay(
			faroProject, true, true, true);

		Assert.assertEquals(
			"Faro Project Test", projectUsageDisplay.getCorpProjectName());
		Assert.assertEquals(
			"KOR-0001", projectUsageDisplay.getCorpProjectUuid());
		Assert.assertEquals(
			1, projectUsageDisplay.getIndividualsCountSinceLastAnniversary());
		Assert.assertEquals(
			_formatLocalDateTime(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", localDateTime1),
			projectUsageDisplay.getLastAccessDateString());
		Assert.assertEquals(
			_formatLocalDateTime(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", localDateTime1),
			projectUsageDisplay.getLastAnniversaryDateString());
		Assert.assertEquals(
			1, projectUsageDisplay.getPageViewsCountSinceLastAnniversary());
		Assert.assertEquals(
			"weDeployKey", projectUsageDisplay.getWeDeployKey());
		Assert.assertFalse(projectUsageDisplay.getOffline());

		_assertCountsDisplay(
			projectUsageDisplay.getIndividualsCountsDisplay(),
			Collections.singletonMap(
				_formatLocalDateTime("MMM yyyy", localDateTime1),
				new ProjectUsageDisplay.MonthlyValue(1, 1)),
			1, 1);
		_assertCountsDisplay(
			projectUsageDisplay.getPageViewsCountsDisplay(),
			Collections.singletonMap(
				_formatLocalDateTime("MMM yyyy", localDateTime1),
				new ProjectUsageDisplay.MonthlyValue(1, 1)),
			1, 1);
	}

	private void _assertCountsDisplay(
		ProjectUsageDisplay.CountsDisplay countsDisplay,
		Map<String, ProjectUsageDisplay.MonthlyValue> expectedMonthlyValues,
		int expectedTotal, int expectedTotalSinceLastAnniversary) {

		Assert.assertEquals(
			expectedMonthlyValues, countsDisplay.getMonthlyValues());
		Assert.assertEquals(expectedTotal, countsDisplay.getTotal());
		Assert.assertEquals(
			expectedTotalSinceLastAnniversary,
			countsDisplay.getTotalSinceLastAnniversary());
	}

	private String _formatLocalDateTime(
		String dateFormat, LocalDateTime localDateTime) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			dateFormat);

		return localDateTime.format(dateTimeFormatter);
	}

	private FaroProject _mockFaroProject(
		String corpProjectName, String corpProjectUuid, long lastAccessTime,
		String productName, JSONObject subscriptionJSONObject,
		long subscriptionModifiedTime, String weDeployKey) {

		FaroProject faroProject = Mockito.mock(FaroProject.class);

		Mockito.when(
			faroProject.getCorpProjectName()
		).thenReturn(
			corpProjectName
		);

		Mockito.when(
			faroProject.getCorpProjectUuid()
		).thenReturn(
			corpProjectUuid
		);

		Mockito.when(
			faroProject.getLastAccessTime()
		).thenReturn(
			lastAccessTime
		);

		Mockito.when(
			faroProject.getState()
		).thenReturn(
			FaroProjectConstants.STATE_READY
		);

		Mockito.when(
			faroProject.getSubscription()
		).thenReturn(
			subscriptionJSONObject.put(
				"name", productName
			).toString()
		);

		Mockito.when(
			faroProject.getSubscriptionModifiedTime()
		).thenReturn(
			subscriptionModifiedTime
		);

		Mockito.when(
			faroProject.getWeDeployKey()
		).thenReturn(
			weDeployKey
		);

		return faroProject;
	}

}