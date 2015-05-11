/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.service.persistence.TeamPersistence;
import com.liferay.portal.service.persistence.TeamUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class TeamPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = TeamUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Team> iterator = _teams.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Team team = _persistence.create(pk);

		Assert.assertNotNull(team);

		Assert.assertEquals(team.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Team newTeam = addTeam();

		_persistence.remove(newTeam);

		Team existingTeam = _persistence.fetchByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertNull(existingTeam);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTeam();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Team newTeam = _persistence.create(pk);

		newTeam.setMvccVersion(RandomTestUtil.nextLong());

		newTeam.setCompanyId(RandomTestUtil.nextLong());

		newTeam.setUserId(RandomTestUtil.nextLong());

		newTeam.setUserName(RandomTestUtil.randomString());

		newTeam.setCreateDate(RandomTestUtil.nextDate());

		newTeam.setModifiedDate(RandomTestUtil.nextDate());

		newTeam.setGroupId(RandomTestUtil.nextLong());

		newTeam.setName(RandomTestUtil.randomString());

		newTeam.setDescription(RandomTestUtil.randomString());

		_teams.add(_persistence.update(newTeam));

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam.getMvccVersion(),
			newTeam.getMvccVersion());
		Assert.assertEquals(existingTeam.getTeamId(), newTeam.getTeamId());
		Assert.assertEquals(existingTeam.getCompanyId(), newTeam.getCompanyId());
		Assert.assertEquals(existingTeam.getUserId(), newTeam.getUserId());
		Assert.assertEquals(existingTeam.getUserName(), newTeam.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(existingTeam.getCreateDate()),
			Time.getShortTimestamp(newTeam.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingTeam.getModifiedDate()),
			Time.getShortTimestamp(newTeam.getModifiedDate()));
		Assert.assertEquals(existingTeam.getGroupId(), newTeam.getGroupId());
		Assert.assertEquals(existingTeam.getName(), newTeam.getName());
		Assert.assertEquals(existingTeam.getDescription(),
			newTeam.getDescription());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_N(0L, StringPool.NULL);

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam, newTeam);
	}

	@Test(expected = NoSuchTeamException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Team> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Team", "mvccVersion", true,
			"teamId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "groupId", true,
			"name", true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.fetchByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam, newTeam);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Team missingTeam = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTeam);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Team newTeam1 = addTeam();
		Team newTeam2 = addTeam();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTeam1.getPrimaryKey());
		primaryKeys.add(newTeam2.getPrimaryKey());

		Map<Serializable, Team> teams = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, teams.size());
		Assert.assertEquals(newTeam1, teams.get(newTeam1.getPrimaryKey()));
		Assert.assertEquals(newTeam2, teams.get(newTeam2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Team> teams = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(teams.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Team newTeam = addTeam();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTeam.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Team> teams = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, teams.size());
		Assert.assertEquals(newTeam, teams.get(newTeam.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Team> teams = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(teams.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Team newTeam = addTeam();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTeam.getPrimaryKey());

		Map<Serializable, Team> teams = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, teams.size());
		Assert.assertEquals(newTeam, teams.get(newTeam.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = TeamLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Team team = (Team)object;

					Assert.assertNotNull(team);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Team newTeam = addTeam();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("teamId",
				newTeam.getTeamId()));

		List<Team> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Team existingTeam = result.get(0);

		Assert.assertEquals(existingTeam, newTeam);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("teamId",
				RandomTestUtil.nextLong()));

		List<Team> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Team newTeam = addTeam();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("teamId"));

		Object newTeamId = newTeam.getTeamId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("teamId",
				new Object[] { newTeamId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTeamId = result.get(0);

		Assert.assertEquals(existingTeamId, newTeamId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("teamId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("teamId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Team newTeam = addTeam();

		_persistence.clearCache();

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam.getGroupId(),
			ReflectionTestUtil.invoke(existingTeam, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(Validator.equals(existingTeam.getName(),
				ReflectionTestUtil.invoke(existingTeam, "getOriginalName",
					new Class<?>[0])));
	}

	protected Team addTeam() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Team team = _persistence.create(pk);

		team.setMvccVersion(RandomTestUtil.nextLong());

		team.setCompanyId(RandomTestUtil.nextLong());

		team.setUserId(RandomTestUtil.nextLong());

		team.setUserName(RandomTestUtil.randomString());

		team.setCreateDate(RandomTestUtil.nextDate());

		team.setModifiedDate(RandomTestUtil.nextDate());

		team.setGroupId(RandomTestUtil.nextLong());

		team.setName(RandomTestUtil.randomString());

		team.setDescription(RandomTestUtil.randomString());

		_teams.add(_persistence.update(team));

		return team;
	}

	private List<Team> _teams = new ArrayList<Team>();
	private TeamPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}