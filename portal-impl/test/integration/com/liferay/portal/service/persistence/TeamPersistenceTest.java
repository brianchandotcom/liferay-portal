/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.TeamModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (TeamPersistence)PortalBeanLocatorUtil.locate(TeamPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Team team = _persistence.create(pk);

		assertNotNull(team);

		assertEquals(team.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Team newTeam = addTeam();

		_persistence.remove(newTeam);

		Team existingTeam = _persistence.fetchByPrimaryKey(newTeam.getPrimaryKey());

		assertNull(existingTeam);
	}

	public void testUpdateNew() throws Exception {
		addTeam();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Team newTeam = _persistence.create(pk);

		newTeam.setCompanyId(nextLong());

		newTeam.setUserId(nextLong());

		newTeam.setUserName(randomString());

		newTeam.setCreateDate(nextDate());

		newTeam.setModifiedDate(nextDate());

		newTeam.setGroupId(nextLong());

		newTeam.setName(randomString());

		newTeam.setDescription(randomString());

		_persistence.update(newTeam, false);

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		assertEquals(existingTeam.getTeamId(), newTeam.getTeamId());
		assertEquals(existingTeam.getCompanyId(), newTeam.getCompanyId());
		assertEquals(existingTeam.getUserId(), newTeam.getUserId());
		assertEquals(existingTeam.getUserName(), newTeam.getUserName());
		assertEquals(Time.getShortTimestamp(existingTeam.getCreateDate()),
			Time.getShortTimestamp(newTeam.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingTeam.getModifiedDate()),
			Time.getShortTimestamp(newTeam.getModifiedDate()));
		assertEquals(existingTeam.getGroupId(), newTeam.getGroupId());
		assertEquals(existingTeam.getName(), newTeam.getName());
		assertEquals(existingTeam.getDescription(), newTeam.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		assertEquals(existingTeam, newTeam);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTeamException");
		}
		catch (NoSuchTeamException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Team newTeam = addTeam();

		Team existingTeam = _persistence.fetchByPrimaryKey(newTeam.getPrimaryKey());

		assertEquals(existingTeam, newTeam);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Team missingTeam = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingTeam);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Team newTeam = addTeam();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				Team.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("teamId",
				newTeam.getTeamId()));

		List<Team> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Team existingTeam = result.get(0);

		assertEquals(existingTeam, newTeam);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				Team.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("teamId", nextLong()));

		List<Team> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingTeamId = result.get(0);

		assertEquals(existingTeamId, newTeamId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Team.class,
				Team.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("teamId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("teamId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Team newTeam = addTeam();

		_persistence.clearCache();

		TeamModelImpl existingTeamModelImpl = (TeamModelImpl)_persistence.findByPrimaryKey(newTeam.getPrimaryKey());

		assertEquals(existingTeamModelImpl.getGroupId(),
			existingTeamModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingTeamModelImpl.getName(),
				existingTeamModelImpl.getOriginalName()));
	}

	protected Team addTeam() throws Exception {
		long pk = nextLong();

		Team team = _persistence.create(pk);

		team.setCompanyId(nextLong());

		team.setUserId(nextLong());

		team.setUserName(randomString());

		team.setCreateDate(nextDate());

		team.setModifiedDate(nextDate());

		team.setGroupId(nextLong());

		team.setName(randomString());

		team.setDescription(randomString());

		_persistence.update(team, false);

		return team;
	}

	private TeamPersistence _persistence;
}