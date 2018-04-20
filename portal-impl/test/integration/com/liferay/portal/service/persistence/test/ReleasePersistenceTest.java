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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchReleaseException;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.ReleasePersistence;
import com.liferay.portal.kernel.service.persistence.ReleaseUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
public class ReleasePersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ReleaseUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Release> iterator = _releases.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Release release = _persistence.create(pk);

		Assert.assertNotNull(release);

		Assert.assertEquals(release.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Release newRelease = addRelease();

		_persistence.remove(newRelease);

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertNull(existingRelease);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRelease();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Release newRelease = _persistence.create(pk);

		newRelease.setMvccVersion(RandomTestUtil.nextLong());

		newRelease.setCreateDate(RandomTestUtil.nextDate());

		newRelease.setModifiedDate(RandomTestUtil.nextDate());

		newRelease.setServletContextName(RandomTestUtil.randomString());

		newRelease.setSchemaVersion(RandomTestUtil.randomString());

		newRelease.setBuildNumber(RandomTestUtil.nextInt());

		newRelease.setBuildDate(RandomTestUtil.nextDate());

		newRelease.setVerified(RandomTestUtil.randomBoolean());

		newRelease.setState(RandomTestUtil.nextInt());

		newRelease.setTestString(RandomTestUtil.randomString());

		_releases.add(_persistence.update(newRelease));

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease.getMvccVersion(),
			newRelease.getMvccVersion());
		Assert.assertEquals(existingRelease.getReleaseId(),
			newRelease.getReleaseId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getCreateDate()),
			Time.getShortTimestamp(newRelease.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getModifiedDate()),
			Time.getShortTimestamp(newRelease.getModifiedDate()));
		Assert.assertEquals(existingRelease.getServletContextName(),
			newRelease.getServletContextName());
		Assert.assertEquals(existingRelease.getSchemaVersion(),
			newRelease.getSchemaVersion());
		Assert.assertEquals(existingRelease.getBuildNumber(),
			newRelease.getBuildNumber());
		Assert.assertEquals(Time.getShortTimestamp(
				existingRelease.getBuildDate()),
			Time.getShortTimestamp(newRelease.getBuildDate()));
		Assert.assertEquals(existingRelease.isVerified(),
			newRelease.isVerified());
		Assert.assertEquals(existingRelease.getState(), newRelease.getState());
		Assert.assertEquals(existingRelease.getTestString(),
			newRelease.getTestString());
	}

	@Test
	public void testCountByServletContextName() throws Exception {
		_persistence.countByServletContextName("");

		_persistence.countByServletContextName("null");

		_persistence.countByServletContextName((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test(expected = NoSuchReleaseException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Release> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Release_", "mvccVersion",
			true, "releaseId", true, "createDate", true, "modifiedDate", true,
			"servletContextName", true, "schemaVersion", true, "buildNumber",
			true, "buildDate", true, "verified", true, "state", true,
			"testString", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Release newRelease = addRelease();

		Release existingRelease = _persistence.fetchByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Release missingRelease = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRelease);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Release newRelease1 = addRelease();
		Release newRelease2 = addRelease();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRelease1.getPrimaryKey());
		primaryKeys.add(newRelease2.getPrimaryKey());

		Map<Serializable, Release> releases = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, releases.size());
		Assert.assertEquals(newRelease1,
			releases.get(newRelease1.getPrimaryKey()));
		Assert.assertEquals(newRelease2,
			releases.get(newRelease2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Release> releases = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(releases.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Release newRelease = addRelease();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRelease.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Release> releases = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, releases.size());
		Assert.assertEquals(newRelease, releases.get(newRelease.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Release> releases = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(releases.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Release newRelease = addRelease();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRelease.getPrimaryKey());

		Map<Serializable, Release> releases = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, releases.size());
		Assert.assertEquals(newRelease, releases.get(newRelease.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ReleaseLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Release>() {
				@Override
				public void performAction(Release release) {
					Assert.assertNotNull(release);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				newRelease.getReleaseId()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Release existingRelease = result.get(0);

		Assert.assertEquals(existingRelease, newRelease);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("releaseId",
				RandomTestUtil.nextLong()));

		List<Release> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Release newRelease = addRelease();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		Object newReleaseId = newRelease.getReleaseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { newReleaseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingReleaseId = result.get(0);

		Assert.assertEquals(existingReleaseId, newReleaseId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Release.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("releaseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("releaseId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Release newRelease = addRelease();

		_persistence.clearCache();

		Release existingRelease = _persistence.findByPrimaryKey(newRelease.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingRelease.getServletContextName(),
				ReflectionTestUtil.invoke(existingRelease,
					"getOriginalServletContextName", new Class<?>[0])));
	}

	protected Release addRelease() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Release release = _persistence.create(pk);

		release.setMvccVersion(RandomTestUtil.nextLong());

		release.setCreateDate(RandomTestUtil.nextDate());

		release.setModifiedDate(RandomTestUtil.nextDate());

		release.setServletContextName(RandomTestUtil.randomString());

		release.setSchemaVersion(RandomTestUtil.randomString());

		release.setBuildNumber(RandomTestUtil.nextInt());

		release.setBuildDate(RandomTestUtil.nextDate());

		release.setVerified(RandomTestUtil.randomBoolean());

		release.setState(RandomTestUtil.nextInt());

		release.setTestString(RandomTestUtil.randomString());

		_releases.add(_persistence.update(release));

		return release;
	}

	private List<Release> _releases = new ArrayList<Release>();
	private ReleasePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}