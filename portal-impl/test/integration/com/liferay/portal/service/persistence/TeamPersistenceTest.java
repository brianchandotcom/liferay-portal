/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.TeamModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class TeamPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		Team newTeam = _persistence.create(pk);

		newTeam.setCompanyId(ServiceTestUtil.nextLong());

		newTeam.setUserId(ServiceTestUtil.nextLong());

		newTeam.setUserName(ServiceTestUtil.randomString());

		newTeam.setCreateDate(ServiceTestUtil.nextDate());

		newTeam.setModifiedDate(ServiceTestUtil.nextDate());

		newTeam.setGroupId(ServiceTestUtil.nextLong());

		newTeam.setName(ServiceTestUtil.randomString());

		newTeam.setDescription(ServiceTestUtil.randomString());

		newTeam.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newTeam);

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

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
		Assert.assertEquals(existingTeam.getMvccVersion(),
			newTeam.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam, newTeam);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTeamException");
		}
		catch (NoSuchTeamException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Team", "teamId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "groupId", true, "name", true,
			"description", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.fetchByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeam, newTeam);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Team missingTeam = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTeam);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new TeamActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Team team = (Team)object;

					Assert.assertNotNull(team);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Team newTeam = addTeam();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				Team.class.getClassLoader());

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
				Team.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("teamId",
				ServiceTestUtil.nextLong()));

		List<Team> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Team newTeam = addTeam();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				Team.class.getClassLoader());

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
				Team.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("teamId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("teamId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Team newTeam = addTeam();

		_persistence.clearCache();

		TeamModelImpl existingTeamModelImpl = (TeamModelImpl)_persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		Assert.assertEquals(existingTeamModelImpl.getGroupId(),
			existingTeamModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(existingTeamModelImpl.getName(),
				existingTeamModelImpl.getOriginalName()));
	}

	protected Team addTeam() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Team team = _persistence.create(pk);

		team.setCompanyId(ServiceTestUtil.nextLong());

		team.setUserId(ServiceTestUtil.nextLong());

		team.setUserName(ServiceTestUtil.randomString());

		team.setCreateDate(ServiceTestUtil.nextDate());

		team.setModifiedDate(ServiceTestUtil.nextDate());

		team.setGroupId(ServiceTestUtil.nextLong());

		team.setName(ServiceTestUtil.randomString());

		team.setDescription(ServiceTestUtil.randomString());

		team.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(team);

		return team;
	}

	private static Log _log = LogFactoryUtil.getLog(TeamPersistenceTest.class);
	private TeamPersistence _persistence = (TeamPersistence)PortalBeanLocatorUtil.locate(TeamPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}