/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.main;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.CerebroEngineClient;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;
import com.liferay.osb.faro.provisioning.client.model.OSBOfferingEntry;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class FaroSubscriptionDisplayTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_cerebroEngineClient.getPageViews(
				Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			1L
		);

		Mockito.when(
			_contactsEngineClient.getIndividualsCreatedBetweenCount(
				Mockito.any(), Mockito.any(), Mockito.any())
		).thenReturn(
			1L
		);
	}

	@Test
	public void testSetUsageCounts1() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONFactoryUtil.createJSONObject(), instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusDays(5);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					))
			).put(
				"total", 1
			).put(
				"totalSinceLastAnniversary", 1
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					))
			).put(
				"total", 1
			).put(
				"totalSinceLastAnniversary", 1
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts2() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
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
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
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
			),
			instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusDays(5);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 2
					).put(
						"countSinceLastAnniversary", 2
					))
			).put(
				"total", 2
			).put(
				"totalSinceLastAnniversary", 2
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 2
					).put(
						"countSinceLastAnniversary", 2
					))
			).put(
				"total", 2
			).put(
				"totalSinceLastAnniversary", 2
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts3() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
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
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
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
			),
			instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusMonths(1);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 2
					)
				)
			).put(
				"total", 2
			).put(
				"totalSinceLastAnniversary", 2
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 2
					)
				)
			).put(
				"total", 2
			).put(
				"totalSinceLastAnniversary", 2
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts4() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		LocalDateTime localDateTime2 = localDateTime1.plusMonths(1);

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime2),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			).put(
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime2),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			),
			instant.toEpochMilli());

		localDateTime2 = localDateTime2.plusDays(1);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 3
					).put(
						"countSinceLastAnniversary", 4
					)
				)
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 4
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 3
					).put(
						"countSinceLastAnniversary", 4
					)
				)
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 4
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts5() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime1.plusMonths(1)),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			).put(
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime1.plusMonths(1)),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			),
			instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusYears(1);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues", JSONFactoryUtil.createJSONObject()
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 0
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues", JSONFactoryUtil.createJSONObject()
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 0
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts6() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BASIC_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BASIC_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime1.plusMonths(1)),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			).put(
				"pageViewsCounts",
				JSONUtil.put(
					"monthlyValues",
					JSONUtil.put(
						_formatLocalDateTime(localDateTime1),
						JSONUtil.put(
							"count", 1
						).put(
							"countSinceLastAnniversary", 1
						)
					).put(
						_formatLocalDateTime(localDateTime1.plusMonths(1)),
						JSONUtil.put(
							"count", 2
						).put(
							"countSinceLastAnniversary", 3
						)
					)
				).put(
					"total", 3
				).put(
					"totalSinceLastAnniversary", 3
				)
			),
			instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusYears(1);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime1.plusMonths(1)),
					JSONUtil.put(
						"count", 2
					).put(
						"countSinceLastAnniversary", 3
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 4
					)
				)
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 4
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime1),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 1
					)
				).put(
					_formatLocalDateTime(localDateTime1.plusMonths(1)),
					JSONUtil.put(
						"count", 2
					).put(
						"countSinceLastAnniversary", 3
					)
				).put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 4
					)
				)
			).put(
				"total", 4
			).put(
				"totalSinceLastAnniversary", 4
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	@Test
	public void testSetUsageCounts7() throws Exception {
		LocalDate localDate = LocalDate.now();

		LocalDateTime localDateTime1 = LocalDateTime.of(
			localDate.minusYears(1), LocalTime.MIN);

		FaroSubscriptionDisplay faroSubscriptionDisplay =
			_createFaroSubscriptionDisplay(
				ProductConstants.BUSINESS_PRODUCT_ENTRY_ID,
				_toDate(localDateTime1));

		Instant instant = localDateTime1.toInstant(ZoneOffset.UTC);

		FaroProject faroProject = _mockFaroProject(
			ProductConstants.BUSINESS_PRODUCT_NAME,
			JSONUtil.put(
				"individualsCountSinceLastAnniversary", 10
			).put(
				"pageViewsCountSinceLastAnniversary", 500
			),
			instant.toEpochMilli());

		LocalDateTime localDateTime2 = localDateTime1.plusDays(5);

		faroSubscriptionDisplay.setUsageCounts(
			_cerebroEngineClient, _contactsEngineClient,
			_toDate(localDateTime2), faroProject);

		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 11
					))
			).put(
				"total", 11
			).put(
				"totalSinceLastAnniversary", 11
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getIndividualsCounts()));
		_assertCounts(
			JSONUtil.put(
				"monthlyValues",
				JSONUtil.put(
					_formatLocalDateTime(localDateTime2),
					JSONUtil.put(
						"count", 1
					).put(
						"countSinceLastAnniversary", 501
					))
			).put(
				"total", 501
			).put(
				"totalSinceLastAnniversary", 501
			),
			JSONFactoryUtil.createJSONObject(
				faroSubscriptionDisplay.getPageViewsCounts()));
	}

	private void _assertCounts(
		JSONObject actualCountsJSONObject,
		JSONObject expectedCountsJSONObject) {

		Assert.assertEquals(
			expectedCountsJSONObject.getLong("total"),
			actualCountsJSONObject.getLong("total"));

		Assert.assertEquals(
			expectedCountsJSONObject.getLong("totalSinceLastAnniversary"),
			actualCountsJSONObject.getLong("totalSinceLastAnniversary"));

		JSONObject expectedMonthlyValuesJSONObject =
			expectedCountsJSONObject.getJSONObject("monthlyValues");

		Set<String> expectedKeySet = expectedMonthlyValuesJSONObject.keySet();

		JSONObject actualMonthlyValuesJSONObject =
			actualCountsJSONObject.getJSONObject("monthlyValues");

		Set<String> actualKeySet = actualMonthlyValuesJSONObject.keySet();

		Assert.assertEquals(
			actualKeySet.toString(), expectedKeySet.size(),
			actualKeySet.size());

		for (String key : expectedMonthlyValuesJSONObject.keySet()) {
			JSONObject actualMonthlyValueJSONObject =
				actualMonthlyValuesJSONObject.getJSONObject(key);

			JSONObject expectedMonthlyValueJSONObject =
				expectedMonthlyValuesJSONObject.getJSONObject(key);

			Assert.assertEquals(
				expectedMonthlyValueJSONObject.getLong("count"),
				actualMonthlyValueJSONObject.getLong("count"));

			Assert.assertEquals(
				expectedMonthlyValueJSONObject.getLong(
					"countSinceLastAnniversary"),
				actualMonthlyValueJSONObject.getLong(
					"countSinceLastAnniversary"));
		}
	}

	private FaroSubscriptionDisplay _createFaroSubscriptionDisplay(
		String productEntryId, Date startDate) {

		OSBAccountEntry osbAccountEntry = new OSBAccountEntry();

		OSBOfferingEntry osbOfferingEntry = new OSBOfferingEntry();

		osbOfferingEntry.setProductEntryId(productEntryId);
		osbOfferingEntry.setStartDate(startDate);
		osbOfferingEntry.setStatus(
			ProductConstants.OSB_OFFERING_ENTRY_STATUS_ACTIVE);

		osbAccountEntry.setOfferingEntries(
			Collections.singletonList(osbOfferingEntry));

		return new FaroSubscriptionDisplay(osbAccountEntry);
	}

	private String _formatLocalDateTime(LocalDateTime localDateTime) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"MMM yyyy");

		return localDateTime.format(dateTimeFormatter);
	}

	private FaroProject _mockFaroProject(
		String productName, JSONObject subscriptionJSONObject,
		long subscriptionModifiedTime) {

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
				"name", productName
			).toString()
		);

		Mockito.when(
			faroProject.getSubscriptionModifiedTime()
		).thenReturn(
			subscriptionModifiedTime
		);

		return faroProject;
	}

	private Date _toDate(LocalDateTime localDateTime) {
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));

		return Date.from(zonedDateTime.toInstant());
	}

	private final CerebroEngineClient _cerebroEngineClient = Mockito.mock(
		CerebroEngineClient.class);
	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);

}