/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBManager;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
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
import java.util.Arrays;
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
	public void testAddOrder() {
		_testAddOrder(
			OrderFactoryUtil.asc("amount"), "alpha", "beta", "gamma", "delta");
		_testAddOrder(
			OrderFactoryUtil.desc("amount"), "delta", "gamma", "beta", "alpha");
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
				public String getOrderBy() {
					return "amount DESC";
				}

			});

		_testDynamicQuery(dynamicQuery, "delta", "gamma", "beta", "alpha");
	}

	@Test
	public void testAlias() {
		Class<?> clazz = _dynamicQueryEntryLocalService.getClass();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DynamicQueryEntry.class, "parent", clazz.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("parent.name", "alpha"));

		_testDynamicQuery(dynamicQuery, "alpha");

		Property property = PropertyFactoryUtil.forName("parent.name");

		dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DynamicQueryEntry.class, "parent", clazz.getClassLoader());

		dynamicQuery.add(property.eq("alpha"));

		_testDynamicQuery(dynamicQuery, "alpha");

		dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DynamicQueryEntry.class, "child", clazz.getClassLoader());

		dynamicQuery.add(
			RestrictionsFactoryUtil.eqProperty("child.status", "status"));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("child.name", "alpha"));
		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("child.name"));

		property = PropertyFactoryUtil.forName("name");

		_testCriterion(property.eq(dynamicQuery), "alpha");
	}

	@Test
	public void testCompareProperty() {
		_testCriterion(
			RestrictionsFactoryUtil.eqProperty("createDate", "modifiedDate"),
			"alpha", "gamma");
		_testCriterion(
			RestrictionsFactoryUtil.geProperty("modifiedDate", "createDate"),
			"alpha", "beta", "gamma", "delta");
		_testCriterion(
			RestrictionsFactoryUtil.gtProperty("modifiedDate", "createDate"),
			"beta", "delta");
		_testCriterion(
			RestrictionsFactoryUtil.leProperty("createDate", "modifiedDate"),
			"alpha", "beta", "gamma", "delta");
		_testCriterion(
			RestrictionsFactoryUtil.ltProperty("createDate", "modifiedDate"),
			"beta", "delta");
		_testCriterion(
			RestrictionsFactoryUtil.neProperty("createDate", "modifiedDate"),
			"beta", "delta");
	}

	@Test
	public void testConjunction() {
		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

		conjunction.add(RestrictionsFactoryUtil.ge("amount", 100L));
		conjunction.add(RestrictionsFactoryUtil.le("amount", 300L));
		conjunction.add(RestrictionsFactoryUtil.eq("status", 1));

		_testCriterion(conjunction, "alpha", "gamma");
	}

	@Test
	public void testDisjunction() {
		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(RestrictionsFactoryUtil.isNull("description"));
		disjunction.add(RestrictionsFactoryUtil.eq("name", "alpha"));
		disjunction.add(RestrictionsFactoryUtil.like("name", "g%"));

		_testCriterion(disjunction, "alpha", "beta", "gamma");
	}

	@Test
	public void testEq() {
		_testCriterion(RestrictionsFactoryUtil.eq("name", "alpha"), "alpha");

		Property property = PropertyFactoryUtil.forName("name");

		_testCriterion(property.eq("alpha"), "alpha");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", "alpha"));

		dynamicQuery.setProjection(property);

		_testCriterion(property.eq(dynamicQuery), "alpha");

		_testCriterion(property.eqAll(dynamicQuery), "alpha");

		_testCriterion(
			RestrictionsFactoryUtil.sqlRestriction(
				"name = ?", "alpha", Type.STRING),
			"alpha");
	}

	@Test
	public void testGe() {
		_testCriterion(
			RestrictionsFactoryUtil.ge("amount", 200L), "beta", "gamma",
			"delta");

		Property property = PropertyFactoryUtil.forName("amount");

		_testCriterion(property.ge(200L), "beta", "gamma", "delta");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 2));
		dynamicQuery.setProjection(property);

		_testCriterion(property.ge(dynamicQuery), "beta", "gamma", "delta");

		dynamicQuery = _dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 1));
		dynamicQuery.setProjection(property);

		_testCriterion(property.geAll(dynamicQuery), "gamma", "delta");
		_testCriterion(
			property.geSome(dynamicQuery), "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testGt() {
		_testCriterion(
			RestrictionsFactoryUtil.gt("amount", 200L), "gamma", "delta");

		Property property = PropertyFactoryUtil.forName("amount");

		_testCriterion(property.gt(200L), "gamma", "delta");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 2));
		dynamicQuery.setProjection(property);

		_testCriterion(property.gt(dynamicQuery), "gamma", "delta");

		dynamicQuery = _dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 1));
		dynamicQuery.setProjection(property);

		_testCriterion(property.gtAll(dynamicQuery), "delta");
		_testCriterion(property.gtSome(dynamicQuery), "beta", "gamma", "delta");
	}

	@Test
	public void testIn() {
		_testCriterion(
			RestrictionsFactoryUtil.in("status", new Integer[] {1, 3}), "alpha",
			"gamma", "delta");

		Property property = PropertyFactoryUtil.forName("status");

		_testCriterion(
			property.in(Arrays.asList(1, 3)), "alpha", "gamma", "delta");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.ne("status", 2));
		dynamicQuery.setProjection(property);

		_testCriterion(property.in(dynamicQuery), "alpha", "gamma", "delta");
		_testCriterion(property.notIn(dynamicQuery), "beta");

		dynamicQuery = _dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 1));
		dynamicQuery.setProjection(
			ProjectionFactoryUtil.distinct(
				ProjectionFactoryUtil.property("status")));

		_testCriterion(property.in(dynamicQuery), "alpha", "gamma");
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

			_testDynamicQuery(dynamicQuery, "alpha");
		}
	}

	@Test
	public void testLe() {
		_testCriterion(
			RestrictionsFactoryUtil.le("amount", 200L), "alpha", "beta");

		Property property = PropertyFactoryUtil.forName("amount");

		_testCriterion(property.le(200L), "alpha", "beta");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 2));
		dynamicQuery.setProjection(property);

		_testCriterion(property.le(dynamicQuery), "alpha", "beta");

		dynamicQuery = _dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 1));
		dynamicQuery.setProjection(property);

		_testCriterion(property.leAll(dynamicQuery), "alpha");
		_testCriterion(property.leSome(dynamicQuery), "alpha", "beta", "gamma");
	}

	@Test
	public void testLike() throws Exception {
		_testCriterion(RestrictionsFactoryUtil.ilike("name", "A%"), "alpha");

		_testCriterion(
			RestrictionsFactoryUtil.like("name", "%a"), "alpha", "beta",
			"gamma", "delta");

		Property property = PropertyFactoryUtil.forName("name");

		_testCriterion(property.like("%a"), "alpha", "beta", "gamma", "delta");

		_testCriterion(
			RestrictionsFactoryUtil.sqlRestriction("name like 'alph%'"),
			"alpha");

		_testCriterion(
			RestrictionsFactoryUtil.sqlRestriction("name not like 'alph%'"),
			"beta", "gamma", "delta");

		DynamicQueryEntry dynamicQueryEntry = _addDynamicQueryEntry(
			"a%b", null, 500, 0, new Date(), new Date());

		try {
			DynamicQuery dynamicQuery =
				_dynamicQueryEntryLocalService.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.sqlRestriction(
					"name like 'a=%%' ESCAPE '='"));

			_testDynamicQuery(dynamicQuery, "a%b");
		}
		finally {
			_dynamicQueryEntryLocalService.deleteDynamicQueryEntry(
				dynamicQueryEntry);
		}
	}

	@Test
	public void testLt() {
		_testCriterion(RestrictionsFactoryUtil.lt("amount", 200L), "alpha");

		Property property = PropertyFactoryUtil.forName("amount");

		_testCriterion(property.lt(200L), "alpha");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("status", 2));
		dynamicQuery.setProjection(property);

		_testCriterion(property.lt(dynamicQuery), "alpha");

		dynamicQuery = _dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.ge("amount", 300L));
		dynamicQuery.setProjection(property);

		_testCriterion(property.ltAll(dynamicQuery), "alpha", "beta");
		_testCriterion(property.ltSome(dynamicQuery), "alpha", "beta", "gamma");
	}

	@Test
	public void testMix() {
		_testCriterion(
			RestrictionsFactoryUtil.and(
				RestrictionsFactoryUtil.eq("status", 1),
				RestrictionsFactoryUtil.ge("amount", 200L)),
			"gamma");

		_testCriterion(
			RestrictionsFactoryUtil.between("amount", 150L, 350L), "beta",
			"gamma");

		_testCriterion(
			RestrictionsFactoryUtil.not(
				RestrictionsFactoryUtil.eq("name", "alpha")),
			"beta", "gamma", "delta");

		_testCriterion(
			RestrictionsFactoryUtil.or(
				RestrictionsFactoryUtil.eq("name", "alpha"),
				RestrictionsFactoryUtil.eq("name", "delta")),
			"alpha", "delta");
	}

	@Test
	public void testNe() {
		_testCriterion(
			RestrictionsFactoryUtil.ne("name", "alpha"), "beta", "gamma",
			"delta");

		Property property = PropertyFactoryUtil.forName("name");

		_testCriterion(property.ne("alpha"), "beta", "gamma", "delta");

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("name", "alpha"));
		dynamicQuery.setProjection(property);

		_testCriterion(property.ne(dynamicQuery), "beta", "gamma", "delta");
	}

	@Test
	public void testNestedConjunctionInsideDisjunction() {
		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

		conjunction.add(RestrictionsFactoryUtil.eq("status", 1));
		conjunction.add(RestrictionsFactoryUtil.ge("amount", 200L));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(conjunction);
		disjunction.add(RestrictionsFactoryUtil.eq("name", "delta"));

		_testCriterion(disjunction, "gamma", "delta");
	}

	@Test
	public void testNestedDisjunctionInsideConjunction() {
		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(RestrictionsFactoryUtil.eq("name", "alpha"));
		disjunction.add(RestrictionsFactoryUtil.eq("name", "gamma"));

		Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

		conjunction.add(disjunction);
		conjunction.add(RestrictionsFactoryUtil.eq("status", 1));

		_testCriterion(conjunction, "alpha", "gamma");
	}

	@Test
	public void testNoLimit() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));

		_testDynamicQuery(dynamicQuery, "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testNull() {
		_testCriterion(
			RestrictionsFactoryUtil.isNotNull("description"), "alpha", "gamma",
			"delta");
		_testCriterion(RestrictionsFactoryUtil.isNull("description"), "beta");

		Property property = PropertyFactoryUtil.forName("description");

		_testCriterion(property.isNotNull(), "alpha", "gamma", "delta");
		_testCriterion(property.isNull(), "beta");
	}

	@Test
	public void testProjectionAggregates() {
		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.count("dynamicQueryEntryId"));
		projectionList.add(ProjectionFactoryUtil.countDistinct("status"));
		projectionList.add(ProjectionFactoryUtil.rowCount());
		projectionList.add(ProjectionFactoryUtil.sum("amount"));
		projectionList.add(ProjectionFactoryUtil.max("amount"));
		projectionList.add(ProjectionFactoryUtil.min("amount"));
		projectionList.add(ProjectionFactoryUtil.avg("amount"));

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.setProjection(projectionList);

		List<Object[]> rows = _dynamicQueryEntryLocalService.dynamicQuery(
			dynamicQuery);

		Object[] row = rows.get(0);

		Assert.assertEquals(Long.valueOf(_dynamicQueryEntries.size()), row[0]);
		Assert.assertEquals(3L, row[1]);
		Assert.assertEquals(Long.valueOf(_dynamicQueryEntries.size()), row[2]);
		Assert.assertEquals(1000L, row[3]);
		Assert.assertEquals(400L, row[4]);
		Assert.assertEquals(100L, row[5]);
		Assert.assertEquals(250.0, (Double)row[6], 0.001);
	}

	@Test
	public void testProjectionGroupProperty() {
		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(ProjectionFactoryUtil.groupProperty("status"));
		projectionList.add(ProjectionFactoryUtil.count("dynamicQueryEntryId"));

		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("status"));
		dynamicQuery.setProjection(projectionList);

		List<Object[]> rows = _dynamicQueryEntryLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(rows.toString(), 3, rows.size());

		Assert.assertArrayEquals(new Object[] {1, 2L}, rows.get(0));
		Assert.assertArrayEquals(new Object[] {2, 1L}, rows.get(1));
		Assert.assertArrayEquals(new Object[] {3, 1L}, rows.get(2));
	}

	@Test
	public void testProjectionSqlGroupProjection() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("status"));
		dynamicQuery.setProjection(
			ProjectionFactoryUtil.sqlGroupProjection(
				"status AS s, count(*) AS c", "status", new String[] {"s", "c"},
				new Type[] {Type.INTEGER, Type.LONG}));

		List<Object[]> rows = _dynamicQueryEntryLocalService.dynamicQuery(
			dynamicQuery);

		Assert.assertEquals(rows.toString(), 3, rows.size());

		Assert.assertArrayEquals(new Object[] {1, 2L}, rows.get(0));
		Assert.assertArrayEquals(new Object[] {2, 1L}, rows.get(1));
		Assert.assertArrayEquals(new Object[] {3, 1L}, rows.get(2));
	}

	@Test
	public void testProjectionSqlProjection() {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("amount"));
		dynamicQuery.setProjection(
			ProjectionFactoryUtil.sqlProjection(
				"name AS sqlName", new String[] {"sqlName"},
				new Type[] {Type.STRING}));

		_testDynamicQuery(dynamicQuery, "alpha", "beta", "gamma", "delta");
	}

	@Test
	public void testSetLimit() {
		_testSetLimit(-5, -3);
		_testSetLimit(2, 2);
		_testSetLimit(5, 2);
		_testSetLimit(0, 2, "alpha", "beta");
		_testSetLimit(1, 2, "beta");
		_testSetLimit(-5, 3, "alpha", "beta", "gamma");
		_testSetLimit(2, QueryUtil.ALL_POS, "gamma", "delta");
		_testSetLimit(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, "alpha", "beta", "gamma",
			"delta");
		_testSetLimit(QueryUtil.ALL_POS, -50);
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

	private void _testAddOrder(Order order, String... expectedNames) {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(order);

		_testDynamicQuery(dynamicQuery, expectedNames);
	}

	private void _testCriterion(Criterion criterion, String... expectedNames) {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.add(criterion);
		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));

		_testDynamicQuery(dynamicQuery, expectedNames);
	}

	private void _testDynamicQuery(
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

	private void _testSetLimit(int start, int end, String... expectedNames) {
		DynamicQuery dynamicQuery =
			_dynamicQueryEntryLocalService.dynamicQuery();

		dynamicQuery.addOrder(OrderFactoryUtil.asc("dynamicQueryEntryId"));
		dynamicQuery.setLimit(start, end);

		_testDynamicQuery(dynamicQuery, expectedNames);
	}

	private static final List<DynamicQueryEntry> _dynamicQueryEntries =
		new ArrayList<>();

	@Inject
	private static DynamicQueryEntryLocalService _dynamicQueryEntryLocalService;

}