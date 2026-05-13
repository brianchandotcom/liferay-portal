/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.DynamicQueryEntry;
import com.liferay.portal.tools.service.builder.test.service.DynamicQueryEntryLocalService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DynamicQueryEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		long now = System.currentTimeMillis();

		_dynamicQueryEntries.add(
			_addDynamicQueryEntry(
				"alpha", "first", 100, 1, new Date(now - 4000),
				new Date(now - 4000)));
		_dynamicQueryEntries.add(
			_addDynamicQueryEntry(
				"beta", null, 200, 2, new Date(now - 3000),
				new Date(now - 1500)));
		_dynamicQueryEntries.add(
			_addDynamicQueryEntry(
				"gamma", "third", 300, 1, new Date(now - 2000),
				new Date(now - 2000)));
		_dynamicQueryEntries.add(
			_addDynamicQueryEntry(
				"delta", "fourth", 400, 3, new Date(now - 1000),
				new Date(now - 500)));
	}

	@AfterClass
	public static void tearDownClass() {
		for (DynamicQueryEntry dynamicQueryEntry : _dynamicQueryEntries) {
			_dynamicQueryEntryLocalService.deleteDynamicQueryEntry(
				dynamicQueryEntry);
		}
	}

	@Test
	public void testAddOrderAsc() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("amount"));

		_assertDynamicQueryResult(
			dynamicQuery, "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testAddOrderByComparator() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		OrderFactoryUtil.addOrderByComparator(
			dynamicQuery,
			new OrderByComparator<DynamicQueryEntry>() {

				@Override
				public int compare(
					DynamicQueryEntry dynamicQueryEntry1,
					DynamicQueryEntry dynamicQueryEntry2) {

					return Long.compare(
						dynamicQueryEntry2.getAmount(),
						dynamicQueryEntry1.getAmount());
				}

				@Override
				public String[] getOrderByFields() {
					return new String[] {"amount"};
				}

				@Override
				public boolean isAscending() {
					return false;
				}

				@Override
				public boolean isAscending(String field) {
					return false;
				}

			});

		_assertDynamicQueryResult(
			dynamicQuery, "delta", "gamma", "beta", "alpha");
	}

	@Test
	public void testAddOrderDesc() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.desc("amount"));

		_assertDynamicQueryResult(
			dynamicQuery, "delta", "gamma", "beta", "alpha");
	}

	@Test
	public void testEq() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", "alpha"));

		_assertDynamicQueryResult(dynamicQuery, "alpha");
	}

	@Test
	public void testIn() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("status", new Integer[] {1, 3}));
		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));

		_assertDynamicQueryResult(dynamicQuery, "alpha", "gamma", "delta");
	}

	@Test
	public void testInLargeCollectionTriggersDisjunction() throws Exception {
		DBManager dbManager = ReflectionTestUtil.getFieldValue(
			DBManagerUtil.class, "_dbManager");

		int dbInMaxParameters = RandomTestUtil.randomInt(10, 100);

		try (AutoCloseable autoCloseable =
				ReflectionTestUtil.setFieldValueWithAutoCloseable(
					dbManager, "_databaseInMaxParameters", dbInMaxParameters)) {

			Assert.assertEquals(
				dbInMaxParameters, DBManagerUtil.getDBInMaxParameters());

			List<Long> ids = new ArrayList<>(dbInMaxParameters + 1);

			for (int i = 0; i < dbInMaxParameters; i++) {
				ids.add(i * -1L);
			}

			DynamicQueryEntry dynamicQueryEntry = _dynamicQueryEntries.get(0);

			ids.add(dynamicQueryEntry.getDynamicQueryEntryId());

			DynamicQuery dynamicQuery =
				_dynamicQueryEntryLocalService.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.in("dynamicQueryEntryId", ids));

			_assertDynamicQueryResult(dynamicQuery, "alpha");
		}
	}

	@Test
	public void testNoLimit() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));

		_assertDynamicQueryResult(
			dynamicQuery, "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testSetLimit() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(0, 2);

		_assertDynamicQueryResult(dynamicQuery, "alpha", "beta");
	}

	@Test
	public void testSetLimitAllPos() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		_assertDynamicQueryResult(
			dynamicQuery, "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testSetLimitBothNegative() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.setLimit(-5, -3);

		_assertDynamicQueryResult(dynamicQuery);
	}

	@Test
	public void testSetLimitEndAllPos() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(2, QueryUtil.ALL_POS);

		_assertDynamicQueryResult(dynamicQuery, "gamma", "delta");
	}

	@Test
	public void testSetLimitEndNegative() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.setLimit(QueryUtil.ALL_POS, -50);

		_assertDynamicQueryResult(dynamicQuery);
	}

	@Test
	public void testSetLimitNegativeStart() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(-5, 3);

		_assertDynamicQueryResult(dynamicQuery, "alpha", "beta", "gamma");
	}

	@Test
	public void testSetLimitSingleResult() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(1, 2);

		_assertDynamicQueryResult(dynamicQuery, "beta");
	}

	@Test
	public void testSetLimitStartEqualsEnd() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.setLimit(2, 2);

		_assertDynamicQueryResult(dynamicQuery);
	}

	@Test
	public void testSetLimitStartGreaterThanEnd() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.setLimit(5, 2);

		_assertDynamicQueryResult(dynamicQuery);
	}

	@Test
	public void testSqlRestriction() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.sqlRestriction(
				"name = ?", "alpha", Type.STRING));

		_assertDynamicQueryResult(dynamicQuery, "alpha");
	}

	@Test
	public void testSqlRestrictionLike() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.sqlRestriction("name like 'alph%'"));

		_assertDynamicQueryResult(dynamicQuery, "alpha");
	}

	@Test
	public void testSqlRestrictionLikeEscape() throws Exception {
		DynamicQueryEntry dynamicQueryEntry = _addDynamicQueryEntry(
			"a%b", null, 500, 0, new Date(), new Date());

		try {
			DynamicQuery dynamicQuery =
				_dynamicQueryEntryLocalService.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.sqlRestriction(
					"name like 'a=%%' ESCAPE '='"));

			_assertDynamicQueryResult(dynamicQuery, "a%b");
		}
		finally {
			_dynamicQueryEntryLocalService.deleteDynamicQueryEntry(
				dynamicQueryEntry);
		}
	}

	@Test
	public void testSqlRestrictionNotLike() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.sqlRestriction("name not like 'alph%'"));
		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));

		_assertDynamicQueryResult(dynamicQuery, "beta", "gamma", "delta");
	}

	private static DynamicQueryEntry _addDynamicQueryEntry(
			String name, String description, long amount, int status,
			Date createDate, Date modifiedDate)
		throws Exception {

		DynamicQueryEntry dynamicQueryEntry =
			_dynamicQueryEntryLocalService.createDynamicQueryEntry(
				RandomTestUtil.nextLong());

		dynamicQueryEntry.setCompanyId(TestPropsValues.getCompanyId());
		dynamicQueryEntry.setUserId(TestPropsValues.getUserId());
		dynamicQueryEntry.setCreateDate(createDate);
		dynamicQueryEntry.setModifiedDate(modifiedDate);
		dynamicQueryEntry.setName(name);
		dynamicQueryEntry.setDescription(description);
		dynamicQueryEntry.setAmount(amount);
		dynamicQueryEntry.setStatus(status);

		return _dynamicQueryEntryLocalService.addDynamicQueryEntry(
			dynamicQueryEntry);
	}

	private void _assertDynamicQueryResult(
		DynamicQuery dynamicQuery, String... expectedNames) {

		List<?> results = _dynamicQueryEntryLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(
			results.toString(), expectedNames.length, results.size());

		for (int i = 0; i < expectedNames.length; i++) {
			Object result = results.get(i);

			String name;

			if (result instanceof DynamicQueryEntry) {
				DynamicQueryEntry dynamicQueryEntry = (DynamicQueryEntry)result;

				name = dynamicQueryEntry.getName();
			}
			else {
				name = (String)result;
			}

			Assert.assertEquals(results.toString(), expectedNames[i], name);
		}
	}

	private static final List<DynamicQueryEntry> _dynamicQueryEntries =
		new ArrayList<>();

	@Inject
	private static DynamicQueryEntryLocalService _dynamicQueryEntryLocalService;

}