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

package com.liferay.portal.search.tuning.rankings.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.search.tuning.rankings.exception.NoSuchRankingException;
import com.liferay.portal.search.tuning.rankings.model.Ranking;
import com.liferay.portal.search.tuning.rankings.service.RankingLocalServiceUtil;
import com.liferay.portal.search.tuning.rankings.service.persistence.RankingPersistence;
import com.liferay.portal.search.tuning.rankings.service.persistence.RankingUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class RankingPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.search.tuning.rankings.service"));

	@Before
	public void setUp() {
		_persistence = RankingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Ranking> iterator = _rankings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ranking ranking = _persistence.create(pk);

		Assert.assertNotNull(ranking);

		Assert.assertEquals(ranking.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Ranking newRanking = addRanking();

		_persistence.remove(newRanking);

		Ranking existingRanking = _persistence.fetchByPrimaryKey(
			newRanking.getPrimaryKey());

		Assert.assertNull(existingRanking);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRanking();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ranking newRanking = _persistence.create(pk);

		newRanking.setMvccVersion(RandomTestUtil.nextLong());

		newRanking.setCompanyId(RandomTestUtil.nextLong());

		newRanking.setJson(RandomTestUtil.randomString());

		_rankings.add(_persistence.update(newRanking));

		Ranking existingRanking = _persistence.findByPrimaryKey(
			newRanking.getPrimaryKey());

		Assert.assertEquals(
			existingRanking.getMvccVersion(), newRanking.getMvccVersion());
		Assert.assertEquals(
			existingRanking.getRankingId(), newRanking.getRankingId());
		Assert.assertEquals(
			existingRanking.getCompanyId(), newRanking.getCompanyId());
		Assert.assertEquals(existingRanking.getJson(), newRanking.getJson());
	}

	@Test
	public void testCountBycompanyId() throws Exception {
		_persistence.countBycompanyId(RandomTestUtil.nextLong());

		_persistence.countBycompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Ranking newRanking = addRanking();

		Ranking existingRanking = _persistence.findByPrimaryKey(
			newRanking.getPrimaryKey());

		Assert.assertEquals(existingRanking, newRanking);
	}

	@Test(expected = NoSuchRankingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Ranking> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Ranking", "mvccVersion", true, "rankingId", true, "companyId",
			true, "json", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Ranking newRanking = addRanking();

		Ranking existingRanking = _persistence.fetchByPrimaryKey(
			newRanking.getPrimaryKey());

		Assert.assertEquals(existingRanking, newRanking);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ranking missingRanking = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRanking);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Ranking newRanking1 = addRanking();
		Ranking newRanking2 = addRanking();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRanking1.getPrimaryKey());
		primaryKeys.add(newRanking2.getPrimaryKey());

		Map<Serializable, Ranking> rankings = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, rankings.size());
		Assert.assertEquals(
			newRanking1, rankings.get(newRanking1.getPrimaryKey()));
		Assert.assertEquals(
			newRanking2, rankings.get(newRanking2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Ranking> rankings = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(rankings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Ranking newRanking = addRanking();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRanking.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Ranking> rankings = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, rankings.size());
		Assert.assertEquals(
			newRanking, rankings.get(newRanking.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Ranking> rankings = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(rankings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Ranking newRanking = addRanking();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRanking.getPrimaryKey());

		Map<Serializable, Ranking> rankings = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, rankings.size());
		Assert.assertEquals(
			newRanking, rankings.get(newRanking.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			RankingLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Ranking>() {

				@Override
				public void performAction(Ranking ranking) {
					Assert.assertNotNull(ranking);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Ranking newRanking = addRanking();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Ranking.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("rankingId", newRanking.getRankingId()));

		List<Ranking> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Ranking existingRanking = result.get(0);

		Assert.assertEquals(existingRanking, newRanking);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Ranking.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("rankingId", RandomTestUtil.nextLong()));

		List<Ranking> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Ranking newRanking = addRanking();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Ranking.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rankingId"));

		Object newRankingId = newRanking.getRankingId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"rankingId", new Object[] {newRankingId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRankingId = result.get(0);

		Assert.assertEquals(existingRankingId, newRankingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Ranking.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rankingId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"rankingId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Ranking addRanking() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ranking ranking = _persistence.create(pk);

		ranking.setMvccVersion(RandomTestUtil.nextLong());

		ranking.setCompanyId(RandomTestUtil.nextLong());

		ranking.setJson(RandomTestUtil.randomString());

		_rankings.add(_persistence.update(ranking));

		return ranking;
	}

	private List<Ranking> _rankings = new ArrayList<Ranking>();
	private RankingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}